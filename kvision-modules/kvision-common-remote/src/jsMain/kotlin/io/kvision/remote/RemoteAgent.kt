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

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.serializer
import io.kvision.types.JsonDateSerializer
import io.kvision.types.toStringInternal
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.js.Date
import kotlin.reflect.KClass

class NotStandardTypeException(type: String) : Exception("Not a standard type: $type!")

class NotEnumTypeException : Exception("Not the Enum type!")

/**
 * Interface for client side agent for JSON-RPC remote calls.
 */
@OptIn(ExperimentalSerializationApi::class)
open class RemoteAgent {

    /**
     * @suppress
     * Internal function
     */
    inline fun <reified PAR> serialize(value: PAR): String? {
        return try {
            Serialization.plain.encodeToString(value)
        } catch (e: Throwable) {
            Serialization.plain.encodeToString(String.serializer(), value.toString())
        }
    }

    /**
     * @suppress
     * Internal function
     * Workaround for KT-41282
     */
    @InternalSerializationApi
    inline fun <reified PAR : Any> serializeNotNull(value: PAR): String {
        return try {
            Serialization.plain.encodeToString(value)
        } catch (e: Throwable) {
            try {
                Serialization.plain.encodeToString(PAR::class.serializer(), value)
            } catch (e: Throwable) {
                val maybeDate = (value as? Date)?.toStringInternal() ?: value.toString()
                Serialization.plain.encodeToString(String.serializer(), maybeDate)
            }
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("UNCHECKED_CAST", "ComplexMethod")
    fun <RET> deserialize(value: String, jsType: String): RET {
        return when (jsType) {
            "String" -> Serialization.plain.decodeFromString(String.serializer(), value) as RET
            "Number" -> Serialization.plain.decodeFromString(Double.serializer(), value) as RET
            "Long" -> Serialization.plain.decodeFromString(Long.serializer(), value) as RET
            "Boolean" -> Serialization.plain.decodeFromString(Boolean.serializer(), value) as RET
            "BoxedChar" -> Serialization.plain.decodeFromString(Char.serializer(), value) as RET
            "Short" -> Serialization.plain.decodeFromString(Short.serializer(), value) as RET
            "Date" -> Serialization.plain.decodeFromString(JsonDateSerializer, value) as RET
            "Byte" -> Serialization.plain.decodeFromString(Byte.serializer(), value) as RET
            else -> throw NotStandardTypeException(jsType)
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("UNCHECKED_CAST", "ComplexMethod")
    fun <RET> deserializeList(value: String, jsType: String): List<RET> {
        return when (jsType) {
            "String" -> Serialization.plain.decodeFromString(ListSerializer(String.serializer()), value) as List<RET>
            "Number" -> Serialization.plain.decodeFromString(ListSerializer(Double.serializer()), value) as List<RET>
            "Long" -> Serialization.plain.decodeFromString(ListSerializer(Long.serializer()), value) as List<RET>
            "Boolean" -> Serialization.plain.decodeFromString(ListSerializer(Boolean.serializer()), value) as List<RET>
            "BoxedChar" -> Serialization.plain.decodeFromString(ListSerializer(Char.serializer()), value) as List<RET>
            "Short" -> Serialization.plain.decodeFromString(ListSerializer(Short.serializer()), value) as List<RET>
            "Date" -> Serialization.plain.decodeFromString(ListSerializer(JsonDateSerializer), value) as List<RET>
            "Byte" -> Serialization.plain.decodeFromString(ListSerializer(Byte.serializer()), value) as List<RET>
            else -> throw NotStandardTypeException(jsType)
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("TooGenericExceptionCaught", "ThrowsCount")
    fun tryDeserializeEnum(kClass: KClass<Any>, value: String): Any {
        return try {
            if (kClass.asDynamic().jClass.`$metadata$`.interfaces[0].name == "Enum") {
                findEnumValue(kClass, Serialization.plain.decodeFromString(String.serializer(), value))
                    ?: throw NotEnumTypeException()
            } else {
                throw NotEnumTypeException()
            }
        } catch (e: Throwable) {
            throw NotEnumTypeException()
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("TooGenericExceptionCaught", "ThrowsCount")
    fun tryDeserializeEnumList(kClass: KClass<Any>, value: String): List<Any> {
        return try {
            if (kClass.asDynamic().jClass.`$metadata$`.interfaces[0].name == "Enum") {
                Serialization.plain.decodeFromString(ListSerializer(String.serializer()), value).map {
                    findEnumValue(kClass, Serialization.plain.decodeFromString(String.serializer(), it))
                        ?: throw NotEnumTypeException()
                }
            } else {
                throw NotEnumTypeException()
            }
        } catch (e: Throwable) {
            throw NotEnumTypeException()
        }
    }

    fun findEnumValue(kClass: KClass<Any>, value: String): Any? {
        return (kClass.asDynamic().jClass.values() as Array<Any>).find {
            it.asDynamic().name == value
        }
    }
}
