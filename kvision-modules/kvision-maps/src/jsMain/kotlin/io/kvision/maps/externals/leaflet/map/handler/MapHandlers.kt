@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

package io.kvision.maps.externals.leaflet.map.handler

import io.kvision.maps.externals.leaflet.core.Handler
import io.kvision.maps.externals.leaflet.map.LeafletMap

/** Zooms to a box drawn with Shift-drag (`map.boxZoom`). */
open external class BoxZoomHandler : Handler {
    constructor(map: LeafletMap)
    /** Returns `true` if a box is being drawn or was just drawn. */
    open fun moved(): Boolean?
}

/** Zooms in on double click, and out on Shift-double click (`map.doubleClickZoom`). */
open external class DoubleClickZoomHandler : Handler {
    constructor(map: LeafletMap)
}

/** Makes the map draggable with panning inertia (`map.dragging`). */
open external class DragHandler : Handler {
    constructor(map: LeafletMap)
    /** Returns `true` if the current or last drag moved the map. */
    open fun moved(): Boolean?

    /** Returns `true` while the map is being dragged. */
    open fun moving(): Boolean?
}

/** Pans and zooms the map with the keyboard (`map.keyboard`). */
open external class KeyboardHandler : Handler {
    constructor(map: LeafletMap)
    companion object {
        /** The `KeyboardEvent.key` values for each action. */
        var keys: dynamic
    }
}

/** Zooms with the mouse scroll wheel (`map.scrollWheelZoom`). */
open external class ScrollWheelZoomHandler : Handler {
    constructor(map: LeafletMap)
}

/** Fires a `contextmenu` event on long tap (`map.tapHold`). */
open external class TapHoldHandler : Handler {
    constructor(map: LeafletMap)
}

/** Zooms with a two-finger pinch on touch devices (`map.pinchZoom`). */
open external class PinchZoomHandler : Handler {
    constructor(map: LeafletMap)
}
