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

import kotlinx.coroutines.experimental.Deferred
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.BooleanSerializer
import kotlinx.serialization.internal.CharSerializer
import kotlinx.serialization.internal.DoubleSerializer
import kotlinx.serialization.internal.LongSerializer
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import kotlin.js.Promise
import kotlin.js.js
import kotlin.reflect.KClass
import kotlin.js.JSON as NativeJSON

internal class NonStandardTypeException(type: String) : Exception("Non standard type: $type!")

/**
 * Client side agent for JSON-RPC remote calls.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING", "LargeClass", "TooManyFunctions")
open class RemoteAgent<out T>(val serviceManager: ServiceManager<T>) {

    val callAgent = CallAgent()

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified RET : Any, T> call(noinline function: T.(Request?) -> Deferred<RET>): Promise<RET> {
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, method = method).then {
            try {
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer(), it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified RET : Any, T> call(noinline function: T.(Request?) -> Deferred<List<RET>>): Promise<List<RET>> {
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, method = method).then {
            try {
                deserializeLists<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer().list, it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: T.(PAR, Request?) -> Deferred<RET>,
        p: PAR, serializer: KSerializer<PAR>? = null
    ): Promise<RET> {
        val data = serialize(p, serializer)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, (RET::class as KClass<Any>).js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer(), it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: T.(PAR, Request?) -> Deferred<List<RET>>,
        p: PAR, serializer: KSerializer<PAR>? = null
    ): Promise<List<RET>> {
        val data = serialize(p, serializer)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data), method).then {
            try {
                deserializeLists<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer().list, it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<RET>,
        p1: PAR1, p2: PAR2, serializer1: KSerializer<PAR1>? = null, serializer2: KSerializer<PAR2>? = null
    ): Promise<RET> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2), method).then {
            try {
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer(), it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<List<RET>>,
        p1: PAR1, p2: PAR2, serializer1: KSerializer<PAR1>? = null, serializer2: KSerializer<PAR2>? = null
    ): Promise<List<RET>> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2), method).then {
            try {
                deserializeLists<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer().list, it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<RET>,
        p1: PAR1, p2: PAR2, p3: PAR3, serializer1: KSerializer<PAR1>? = null, serializer2: KSerializer<PAR2>? = null,
        serializer3: KSerializer<PAR3>? = null
    ): Promise<RET> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val data3 = serialize(p3, serializer3)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method).then {
            try {
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer(), it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<List<RET>>,
        p1: PAR1, p2: PAR2, p3: PAR3, serializer1: KSerializer<PAR1>? = null, serializer2: KSerializer<PAR2>? = null,
        serializer3: KSerializer<PAR3>? = null
    ): Promise<List<RET>> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val data3 = serialize(p3, serializer3)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method).then {
            try {
                deserializeLists<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer().list, it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<RET>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        serializer1: KSerializer<PAR1>? = null,
        serializer2: KSerializer<PAR2>? = null,
        serializer3: KSerializer<PAR3>? = null,
        serializer4: KSerializer<PAR4>? = null
    ): Promise<RET> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val data3 = serialize(p3, serializer3)
        val data4 = serialize(p4, serializer4)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method).then {
            try {
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer(), it)
            }
        }
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<List<RET>>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        serializer1: KSerializer<PAR1>? = null,
        serializer2: KSerializer<PAR2>? = null,
        serializer3: KSerializer<PAR3>? = null,
        serializer4: KSerializer<PAR4>? = null
    ): Promise<List<RET>> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val data3 = serialize(p3, serializer3)
        val data4 = serialize(p4, serializer4)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method).then {
            try {
                deserializeLists<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer().list, it)
            }
        }
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
        p5: PAR5,
        serializer1: KSerializer<PAR1>? = null,
        serializer2: KSerializer<PAR2>? = null,
        serializer3: KSerializer<PAR3>? = null,
        serializer4: KSerializer<PAR4>? = null,
        serializer5: KSerializer<PAR5>? = null
    ): Promise<RET> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val data3 = serialize(p3, serializer3)
        val data4 = serialize(p4, serializer4)
        val data5 = serialize(p5, serializer5)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method).then {
            try {
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer(), it)
            }
        }
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
        p5: PAR5,
        serializer1: KSerializer<PAR1>? = null,
        serializer2: KSerializer<PAR2>? = null,
        serializer3: KSerializer<PAR3>? = null,
        serializer4: KSerializer<PAR4>? = null,
        serializer5: KSerializer<PAR5>? = null
    ): Promise<List<RET>> {
        val data1 = serialize(p1, serializer1)
        val data2 = serialize(p2, serializer2)
        val data3 = serialize(p3, serializer3)
        val data4 = serialize(p4, serializer4)
        val data5 = serialize(p5, serializer5)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method).then {
            try {
                deserializeLists<RET>(it, RET::class.js.name)
            } catch (t: NonStandardTypeException) {
                JSON.parse(RET::class.serializer().list, it)
            }
        }
    }


    /**
     * @suppress
     * Internal function
     */
    @Suppress("TooGenericExceptionCaught", "NestedBlockDepth")
    inline fun <reified PAR> serialize(value: PAR, serializer: KSerializer<PAR>?): String? {
        return value?.let {
            if (serializer != null) {
                JSON.stringify(serializer, it)
            } else {
                if (it is Enum<*>) {
                    "\"$it\""
                } else {
                    try {
                        @Suppress("UNCHECKED_CAST")
                        JSON.stringify((PAR::class as KClass<Any>).serializer(), it as Any)
                    } catch (e: Throwable) {
                        it.toString()
                    }
                }
            }
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("UNCHECKED_CAST")
    fun <RET> deserialize(value: String, type: String): RET {
        return when (type) {
            "String" -> JSON.parse(StringSerializer, value) as RET
            "Number" -> JSON.parse(DoubleSerializer, value) as RET
            "Long" -> JSON.parse(LongSerializer, value) as RET
            "Boolean" -> JSON.parse(BooleanSerializer, value) as RET
            "Char" -> JSON.parse(CharSerializer, value) as RET
            else -> throw NonStandardTypeException(type)
        }
    }

    /**
     * @suppress
     * Internal function
     */
    @Suppress("UNCHECKED_CAST")
    fun <RET> deserializeLists(value: String, type: String): List<RET> {
        return when (type) {
            "String" -> JSON.parse(ArrayListSerializer(StringSerializer), value) as List<RET>
            "Number" -> JSON.parse(ArrayListSerializer(DoubleSerializer), value) as List<RET>
            "Long" -> JSON.parse(ArrayListSerializer(LongSerializer), value) as List<RET>
            "Boolean" -> JSON.parse(ArrayListSerializer(BooleanSerializer), value) as List<RET>
            "Char" -> JSON.parse(ArrayListSerializer(CharSerializer), value) as List<RET>
            else -> throw NonStandardTypeException(type)
        }
    }
}
