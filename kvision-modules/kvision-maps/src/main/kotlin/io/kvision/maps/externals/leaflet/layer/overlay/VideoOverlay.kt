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
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import org.w3c.dom.HTMLVideoElement

open external class VideoOverlay : Layer<VideoOverlay.VideoOverlayOptions>, MediaOverlay {

    constructor(
        video: String,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    constructor(
        video: Array<String>,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    constructor(
        video: HTMLVideoElement,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    override fun setOpacity(opacity: Number): VideoOverlay /* this */
    override fun bringToFront(): VideoOverlay /* this */
    override fun bringToBack(): VideoOverlay /* this */
    override fun setUrl(url: String): VideoOverlay /* this */
    override fun setBounds(bounds: LatLngBounds): VideoOverlay /* this */
    override fun setZIndex(value: Number): VideoOverlay /* this */
    override fun getBounds(): LatLngBounds
    override fun getCenter(): LatLng?
    override fun getElement(): HTMLVideoElement?

    interface VideoOverlayOptions : ImageOverlayOptions {
        var autoplay: Boolean?
        var loop: Boolean?
        var keepAspectRatio: Boolean?
        var muted: Boolean?
    }

}
