@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point

/**
 * A class for drawing polyline overlays on a map. Extends [Path].
 *
 * See [`https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js`](https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js)
 */
open external class Polyline<T : externals.geojson.LineStringMultiLineStringUnion, P : Any>/* LatLng | LatLngLiteral | JsTuple<Number, Number> */
    : Path {

    /** Create a polyline from an array of [LatLng] points */
    constructor   (
        latlngs: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>,
        options: PolylineOptions = definedExternally
    )
    
    /** @param[latlngs] pass a multi-dimensional array to represent a [MultiPolyline] shape */
    constructor  (
        latlngs: Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>,
        options: PolylineOptions = definedExternally
    )

    open var feature: Feature<T, P>?
    override var options: PolylineOptions

    open fun toGeoJSON(precision: Number = definedExternally): Feature<T, P>

    open fun getLatLngs(): dynamic /* Array<LatLng> | Array<Array<LatLng>> | Array<Array<Array<LatLng>>> */

    open fun setLatLngs(latlngs: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>): Polyline<T, P> /* this */
    open fun setLatLngs(latlngs: Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>): Polyline<T, P> /* this */
    open fun setLatLngs(latlngs: Array<Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>>): Polyline<T, P> /* this */

    open fun isEmpty(): Boolean
    open fun getCenter(): LatLng
    open fun getBounds(): LatLngBounds
    open fun addLatLng(
        latlng: LatLng,
        latlngs: Array<LatLng> = definedExternally
    ): Polyline<T, P> /* this */

    open fun addLatLng(
        latlng: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>,
        latlngs: Array<LatLng> = definedExternally
    ): Polyline<T, P> /* this */

    open fun closestLayerPoint(p: Point): Point
}
