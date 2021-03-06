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
import io.kvision.core.Container
import io.kvision.jquery.invoke
import io.kvision.jquery.jQuery
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set
import kotlinx.browser.document

/**
 * Basic rich text component.
 *
 * @constructor
 * @param value text input value
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class RichTextInput(
    value: String? = null,
    classes: Set<String> = setOf(),
    init: (RichTextInput.() -> Unit)? = null
) : AbstractTextInput(value, classes + "form-control" + "trix-control") {

    private var trixId: String? = null

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("trix-editor")
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
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
    }

    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    override fun afterInsert(node: VNode) {
        if (this.disabled || this.readonly == true) {
            this.getElementJQuery()?.removeAttr("contenteditable")
        } else {
            this.getElementJQuery()?.on("trix-change") { _, _ ->
                if (trixId != null) {
                    val v = document.getElementById("trix-input-$trixId")?.let { jQuery(it).`val`() as String? }
                    value = if (v != null && v != "") {
                        v
                    } else {
                        null
                    }
                    val event = org.w3c.dom.events.Event("change")
                    this.getElement()?.dispatchEvent(event)
                }
            }
        }
        this.getElementJQuery()?.on("trix-initialize") { _, _ ->
            trixId = this.getElementJQuery()?.attr("trix-id")
            if (trixId != null) {
                value?.let {
                    if (this.getElement().asDynamic().editor != undefined) {
                        this.getElement().asDynamic().editor.loadHTML(it)
                    }
                }
            }
        }
        this.getElementJQuery()?.on("trix-file-accept") { e, _ -> e.preventDefault() }
    }

    override fun afterDestroy() {
        document.getElementById("trix-input-$trixId")?.let { jQuery(it).remove() }
        document.getElementById("trix-toolbar-$trixId")?.let { jQuery(it).remove() }
        trixId = null
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun refreshState() {
        val v = document.getElementById("trix-input-$trixId")?.let { jQuery(it).`val`() as String? }
        if (value != v) {
            val element = this.getElement()
            if (element != null) {
                val editor = element.asDynamic().editor
                if (editor != undefined) {
                    value?.let {
                        editor.loadHTML(it)
                    } ?: editor.loadHTML("")
                }
            }
        }
    }

    override fun changeValue() {
        // disabled parent class functionality
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.richTextInput(
    value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (RichTextInput.() -> Unit)? = null
): RichTextInput {
    val richTextInput = RichTextInput(value, classes ?: className.set, init)
    this.add(richTextInput)
    return richTextInput
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.richTextInput(
    state: ObservableState<S>,
    value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (RichTextInput.(S) -> Unit)
) = richTextInput(value, classes, className).bind(state, true, init)
