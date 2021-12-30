@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control


external interface AttributionOptions : ControlOptions {
    var prefix: dynamic /* String? | Boolean? */
}
