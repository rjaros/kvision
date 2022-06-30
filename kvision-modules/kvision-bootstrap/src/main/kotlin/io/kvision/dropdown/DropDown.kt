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
package io.kvision.dropdown

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.DomAttribute
import io.kvision.core.ResString
import io.kvision.core.StringPair
import io.kvision.html.ButtonStyle
import io.kvision.html.Link
import io.kvision.panel.SimplePanel
import io.kvision.utils.obj
import org.w3c.dom.CustomEventInit

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
    DROPSTART("dropstart"),
    DROPEND("dropend"),

    @Deprecated("Use DROPSTART instead", ReplaceWith("DROPSTART"))
    DROPLEFT("dropstart"),

    @Deprecated("Use DROPEND instead", ReplaceWith("DROPEND"))
    DROPRIGHT("dropend"),
}

/**
 * Dropdown auto close.
 */
enum class AutoClose(override val attributeValue: String) : DomAttribute {
    TRUE("true"),
    OUTSIDE("outside"),
    INSIDE("inside"),
    FALSE("false"),
    ;

    override val attributeName: String
        get() = "data-bs-auto-close"
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
 * @param dark use dark background
 * @param rightAligned right align the dropdown menu
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class DropDown(
    text: String, elements: List<StringPair>? = null, icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY, direction: Direction = Direction.DROPDOWN, disabled: Boolean = false,
    val forNavbar: Boolean = false, val forDropDown: Boolean = false, dark: Boolean = false,
    rightAligned: Boolean = false,
    className: String? = null, init: (DropDown.() -> Unit)? = null
) : SimplePanel(className) {
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
     * Use dark background for the dropdown.
     */
    var dark
        get() = menu.dark
        set(value) {
            menu.dark = value
        }

    /**
     * Right align the dropdown menu.
     */
    var rightAligned
        get() = menu.rightAligned
        set(value) {
            menu.rightAligned = value
        }

    /**
     * The direction of the dropdown.
     */
    var direction by refreshOnUpdate(direction)

    /**
     * The auto closing mode of the dropdown menu.
     */
    var autoClose
        get() = button.autoClose
        set(value) {
            button.autoClose = value
        }

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

    val menu: DropDownMenu = DropDownMenu(idc, dark, rightAligned)

    init {
        if (forDropDown) {
            this.style = ButtonStyle.LIGHT
            this.direction = Direction.DROPEND
        }
        setChildrenFromElements()
        this.addPrivate(button)
        this.addPrivate(menu)
        counter++
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun add(child: Component): DropDown {
        menu.add(child)
        return this
    }

    override fun add(position: Int, child: Component): DropDown {
        menu.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): DropDown {
        menu.addAll(children)
        return this
    }

    override fun remove(child: Component): DropDown {
        menu.remove(child)
        return this
    }

    override fun removeAt(position: Int): DropDown {
        menu.removeAt(position)
        return this
    }

    override fun removeAll(): DropDown {
        menu.removeAll()
        return this
    }

    override fun disposeAll(): DropDown {
        menu.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return menu.getChildren()
    }

    private fun setChildrenFromElements() {
        menu.removeAll()
        elements?.let { elems ->
            val c = elems.map {
                when (it.second) {
                    DD.HEADER.option -> Header(it.first)
                    DD.SEPARATOR.option -> Separator()
                    DD.DISABLED.option -> {
                        Link(it.first, "javascript:void(0)", className = "dropdown-item disabled").apply {
                            tabindex = -1
                            setAttribute("aria-disabled", "true")
                        }
                    }
                    else -> Link(it.first, it.second, className = "dropdown-item")
                }
            }
            menu.addAll(c)
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (forNavbar) classSetBuilder.add("nav-item")
        classSetBuilder.add(direction.direction)
    }

    /**
     * Toggles dropdown visibility.
     */
    open fun toggle() {
        this.button.dispatchEvent("click", obj<CustomEventInit> {
            bubbles = true
        })
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
    dark: Boolean = false, rightAligned: Boolean = false, className: String? = null,
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
            dark,
            rightAligned,
            className,
            init
        )
    this.add(dropDown)
    return dropDown
}

/**
 * DSL builder extension function for a link in a dropdown list.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun DropDown.ddLink(
    label: String, url: String? = null, icon: String? = null, image: ResString? = null, dataNavigo: Boolean? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link =
        Link(
            label,
            url,
            icon,
            image,
            null,
            true,
            null,
            dataNavigo,
            (className?.let { "$it " } ?: "") + "dropdown-item",
            init)
    this.add(link)
    return link
}

/**
 * DSL builder extension function for a link in a context menu list.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun ContextMenu.cmLink(
    label: String, url: String? = null, icon: String? = null, image: ResString? = null, dataNavigo: Boolean? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link =
        Link(
            label,
            url,
            icon,
            image,
            null,
            true,
            null,
            dataNavigo,
            (className?.let { "$it " } ?: "") + "dropdown-item",
            init)
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
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(
        label,
        "javascript:void(0)",
        icon,
        image, null, true, null, null,
        (className?.let { "$it " } ?: "") + "dropdown-item disabled", init
    ).apply {
        tabindex = -1
        setAttribute("aria-disabled", "true")
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
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(
        label,
        "javascript:void(0)",
        icon,
        image, null, true, null, null,
        (className?.let { "$it " } ?: "") + "dropdown-item disabled", init
    ).apply {
        tabindex = -1
        setAttribute("aria-disabled", "true")
    }
    this.add(link)
    return link
}
