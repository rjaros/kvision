@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geo

/**
 * Represents a geographical point with a certain latitude and longitude.
 *
 * See [`https://leafletjs.com/reference.html#latlng`](https://leafletjs.com/reference.html#latlng)
 */
open external class LatLng(
    latitude: Number,
    longitude: Number,
    altitude: Number = definedExternally
) {

    open var lat: Number
    open var lng: Number
    open var alt: Number?

    open fun equals(otherLatLng: LatLng, maxMargin: Number = definedExternally): Boolean
    open fun equals(otherLatLng: LatLng): Boolean

    open fun distanceTo(otherLatLng: LatLng): Number

    open fun wrap(): LatLng
    open fun toBounds(sizeInMeters: Number): LatLngBounds
    open fun clone(): LatLng
}
