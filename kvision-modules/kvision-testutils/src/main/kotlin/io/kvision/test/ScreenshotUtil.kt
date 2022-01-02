package io.kvision.test

import externals.Html2CanvasOptions
import externals.html2canvas
import io.kvision.utils.obj
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement

object ScreenshotUtil {

    /**
     * Take a screenshot of the requested [HTMLElement].
     *
     * Note: Kotlinx Coroutines [runTest] is required, as Kotlin/JS does not have another
     * way to suspend execution to await the screenshot (`runBlocking` is not available).
     */
    suspend fun capture(
        element: HTMLElement,
        options: Html2CanvasOptions.() -> Unit = {}
    ): HTMLCanvasElement {
        val h2cOpts = obj<Html2CanvasOptions>(options)
        return html2canvas(element, h2cOpts).await()
    }

}
