@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

external interface VideoOverlayOptions : ImageOverlayOptions {
    var autoplay: Boolean?
    var loop: Boolean?
    var keepAspectRatio: Boolean?
    var muted: Boolean?
}
