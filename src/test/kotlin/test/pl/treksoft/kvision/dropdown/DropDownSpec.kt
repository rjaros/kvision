package test.pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.dropdown.DropDown
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class DropDownSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
            root.add(dd)
            val element = document.getElementById("test")
            assertEquals("<div class=\"dropdown\"><button class=\"dropdown btn btn-default\" id=\"kv_dropdown_0\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-flag\"></span> Dropdown</button><ul class=\"dropdown-menu\" aria-labelledby=\"kv_dropdown_0\"><li><a href=\"#!/x\">abc</a></li><li><a href=\"#!/y\">def</a></li></ul></div>", element?.innerHTML, "Should render correct drop down")
        }
    }

}