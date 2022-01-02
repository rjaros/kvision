@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.dom

import externals.leaflet.events.Evented
import org.w3c.dom.HTMLElement

/**
 * A class for making DOM elements draggable (including touch support). Used internally for map and
 * marker dragging. Only works for elements that were positioned with [DomUtil.setPosition].
 *
 * See [`https://leafletjs.com/reference.html#draggable`](https://leafletjs.com/reference.html#draggable)
 */
open external class Draggable(
    element: HTMLElement,
    dragStartTarget: HTMLElement = definedExternally,
    preventOutline: Boolean = definedExternally
) : Evented {
    /** Enables the dragging ability */
    open fun enable()
    /** Disables the dragging ability */
    open fun disable()
    open fun finishDrag()
}
