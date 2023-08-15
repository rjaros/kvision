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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer

/**
 * Convenience function for cases were the raw channel works with strings. See the overloaded method for details.
 */
suspend fun <T, OBJECTS_OUT> handleSseConnection(
    deSerializer: ObjectDeSerializer,
    rawOut: SendChannel<String>,
    serializerOut: KSerializer<OBJECTS_OUT>,
    service: T,
    function: suspend T.(SendChannel<OBJECTS_OUT>) -> Unit
) {
    handleSseConnection(
        deSerializer = deSerializer,
        rawOut = rawOut,
        rawOutFromText = { it },
        serializerOut = serializerOut,
        service = service,
        function = function
    )
}

/**
 * [function] receives a [SendChannel], from which objects are read, serialized and sent to the client
 * in the form of JSON-RPC calls.
 *
 * @param deSerializer used to (de-)serialize objects
 * @param rawOut a channel for outgoing messages
 * @param rawOutFromText a function to convert the JSON-string to an object to be sent via [rawOut]
 * @param service the receiver to be used when calling [function]
 * @param function the function to delegate data processing to
 */
suspend fun <T, RAW_OUT, OBJECTS_OUT> handleSseConnection(
    deSerializer: ObjectDeSerializer,
    rawOut: SendChannel<RAW_OUT>,
    rawOutFromText: (String) -> RAW_OUT,
    serializerOut: KSerializer<OBJECTS_OUT>,
    service: T,
    function: suspend T.(SendChannel<OBJECTS_OUT>) -> Unit
) {
    coroutineScope {
        val parsedOut = Channel<OBJECTS_OUT>()
        parsedOut
            .consumeAsFlow()
            .map { rawOutFromText(serializeMessage(it, deSerializer, serializerOut)) }
            .pipe(this, rawOut)

        launch {
            try {
                function(service, parsedOut)
            } finally {
                parsedOut.close() // this also closes rawOut
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
private suspend fun <T> Flow<T>.pipe(scope: CoroutineScope, channel: SendChannel<T>) {
    scope.launch {
        onCompletion {
            if (!channel.isClosedForSend) channel.close(it)
        }.collect {
            if (!channel.isClosedForSend) {
                channel.send(it)
            } else {
                cancel("Channel was closed")
            }
        }
    }
}

private fun <T> serializeMessage(message: T?, deSerializer: ObjectDeSerializer, serializer: KSerializer<T>) =
    deSerializer.serializeNonNull(
        JsonRpcResponse(
            id = 0,
            result = deSerializer.serializeNullableToString(message, serializer)
        )
    )
