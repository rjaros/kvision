package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.html.IMAGESHAPE
import pl.treksoft.kvision.html.Image
import test.pl.treksoft.kvision.DomSpec
import test.pl.treksoft.kvision.require
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val res = require("./img/placeholder.png")
            val image = Image(res, "Image", true, IMAGESHAPE.ROUNDED, true)
            root.add(image)
            val element = document.getElementById("test")
            assertEquals(
                "<img class=\"img-responsive center-block img-rounded\" src=\"$res\" alt=\"Image\">",
                element?.innerHTML,
                "Should render correct html image"
            )
        }
    }

}