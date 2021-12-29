 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.geo


open external class LatLng(
    latitude: Number,
    longitude: Number,
    altitude: Number = definedExternally
) {
    open fun equals(otherLatLng: LatLng, maxMargin: Number = definedExternally): Boolean
    open fun equals(otherLatLng: LatLng): Boolean

    open fun distanceTo(otherLatLng: LatLng): Number

    open fun wrap(): LatLng
    open fun toBounds(sizeInMeters: Number): LatLngBounds
    open fun clone(): LatLng
    open var lat: Number
    open var lng: Number
    open var alt: Number?

    override fun toString(): String
}
