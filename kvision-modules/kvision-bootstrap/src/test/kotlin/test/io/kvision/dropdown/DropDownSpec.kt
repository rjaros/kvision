/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.io.kvision.dropdown

import kotlinx.browser.document
import io.kvision.dropdown.DD
import io.kvision.dropdown.Direction
import io.kvision.dropdown.DropDown
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlin.test.Test
import kotlin.test.assertTrue

class DropDownSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
            root.add(dd)
            dd.list.getElementJQueryD()?.dropdown("toggle")
            val element = document.getElementById("test")
            val id = dd.buttonId()
            assertEqualsHtml(
                "<div class=\"dropdown show\"><button class=\"btn btn-primary dropdown-toggle\" id=\"$id\" role=\"button\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" href=\"javascript:void(0)\"><i class=\"flag\"></i> Dropdown</button><div class=\"dropdown-menu show\" aria-labelledby=\"$id\" x-placement=\"bottom-start\" aria-expanded=\"true\" style=\"position: absolute;\"><a class=\"dropdown-item\" href=\"#!/x\">abc</a><a class=\"dropdown-item\" href=\"#!/y\">def</a></div></div>",
                element?.innerHTML?.replace("position: ;","position: absolute;"),
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderDropUp() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag").apply {
                direction = Direction.DROPUP
            }
            root.add(dd)
            dd.list.getElementJQueryD()?.dropdown("toggle")
            val element = document.getElementById("test")
            val id = dd.buttonId()
            assertEqualsHtml(
                "<div class=\"dropup show\"><button class=\"btn btn-primary dropdown-toggle\" id=\"$id\" role=\"button\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" href=\"javascript:void(0)\"><i class=\"flag\"></i> Dropdown</button><div class=\"dropdown-menu show\" aria-labelledby=\"$id\" x-placement=\"top-start\" aria-expanded=\"true\" style=\"position: absolute;\"><a class=\"dropdown-item\" href=\"#!/x\">abc</a><a class=\"dropdown-item\" href=\"#!/y\">def</a></div></div>",
                element?.innerHTML?.replace("position: ;","position: absolute;"),
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderHeaderElement() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dd = DropDown("Dropdown", listOf("abc" to DD.HEADER.option), "flag")
            root.add(dd)
            dd.list.getElementJQueryD()?.dropdown("toggle")
            val element = document.getElementById("test")
            val id = dd.buttonId()
            assertEqualsHtml(
                "<div class=\"dropdown show\"><button class=\"btn btn-primary dropdown-toggle\" id=\"$id\" role=\"button\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" href=\"javascript:void(0)\"><i class=\"flag\"></i> Dropdown</button><div class=\"dropdown-menu show\" aria-labelledby=\"$id\" x-placement=\"bottom-start\" aria-expanded=\"true\" style=\"position: absolute;\"><h6 class=\"dropdown-header\">abc</h6></div></div>",
                element?.innerHTML?.replace("position: ;","position: absolute;"),
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderSeparatorElement() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dd = DropDown("Dropdown", listOf("abc" to DD.SEPARATOR.option), "flag")
            root.add(dd)
            dd.list.getElementJQueryD()?.dropdown("toggle")
            val element = document.getElementById("test")
            val id = dd.buttonId()
            assertEqualsHtml(
                "<div class=\"dropdown show\"><button class=\"btn btn-primary dropdown-toggle\" id=\"$id\" role=\"button\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" href=\"javascript:void(0)\"><i class=\"flag\"></i> Dropdown</button><div class=\"dropdown-menu show\" aria-labelledby=\"$id\" x-placement=\"bottom-start\" aria-expanded=\"true\" style=\"position: absolute;\"><div class=\"dropdown-divider\"></div></div></div>",
                element?.innerHTML?.replace("position: ;","position: absolute;"),
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderDisabledElement() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dd = DropDown("Dropdown", listOf("abc" to DD.DISABLED.option), "flag")
            root.add(dd)
            dd.list.getElementJQueryD()?.dropdown("toggle")
            val element = document.getElementById("test")
            val id = dd.buttonId()
            assertEqualsHtml(
                "<div class=\"dropdown show\"><button class=\"btn btn-primary dropdown-toggle\" id=\"$id\" role=\"button\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" href=\"javascript:void(0)\"><i class=\"flag\"></i> Dropdown</button><div class=\"dropdown-menu show\" aria-labelledby=\"$id\" x-placement=\"bottom-start\" aria-expanded=\"true\" style=\"position: absolute;\"><a class=\"dropdown-item disabled\" tabindex=\"-1\" aria-disabled=\"true\" href=\"javascript:void(0)\">abc</a></div></div>",
                element?.innerHTML?.replace("position: ;","position: absolute;"),
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun toggle() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
            root.add(dd)
            val visible = dd.getElementJQuery()?.hasClass("show") ?: false
            assertTrue("Dropdown menu is not visible before toggle") { !visible }
            dd.list.getElementJQueryD()?.dropdown("toggle")
            val visible2 = dd.getElementJQuery()?.hasClass("show") ?: false
            assertTrue("Dropdown menu is visible after toggle") { visible2 }
        }
    }
}