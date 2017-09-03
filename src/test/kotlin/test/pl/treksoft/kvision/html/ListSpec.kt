package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.html.LIST
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class ListSpec : DomSpec {

    @Test
    fun render_Elements() {
        run {
            val root = Root("test")
            val list = ListTag(LIST.DL_HORIZ, listOf("a1", "a2", "b1", "b2"))
            root.add(list)
            val element = document.getElementById("test")
            assertEquals("<dl class=\"dl-horizontal\"><dt>a1</dt><dd>a2</dd><dt>b1</dt><dd>b2</dd></dl>", element?.innerHTML, "Should render correct html list")
        }
    }

    @Test
    fun render_AsContainer() {
        run {
            val root = Root("test")
            val list = ListTag(LIST.UL)
            list.add(Tag(TAG.PRE, "pre"))
            list.add(Tag(TAG.DEL, "del"))
            root.add(list)
            val element = document.getElementById("test")
            assertEquals("<ul><li><pre>pre</pre></li><li><del>del</del></li></ul>", element?.innerHTML, "Should render correct html list")
        }
    }

}