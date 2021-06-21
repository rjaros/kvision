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
import io.kvision.form.InvalidFeedback
import io.kvision.form.KFilesFormControl
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.types.KFile
import io.kvision.utils.SnOn
import org.w3c.files.File

/**
 * The form field file upload component.
 *
 * @constructor
 * @param uploadUrl the optional URL for the upload processing action
 * @param multiple determines if multiple file upload is supported
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Upload(
    uploadUrl: String? = null, multiple: Boolean = false, label: String? = null,
    rich: Boolean = false, init: (Upload.() -> Unit)? = null
) : SimplePanel("form-group"), KFilesFormControl, MutableState<List<KFile>?> {

    protected val observers = mutableListOf<(String?) -> Unit>()

    /**
     * File input value.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }

    /**
     * The optional URL for the upload processing action.
     * If not set the upload button action will default to form submission.
     */
    var uploadUrl
        get() = input.uploadUrl
        set(value) {
            input.uploadUrl = value
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
     * The extra data that will be passed as data to the AJAX server call via POST.
     */
    var uploadExtraData
        get() = input.uploadExtraData
        set(value) {
            input.uploadExtraData = value
        }

    /**
     * Determines if the explorer theme is used.
     */
    var explorerTheme
        get() = input.explorerTheme
        set(value) {
            input.explorerTheme = value
        }

    /**
     * Determines if the input selection is required.
     */
    var required
        get() = input.required
        set(value) {
            input.required = value
        }

    /**
     * Determines if the caption is shown.
     */
    var showCaption
        get() = input.showCaption
        set(value) {
            input.showCaption = value
        }

    /**
     * Determines if the preview is shown.
     */
    var showPreview
        get() = input.showPreview
        set(value) {
            input.showPreview = value
        }

    /**
     * Determines if the remove button is shown.
     */
    var showRemove
        get() = input.showRemove
        set(value) {
            input.showRemove = value
        }

    /**
     * Determines if the upload button is shown.
     */
    var showUpload
        get() = input.showUpload
        set(value) {
            input.showUpload = value
        }

    /**
     * Determines if the cancel button is shown.
     */
    var showCancel
        get() = input.showCancel
        set(value) {
            input.showCancel = value
        }

    /**
     * Determines if the file browse button is shown.
     */
    var showBrowse
        get() = input.showBrowse
        set(value) {
            input.showBrowse = value
        }

    /**
     * Determines if the click on the preview zone opens file browse window.
     */
    var browseOnZoneClick
        get() = input.browseOnZoneClick
        set(value) {
            input.browseOnZoneClick = value
        }

    /**
     * Determines if the iconic preview is prefered.
     */
    var preferIconicPreview
        get() = input.preferIconicPreview
        set(value) {
            input.preferIconicPreview = value
        }

    /**
     * Allowed file types.
     */
    var allowedFileTypes
        get() = input.allowedFileTypes
        set(value) {
            input.allowedFileTypes = value
        }

    /**
     * Allowed file extensions.
     */
    var allowedFileExtensions
        get() = input.allowedFileExtensions
        set(value) {
            input.allowedFileExtensions = value
        }

    /**
     * Determines if Drag&Drop zone is enabled.
     */
    var dropZoneEnabled
        get() = input.dropZoneEnabled
        set(value) {
            input.dropZoneEnabled = value
        }

    /**
     * The placeholder for the upload control.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }

    /**
     * The label text bound to the spinner input element.
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
    final override val input: UploadInput = UploadInput(uploadUrl, multiple)
        .apply {
            this.id = this@Upload.idc
            this.name = name
        }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "control-label")
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
     * Resets the file input control.
     */
    open fun resetInput() {
        input.resetInput()
    }

    /**
     * Clears the file input control (including the native input).
     */
    open fun clearInput() {
        input.clearInput()
    }

    /**
     * Trigger ajax upload (only for ajax mode).
     */
    open fun upload() {
        input.upload()
    }

    /**
     * Cancel an ongoing ajax upload (only for ajax mode).
     */
    open fun cancel() {
        input.cancel()
    }

    /**
     * Locks the file input (disabling all buttons except a cancel button).
     */
    open fun lock() {
        input.lock()
    }

    /**
     * Unlocks the file input.
     */
    open fun unlock() {
        input.unlock()
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
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
    uploadUrl: String? = null,
    multiple: Boolean = false,
    label: String? = null,
    rich: Boolean = false,
    init: (Upload.() -> Unit)? = null
): Upload {
    val upload = Upload(uploadUrl, multiple, label, rich, init)
    this.add(upload)
    return upload
}
