/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.toolbar

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.toolbar.ButtonGroup
import pl.treksoft.kvision.toolbar.ButtonGroupSize
import pl.treksoft.kvision.toolbar.ButtonGroupStyle
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ButtonGroupSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val group = ButtonGroup()
            root.add(group)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"btn-group\" role=\"group\"></div>",
                element?.innerHTML,
                "Should render correct button group"
            )
            group.size = ButtonGroupSize.LARGE
            group.style = ButtonGroupStyle.JUSTIFIED
            assertEqualsHtml(
                "<div class=\"btn-group btn-group-lg btn-group-justified\" role=\"group\"></div>",
                element?.innerHTML,
                "Should render correct button group with large and justified buttons"
            )

        }
    }

}
