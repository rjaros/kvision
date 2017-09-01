package test.pl.treksoft.kvision.core

import com.github.snabbdom.h
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.snabbdom.snAttrs
import pl.treksoft.kvision.snabbdom.snOpt
import pl.treksoft.kvision.snabbdom.snStyle
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class KVManagerSpec : DomSpec {

    @Test
    fun patch_ById() {
        run {
            val vnode = h("span", snOpt {
                attrs = snAttrs("id" to "test_new")
                style = snStyle("fontWeight" to "bold", "fontStyle" to "italic")
            })
            KVManager.patch("test", vnode)
            assertTrue("Original container should not exist") { document.getElementById("test") == null }
            assertTrue("New container should exist") { document.getElementById("test_new") != null }
        }
    }

    @Test
    fun patch_ByVnode() {
        run {
            val vnode1 = h("span", snOpt {
                attrs = snAttrs("id" to "test2")
                style = snStyle("fontWeight" to "bold", "fontStyle" to "italic")
            })
            val vnode2 = KVManager.patch("test", vnode1)
            val vnode3 = h("span", snOpt {
                attrs = snAttrs("id" to "test3")
                style = snStyle("fontWeight" to "bold", "fontStyle" to "italic")
            })
            KVManager.patch(vnode2, vnode3)
            assertTrue("First container should not exist") { document.getElementById("test") == null }
            assertTrue("Second container should not exist") { document.getElementById("test2") == null }
            assertTrue("Third container should exist") { document.getElementById("test3") != null }
        }
    }

    @Test
    fun virtualize() {
        run {
            val node = KVManager.virtualize("<div id=\"virtual\"><p>Virtual node</p></div>")
            KVManager.patch("test", node)
            assertTrue("Original container should not exist") { document.getElementById("test") == null }
            val v = document.getElementById("virtual")
            assertTrue("New container should exist") { v != null }
            assertTrue("New container should have one child") { v?.children?.length == 1 }
        }
    }
}
