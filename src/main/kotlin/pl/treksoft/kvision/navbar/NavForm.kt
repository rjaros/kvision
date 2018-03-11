/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.navbar

import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * The Bootstrap Nav form container.
 *
 * @constructor
 * @param rightAlign determines if the nav form is aligned to the right
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class NavForm(rightAlign: Boolean = false, classes: Set<String> = setOf(), init: (NavForm.() -> Unit)? = null) :
    Tag(TAG.FORM, classes = classes) {

    /**
     * Determines if the nav form is aligned to the right.
     */
    var rightAlign by refreshOnUpdate(rightAlign)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("navbar-form" to true)
        if (rightAlign) {
            cl.add("navbar-right" to true)
        } else {
            cl.add("navbar-left" to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Navbar.navForm(
            rightAlign: Boolean = false, classes: Set<String> = setOf(), init: (NavForm.() -> Unit)? = null
        ): NavForm {
            val navForm = NavForm(rightAlign, classes).apply { init?.invoke(this) }
            this.add(navForm)
            return navForm
        }
    }
}
