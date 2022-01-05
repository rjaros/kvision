@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

external interface InteractiveLayerOptions : Layer.LayerOptions {
    var interactive: Boolean?
    var bubblingMouseEvents: Boolean?
}
