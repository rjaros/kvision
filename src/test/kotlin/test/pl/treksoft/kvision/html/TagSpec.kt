package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TagSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = false, align = ALIGN.CENTER)
            root.add(tag)
            val element = document.getElementById("test")
            assertEquals(
                "<h1 class=\"text-center\">This is &lt;b&gt;h1&lt;/b&gt;</h1>",
                element?.innerHTML,
                "Should render correct html tag"
            )
        }
    }

    @Test
    fun renderRich() {
        run {
            val root = Root("test")
            val tag = Tag(TAG.H1, "This is <b>h1</b>", rich = true, align = ALIGN.RIGHT)
            root.add(tag)
            val element = document.getElementById("test")
            assertEquals(
                "<h1 class=\"text-right\"><span>This is <b>h1</b></span></h1>",
                element?.innerHTML,
                "Should render correct html tag"
            )
        }
    }

    @Test
    fun renderAsContainer() {
        run {
            val root = Root("test")
            val tag = Tag(TAG.P, align = ALIGN.RIGHT)
            tag.add(Tag(TAG.DEL, "This is test"))
            tag.add(Link("abc", "/x"))
            root.add(tag)
            val element = document.getElementById("test")
            assertEquals(
                "<p class=\"text-right\"><del>This is test</del><a href=\"/x\">abc</a></p>",
                element?.innerHTML,
                "Should render correct html tag with children"
            )
        }
    }
}