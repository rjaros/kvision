/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 Maanrifa Bacar Ali <dev.manrif@gmail.com>
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
package io.kvision.material.textfield

import io.kvision.material.textfield.TextFieldRangeConstraint.Numeric
import io.kvision.material.textfield.TextFieldRangeConstraint.Raw
import io.kvision.material.textfield.TextFieldRangeConstraint.Temporal
import kotlin.js.Date

/**
 * Input type.
 */
enum class TextFieldInputType(internal val value: String) {
    // Fully supported
    Text("text"),
    TextArea("textarea"),
    Email("email"),
    Number("number"),
    Password("password"),
    Search("search"),
    Tel("tel"),
    Url("url"),

    // Partially supported
    @MaterielUnsupportedTextFieldType
    Color("color"),

    @MaterielUnsupportedTextFieldType
    Date("date"),

    @MaterielUnsupportedTextFieldType
    DatetimeLocal("datetime-local"),

    @MaterielUnsupportedTextFieldType
    File("file"),

    @MaterielUnsupportedTextFieldType
    Month("month"),

    @MaterielUnsupportedTextFieldType
    Time("time"),

    @MaterielUnsupportedTextFieldType
    Week("week")
}

/**
 * Input mode.
 */
enum class TextFieldInputMode(internal val value: String) {
    None("none"),
    Text("text"),
    Decimal("decimal"),
    Numeric("numeric"),
    Tel("tel"),
    Search("search"),
    Email("email"),
    Url("url")
}

/**
 * Selection direction.
 */
enum class TextFieldSelectionDirection(internal val value: String) {
    None("none"),
    Forward("forward"),
    Backward("backward")
}

///////////////////////////////////////////////////////////////////////////
// Constraint
///////////////////////////////////////////////////////////////////////////

private fun Int.toPaddedString(length: Int) = toString().padStart(length, '0')

/**
 * Range constraint for min and max properties.
 *
 * https://developer.mozilla.org/en-US/docs/Web/HTML/Constraint_validation
 */
sealed interface TextFieldRangeConstraint {

    fun stringValue(type: TextFieldInputType): String

    /**
     * For range and number input types.
     */
    value class Numeric(private val value: Number) : TextFieldRangeConstraint {

        override fun stringValue(type: TextFieldInputType): String {
            require(type == TextFieldInputType.Number) {
                "`${type.value}` input type does not support numeric constraint"
            }

            return value.toString()
        }
    }

    /**
     * For date, time and datetime-local (UTC) input types.
     *
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/date
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/time
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/datetime-local
     */
    value class Temporal(private val date: Date) : TextFieldRangeConstraint {

        @OptIn(MaterielUnsupportedTextFieldType::class)
        override fun stringValue(type: TextFieldInputType): String {
            return when (type) {
                TextFieldInputType.DatetimeLocal -> date.toISOString()

                TextFieldInputType.Date -> buildString {
                    append(date.getFullYear().toPaddedString(4))
                    append('-')
                    append(date.getMonth().toPaddedString(2))
                    append('-')
                    append(date.getDate().toPaddedString(2))
                }

                TextFieldInputType.Time -> buildString {
                    append(date.getHours().toPaddedString(2))
                    append(':')
                    append(date.getMinutes().toPaddedString(2))
                    append(':')
                    append(date.getSeconds().toPaddedString(2))
                }

                else -> throw IllegalArgumentException("`${type.value}` input type does not support temporal constraint")
            }
        }
    }

    /**
     * For month input type.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/month
     */
    data class Month(val year: Int, val month: Int) : TextFieldRangeConstraint {

        @OptIn(MaterielUnsupportedTextFieldType::class)
        override fun stringValue(type: TextFieldInputType): String {
            require(type == TextFieldInputType.Month) {
                "`${type.value}` input type does not support month constraint"
            }

            return buildString {
                append(year.toPaddedString(4))
                append('-')
                append(month.toPaddedString(2))
            }
        }
    }

    /**
     * For week input type.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/week
     */
    data class Week(val year: Int, val week: Int) : TextFieldRangeConstraint {

        @OptIn(MaterielUnsupportedTextFieldType::class)
        override fun stringValue(type: TextFieldInputType): String {
            require(type == TextFieldInputType.Week) {
                "`${type.value}` input type does not support week constraint"
            }

            return buildString {
                append(year.toPaddedString(4))
                append('-')
                append('W')
                append(week.toPaddedString(2))
            }
        }
    }

    /**
     * Free constraint value.
     */
    value class Raw(private val value: String) : TextFieldRangeConstraint {

        override fun stringValue(type: TextFieldInputType): String {
            return value
        }
    }

    companion object {

        /**
         * Returns a [Numeric] constraint.
         */
        operator fun invoke(value: Number) = Numeric(value)

        /**
         * Returns a [Temporal] constraint.
         */
        operator fun invoke(value : Date) = Temporal(value)

        /**
         * Returns a [Raw] constraint.
         */
        operator fun invoke(value: String) = Raw(value)
    }
}

/**
 * Returns a [Numeric] constraint.
 */
inline val Number.constraint: TextFieldRangeConstraint
    get() = TextFieldRangeConstraint(this)

/**
 * Returns a [Temporal] constraint.
 */
inline val Date.constraint: TextFieldRangeConstraint
    get() = TextFieldRangeConstraint(this)

/**
 * Returns a [Raw] constraint.
 */
inline val String.constraint: TextFieldRangeConstraint
    get() = TextFieldRangeConstraint(this)
