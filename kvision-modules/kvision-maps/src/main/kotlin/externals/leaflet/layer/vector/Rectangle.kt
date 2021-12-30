@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.geo.LatLngBounds


open external class Rectangle<P : Any>(
    latLngBounds: LatLngBounds,
    options: PolylineOptions = definedExternally
) : Polygon<P> {
    //    constructor(latLngBounds: LatLngBounds)
//    constructor(latLngBounds: LatLngBoundsLiteral, options: PolylineOptions = definedExternally)
//    constructor(latLngBounds: LatLngBoundsLiteral)
    open fun setBounds(latLngBounds: LatLngBounds): Rectangle<P> /* this */
//    open fun setBounds(latLngBounds: LatLngBoundsLiteral): Rectangle<P> /* this */
}
