@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLng
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.DivOverlay.DivOverlayOptions
import org.w3c.dom.HTMLElement

open external class DivOverlay<T : DivOverlayOptions>(
    options: DivOverlayOptions = definedExternally,
    source: Layer<*> = definedExternally
) : Layer<Layer.LayerOptions> {

    open fun getLatLng(): LatLng?
    open fun setLatLng(latlng: LatLng): DivOverlay<T> /* this */
    open fun getContent(): dynamic /* String? | HTMLElement? | ((source: Layer) -> dynamic)? */
    open fun setContent(htmlContent: (source: Layer<*>) -> Any): DivOverlay<T> /* this */
    open fun setContent(htmlContent: String): DivOverlay<T> /* this */
    open fun setContent(htmlContent: HTMLElement): DivOverlay<T> /* this */
    open fun getElement(): HTMLElement?
    open fun update()
    open fun isOpen(): Boolean
    open fun bringToFront(): DivOverlay<T> /* this */
    open fun bringToBack(): DivOverlay<T> /* this */

    interface DivOverlayOptions {
        var offset: Point
        var zoomAnimation: Boolean?
        var className: String?
        var pane: String?
    }

}
