/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.html.Iframe
import pl.treksoft.kvision.html.Sandbox
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class IframeSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val iframe = Iframe("https://www.google.com", null, "test", 800, 600, setOf(Sandbox.ALLOWSAMEORIGIN))
            root.add(iframe)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<iframe src=\"https://www.google.com\" name=\"test\" width=\"800\" height=\"600\" sandbox=\"allow-same-origin\"></iframe>",
                element?.innerHTML,
                "Should render correct iframe"
            )
            iframe.src = "https://www.google.pl"
            iframe.sandbox = setOf()
            assertEqualsHtml(
                "<iframe src=\"https://www.google.pl\" name=\"test\" width=\"800\" height=\"600\" sandbox=\"\"></iframe>",
                element?.innerHTML,
                "Should render correct iframe after properties change"
            )
        }
    }

}