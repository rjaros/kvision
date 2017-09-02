package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TagSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = false, align = ALIGN.CENTER)
            root.add(tag)
            val element = document.getElementById("test")
            assertEquals("<h1 class=\"text-center\">This is &lt;b&gt;h1&lt;/b&gt;</h1>", element?.innerHTML, "Should render correct html tag")
        }
    }

    @Test
    fun render_rich() {
        run {
            val root = Root("test")
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = true, align = ALIGN.CENTER)
            root.add(tag)
            val element = document.getElementById("test")
            assertEquals("<h1 class=\"text-center\"><span>This is <b>h1</b></span></h1>", element?.innerHTML, "Should render correct html tag")
        }
    }


}