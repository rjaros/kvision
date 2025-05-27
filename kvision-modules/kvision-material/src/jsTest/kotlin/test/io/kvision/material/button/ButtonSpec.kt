package test.io.kvision.material.button

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.button.MdTextButton
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

/**
 * TODO
 */
class ButtonSpec : DomSpec {
    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            root.add(MdTextButton("Test Button", className = "test-button"))
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<md-text-button class=\"test-button\" type=\"submit\">Test Button</md-text-button>",
                element?.innerHTML,
                "Should render Material Text Button correctly"
            )
        }
    }

}
