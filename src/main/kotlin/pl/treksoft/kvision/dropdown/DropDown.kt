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
package pl.treksoft.kvision.dropdown

import com.github.snabbdom.VNode
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.ButtonStyle
import pl.treksoft.kvision.html.ButtonType
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.ListType
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.obj

/**
 * Useful options for use in DropDown's *elements* parameter.
 */
enum class DD(val option: String) {
    HEADER("DD#HEADER"),
    DISABLED("DD#DISABLED"),
    SEPARATOR("DD#SEPARATOR")
}

/**
 * Bootstrap dropdown component.
 *
 * @constructor
 * @param text the label of the dropdown button
 * @param elements an optional list of link elements (special options from [DD] enum class can be used as values)
 * @param icon the icon of the dropdown button
 * @param style the style of the dropdown button
 * @param disabled determines if the component is disabled on start
 * @param forNavbar determines if the component will be used in a navbar
 * @param withCaret determines if the dropdown button renders caret
 * @param classes a set of CSS class names
 */
open class DropDown(
    text: String, elements: List<StringPair>? = null, icon: String? = null,
    style: ButtonStyle = ButtonStyle.DEFAULT, disabled: Boolean = false, val forNavbar: Boolean = false,
    withCaret: Boolean = true, classes: Set<String> = setOf()
) : SimplePanel(classes) {
    /**
     * Label of the dropdown button.
     */
    var text
        get() = button.text
        set(value) {
            button.text = value
        }
    private var elements by refreshOnUpdate(elements, { setChildrenFromElements() })
    /**
     * The icon of the dropdown button.
     */
    var icon
        get() = button.icon
        set(value) {
            button.icon = value
        }
    /**
     * The style of the dropdown button.
     */
    var style
        get() = button.style
        set(value) {
            button.style = value
        }
    /**
     * The size of the dropdown button.
     */
    var size
        get() = button.size
        set(value) {
            button.size = value
        }
    /**
     * Determines if the dropdown button takes all the space horizontally.
     */
    var block
        get() = button.block
        set(value) {
            button.block = value
        }
    /**
     * Determines if the dropdown is disabled.
     */
    var disabled
        get() = button.disabled
        set(value) {
            button.disabled = value
        }
    /**
     * The image on the dropdown button.
     */
    var image
        get() = button.image
        set(value) {
            button.image = value
        }
    /**
     * Determines if the dropdown is showing upwards.
     */
    var dropup by refreshOnUpdate(false)
    /**
     * Width of the dropdown button.
     */
    override var width: CssSize?
        get() = super.width
        set(value) {
            super.width = value
            button.width = value
        }

    private val idc = "kv_dropdown_$counter"
    internal val button: DropDownButton = DropDownButton(
        idc, text, icon, style,
        disabled, forNavbar, withCaret, setOf("dropdown")
    )
    internal val list: DropDownListTag = DropDownListTag(idc, setOf("dropdown-menu"))

    init {
        setChildrenFromElements()
        this.addInternal(button)
        this.addInternal(list)
        counter++
    }

    override fun render(): VNode {
        return if (forNavbar) {
            render("li", childrenVNodes())
        } else {
            render("div", childrenVNodes())
        }
    }

    override fun add(child: Component): SimplePanel {
        list.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        list.addAll(children)
        return this
    }

    override fun remove(child: Component): SimplePanel {
        list.remove(child)
        return this
    }

    override fun removeAll(): SimplePanel {
        list.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return list.getChildren()
    }

    private fun setChildrenFromElements() {
        list.removeAll()
        elements?.let { elems ->
            val c = elems.map {
                when (it.second) {
                    DD.HEADER.option -> Header(it.first)
                    DD.SEPARATOR.option -> Separator()
                    DD.DISABLED.option -> {
                        val tag = Tag(TAG.LI, classes = setOf("disabled"))
                        tag.add(Link(it.first))
                        tag
                    }
                    else -> Link(it.first, it.second)
                }
            }
            list.addAll(c)
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("show.bs.dropdown", { e, _ ->
            this.dispatchEvent("showBsDropdown", obj { detail = e })
        })
        this.getElementJQuery()?.on("shown.bs.dropdown", { e, _ ->
            this.dispatchEvent("shownBsDropdown", obj { detail = e })
        })
        this.getElementJQuery()?.on("hide.bs.dropdown", { e, _ ->
            this.dispatchEvent("hideBsDropdown", obj { detail = e })
        })
        this.getElementJQuery()?.on("hidden.bs.dropdown", { e, _ ->
            this.dispatchEvent("hiddenBsDropdown", obj { detail = e })
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

    /**
     * Toggles dropdown visibility.
     */
    open fun toggle() {
        this.list.getElementJQueryD()?.dropdown("toggle")
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.dropDown(
            text: String, elements: List<StringPair>? = null, icon: String? = null,
            style: ButtonStyle = ButtonStyle.DEFAULT, disabled: Boolean = false, forNavbar: Boolean = false,
            withCaret: Boolean = true, classes: Set<String> = setOf(), init: (DropDown.() -> Unit)? = null
        ): DropDown {
            val dropDown =
                DropDown(
                    text,
                    elements,
                    icon,
                    style,
                    disabled,
                    forNavbar,
                    withCaret,
                    classes
                ).apply { init?.invoke(this) }
            this.add(dropDown)
            return dropDown
        }
    }
}

internal class DropDownButton(
    id: String,
    text: String,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    disabled: Boolean = false,
    val forNavbar: Boolean = false,
    val withCaret: Boolean = true,
    classes: Set<String> = setOf()
) :
    Button(text, icon, style, ButtonType.BUTTON, disabled, classes) {

    init {
        this.id = id
        this.role = "button"
        setInternalEventListener<DropDownButton> {
            click = { e ->
                if (parent?.parent is ContextMenu) e.asDynamic().dropDownCM = true
            }
        }
    }

    override fun render(): VNode {
        val text = createLabelWithIcon(text, icon, image)
        return if (forNavbar) {
            if (withCaret) {
                val textWithCarret = text.toMutableList()
                textWithCarret.add(" ")
                textWithCarret.add(KVManager.virtualize("<span class='caret'></span>"))
                render("a", textWithCarret.toTypedArray())
            } else {
                render("a", text)
            }
        } else {
            render("button", text)
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        return if (forNavbar) {
            listOf("dropdown" to true)
        } else {
            super.getSnClass()
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf(
            "data-toggle" to "dropdown", "aria-haspopup" to "true",
            "aria-expanded" to "false", "href" to "#"
        )
    }
}

internal class DropDownListTag(private val ariaId: String, classes: Set<String> = setOf()) : ListTag(
    ListType.UL, null,
    false, classes
) {

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("aria-labelledby" to ariaId)
    }
}
