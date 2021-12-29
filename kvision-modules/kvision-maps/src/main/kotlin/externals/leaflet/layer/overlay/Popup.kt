 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.overlay

import externals.leaflet.map.LeafletMap
import externals.leaflet.layer.Layer

open external class Popup(
    options: PopupOptions = definedExternally,
    source: Layer = definedExternally
) : DivOverlay<PopupOptions> {
    open fun openOn(map: LeafletMap): Popup /* this */
    override var options: PopupOptions
}
