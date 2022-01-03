@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control

import externals.leaflet.control.Layers.LayersOptions
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
) : Control<LayersOptions> {

    open fun addBaseLayer(layer: Layer<*>, name: String): Layers /* this */
    open fun addOverlay(layer: Layer<*>, name: String): Layers /* this */
    open fun removeLayer(layer: Layer<*>): Layers /* this */
    open fun expand(): Layers /* this */
    open fun collapse(): Layers /* this */

    /**
     * The layers control gives users the ability to switch between different base layers and switch
     * overlays on/off (check out the [detailed example](http://leafletjs.com/examples/layers-control/)).
     *
     * See: [`https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/control/Control.Layers.js`](https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/control/Control.Layers.js)
     *
     * @see [Control.LayersObject]
     */
    interface LayersOptions : ControlOptions {

        /**
         * If `true`, the control will be collapsed into an icon and expanded on mouse hover or touch.
         */
        var collapsed: Boolean?
        /**
         * If `true`, the control will assign zIndexes in increasing order to all of its layers so
         * that the order is preserved when switching them on/off.
         */
        var autoZIndex: Boolean?
        /**
         * If `true`, the base layers in the control will be hidden when there is only one.
         */
        var hideSingleBase: Boolean?
        /**
         * Whether to sort the layers. When `false`, layers will keep the order in which they were
         * added to the control.
         *
         * @see [sortFunction]
         */
        var sortLayers: Boolean?

        /**
         * A [compare function](https://developer.mozilla.org/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)
         * that will be used for sorting the layers, when [sortLayers] is `true`.
         *
         * The function receives both the [Layer] instances and their names, as in
         *
         * ```
         * sortFunction(layerA, layerB, nameA, nameB)
         * ```
         *
         * By default, it sorts layers alphabetically by their name.
         */
        var sortFunction: ((layerA: Layer<*>, layerB: Layer<*>, nameA: String, nameB: String) -> Number)?
    }

}
