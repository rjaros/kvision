package io.kvision.test

import kotlin.test.Test
import kotlinx.browser.document
import kotlinx.coroutines.test.runTest
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.dom.create
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.p
import org.w3c.dom.HTMLElement

class ScreenshotTest {

    @Test
    fun testScreenshotOfContainerWithLink() = runTest {
        val container = createSimpleDocument()

        val canvas = ScreenshotUtil.capture(container)
        val data = canvas.toDataURL("image/png")
        println("\n\n---\n\ncanvas data = $data\n\n---\n\n")
    }

    companion object {
        fun createSimpleDocument(): HTMLElement {

            val expectedImage = require("img/testScreenshotOfContainerWithLink.png")

            document.body!!.append.div {
                id = "container"
            }
            val myDiv = document.create.div("panel") {
                p {
                    a("https://kvision.io/") { +"KVision" }
                    +" - object oriented web framework for Kotlin/JS"
                    img(src = expectedImage.unsafeCast<String>())
                }
            }
            val container = document.getElementById("container") as HTMLElement
            container.appendChild(myDiv)
            return container
        }
    }
}
