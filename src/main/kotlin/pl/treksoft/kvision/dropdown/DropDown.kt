package pl.treksoft.kvision.dropdown

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.snabbdom.StringPair

open class DropDown(text: String, elements: List<String>, icon: String? = null, style: BUTTON_STYLE = BUTTON_STYLE.DEFAULT, size: BUTTON_SIZE? = null,
                    block: Boolean = false, disabled: Boolean = false, image: ResString? = null, classes: Set<String> = setOf()) : Container(classes) {
    val idk = "abc"
    val button: DropDownButton = DropDownButton(idk, text, icon, style, size, block, disabled, image, setOf("dropdown"))
    val list: DropDownListTag = DropDownListTag(idk, elements, setOf("dropdown-menu"))

    init {
        this.addCssClass("dropdown")
        this.add(button)
        this.add(list)
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

open class DropDownListTag(val ariaId: String, elements: List<String>, classes: Set<String> = setOf()) : ListTag(LIST.UL, elements, true, classes) {

    override fun render(): VNode {
        val children = elements.map { el -> element("li", el, true) }.toTypedArray()
        return kvh(type.tagName, children)
    }

    private fun element(name: String, value: String, rich: Boolean): VNode {
        if (rich) {
            return h(name, arrayOf(KVManager.virtualize("<a href='#'>$value</a>")))
        } else {
            return h(name, value)
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("aria-labelledby" to ariaId)
    }
}