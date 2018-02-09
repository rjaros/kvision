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
package test.pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.dropdown.DD
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DropDownSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals(
                "<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li><a href=\"#!/x\">abc</a></li><li><a href=\"#!/y\">def</a></li></ul></div>",
                element?.innerHTML,
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderDropUp() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag").apply { dropup = true }
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals(
                "<div class=\"dropup open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li><a href=\"#!/x\">abc</a></li><li><a href=\"#!/y\">def</a></li></ul></div>",
                element?.innerHTML,
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderHeaderElement() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to DD.HEADER.option), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals(
                "<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li class=\"dropdown-header\">abc</li></ul></div>",
                element?.innerHTML,
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderSeparatorElement() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to DD.SEPARATOR.option), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals(
                "<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li class=\"divider\" role=\"separator\">abc</li></ul></div>",
                element?.innerHTML,
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun renderDisabledElement() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to DD.DISABLED.option), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals(
                "<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li class=\"disabled\"><a href=\"#\">abc</a></li></ul></div>",
                element?.innerHTML,
                "Should render correct drop down"
            )
        }
    }

    @Test
    fun toggle() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
            root.add(dd)
            val visible = dd.getElementJQuery()?.hasClass("open") ?: false
            assertTrue("Dropdown menu is not visible before toggle") { !visible }
            dd.toggle()
            val visible2 = dd.getElementJQuery()?.hasClass("open") ?: false
            assertTrue("Dropdown menu is visible after toggle") { visible2 }
        }
    }
}