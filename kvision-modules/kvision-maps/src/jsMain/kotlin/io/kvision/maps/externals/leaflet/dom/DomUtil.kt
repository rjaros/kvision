@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

package io.kvision.maps.externals.leaflet.dom

import io.kvision.maps.externals.leaflet.geometry.Point
import org.w3c.dom.Element
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

    /** Get Element by the given HTML-Element */
    fun get(element: HTMLElement): HTMLElement?

    /** Get Element by its ID */
    fun get(element: String): HTMLElement?

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

    fun toFront(el: Element)
    fun toBack(el: Element)
    fun setTransform(el: Element, offset: Point, scale: Number = definedExternally)

    /**
     * Sets the position of el to coordinates specified by position, using CSS translate or
     * top/left positioning depending on the browser (used by Leaflet internally to position its
     * layers).
     */
    fun setPosition(el: Element, position: Point, flat: Boolean = definedExternally)

    /** Returns the coordinates of an element previously positioned with [setPosition]. */
    fun getPosition(el: Element): Point
    fun disableImageDrag()
    fun enableImageDrag()

    /**
     * Finds the closest parent node which size (width and height) is not null.
     */
    fun getSizedParentNode(element: HTMLElement): HTMLElement

    /**
     * Computes the CSS scale currently applied on the element.
     */
    fun getScale(el: HTMLElement): Point
}
