/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.navbar

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag.Companion.tag
import pl.treksoft.kvision.panel.SimplePanel

/**
 * Navbar types.
 */
enum class NavbarType(internal val navbarType: String) {
    FIXEDTOP("navbar-fixed-top"),
    FIXEDBOTTOM("navbar-fixed-bottom"),
    STATICTOP("navbar-static-top")
}

/**
 * The Bootstrap Navbar container.
 *
 * @constructor
 * @param label the navbar label
 * @param type the navbar type
 * @param inverted determines if the navbar is inverted
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Navbar(
    label: String = "",
    type: NavbarType? = null,
    inverted: Boolean = false,
    classes: Set<String> = setOf(), init: (Navbar.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * The navbar header label.
     */
    var label
        get() = brandLink.label
        set(value) {
            brandLink.label = value
        }

    /**
     * The navbar type.
     */
    var type by refreshOnUpdate(type)
    /**
     * Determines if the navbar is inverted.
     */
    var inverted by refreshOnUpdate(inverted)

    private val idc = "kv_navbar_$counter"

    private val brandLink = Link(label, "#", classes = setOf("navbar-brand"))
    internal val container = SimplePanel(setOf("collapse", "navbar-collapse")) {
        id = idc
    }

    init {
        val c = SimplePanel(setOf("container-fluid")) {
            simplePanel(setOf("navbar-header")) {
                add(NavbarButton(idc))
                add(brandLink)
            }
            add(container)
        }
        addInternal(c)
        counter++
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

    override fun addAll(children: List<Component>): Navbar {
        container.addAll(children)
        return this
    }

    override fun remove(child: Component): Navbar {
        container.remove(child)
        return this
    }

    override fun removeAll(): Navbar {
        container.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return container.getChildren()
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("navbar" to true)
        type?.let {
            cl.add(it.navbarType to true)
        }
        if (inverted) {
            cl.add("navbar-inverse" to true)
        } else {
            cl.add("navbar-default" to true)
        }
        return cl
    }

    companion object {
        internal var counter = 0

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.navbarPanel(
            text: String = "",
            type: NavbarType? = null,
            inverted: Boolean = false,
            classes: Set<String> = setOf(), init: (Navbar.() -> Unit)? = null
        ): Navbar {
            val navbarPanel = Navbar(text, type, inverted, classes, init)
            this.add(navbarPanel)
            return navbarPanel
        }
    }
}

/**
 * @suppress
 * Internal component.
 * The Bootstrap Navbar header button.
 */
internal class NavbarButton(private val idc: String, toggle: String = "Toggle navigation") :
    SimplePanel(setOf("navbar-toggle", "collapsed")) {

    init {
        tag(TAG.SPAN, toggle, classes = setOf("sr-only"))
        tag(TAG.SPAN, classes = setOf("icon-bar"))
        tag(TAG.SPAN, classes = setOf("icon-bar"))
        tag(TAG.SPAN, classes = setOf("icon-bar"))
    }

    override fun render(): VNode {
        return render("button", childrenVNodes())
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + listOf(
            "type" to "button",
            "data-toggle" to "collapse",
            "data-target" to "#$idc",
            "aria-expanded" to "false"
        )
    }
}
