package pl.treksoft.kvision.dropdown

import com.github.snabbdom.VNode
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.LIST
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair
import pl.treksoft.kvision.snabbdom.obj

enum class DD(val type: String) {
    HEADER("DD#HEADER"),
    DISABLED("DD#DISABLED"),
    SEPARATOR("DD#SEPARATOR")
}

open class DropDown(text: String, elements: List<StringPair>? = null, icon: String? = null,
                    style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT, disabled: Boolean = false, image: ResString? = null,
                    dropup: Boolean = false, classes: Set<String> = setOf()) : SimplePanel(classes) {
    var text
        get() = button.text
        set(value) {
            button.text = value
        }
    private var elements = elements
        set(value) {
            field = elements
            setChildrenFromElements()
        }
    var icon
        get() = button.icon
        set(value) {
            button.icon = value
        }
    var style
        get() = button.style
        set(value) {
            button.style = value
        }
    var size
        get() = button.size
        set(value) {
            button.size = value
        }
    var block
        get() = button.block
        set(value) {
            button.block = value
        }
    var disabled
        get() = button.disabled
        set(value) {
            button.disabled = value
        }
    var image
        get() = button.image
        set(value) {
            button.image = value
        }
    private var dropup = dropup
        set(value) {
            field = value
            refresh()
        }

    private val idc = "kv_dropdown_" + counter
    internal val button: DropDownButton = DropDownButton(idc, text, icon, style,
            disabled, image, setOf("dropdown"))
    internal val list: DropDownListTag = DropDownListTag(idc, setOf("dropdown-menu"))

    init {
        button.setEventListener {
            click = {
                if (!button.disabled) toggle()
            }
        }

        list.hide()
        setChildrenFromElements()
        this.addInternal(button)
        this.addInternal(list)
        counter++
    }

    companion object {
        var counter = 0
    }

    override fun add(child: Widget): SimplePanel {
        list.add(child)
        return this
    }

    override fun addAll(children: List<Widget>): SimplePanel {
        list.addAll(children)
        return this
    }

    private fun setChildrenFromElements() {
        val elems = elements
        if (elems != null) {
            val c = elems.map {
                when (it.second) {
                    DD.HEADER.type -> Tag(TAG.LI, it.first, classes = setOf("dropdown-header"))
                    DD.SEPARATOR.type -> {
                        val tag = Tag(TAG.LI, it.first, classes = setOf("divider"))
                        tag.role = "separator"
                        tag
                    }
                    DD.DISABLED.type -> {
                        val tag = Tag(TAG.LI, classes = setOf("disabled"))
                        tag.add(Link(it.first, "#"))
                        tag
                    }
                    else -> Link(it.first, it.second)
                }
            }
            list.addAll(c)
        } else {
            list.removeAll()
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("show.bs.dropdown", { e, _ ->
            this.dispatchEvent("showBsDropdown", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("shown.bs.dropdown", { e, _ ->
            this.dispatchEvent("shownBsDropdown", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("hide.bs.dropdown", { e, _ ->
            this.dispatchEvent("hideBsDropdown", obj({ detail = e }))
        })
        this.getElementJQuery()?.on("hidden.bs.dropdown", { e, _ ->
            list.visible = false
            this.dispatchEvent("hiddenBsDropdown", obj({ detail = e }))
        })
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (dropup)
            cl.add("dropup" to true)
        else
            cl.add("dropdown" to true)
        return cl
    }

    open fun toggle() {
        if (list.visible)
            list.hideInternal()
        else
            list.show()
    }
}

open class DropDownButton(id: String, text: String, icon: String? = null, style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT,
                          disabled: Boolean = false,
                          image: ResString? = null, classes: Set<String> = setOf()) :
        Button(text, icon, style, disabled, image, classes) {

    init {
        this.id = id
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("data-toggle" to "dropdown", "aria-haspopup" to "true",
                "aria-expanded" to "false")
    }
}

open class DropDownListTag(private val ariaId: String, classes: Set<String> = setOf()) : ListTag(LIST.UL, null,
        false, classes) {

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("aria-labelledby" to ariaId)
    }

    override fun hide(): Widget {
        if (visible) hideInternal()
        return super.hide()
    }

    override fun afterInsert(node: VNode) {
        if (visible) showInternal()
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun showInternal() {
        if (getElementJQueryD()?.`is`(":hidden")) {
            getElementJQueryD()?.dropdown("toggle")
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun hideInternal() {
        if (!getElementJQueryD()?.`is`(":hidden")) {
            getElementJQueryD()?.dropdown("toggle")
        }
    }
}
