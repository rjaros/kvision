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

import io.kvision.core.Display
import io.kvision.core.Widget
import io.kvision.panel.Root
import io.kvision.panel.SimplePanel
import io.kvision.utils.auto
import io.kvision.utils.px
import org.w3c.dom.events.MouseEvent

/**
 * Context menu component.
 *
 * @constructor
 * @param element an element to bind
 * @param fixedPosition use fixed positioning
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class ContextMenu(
    element: Widget? = null,
    protected val fixedPosition: Boolean = false,
    className: String? = null, init: (ContextMenu.() -> Unit)? = null
) : SimplePanel((className?.let { "$it " } ?: "") + "dropdown-menu") {

    init {
        @Suppress("LeakingThis")
        hide()
        @Suppress("LeakingThis")
        display = Display.BLOCK
        width = auto
        val root = element?.getRoot() ?: Root.getLastRoot()
        if (root != null) {
            @Suppress("LeakingThis")
            root.addContextMenu(this)
        } else {
            console.log("At least one Root object is required to create a context menu!")
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
        if (fixedPosition) {
            this.top = DEFAULT_FIXED_POS_Y.px
            this.left = DEFAULT_FIXED_POS_X.px
        } else {
            this.top = mouseEvent.pageY.toInt().px
            this.left = mouseEvent.pageX.toInt().px
        }
        this.show()
        return this
    }

    companion object {
        const val DEFAULT_FIXED_POS_X = 5
        const val DEFAULT_FIXED_POS_Y = 5
    }
}

/**
 * Sets context menu for the current widget.
 * @param contextMenu a context menu
 * @return current widget
 */
fun Widget.setContextMenu(contextMenu: ContextMenu): Widget {
    this.setEventListener<Widget> {
        contextmenu = { e: MouseEvent ->
            e.preventDefault()
            contextMenu.positionMenu(e)
        }
    }
    return this
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Widget.contextMenu(
    fixedPosition: Boolean = false,
    className: String? = null,
    init: (ContextMenu.() -> Unit)? = null
): ContextMenu {
    val contextMenu = ContextMenu(this, fixedPosition, className, init)
    this.setContextMenu(contextMenu)
    return contextMenu
}
