@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control


external interface InvalidateSizeOptions : ZoomPanOptions {
    var debounceMoveend: Boolean?
    var pan: Boolean?
}
