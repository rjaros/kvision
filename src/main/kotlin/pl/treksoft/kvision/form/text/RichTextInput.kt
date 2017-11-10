package pl.treksoft.kvision.form.text

import com.github.snabbdom.VNode
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.snabbdom.StringPair
import kotlin.browser.document

open class RichTextInput(value: String? = null, classes: Set<String> = setOf()) :
        AbstractTextInput(value, classes + "form-control" + "trix-control") {

    private var trixId: String? = null

    override fun render(): VNode {
        return kvh("trix-editor")
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        placeholder?.let {
            sn.add("placeholder" to it)
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
            sn.add("disabled" to "true")
        }
        return sn
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        if (this.disabled || this.readonly == true) {
            this.getElementJQuery()?.removeAttr("contenteditable")
        } else {
            this.getElementJQuery()?.on("trix-change", { _, _ ->
                if (trixId != null) {
                    val v = document.getElementById("trix-input-" + trixId)?.let { jQuery(it).`val`() as String? }
                    value = if (v != null && v.isNotEmpty()) {
                        v
                    } else {
                        null
                    }
                    val event = org.w3c.dom.events.Event("change")
                    this.getElement()?.dispatchEvent(event)
                }
            })
        }
        this.getElementJQuery()?.on("trix-initialize", { _, _ ->
            trixId = this.getElementJQuery()?.attr("trix-id")
            value?.let {
                this.getElement().asDynamic().editor.loadHTML(it)
            }
        })
        this.getElementJQuery()?.on("trix-file-accept", { e, _ -> e.preventDefault() })
    }

    override fun afterDestroy() {
        document.getElementById("trix-input-" + trixId)?.remove()
        document.getElementById("trix-toolbar-" + trixId)?.remove()
        trixId = null
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun refreshState() {
        val v = document.getElementById("trix-input-" + trixId)?.let { jQuery(it).`val`() as String? }
        if (value != v) {
            val editor = this.getElement().asDynamic().editor
            value?.let {
                editor.loadHTML(it)
            } ?: editor.loadHTML("")
        }
    }

    override fun changeValue() {
        // disabled parent class functionality
    }
}
