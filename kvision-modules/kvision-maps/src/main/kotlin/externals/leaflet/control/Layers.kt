@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control

import externals.leaflet.layer.Layer

/**
 * The layers control gives users the ability to switch between different base layers and switch
 * overlays on/off
 * (check out the [detailed example](http://leafletjs.com/examples/layers-control/)).
 *
 * Base layers will be switched with radio buttons, while overlays will be switched with checkboxes.
 *
 * Note that all base layers should be passed in the base layers object, but only one should be
 * added to the map during map instantiation.
 */
open external class Layers(
    baseLayers: LayersObject = definedExternally,
    overlays: LayersObject = definedExternally,
    options: LayersOptions = definedExternally
) : Control {
    open fun addBaseLayer(layer: Layer, name: String): Layers /* this */
    open fun addOverlay(layer: Layer, name: String): Layers /* this */
    open fun removeLayer(layer: Layer): Layers /* this */
    open fun expand(): Layers /* this */
    open fun collapse(): Layers /* this */
    override var options: LayersOptions
}
