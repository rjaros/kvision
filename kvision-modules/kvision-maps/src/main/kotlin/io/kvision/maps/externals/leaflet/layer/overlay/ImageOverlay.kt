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

package io.kvision.maps.externals.leaflet.layer.overlay

import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.InteractiveLayerOptions
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import org.w3c.dom.HTMLImageElement

/**
 * Used to load and display a single image over specific bounds of the map.
 */
open external class ImageOverlay(
    imageUrl: String,
    bounds: LatLngBounds,
    options: ImageOverlayOptions = definedExternally
) : Layer<ImageOverlayOptions>, MediaOverlay {

    override fun setOpacity(opacity: Number): ImageOverlay /* this */
    override fun bringToFront(): ImageOverlay /* this */
    override fun bringToBack(): ImageOverlay /* this */
    override fun setUrl(url: String): ImageOverlay /* this */
    override fun setBounds(bounds: LatLngBounds): ImageOverlay /* this */
    override fun setZIndex(value: Number): ImageOverlay /* this */
    override fun getBounds(): LatLngBounds
    override fun getElement(): HTMLImageElement?
    override fun getCenter(): LatLng?

    interface ImageOverlayOptions : InteractiveLayerOptions {
        var opacity: Number?
        var alt: String?
        /**
         * Whether the crossOrigin attribute will be added to the image. If a String is provided, the
         * image will have its `crossOrigin` attribute set to the String provided. This is needed if
         * you want to access image pixel data.
         *
         * Refer to [CORS Settings](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/crossorigin)
         * for valid String values.
         */
        var crossOrigin: dynamic /* Boolean? | String? */
        var errorOverlayUrl: String?
        var zIndex: Number?
        var className: String?
    }

}
