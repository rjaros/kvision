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
package io.kvision.form.text

import io.kvision.i18n.I18n
import io.kvision.utils.obj
import kotlin.js.RegExp

/**
 * Text input mask overwrite modes.
 */
enum class MaskOverwrite(internal val overwrite: String) {
    TRUE("true"),
    FALSE("false"),
    SHIFT("shift")
}

/**
 * Text input number mask autofix modes.
 */
enum class MaskAutofix(internal val autofix: String) {
    TRUE("true"),
    FALSE("false"),
    PAD("pad")
}

/**
 * A text input mask configuration with a pattern.
 */
data class PatternMask(
    val pattern: String,
    val lazy: Boolean? = null,
    val eager: Boolean? = null,
    val placeholderChar: Char? = null,
    val definitions: dynamic = null,
    val blocks: Map<String, ImaskOptions>? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
fun PatternMask.toJs(imask: dynamic): dynamic {
    return obj {
        this.mask = pattern
        if (lazy != null) this.lazy = lazy
        if (eager != null) this.eager = eager
        if (placeholderChar != null) this.placeholderChar = placeholderChar.toString()
        if (definitions != null) this.definitions = definitions
        if (blocks != null) {
            this.blocks = obj {
                blocks.forEach { (def, options) ->
                    this[def] = options.toJs(imask)
                }
            }
        }
    }
}

/**
 * A text input mask configuration with a range.
 */
data class RangeMask(
    val from: Int,
    val to: Int,
    val maxLength: Int? = null,
    val autofix: MaskAutofix? = null,
    val lazy: Boolean? = null,
    val eager: Boolean? = null,
    val placeholderChar: Char? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
fun RangeMask.toJs(imask: dynamic): dynamic {
    val autofixDynamic = when (autofix) {
        MaskAutofix.TRUE -> true
        MaskAutofix.PAD -> MaskAutofix.PAD.autofix
        else -> undefined
    }
    return obj {
        this.mask = imask.MaskedRange
        this.from = from
        this.to = to
        if (maxLength != null) this.maxLength = maxLength
        if (autofix != null) this.autofix = autofixDynamic
        if (lazy != null) this.lazy = lazy
        if (eager != null) this.eager = eager
        if (placeholderChar != null) this.placeholderChar = placeholderChar.toString()
    }
}

/**
 * A text input mask configuration with a list of values.
 */
data class EnumMask(
    val enum: List<String>,
    val lazy: Boolean? = null,
    val eager: Boolean? = null,
    val placeholderChar: Char? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
fun EnumMask.toJs(imask: dynamic): dynamic {
    return obj {
        this.mask = imask.MaskedEnum
        this.enum = enum.toTypedArray()
        if (lazy != null) this.lazy = lazy
        if (eager != null) this.eager = eager
        if (placeholderChar != null) this.placeholderChar = placeholderChar.toString()
    }
}

/**
 * A text input mask configuration for a number value.
 */
data class NumberMask(
    val scale: Int? = null,
    val signed: Boolean? = null,
    val thousandsSeparator: Char? = null,
    val padFractionalZeros: Boolean? = null,
    val normalizeZeros: Boolean? = null,
    val radix: Char = I18n.detectDecimalSeparator(),
    val mapToRadix: List<Char> = listOf('.'),
    val min: Number? = null,
    val max: Number? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
fun NumberMask.toJs(): dynamic {
    return obj {
        this.mask = js("Number")
        if (scale != null) this.scale = scale
        if (signed != null) this.signed = signed
        if (thousandsSeparator != null) this.thousandsSeparator = thousandsSeparator.toString()
        if (padFractionalZeros != null) this.padFractionalZeros = padFractionalZeros
        if (normalizeZeros != null) this.normalizeZeros = normalizeZeros
        this.radix = radix.toString()
        this.mapToRadix = mapToRadix.map { it.toString() }.toTypedArray()
        if (min != null) this.min = min
        if (max != null) this.max = max
    }
}

/**
 * A text input mask configuration.
 */
data class ImaskOptions(
    val pattern: PatternMask? = null,
    val range: RangeMask? = null,
    val enum: EnumMask? = null,
    val number: NumberMask? = null,
    val regExp: RegExp? = null,
    val function: ((String) -> Boolean)? = null,
    val list: List<ImaskOptions>? = null,
    val overwrite: MaskOverwrite? = null,
) : MaskOptions {
    override fun maskNumericValue(value: String): String {
        return if (number?.radix != null) {
            value.replace(".", number.radix.toString())
        } else value
    }
}

/**
 * An extension function to convert configuration class to a JS object.
 */
fun ImaskOptions.toJs(imask: dynamic): dynamic {
    val overwriteDynamic = when (overwrite) {
        MaskOverwrite.TRUE -> true
        MaskOverwrite.SHIFT -> MaskOverwrite.SHIFT.overwrite
        else -> undefined
    }
    return obj {
        if (pattern != null) {
            val patternOptions = pattern.toJs(imask)
            js("Object").assign(this, patternOptions)
        } else if (range != null) {
            val rangeOptions = range.toJs(imask)
            js("Object").assign(this, rangeOptions)
        } else if (enum != null) {
            val enumOptions = enum.toJs(imask)
            js("Object").assign(this, enumOptions)
        } else if (number != null) {
            val numberOptions = number.toJs()
            js("Object").assign(this, numberOptions)
        } else if (regExp != null) {
            this.mask = regExp
        } else if (function != null) {
            this.mask = function
        } else if (list != null) {
            this.mask = list.map { it.toJs(imask) }.toTypedArray()
        }
        if (overwrite != null) this.overwrite = overwriteDynamic
    }
}
