@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker

import externals.leaflet.layer.LayerOptions

external interface BaseIconOptions : LayerOptions {
    var iconUrl: String?
    var iconRetinaUrl: String?
    var iconSize: dynamic /* Point? | JsTuple<Number, Number> */
    var iconAnchor: dynamic /* Point? | JsTuple<Number, Number> */
    var popupAnchor: dynamic /* Point? | JsTuple<Number, Number> */
    var tooltipAnchor: dynamic /* Point? | JsTuple<Number, Number> */
    var shadowUrl: String?
    var shadowRetinaUrl: String?
    var shadowSize: dynamic /* Point? | JsTuple<Number, Number> */
    var shadowAnchor: dynamic /* Point? | JsTuple<Number, Number> */
    var className: String?
}
