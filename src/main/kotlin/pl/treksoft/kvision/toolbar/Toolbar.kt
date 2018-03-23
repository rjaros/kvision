/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.toolbar

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.panel.SimplePanel

/**
 * The Bootstrap toolbar.
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Toolbar(
    classes: Set<String> = setOf(), init: (Toolbar.() -> Unit)? = null
) : SimplePanel(classes + "btn-toolbar") {

    init {
        role = "toolbar"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.toolbar(
            classes: Set<String> = setOf(), init: (Toolbar.() -> Unit)? = null
        ): Toolbar {
            val toolbar = Toolbar(classes).apply { init?.invoke(this) }
            this.add(toolbar)
            return toolbar
        }
    }
}
