@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.GeoJsonSingleOrMultiLineString
import externals.geojson.GeoJsonSingleOrMultiPolygon
import externals.leaflet.geo.LatLng

/**
 * A class for drawing polygon overlays on a map. Extends [Polyline].
 *
 * Note that points you pass when creating a polygon shouldn't have an additional last point equal
 * to the first one — it's better to filter out such points.
 *
 * @param T [externals.geojson.Polygon] or [externals.geojson.MultiPolygon]
 */
open external class Polygon<T: GeoJsonSingleOrMultiPolygon> : Polyline<GeoJsonSingleOrMultiLineString> {

    /** Create a polygon from an array of LatLng points. */
    constructor(
        latlngs: Array<LatLng>,
        options: PolylineOptions = definedExternally
    )

    /**
     * @param[latlngs] An array of arrays of [LatLng], with the first array representing the
     * outer shape and the other arrays representing holes in the outer shape.
     */
    constructor(
        latlngs: Array<Array<LatLng>>,
        options: PolylineOptions = definedExternally
    )

    /**
     * @param[latlngs] a multi-dimensional array to represent a MultiPolygon shape.
     */
    constructor(
        latlngs: Array<Array<Array<LatLng>>>,
        options: PolylineOptions = definedExternally
    )

}
