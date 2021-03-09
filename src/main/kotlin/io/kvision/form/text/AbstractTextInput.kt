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

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.state.MutableState

/**
 * Base class for basic text components.
 *
 * @constructor
 * @param value text input value
 * @param classes a set of CSS class names
 */
abstract class AbstractTextInput(
    value: String? = null,
    classes: Set<String> = setOf()
) : Widget(classes), GenericFormComponent<String?>, FormInput, MutableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    init {
        this.setInternalEventListener<AbstractTextInput> {
            input = {
                self.changeValue()
            }
        }
    }

    /**
     * Text input value.
     */
    override var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the text input value.
     */
    var startValue by refreshOnUpdate(value) { this.value = it; refresh() }

    /**
     * The placeholder for the text input.
     */
    var placeholder: String? by refreshOnUpdate()

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Maximal length of the text input value.
     */
    var maxlength: Int? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * Determines if the text input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * Determines if the text input is read-only.
     */
    var readonly: Boolean? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
        classSetBuilder.add(size)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        placeholder?.let {
            attributeSetBuilder.add("placeholder", translate(it))
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        autofocus?.let {
            if (it) {
                attributeSetBuilder.add("autofocus")
            }
        }
        maxlength?.let {
            attributeSetBuilder.add("maxlength", ("" + it))
        }
        readonly?.let {
            if (it) {
                attributeSetBuilder.add("readonly")
            }
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        val v = (getElementJQuery()?.`val`() as? String)
        if (v != value && !(v.isNullOrEmpty() && value == null)) {
            value?.let {
                getElementJQuery()?.`val`(it)
            } ?: getElementJQueryD()?.`val`(null)
        }
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v != "") {
            this.value = v
        } else {
            this.value = null
        }
    }

    /**
     * Makes the input element focused.
     */
    override fun focus() {
        getElementJQuery()?.focus()
    }

    /**
     * Makes the input element blur.
     */
    override fun blur() {
        getElementJQuery()?.blur()
    }

    override fun getState(): String? = value

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: String?) {
        value = state
    }
}
