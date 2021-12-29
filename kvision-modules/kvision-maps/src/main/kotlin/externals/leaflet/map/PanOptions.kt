@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.map

external interface PanOptions {
    var animate: Boolean?
    var duration: Number?
    var easeLinearity: Number?
    var noMoveStart: Boolean?
}
