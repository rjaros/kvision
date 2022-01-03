@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

open external class Zoom(options: ZoomOptions = definedExternally) : Control {
    override var options: ZoomOptions

    interface ZoomOptions : ControlOptions {
        var zoomInText: String?
        var zoomInTitle: String?
        var zoomOutText: String?
        var zoomOutTitle: String?
    }

}
