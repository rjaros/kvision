@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.map.LeafletMap

open external class Popup(
    source: Layer = definedExternally,
    options: PopupOptions = definedExternally,
) : DivOverlay {

    override var options: PopupOptions

    open fun openOn(map: LeafletMap): Popup /* this */

    interface PopupOptions : DivOverlayOptions {
        var maxWidth: Number?
        var minWidth: Number?
        var maxHeight: Number?
        var keepInView: Boolean?
        var closeButton: Boolean?
        var autoPan: Boolean?
        var autoPanPaddingTopLeft: Point
        var autoPanPaddingBottomRight: Point
        var autoPanPadding: Point
        var autoClose: Boolean?
        var closeOnClick: Boolean?
        var closeOnEscapeKey: Boolean?
    }

}
