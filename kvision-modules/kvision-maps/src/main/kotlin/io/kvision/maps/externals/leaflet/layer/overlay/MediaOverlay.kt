package io.kvision.maps.externals.leaflet.layer.overlay

import io.kvision.maps.externals.leaflet.geo.LatLngBounds

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
}
