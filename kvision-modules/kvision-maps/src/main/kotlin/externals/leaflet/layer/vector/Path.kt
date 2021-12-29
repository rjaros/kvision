 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.vector

import externals.leaflet.layer.Layer
import externals.leaflet.layer.LayerOptions
import org.w3c.dom.Element


open external class Path(options: LayerOptions = definedExternally) : Layer {
    open fun redraw(): Path /* this */
    open fun setStyle(style: PathOptions): Path /* this */
    open fun bringToFront(): Path /* this */
    open fun bringToBack(): Path /* this */
    open fun getElement(): Element?
    open var options: PathOptions
}
