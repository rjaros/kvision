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
import io.kvision.snabbdom.h
import io.kvision.RichTextModule
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import kotlinx.browser.document
import org.w3c.dom.Element

/**
 * Basic rich text component.
 *
 * @constructor
 * @param value text input value
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class RichTextInput(
    value: String? = null,
    className: String? = null,
    init: (RichTextInput.() -> Unit)? = null
) : AbstractTextInput(value, (className?.let { "$it " } ?: "") + "form-control trix-control") {

    override var maskOptions: MaskOptions? = null
        set(value) {
            field = value
            throw IllegalStateException("RichTextInput component doesn't support mask options")
        }

    private var trixId: String? = null

    init {
        useSnabbdomDistinctKey()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return h("span", getSnOptContents(), arrayOf(render("trix-editor")))
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
            this.getElement()?.unsafeCast<Element>()?.removeAttribute("contenteditable")
        } else {
            this.getElement()?.addEventListener("trix-change", { _ ->
                if (trixId != null) {
                    val v = document.getElementById("trix-input-$trixId")?.let { it.asDynamic().value }
                    value = if (v != null && v != "") {
                        v
                    } else {
                        null
                    }
                    val event = org.w3c.dom.events.Event("change")
                    this.getElement()?.dispatchEvent(event)
                }
            })
        }
        this.getElement()?.addEventListener("trix-initialize", { _ ->
            trixId = this.getElement()?.unsafeCast<Element>()?.getAttribute("trix-id")
            if (trixId != null) {
                value?.let {
                    if (this.getElementD().editor != undefined) {
                        this.getElementD().editor.loadHTML(it)
                    }
                }
            }
        })
        this.getElement()?.addEventListener("trix-file-accept", { e -> e.preventDefault() })
    }

    override fun afterDestroy() {
        document.getElementById("trix-input-$trixId")?.let { it.parentNode?.removeChild(it) }
        document.getElementById("trix-toolbar-$trixId")?.let { it.parentNode?.removeChild(it) }
        trixId = null
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun refreshState() {
        val v = document.getElementById("trix-input-$trixId")?.let { it.asDynamic().value }
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

    companion object {
        init {
            RichTextModule.initialize()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.richTextInput(
    value: String? = null,
    className: String? = null,
    init: (RichTextInput.() -> Unit)? = null
): RichTextInput {
    val richTextInput = RichTextInput(value, className, init)
    this.add(richTextInput)
    return richTextInput
}
