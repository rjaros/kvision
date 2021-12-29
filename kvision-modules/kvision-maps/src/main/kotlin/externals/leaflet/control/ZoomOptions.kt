@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

external interface ZoomOptions : ControlOptions {
    var zoomInText: String?
    var zoomInTitle: String?
    var zoomOutText: String?
    var zoomOutTitle: String?
}
