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

package io.kvision.maps.externals.leaflet.control

import io.kvision.maps.externals.leaflet.PositionsUnion
import io.kvision.maps.externals.leaflet.control.Control.ControlOptions
import io.kvision.maps.externals.leaflet.control.Control.LayersObject
import io.kvision.maps.externals.leaflet.core.Class
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

/**
 * `Control` is a base class for implementing map controls. Handles positioning. All other controls
 * extend from this class.
 *
 * See [`https://leafletjs.com/reference.html#control`](https://leafletjs.com/reference.html#control)
 */
@JsModule("leaflet")
@JsNonModule
abstract external class Control<T : ControlOptions>(
    options: T = definedExternally
) : Class {

    /** Returns the position of the control. */
    open fun getPosition(): PositionsUnion
    /** Sets the position of the control. */
    open fun setPosition(position: PositionsUnion): Control<T> /* this */
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
    open val onAdd: ((map: LeafletMap) -> HTMLElement)?
    /**
     * Optional method. Should contain all clean up code that removes the listeners previously
     * added in onAdd. Called on [remove].
     */
    open val onRemove: ((map: LeafletMap) -> Unit)?

    interface ControlOptions {
        /**
         * The position of the control (one of the map corners).
         *
         * Possible values are `topleft`, `topright`, `bottomleft` or `bottomright`
         */
        var position: PositionsUnion
    }

    /**
     * Object literal with layer names as keys and [Layer] objects as values.
     *
     * @see [LayersObject.set]
     * @see [LayersObject.get]
     */
    interface LayersObject
}

/** Native getter for [LayersObject] */
inline operator fun LayersObject.get(name: String): Layer<*>? =
    asDynamic()[name] as Layer<*>?

/** Native setter for [LayersObject] */
inline operator fun LayersObject.set(name: String, value: Layer<*>) {
    asDynamic()[name] = value
}
