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
@file:Suppress("TooManyFunctions")

package pl.treksoft.kvision.utils

import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.UNIT
import kotlin.js.Date

/**
 * Extension function to convert Int to CSS px units.
 */
fun Int.px(): CssSize = Pair(this, UNIT.px)

/**
 * Extension function to convert Int to CSS em units.
 */
fun Int.em(): CssSize = Pair(this, UNIT.em)

/**
 * Extension function to convert Int to CSS pt units.
 */
fun Int.pt(): CssSize = Pair(this, UNIT.pt)

/**
 * Extension function to convert Int to CSS percent units.
 */
fun Int.perc(): CssSize = Pair(this, UNIT.perc)

/**
 * Extension function to convert Int to CSS rem units.
 */
fun Int.rem(): CssSize = Pair(this, UNIT.rem)

/**
 * Extension function to convert Int to CSS ch units.
 */
fun Int.ch(): CssSize = Pair(this, UNIT.ch)

/**
 * Extension function to convert Int to CSS cm units.
 */
fun Int.cm(): CssSize = Pair(this, UNIT.cm)

/**
 * Extension function to convert Int to CSS mm units.
 */
fun Int.mm(): CssSize = Pair(this, UNIT.mm)

/**
 * Extension function to convert Int to CSS in units.
 */
@Suppress("FunctionNaming")
fun Int.`in`(): CssSize = Pair(this, UNIT.`in`)

/**
 * Extension function to convert Int to CSS pc units.
 */
fun Int.pc(): CssSize = Pair(this, UNIT.pc)

/**
 * Extension function to convert Int to CSS vh units.
 */
fun Int.vh(): CssSize = Pair(this, UNIT.vh)

/**
 * Extension function to convert Int to CSS vw units.
 */
fun Int.vw(): CssSize = Pair(this, UNIT.vw)

/**
 * Extension function to convert Int to CSS vmin units.
 */
fun Int.vmin(): CssSize = Pair(this, UNIT.vmin)

/**
 * Extension function to convert Int to CSS vmax units.
 */
fun Int.vmax(): CssSize = Pair(this, UNIT.vmax)

/**
 * Helper function to describe CSS auto value.
 */
fun auto(): CssSize = Pair(0, UNIT.auto)

/**
 * Extension function to convert CssSize to String.
 */
fun CssSize.asString(): String {
    return if (this.second != UNIT.auto) {
        this.first.toString() + this.second.unit
    } else {
        "auto"
    }
}

private val hex = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

/**
 * Extension function to convert Int to hex format.
 */
@Suppress("MagicNumber")
fun Int.toHexString(): String {
    var result = ""
    var num = this
    for (i in 0 until 6) {
        result = hex[num and 0xF] + result
        num = num shr 4
    }
    return result
}

/**
 * Extension function to convert String to Date with a given date format.
 * @param format date/time format
 * @return Date object
 */
@Suppress("UnsafeCastFromDynamic")
fun String.toDateF(format: String = "YYYY-MM-DD HH:mm:ss"): Date {
    return KVManager.fecha.parse(this, format)
}

/**
 * Extension function to convert Date to String with a given date format.
 * @param format date/time format
 * @return String object
 */
@Suppress("UnsafeCastFromDynamic")
fun Date.toStringF(format: String = "YYYY-MM-DD HH:mm:ss"): String {
    return KVManager.fecha.format(this, format)
}
