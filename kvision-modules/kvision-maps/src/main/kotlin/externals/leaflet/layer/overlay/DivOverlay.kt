@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLng
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement

open external class DivOverlay(
    options: DivOverlayOptions = definedExternally,
    source: Layer = definedExternally
) : Layer {
    open var options: dynamic // DivOverlayOptions

    open fun getLatLng(): LatLng?
    open fun setLatLng(latlng: LatLng): DivOverlay /* this */
    open fun getContent(): dynamic /* String? | HTMLElement? | ((source: Layer) -> dynamic)? */
    open fun setContent(htmlContent: (source: Layer) -> Any): DivOverlay /* this */
    open fun setContent(htmlContent: String): DivOverlay /* this */
    open fun setContent(htmlContent: HTMLElement): DivOverlay /* this */
    open fun getElement(): HTMLElement?
    open fun update()
    open fun isOpen(): Boolean
    open fun bringToFront(): DivOverlay /* this */
    open fun bringToBack(): DivOverlay /* this */
}
