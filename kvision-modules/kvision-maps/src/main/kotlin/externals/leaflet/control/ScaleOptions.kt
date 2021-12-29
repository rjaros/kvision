@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

external interface ScaleOptions : ControlOptions {
    var maxWidth: Number?
    var metric: Boolean?
    var imperial: Boolean?
    var updateWhenIdle: Boolean?
}
