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

import io.micronaut.context.ApplicationContext
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

/**
 * Micronaut WebSocket handler.
 */
@ServerWebSocket("/kvws/{path}")
class KVServerWebSocket {

    private val sessions = ConcurrentHashMap<String, Pair<Channel<String>, Channel<String>>>()

    @Inject
    lateinit var kvManagers: KVManagers

    @Inject
    lateinit var applicationContext: ApplicationContext

    @PostConstruct
    fun init() {
        kvManagers.services.forEach {
            it.deSerializer = kotlinxObjectDeSerializer(kvManagers.serializersModules)
        }
    }

    @OnOpen
    suspend fun onOpen(path: String, session: WebSocketSession) {
        kvManagers.services.mapNotNull {
            it.webSocketRequests["/kvws/$path"]
        }.firstOrNull()?.let { handler ->
            val requestChannel = Channel<String>()
            val responseChannel = Channel<String>()
            coroutineScope {
                launch {
                    for (text in responseChannel) {
                        session.sendAsync(text).await()
                    }
                    session.close()
                }
                launch {
                    handler.invoke(
                        session,
                        WebSocketSessionHolder.threadLocalSession,
                        applicationContext,
                        requestChannel,
                        responseChannel
                    )
                }
                sessions[session.id + "###" + path] = responseChannel to requestChannel
            }
        }
    }

    @OnMessage
    suspend fun onMessage(
        path: String,
        message: String,
        session: WebSocketSession
    ) {
        sessions[session.id + "###" + path]?.let { (_, requestChannel) ->
            requestChannel.send(message)
        }
    }

    @OnClose
    suspend fun onClose(
        path: String,
        session: WebSocketSession
    ) {
        sessions[session.id + "###" + path]?.let { (responseChannel, requestChannel) ->
            responseChannel.close()
            requestChannel.close()
            sessions.remove(session.id + "###" + path)
        }
    }
}
