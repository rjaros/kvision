/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer

/**
 * Convenience function for cases were the raw channels work with strings. See the overloaded method for details.
 */
suspend fun <T, OBJECTS_IN, OBJECTS_OUT> handleWebsocketConnection(
    deSerializer: ObjectDeSerializer,
    rawIn: ReceiveChannel<String>,
    rawOut: SendChannel<String>,
    serializerIn: KSerializer<OBJECTS_IN>,
    serializerOut: KSerializer<OBJECTS_OUT>,
    service: T,
    function: suspend T.(ReceiveChannel<OBJECTS_IN>, SendChannel<OBJECTS_OUT>) -> Unit
) {
    handleWebsocketConnection(
        deSerializer = deSerializer,
        rawIn = rawIn,
        rawInToText = { it },
        rawOut = rawOut,
        rawOutFromText = { it },
        serializerIn = serializerIn,
        serializerOut = serializerOut,
        service = service,
        function = function
    )
}

/**
 * Reads JSON-RPC calls from [rawIn], deserializes them and forwards them in form of a channel to [function].
 * Additionally [function] also receives a [SendChannel], from which objects are read, serialized and sent to the client
 * in the form of JSON-RPC calls.
 *
 * @param deSerializer used to (de-)serialize objects
 * @param rawIn a channel that supplies serialized JSON-RPC calls
 * @param rawInToText a function to convert the objects supplied by [rawIn] to a String. The function may return null to
 *                    indicate that the message should be ignored
 * @param rawOut a channel for outgoing messages
 * @param rawOutFromText a function to convert the JSON-string to an object to be sent via [rawOut]
 * @param parsedInType the type of object that [function] receives through a channel
 * @param service the receiver to be used when calling [function]
 * @param function the function to delegate data processing to
 */
@OptIn(FlowPreview::class)
suspend fun <T, RAW_IN, RAW_OUT, OBJECTS_IN, OBJECTS_OUT> handleWebsocketConnection(
    deSerializer: ObjectDeSerializer,
    rawIn: ReceiveChannel<RAW_IN>,
    rawInToText: (RAW_IN) -> String?,
    rawOut: SendChannel<RAW_OUT>,
    rawOutFromText: (String) -> RAW_OUT,
    serializerIn: KSerializer<OBJECTS_IN>,
    serializerOut: KSerializer<OBJECTS_OUT>,
    service: T,
    function: suspend T.(ReceiveChannel<OBJECTS_IN>, SendChannel<OBJECTS_OUT>) -> Unit
) {
    coroutineScope {
        val parsedIn = rawIn
            .consumeAsFlow()
            .mapNotNull { rawInToText(it) }
            .mapNotNull { deSerializer.deserialize<JsonRpcRequest>(it).params.singleOrNull() }
            .map { deSerializer.deserialize(it, serializerIn) }
            .produceIn(this)

        val parsedOut = Channel<OBJECTS_OUT>()
        parsedOut
            .consumeAsFlow()
            .map { rawOutFromText(serializeMessage(it, deSerializer, serializerOut)) }
            .pipe(this, rawOut)

        launch {
            try {
                function(service, parsedIn, parsedOut)
            } finally {
                parsedOut.close() // this also closes rawOut
                rawIn.cancel()
            }
        }
    }
}

private suspend fun <T> Flow<T>.pipe(scope: CoroutineScope, channel: SendChannel<T>) {
    scope.launch {
        onCompletion { channel.close(it) }.collect { channel.send(it) }
    }
}

private fun <T> serializeMessage(message: T?, deSerializer: ObjectDeSerializer, serializer: KSerializer<T>) =
    deSerializer.serializeNonNull(
        JsonRpcResponse(
            id = 0,
            result = deSerializer.serializeNullableToString(message, serializer)
        )
    )
