package pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.snabbdom.StringPair

open class DropDown(text: String, elements: List<StringPair>, icon: String? = null, style: BUTTON_STYLE = BUTTON_STYLE.DEFAULT, size: BUTTON_SIZE? = null,
                    block: Boolean = false, disabled: Boolean = false, image: ResString? = null, classes: Set<String> = setOf()) : Container(classes) {
    val idc = "kv_dropdown_" + counter
    val button: DropDownButton = DropDownButton(idc, text, icon, style, size, block, disabled, image, setOf("dropdown"))
    val list: DropDownListTag = DropDownListTag(idc, setOf("dropdown-menu"))

    init {
        this.addCssClass("dropdown")
        val children = elements.map { Link(it.first, it.second) }
        list.addAll(children)
        this.add(button)
        this.add(list)
        counter++
    }

    companion object {
        var counter = 0
    }
}

open class DropDownButton(id: String, text: String, icon: String? = null, style: BUTTON_STYLE = BUTTON_STYLE.DEFAULT, size: BUTTON_SIZE? = null,
                          block: Boolean = false, disabled: Boolean = false, image: ResString? = null, classes: Set<String> = setOf()) :
        Button(text, icon, style, size, block, disabled, image, classes) {

    init {
        this.id = id
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("data-toggle" to "dropdown", "aria-haspopup" to "true", "aria-expanded" to "false")
    }
}

open class DropDownListTag(val ariaId: String, classes: Set<String> = setOf()) : ListTag(LIST.UL, null, false, classes) {
    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("aria-labelledby" to ariaId)
    }
}