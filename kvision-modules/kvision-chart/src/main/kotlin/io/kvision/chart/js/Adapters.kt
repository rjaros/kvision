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

package io.kvision.chart.js

external interface DateAdapter {
    fun override(members: DateAdapterPartial)
    var options: Any
    fun formats(): dynamic
    fun parse(
        value: Any,
        format: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */ = definedExternally
    ): Number?

    fun format(
        timestamp: Number,
        format: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */
    ): String

    fun add(
        timestamp: Number,
        amount: Number,
        unit: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */
    ): Number

    fun diff(
        a: Number,
        b: Number,
        unit: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */
    ): Number

    fun startOf(
        timestamp: Number,
        unit: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" | "isoWeek" */,
        weekday: Number = definedExternally
    ): Number

    fun endOf(
        timestamp: Number,
        unit: String /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" | "isoWeek" */
    ): Number
}

external interface DateAdapterPartial {
    var override: ((members: dynamic) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var options: Any?
        get() = definedExternally
        set(value) = definedExternally
    var formats: (() -> dynamic)?
        get() = definedExternally
        set(value) = definedExternally
    var parse: ((value: Any, format: String? /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */) -> Number?)?
        get() = definedExternally
        set(value) = definedExternally
    var format: ((timestamp: Number, format: String? /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */) -> String)?
        get() = definedExternally
        set(value) = definedExternally
    var add: ((timestamp: Number, amount: Number, unit: String? /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */) -> Number)?
        get() = definedExternally
        set(value) = definedExternally
    var diff: ((a: Number, b: Number, unit: String? /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" */) -> Number)?
        get() = definedExternally
        set(value) = definedExternally
    var startOf: ((timestamp: Number, unit: String? /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" | "isoWeek" */, weekday: Number) -> Number)?
        get() = definedExternally
        set(value) = definedExternally
    var endOf: ((timestamp: Number, unit: String? /* "millisecond" | "second" | "minute" | "hour" | "day" | "week" | "month" | "quarter" | "year" | "isoWeek" */) -> Number)?
        get() = definedExternally
        set(value) = definedExternally
}