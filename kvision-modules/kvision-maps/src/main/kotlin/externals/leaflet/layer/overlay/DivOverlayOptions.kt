@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

external interface DivOverlayOptions {
    var offset: dynamic /* Point? | JsTuple<Number, Number> */
    var zoomAnimation: Boolean?
    var className: String?
    var pane: String?
}
