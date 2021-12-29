@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.layer.InteractiveLayerOptions

external interface ImageOverlayOptions : InteractiveLayerOptions {
    var opacity: Number?
    var alt: String?
    override var interactive: Boolean?
    override var attribution: String?
    var crossOrigin: dynamic /* Boolean? | String? */
    var errorOverlayUrl: String?
    var zIndex: Number?
    var className: String?
}
