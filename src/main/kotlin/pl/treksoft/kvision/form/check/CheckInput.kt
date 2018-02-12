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
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.INPUTSIZE

/**
 * Type of the check input control (checkbox or radio).
 */
enum class CHECKINPUTTYPE(internal val type: String) {
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
open class CheckInput(
    type: CHECKINPUTTYPE = CHECKINPUTTYPE.CHECKBOX, value: Boolean = false,
    classes: Set<String> = setOf()
) : Widget(classes) {

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
    var value: Boolean = value
        set(value) {
            field = value
            refreshState()
        }
    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the input selection state.
     */
    var startValue: Boolean = value
        set(value) {
            field = value
            this.value = value
            refresh()
        }
    /**
     * The type of the generated HTML input element.
     */
    var type: CHECKINPUTTYPE = type
        set(value) {
            field = value
            refresh()
        }
    /**
     * The name attribute of the generated HTML input element.
     */
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    /**
     * The additional String value used for the radio button group.
     */
    var extraValue: String? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * The size of the input.
     */
    var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return render("input")
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
        sn.add("type" to type.type)
        if (startValue) {
            sn.add("checked" to "true")
        }
        name?.let {
            sn.add("name" to it)
        }
        if (disabled) {
            sn.add("disabled" to "true")
        }
        extraValue?.let {
            sn.add("value" to it)
        }
        return sn
    }

    override fun afterInsert(node: VNode) {
        refreshState()
    }

    override fun afterPostpatch(node: VNode) {
        refreshState()
    }

    private fun refreshState() {
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

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.checkInput(
            type: CHECKINPUTTYPE = CHECKINPUTTYPE.CHECKBOX, value: Boolean = false,
            classes: Set<String> = setOf(), init: (CheckInput.() -> Unit)? = null
        ): CheckInput {
            val checkInput = CheckInput(type, value, classes).apply { init?.invoke(this) }
            this.add(checkInput)
            return checkInput
        }
    }
}
