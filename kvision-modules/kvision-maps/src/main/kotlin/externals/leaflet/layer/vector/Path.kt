@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.layer.InteractiveLayerOptions
import externals.leaflet.layer.Layer
import org.w3c.dom.Element


/**
 * An abstract class that contains options and constants shared between vector overlays
 * ([Polygon], [Polyline], [Circle]). Do not use it directly.
 */
abstract external class Path(
    options: LayerOptions = definedExternally
) : Layer {
    override var options: dynamic // PathOptions

    open fun redraw(): Path /* this */
    open fun setStyle(style: PathOptions): Path /* this */
    open fun bringToFront(): Path /* this */
    open fun bringToBack(): Path /* this */
    open fun getElement(): Element?

      interface PathOptions : InteractiveLayerOptions {
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

}
