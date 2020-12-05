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
package pl.treksoft.kvision.form.check

import com.github.snabbdom.VNode
import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.core.ClassSetBuilder
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.form.ValidationStatus
import pl.treksoft.kvision.state.ObservableState

/**
 * Type of the check input control (checkbox or radio).
 */
enum class CheckInputType(internal val type: String) {
    CHECKBOX("checkbox"),
    RADIO("radio")
}

/**
 * The basic input component rendered as HTML *input type="checkbox"* or *input type="radio"*.
 *
 * @constructor
 * @param type type of the input control
 * @param value selection state
 * @param classes a set of CSS class names
 */
abstract class CheckInput(
    type: CheckInputType = CheckInputType.CHECKBOX, value: Boolean = false,
    classes: Set<String> = setOf()
) : Widget(classes), FormInput, ObservableState<Boolean> {

    protected val observers = mutableListOf<(Boolean) -> Unit>()

    init {
        this.setInternalEventListener<CheckInput> {
            click = {
                val v = getElementJQuery()?.prop("checked") as Boolean?
                self.value = (v == true)
            }
            change = {
                val v = getElementJQuery()?.prop("checked") as Boolean?
                self.value = (v == true)
            }
        }
    }

    /**
     * The selection state of the input.
     */
    var value by refreshOnUpdate(value) { refreshState(); observers.forEach { ob -> ob(it) } }

    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the input selection state.
     */
    var startValue by refreshOnUpdate(value) { this.value = it; refresh() }

    /**
     * The type of the generated HTML input element.
     */
    var type by refreshOnUpdate(type)

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * The additional String value used for the radio button group.
     */
    var extraValue: String? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    override fun render(): VNode {
        return render("input")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
        classSetBuilder.add(size)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", type.type)
        if (startValue) {
            attributeSetBuilder.add("checked")
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
        extraValue?.let {
            attributeSetBuilder.add("value", it)
        }
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    protected open fun refreshState() {
        val v = getElementJQuery()?.prop("checked") as Boolean?
        if (this.value != v) {
            getElementJQuery()?.prop("checked", this.value)
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: CheckInput.(MouseEvent) -> Unit): CheckInput {
        this.setEventListener<CheckInput> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
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

    override fun getState(): Boolean = value

    override fun subscribe(observer: (Boolean) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }
}
