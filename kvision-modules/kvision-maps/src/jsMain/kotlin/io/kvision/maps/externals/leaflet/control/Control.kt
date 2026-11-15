@file:JsModule("leaflet")
@file:Suppress("INTERFACE_WITH_SUPERCLASS")
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

package io.kvision.maps.externals.leaflet.control

import io.kvision.maps.externals.leaflet.control.Control.ControlOptions
import io.kvision.maps.externals.leaflet.core.Class
import io.kvision.maps.externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

/**
 * `Control` is a base class for implementing map controls. Handles positioning. All other controls
 * extend from this class.
 *
 * See [`https://leafletjs.com/reference.html#control`](https://leafletjs.com/reference.html#control)
 */
abstract external class Control<T : ControlOptions>(
    options: T = definedExternally
) : Class {

    /** Returns the position of the control. */
    open fun getPosition(): ControlPosition

    /** Sets the position of the control. */
    open fun setPosition(position: ControlPosition): Control<T> /* this */

    /** Returns the HTMLElement that contains the control. */
    open fun getContainer(): HTMLElement?

    /** Adds the control to the given map. */
    open fun addTo(map: LeafletMap): Control<T> /* this */

    /** Removes the control from the map it is currently active on. */
    open fun remove(): Control<T> /* this */

    /**
     * Should return the container DOM element for the control and add listeners on relevant map
     * events. Called on [addTo]
     */
    fun onAdd(map: LeafletMap): HTMLElement?

    /**
     * Optional method. Should contain all clean up code that removes the listeners previously
     * added in onAdd. Called on [remove].
     */
    fun onRemove(map: LeafletMap)

    interface ControlOptions {
        /**
         * The position of the control (one of the map corners).
         *
         * Possible values are `topleft`, `topright`, `bottomleft` or `bottomright`
         */
        var position: ControlPosition
    }

}
