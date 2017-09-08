package pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.html.BUTTONSIZE
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.LIST
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.snabbdom.StringPair

enum class DD(val POS: String) {
    HEADER("DD#HEADER"),
    DISABLED("DD#DISABLED"),
    SEPARATOR("DD#SEPARATOR")
}

open class DropDown(text: String, elements: List<StringPair>, icon: String? = null,
                    style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT, size: BUTTONSIZE? = null,
                    block: Boolean = false, disabled: Boolean = false, image: ResString? = null,
                    dropup: Boolean = false, classes: Set<String> = setOf()) : Container(classes) {
    val idc = "kv_dropdown_" + counter
    val button: DropDownButton = DropDownButton(idc, text, icon, style, size, block,
            disabled, image, setOf("dropdown"))
    val list: DropDownListTag = DropDownListTag(idc, setOf("dropdown-menu"))

    init {
        this.addCssClass(if (dropup) "dropup" else "dropdown")
        val children = elements.map {
            when (it.second) {
                DD.HEADER.POS -> Tag(TAG.LI, it.first, classes = setOf("dropdown-header"))
                DD.SEPARATOR.POS -> {
                    val tag = Tag(TAG.LI, it.first, classes = setOf("divider"))
                    tag.role = "separator"
                    tag
                }
                DD.DISABLED.POS -> {
                    val tag = Tag(TAG.LI, classes = setOf("disabled"))
                    tag.add(Link(it.first, "#"))
                    tag
                }
                else -> Link(it.first, it.second)
            }
        }
        list.addAll(children)
        this.add(button)
        this.add(list)
        counter++
    }

    companion object {
        var counter = 0
    }
}

open class DropDownButton(id: String, text: String, icon: String? = null, style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT,
                          size: BUTTONSIZE? = null, block: Boolean = false, disabled: Boolean = false,
                          image: ResString? = null, classes: Set<String> = setOf()) :
        Button(text, icon, style, size, block, disabled, image, classes) {

    init {
        this.id = id
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("data-toggle" to "dropdown", "aria-haspopup" to "true",
                "aria-expanded" to "false")
    }
}

open class DropDownListTag(val ariaId: String, classes: Set<String> = setOf()) : ListTag(LIST.UL, null,
        false, classes) {
    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("aria-labelledby" to ariaId)
    }
}
