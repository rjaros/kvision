 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.vector

import externals.leaflet.layer.LayerOptions

external interface RendererOptions : LayerOptions {
    var padding: Number?
    var tolerance: Number?
}
