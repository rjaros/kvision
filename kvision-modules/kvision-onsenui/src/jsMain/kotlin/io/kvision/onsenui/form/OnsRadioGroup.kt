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
package io.kvision.onsenui.form

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.form.FieldLabel
import io.kvision.form.InputSize
import io.kvision.form.InvalidFeedback
import io.kvision.form.StringFormControl
import io.kvision.form.ValidationStatus
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.obj

/**
 * The form field component rendered as a group of OnsenUI radio buttons with the same name attribute.
 *
 * The radio group can be populated directly from *options* parameter or manually by adding
 * [OnsRadio] components to the container.
 *
 * @constructor
 * @param options an optional list of options (label to value pairs) for the group
 * @param value selected option
 * @param name the name attribute of the generated HTML input element
 * @param label label text of the options group
 * @param rich determines if [label] can contain HTML code
 */
open class OnsRadioGroup(
    options: List<StringPair>? = null,
    value: String? = null,
    name: String? = null,
    label: String? = null,
    rich: Boolean = false,
    className: String? = null,
    init: (OnsRadioGroup.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "form-group kv-mb-3 kv-ons-form-group"), StringFormControl,
    MutableState<String?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * A list of options (label to value pairs) for the group.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * A value of the selected option.
     */
    override var value by refreshOnUpdate(value) {
        setValueToChildren(it)
        observers.forEach { ob -> ob(it) }
        @Suppress("UnsafeCastFromDynamic")
        dispatchEvent("change", obj { detail = obj { data = it } })
    }

    override var disabled
        get() = getDisabledFromChildren()
        set(value) {
            setDisabledToChildren(value)
        }

    /**
     * The label text of the options group.
     */
    var label
        get() = flabel.content
        set(value) {
            flabel.content = value
        }

    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }

    override var name: String?
        get() = getNameFromChildren()
        set(value) {
            setNameToChildren(value)
        }

    override var size: InputSize? by refreshOnUpdate()

    override var validationStatus
        get() = getValidationStatusFromChildren()
        set(value) {
            setValidationStatusToChildren(value)
        }

    override var validatorError: String?
        get() = super.validatorError
        set(value) {
            super.validatorError = value
            if (value != null) {
                container.addCssClass("is-invalid")
            } else {
                container.removeCssClass("is-invalid")
            }
        }

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier
        get() = getModifierFromChildren()
        set(value) {
            setModifierToChildren(value)
        }

    private val idc = "kv_ons_form_radiogroup_$counter"
    final override val input = OnsRadioInput()
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "form-label")
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    internal val container = SimplePanel("kv-radiogroup-container") {
        id = this@OnsRadioGroup.idc
    }

    init {
        this.addPrivate(flabel)
        this.addPrivate(container)
        this.addPrivate(invalidFeedback)
        setChildrenFromOptions()
        setValueToChildren(value)
        setNameToChildren(name)
        @Suppress("LeakingThis")
        init?.invoke(this)
        counter++
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("kv-text-danger")
        }
    }

    private fun setValueToChildren(value: String?) {
        val radios = container.getChildren().filterIsInstance<OnsRadio>()
        radios.forEach { it.value = false }
        radios.find {
            it.extraValue == value
        }?.value = true
    }

    private fun getDisabledFromChildren(): Boolean {
        return container.getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.disabled ?: false
    }

    private fun setDisabledToChildren(disabled: Boolean) {
        container.getChildren().filterIsInstance<OnsRadio>().forEach { it.disabled = disabled }
    }

    private fun getNameFromChildren(): String {
        return container.getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.name ?: this.idc
    }

    private fun setNameToChildren(name: String?) {
        val tname = name ?: this.idc
        container.getChildren().filterIsInstance<OnsRadio>().forEach { it.name = tname }
    }

    private fun getValidationStatusFromChildren(): ValidationStatus? {
        return container.getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.validationStatus
    }

    private fun setValidationStatusToChildren(validationStatus: ValidationStatus?) {
        container.getChildren().filterIsInstance<OnsRadio>().forEach { it.validationStatus = validationStatus }
    }

    private fun getModifierFromChildren(): String? {
        return container.getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.modifier
    }

    private fun setModifierToChildren(modifier: String?) {
        container.getChildren().filterIsInstance<OnsRadio>().forEach { it.modifier = modifier }
    }

    private fun setChildrenFromOptions() {
        val currentName = this.name
        container.disposeAll()
        options?.let {
            val tname = currentName ?: this.idc
            val c = it.map {
                OnsRadio(false, extraValue = it.first, label = it.second).apply {
                    name = tname
                    eventTarget = this@OnsRadioGroup
                    setEventListener<OnsRadio> {
                        change = { ev ->
                            this@OnsRadioGroup.value = self.extraValue
                            ev.stopPropagation()
                        }
                    }
                }
            }
            container.addAll(c)
        }
    }

    protected fun configureChild(child: Component) {
        if (child is OnsRadio) {
            child.eventTarget = this
            child.name = name
            child.setEventListener<OnsRadio> {
                change = { ev ->
                    this@OnsRadioGroup.value = self.extraValue
                    ev.stopPropagation()
                }
            }
        }
    }

    override fun add(child: Component) {
        configureChild(child)
        container.add(child)
    }

    override fun add(position: Int, child: Component) {
        configureChild(child)
        container.add(position, child)
    }

    override fun addAll(children: List<Component>) {
        singleRender {
            children.forEach { add(it) }
        }
    }

    override fun remove(child: Component) {
        container.remove(child)
    }

    override fun removeAt(position: Int) {
        container.removeAt(position)
    }

    override fun removeAll() {
        container.removeAll()
    }

    override fun disposeAll() {
        container.disposeAll()
    }

    override fun getChildren(): List<Component> {
        return container.getChildren()
    }

    override fun focus() {
        container.getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.focus()
    }

    override fun blur() {
        container.getChildren().filterIsInstance<OnsRadio>().firstOrNull()?.blur()
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

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsRadioGroup(
    options: List<StringPair>? = null,
    value: String? = null, name: String? = null,
    label: String? = null, rich: Boolean = false,
    className: String? = null,
    init: (OnsRadioGroup.() -> Unit)? = null
): OnsRadioGroup {
    val onsRadioGroup = OnsRadioGroup(options, value, name, label, rich, className, init)
    this.add(onsRadioGroup)
    return onsRadioGroup
}
