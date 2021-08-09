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

package io.kvision.utils

import kotlinx.browser.window
import kotlin.reflect.KClass

/**
 * JavaScript Object type
 */
external class Object

/**
 * JavaScript delete operator
 */
external fun delete(p: dynamic): Boolean

/**
 * A helper function for JavaScript delete operator
 */
fun delete(thing: dynamic, key: String) {
    delete(thing[key])
}

/**
 * Helper function for creating JavaScript objects.
 */
inline fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

/**
 * Helper function for creating JavaScript objects with given type.
 */
inline fun <T> obj(init: T.() -> Unit): T {
    return (js("{}") as T).apply(init)
}

/**
 * Helper function for creating JavaScript objects from dynamic constructors.
 */
@Suppress("UNUSED_VARIABLE")
fun <T> Any?.createInstance(vararg args: dynamic): T {
    val jsClassConstructor = this
    val argsArray = (listOf<dynamic>(null) + args).toTypedArray()
    return js("new (Function.prototype.bind.apply(jsClassConstructor, argsArray))").unsafeCast<T>()
}

internal data class LegacyTest(val test: Boolean = true)

/**
 * A helper property to test whether current compiler is running in legacy mode.
 */
val isLegacyBackend by lazy {
    LegacyTest().asDynamic()["test"] == true
}

/**
 * Helper function to enumerate properties of a data class for IR backend.
 */
@Suppress("UnsafeCastFromDynamic")
fun getAllPropertyNamesForIR(obj: Any): List<String> {
    val prototype = js("Object").getPrototypeOf(obj)
    val prototypeProps: Array<String> = js("Object").getOwnPropertyNames(prototype)
    return prototypeProps.filter { it != "constructor" }.filterNot { prototype.propertyIsEnumerable(it) }.toList()
}

/**
 * Helper extension function to convert a data class to a plain JS object.
 */
fun toPlainObj(data: Any): dynamic {
    return if (isLegacyBackend) {
        data
    } else {
        val properties = getAllPropertyNamesForIR(data)
        val ret = js("{}")
        properties.forEach {
            ret[it] = data.asDynamic()[it]
        }
        ret
    }
}

/**
 * Helper function to convert a plain JS object to a data class.
 */
fun <T : Any> toKotlinObj(data: dynamic, kClass: KClass<T>): T {
    val prototype = js("Object").getPrototypeOf(data)
    return if (isLegacyBackend && prototype != null && prototype.constructor != undefined && prototype.constructor.name == kClass.simpleName) {
        data.unsafeCast<T>()
    } else {
        val newT = kClass.js.createInstance<T>()
        for (key in js("Object").keys(data)) {
            newT.asDynamic()[key] = data[key]
        }
        newT
    }
}

/**
 * Utility function to detect Internet Explorer 11.
 * @return true if the current browser is IE11
 */
fun isIE11(): Boolean = window.navigator.userAgent.matches("Trident\\/7\\.")
