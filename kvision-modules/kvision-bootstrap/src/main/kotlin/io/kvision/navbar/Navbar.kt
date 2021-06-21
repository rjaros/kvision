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

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.BsBgColor
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.html.Link
import io.kvision.html.Span
import io.kvision.html.span
import io.kvision.jquery.invoke
import io.kvision.jquery.jQuery
import io.kvision.panel.SimplePanel

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
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Navbar(
    label: String? = null,
    link: String? = null,
    type: NavbarType? = null,
    expand: NavbarExpand? = NavbarExpand.LG,
    nColor: NavbarColor = NavbarColor.LIGHT,
    bgColor: BsBgColor = BsBgColor.LIGHT,
    collapseOnClick: Boolean = false,
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

    private val brandLink = Link(label ?: "", link, className = "navbar-brand")
    internal val container = SimplePanel("collapse navbar-collapse") {
        id = this@Navbar.idc
    }

    init {
        addPrivate(brandLink)
        addPrivate(NavbarButton(idc))
        addPrivate(container)
        if (label == null) brandLink.hide()
        counter++
        if (collapseOnClick) {
            setInternalEventListener<Navbar> {
                click = {
                    val target = jQuery(it.target)
                    if (target.`is`("a.nav-item.nav-link") || target.`is`("a.dropdown-item")) {
                        val navbar = target.parents("nav.navbar").first()
                        val toggler = navbar.children("button.navbar-toggler")
                        val collapse = navbar.children("div.navbar-collapse")
                        if (collapse.`is`(".show")) toggler.click()
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

    override fun add(child: Component): Navbar {
        container.add(child)
        return this
    }

    override fun add(position: Int, child: Component): Navbar {
        container.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): Navbar {
        container.addAll(children)
        return this
    }

    override fun remove(child: Component): Navbar {
        container.remove(child)
        return this
    }

    override fun removeAt(position: Int): Navbar {
        container.removeAt(position)
        return this
    }

    override fun removeAll(): Navbar {
        container.removeAll()
        return this
    }

    override fun disposeAll(): Navbar {
        container.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return container.getChildren()
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add("navbar")
        classSetBuilder.add(type)
        classSetBuilder.add(expand)
        classSetBuilder.add(nColor.navbarColor)
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
    nColor: NavbarColor = NavbarColor.LIGHT,
    bgColor: BsBgColor = BsBgColor.LIGHT,
    collapseOnClick: Boolean = false,
    className: String? = null,
    init: (Navbar.() -> Unit)? = null
): Navbar {
    val navbar = Navbar(label, link, type, expand, nColor, bgColor, collapseOnClick, className, init)
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
        attributeSetBuilder.add("data-toggle", "collapse")
        attributeSetBuilder.add("data-target", "#$idc")
        attributeSetBuilder.add("aria-controls", idc)
        attributeSetBuilder.add("aria-expanded", "false")
        attributeSetBuilder.add("aria-label", toggle)
    }
}
