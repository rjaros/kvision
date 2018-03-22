/*
 * Copyright (c) 2018. Robert Jaros
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
            val root = Root("test", true)
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
