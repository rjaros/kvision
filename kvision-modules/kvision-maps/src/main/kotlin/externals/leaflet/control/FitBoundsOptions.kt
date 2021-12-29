@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

import externals.leaflet.map.PanOptions

external interface FitBoundsOptions : ZoomOptions, PanOptions {
    var paddingTopLeft: dynamic /* Point? | JsTuple<Number, Number> */
    var paddingBottomRight: dynamic /* Point? | JsTuple<Number, Number> */
    var padding: dynamic /* Point? | JsTuple<Number, Number> */
    var maxZoom: Number?
    override var animate: Boolean?
}
