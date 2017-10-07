package pl.treksoft.kvision.modal

import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

open class Confirm(caption: String? = null, text: String? = null, rich: Boolean = false,
                   align: ALIGN? = null, size: MODALSIZE? = null, animation: Boolean = true,
                   cancelVisible: Boolean = false,
                   private val noCallback: (() -> Unit)? = null,
                   private val yesCallback: (() -> Unit)? = null) : Modal(caption, false, size, animation, false) {
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
    private var cancelVisible = cancelVisible
        set(value) {
            field = value
            refreshCancelButton()
        }

    private val content = Tag(TAG.SPAN, text, rich, align)
    private val cancelButton = Button("Cancel", "remove")

    init {
        body.add(content)
        cancelButton.setEventListener {
            click = {
                hide()
            }
        }
        this.addButton(cancelButton)
        val noButton = Button("No", "ban-circle")
        noButton.setEventListener {
            click = {
                hide()
                noCallback?.invoke()
            }
        }
        this.addButton(noButton)
        val yesButton = Button("Yes", "ok", BUTTONSTYLE.PRIMARY)
        yesButton.setEventListener {
            click = {
                hide()
                yesCallback?.invoke()
            }
        }
        this.addButton(yesButton)
        refreshCancelButton()
    }

    private fun refreshCancelButton() {
        if (cancelVisible) {
            cancelButton.show()
            closeIcon.show()
        } else {
            cancelButton.hide()
            closeIcon.hide()
        }
    }

    companion object {
        @Suppress("LongParameterList")
        fun show(caption: String? = null, text: String? = null, rich: Boolean = false,
                 align: ALIGN? = null, size: MODALSIZE? = null, animation: Boolean = true,
                 cancelVisible: Boolean = false,
                 noCallback: (() -> Unit)? = null, yesCallback: (() -> Unit)? = null) {
            Confirm(caption, text, rich, align, size, animation, cancelVisible, noCallback, yesCallback).show()
        }
    }
}
