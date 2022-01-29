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

import io.kvision.types.JsonDateSerializer
import io.kvision.types.toStringInternal
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotlin.js.Date
import kotlin.reflect.KClass

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
class SerializationAgentLegacy(serializersModules: List<SerializersModule>? = null) {

    val json = RemoteSerialization.getJson(serializersModules)

    class NotStandardTypeException(type: String) : Exception("Not a standard type: $type!")

    class NotEnumTypeException : Exception("Not the Enum type!")

    inline fun <reified PAR> serialize(value: PAR): String? {
        return try {
            json.encodeToString(value)
        } catch (e: Throwable) {
            json.encodeToString(String.serializer(), value.toString())
        }
    }

    /**
     * @suppress
     * Workaround for KT-41282
     */
    inline fun <reified PAR : Any> serializeNotNull(value: PAR): String {
        return try {
            json.encodeToString(value)
        } catch (e: Throwable) {
            try {
                json.encodeToString(PAR::class.serializer(), value)
            } catch (e: Throwable) {
                val maybeDate = (value as? Date)?.toStringInternal() ?: value.toString()
                json.encodeToString(String.serializer(), maybeDate)
            }
        }
    }

    inline fun <reified PAR : Any> deserialize(value: String): PAR {
        return try {
            @Suppress("UNCHECKED_CAST")
            deserialize(value, PAR::class.js.name)
        } catch (t: NotStandardTypeException) {
            try {
                @Suppress("UNCHECKED_CAST")
                tryDeserializeEnum(PAR::class as KClass<Any>, value) as PAR
            } catch (t: NotEnumTypeException) {
                json.decodeFromString(PAR::class.serializer(), value)
            }
        }
    }

    inline fun <reified PAR : Any> deserializeList(value: String): List<PAR> {
        return try {
            deserializeList(value, PAR::class.js.name)
        } catch (t: NotStandardTypeException) {
            try {
                @Suppress("UNCHECKED_CAST")
                tryDeserializeEnumList(PAR::class as KClass<Any>, value) as List<PAR>
            } catch (t: NotEnumTypeException) {
                json.decodeFromString(ListSerializer(PAR::class.serializer()), value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST", "ComplexMethod")
    fun <RET> deserialize(value: String, jsType: String): RET {
        return when (jsType) {
            "String" -> json.decodeFromString(String.serializer(), value) as RET
            "Number" -> json.decodeFromString(Double.serializer(), value) as RET
            "Long" -> json.decodeFromString(Long.serializer(), value) as RET
            "Boolean" -> json.decodeFromString(Boolean.serializer(), value) as RET
            "BoxedChar" -> json.decodeFromString(Char.serializer(), value) as RET
            "Short" -> json.decodeFromString(Short.serializer(), value) as RET
            "Date" -> json.decodeFromString(JsonDateSerializer, value) as RET
            "Byte" -> json.decodeFromString(Byte.serializer(), value) as RET
            else -> throw NotStandardTypeException(jsType)
        }
    }

    @Suppress("UNCHECKED_CAST", "ComplexMethod")
    fun <RET> deserializeList(value: String, jsType: String): List<RET> {
        return when (jsType) {
            "String" -> json.decodeFromString(ListSerializer(String.serializer()), value) as List<RET>
            "Number" -> json.decodeFromString(ListSerializer(Double.serializer()), value) as List<RET>
            "Long" -> json.decodeFromString(ListSerializer(Long.serializer()), value) as List<RET>
            "Boolean" -> json.decodeFromString(ListSerializer(Boolean.serializer()), value) as List<RET>
            "BoxedChar" -> json.decodeFromString(ListSerializer(Char.serializer()), value) as List<RET>
            "Short" -> json.decodeFromString(ListSerializer(Short.serializer()), value) as List<RET>
            "Date" -> json.decodeFromString(ListSerializer(JsonDateSerializer), value) as List<RET>
            "Byte" -> json.decodeFromString(ListSerializer(Byte.serializer()), value) as List<RET>
            else -> throw NotStandardTypeException(jsType)
        }
    }

    @Suppress("TooGenericExceptionCaught", "ThrowsCount")
    fun tryDeserializeEnum(kClass: KClass<Any>, value: String): Any {
        return try {
            if (kClass.asDynamic().jClass.`$metadata$`.interfaces[0].name == "Enum") {
                findEnumValue(kClass, json.decodeFromString(String.serializer(), value))
                    ?: throw NotEnumTypeException()
            } else {
                throw NotEnumTypeException()
            }
        } catch (e: Throwable) {
            throw NotEnumTypeException()
        }
    }

    @Suppress("TooGenericExceptionCaught", "ThrowsCount")
    fun tryDeserializeEnumList(kClass: KClass<Any>, value: String): List<Any> {
        return try {
            if (kClass.asDynamic().jClass.`$metadata$`.interfaces[0].name == "Enum") {
                json.decodeFromString(ListSerializer(String.serializer()), value).map {
                    findEnumValue(kClass, json.decodeFromString(String.serializer(), it))
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
