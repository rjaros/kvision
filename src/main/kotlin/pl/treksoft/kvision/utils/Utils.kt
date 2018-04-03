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

import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import org.w3c.files.File
import org.w3c.files.FileReader
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.UNIT
import kotlin.browser.window
import kotlin.js.Date

/**
 * Extension property to convert Int to CSS px units.
 */
val Int.px: CssSize
    get() {
        return Pair(this, UNIT.px)
    }

/**
 * Extension property to convert Int to CSS em units.
 */
val Int.em: CssSize
    get() {
        return Pair(this, UNIT.em)
    }

/**
 * Extension property to convert Int to CSS pt units.
 */
val Int.pt: CssSize
    get() {
        return Pair(this, UNIT.pt)
    }

/**
 * Extension property to convert Int to CSS percent units.
 */
val Int.perc: CssSize
    get() {
        return Pair(this, UNIT.perc)
    }

/**
 * Extension property to convert Int to CSS rem units.
 */
val Int.rem: CssSize
    get() {
        return Pair(this, UNIT.rem)
    }

/**
 * Extension property to convert Int to CSS ch units.
 */
val Int.ch: CssSize
    get() {
        return Pair(this, UNIT.ch)
    }

/**
 * Extension property to convert Int to CSS cm units.
 */
val Int.cm: CssSize
    get() {
        return Pair(this, UNIT.cm)
    }

/**
 * Extension property to convert Int to CSS mm units.
 */
val Int.mm: CssSize
    get() {
        return Pair(this, UNIT.mm)
    }

/**
 * Extension property to convert Int to CSS in units.
 */
@Suppress("TopLevelPropertyNaming")
val Int.`in`: CssSize
    get() {
        return Pair(this, UNIT.`in`)
    }

/**
 * Extension property to convert Int to CSS pc units.
 */
val Int.pc: CssSize
    get() {
        return Pair(this, UNIT.pc)
    }

/**
 * Extension property to convert Int to CSS vh units.
 */
val Int.vh: CssSize
    get() {
        return Pair(this, UNIT.vh)
    }

/**
 * Extension property to convert Int to CSS vw units.
 */
val Int.vw: CssSize
    get() {
        return Pair(this, UNIT.vw)
    }

/**
 * Extension property to convert Int to CSS vmin units.
 */
val Int.vmin: CssSize
    get() {
        return Pair(this, UNIT.vmin)
    }

/**
 * Extension property to convert Int to CSS vmax units.
 */
val Int.vmax: CssSize
    get() {
        return Pair(this, UNIT.vmax)
    }

/**
 * Helper property to describe CSS auto value.
 */
val auto: CssSize = Pair(0, UNIT.auto)

/**
 * Helper property to describe CSS normal value.
 */
val normal: CssSize = Pair(0, UNIT.normal)

/**
 * Extension function to convert CssSize to String.
 */
fun CssSize.asString(): String {
    return when (this.second) {
        UNIT.auto -> "auto"
        UNIT.normal -> "normal"
        else -> this.first.toString() + this.second.unit
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

/**
 * Utility function to detect Internet Explorer 11.
 * @return true if the current browser is IE11
 */
fun isIE11(): Boolean = window.navigator.userAgent.matches("Trident\\/7\\.")

/**
 * Suspending extension function to get file content.
 * @return file content
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
suspend fun File.getContent(): String = suspendCancellableCoroutine { cont ->
    val reader = FileReader()
    reader.onload = {
        @Suppress("UnsafeCastFromDynamic")
        cont.resume(reader.result)
    }
    reader.onerror = { e ->
        cont.resumeWithException(Exception(e.type))
    }
    reader.readAsDataURL(this@getContent)
}
