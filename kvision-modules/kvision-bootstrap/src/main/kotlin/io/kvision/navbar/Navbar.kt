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
package io.kvision.navbar

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.BsBgColor
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.html.Link
import io.kvision.html.Span
import io.kvision.html.span
import io.kvision.panel.ContainerType
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode
import io.kvision.utils.obj
import org.w3c.dom.CustomEventInit
import org.w3c.dom.Element

/**
 * Navbar types.
 */
enum class NavbarType(override val className: String) : CssClass {
    FIXEDTOP("fixed-top"),
    FIXEDBOTTOM("fixed-bottom"),
    STICKYTOP("sticky-top")
}

/**
 * Navbar colors.
 */
enum class NavbarColor(internal val navbarColor: String) {
    LIGHT("navbar-light"),
    DARK("navbar-dark")
}

/**
 * Navbar responsive behavior.
 */
enum class NavbarExpand(override val className: String) : CssClass {
    ALWAYS("navbar-expand"),
    XL("navbar-expand-xl"),
    LG("navbar-expand-lg"),
    MD("navbar-expand-md"),
    SM("navbar-expand-sm"),
    XXL("navbar-expand-xxl"),
}

/**
 * The Bootstrap Navbar container.
 *
 * @constructor
 * @param label the navbar header label
 * @param link the navbar header link
 * @param type the navbar type
 * @param expand the navbar responsive behavior
 * @param nColor the navbar color
 * @param bgColor the navbar background color
 * @param collapseOnClick the navbar is auto collapsed when the link is clicked
 * @param dataNavigo the 'data-navigo' attribute for history API based JS routing
 * @param containerType the internal container type
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Navbar(
    label: String? = null,
    link: String? = null,
    type: NavbarType? = null,
    expand: NavbarExpand? = NavbarExpand.LG,
    nColor: NavbarColor? = null,
    bgColor: BsBgColor = BsBgColor.BODYTERTIARY,
    collapseOnClick: Boolean = false,
    dataNavigo: Boolean? = null,
    containerType: ContainerType = ContainerType.FLUID,
    className: String? = null, init: (Navbar.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * The navbar header label.
     */
    var label
        get() = if (brandLink.visible) brandLink.label else null
        set(value) {
            if (value != null) {
                brandLink.label = value
                brandLink.show()
            } else {
                brandLink.hide()
            }
        }

    /**
     * The navbar header link.
     */
    var link
        get() = brandLink.url
        set(value) {
            brandLink.url = value
        }

    /**
     * The 'data-navigo' attribute for history API based JS routing.
     */
    var dataNavigo
        get() = brandLink.dataNavigo
        set(value) {
            brandLink.dataNavigo = value
        }

    /**
     * The navbar type.
     */
    var type by refreshOnUpdate(type)

    /**
     * The navbar responsive behavior.
     */
    var expand by refreshOnUpdate(expand)

    /**
     * The navbar color.
     */
    var nColor by refreshOnUpdate(nColor)

    /**
     * The navbar background color.
     */
    var bgColor by refreshOnUpdate(bgColor)

    private val idc = "kv_navbar_$counter"

    private val brandLink = Link(label ?: "", link, dataNavigo = dataNavigo, className = "navbar-brand")
    private val toggler = NavbarButton(idc)
    internal val container = SimplePanel("collapse navbar-collapse") {
        id = this@Navbar.idc
    }
    internal val extContainer = SimplePanel(containerType.type)

    init {
        extContainer.add(brandLink)
        extContainer.add(toggler)
        extContainer.add(container)
        addPrivate(extContainer)
        if (label == null) brandLink.hide()
        counter++
        if (collapseOnClick) {
            setInternalEventListener<Navbar> {
                click = {
                    val target = it.target.unsafeCast<Element>()
                    if (target.matches("a.nav-item.nav-link") || target.matches("a.dropdown-item")) {
                        if (container.getElement()?.unsafeCast<Element>()?.matches(".show") == true) {
                            toggler.dispatchEvent("click", obj<CustomEventInit> {
                                bubbles = true
                            })
                        }
                    }
                }
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("nav", childrenVNodes())
    }

    override fun add(child: Component) {
        container.add(child)
    }

    override fun add(position: Int, child: Component) {
        container.add(position, child)
    }

    override fun addAll(children: List<Component>) {
        container.addAll(children)
    }

    override fun remove(child: Component) {
        container.remove(child)
    }

    override fun removeAt(position: Int) {
        container.removeAt(position)
    }

    override fun removeAll() {
        container.removeAll()
    }

    override fun disposeAll() {
        container.disposeAll()
    }

    override fun getChildren(): List<Component> {
        return container.getChildren()
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        if (nColor == NavbarColor.DARK) attributeSetBuilder.add("data-bs-theme", "dark")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add("navbar")
        classSetBuilder.add(type)
        classSetBuilder.add(expand)
        if (nColor == NavbarColor.LIGHT) classSetBuilder.add(nColor!!.navbarColor)
        classSetBuilder.add(bgColor.className)
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
fun Container.navbar(
    label: String? = null,
    link: String? = null,
    type: NavbarType? = null,
    expand: NavbarExpand? = NavbarExpand.LG,
    nColor: NavbarColor? = null,
    bgColor: BsBgColor = BsBgColor.BODYTERTIARY,
    collapseOnClick: Boolean = false,
    dataNavigo: Boolean? = null,
    containerType: ContainerType = ContainerType.FLUID,
    className: String? = null,
    init: (Navbar.() -> Unit)? = null
): Navbar {
    val navbar = Navbar(label, link, type, expand, nColor, bgColor, collapseOnClick, dataNavigo, containerType, className, init)
    this.add(navbar)
    return navbar
}

fun Navbar.navText(
    label: String,
    className: String? = null
): Span {
    val text = Span(label, className = (className?.let { "$it " } ?: "") + "navbar-text")
    this.add(text)
    return text
}

/**
 * @suppress
 * Internal component.
 * The Bootstrap Navbar header button.
 */
internal class NavbarButton(private val idc: String, private val toggle: String = "Toggle navigation") :
    SimplePanel("navbar-toggler") {

    init {
        span(className = "navbar-toggler-icon")
    }

    override fun render(): VNode {
        return render("button", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", "button")
        attributeSetBuilder.add("data-bs-toggle", "collapse")
        attributeSetBuilder.add("data-bs-target", "#$idc")
        attributeSetBuilder.add("aria-controls", idc)
        attributeSetBuilder.add("aria-expanded", "false")
        attributeSetBuilder.add("aria-label", toggle)
    }
}
