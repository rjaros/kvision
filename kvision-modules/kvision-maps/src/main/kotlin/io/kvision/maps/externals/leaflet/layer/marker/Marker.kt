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

package io.kvision.maps.externals.leaflet.layer.marker


import io.kvision.maps.externals.geojson.Feature
import io.kvision.maps.externals.geojson.Point
import io.kvision.maps.externals.leaflet.core.Handler
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.layer.InteractiveLayerOptions
import io.kvision.maps.externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement


open external class Marker(
    latlng: LatLng,
    options: MarkerOptions = definedExternally,
) : Layer<Marker.MarkerOptions> {

    open var dragging: Handler?
    open var feature: Feature<Point>?
    open var _shadow: HTMLElement?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<Point>
    open fun getLatLng(): LatLng
    open fun setLatLng(latlng: LatLng): Marker /* this */
    open fun setZIndexOffset(offset: Number): Marker /* this */
    open fun getIcon(): Icon<*>
    open fun setIcon(icon: DivIcon): Marker /* this */
    open fun setOpacity(opacity: Number): Marker /* this */
    open fun getElement(): HTMLElement?

    interface MarkerOptions : InteractiveLayerOptions {
        var icon: Icon<*>
        var draggable: Boolean?
        var keyboard: Boolean?
        var title: String?
        var alt: String?
        var zIndexOffset: Number?
        var opacity: Number?
        var riseOnHover: Boolean?
        var riseOffset: Number?
        var shadowPane: String?
        var autoPan: Boolean?
        var autoPanPadding: Point
        var autoPanSpeed: Number?
    }

}
