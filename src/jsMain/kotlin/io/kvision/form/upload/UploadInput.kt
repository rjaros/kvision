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

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.DomAttribute
import io.kvision.core.Widget
import io.kvision.form.FormInput
import io.kvision.form.GenericFormComponent
import io.kvision.form.InputSize
import io.kvision.form.ValidationStatus
import io.kvision.snabbdom.VNode
import io.kvision.state.MutableState
import io.kvision.types.KFile
import io.kvision.utils.getContent
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.files.File

/**
 * File upload input capture mode values
 */
enum class Capture(override val attributeValue: String) : DomAttribute {
    USER("user"),
    ENVIRONMENT("environment"),
    DEFAULT("capture"),
    ;

    override val attributeName: String
        get() = "capture"
}

/**
 * File upload input component.
 *
 * @constructor
 * @param multiple support multiple files selection
 * @param accept file types accepted by the input
 * @param capture capture mode
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class UploadInput(
    multiple: Boolean = false, accept: List<String>? = null, capture: Capture? = null,
    className: String? = null, init: (UploadInput.() -> Unit)? = null
) : Widget((className?.let { "$it " } ?: "") + "form-control"), GenericFormComponent<List<KFile>?>, FormInput,
    MutableState<List<KFile>?> {

    protected val observers = mutableListOf<(List<KFile>?) -> Unit>()

    /**
     * Temporary external value (used in tests)
     */
    protected var tmpValue: List<KFile>? = null

    /**
     * File input value.
     */
    override var value: List<KFile>?
        get() = getValue()
        set(value) {
            if (value == null) clearInput()
            tmpValue = value
            observers.forEach { ob -> ob(value) }
        }

    /**
     * Determines if multiple file upload is supported.
     */
    var multiple: Boolean by refreshOnUpdate(multiple)

    /**
     * File types accepted by the file upload input.
     */
    var accept: List<String>? by refreshOnUpdate(accept)

    /**
     * File upload input capture mode.
     */
    var capture: Capture? by refreshOnUpdate(capture)

    /**
     * Only allow directories for selection.
     */
    var webkitdirectory: Boolean by refreshOnUpdate()

    /**
     * The name attribute of the generated HTML input element.
     */
    override var name: String? by refreshOnUpdate()

    /**
     * Determines if the field is disabled.
     */
    override var disabled by refreshOnUpdate(false)

    /**
     * Determines if the file input is automatically focused.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * The size of the input.
     */
    override var size: InputSize? by refreshOnUpdate()

    /**
     * The validation status of the input.
     */
    override var validationStatus: ValidationStatus? by refreshOnUpdate()

    private val nativeFiles: MutableMap<KFile, File> = mutableMapOf()

    init {
        useSnabbdomDistinctKey()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("input")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(validationStatus)
        classSetBuilder.add(size)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", "file")
        if (multiple) {
            attributeSetBuilder.add("multiple")
        }
        if (accept != null) {
            attributeSetBuilder.add("accept", accept!!.joinToString(","))
        }
        capture?.let {
            attributeSetBuilder.add(it)
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
        if (webkitdirectory) {
            attributeSetBuilder.add("webkitdirectory")
        }
    }

    private fun getValue(): List<KFile>? {
        val v = getFiles() ?: tmpValue
        return if (v.isNullOrEmpty()) null else v
    }

    /**
     * Clear selected files.
     */
    open fun clearInput() {
        nativeFiles.clear()
        getElement()?.unsafeCast<HTMLInputElement>()?.value = ""
    }

    protected open fun getFiles(): List<KFile>? {
        nativeFiles.clear()
        return getElement()?.unsafeCast<HTMLInputElement>()?.files?.let { files ->
            files.asList().map { file ->
                val kfile = KFile(file.name, file.size.toInt())
                nativeFiles[kfile] = file
                kfile
            }
        }
    }

    /**
     * Returns the value of the file input control as a String.
     * @return value as a String
     */
    fun getValueAsString(): String? {
        return value?.joinToString(",") { it.name }
    }

    /**
     * Returns the native JavaScript File object.
     * @param kFile KFile object
     * @return File object
     */
    fun getNativeFile(kFile: KFile): File? {
        return nativeFiles[kFile]
    }

    override fun getState(): List<KFile>? = value

    override fun subscribe(observer: (List<KFile>?) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    override fun setState(state: List<KFile>?) {
        value = state
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.uploadInput(
    multiple: Boolean = false, accept: List<String>? = null, capture: Capture? = null,
    className: String? = null, init: (UploadInput.() -> Unit)? = null
): UploadInput {
    val uploadInput = UploadInput(multiple, accept, capture, className, init)
    this.add(uploadInput)
    return uploadInput
}

/**
 * Returns file with the content read.
 * @param kFile object identifying the file
 * @return KFile object
 */
suspend fun UploadInput.getFileWithContent(kFile: KFile): KFile {
    val content = this.getNativeFile(kFile)?.getContent()
    return kFile.copy(content = content)
}
