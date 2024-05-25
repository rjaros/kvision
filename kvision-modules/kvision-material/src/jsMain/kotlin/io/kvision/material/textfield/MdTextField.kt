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

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormInputWidget
import io.kvision.material.slot.HasLeadingSlot
import io.kvision.material.slot.HasTrailingSlot
import io.kvision.material.util.add
import io.kvision.material.util.addBool
import io.kvision.material.util.isNaN
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.html.Autocomplete
import io.kvision.snabbdom.VNode
import org.w3c.dom.SelectionMode
import org.w3c.dom.events.Event
import kotlin.js.Date

/**
 * Text fields let users enter text into a UI.
 *
 * See https://material-web.dev/components/text-field/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdTextField internal constructor(
    tag: String,
    disabled: Boolean,
    error: Boolean,
    errorText: String?,
    label: String?,
    required: Boolean,
    value: String?,
    prefixText: String?,
    suffixText: String?,
    supportingText: String?,
    rows: Int,
    cols: Int,
    inputMode: TextFieldInputMode?,
    max: TextFieldRangeConstraint?,
    maxLength: Int,
    min: TextFieldRangeConstraint?,
    minLength: Int,
    pattern: String?,
    placeholder: String?,
    readOnly: Boolean,
    multiple: Boolean,
    step: Number?,
    type: TextFieldInputType,
    autoComplete: Autocomplete?,
    name: String?,
    validationMessage: String?,
    className: String?
) : MdFormInputWidget<String>(
    tag = tag,
    disabled = disabled,
    required = required,
    value = value.orEmpty(),
    name = name,
    validationMessage = validationMessage,
    className = className
), HasLeadingSlot,
    HasTrailingSlot {

    /**
     * Gets or sets whether or not the text field is in a visually invalid state.
     * This error state overrides the error state controlled by [reportValidity].
     */
    var error by refreshOnUpdate(error)

    /**
     * The error message that replaces supporting text when [error] is true.
     * If [errorText] is an empty string, then the supporting text will continue to show.
     * This error message overrides the error message displayed by [reportValidity].
     */
    var errorText by refreshOnUpdate(errorText)

    /**
     * Label of the text field.
     */
    var label by refreshOnUpdate(label)

    /**
     * An optional prefix to display before the input value.
     */
    var prefixText by refreshOnUpdate(prefixText)

    /**
     * An optional suffix to display after the input value.
     */
    var suffixText by refreshOnUpdate(suffixText)

    /**
     * Conveys additional information below the text field, such as how it should be used.
     */
    var supportingText by refreshOnUpdate(supportingText)

    /**
     * The number of rows to display for a [TextFieldInputType.TextArea] text field.
     */
    var rows by refreshOnUpdate(rows)

    /**
     * The number of cols to display for a [TextFieldInputType.TextArea] text field.
     */
    var cols by refreshOnUpdate(cols)

    /**
     * Hints at the type of data that might be entered by the user while editing the element or
     * its contents. This allows a browser to display an appropriate virtual keyboard.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Global_attributes/inputmode
     */
    var inputMode by refreshOnUpdate(inputMode)

    /**
     * Defines the greatest value in the range of permitted values.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#max
     */
    var max by refreshOnUpdate(max)

    /**
     * The maximum number of characters a user can enter into the text field.
     * Set to -1 for none.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#maxlength
     */
    var maxLength by refreshOnUpdate(maxLength)

    /**
     * Defines the most negative value in the range of permitted values.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#min
     */
    var min by refreshOnUpdate(min)

    /**
     * The minimum number of characters a user can enter into the text field.
     * Set to -1 for none.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength
     */
    var minLength by refreshOnUpdate(minLength)

    /**
     * A regular expression that the text field's value must match to pass constraint validation.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#pattern
     */
    var pattern by refreshOnUpdate(pattern)

    /**
     * Defines the text displayed in a form control when the text field has no value.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/placeholder
     */
    var placeholder by refreshOnUpdate(placeholder)

    /**
     * Indicates whether or not a user should be able to edit the text field's value.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#readonly
     */
    var readOnly by refreshOnUpdate(readOnly)

    /**
     * Indicates that input accepts multiple email addresses.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/email#multiple
     */
    var multiple by refreshOnUpdate(multiple)

    /**
     * Returns or sets the element's step attribute, which works with min and max to limit the
     * increments at which a numeric or date-time value can be set.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#step
     */
    var step by refreshOnUpdate(step)

    /**
     * The <input> type to use, defaults to "text". The type greatly changes how the text field
     * behaves.
     *
     * See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#input_types for more
     * details on each input type.
     */
    var type by refreshOnUpdate(type)

    /**
     * Describes what, if any, type of autocomplete functionality the input should provide.
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/autocomplete
     */
    var autoComplete by refreshOnUpdate(autoComplete)

    /**
     * The direction in which selection occurred.
     */
    var selectionDirection by syncOnUpdate<TextFieldSelectionDirection?, String?>(
        initialValue = null,
        transform = { it?.value }
    )

    /**
     * The starting position or offset of a text selection.
     */
    var selectionStart by syncOnUpdate<Int?>(null)

    /**
     * The end position or offset of a text selection.
     */
    var selectionEnd by syncOnUpdate<Int?>(null)

    /**
     * The text field's value as a number.
     */
    var valueAsNumber by keepOnUpdate<Number?>(null)

    /**
     * The text field's value as a Date.
     */
    var valueAsDate by keepOnUpdate<Date?>(null)

    init {
        setInternalEventListener<MdTextField> {
            select = { self.onSelect() }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        selectionStart?.let { getElementD().selectionStart = it }
        selectionEnd?.let { getElementD().selectionEnd = it }
        selectionDirection?.let { getElementD().selectionDirection = it.value }
        valueAsDate?.let { getElementD().valueAsDate = it }

        if (!valueAsNumber.isNaN()) {
            getElementD().valueAsNumber = valueAsNumber
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attribute
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (error) {
            attributeSetBuilder.addBool("error")
        }

        errorText?.let {
            attributeSetBuilder.add("error-text", translate(it))
        }

        label?.let {
            attributeSetBuilder.add("label", translate(it))
        }

        prefixText?.let {
            attributeSetBuilder.add("prefix-text", translate(it))
        }

        suffixText?.let {
            attributeSetBuilder.add("suffix-text", translate(it))
        }

        supportingText?.let {
            attributeSetBuilder.add("supporting-text", translate(it))
        }

        if (type == TextFieldInputType.TextArea) {
            attributeSetBuilder.add("rows", rows)
            attributeSetBuilder.add("cols", cols)
        }

        inputMode?.let {
            attributeSetBuilder.add("inputmode", it.value)
        }

        max?.let {
            attributeSetBuilder.add("max", it.stringValue(type))
        }

        if (maxLength != -1) {
            attributeSetBuilder.add("maxlength", maxLength)
        }

        min?.let {
            attributeSetBuilder.add("min", it.stringValue(type))
        }

        if (minLength != -1) {
            attributeSetBuilder.add("minlength", minLength)
        }

        pattern?.let {
            attributeSetBuilder.add("pattern", it)
        }

        placeholder?.let {
            attributeSetBuilder.add("placeholder", translate(it))
        }

        if (readOnly) {
            attributeSetBuilder.addBool("readonly")
        }

        if (multiple) {
            attributeSetBuilder.addBool("multiple")
        }

        step?.let {
            attributeSetBuilder.add("step", it)
        }

        attributeSetBuilder.add("type", type.value)

        autoComplete?.let {
            attributeSetBuilder.add("autocomplete", it.type)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    override fun leading(component: Component?) {
        Slot.LeadingIcon(component)
    }

    override fun trailing(component: Component?) {
        Slot.TrailingIcon(component)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Value
    ///////////////////////////////////////////////////////////////////////////

    private fun refreshValue() {
        value = getElementD().value.unsafeCast<String>()
    }

    /**
     * Replaces a range of text with a new string.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/setRangeText
     */
    fun setRangeText(replacement: String) {
        requireElementD().setRangeText(replacement)
        refreshValue()
    }

    /**
     * Replaces a range of text with a new string.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/setRangeText
     */
    fun setRangeText(
        replacement: String,
        start: Int,
        end: Int,
        selectionMode: SelectionMode
    ) {
        requireElementD().setRangeText(replacement, start, end, selectionMode)
        refreshValue()
    }

    /**
     * Reset the text field to its default value.
     */
    fun reset() {
        requireElementD().reset()
        refreshValue()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Selection
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Selects all the text in the text field.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/select
     */
    fun select() {
        requireElementD().select()
    }

    /**
     * Sets the start and end positions of a selection in the text field.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/setSelectionRange
     */
    fun setSelectionRange(
        start: Int?,
        end: Int?,
        direction: TextFieldSelectionDirection? = null
    ) {
        requireElementD().setSelectionRange(start, end, direction?.value)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Step
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Decrements the value of a numeric type text field by [MdTextField.step] or [stepDecrement]
     * step number of times.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/stepDown
     */
    fun stepDown(stepDecrement: Number? = null) {
        if (stepDecrement.isNaN()) {
            requireElementD().stepDown()
        } else {
            requireElementD().stepDown(stepDecrement)
        }

        refreshValue()
    }

    /**
     * Increments the value of a numeric type text field by [MdTextField.step] or [stepIncrement]
     * step number of times.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/stepUp
     */
    fun stepUp(stepIncrement: Number? = null) {
        if (stepIncrement.isNaN()) {
            requireElementD().stepUp()
        } else {
            requireElementD().stepUp(stepIncrement)
        }

        refreshValue()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    override fun hasInputEvent(): Boolean = true

    override fun onInput(event: Event) {
        super.onInput(event)
        refreshValue()
    }

    private fun onSelect() {
        selectionStart = getElementD().selectionStart.unsafeCast<Int>()
        selectionEnd = getElementD().selectionEnd.unsafeCast<Int>()

        val newSelectionDirection = getElementD().selectionDirection.unsafeCast<String>()

        selectionDirection = TextFieldSelectionDirection.entries
            .first { it.value == newSelectionDirection }
    }
}
