 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.control

import externals.leaflet.layer.Layer

external interface LayersOptions : ControlOptions {
    var collapsed: Boolean?
    var autoZIndex: Boolean?
    var hideSingleBase: Boolean?
    var sortLayers: Boolean?
    var sortFunction: ((layerA: Layer, layerB: Layer, nameA: String, nameB: String) -> Number)?
}
