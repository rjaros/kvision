/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.html

import pl.treksoft.kvision.html.Icon
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class IconSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val icon = Icon("fa-check")
            root.add(icon)
            val element = document.getElementById("test")
            assertEqualsHtml("<span class=\"fa fa-check\"></span>", element?.innerHTML, "Should render correct icon")
        }
    }

}
