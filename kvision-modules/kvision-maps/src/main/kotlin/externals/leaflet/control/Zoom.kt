@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

import externals.leaflet.control.Zoom.ZoomOptions

open external class Zoom(options: ZoomOptions = definedExternally) : Control<ZoomOptions> {

    interface ZoomOptions : ControlOptions {
        var zoomInText: String?
        var zoomInTitle: String?
        var zoomOutText: String?
        var zoomOutTitle: String?
    }

}
