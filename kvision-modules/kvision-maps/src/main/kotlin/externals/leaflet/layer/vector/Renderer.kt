 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.vector

import externals.leaflet.layer.Layer

open external class Renderer(options: RendererOptions = definedExternally) : Layer {
    open var options: RendererOptions
}
