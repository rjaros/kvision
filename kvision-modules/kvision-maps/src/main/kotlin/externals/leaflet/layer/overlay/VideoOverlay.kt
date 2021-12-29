@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLVideoElement

open external class VideoOverlay : Layer {
    constructor(
        video: String,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    constructor(video: String, bounds: LatLngBounds)
    constructor(
        video: Array<String>,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    constructor(video: Array<String>, bounds: LatLngBounds)
    constructor(
        video: HTMLVideoElement,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    constructor(video: HTMLVideoElement, bounds: LatLngBounds)

    open fun setOpacity(opacity: Number): VideoOverlay /* this */
    open fun bringToFront(): VideoOverlay /* this */
    open fun bringToBack(): VideoOverlay /* this */
    open fun setUrl(url: String): VideoOverlay /* this */
    open fun setBounds(bounds: LatLngBounds): VideoOverlay /* this */
    open fun getBounds(): LatLngBounds
    open fun getElement(): HTMLVideoElement?
    open var options: VideoOverlayOptions
}
