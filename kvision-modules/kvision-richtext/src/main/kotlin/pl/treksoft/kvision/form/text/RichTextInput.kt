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
package pl.treksoft.kvision.form.text

import com.github.snabbdom.VNode
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.utils.set
import kotlinx.browser.document

/**
 * Basic rich text component.
 *
 * @constructor
 * @param value text input value
 * @param classes a set of CSS class names
 */
open class RichTextInput(value: String? = null, classes: Set<String> = setOf()) :
    AbstractTextInput(value, classes + "form-control" + "trix-control") {
    private var trixId: String? = null

    override fun render(): VNode {
        return render("trix-editor")
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        placeholder?.let {
            sn.add("placeholder" to translate(it))
        }
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

    @Suppress("UnsafeCastFromDynamic", "ComplexMethod")
    override fun afterInsert(node: VNode) {
        if (this.disabled || this.readonly == true) {
            this.getElementJQuery()?.removeAttr("contenteditable")
        } else {
            this.getElementJQuery()?.on("trix-change") { _, _ ->
                if (trixId != null) {
                    val v = document.getElementById("trix-input-$trixId")?.let { jQuery(it).`val`() as String? }
                    value = if (v != null && v.isNotEmpty()) {
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
            val editor = this.getElement().asDynamic().editor
            if (editor != undefined) {
                value?.let {
                    editor.loadHTML(it)
                } ?: editor.loadHTML("")
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
    val richTextInput = RichTextInput(value, classes ?: className.set).apply { init?.invoke(this) }
    this.add(richTextInput)
    return richTextInput
}
