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

package io.kvision.print

import io.kvision.PrintModule
import io.kvision.PrintModule.counter
import io.kvision.core.Widget
import io.kvision.utils.obj

/**
 * Print types.
 */
enum class PrintType(internal val type: String) {
    PDF("pdf"),
    HTML("html"),
    IMAGE("image"),
    JSON("json"),
    RAWHTML("raw-html"),
}

/**
 * Print options.
 */
data class PrintOptions(
    val header: String? = null,
    val headerStyle: String? = null,
    val maxWidth: Int? = null,
    val css: String? = null,
    val style: String? = null,
    val scanStyles: Boolean? = null,
    val targetStyle: List<String>? = null,
    val targetStyles: List<String>? = null,
    val ignoreElements: List<String>? = null,
    val properties: List<dynamic>? = null,
    val gridHeaderStyle: String? = null,
    val gridStyle: String? = null,
    val repeatTableHeader: Boolean? = null,
    val showModal: Boolean? = null,
    val modalMessage: String? = null,
    val onLoadingStart: (() -> Unit)? = null,
    val onLoadingEnd: (() -> Unit)? = null,
    val documentTitle: String? = null,
    val fallbackPrintable: dynamic = null,
    val onPdfOpen: (() -> Unit)? = null,
    val onPrintDialogClose: (() -> Unit)? = null,
    val onError: ((e: Error) -> Unit)? = null,
    val base64: Boolean? = null,
)

internal fun PrintOptions.toJs(printable: dynamic, type: PrintType): dynamic {
    return obj {
        this.printable = printable
        this.type = type.type
        if (header != null) this.header = header
        if (headerStyle != null) this.headerStyle = headerStyle
        if (maxWidth != null) this.maxWidth = maxWidth
        if (css != null) this.css = css
        if (style != null) this.style = style
        if (scanStyles != null) this.scanStyles = scanStyles
        if (targetStyle != null) this.targetStyle = targetStyle.toTypedArray()
        if (targetStyles != null) this.targetStyles = targetStyles.toTypedArray()
        if (ignoreElements != null) this.ignoreElements = ignoreElements.toTypedArray()
        if (properties != null) this.properties = properties.toTypedArray()
        if (gridHeaderStyle != null) this.gridHeaderStyle = gridHeaderStyle
        if (gridStyle != null) this.gridStyle = gridStyle
        if (repeatTableHeader != null) this.repeatTableHeader = repeatTableHeader
        if (showModal != null) this.showModal = showModal
        if (modalMessage != null) this.modalMessage = modalMessage
        if (onLoadingStart != null) this.onLoadingStart = onLoadingStart
        if (onLoadingEnd != null) this.onLoadingEnd = onLoadingEnd
        if (documentTitle != null) this.documentTitle = documentTitle
        if (fallbackPrintable != null) this.fallbackPrintable = fallbackPrintable
        if (onPdfOpen != null) this.onPdfOpen = onPdfOpen
        if (onPrintDialogClose != null) this.onPrintDialogClose = onPrintDialogClose
        if (onError != null) this.onError = onError
        if (base64 != null) this.base64 = base64
    }
}

/**
 * Prints the given widget content directly from the browser window.
 * @param options print options
 */
fun Widget.print(options: PrintOptions? = null) {
    val previousId = this.id
    val id = "kv_print_js_id_${counter++}"
    this.id = id
    if (options != null) {
        PrintModule.printjs(options.toJs(id, PrintType.HTML))
    } else {
        PrintModule.printjs(id, "html")
    }
    this.id = previousId
}

/**
 * Print-Js helper object.
 */
object Print {

    init {
        PrintModule.initialize()
    }

    /**
     * Prints the given data directly from the browser window.
     * @param data an URL or printable data object
     * @param type printable type
     * @param options print options
     */
    fun print(data: dynamic, type: PrintType, options: PrintOptions? = null) {
        if (options != null) {
            PrintModule.printjs(options.toJs(data, type))
        } else {
            PrintModule.printjs(data, type.type)
        }
    }

}
