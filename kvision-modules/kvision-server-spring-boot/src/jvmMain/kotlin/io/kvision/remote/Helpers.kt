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

import org.reactivestreams.Publisher
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.HttpCookie
import org.springframework.http.codec.HttpMessageReader
import org.springframework.http.codec.multipart.Part
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyExtractor
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.socket.CloseStatus
import org.springframework.web.reactive.socket.HandshakeInfo
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.InetSocketAddress
import java.net.URI
import java.security.Principal
import java.util.*
import java.util.function.Function

/**
 * Empty implementation of the ServerRequest interface
 */
internal class KVServerRequest : ServerRequest {
    override fun <T : Any> body(extractor: BodyExtractor<T, in ServerHttpRequest>): T {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any> body(extractor: BodyExtractor<T, in ServerHttpRequest>, hints: MutableMap<String, Any>): T {
        throw IllegalStateException("Empty implementation")
    }

    override fun remoteAddress(): Optional<InetSocketAddress> {
        throw IllegalStateException("Empty implementation")
    }

    override fun attributes(): MutableMap<String, Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun session(): Mono<WebSession> {
        throw IllegalStateException("Empty implementation")
    }

    override fun principal(): Mono<out Principal> {
        throw IllegalStateException("Empty implementation")
    }

    override fun multipartData(): Mono<MultiValueMap<String, Part>> {
        throw IllegalStateException("Empty implementation")
    }

    override fun messageReaders(): MutableList<HttpMessageReader<*>> {
        throw IllegalStateException("Empty implementation")
    }

    override fun uri(): URI {
        throw IllegalStateException("Empty implementation")
    }

    override fun exchange(): ServerWebExchange {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any?> bodyToFlux(elementClass: Class<out T>): Flux<T> {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any?> bodyToFlux(typeReference: ParameterizedTypeReference<T>): Flux<T> {
        throw IllegalStateException("Empty implementation")
    }

    override fun formData(): Mono<MultiValueMap<String, String>> {
        throw IllegalStateException("Empty implementation")
    }

    override fun queryParams(): MultiValueMap<String, String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun pathVariables(): MutableMap<String, String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun localAddress(): Optional<InetSocketAddress> {
        throw IllegalStateException("Empty implementation")
    }

    override fun uriBuilder(): UriBuilder {
        throw IllegalStateException("Empty implementation")
    }

    override fun methodName(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any?> bodyToMono(elementClass: Class<out T>): Mono<T> {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : Any?> bodyToMono(typeReference: ParameterizedTypeReference<T>): Mono<T> {
        throw IllegalStateException("Empty implementation")
    }

    override fun cookies(): MultiValueMap<String, HttpCookie> {
        throw IllegalStateException("Empty implementation")
    }

    override fun headers(): ServerRequest.Headers {
        throw IllegalStateException("Empty implementation")
    }

}

/**
 * Empty implementation of the WebSocketSession interface
 */
internal class KVWebSocketSession : WebSocketSession {
    override fun getId(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun binaryMessage(payloadFactory: Function<DataBufferFactory, DataBuffer>): WebSocketMessage {
        throw IllegalStateException("Empty implementation")
    }

    override fun pingMessage(payloadFactory: Function<DataBufferFactory, DataBuffer>): WebSocketMessage {
        throw IllegalStateException("Empty implementation")
    }

    override fun bufferFactory(): DataBufferFactory {
        throw IllegalStateException("Empty implementation")
    }

    override fun getAttributes(): MutableMap<String, Any> {
        throw IllegalStateException("Empty implementation")
    }

    override fun receive(): Flux<WebSocketMessage> {
        throw IllegalStateException("Empty implementation")
    }

    override fun pongMessage(payloadFactory: Function<DataBufferFactory, DataBuffer>): WebSocketMessage {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHandshakeInfo(): HandshakeInfo {
        throw IllegalStateException("Empty implementation")
    }

    override fun send(messages: Publisher<WebSocketMessage>): Mono<Void> {
        throw IllegalStateException("Empty implementation")
    }

    override fun isOpen(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun close(status: CloseStatus): Mono<Void> {
        throw IllegalStateException("Empty implementation")
    }

    override fun closeStatus(): Mono<CloseStatus> {
        throw IllegalStateException("Empty implementation")
    }

    override fun textMessage(payload: String): WebSocketMessage {
        throw IllegalStateException("Empty implementation")
    }

}
