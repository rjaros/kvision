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

package io.kvision.maps.externals.leaflet.layer.overlay

import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions

/**
 * Used to load and display a single image over specific bounds of the map.
 */
open external class ImageOverlay<T : ImageOverlayOptions>(
    imageUrl: String,
    bounds: LatLngBounds,
    options: T = definedExternally
) : Layer<T> {

    open fun setOpacity(opacity: Double): ImageOverlay<T> /* this */
    open fun setStyle(styleOpts: dynamic): ImageOverlay<T> /* this */
    open fun bringToFront(): ImageOverlay<T> /* this */
    open fun bringToBack(): ImageOverlay<T> /* this */
    open fun setUrl(url: String): ImageOverlay<T> /* this */
    open fun setBounds(bounds: LatLngBounds): ImageOverlay<T> /* this */
    open fun setZIndex(value: Int): ImageOverlay<T> /* this */
    fun getBounds(): LatLngBounds
    open fun getElement(): dynamic
    fun getCenter(): LatLng

    interface ImageOverlayOptions : InteractiveLayerOptions {
        var opacity: Double?
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
        var zIndex: Int?
        var className: String?

        /** Tells the browser how to decode the image. */
        var decoding: String?
    }

}
