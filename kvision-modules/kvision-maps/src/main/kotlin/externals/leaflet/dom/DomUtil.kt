@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.dom

import externals.leaflet.geometry.Point
import org.w3c.dom.HTMLElement

/**
 * Utility functions to work with the [DOM](https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model)
 * tree, used by Leaflet internally.
 *
 * Most functions expecting or returning a [HTMLElement] also work for SVG elements. The only
 * difference is that classes refer to CSS classes in HTML and SVG classes in SVG.
 *
 * See [`https://leafletjs.com/reference.html#domutil`](https://leafletjs.com/reference.html#domutil)
 */
external object DomUtil {

    /** Vendor-prefixed transform style name (e.g. 'webkitTransform' for WebKit). */
    val TRANSFORM: String
    /** Vendor-prefixed transition style name. */
    val TRANSITION: String
    /** Vendor-prefixed transitionend event name. */
    val TRANSITION_END: String

    /** Get Element by the given HTML-Element */
    fun get(element: HTMLElement): HTMLElement?
    /** Get Element by its ID */
    fun get(element: String): HTMLElement?
    fun getStyle(el: HTMLElement, styleAttrib: String): String?
    /**
     * Creates an HTML element with `tagName`, sets its class to `className`, and optionally
     * appends it to `container` element.
     *
     * @param tagName The name of the tag to create (for example: `div` or `canvas`).
     * @param className The class to set on the created element.
     * @param container The container to append the created element to.
     */
    fun create(
        tagName: String,
        className: String = definedExternally,
        container: HTMLElement = definedExternally
    ): HTMLElement

    fun remove(el: HTMLElement)
    fun empty(el: HTMLElement)
    fun toFront(el: HTMLElement)
    fun toBack(el: HTMLElement)
    fun hasClass(el: HTMLElement, name: String): Boolean
    fun addClass(el: HTMLElement, name: String)
    fun removeClass(el: HTMLElement, name: String)
    fun setClass(el: HTMLElement, name: String)
    fun getClass(el: HTMLElement): String
    fun setOpacity(el: HTMLElement, opacity: Number)
    fun testProp(props: Array<String>): dynamic // string | false
    fun setTransform(el: HTMLElement, offset: Point, scale: Number = definedExternally)
    /**
     * Sets the position of el to coordinates specified by position, using CSS translate or
     * top/left positioning depending on the browser (used by Leaflet internally to position its
     * layers).
     */
    fun setPosition(el: HTMLElement, position: Point)
    /** Returns the coordinates of an element previously positioned with [setPosition]. */
    fun getPosition(el: HTMLElement): Point
    fun disableTextSelection()
    fun enableTextSelection()
    fun disableImageDrag()
    fun enableImageDrag()
    fun preventOutline(el: HTMLElement)
    fun restoreOutline()
}
