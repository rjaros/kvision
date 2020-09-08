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
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.ButtonStyle
import pl.treksoft.kvision.html.ButtonType
import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set

/**
 * Useful options for use in DropDown's *elements* parameter.
 */
enum class DD(val option: String) {
    HEADER("DD#HEADER"),
    DISABLED("DD#DISABLED"),
    SEPARATOR("DD#SEPARATOR")
}

/**
 * Dropdown directions.
 */
enum class Direction(internal val direction: String) {
    DROPDOWN("dropdown"),
    DROPUP("dropup"),
    DROPLEFT("dropleft"),
    DROPRIGHT("dropright")
}

/**
 * Bootstrap dropdown component.
 *
 * @constructor
 * @param text the label of the dropdown button
 * @param elements an optional list of link elements (special options from [DD] enum class can be used as values)
 * @param icon the icon of the dropdown button
 * @param style the style of the dropdown button
 * @param direction the direction of the dropdown
 * @param disabled determines if the component is disabled on start
 * @param forNavbar determines if the component will be used in a navbar
 * @param forDropDown determines if the component will be used in a dropdown
 * @param classes a set of CSS class names
 */
@Suppress("TooManyFunctions")
open class DropDown(
    text: String, elements: List<StringPair>? = null, icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY, direction: Direction = Direction.DROPDOWN, disabled: Boolean = false,
    val forNavbar: Boolean = false, val forDropDown: Boolean = false, classes: Set<String> = setOf()
) : SimplePanel(classes) {
    /**
     * Label of the dropdown button.
     */
    var text
        get() = button.text
        set(value) {
            button.text = value
        }
    private var elements by refreshOnUpdate(elements) { setChildrenFromElements() }

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
     * The direction of the dropdown.
     */
    var direction by refreshOnUpdate(direction)

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
    val button: DropDownButton = DropDownButton(
        idc, text, icon, style, disabled, forNavbar, forDropDown
    )

    fun buttonId() = button.id

    internal val list: DropDownDiv = DropDownDiv(idc)

    init {
        if (forDropDown) {
            this.style = ButtonStyle.LIGHT
            this.direction = Direction.DROPRIGHT
        }
        setChildrenFromElements()
        this.addPrivate(button)
        this.addPrivate(list)
        counter++
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
                        Link(it.first, "javascript:void(0)", classes = setOf("dropdown-item", "disabled")).apply {
                            tabindex = -1
                            setAttribute("aria-disabled", "true")
                        }
                    }
                    else -> Link(it.first, it.second, classes = setOf("dropdown-item"))
                }
            }
            list.addAll(c)
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("show.bs.dropdown") { e, _ ->
            this.dispatchEvent("showBsDropdown", obj { detail = e })
        }
        this.getElementJQuery()?.on("shown.bs.dropdown") { e, _ ->
            this.dispatchEvent("shownBsDropdown", obj { detail = e })
        }
        this.getElementJQuery()?.on("hide.bs.dropdown") { e, _ ->
            this.dispatchEvent("hideBsDropdown", obj { detail = e })
        }
        this.getElementJQuery()?.on("hidden.bs.dropdown") { e, _ ->
            this.dispatchEvent("hiddenBsDropdown", obj { detail = e })
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (forNavbar) cl.add("nav-item" to true)
        cl.add(direction.direction to true)
        return cl
    }

    /**
     * Toggles dropdown visibility.
     */
    open fun toggle() {
        this.button.getElementJQuery()?.click()
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.dropDown(
    text: String, elements: List<StringPair>? = null, icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY, direction: Direction = Direction.DROPDOWN,
    disabled: Boolean = false, forNavbar: Boolean = false, forDropDown: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (DropDown.() -> Unit)? = null
): DropDown {
    val dropDown =
        DropDown(
            text,
            elements,
            icon,
            style,
            direction,
            disabled,
            forNavbar,
            forDropDown,
            classes ?: className.set
        ).apply { init?.invoke(this) }
    this.add(dropDown)
    return dropDown
}

/**
 * DSL builder extension function for a link in a dropdown list.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun DropDown.ddLink(
    label: String, url: String? = null, icon: String? = null, image: ResString? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(label, url, icon, image, null, true, (classes ?: className.set) + "dropdown-item").apply {
        init?.invoke(this)
    }
    this.add(link)
    return link
}

/**
 * DSL builder extension function for a link in a context menu list.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun ContextMenu.cmLink(
    label: String, url: String? = null, icon: String? = null, image: ResString? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(label, url, icon, image, null, true, (classes ?: className.set) + "dropdown-item").apply {
        init?.invoke(this)
    }
    this.add(link)
    return link
}

/**
 * DSL builder extension function for a disabled link in a dropdown list.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun DropDown.ddLinkDisabled(
    label: String, icon: String? = null, image: ResString? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(
        label,
        "javascript:void(0)",
        icon,
        image, null, true,
        (classes ?: className.set) + "dropdown-item" + "disabled"
    ).apply {
        tabindex = -1
        setAttribute("aria-disabled", "true")
        init?.invoke(this)
    }
    this.add(link)
    return link
}

/**
 * DSL builder extension function for a disabled link in a context menu list.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun ContextMenu.cmLinkDisabled(
    label: String, icon: String? = null, image: ResString? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(
        label,
        "javascript:void(0)",
        icon,
        image, null, true,
        (classes ?: className.set) + "dropdown-item" + "disabled"
    ).apply {
        tabindex = -1
        setAttribute("aria-disabled", "true")
        init?.invoke(this)
    }
    this.add(link)
    return link
}

/**
 * A drop down button component.
 *
 * @constructor
 * @param id the id of the element
 * @param text the dropdown button text
 * @param icon the icon of the dropdown button
 * @param style the style of the dropdown button
 * @param disabled determines if the component is disabled on start
 * @param forNavbar determines if the component will be used in a navbar
 * @param forDropDown determines if the component will be used in a dropdown
 * @param classes a set of CSS class names
 */
class DropDownButton(
    id: String,
    text: String,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    disabled: Boolean = false,
    val forNavbar: Boolean = false,
    val forDropDown: Boolean = false,
    classes: Set<String> = setOf()
) :
    Button(text, icon, style, ButtonType.BUTTON, disabled, null, true, classes) {

    init {
        this.id = id
        if (!forNavbar && !forDropDown) this.role = "button"
        setInternalEventListener<DropDownButton> {
            click = { e ->
                if (parent?.parent is ContextMenu) {
                    e.asDynamic().dropDownCM = true
                } else if (forDropDown || forNavbar) {
                    (parent as DropDown).list.getElementJQuery()?.toggle()
                    e.stopPropagation()
                }
            }
        }
    }

    override fun render(): VNode {
        val text = createLabelWithIcon(text, icon, image)
        return if (forNavbar || forDropDown) {
            render("a", text)
        } else {
            render("button", text)
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        return when {
            forNavbar -> listOf("nav-link" to true, "dropdown-toggle" to true)
            forDropDown -> super.getSnClass() + listOf("dropdown-item" to true, "dropdown-toggle" to true)
            else -> super.getSnClass() + ("dropdown-toggle" to true)
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        val inherited = super.getSnAttrs()
        return if (forDropDown || forNavbar) {
            inherited.filter { it.first != "type" }
        } else {
            inherited
        } + listOf(
            "data-toggle" to "dropdown", "aria-haspopup" to "true",
            "aria-expanded" to "false", "href" to "javascript:void(0)"
        )
    }
}

internal class DropDownDiv(private val ariaId: String) : Div(
    null, false, null, setOf("dropdown-menu")
) {
    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf("aria-labelledby" to ariaId)
    }
}
