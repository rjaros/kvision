@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.layer.Layer
import externals.leaflet.map.LeafletMap

open external class Popup(
    options: PopupOptions = definedExternally,
    source: Layer = definedExternally
) : DivOverlay {
    open fun openOn(map: LeafletMap): Popup /* this */
    override var options: PopupOptions
}
