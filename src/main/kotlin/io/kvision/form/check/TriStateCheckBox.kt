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
package io.kvision.form.check

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.FieldLabelCheck
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.InvalidFeedback
import io.kvision.form.TriStateFormControl
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import io.kvision.utils.SnOn
import org.w3c.dom.events.MouseEvent

/**
 * The form field component rendered as HTML *input type="checkbox"* with tri-state value.
 *
 * @constructor
 * @param value selection state
 * @param name the name of the input element
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class TriStateCheckBox(
    value: Boolean? = null, name: String? = null, label: String? = null,
    rich: Boolean = false,
    init: (TriStateCheckBox.() -> Unit)? = null
) : SimplePanel("form-check"), TriStateFormControl, MutableState<Boolean?> {

    protected val observers = mutableListOf<(Boolean?) -> Unit>()

    /**
     * The selection state of the checkbox.
     */
    override var value: Boolean?
        get() {
            return if (input.indeterminate) null else input.value
        }
        set(value) {
            if (value == null) {
                input.indeterminate = true
                input.value = false
            } else {
                input.value = value
                input.indeterminate = false
            }
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
     * The label text bound to the input element.
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

    /**
     * The style (one of Bootstrap standard colors) of the input.
     */
    var style
        get() = input.style
        set(value) {
            input.style = value
        }

    /**
     * Determines if the checkbox is rendered as a circle.
     */
    var circled
        get() = input.circled
        set(value) {
            input.circled = value
        }

    /**
     * Determines if the checkbox is rendered inline.
     */
    var inline by refreshOnUpdate(false)

    /**
     * Render as a switch.
     */
    var switch by refreshOnUpdate(false)

    /**
     * Render checkbox on the opposite side.
     */
    var reversed by refreshOnUpdate(false)

    /**
     * Render label as first child.
     */
    var labelFirst by refreshOnUpdate(false)

    private val idc = "kv_form_tristatecheckbox_$counter"
    final override val input: TriStateCheckBoxInput =
        TriStateCheckBoxInput(value ?: false).apply {
            this.id = this@TriStateCheckBox.idc
            this.name = name
            this.indeterminate = value == null
        }
    final override val flabel: FieldLabel = FieldLabelCheck(idc, label, rich, className = "form-check-label") {
        span()
    }
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        counter++
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        val childrenList = if (labelFirst)
            listOf(flabel, input, invalidFeedback)
        else
            listOf(input, flabel, invalidFeedback)
        val children = childrenList.mapNotNull { if (it.visible) it.renderVNode() else null }.toTypedArray()
        return render("div", children)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    override fun removeEventListener(id: Int): Widget {
        input.removeEventListener(id)
        return this
    }

    override fun removeEventListeners(): Widget {
        input.removeEventListeners()
        return this
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (switch) {
            classSetBuilder.add("form-switch")
        }
        if (inline) {
            classSetBuilder.add("form-check-inline")
        }
        if (reversed) {
            classSetBuilder.add("form-check-reverse")
        }
        if (validatorError != null) {
            classSetBuilder.add("text-danger")
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: TriStateCheckBox.(MouseEvent) -> Unit): TriStateCheckBox {
        this.setEventListener<TriStateCheckBox> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }

    override fun styleForHorizontalFormPanel(horizontalRatio: FormHorizontalRatio) {
        if (labelFirst) {
            super.styleForHorizontalFormPanel(horizontalRatio)
            addCssClass("form-group")
            addCssClass("kv-mb-3")
            removeCssClass("form-check")
        } else {
            addCssClass("form-group")
            addCssClass("kv-mb-3")
            addSurroundingCssClass("row")
            addCssClass("offset-sm-${horizontalRatio.labels}")
            addCssClass("col-sm-${horizontalRatio.fields}")
        }
    }

    override fun styleForInlineFormPanel() {
        addCssClass("form-group")
    }

    override fun styleForVerticalFormPanel() {
        addCssClass("form-group")
        addCssClass("kv-mb-3")
    }

    override fun dispose() {
        super.dispose()
        input.dispose()
        flabel.dispose()
        invalidFeedback.dispose()
    }

    override fun getState(): Boolean? {
        return value
    }

    override fun subscribe(observer: (Boolean?) -> Unit): () -> Unit {
        return input.subscribe {
            if (!input.indeterminate || !input.value) {
                observer(this.value)
            }
        }
    }

    override fun setState(state: Boolean?) {
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
fun Container.triStateCheckBox(
    value: Boolean? = null, name: String? = null, label: String? = null,
    rich: Boolean = false, init: (TriStateCheckBox.() -> Unit)? = null
): TriStateCheckBox {
    val triStateCheckBox = TriStateCheckBox(value, name, label, rich, init)
    this.add(triStateCheckBox)
    return triStateCheckBox
}
