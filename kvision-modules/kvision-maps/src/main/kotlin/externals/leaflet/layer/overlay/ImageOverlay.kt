@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.InteractiveLayerOptions
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import org.w3c.dom.HTMLImageElement

open external class ImageOverlay(
    imageUrl: String,
    bounds: LatLngBounds,
    options: ImageOverlayOptions = definedExternally
) : Layer<ImageOverlayOptions> {

    open fun setOpacity(opacity: Number): ImageOverlay /* this */
    open fun bringToFront(): ImageOverlay /* this */
    open fun bringToBack(): ImageOverlay /* this */
    open fun setUrl(url: String): ImageOverlay /* this */
    open fun setBounds(bounds: LatLngBounds): ImageOverlay /* this */
    open fun setZIndex(value: Number): ImageOverlay /* this */
    open fun getBounds(): LatLngBounds
    open fun getElement(): HTMLImageElement?

    interface ImageOverlayOptions : InteractiveLayerOptions {
        var opacity: Number?
        var alt: String?
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

}
