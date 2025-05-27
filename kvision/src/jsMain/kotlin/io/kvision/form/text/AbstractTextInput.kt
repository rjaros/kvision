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

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.state.MutableState
import org.w3c.dom.HTMLElement

/**
 * Base class for basic text components.
 *
 * @constructor
 * @param value text input value
 * @param maxlength maximum length of the text input
 * @param className CSS class names
 */
abstract class AbstractTextInput(
    value: String? = null,
    maxlength: Int? = null,
    className: String? = null
) : Widget(className), GenericFormComponent<String?>, FormInput, MutableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * Text input value.
     */
    override var value: String? by refreshOnUpdate(value?.ifEmpty { null }) {
        if (it == "") {
            this.value = null
        } else {
            refreshState(); observers.forEach { ob -> ob(it) }
        }
    }

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
    var maxlength: Int? by refreshOnUpdate(maxlength)

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

    /**
     * The input mask options.
     */
    open var maskOptions: MaskOptions? = null
        set(value) {
            if (field != null) {
                uninstallMask()
            }
            field = value
            installMask()
            refreshState()
        }

    /**
     * The input mask controller.
     */
    protected var mask: Mask? = null

    init {
        useSnabbdomDistinctKey()
        this.setInternalEventListener<AbstractTextInput> {
            input = {
                self.changeValue()
            }
        }
    }

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
        installMask()
        refreshState()
    }

    override fun afterDestroy() {
        uninstallMask()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        if (mask == null) {
            val v = getElementD()?.value?.unsafeCast<String>()
            if (v != value && !(v.isNullOrEmpty() && value == null)) {
                getElementD()?.value = value
            }
        } else {
            getElementD()?.value = value
            mask!!.refresh()
            val v = mask?.getValue()?.let { maskOptions?.maskNumericValue(it) ?: it }?.ifEmpty { null }
            if (this.value != v) {
                this.value = v
            }
        }
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        if (mask == null) {
            val v = getElementD()?.value?.unsafeCast<String>()
            if (v != null && v != "") {
                this.value = v
            } else {
                this.value = null
            }
        }
    }

    /**
     * Install the input mask controller.
     */
    open fun installMask() {
        if (getElement() != null && maskOptions != null) {
            if (MaskManager.factory == null) throw IllegalStateException("Input mask module has not been initialized")
            mask = MaskManager.factory!!.createMask(getElement().unsafeCast<HTMLElement>(), maskOptions!!)
            mask!!.onChange {
                val v = it?.let { maskOptions?.maskNumericValue(it) ?: it }?.ifEmpty { null }
                if (this.value != v) {
                    this.value = v
                }
            }
        }
    }

    /**
     * Uninstall the input mask controller.
     */
    open fun uninstallMask() {
        mask?.destroy()
        mask = null
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
