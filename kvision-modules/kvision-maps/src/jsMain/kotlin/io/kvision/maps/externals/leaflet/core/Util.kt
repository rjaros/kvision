@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

package io.kvision.maps.externals.leaflet.core

/** Various utility functions, used by Leaflet internally. */
external object Util {
    /** Last unique ID used by [stamp]. */
    var lastId: Int

    /** Returns the unique ID of an object, assigning one if it doesn't have it. */
    fun stamp(obj: dynamic): Int

    /** Returns a throttled function. */
    fun throttle(
        fn: (Any?) -> Unit,
        time: Double,
        context: dynamic = definedExternally
    ): (Any?) -> Unit

    /** Returns `num` modulo `range`, wrapped into the range. */
    fun wrapNum(
        num: Double,
        range: Array<Number>,
        includeMax: Boolean = definedExternally
    ): Double

    /** Always returns `false`. */
    fun falseFn(): Boolean

    /** Returns `num` rounded with the specified `precision`. */
    fun formatNum(num: Double, precision: Double = definedExternally): Double

    /** Converts a value to a number, returning `NaN` for non-numeric values. */
    fun toNumber(value: dynamic): Double

    /** Trims and splits a string on whitespace. */
    fun splitWords(str: String): Array<String>

    /** Merges properties into the options of an object. */
    fun setOptions(obj: dynamic, options: dynamic = definedExternally): dynamic

    /** Evaluates a `Hello {a}, {b}` style template. */
    fun template(str: String, data: dynamic): String
}
