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

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.asDeferred
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.BooleanSerializer
import kotlinx.serialization.internal.ByteSerializer
import kotlinx.serialization.internal.CharSerializer
import kotlinx.serialization.internal.DoubleSerializer
import kotlinx.serialization.internal.FloatSerializer
import kotlinx.serialization.internal.IntSerializer
import kotlinx.serialization.internal.LongSerializer
import kotlinx.serialization.internal.ShortSerializer
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import pl.treksoft.kvision.types.DateSerializer
import pl.treksoft.kvision.types.toStringF
import pl.treksoft.kvision.utils.JSON
import kotlin.js.Date
import kotlin.js.asDynamic
import kotlin.js.js
import kotlin.reflect.KClass
import kotlin.js.JSON as NativeJSON

internal class NotStandardTypeException(type: String) : Exception("Not a standard type: $type!")

internal class NotEnumTypeException : Exception("Not the Enum type!")

/**
 * Client side agent for JSON-RPC remote calls.
 */
@Suppress("LargeClass", "TooManyFunctions")
open class RemoteAgent<out T>(val serviceManager: ServiceManager<T>) {

    val callAgent = CallAgent()

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified RET : Any, T> call(noinline function: T.(Request?) -> Deferred<RET>): Deferred<RET> {
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, method = method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified RET : Any, T> call(
        noinline function: T.(Request?) -> Deferred<List<RET>>
    ): Deferred<List<RET>> {
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, method = method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: T.(PAR, Request?) -> Deferred<RET>, p: PAR
    ): Deferred<RET> {
        val data = serialize(p)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: T.(PAR, Request?) -> Deferred<List<RET>>, p: PAR
    ): Deferred<List<RET>> {
        val data = serialize(p)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<RET>, p1: PAR1, p2: PAR2
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<List<RET>>, p1: PAR1, p2: PAR2
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<RET>, p1: PAR1, p2: PAR2, p3: PAR3
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<List<RET>>, p1: PAR1, p2: PAR2, p3: PAR3
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<RET>, p1: PAR1, p2: PAR2, p3: PAR3, p4: PAR4
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<List<RET>>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5,
            reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<RET>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5,
            reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<List<RET>>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }


    /**
     * @suppress
     * Internal function
     */
    inline fun <reified PAR> serialize(value: PAR): String? {
        return value?.let {
            @Suppress("UNCHECKED_CAST")
            trySerialize((PAR::class as KClass<Any>), it as Any)
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("ComplexMethod", "TooGenericExceptionCaught", "NestedBlockDepth")
    fun trySerialize(kClass: KClass<Any>, value: Any): String {
        return if (value is List<*>) {
            if (value.size > 0) {
                when {
                    value[0] is String ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(StringSerializer.list as KSerializer<Any>, value)
                    value[0] is Date ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(DateSerializer.list as KSerializer<Any>, value)
                    value[0] is Int ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(IntSerializer.list as KSerializer<Any>, value)
                    value[0] is Long ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(LongSerializer.list as KSerializer<Any>, value)
                    value[0] is Boolean ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(BooleanSerializer.list as KSerializer<Any>, value)
                    value[0] is Float ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(FloatSerializer.list as KSerializer<Any>, value)
                    value[0] is Double ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(DoubleSerializer.list as KSerializer<Any>, value)
                    value[0] is Char ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(CharSerializer.list as KSerializer<Any>, value)
                    value[0] is Short ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(ShortSerializer.list as KSerializer<Any>, value)
                    value[0] is Byte ->
                        @Suppress("UNCHECKED_CAST")
                        JSON.plain.stringify(ByteSerializer.list as KSerializer<Any>, value)
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
                                JSON.plain.stringify(StringSerializer.list as KSerializer<Any>, value)
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
                is Date -> "\"${value.toStringF()}\""
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
            "String" -> JSON.plain.parse(StringSerializer, value) as RET
            "Number" -> JSON.plain.parse(DoubleSerializer, value) as RET
            "Long" -> JSON.plain.parse(LongSerializer, value) as RET
            "Boolean" -> JSON.plain.parse(BooleanSerializer, value) as RET
            "BoxedChar" -> JSON.plain.parse(CharSerializer, value) as RET
            "Short" -> JSON.plain.parse(ShortSerializer, value) as RET
            "Date" -> JSON.plain.parse(DateSerializer, value) as RET
            "Byte" -> JSON.plain.parse(ByteSerializer, value) as RET
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
            "String" -> JSON.plain.parse(StringSerializer.list, value) as List<RET>
            "Number" -> JSON.plain.parse(DoubleSerializer.list, value) as List<RET>
            "Long" -> JSON.plain.parse(LongSerializer.list, value) as List<RET>
            "Boolean" -> JSON.plain.parse(BooleanSerializer.list, value) as List<RET>
            "BoxedChar" -> JSON.plain.parse(CharSerializer.list, value) as List<RET>
            "Short" -> JSON.plain.parse(ShortSerializer.list, value) as List<RET>
            "Date" -> JSON.plain.parse(DateSerializer.list, value) as List<RET>
            "Byte" -> JSON.plain.parse(ByteSerializer.list, value) as List<RET>
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
                findEnumValue(kClass, JSON.plain.parse(StringSerializer, value)) ?: throw NotEnumTypeException()
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
                JSON.plain.parse(StringSerializer.list, value).map {
                    findEnumValue(kClass, JSON.plain.parse(StringSerializer, it)) ?: throw NotEnumTypeException()
                }
            } else {
                throw NotEnumTypeException()
            }
        } catch (e: Throwable) {
            throw NotEnumTypeException()
        }
    }

    private fun findEnumValue(kClass: KClass<Any>, value: String): Any? {
        return (kClass.asDynamic().jClass.values() as Array<Any>).find {
            it.asDynamic().name == value
        }
    }
}
