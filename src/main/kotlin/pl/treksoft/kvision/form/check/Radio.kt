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

import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.BoolFormControl
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.HelpBlock
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.SnOn

/**
 * Radio style options.
 */
enum class RADIOSTYLE(internal val className: String) {
    DEFAULT("radio-default"),
    PRIMARY("radio-primary"),
    SUCCESS("radio-success"),
    INFO("radio-info"),
    WARNING("radio-warning"),
    DANGER("radio-danger"),
}

/**
 * The form field component rendered as HTML *input type="radio"*.
 *
 * @constructor
 * @param value selection state
 * @param extraValue the additional String value used for the radio button group
 * @param name the name attribute of the generated HTML input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 */
open class Radio(
    value: Boolean = false, extraValue: String? = null, name: String? = null, label: String? = null,
    rich: Boolean = false
) : SimplePanel(), BoolFormControl {

    /**
     * The selection state of the radio button.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }
    /**
     * The value attribute of the generated HTML input element.
     *
     * This value is placed directly in generated HTML code, while the [value] property is dynamically
     * bound to the input selection state.
     */
    var startValue
        get() = input.startValue
        set(value) {
            input.startValue = value
        }
    /**
     * The additional String value used for the radio button group.
     */
    var extraValue
        get() = input.extraValue
        set(value) {
            input.extraValue = value
        }
    /**
     * The name attribute of the generated HTML input element.
     */
    var name
        get() = input.name
        set(value) {
            input.name = value
        }
    override var disabled
        get() = input.disabled
        set(value) {
            input.disabled = value
        }
    /**
     * The label text bound to the input element.
     */
    var label
        get() = flabel.text
        set(value) {
            flabel.text = value
        }
    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }
    /**
     * The style (one of Bootstrap standard colors) of the input.
     */
    var style: RADIOSTYLE? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the radio button is rendered as a square.
     */
    var squared: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the radio button is rendered inline.
     */
    var inline: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    /**
     * The size of the input.
     */
    override var size
        get() = input.size
        set(value) {
            input.size = value
        }

    private val idc = "kv_form_radio_" + counter
    final override val input: CheckInput = CheckInput(CHECKINPUTTYPE.RADIO, value).apply {
        this.id = idc
        this.extraValue = extraValue
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, classes = setOf())
    final override val validationInfo: HelpBlock = HelpBlock().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this.eventTarget ?: this
        this.addInternal(input)
        this.addInternal(flabel)
        this.addInternal(validationInfo)
        counter++
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun setEventListener(block: SnOn<Widget>.() -> Unit): Widget {
        input.setEventListener(block)
        return this
    }

    override fun removeEventListeners(): Widget {
        input.removeEventListeners()
        return this
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (!squared) {
            cl.add("radio" to true)
            style?.let {
                cl.add(it.className to true)
            }
            if (inline) {
                cl.add("radio-inline" to true)
            }
        } else {
            cl.add("checkbox" to true)
            cl.add("kv-radio-checkbox" to true)
            style?.let {
                cl.add(it.className.replace("radio", "checkbox") to true)
            }
            if (inline) {
                cl.add("checkbox-inline" to true)
            }
        }
        if (validatorError != null) {
            cl.add("has-error" to true)
        }
        return cl
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: Radio.(MouseEvent) -> Unit): Radio {
        this.setEventListener<Radio> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.radio(
            value: Boolean = false, extraValue: String? = null, name: String? = null, label: String? = null,
            rich: Boolean = false, init: (Radio.() -> Unit)? = null
        ) {
            this.add(Radio(value, extraValue, name, label, rich).apply { init?.invoke(this) })
        }
    }
}
