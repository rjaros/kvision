@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.dom

import externals.leaflet.events.Evented
import org.w3c.dom.HTMLElement

open external class Draggable(
    element: HTMLElement,
    dragStartTarget: HTMLElement = definedExternally,
    preventOutline: Boolean = definedExternally
) : Evented {
    open fun enable()
    open fun disable()
    open fun finishDrag()
}
