@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control

import externals.leaflet.control.Scale.ScaleOptions

/**
 * A simple scale control that shows the scale of the current center of screen in metric (m/km) and
 * imperial (mi/ft) systems.
 */
open external class Scale(options: ScaleOptions = definedExternally) : Control<ScaleOptions> {

    interface ScaleOptions : ControlOptions {
        var maxWidth: Number?
        var metric: Boolean?
        var imperial: Boolean?
        var updateWhenIdle: Boolean?
    }

}
