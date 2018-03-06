/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.html

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair

/**
 * Icon component with support for FontAwesome and Bootstrap glyphicons.
 *
 * @constructor
 * @param icon icon name
 */
open class Icon(icon: String) : Tag(TAG.SPAN) {

    /**
     * Icon type.
     */
    var icon by refreshOnUpdate(icon)

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (icon.startsWith("fa-")) {
            cl.add("fa" to true)
            cl.add(icon to true)
        } else {
            cl.add("glyphicon" to true)
            cl.add("glyphicon-$icon" to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.icon(
            icon: String, init: (Icon.() -> Unit)? = null
        ): Icon {
            val i = Icon(icon).apply { init?.invoke(this) }
            this.add(i)
            return i
        }
    }
}
