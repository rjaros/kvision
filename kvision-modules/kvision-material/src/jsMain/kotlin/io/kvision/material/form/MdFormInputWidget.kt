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
package io.kvision.material.form

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.addBool
import io.kvision.material.util.requireElementD
import io.kvision.core.AttributeSetBuilder
import io.kvision.snabbdom.VNode
import org.w3c.dom.ValidityState

/**
 * Subclass of input widgets that are associated to a form.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdFormInputWidget<V> internal constructor(
    tag: String,
    disabled: Boolean,
    required: Boolean,
    value: V,
    name: String?,
    validationMessage: String?,
    className: String?,
) : MdFormLabelWidget<V>(
    tag = tag,
    disabled = disabled,
    value = value,
    name = name,
    className = className
) {

    /**
     * The initial validation message to set once the component is ready.
     */
    private var initialValidationMessage = validationMessage

    /**
     * Indicates that the component must provides a valid value when participating in
     * form submission.
     */
    var required by refreshOnUpdate(required)

    /**
     * Message to display when the component value is not valid.
     */
    val validationMessage: String?
        get() = getElement()?.asDynamic()?.validationMessage as? String ?: initialValidationMessage

    /**
     * Gets the element's current validity state.
     */
    val validity: ValidityState?
        get() = getElement()?.asDynamic()?.validity as? ValidityState

    /**
     * Indicates that the element is a candidate for constraint validation.
     */
    val willValidate: Boolean
        get() = getElement()?.asDynamic()?.willValidate as? Boolean ?: false

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        initialValidationMessage?.let(this::setCustomValidity)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (required) {
            attributeSetBuilder.addBool("required")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Validation
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Indicates the validity of the value of the element.
     * If the value is invalid, this method also fires the invalid event on the element.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/checkValidity
     */
    fun checkValidity(): Boolean {
        return requireElementD().checkValidity() as Boolean
    }

    /**
     * Performs the same validity checking steps as the [checkValidity] method.
     * If the value is invalid, this method also fires the invalid event on the element, and (if
     * the event isn't canceled) reports the problem to the user.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/reportValidity
     */
    fun reportValidity(): Boolean {
        return requireElementD().reportValidity() as Boolean
    }

    /**
     * Sets a custom validity message for the underlying element.
     *
     * https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/setCustomValidity
     */
    fun setCustomValidity(message: String) {
        initialValidationMessage = message
        requireElementD().setCustomValidity(translate(message))
    }
}
