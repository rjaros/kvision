@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.DirectionsUnion
import externals.leaflet.layer.Layer

open external class Tooltip(
    source: Layer = definedExternally,
    options: TooltipOptions = definedExternally,
) : DivOverlay {

    override var options: TooltipOptions

    open fun setOpacity(param_val: Number)

    interface TooltipOptions : DivOverlayOptions {
        var direction: DirectionsUnion?
        var permanent: Boolean?
        var sticky: Boolean?
        var interactive: Boolean?
        var opacity: Number?
    }

}
