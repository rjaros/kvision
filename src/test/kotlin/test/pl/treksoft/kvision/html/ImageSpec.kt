package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.core.Img
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.html.IMAGE_SHAPE
import pl.treksoft.kvision.html.Image
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val res = Img("kotlin.png")
            val image = Image(res, "Image", true, IMAGE_SHAPE.ROUNDED, true)
            root.add(image)
            val element = document.getElementById("test")
            assertEquals("<img class=\"img-responsive center-block img-rounded\" src=\"$res\" alt=\"Image\">", element?.innerHTML, "Should render correct html image")
        }
    }

}