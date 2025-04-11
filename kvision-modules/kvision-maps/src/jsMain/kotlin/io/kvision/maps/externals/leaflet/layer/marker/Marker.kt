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

@file:JsModule("leaflet/dist/leaflet-src.esm.js")

package io.kvision.maps.externals.leaflet.layer.marker

import io.kvision.maps.externals.geojson.Feature
import io.kvision.maps.externals.geojson.Point
import io.kvision.maps.externals.leaflet.core.Handler
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement


/**
 * Marker is used to display clickable/draggable icons on the map.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#marker
 */
open external class Marker(
    latlng: LatLng,
    options: MarkerOptions = definedExternally,
) : Layer<Marker.MarkerOptions> {

    open var dragging: Handler?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<Point>
    open fun getLatLng(): LatLng
    open fun setLatLng(latlng: LatLng): Marker /* this */
    open fun setZIndexOffset(offset: Number): Marker /* this */
    open fun getIcon(): Icon<*>
    open fun setIcon(icon: Icon<*>): Marker /* this */
    open fun setOpacity(opacity: Number): Marker /* this */
    open fun getElement(): HTMLElement?

    interface MarkerOptions : InteractiveLayerOptions, DraggableMarkerOptions {
        /**
         * Icon instance to use for rendering the marker. See [Icon] documentation for details on
         * how to customize the marker icon. If not specified, a common instance of [Icon.Default]
         * is used.
         */
        var icon: Icon<*>?
        /** Whether the marker can be tabbed to with a keyboard and clicked by pressing enter.*/
        var keyboard: Boolean?
        /**
         * Text for the browser tooltip that appear on marker hover (no tooltip by default).
         * Useful for accessibility.
         */
        var title: String?
        /** Text for the alt attribute of the icon image. Useful for accessibility.*/
        var alt: String?
        /**
         * By default, marker images zIndex is set automatically based on its latitude.
         * Use this option if you want to put the marker on top of all others (or below),
         * specifying a high value like 1000 (or high negative value, respectively).
         */
        var zIndexOffset: Number?
        /** The opacity of the marker.*/
        var opacity: Number?
        /** If `true`, the marker will get on top of others when you hover the mouse over it.*/
        var riseOnHover: Boolean?
        /** The z-index offset used for the `riseOnHover` feature.*/
        var riseOffset: Number?
        /** Map pane where the markers shadow will be added.*/
        var shadowPane: String?
        /**
         * When true, the map will pan whenever the marker is focused (via e.g. pressing tab on
         * the keyboard) to ensure the marker is visible within the map's bounds.
         */
        var autoPanOnFocus: Boolean?
    }

    interface DraggableMarkerOptions {
        /** Whether the marker is draggable with mouse/touch or not. */
        var draggable: Boolean?
        /** Whether to pan the map when dragging this marker near its edge or not. */
        var autoPan: Boolean?
        /**
         * Distance (in pixels to the left/right and to the top/bottom) of the map edge to start
         * panning the map.
         */
        var autoPanPadding: Point
        /** Number of pixels the map should pan by. */
        var autoPanSpeed: Number?
    }

}
