@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.DirectionsUnion
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.Tooltip.TooltipOptions

open external class Tooltip(
    source: Layer<*> = definedExternally,
    options: TooltipOptions = definedExternally,
) : DivOverlay<TooltipOptions> {

    open fun setOpacity(param_val: Number)

    interface TooltipOptions : DivOverlay.DivOverlayOptions {
        var direction: DirectionsUnion?
        var permanent: Boolean?
        var sticky: Boolean?
        var interactive: Boolean?
        var opacity: Number?
    }

}
