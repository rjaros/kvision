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

@file:JsModule("leaflet")
@file:JsNonModule

package io.kvision.maps.externals.leaflet.events

import io.kvision.maps.externals.leaflet.geo.Coords
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.geometry.Point
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.Popup
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

external interface LeafletEvent {
    var type: String
    var target: Any
    var sourceTarget: Any
    /** Replacement for `var layer: Any` */
    var propagatedFrom: Any
}

external interface LeafletMouseEvent : LeafletEvent {
    var latlng: LatLng
    var layerPoint: Point
    var containerPoint: Point
    var originalEvent: MouseEvent
}

external interface LeafletKeyboardEvent : LeafletEvent {
    var originalEvent: KeyboardEvent
}

external interface LocationEvent : LeafletEvent {
    var latlng: LatLng
    var bounds: LatLngBounds
    var accuracy: Number
    var altitude: Number
    var altitudeAccuracy: Number
    var heading: Number
    var speed: Number
    var timestamp: Number
}

external interface ErrorEvent : LeafletEvent {
    var message: String
    var code: Number
}

external interface LayerEvent : LeafletEvent {
    var layer: Layer<*>
}

external interface LayersControlEvent : LayerEvent {
    var name: String
}

external interface TileEvent : LeafletEvent {
    var tile: HTMLImageElement
    var coords: Coords
}

external interface TileErrorEvent : TileEvent {
    var error: Error
}

external interface ResizeEvent : LeafletEvent {
    var oldSize: Point
    var newSize: Point
}

external interface GeoJSONEvent : LeafletEvent {
    var layer: Layer<*>
    var properties: Any
    var geometryType: String
    var id: String
}

external interface PopupEvent : LeafletEvent {
    var popup: Popup
}

external interface TooltipEvent : LeafletEvent {
    var tooltip: Tooltip
}

external interface DragEndEvent : LeafletEvent {
    var distance: Number
}

external interface ZoomAnimEvent : LeafletEvent {
    var center: LatLng
    var zoom: Number
    var noUpdate: Boolean?
}
