@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLng
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement

open external class DivOverlay<OptionsType : DivOverlayOptions>(
    options: DivOverlayOptions = definedExternally,
    source: Layer = definedExternally
) : Layer {
    open fun getLatLng(): LatLng?
    open fun setLatLng(latlng: LatLng): DivOverlay<OptionsType> /* this */
    open fun getContent(): dynamic /* String? | HTMLElement? | ((source: Layer) -> dynamic)? */
    open fun setContent(htmlContent: (source: Layer) -> Any): DivOverlay<OptionsType> /* this */
    open fun setContent(htmlContent: String): DivOverlay<OptionsType> /* this */
    open fun setContent(htmlContent: HTMLElement): DivOverlay<OptionsType> /* this */
    open fun getElement(): HTMLElement?
    open fun update()
    open fun isOpen(): Boolean
    open fun bringToFront(): DivOverlay<OptionsType> /* this */
    open fun bringToBack(): DivOverlay<OptionsType> /* this */
    open var options: OptionsType
}
