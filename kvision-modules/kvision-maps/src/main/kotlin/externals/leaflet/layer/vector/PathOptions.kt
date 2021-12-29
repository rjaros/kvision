 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.vector

import externals.leaflet.layer.InteractiveLayerOptions

external interface PathOptions : InteractiveLayerOptions {
    var stroke: Boolean?
    var color: String?
    var weight: Number?
    var opacity: Number?
    var lineCap: String? /* "butt" | "round" | "square" | "inherit" */
    var lineJoin: String? /* "miter" | "round" | "bevel" | "inherit" */
    var dashArray: dynamic /* String? | Array<Number>? */
    var dashOffset: String?
    var fill: Boolean?
    var fillColor: String?
    var fillOpacity: Number?
    var fillRule: String? /* "nonzero" | "evenodd" | "inherit" */
    var renderer: Renderer?
    var className: String?
}
