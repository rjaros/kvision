@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker


import externals.geojson.Feature
import externals.leaflet.geo.LatLng
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.map.Handler
import org.w3c.dom.HTMLElement


open external class Marker<P>(
    latlng: LatLng, options: MarkerOptions = definedExternally
) : Layer {
    //    constructor(latlng: LatLng)
//    constructor(latlng: LatLngLiteral, options: MarkerOptions = definedExternally)
//    constructor(latlng: LatLngLiteral)
//    constructor(latlng: Any, options: MarkerOptions = definedExternally)
//    constructor(latlng: Any)
    open fun toGeoJSON(precision: Number = definedExternally): Feature<Point, P>
    open fun getLatLng(): LatLng
    open fun setLatLng(latlng: LatLng): Marker<P> /* this */
    //    open fun setLatLng(latlng: LatLngLiteral): Marker<P> /* this */
//    open fun setLatLng(latlng: Any /* JsTuple<Number, Number> */): Marker<P> /* this */
    open fun setZIndexOffset(offset: Number): Marker<P> /* this */
    open fun getIcon(): dynamic /* Icon__0 | DivIcon */
    //    open fun setIcon(icon: Icon__0): Marker<P> /* this */
    open fun setIcon(icon: DivIcon): Marker<P> /* this */
    open fun setOpacity(opacity: Number): Marker<P> /* this */
    open fun getElement(): HTMLElement?
    open var options: MarkerOptions
    open var dragging: Handler?
    open var feature: Feature<Point, P>?
    open var _shadow: HTMLElement?
}
