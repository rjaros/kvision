package test.pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.dropdown.DD
import pl.treksoft.kvision.dropdown.DropDown
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
            val dd = DropDown("Dropdown", listOf("abc" to DD.HEADER.type), "flag")
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
            val dd = DropDown("Dropdown", listOf("abc" to DD.SEPARATOR.type), "flag")
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
            val dd = DropDown("Dropdown", listOf("abc" to DD.DISABLED.type), "flag")
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