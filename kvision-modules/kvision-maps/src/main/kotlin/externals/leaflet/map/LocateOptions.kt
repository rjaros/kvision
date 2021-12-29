@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.map

external interface LocateOptions {
    var watch: Boolean?
    var setView: Boolean?
    var maxZoom: Number?
    var timeout: Number?
    var maximumAge: Number?
    var enableHighAccuracy: Boolean?
}
