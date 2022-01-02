@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.layer.Layer
import externals.leaflet.map.LeafletMap

open external class Popup(
    source: Layer = definedExternally,
    options: PopupOptions = definedExternally,
) : DivOverlay {
    override var options: PopupOptions
    open fun openOn(map: LeafletMap): Popup /* this */
}
