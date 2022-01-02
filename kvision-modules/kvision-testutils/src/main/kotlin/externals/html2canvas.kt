package externals

import kotlin.js.Promise
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement

@JsNonModule
@JsModule("html2canvas")
external fun html2canvas(element: HTMLElement, options: Html2CanvasOptions = definedExternally): Promise<HTMLCanvasElement>


/** [`https://github.com/niklasvh/html2canvas/blob/v1.3.4/src/index.ts#L19`](https://github.com/niklasvh/html2canvas/blob/v1.3.4/src/index.ts#L19) */
external interface CloneOptions {
    var ignoreElements: ((element: Element) -> Boolean)?
    var onclone: ((document: Document, element: HTMLElement) -> Unit)?
    var allowTaint: Boolean?
}

external interface WindowOptions {
    var scrollX: Number
    var scrollY: Number
    var windowWidth: Number
    var windowHeight: Number
}

external interface RenderOptions {
    var scale: Number
    var canvas: HTMLCanvasElement?
    var x: Number
    var y: Number
    var width: Number
    var height: Number
}

external interface ContextOptions : ResourceOptions {
    var logging: Boolean?
    var cache: Cache?
}

/** [`https://github.com/niklasvh/html2canvas/blob/v1.3.4/src/core/cache-storage.ts`](https://github.com/niklasvh/html2canvas/blob/v1.3.4/src/core/cache-storage.ts) */
external class Cache {
    fun addImage(src: String): Promise<Unit>
    fun match(src: String): Promise<Any>
    fun has(key: String): Boolean
    fun keys(): Promise<Array<String>>
}

external interface ResourceOptions {
    var imageTimeout: Number;
    var useCORS: Boolean?
    var allowTaint: Boolean?
    var proxy: String?;
}

/** [`https://github.com/niklasvh/html2canvas/blob/v1.3.4/src/index.ts#L10`](https://github.com/niklasvh/html2canvas/blob/v1.3.4/src/index.ts#L10) */
@JsName("Options")
external interface Html2CanvasOptions
    : CloneOptions,
    WindowOptions,
    RenderOptions,
    ContextOptions {

    var backgroundColor: String?
    var foreignObjectRendering: Boolean?
    var removeContainer: Boolean?

}
