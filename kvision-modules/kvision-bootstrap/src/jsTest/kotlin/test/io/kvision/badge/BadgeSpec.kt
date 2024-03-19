package test.io.kvision.badge

import io.kvision.badge.BadgePosition
import io.kvision.badge.badge
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class BadgeSpec: DomSpec {

    @Test
    fun render() {
        run {
            Root("test", containerType = io.kvision.panel.ContainerType.FIXED) {
                div {
                    button("") {
                        badge("MR", position = BadgePosition.MIDDLE_RIGHT)
                    }
                }
            }
            val element = document.getElementById("test")
            assertEqualsHtml("""<div><button class="position-relative btn btn-primary" type="button"><span class="badge text-bg-secondary position-absolute top-50 start-100 translate-middle">MR</span></button></div>""",
                element?.innerHTML,
                "Should render correct badge components")
        }
    }
}
