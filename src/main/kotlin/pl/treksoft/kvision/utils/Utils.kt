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

import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.UNIT
import kotlinx.browser.window

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
val Number.em: CssSize
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
val Number.perc: CssSize
    get() {
        return Pair(this, UNIT.perc)
    }

/**
 * Extension property to convert Int to CSS rem units.
 */
val Number.rem: CssSize
    get() {
        return Pair(this, UNIT.rem)
    }

/**
 * Extension property to convert Int to CSS ch units.
 */
val Number.ch: CssSize
    get() {
        return Pair(this, UNIT.ch)
    }

/**
 * Extension property to convert Int to CSS cm units.
 */
val Number.cm: CssSize
    get() {
        return Pair(this, UNIT.cm)
    }

/**
 * Extension property to convert Int to CSS mm units.
 */
val Number.mm: CssSize
    get() {
        return Pair(this, UNIT.mm)
    }

/**
 * Extension property to convert Int to CSS in units.
 */
@Suppress("TopLevelPropertyNaming")
val Number.`in`: CssSize
    get() {
        return Pair(this, UNIT.`in`)
    }

/**
 * Extension property to convert Int to CSS pc units.
 */
val Number.pc: CssSize
    get() {
        return Pair(this, UNIT.pc)
    }

/**
 * Extension property to convert Int to CSS vh units.
 */
val Number.vh: CssSize
    get() {
        return Pair(this, UNIT.vh)
    }

/**
 * Extension property to convert Int to CSS vw units.
 */
val Number.vw: CssSize
    get() {
        return Pair(this, UNIT.vw)
    }

/**
 * Extension property to convert Int to CSS vmin units.
 */
val Number.vmin: CssSize
    get() {
        return Pair(this, UNIT.vmin)
    }

/**
 * Extension property to convert Int to CSS vmax units.
 */
val Number.vmax: CssSize
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

/**
 * Extension operator to increase CssSize units.
 */
operator fun CssSize?.plus(i: Number): CssSize {
    return this?.let { CssSize(it.first.toDouble() + i.toDouble(), it.second) } ?: CssSize(i, UNIT.px)
}

/**
 * Extension operator to decrease CssSize units.
 */
operator fun CssSize?.minus(i: Number): CssSize {
    return this?.let { CssSize(it.first.toDouble() - i.toDouble(), it.second) } ?: CssSize(i, UNIT.px)
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
 * Utility function to detect Internet Explorer 11.
 * @return true if the current browser is IE11
 */
fun isIE11(): Boolean = window.navigator.userAgent.matches("Trident\\/7\\.")

/**
 * Utility extension function to synchronise elements of the MutableList.
 */
fun <T> MutableList<T>.syncWithList(list: List<T>) {
    if (list.isEmpty()) {
        this.clear()
    } else {
        for (pos in (this.size - 1) downTo list.size) this.removeAt(pos)
        list.forEachIndexed { index, element ->
            if (index < this.size) {
                if (this[index] != element) this[index] = element
            } else {
                this.add(element)
            }
        }
    }
}

/**
 * Utility extension property to generate a set of strings to simplify the notation when using classes parameter.
 */
val String?.set: Set<String>
    get() {
        return this?.split(Regex("\\s+"))?.toSet() ?: setOf()
    }

/**
 * Utility extension function to convert string from kebab-case to camelCase.
 */
fun String.toCamelCase(): String {
    return this.replace(Regex("(-\\w)")) {
        it.value.drop(1).toUpperCase()
    }
}
