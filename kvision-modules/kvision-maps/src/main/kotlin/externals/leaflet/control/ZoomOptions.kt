@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control

external interface ZoomOptions : ControlOptions {
    var zoomInText: String?
    var zoomInTitle: String?
    var zoomOutText: String?
    var zoomOutTitle: String?
}
