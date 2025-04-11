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

/**
 * Helper function to deeply merge two JS objects.
 */
@Suppress("UnsafeCastFromDynamic")
fun deepMerge(target: dynamic, source: dynamic): dynamic {
    fun isObject(obj: dynamic): Boolean {
        return obj != null && obj != undefined && jsTypeOf(obj) === "object"
    }
    if (!isObject(target) || !isObject(source)) return source
    for (key in js("Object").keys(source)) {
        val targetValue = target[key]
        val sourceValue = source[key]
        if (js("Array").isArray(targetValue) && js("Array").isArray(sourceValue)) {
            target[key] = targetValue.concat(sourceValue)
        } else if (isObject(targetValue) && isObject(sourceValue)) {
            target[key] = deepMerge(js("Object").assign(js("{}"), targetValue), sourceValue)
        } else {
            target[key] = sourceValue
        }
    }
    return target
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> useModule(@Suppress("UNUSED_PARAMETER") module: T) {
    // empty body
}
