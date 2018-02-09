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
package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Component
import kotlin.js.Date

/**
 * Input controls sizes.
 */
enum class INPUTSIZE(val className: String) {
    LARGE("input-lg"),
    SMALL("input-sm")
}

/**
 * Base interface of a form control.
 */
interface FormControl : Component {
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean
    /**
     * Input control size.
     */
    var size: INPUTSIZE?
    /**
     * The actual input component.
     */
    val input: Component
    /**
     * Form field label.
     */
    val flabel: FieldLabel
    /**
     * Validation info component.
     */
    val validationInfo: HelpBlock

    /**
     * Returns the value of the control.
     * @return the value
     */
    fun getValue(): Any?

    /**
     * Sets the value of the control.
     * @param v the value
     */
    fun setValue(v: Any?)

    /**
     * Returns the value of the control as a String.
     */
    fun getValueAsString(): String?

    /**
     * @suppress
     * Internal function
     * Re-renders the current component.
     * @return current component
     */
    fun refresh(): Component

    /**
     * Validator error message.
     */
    var validatorError: String?
        get() = validationInfo.text
        set(value) {
            validationInfo.text = value
            validationInfo.visible = value != null
            refresh()
        }
}

/**
 * Base interface of a form control with a text value.
 */
interface StringFormControl : FormControl {
    /**
     * Text value.
     */
    var value: String?

    override fun getValue(): String? = value
    override fun setValue(v: Any?) {
        value = v as? String
    }

    override fun getValueAsString(): String? = value
}

/**
 * Base interface of a form control with a numeric value.
 */
interface NumberFormControl : FormControl {
    /**
     * Numeric value.
     */
    var value: Number?

    override fun getValue(): Number? = value
    override fun setValue(v: Any?) {
        value = v as? Number
    }

    override fun getValueAsString(): String? = value?.toString()
}

/**
 * Base interface of a form control with a boolean value.
 */
interface BoolFormControl : FormControl {
    /**
     * Boolean value.
     */
    var value: Boolean

    override fun getValue(): Boolean = value
    override fun setValue(v: Any?) {
        value = v as? Boolean ?: false
    }

    override fun getValueAsString(): String? = value.toString()
}

/**
 * Base interface of a form control with a date value.
 */
interface DateFormControl : FormControl {
    /**
     * Date value.
     */
    var value: Date?

    override fun getValue(): Date? = value
    override fun setValue(v: Any?) {
        value = v as? Date
    }

    override fun getValueAsString(): String? = value?.toString()
}
