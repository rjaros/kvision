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
package pl.treksoft.kvision.form.select

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.SimplePanel

internal const val KVNULL = "#kvnull"

/**
 * Simple select component.
 *
 * @constructor
 * @param options an optional list of options (value to label pairs) for the select control
 * @param value text input value
 * @param emptyOption determines if an empty option is automatically generated
 * @param classes a set of CSS class names
 */
open class SimpleSelectInput(
    options: List<StringPair>? = null, value: String? = null, emptyOption: Boolean = false,
    classes: Set<String> = setOf()
) : SimplePanel(classes + "form-control"), FormInput {

    /**
     * A list of options (value to label pairs) for the select control.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * Text input value.
     */
    var value by refreshOnUpdate(value) { refreshState() }
    /**
     * The value of the selected child option.
     *
     * This value is placed directly in the generated HTML code, while the [value] property is dynamically
     * bound to the select component.
     */
    var startValue by refreshOnUpdate(value) { this.value = it; selectOption() }
    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()
    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)
    /**
     * Determines if the text input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()
    /**
     * Determines if an empty option is automatically generated.
     */
    var emptyOption by refreshOnUpdate(emptyOption) { setChildrenFromOptions() }
    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    init {
        this.vnkey = "kv_simpleselectinput_${counter++}"
        setChildrenFromOptions()
        this.setInternalEventListener<SimpleSelectInput> {
            change = {
                self.changeValue()
            }
        }
    }

    override fun render(): VNode {
        return render("select", childrenVNodes())
    }

    private fun setChildrenFromOptions() {
        super.removeAll()
        if (emptyOption) {
            super.add(Tag(TAG.OPTION, "", attributes = mapOf("value" to KVNULL)))
        }
        options?.let {
            val c = it.map {
                val attributes = if (it.first == value) {
                    mapOf("value" to it.first, "selected" to "selected")
                } else {
                    mapOf("value" to it.first)
                }
                Tag(TAG.OPTION, it.second, attributes = attributes)
            }
            super.addAll(c)
        }
    }

    private fun selectOption() {
        children.forEach { child ->
            if (child is Tag && child.type == TAG.OPTION) {
                if (value != null && child.getAttribute("value") == value) {
                    child.setAttribute("selected", "selected")
                } else {
                    child.removeAttribute("selected")
                }
            }
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        name?.let {
            sn.add("name" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        if (disabled) {
            sn.add("disabled" to "disabled")
        }
        return sn
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun refreshState() {
        value?.let {
            getElementJQuery()?.`val`(it)
        } ?: getElementJQueryD()?.`val`(null)
    }

    /**
     * @suppress
     * Internal function
     */
    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v.isNotEmpty() && v != KVNULL) {
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

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.simpleSelectInput(
            options: List<StringPair>? = null, value: String? = null, emptyOption: Boolean = false,
            classes: Set<String> = setOf(), init: (SimpleSelectInput.() -> Unit)? = null
        ): SimpleSelectInput {
            val simpleSelectInput = SimpleSelectInput(options, value, emptyOption, classes).apply { init?.invoke(this) }
            this.add(simpleSelectInput)
            return simpleSelectInput
        }
    }
}
