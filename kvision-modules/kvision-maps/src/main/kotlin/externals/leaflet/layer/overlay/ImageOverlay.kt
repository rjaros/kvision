@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLImageElement

open external class ImageOverlay(
    imageUrl: String,
    bounds: LatLngBounds,
    options: ImageOverlayOptions = definedExternally
) : Layer {
    open var options: ImageOverlayOptions

    open fun setOpacity(opacity: Number): ImageOverlay /* this */
    open fun bringToFront(): ImageOverlay /* this */
    open fun bringToBack(): ImageOverlay /* this */
    open fun setUrl(url: String): ImageOverlay /* this */
    open fun setBounds(bounds: LatLngBounds): ImageOverlay /* this */
    open fun setZIndex(value: Number): ImageOverlay /* this */
    open fun getBounds(): LatLngBounds
    open fun getElement(): HTMLImageElement?
}
