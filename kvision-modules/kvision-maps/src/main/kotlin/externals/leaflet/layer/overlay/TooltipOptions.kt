 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer.overlay

import externals.leaflet.DirectionsUnion

external interface TooltipOptions : DivOverlayOptions {
    override var pane: String?
    override var offset: dynamic /* Point? | JsTuple<Number, Number> */
    var direction: DirectionsUnion?
    var permanent: Boolean?
    var sticky: Boolean?
    var interactive: Boolean?
    var opacity: Number?
}
