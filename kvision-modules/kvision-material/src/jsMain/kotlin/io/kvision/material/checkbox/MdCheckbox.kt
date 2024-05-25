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
package io.kvision.material.checkbox

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormToggleInputWidget
import io.kvision.material.util.addBool
import io.kvision.material.widget.TouchTarget
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import org.w3c.dom.events.Event

/**
 * Checkboxes allow users to select one or more items from a set.
 * Checkboxes can turn an option on or off.
 *
 * There's one type of checkbox in Material.
 * Use this selection control when the user needs to select one or more options from a list.
 *
 * See https://material-web.dev/components/checkbox/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdCheckbox(
    checked: Boolean = false,
    disabled: Boolean = false,
    indeterminate: Boolean = false,
    required: Boolean = false,
    value: String = "on",
    name: String? = null,
    touchTarget: TouchTarget? = TouchTarget.Wrapper,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdCheckbox.() -> Unit)? = null
) : MdFormToggleInputWidget(
    tag = "md-checkbox",
    disabled = disabled,
    required = required,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className,
) {

    /**
     * Whether or not the checkbox is selected.
     */
    var checked by syncOnUpdate(checked)

    /**
     * Whether or not the checkbox is indeterminate.
     *
     * https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/checkbox#indeterminate_state_checkboxes
     */
    var indeterminate by refreshOnUpdate(indeterminate)

    /**
     * Checkbox touch target.
     */
    var touchTarget by refreshOnUpdate(touchTarget)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (indeterminate) {
            attributeSetBuilder.addBool("indeterminate")
        }

        if (checked) {
            attributeSetBuilder.addBool("checked")
        }

        touchTarget?.let {
            attributeSetBuilder.add("touch-target", it.value)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // State
    ///////////////////////////////////////////////////////////////////////////

    override fun toggle() {
        checked = !checked
    }

    override fun onChange(event: Event) {
        super.onChange(event)
        checked = getElementD().checked == true
    }
}

@ExperimentalMaterialApi
fun Container.checkbox(
    checked: Boolean = false,
    disabled: Boolean = false,
    indeterminate: Boolean = false,
    required: Boolean = false,
    value: String = "on",
    name: String? = null,
    touchTarget: TouchTarget? = TouchTarget.Wrapper,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdCheckbox.() -> Unit)? = null
) = MdCheckbox(
    checked = checked,
    disabled = disabled,
    indeterminate = indeterminate,
    required = required,
    value = value,
    name = name,
    touchTarget = touchTarget,
    validationMessage = validationMessage,
    className = className,
    init = init
).also(this::add)
