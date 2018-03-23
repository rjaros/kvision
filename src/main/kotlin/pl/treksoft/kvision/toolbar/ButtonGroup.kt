/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.toolbar

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.panel.SimplePanel

/**
 * Button group sizes.
 */
enum class ButtonGroupSize(internal val className: String) {
    LARGE("btn-group-lg"),
    SMALL("btn-group-sm"),
    XSMALL("btn-group-xs")
}

/**
 * Button group styles.
 */
enum class ButtonGroupStyle(internal val className: String) {
    VERTICAL("btn-group-vertical"),
    JUSTIFIED("btn-group-justified")
}

/**
 * The Bootstrap button group.
 *
 * @constructor
 * @param size button group size
 * @param style button group style
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class ButtonGroup(
    size: ButtonGroupSize? = null, style: ButtonGroupStyle? = null,
    classes: Set<String> = setOf(), init: (ButtonGroup.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * Button group size.
     */
    var size by refreshOnUpdate(size)
    /**
     * Button group style.
     */
    var style by refreshOnUpdate(style)

    init {
        role = "group"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (style != ButtonGroupStyle.VERTICAL) {
            cl.add("btn-group" to true)
        }
        style?.let {
            cl.add(it.className to true)
        }
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.buttonGroup(
            size: ButtonGroupSize? = null, style: ButtonGroupStyle? = null,
            classes: Set<String> = setOf(), init: (ButtonGroup.() -> Unit)? = null
        ): ButtonGroup {
            val group = ButtonGroup(size, style, classes).apply { init?.invoke(this) }
            this.add(group)
            return group
        }
    }
}
