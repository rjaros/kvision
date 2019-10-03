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
package test.pl.treksoft.kvision.navbar

import pl.treksoft.kvision.html.Link.Companion.link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag.Companion.tag
import pl.treksoft.kvision.navbar.Nav
import pl.treksoft.kvision.navbar.Navbar
import pl.treksoft.kvision.navbar.NavbarType
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class NavbarSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", fixed = true)
            val navbar = Navbar("TEST", NavbarType.FIXEDTOP)
            root.add(navbar)
            val element = document.getElementById("test")
            val id = navbar.container.id
            assertEqualsHtml(
                "<nav class=\"navbar navbar-fixed-top navbar-default\"><div class=\"container-fluid\"><div class=\"navbar-header\"><button class=\"navbar-toggle collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#$id\" aria-expanded=\"false\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button><a class=\"navbar-brand\" href=\"#\">TEST</a></div><div class=\"collapse navbar-collapse\" id=\"$id\"></div></div></nav>",
                element?.innerHTML,
                "Should render correct navbar"
            )
            navbar.inverted = true
            assertEqualsHtml(
                "<nav class=\"navbar navbar-fixed-top navbar-inverse\"><div class=\"container-fluid\"><div class=\"navbar-header\"><button class=\"navbar-toggle collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#$id\" aria-expanded=\"false\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button><a class=\"navbar-brand\" href=\"#\">TEST</a></div><div class=\"collapse navbar-collapse\" id=\"$id\"></div></div></nav>",
                element?.innerHTML,
                "Should render correct inverted navbar"
            )
            navbar.add(Nav {
                tag(TAG.LI) {
                    link("Test", "#!/test")
                }
            })
            assertEqualsHtml(
                "<nav class=\"navbar navbar-fixed-top navbar-inverse\"><div class=\"container-fluid\"><div class=\"navbar-header\"><button class=\"navbar-toggle collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#$id\" aria-expanded=\"false\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button><a class=\"navbar-brand\" href=\"#\">TEST</a></div><div class=\"collapse navbar-collapse\" id=\"$id\"><ul class=\"nav navbar-nav\"><li><a href=\"#!/test\">Test</a></li></ul></div></div></nav>",
                element?.innerHTML,
                "Should render correct navbar with nav link"
            )

        }
    }

}
