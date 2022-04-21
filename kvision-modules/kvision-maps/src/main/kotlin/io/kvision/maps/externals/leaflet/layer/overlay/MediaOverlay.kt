package io.kvision.maps.externals.leaflet.layer.overlay

import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.InteractiveLayerOptions

/**
 * Artificial interface that does not represent a Leaflet type. It is used to align
 * [ImageOverlay], [SVGOverlay], and [VideoOverlay], because [getElement] and methods that return
 * `this` are dynamic.
 */
external interface MediaOverlay {
    fun getElement(): dynamic

    fun setOpacity(opacity: Number): dynamic /* this */
    fun bringToFront(): dynamic /* this */
    fun bringToBack(): dynamic /* this */
    fun setUrl(url: String): dynamic /* this */
    fun setBounds(bounds: LatLngBounds): dynamic /* this */
    fun setZIndex(value: Number): dynamic /* this */
    fun getBounds(): LatLngBounds
    fun getCenter(): LatLng?

    interface CanvasImageOverlayOptions : InteractiveLayerOptions {
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
