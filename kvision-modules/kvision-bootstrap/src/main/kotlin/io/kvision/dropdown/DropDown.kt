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

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.ResString
import io.kvision.core.StringPair
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.ButtonType
import io.kvision.html.Div
import io.kvision.html.Link
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.obj
import io.kvision.utils.set

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
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class DropDown(
    text: String, elements: List<StringPair>? = null, icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY, direction: Direction = Direction.DROPDOWN, disabled: Boolean = false,
    val forNavbar: Boolean = false, val forDropDown: Boolean = false,
    classes: Set<String> = setOf(), init: (DropDown.() -> Unit)? = null
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
        @Suppress("LeakingThis")
        init?.invoke(this)
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

    override fun disposeAll(): SimplePanel {
        list.disposeAll()
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

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (forNavbar) classSetBuilder.add("nav-item")
        classSetBuilder.add(direction.direction)
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
            classes ?: className.set,
            init
        )
    this.add(dropDown)
    return dropDown
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.dropDown(
    state: ObservableState<S>,
    text: String, elements: List<StringPair>? = null, icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY, direction: Direction = Direction.DROPDOWN,
    disabled: Boolean = false, forNavbar: Boolean = false, forDropDown: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (DropDown.(S) -> Unit)
) = dropDown(
    text,
    elements,
    icon,
    style,
    direction,
    disabled,
    forNavbar,
    forDropDown,
    classes,
    className
).bind(state, true, init)

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
    val link = Link(label, url, icon, image, null, true, null, (classes ?: className.set) + "dropdown-item", init)
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
    val link = Link(label, url, icon, image, null, true, null, (classes ?: className.set) + "dropdown-item", init)
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
        image, null, true, null,
        (classes ?: className.set) + "dropdown-item" + "disabled", init
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
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link = Link(
        label,
        "javascript:void(0)",
        icon,
        image, null, true, null,
        (classes ?: className.set) + "dropdown-item" + "disabled", init
    ).apply {
        tabindex = -1
        setAttribute("aria-disabled", "true")
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
                } else if (forDropDown) {
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

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        classSetBuilder.add("dropdown-toggle")
        when {
            forNavbar -> classSetBuilder.add("nav-link")
            forDropDown -> classSetBuilder.run { super.buildClassSet(this); add("dropdown-item") }
            else -> super.buildClassSet(classSetBuilder)
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(
            if (forDropDown || forNavbar) {
                object : AttributeSetBuilder {
                    override fun add(name: String, value: String) {
                        if (name != "type") attributeSetBuilder.add(name, value)
                    }
                }
            } else {
                attributeSetBuilder
            }
        )
        attributeSetBuilder.add("data-toggle", "dropdown")
        attributeSetBuilder.add("aria-haspopup", "true")
        attributeSetBuilder.add("aria-expanded", "false")
        attributeSetBuilder.add("href", "javascript:void(0)")
    }
}

internal class DropDownDiv(private val ariaId: String) : Div(
    null, false, null, setOf("dropdown-menu")
) {

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("aria-labelledby", ariaId)
    }
}
