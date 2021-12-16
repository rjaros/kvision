/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.asMono
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Mono
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Spring Boot WebSocket handler
 */
class KVWebSocketHandler(
    private val services: List<KVServiceManager<*>>,
    private val threadLocalWebSocketSession: ThreadLocal<WebSocketSession>,
    private val applicationContext: ApplicationContext
) : WebSocketHandler, CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default) {

    private fun getHandler(session: WebSocketSession): (suspend (
        WebSocketSession, ThreadLocal<WebSocketSession>, ApplicationContext,
        ReceiveChannel<String>, SendChannel<String>
    ) -> Unit) {
        val uri = session.handshakeInfo.uri.toString()
        val route = uri.substring(uri.lastIndexOf('/') + 1)
        return services.mapNotNull {
            it.webSocketRequests["/kvws/$route"]
        }.first()
    }

    override fun handle(session: WebSocketSession): Mono<Void> {
        val handler = getHandler(session)
        val responseChannel = Channel<String>()
        val requestChannel = Channel<String>()
        val output = session.send(responseChannel.consumeAsFlow().asFlux().map(session::textMessage))
        val input = async {
            coroutineScope {
                launch {
                    session.receive().map {
                        it.payloadAsText
                    }.asFlow().collect {
                        requestChannel.send(it)
                    }
                    requestChannel.close()
                }
                launch {
                    handler.invoke(
                        session,
                        threadLocalWebSocketSession,
                        applicationContext,
                        requestChannel,
                        responseChannel
                    )
                    session.close().awaitSingle()
                }
            }
        }.asMono(EmptyCoroutineContext).then()
        return Mono.zip(input, output).then()
    }
}

/**
 * Spring Boot WebSocket configuration
 */
@Configuration
open class KVWebSocketConfig(
    private val services: List<KVServiceManager<*>>,
    private val applicationContext: ApplicationContext
) {
    private val threadLocalWebSocketSession = ThreadLocal<WebSocketSession>()

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    open fun webSocketSession(): WebSocketSession {
        return threadLocalWebSocketSession.get() ?: KVWebSocketSession()
    }

    @Bean
    open fun handlerMapping(): HandlerMapping {
        val map = mapOf("/kvws/*" to KVWebSocketHandler(services, threadLocalWebSocketSession, applicationContext))
        val order = -1
        return SimpleUrlHandlerMapping(map, order)
    }

    @Bean
    open fun handlerAdapter() = WebSocketHandlerAdapter()
}
