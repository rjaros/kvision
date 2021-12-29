@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control

import externals.leaflet.layer.Layer

// @JsName("Layers")
open external class Layers(
    baseLayers: LayersObject = definedExternally,
    overlays: LayersObject = definedExternally,
    options: LayersOptions = definedExternally
) : Control<LayersOptions> {
    open fun addBaseLayer(layer: Layer, name: String): Layers /* this */
    open fun addOverlay(layer: Layer, name: String): Layers /* this */
    open fun removeLayer(layer: Layer): Layers /* this */
    open fun expand(): Layers /* this */
    open fun collapse(): Layers /* this */
    override var options: LayersOptions
}
