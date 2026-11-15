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

package io.kvision.maps.externals.leaflet.events

import io.kvision.maps.externals.leaflet.geo.Coords
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.geometry.Point
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.Popup
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

external interface LeafletEvent {
    var type: String
    var target: Evented
    var sourceTarget: Evented

    /** For propagated events, the last object that propagated the event to its event parent. */
    var propagatedFrom: Evented?
}

external interface LeafletPointerEvent : LeafletEvent {
    var latlng: LatLng
    var layerPoint: Point
    var containerPoint: Point
    var originalEvent: MouseEvent /* MouseEvent | PointerEvent */
}

external interface LeafletKeyboardEvent : LeafletEvent {
    var originalEvent: KeyboardEvent
}

external interface LocationEvent : LeafletEvent {
    var latlng: LatLng
    var bounds: LatLngBounds
    var accuracy: Double
    var altitude: Double?
    var altitudeAccuracy: Double?
    var heading: Double?
    var speed: Double?
    var timestamp: Double
}

external interface ErrorEvent : LeafletEvent {
    var message: String
    var code: Int
}

external interface LayerEvent : LeafletEvent {
    var layer: Layer<*>
}

external interface LayersControlEvent : LayerEvent {
    var name: String
}

external interface TileEvent : LeafletEvent {
    var tile: HTMLElement
    var coords: Coords
}

external interface TileErrorEvent : TileEvent {
    var error: dynamic
}

external interface ResizeEvent : LeafletEvent {
    var oldSize: Point
    var newSize: Point
}

external interface GeoJSONEvent : LeafletEvent {
    var layer: Layer<*>
    var properties: dynamic
    var geometryType: String
    var id: dynamic /* String | Number */
}

external interface PopupEvent : LeafletEvent {
    var popup: Popup
}

external interface TooltipEvent : LeafletEvent {
    var tooltip: Tooltip
}

external interface DragEndEvent : LeafletEvent {
    var distance: Double
    var noInertia: Boolean?
}

external interface ZoomAnimEvent : LeafletEvent {
    var center: LatLng
    var zoom: Int
    var noUpdate: Boolean
}

external interface ViewChangeEvent : LeafletEvent {
    var originalEvent: Event?
    var flyTo: Boolean?
    var pinch: Boolean?
}

external interface ViewPreResetEvent : LeafletEvent {
    var center: LatLng
    var zoom: Int
}

external interface WorldCopyJumpEvent : LeafletEvent {
    var worlds: Double
}

external interface BoxZoomEvent : LeafletEvent {
    var boxZoomBounds: LatLngBounds
}

external interface MarkerMoveEvent : LeafletEvent {
    var oldLatLng: LatLng
    var latlng: LatLng
}
