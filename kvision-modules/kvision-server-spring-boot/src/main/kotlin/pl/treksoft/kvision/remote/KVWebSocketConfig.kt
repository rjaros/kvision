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
package pl.treksoft.kvision.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpHeaders
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.context.support.GenericWebApplicationContext
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketExtension
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.net.InetSocketAddress
import java.net.URI
import java.security.Principal
import java.util.concurrent.ConcurrentHashMap

const val KV_ROUTE_ID_ATTRIBUTE = "KV_ROUTE_ID_ATTRIBUTE"

/**
 * Automatic websockets configuration.
 */
@Configuration
@EnableWebSocket
open class KVWebSocketConfig : WebSocketConfigurer {

    @Autowired
    lateinit var services: List<KVServiceManager<*>>

    @Autowired
    lateinit var applicationContext: GenericWebApplicationContext

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(socketHandler(), "/kvws/*").setAllowedOrigins("*").addInterceptors(routeInterceptor())
    }

    @Bean
    open fun routeInterceptor(): HandshakeInterceptor {
        return KvHandshakeInterceptor()
    }

    @Bean
    open fun socketHandler(): WebSocketHandler {
        return KvWebSocketHandler(services, applicationContext)
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    open fun webSocketSession(): WebSocketSession {
        return WebSocketSessionHolder.webSocketSession
    }
}

object WebSocketSessionHolder {
    var webSocketSession: WebSocketSession = DummyWebSocketSession()
}

internal open class KvHandshakeInterceptor : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val path = request.uri.path
        val route = path.substring(path.lastIndexOf('/') + 1)
        attributes[KV_ROUTE_ID_ATTRIBUTE] = route
        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
    }
}

@UseExperimental(ExperimentalCoroutinesApi::class)
internal open class KvWebSocketHandler(
    private val services: List<KVServiceManager<*>>,
    private val applicationContext: GenericWebApplicationContext
) : TextWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, Pair<Channel<String>, Channel<String>>>()

    private fun getHandler(session: WebSocketSession): (suspend (
        WebSocketSession, GenericWebApplicationContext,
        ReceiveChannel<String>, SendChannel<String>
    ) -> Unit)? {
        val routeId = session.attributes[KV_ROUTE_ID_ATTRIBUTE] as String
        return services.mapNotNull {
            it.webSocketsRequests[routeId]
        }.firstOrNull()
    }

    private fun getSessionId(session: WebSocketSession): String {
        val routeId = session.attributes[KV_ROUTE_ID_ATTRIBUTE] as String
        return session.id + "###" + routeId
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        getHandler(session)?.let { handler ->
            val requestChannel = Channel<String>()
            val responseChannel = Channel<String>()
            GlobalScope.launch {
                coroutineScope {
                    launch(Dispatchers.IO) {
                        for (text in responseChannel) {
                            session.sendMessage(TextMessage(text))
                        }
                        session.close()
                    }
                    launch {
                        handler.invoke(session, applicationContext, requestChannel, responseChannel)
                        if (!responseChannel.isClosedForReceive) responseChannel.close()
                    }
                    sessions[getSessionId(session)] = responseChannel to requestChannel
                }
            }
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        getHandler(session)?.let {
            sessions[getSessionId(session)]?.let { (_, requestChannel) ->
                GlobalScope.launch {
                    requestChannel.send(message.payload)
                }
            }
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        getHandler(session)?.let {
            sessions[getSessionId(session)]?.let { (responseChannel, requestChannel) ->
                GlobalScope.launch {
                    responseChannel.close()
                    requestChannel.close()
                }
                sessions.remove(getSessionId(session))
            }
        }
    }
}

open class DummyWebSocketSession : WebSocketSession {
    override fun getBinaryMessageSizeLimit(): Int {
        return 0
    }

    override fun sendMessage(message: WebSocketMessage<*>) {
    }

    override fun getAcceptedProtocol(): String? {
        return null
    }

    override fun getTextMessageSizeLimit(): Int {
        return 0
    }

    override fun getLocalAddress(): InetSocketAddress? {
        return null
    }

    override fun getId(): String {
        return ""
    }

    override fun getExtensions(): MutableList<WebSocketExtension> {
        return mutableListOf()
    }

    override fun getUri(): URI? {
        return null
    }

    override fun setBinaryMessageSizeLimit(messageSizeLimit: Int) {
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    override fun getHandshakeHeaders(): HttpHeaders {
        return HttpHeaders.EMPTY
    }

    override fun isOpen(): Boolean {
        return false
    }

    override fun getPrincipal(): Principal? {
        return null
    }

    override fun close() {
    }

    override fun close(status: CloseStatus) {
    }

    override fun setTextMessageSizeLimit(messageSizeLimit: Int) {
    }

    override fun getRemoteAddress(): InetSocketAddress? {
        return null
    }

}
