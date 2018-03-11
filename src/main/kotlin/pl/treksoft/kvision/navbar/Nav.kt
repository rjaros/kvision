/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.navbar

import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * The Bootstrap Nav container.
 *
 * @constructor
 * @param rightAlign determines if the nav is aligned to the right
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Nav(rightAlign: Boolean = false, classes: Set<String> = setOf(), init: (Nav.() -> Unit)? = null) :
    Tag(TAG.UL, classes = classes) {

    /**
     * Determines if the nav is aligned to the right.
     */
    var rightAlign by refreshOnUpdate(rightAlign)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("nav" to true)
        cl.add("navbar-nav" to true)
        if (rightAlign) {
            cl.add("navbar-right" to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Navbar.nav(
            rightAlign: Boolean = false, classes: Set<String> = setOf(), init: (Nav.() -> Unit)? = null
        ): Nav {
            val nav = Nav(rightAlign, classes).apply { init?.invoke(this) }
            this.add(nav)
            return nav
        }
    }
}
