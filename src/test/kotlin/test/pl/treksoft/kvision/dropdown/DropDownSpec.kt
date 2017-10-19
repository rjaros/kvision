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
            assertEquals("<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li><a href=\"#!/x\">abc</a></li><li><a href=\"#!/y\">def</a></li></ul></div>", element?.innerHTML, "Should render correct drop down")
        }
    }

    @Test
    fun render_DropUp() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag", dropup = true)
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals("<div class=\"dropup open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li><a href=\"#!/x\">abc</a></li><li><a href=\"#!/y\">def</a></li></ul></div>", element?.innerHTML, "Should render correct drop down")
        }
    }

    @Test
    fun render_HeaderElement() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to DD.HEADER.type), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals("<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li class=\"dropdown-header\">abc</li></ul></div>", element?.innerHTML, "Should render correct drop down")
        }
    }

    @Test
    fun render_SeparatorElement() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to DD.SEPARATOR.type), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals("<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li class=\"divider\" role=\"separator\">abc</li></ul></div>", element?.innerHTML, "Should render correct drop down")
        }
    }

    @Test
    fun render_DisabledElement() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to DD.DISABLED.type), "flag")
            root.add(dd)
            dd.toggle()
            val element = document.getElementById("test")
            val id = dd.button.id
            assertEquals("<div class=\"dropdown open\"><button class=\"dropdown btn btn-default\" id=\"$id\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"$id\" aria-expanded=\"true\"><li class=\"disabled\"><a href=\"#\">abc</a></li></ul></div>", element?.innerHTML, "Should render correct drop down")
        }
    }

    @Test
    fun toggle() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
            root.add(dd)
            val menu = dd.list.getElementJQuery()
            assertTrue("Dropdown menu is not rendered before toggle") { menu == null }
            dd.toggle()
            val classes = dd.getElementJQuery()?.attr("class")
            assertTrue("Dropdown is visible after toggle") { classes?.contains("open") == true }
            val menu2 = dd.list.getElementJQuery()
            assertTrue("Dropdown menu is rendered after toggle") { menu2 != null }
        }
    }
}