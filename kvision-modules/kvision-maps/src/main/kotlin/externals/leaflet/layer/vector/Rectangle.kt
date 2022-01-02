@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.geo.LatLngBounds


open external class Rectangle(
    latLngBounds: LatLngBounds,
    options: PolylineOptions = definedExternally
) : Polygon {

    open fun setBounds(latLngBounds: LatLngBounds): Rectangle /* this */
}
