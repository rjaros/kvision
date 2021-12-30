@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point


open external class Polyline<T, P: Any> : Path {
    constructor(latlngs: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>, options: PolylineOptions = definedExternally)
//    constructor(latlngs: Array<Any /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>)
    constructor(latlngs: Array<Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>, options: PolylineOptions = definedExternally)
//    constructor(latlngs: Array<Array<Any /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>>)

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
    open fun addLatLng(latlng: LatLng, latlngs: Array<LatLng> = definedExternally): Polyline<T, P> /* this */
//    open fun addLatLng(latlng: LatLng): Polyline<T, P> /* this */
//    open fun addLatLng(latlng: LatLngLiteral, latlngs: Array<LatLng> = definedExternally): Polyline<T, P> /* this */
//    open fun addLatLng(latlng: LatLngLiteral): Polyline<T, P> /* this */
//    open fun addLatLng(latlng: Any /* JsTuple<Number, Number> */, latlngs: Array<LatLng> = definedExternally): Polyline<T, P> /* this */
//    open fun addLatLng(latlng: Any /* JsTuple<Number, Number> */): Polyline<T, P> /* this */
    open fun addLatLng(latlng: Array<LatLng /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>, latlngs: Array<LatLng> = definedExternally): Polyline<T, P> /* this */
//    open fun addLatLng(latlng: Array<Any /* LatLng | LatLngLiteral | JsTuple<Number, Number> */>): Polyline<T, P> /* this */
    open fun closestLayerPoint(p: Point): Point
}
