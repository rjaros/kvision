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
import io.kvision.material.util.add
import io.kvision.material.util.addBool
import io.kvision.material.widget.MdWidget
import io.kvision.core.AttributeSetBuilder
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.events.Event

/**
 * Subclass of widgets that are associated with form.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
abstract class MdFormWidget<V> internal constructor(
    tag: String,
    disabled: Boolean,
    value: V,
    name: String?,
    className: String?,
) : MdWidget(
    tag = tag,
    className = className
) {

    /**
     * Whether or not the widget is disabled.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * The value that is submitted to the form.
     */
    var value: V by syncOnUpdate(value)

    /**
     * The name to use in form submission.
     */
    var name by refreshOnUpdate(name)

    /**
     * The associated form element with which this element's value will submit.
     */
    val form: HTMLFormElement?
        get() = getElement()?.asDynamic()?.form as? HTMLFormElement

    init {
        subscribeEvents()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (disabled) {
            attributeSetBuilder.addBool("disabled")
        }

        name?.let {
            attributeSetBuilder.add("name", it)
        }

        value?.let {
            attributeSetBuilder.add("value", it)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Indicates that the widget produce change events.
     * Default to true.
     */
    protected open fun hasChangeEvent(): Boolean = true

    /**
     * Indicates that the widget produce input events.
     * Default to false.
     */
    protected open fun hasInputEvent(): Boolean = false

    /**
     * Notifies about 'change' event.
     *
     * It is a good practice to compare DOM element value with object value before updating
     * object value in order to avoid unnecessary refreshes.
     */
    protected open fun onChange(event: Event) = Unit

    /**
     * Notifies about 'input' event.
     */
    protected open fun onInput(event: Event) = Unit

    /**
     * Subscribe to events.
     */
    private fun subscribeEvents() {
        val changeEvent = hasChangeEvent()
        val inputEvent = hasInputEvent()

        if (!changeEvent && !inputEvent) {
            return
        }

        setInternalEventListener<MdFormWidget<*>> {
            if (changeEvent) {
                change = self::onChange
            }

            if (inputEvent) {
                input = self::onInput
            }
        }
    }
}
