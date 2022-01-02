@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds


open external class Circle(
    latlng: LatLng,
    options: CircleMarkerOptions = definedExternally,
) :
    CircleMarker {
    open fun getBounds(): LatLngBounds
}
