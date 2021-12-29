 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.overlay

import externals.leaflet.layer.overlay.DivOverlayOptions

external interface PopupOptions : DivOverlayOptions {
    var maxWidth: Number?
    var minWidth: Number?
    var maxHeight: Number?
    var keepInView: Boolean?
    var closeButton: Boolean?
    var autoPan: Boolean?
    var autoPanPaddingTopLeft: dynamic /* Point? | JsTuple<Number, Number> */
    var autoPanPaddingBottomRight: dynamic /* Point? | JsTuple<Number, Number> */
    var autoPanPadding: dynamic /* Point? | JsTuple<Number, Number> */
    var autoClose: Boolean?
    var closeOnClick: Boolean?
    var closeOnEscapeKey: Boolean?
}
