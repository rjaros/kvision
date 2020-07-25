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

import kotlinx.serialization.KSerializer
import kotlinx.serialization.UnsafeSerializationApi
import kotlinx.serialization.builtins.list
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.serializer
import pl.treksoft.kvision.types.JsonDateSerializer
import pl.treksoft.kvision.types.toStringInternal
import kotlin.js.Date
import kotlin.reflect.KClass

class NotStandardTypeException(type: String) : Exception("Not a standard type: $type!")

class NotEnumTypeException : Exception("Not the Enum type!")

/**
 * Interface for client side agent for JSON-RPC remote calls.
 */
interface RemoteAgent {

    /**
     * @suppress
     * Internal function
     */
    @OptIn(UnsafeSerializationApi::class)
    @Suppress("ComplexMethod", "TooGenericExceptionCaught", "NestedBlockDepth")
    fun trySerialize(kClass: KClass<Any>, value: Any): String {
        return if (value is List<*>) {
            if (value.size > 0) {
                when {
                    value[0] is String ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(String.serializer().list as KSerializer<Any>, value)
                    value[0] is Date ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(JsonDateSerializer.list as KSerializer<Any>, value)
                    value[0] is Int ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Int.serializer().list as KSerializer<Any>, value)
                    value[0] is Long ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Long.serializer().list as KSerializer<Any>, value)
                    value[0] is Boolean ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Boolean.serializer().list as KSerializer<Any>, value)
                    value[0] is Float ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Float.serializer().list as KSerializer<Any>, value)
                    value[0] is Double ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Double.serializer().list as KSerializer<Any>, value)
                    value[0] is Char ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Char.serializer().list as KSerializer<Any>, value)
                    value[0] is Short ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Short.serializer().list as KSerializer<Any>, value)
                    value[0] is Byte ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(Byte.serializer().list as KSerializer<Any>, value)
                    value[0] is Enum<*> -> "[" + value.joinToString(",") { "\"$it\"" } + "]"
                    else -> try {
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(kClass.serializer().list as KSerializer<Any>, value)
                    } catch (e: Throwable) {
                        try {
                            @Suppress("UNCHECKED_CAST")
                            JSON.plain.stringify(value[0]!!::class.serializer().list as KSerializer<Any>, value)
                        } catch (e: Throwable) {
                            try {
                                @Suppress("UNCHECKED_CAST")
                                JSON.plain.stringify(String.serializer().list as KSerializer<Any>, value)
                            } catch (e: Throwable) {
                                value.toString()
                            }
                        }
                    }
                }
            } else {
                "[]"
            }
        } else {
            when (value) {
                is Enum<*> -> "\"$value\""
                is String -> value
                is Char -> "\"$value\""
                is Date -> "\"${value.toStringInternal()}\""
                else -> try {
                    @Suppress("UNCHECKED_CAST")
                    JSON.plain.stringify(kClass.serializer(), value)
                } catch (e: Throwable) {
                    value.toString()
                }
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
            "String" -> JSON.plain.parse(String.serializer(), value) as RET
            "Number" -> JSON.plain.parse(Double.serializer(), value) as RET
            "Long" -> JSON.plain.parse(Long.serializer(), value) as RET
            "Boolean" -> JSON.plain.parse(Boolean.serializer(), value) as RET
            "BoxedChar" -> JSON.plain.parse(Char.serializer(), value) as RET
            "Short" -> JSON.plain.parse(Short.serializer(), value) as RET
            "Date" -> JSON.plain.parse(JsonDateSerializer, value) as RET
            "Byte" -> JSON.plain.parse(Byte.serializer(), value) as RET
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
            "String" -> JSON.plain.parse(String.serializer().list, value) as List<RET>
            "Number" -> JSON.plain.parse(Double.serializer().list, value) as List<RET>
            "Long" -> JSON.plain.parse(Long.serializer().list, value) as List<RET>
            "Boolean" -> JSON.plain.parse(Boolean.serializer().list, value) as List<RET>
            "BoxedChar" -> JSON.plain.parse(Char.serializer().list, value) as List<RET>
            "Short" -> JSON.plain.parse(Short.serializer().list, value) as List<RET>
            "Date" -> JSON.plain.parse(JsonDateSerializer.list, value) as List<RET>
            "Byte" -> JSON.plain.parse(Byte.serializer().list, value) as List<RET>
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
                findEnumValue(kClass, JSON.plain.parse(String.serializer(), value)) ?: throw NotEnumTypeException()
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
                JSON.plain.parse(String.serializer().list, value).map {
                    findEnumValue(kClass, JSON.plain.parse(String.serializer(), it)) ?: throw NotEnumTypeException()
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
