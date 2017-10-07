package pl.treksoft.kvision.modal

import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

open class Alert(caption: String? = null, text: String? = null, rich: Boolean = false,
                 align: ALIGN? = null, size: MODALSIZE? = null, animation: Boolean = true,
                 private val callback: (() -> Unit)? = null) : Modal(caption, true, size, animation) {
    var text
        get() = content.text
        set(value) {
            content.text = value
        }
    var rich
        get() = content.rich
        set(value) {
            content.rich = value
        }
    var align
        get() = content.align
        set(value) {
            content.align = value
        }

    private val content = Tag(TAG.SPAN, text, rich, align)

    init {
        body.add(content)
        val okButton = Button("OK", "ok", BUTTONSTYLE.PRIMARY)
        okButton.setEventListener {
            click = {
                hide()
            }
        }
        this.addButton(okButton)
    }

    override fun hide(): Widget {
        super.hide()
        this.callback?.invoke()
        return this
    }

    companion object {
        @Suppress("LongParameterList")
        fun show(caption: String? = null, text: String? = null, rich: Boolean = false,
                 align: ALIGN? = null, size: MODALSIZE? = null, animation: Boolean = true,
                 callback: (() -> Unit)? = null) {
            Alert(caption, text, rich, align, size, animation, callback).show()
        }
    }
}
