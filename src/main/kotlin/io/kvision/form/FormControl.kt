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
package io.kvision.form

import io.kvision.core.Component
import io.kvision.core.CssClass
import io.kvision.types.KFile
import org.w3c.files.File
import kotlin.js.Date

/**
 * Input controls sizes.
 */
enum class InputSize(override val className: String) : CssClass {
    LARGE("form-control-lg"),
    SMALL("form-control-sm")
}

/**
 * Input controls validation status.
 */
enum class ValidationStatus(override val className: String) : CssClass {
    VALID("is-valid"),
    INVALID("is-invalid")
}

interface FormInput : Component {
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean

    /**
     * Input control field size.
     */
    var size: InputSize?

    /**
     * The name attribute of the generated HTML input element.
     */
    var name: String?

    /**
     * Input control validation status.
     */
    var validationStatus: ValidationStatus?
}

/**
 * Base interface of a form component with a generic value.
 */
interface GenericFormComponent<T> : Component {
    /**
     * Generic value.
     */
    var value: T

    fun subscribe(observer: (T) -> Unit): () -> Unit
}

/**
 * Base interface of a form control.
 */
interface FormControl : Component {
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean
        get() = input.disabled
        set(value) {
            input.disabled = value
        }

    /**
     * Input control field size.
     */
    var size: InputSize?
        get() = input.size
        set(value) {
            input.size = value
            if (value == InputSize.SMALL) {
                flabel.addCssClass("col-form-label-sm")
            } else {
                flabel.removeCssClass("col-form-label-sm")
            }
            if (value == InputSize.LARGE) {
                flabel.addCssClass("col-form-label-lg")
            } else {
                flabel.removeCssClass("col-form-label-lg")
            }
        }

    /**
     * The name attribute of the generated HTML input element.
     */
    var name: String?
        get() = input.name
        set(value) {
            input.name = value
        }

    /**
     * Input control validation status.
     */
    var validationStatus: ValidationStatus?
        get() = input.validationStatus
        set(value) {
            input.validationStatus = value
        }

    /**
     * The actual input component.
     */
    val input: FormInput

    /**
     * Form field label.
     */
    val flabel: FieldLabel

    /**
     * Invalid feedback component.
     */
    val invalidFeedback: InvalidFeedback

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
        get() = invalidFeedback.content
        set(value) {
            invalidFeedback.content = value
            invalidFeedback.visible = value != null
            input.validationStatus = if (value != null) ValidationStatus.INVALID else null
            refresh()
        }

    /**
     * Style form control element for vertical form panel.
     */
    fun styleForVerticalFormPanel() {
    }

    /**
     * Style form control element for horizontal form panel.
     */
    fun styleForHorizontalFormPanel(horizontalRatio: FormHorizontalRatio) {
        addCssClass("row")
        flabel.addCssClass("col-sm-${horizontalRatio.labels}")
        flabel.addCssClass("col-form-label")
        input.addCssClass("col-sm-${horizontalRatio.fields}")
        invalidFeedback.addCssClass("offset-sm-${horizontalRatio.labels}")
        invalidFeedback.addCssClass("col-sm-${horizontalRatio.fields}")
    }

    /**
     * Style form control element for inline form panel.
     */
    fun styleForInlineFormPanel() {
    }
}

/**
 * Base interface of a form control with a generic value.
 */
interface GenericFormControl<T> : FormControl, GenericFormComponent<T?> {

    override fun getValue(): T? = value
    override fun setValue(v: Any?) {
        @Suppress("UNCHECKED_CAST")
        value = v as? T
    }

    override fun getValueAsString(): String? = value?.toString()
}

/**
 * Base interface of a form control with a generic, non-nullable value.
 */
interface GenericNonNullableFormControl<T> : FormControl, GenericFormComponent<T> {

    override fun getValue(): T = value
    override fun setValue(v: Any?) {
        @Suppress("UNCHECKED_CAST")
        value = v as? T ?: throw IllegalStateException()
    }

    override fun getValueAsString(): String? = value?.toString()
}

/**
 * Base interface of a form control with a text value.
 */
interface StringFormControl : GenericFormControl<String> {
    override fun setValue(v: Any?) {
        value = v as? String ?: v?.toString()
    }

    override fun getValueAsString(): String? = value
}

/**
 * Base interface of a form control with a numeric value.
 */
interface NumberFormControl : GenericFormControl<Number> {
    override fun setValue(v: Any?) {
        value = v as? Number
    }
}

/**
 * Base interface of a form control with a boolean value.
 */
interface BoolFormControl : GenericNonNullableFormControl<Boolean> {
    override fun setValue(v: Any?) {
        value = v as? Boolean ?: false
    }
}

/**
 * Base interface of a form control with a date value.
 */
interface DateFormControl : GenericFormControl<Date> {
    override fun setValue(v: Any?) {
        value = v as? Date
    }
}

/**
 * Base interface of a form control with a list of files value.
 */
interface KFilesFormControl : GenericFormControl<List<KFile>> {
    override fun setValue(v: Any?) {
        if (v == null) value = null
    }

    override fun getValueAsString(): String? = value?.joinToString(",") { it.name }

    /**
     * Returns the native JavaScript File object.
     * @param kFile KFile object
     * @return File object
     */
    fun getNativeFile(kFile: KFile): File?
}
