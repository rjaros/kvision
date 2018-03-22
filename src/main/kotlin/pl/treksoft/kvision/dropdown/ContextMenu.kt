/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.dropdown

import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Display
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.ListType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.px

/**
 * Context menu component.
 *
 * @constructor
 * @param classes a set of CSS class names
 */
open class ContextMenu(
    classes: Set<String> = setOf(), init: (ContextMenu.() -> Unit)? = null
) : ListTag(ListType.UL, classes = classes + "dropdown-menu") {

    init {
        @Suppress("LeakingThis")
        hide()
        @Suppress("LeakingThis")
        display = Display.BLOCK
        val root = Root.getLastRoot()
        if (root != null) {
            @Suppress("LeakingThis")
            root.addContextMenu(this)
        } else {
            println("At least one Root object is required to create a context menu!")
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * Positions and shows a context menu based on a mouse event.
     * @param mouseEvent mouse event
     * @return current context menu
     */
    open fun positionMenu(mouseEvent: MouseEvent): ContextMenu {
        this.top = mouseEvent.pageY.toInt().px
        this.left = mouseEvent.pageX.toInt().px
        this.show()
        return this
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Widget.contextMenu(
            classes: Set<String> = setOf(), init: (ContextMenu.() -> Unit)? = null
        ): ContextMenu {
            val contextMenu = ContextMenu(classes).apply { init?.invoke(this) }
            this.setContextMenu(contextMenu)
            return contextMenu
        }
    }
}
