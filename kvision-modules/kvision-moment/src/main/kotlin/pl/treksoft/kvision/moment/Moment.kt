/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2019-present Robert Cronin
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
package pl.treksoft.kvision.moment

import kotlin.js.Date

@Suppress("unused", "TooManyFunctions")
@JsNonModule
@JsModule("moment")
open external class Moment {
    constructor(dateArray: Array<dynamic>)
    constructor(dateString: String, patter: String)
    constructor(dateString: String)
    constructor(date: Date)
    constructor()

    open fun add(duration: dynamic, key: String): dynamic = definedExternally
    open fun subtract(duration: dynamic, key: String): dynamic = definedExternally
    open fun startOf(key: String): dynamic = definedExternally
    open fun endOf(key: String): dynamic = definedExternally
    open fun local(): dynamic = definedExternally
    open fun utc(): dynamic = definedExternally
    open fun utcOffset(): dynamic = definedExternally
    open fun utcOffset(amount: dynamic): dynamic = definedExternally
    open fun utcOffset(amount: dynamic, keepExistingTimeOfDay: Boolean): dynamic = definedExternally
    open fun format(pattern: String): dynamic = definedExternally
    open fun format(): dynamic = definedExternally
    open fun fromNow(): dynamic = definedExternally
    open fun fromNow(withoutSuffix: Boolean): dynamic = definedExternally
    open fun from(moment: dynamic): dynamic = definedExternally
    open fun from(withoutSuffix: Boolean): dynamic = definedExternally
    open fun toNow(): dynamic = definedExternally
    open fun toNow(withoutPrefix: Boolean): dynamic = definedExternally
    open fun to(moment: dynamic): dynamic = definedExternally
    open fun to(withoutPrefix: Boolean): dynamic = definedExternally
    open fun calendar(): dynamic = definedExternally
    open fun calendar(referenceTime: dynamic): dynamic = definedExternally
    open fun calendar(referenceTime: dynamic, formats: dynamic): dynamic = definedExternally
    open fun diff(moment: dynamic): dynamic = definedExternally
    open fun diff(moment: dynamic, key: dynamic): dynamic = definedExternally
    open fun diff(moment: dynamic, key: dynamic, floatingPoint: Boolean): dynamic = definedExternally
    open fun valueOf(): dynamic = definedExternally
    open fun unix(): dynamic = definedExternally
    open fun daysInMonth(): dynamic = definedExternally
    open fun toDate(): dynamic = definedExternally
    open fun toArray(): dynamic = definedExternally
    open fun toJSON(): dynamic = definedExternally
    open fun toISOString(): dynamic = definedExternally
    open fun toISOString(keepOffset: Boolean): dynamic = definedExternally
    open fun toObject(): dynamic = definedExternally
    open fun inspect(): dynamic = definedExternally
    open fun isBefore(moment: dynamic): Boolean = definedExternally
    open fun isBefore(moment: dynamic, key: String): Boolean = definedExternally
    open fun isSame(moment: dynamic): Boolean = definedExternally
    open fun isSame(moment: dynamic, key: String): Boolean = definedExternally
    open fun isAfter(moment: dynamic): Boolean = definedExternally
    open fun isAfter(moment: dynamic, key: String): Boolean = definedExternally
    open fun isSameOrBefore(moment: dynamic): Boolean = definedExternally
    open fun isSameOrBefore(moment: dynamic, key: String): Boolean = definedExternally
    open fun isSameOrAfter(moment: dynamic): Boolean = definedExternally
    open fun isSameOrAfter(moment: dynamic, key: String): Boolean = definedExternally
    open fun isBetween(firstMoment: dynamic, secondMoment: dynamic): Boolean = definedExternally
    open fun isBetween(firstMoment: dynamic, secondMoment: dynamic, key: String, inclusivity: String): Boolean =
        definedExternally

    open fun isBetween(firstMoment: dynamic, secondMoment: dynamic, key: String): Boolean = definedExternally
    open fun isDST(): Boolean = definedExternally
    open fun isDSTShifted(): Boolean = definedExternally
    open fun isLeapYear(): Boolean = definedExternally
    open fun locale(locale: String): dynamic = definedExternally
    open fun locale(resetLocale: Boolean): dynamic = definedExternally
    open fun lang(locale: String): dynamic = definedExternally
    open fun lang(resetLocale: Boolean): dynamic = definedExternally
}
