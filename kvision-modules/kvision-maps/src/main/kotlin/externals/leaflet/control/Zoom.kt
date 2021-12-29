@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

open external class Zoom(options: ZoomOptions = definedExternally) : Control<ZoomOptions> {
    override var options: ZoomOptions
}
