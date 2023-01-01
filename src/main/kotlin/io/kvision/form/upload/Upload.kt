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
package io.kvision.form.upload

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.InvalidFeedback
import io.kvision.form.KFilesFormControl
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.types.KFile
import io.kvision.utils.SnOn
import org.w3c.files.File

/**
 * The form field component for the file upload control.
 *
 * @constructor
 * @param multiple support multiple files selection
 * @param accept file types accepted by the input
 * @param capture capture mode
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
open class Upload(
    multiple: Boolean = false, accept: List<String>? = null, capture: Capture? = null,
    label: String? = null,
    rich: Boolean = false,
    init: (Upload.() -> Unit)? = null
) : SimplePanel("form-group kv-mb-3 kv-add-surr-invalid"), KFilesFormControl, MutableState<List<KFile>?> {

    /**
     * File input value.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }

    /**
     * Determines if multiple file upload is supported.
     */
    var multiple
        get() = input.multiple
        set(value) {
            input.multiple = value
        }

    /**
     * File types accepted by the file upload input.
     */
    var accept
        get() = input.accept
        set(value) {
            input.accept = value
        }

    /**
     * File upload input capture mode.
     */
    var capture
        get() = input.capture
        set(value) {
            input.capture = value
        }

    /**
     * Only allow directories for selection.
     */
    var webkitdirectory
        get() = input.webkitdirectory
        set(value) {
            input.webkitdirectory = value
        }

    /**
     * Determines if the file upload input is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * The label text bound to the file upload input element.
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

    protected val idc = "kv_form_upload_$counter"
    final override val input: UploadInput =
        UploadInput(multiple, accept, capture).apply {
            this.id = this@Upload.idc
            this.name = name
        }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "form-label")
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        counter++
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("text-danger")
        }
    }

    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    override fun removeEventListener(id: Int) {
        input.removeEventListener(id)
    }

    override fun removeEventListeners() {
        input.removeEventListeners()
    }

    override fun getValueAsString(): String? {
        return input.getValueAsString()
    }

    /**
     * Returns the native JavaScript File object.
     * @param kFile KFile object
     * @return File object
     */
    override fun getNativeFile(kFile: KFile): File? {
        return input.getNativeFile(kFile)
    }

    /**
     * Clears the file input control (including the native input).
     */
    open fun clearInput() {
        input.clearInput()
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }

    override fun styleForHorizontalFormPanel(horizontalRatio: FormHorizontalRatio) {
        addCssClass("row")
        addCssClass("kv-control-horiz")
        flabel.addCssClass("col-sm-${horizontalRatio.labels}")
        flabel.addCssClass("col-form-label")
        input.addSurroundingCssClass("col-sm-${horizontalRatio.fields}")
        invalidFeedback.addCssClass("offset-sm-${horizontalRatio.labels}")
        invalidFeedback.addCssClass("col-sm-${horizontalRatio.fields}")
    }

    override fun getState(): List<KFile>? = input.getState()

    override fun subscribe(observer: (List<KFile>?) -> Unit): () -> Unit {
        return input.subscribe(observer)
    }

    override fun setState(state: List<KFile>?) {
        input.setState(state)
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
fun Container.upload(
    multiple: Boolean = false, accept: List<String>? = null, capture: Capture? = null,
    label: String? = null,
    rich: Boolean = false,
    init: (Upload.() -> Unit)? = null
): Upload {
    val upload = Upload(multiple, accept, capture, label, rich, init)
    this.add(upload)
    return upload
}

/**
 * Returns file with the content read.
 * @param kFile object identifying the file
 * @return KFile object
 */
suspend fun Upload.getFileWithContent(kFile: KFile): KFile {
    return this.input.getFileWithContent(kFile)
}
