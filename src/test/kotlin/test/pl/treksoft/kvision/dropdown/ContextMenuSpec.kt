/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.dropdown.ContextMenu
import pl.treksoft.kvision.html.Link.Companion.link
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.obj
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ContextMenuSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val m = ContextMenu() {
                link("a", "b")
                link("c", "d")
            }
            root.setContextMenu(m)
            m.show()
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ul class=\"dropdown-menu\" style=\"display: block;\"><li><a href=\"b\">a</a></li><li><a href=\"d\">c</a></li></ul>",
                element?.innerHTML,
                "Should render correct context menu"
            )
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    @Test
    fun positionMenu() {
        run {
            val root = Root("test", true)
            val m = ContextMenu() {
                link("a", "b")
                link("c", "d")
            }
            root.setContextMenu(m)
            m.positionMenu(obj {
                pageX = 40
                pageY = 50
            })
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<ul class=\"dropdown-menu\" style=\"display: block; top: 50px; left: 40px;\"><li><a href=\"b\">a</a></li><li><a href=\"d\">c</a></li></ul>",
                element?.innerHTML,
                "Should place context menu in the correct position"
            )
        }
    }
}