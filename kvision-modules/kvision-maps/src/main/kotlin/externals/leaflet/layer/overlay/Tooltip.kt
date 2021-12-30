@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.layer.Layer

open external class Tooltip(
    options: TooltipOptions = definedExternally,
    source: Layer = definedExternally
) : DivOverlay {

    override var options: TooltipOptions

    open fun setOpacity(param_val: Number)
}
