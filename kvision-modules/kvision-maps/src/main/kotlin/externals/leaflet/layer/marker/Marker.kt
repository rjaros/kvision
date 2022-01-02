@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker


import externals.geojson.Feature
import externals.leaflet.geo.LatLng
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.map.Handler
import org.w3c.dom.HTMLElement


open external class Marker(
    latlng: LatLng,
    options: MarkerOptions = definedExternally,
) : Layer {

    open fun toGeoJSON(precision: Number = definedExternally): Feature<Point, *>
    open fun getLatLng(): LatLng
    open fun setLatLng(latlng: LatLng): Marker /* this */
    open fun setZIndexOffset(offset: Number): Marker /* this */
    open fun getIcon(): dynamic /* Icon__0 | DivIcon */
    open fun setIcon(icon: DivIcon): Marker /* this */
    open fun setOpacity(opacity: Number): Marker /* this */
    open fun getElement(): HTMLElement?
    open var options: MarkerOptions
    open var dragging: Handler?
    open var feature: Feature<Point, *>?
    open var _shadow: HTMLElement?
}
