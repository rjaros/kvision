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
package io.kvision.material.radio

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormInputWidget
import io.kvision.material.util.addBool
import io.kvision.material.widget.TouchTarget
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.snabbdom.VNode
import org.w3c.dom.events.Event

/**
 * Radio buttons let people select one option from a set of options.
 *
 * See https://material-web.dev/components/radio/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdRadio(
    id: String? = null,
    checked: Boolean = false,
    disabled: Boolean = false,
    required: Boolean = false,
    value: String = "on",
    name: String? = null,
    validationMessage: String? = null,
    touchTarget: TouchTarget? = TouchTarget.Wrapper,
    className: String? = null,
    init: (MdRadio.() -> Unit)? = null
) : MdFormInputWidget<String>(
    tag = "md-radio",
    disabled = disabled,
    required = required,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className
) {

    /**
     * Whether or not the radio is selected.
     */
    var checked by syncOnUpdate(checked)

    /**
     * Radio touch target.
     */
    var touchTarget by refreshOnUpdate(touchTarget)

    init {
        this.id = id
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        ActiveRadios.add(this)
    }

    override fun afterDestroy() {
        super.afterDestroy()
        ActiveRadios.remove(this)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (checked) {
            attributeSetBuilder.addBool("checked")
        }

        touchTarget?.let {
            attributeSetBuilder.add("touch-target", it.value)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    override fun onChange(event: Event) {
        super.onChange(event)

        // Update all active radios with the same name as no change event is dispatched to radios
        // that have been unchecked as a side effect.
        ActiveRadios.forEach { radio ->
            if (radio.name == name) {
                radio.checked = radio.getElementD().checked == true
            }
        }
    }

    internal companion object {
        /**
         * List of radios which are part of the DOM.
         */
        val ActiveRadios by lazy { mutableListOf<MdRadio>() }
    }
}

@ExperimentalMaterialApi
fun Container.radio(
    id: String? = null,
    checked: Boolean = false,
    disabled: Boolean = false,
    required: Boolean = false,
    value: String = "on",
    name: String? = null,
    touchTarget: TouchTarget? = TouchTarget.Wrapper,
    validationMessage: String? = null,
    className: String? = null,
    init: (MdRadio.() -> Unit)? = null
) = MdRadio(
    id = id,
    checked = checked,
    disabled = disabled,
    required = required,
    value = value,
    name = name,
    touchTarget = touchTarget,
    validationMessage = validationMessage,
    className = className,
    init = init
).also(this::add)
