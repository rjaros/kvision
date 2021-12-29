 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer

external interface InteractiveLayerOptions : LayerOptions {
    var interactive: Boolean?
    var bubblingMouseEvents: Boolean?
}
