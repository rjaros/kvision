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

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@Serializable
private data class ExceptionJson(
    val message: String? = null,
    val type: String? = null,
    val isServiceException: Boolean = false,
    val serialized: String? = null,
)

/**
 * A custom serializer for the [Result] class.
 */
class ResultSerializer<T>(private val tSerializer: KSerializer<T>) :
    KSerializer<Result<T>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("kotlin.Result", tSerializer.descriptor) {
        element("value", tSerializer.descriptor, isOptional = true)
        element("error", buildClassSerialDescriptor("io.kvision.remote.ExceptionJson"), isOptional = true)
    }

    override fun deserialize(decoder: Decoder): Result<T> {
        return decoder.decodeStructure(descriptor) {
            var r: Result<T>? = null
            while (true) {
                r = when (val index = decodeElementIndex(descriptor)) {
                    0 -> {
                        val v = decodeSerializableElement(descriptor, index, tSerializer)
                        Result.success(v)
                    }

                    1 -> {
                        val exceptionJson = decodeSerializableElement(descriptor, index, ExceptionJson.serializer())
                        if (exceptionJson.isServiceException) {
                            Result.failure(ServiceException(exceptionJson.message ?: "Service exception"))
                        } else if (exceptionJson.serialized != null) {
                            val abstractServiceException = RemoteSerialization.getJson()
                                .decodeFromString<AbstractServiceException>(exceptionJson.serialized)
                            Result.failure(abstractServiceException)
                        } else {
                            val exception = when (exceptionJson.type) {
                                "IllegalArgumentException" -> IllegalArgumentException(exceptionJson.message)
                                "IllegalStateException" -> IllegalStateException(exceptionJson.message)
                                "NullPointerException" -> NullPointerException(exceptionJson.message)
                                "UnsupportedOperationException" -> UnsupportedOperationException(exceptionJson.message)
                                "IndexOutOfBoundsException" -> IndexOutOfBoundsException(exceptionJson.message)
                                "NoSuchElementException" -> NoSuchElementException(exceptionJson.message)
                                "ArithmeticException" -> ArithmeticException(exceptionJson.message)
                                "NumberFormatException" -> NumberFormatException(exceptionJson.message)
                                "ClassCastException" -> ClassCastException(exceptionJson.message)
                                "RuntimeException" -> RuntimeException(exceptionJson.message)
                                else -> Exception(exceptionJson.message)
                            }
                            Result.failure(exception)
                        }
                    }

                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            require(r != null)
            r
        }
    }

    override fun serialize(encoder: Encoder, value: Result<T>) {
        encoder.encodeStructure(descriptor) {
            value.fold({
                encodeSerializableElement(descriptor, 0, tSerializer, it)
            }) {
                when (it) {
                    is ServiceException -> encodeSerializableElement(
                        descriptor,
                        1,
                        ExceptionJson.serializer(),
                        ExceptionJson(it.message, isServiceException = true)
                    )

                    is AbstractServiceException -> {
                        val serialized = RemoteSerialization.getJson().encodeToString(it)
                        encodeSerializableElement(
                            descriptor,
                            1,
                            ExceptionJson.serializer(),
                            ExceptionJson(serialized = serialized)
                        )
                    }

                    else -> {
                        val type = when (it) {
                            is IllegalArgumentException -> "IllegalArgumentException"
                            is IllegalStateException -> "IllegalStateException"
                            is IndexOutOfBoundsException -> "IndexOutOfBoundsException"
                            is ConcurrentModificationException -> "ConcurrentModificationException"
                            is UnsupportedOperationException -> "UnsupportedOperationException"
                            is NumberFormatException -> "NumberFormatException"
                            is NullPointerException -> "NullPointerException"
                            is ClassCastException -> "ClassCastException"
                            is NoSuchElementException -> "NoSuchElementException"
                            is ArithmeticException -> "ArithmeticException"
                            is RuntimeException -> "RuntimeException"
                            else -> null
                        }
                        encodeSerializableElement(
                            descriptor,
                            1,
                            ExceptionJson.serializer(),
                            ExceptionJson(it.message, type)
                        )
                    }
                }
            }
        }
    }
}
