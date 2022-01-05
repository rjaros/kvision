@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import org.w3c.dom.HTMLVideoElement

open external class VideoOverlay : Layer<VideoOverlay.VideoOverlayOptions> {

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

    open fun setOpacity(opacity: Number): VideoOverlay /* this */
    open fun bringToFront(): VideoOverlay /* this */
    open fun bringToBack(): VideoOverlay /* this */
    open fun setUrl(url: String): VideoOverlay /* this */
    open fun setBounds(bounds: LatLngBounds): VideoOverlay /* this */
    open fun getBounds(): LatLngBounds
    open fun getElement(): HTMLVideoElement?

    interface VideoOverlayOptions : ImageOverlayOptions {
        var autoplay: Boolean?
        var loop: Boolean?
        var keepAspectRatio: Boolean?
        var muted: Boolean?
    }

}
