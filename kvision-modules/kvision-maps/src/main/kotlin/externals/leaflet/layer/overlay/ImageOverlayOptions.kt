@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.layer.InteractiveLayerOptions

external interface ImageOverlayOptions : InteractiveLayerOptions {
    var opacity: Number?
    var alt: String?
    override var interactive: Boolean?
    override var attribution: String?
    /**
     * Whether the crossOrigin attribute will be added to the image. If a String is provided, the
     * image will have its `crossOrigin` attribute set to the String provided. This is needed if
     * you want to access image pixel data.
     *
     * Refer to [CORS Settings](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/crossorigin)
     * for valid String values.
     */
    var crossOrigin: dynamic /* Boolean? | String? */
    var errorOverlayUrl: String?
    var zIndex: Number?
    var className: String?
}
