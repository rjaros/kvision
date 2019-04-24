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
package pl.treksoft.kvision.form.upload

import com.github.snabbdom.VNode
import org.w3c.files.File
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.Form
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.form.FormPanel
import pl.treksoft.kvision.form.InputSize
import pl.treksoft.kvision.i18n.I18n
import pl.treksoft.kvision.types.KFile
import pl.treksoft.kvision.utils.getContent
import pl.treksoft.kvision.utils.obj
import kotlin.reflect.KProperty1

/**
 * The file upload component.
 *
 * @constructor
 * @param uploadUrl the optional URL for the upload processing action
 * @param multiple determines if multiple file upload is supported
 * @param classes a set of CSS class names
 */
@Suppress("TooManyFunctions")
open class UploadInput(uploadUrl: String? = null, multiple: Boolean = false, classes: Set<String> = setOf()) :
    Widget(classes + "form-control"), FormInput {

    /**
     * File input value.
     */
    var value: List<KFile>?
        get() = getValue()
        set(value) {
            if (value == null) resetInput()
        }

    /**
     * The optional URL for the upload processing action.
     * If not set the upload button action will default to form submission.
     */
    var uploadUrl: String? by refreshOnUpdate(uploadUrl) { refreshUploadInput() }
    /**
     * Determines if multiple file upload is supported.
     */
    var multiple: Boolean by refreshOnUpdate(multiple) { refresh(); refreshUploadInput() }
    /**
     * The extra data that will be passed as data to the AJAX server call via POST.
     */
    var uploadExtraData: ((String, Int) -> dynamic)? by refreshOnUpdate { refreshUploadInput() }
    /**
     * Determines if the explorer theme is used.
     */
    var explorerTheme: Boolean by refreshOnUpdate(false) { refreshUploadInput() }
    /**
     * Determines if the input selection is required.
     */
    var required: Boolean by refreshOnUpdate(false) { refreshUploadInput() }
    /**
     * Determines if the caption is shown.
     */
    var showCaption: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the preview is shown.
     */
    var showPreview: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the remove button is shown.
     */
    var showRemove: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the upload button is shown.
     */
    var showUpload: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the cancel button is shown.
     */
    var showCancel: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the file browse button is shown.
     */
    var showBrowse: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the click on the preview zone opens file browse window.
     */
    var browseOnZoneClick: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * Determines if the iconic preview is prefered.
     */
    var preferIconicPreview: Boolean by refreshOnUpdate(false) { refreshUploadInput() }
    /**
     * Allowed file types.
     */
    var allowedFileTypes: Set<String>? by refreshOnUpdate { refreshUploadInput() }
    /**
     * Allowed file extensions.
     */
    var allowedFileExtensions: Set<String>? by refreshOnUpdate { refreshUploadInput() }
    /**
     * Determines if Drag&Drop zone is enabled.
     */
    var dropZoneEnabled: Boolean by refreshOnUpdate(true) { refreshUploadInput() }
    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()
    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false) { refresh(); refreshUploadInput() }
    /**
     * The size of the input (currently not working)
     */
    override var size: InputSize? by refreshOnUpdate()

    private val nativeFiles: MutableMap<KFile, File> = mutableMapOf()

    init {
        this.vnkey = "kv_uploadinput_${counter++}"
    }

    override fun render(): VNode {
        return render("input")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to "file")
        name?.let {
            sn.add("name" to it)
        }
        if (multiple) {
            sn.add("multiple" to "true")
        }
        if (disabled) {
            sn.add("disabled" to "disabled")
        }
        return sn
    }

    private fun getValue(): List<KFile>? {
        val v = getFiles()
        return if (v.isNotEmpty()) v else null
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        getElementJQueryD()?.fileinput(getSettingsObj())
        if (uploadUrl != null) {
            this.getElementJQuery()?.on("fileselect") { e, _ ->
                this.dispatchEvent("fileSelectUpload", obj { detail = e })
            }
            this.getElementJQuery()?.on("fileclear") { e, _ ->
                this.dispatchEvent("fileClearUpload", obj { detail = e })
            }
            this.getElementJQuery()?.on("filereset") { e, _ ->
                this.dispatchEvent("fileResetUpload", obj { detail = e })
            }
            this.getElementJQuery()?.on("filebrowse") { e, _ ->
                this.dispatchEvent("fileBrowseUpload", obj { detail = e })
            }
            this.getElementJQueryD()?.on("filepreupload") lambda@{ _, data, previewId, index ->
                data["previewId"] = previewId
                data["index"] = index
                this.dispatchEvent("filePreUpload", obj { detail = data })
                return@lambda null
            }
        }
    }

    override fun afterDestroy() {
        getElementJQueryD()?.fileinput("destroy")
    }

    private fun refreshUploadInput() {
        getElementJQueryD()?.fileinput("refresh", getSettingsObj())
    }

    /**
     * Resets the file input control.
     */
    open fun resetInput() {
        getElementJQueryD()?.fileinput("reset")
    }

    /**
     * Clears the file input control (including the native input).
     */
    open fun clearInput() {
        getElementJQueryD()?.fileinput("clear")
    }

    /**
     * Trigger ajax upload (only for ajax mode).
     */
    open fun upload() {
        getElementJQueryD()?.fileinput("upload")
    }

    /**
     * Cancel an ongoing ajax upload (only for ajax mode).
     */
    open fun cancel() {
        getElementJQueryD()?.fileinput("cancel")
    }

    /**
     * Locks the file input (disabling all buttons except a cancel button).
     */
    open fun lock() {
        getElementJQueryD()?.fileinput("lock")
    }

    /**
     * Unlocks the file input.
     */
    open fun unlock() {
        getElementJQueryD()?.fileinput("unlock")
    }

    /**
     * Returns the native JavaScript File object.
     * @param kFile KFile object
     * @return File object
     */
    fun getNativeFile(kFile: KFile): File? {
        return nativeFiles[kFile]
    }

    private fun getFiles(): List<KFile> {
        nativeFiles.clear()
        return (getElementJQueryD()?.fileinput("getFileStack") as? Array<File>)?.toList()?.map {
            val kFile = KFile(it.name, it.size, null)
            nativeFiles[kFile] = it
            kFile
        } ?: listOf()
    }

    /**
     * Returns the value of the file input control as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.joinToString(",") { it.name }
    }

    /**
     * Makes the input element focused.
     */
    open fun focus() {
        getElementJQuery()?.focus()
    }

    /**
     * Makes the input element blur.
     */
    open fun blur() {
        getElementJQuery()?.blur()
    }

    private fun getSettingsObj(): dynamic {
        val language = I18n.language
        return obj {
            this.uploadUrl = uploadUrl
            this.uploadExtraData = uploadExtraData ?: undefined
            this.theme = if (explorerTheme) "explorer-fa" else null
            this.required = required
            this.showCaption = showCaption
            this.showPreview = showPreview
            this.showRemove = showRemove
            this.showUpload = showUpload
            this.showCancel = showCancel
            this.showBrowse = showBrowse
            this.browseOnZoneClick = browseOnZoneClick
            this.preferIconicPreview = preferIconicPreview
            this.allowedFileTypes = allowedFileTypes?.toTypedArray()
            this.allowedFileExtensions = allowedFileExtensions?.toTypedArray()
            this.dropZoneEnabled = dropZoneEnabled
            this.fileActionSettings = obj {
                this.showUpload = showUpload
                this.showRemove = showRemove
            }
            this.language = language
        }
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.uploadInput(
            uploadUrl: String? = null,
            multiple: Boolean = false,
            classes: Set<String> = setOf(),
            init: (UploadInput.() -> Unit)? = null
        ): UploadInput {
            val uploadInput = UploadInput(uploadUrl, multiple, classes).apply {
                init?.invoke(
                    this
                )
            }
            this.add(uploadInput)
            return uploadInput
        }

        /**
         * Returns file with the content read.
         * @param key key identifier of the control
         * @param kFile object identifying the file
         * @return KFile object
         */
        suspend fun <K : Any> Form<K>.getContent(
            key: KProperty1<K, List<KFile>?>,
            kFile: KFile
        ): KFile {
            val control = getControl(key) as Upload
            val content = control.getNativeFile(kFile)?.getContent()
            return kFile.copy(content = content)
        }


        /**
         * Returns file with the content read.
         * @param key key identifier of the control
         * @param kFile object identifying the file
         * @return KFile object
         */
        suspend fun <K : Any> FormPanel<K>.getContent(
            key: KProperty1<K, List<KFile>?>,
            kFile: KFile
        ): KFile {
            val control = getControl(key) as Upload
            val content = control.getNativeFile(kFile)?.getContent()
            return kFile.copy(content = content)
        }
    }
}
