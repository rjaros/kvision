@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker

import externals.leaflet.layer.InteractiveLayerOptions

external interface MarkerOptions : InteractiveLayerOptions {
    var icon: dynamic /* Icon__0? | DivIcon? */
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
    var autoPanPadding: dynamic /* Point? | JsTuple<Number, Number> */
    var autoPanSpeed: Number?
}
