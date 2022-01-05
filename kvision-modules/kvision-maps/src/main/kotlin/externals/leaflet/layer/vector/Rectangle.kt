@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.GeoJsonSingleOrMultiPolygon
import externals.leaflet.geo.LatLngBounds

/** See [`https://leafletjs.com/reference.html#rectangle`](https://leafletjs.com/reference.html#rectangle) */
open external class Rectangle(
    latLngBounds: LatLngBounds,
    options: PolylineOptions = definedExternally
) : Polygon<GeoJsonSingleOrMultiPolygon> {

    open fun setBounds(latLngBounds: LatLngBounds): Rectangle /* this */
}
