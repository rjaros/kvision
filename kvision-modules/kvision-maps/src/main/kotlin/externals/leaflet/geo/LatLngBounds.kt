 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.geo


open external class LatLngBounds(southWest: LatLng, northEast: LatLng) {
    open fun extend(latLng: LatLng): LatLngBounds /* this */
    open fun extend(latLngBounds: LatLngBounds): LatLngBounds /* this */
    open fun pad(bufferRatio: Number): LatLngBounds
    open fun getCenter(): LatLng
    open fun getSouthWest(): LatLng
    open fun getNorthEast(): LatLng
    open fun getNorthWest(): LatLng
    open fun getSouthEast(): LatLng
    open fun getWest(): Number
    open fun getSouth(): Number
    open fun getEast(): Number
    open fun getNorth(): Number
    open fun contains(otherBoundsOrLatLng: LatLngBounds): Boolean
    open fun contains(otherBoundsOrLatLng: LatLng): Boolean
    open fun intersects(otherBounds: LatLngBounds): Boolean
    open fun overlaps(otherBounds: LatLngBounds): Boolean
    open fun toBBoxString(): String
    open fun equals(otherBounds: LatLngBounds): Boolean
    open fun isValid(): Boolean
}
