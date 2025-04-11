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

package io.kvision.maps.externals.leaflet.layer.overlay

import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geometry.Point
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.DivOverlay.DivOverlayOptions
import io.kvision.maps.externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

open external class DivOverlay<T : DivOverlayOptions>(
    options: DivOverlayOptions = definedExternally,
    source: Layer<*> = definedExternally,
) : Layer<Layer.LayerOptions> {

    open fun openOn(map: LeafletMap)
    open fun close()
    open fun toggle(): DivOverlay<T>?
    open fun getLatLng(): LatLng?
    open fun setLatLng(latlng: LatLng): DivOverlay<T> /* this */
    open fun getContent(): dynamic /* String? | HTMLElement? */
    open fun setContent(htmlContent: (source: Layer<*>) -> Any): DivOverlay<T> /* this */
    open fun setContent(htmlContent: String): DivOverlay<T> /* this */
    open fun setContent(htmlContent: HTMLElement): DivOverlay<T> /* this */
    open fun getElement(): dynamic /* String? | HTMLElement? */
    open fun update()
    open fun isOpen(): Boolean
    open fun bringToFront(): DivOverlay<T> /* this */
    open fun bringToBack(): DivOverlay<T> /* this */

    interface DivOverlayOptions : InteractiveLayerOptions {
        /** The offset of the overlay position. */
        var offset: Point
        /** A custom CSS class name to assign to the overlay. */
        var className: String?
    }

}
