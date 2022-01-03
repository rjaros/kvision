@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker


import externals.geojson.Feature
import externals.geojson.Point
import externals.leaflet.core.Handler
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.InteractiveLayerOptions
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement


open external class Marker(
    latlng: LatLng,
    options: MarkerOptions = definedExternally,
) : Layer {

    override var options: MarkerOptions
    open var dragging: Handler?
    open var feature: Feature<Point>?
    open var _shadow: HTMLElement?

    open fun toGeoJSON(precision: Number = definedExternally): Feature<Point>
    open fun getLatLng(): LatLng
    open fun setLatLng(latlng: LatLng): Marker /* this */
    open fun setZIndexOffset(offset: Number): Marker /* this */
    open fun getIcon(): Icon
    open fun setIcon(icon: DivIcon): Marker /* this */
    open fun setOpacity(opacity: Number): Marker /* this */
    open fun getElement(): HTMLElement?

    interface MarkerOptions : InteractiveLayerOptions {
        var icon: Icon
        var draggable: Boolean?
        var keyboard: Boolean?
        var title: String?
        var alt: String?
        var zIndexOffset: Number?
        var opacity: Number?
        var riseOnHover: Boolean?
        var riseOffset: Number?
        var shadowPane: String?
        var autoPan: Boolean?
        var autoPanPadding: Point
        var autoPanSpeed: Number?
    }

}
