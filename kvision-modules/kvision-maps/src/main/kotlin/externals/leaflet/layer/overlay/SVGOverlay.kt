@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.overlay

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.Layer
import org.w3c.dom.Element
import org.w3c.dom.svg.SVGElement

// Note: SVGOverlay doesn't extend ImageOverlay because SVGOverlay.getElement returns SVGElement
open external class SVGOverlay : Layer {

    constructor(svgImage: String, bounds: LatLngBounds, options: ImageOverlayOptions = definedExternally)
    constructor(svgImage: SVGElement, bounds: LatLngBounds, options: ImageOverlayOptions = definedExternally)

    open fun setOpacity(opacity: Number): SVGOverlay /* this */
    open fun bringToFront(): SVGOverlay /* this */
    open fun bringToBack(): SVGOverlay /* this */
    open fun setUrl(url: String): SVGOverlay /* this */
    open fun setBounds(bounds: LatLngBounds): SVGOverlay /* this */
    open fun setZIndex(value: Number): SVGOverlay /* this */
    open fun getBounds(): LatLngBounds
    open fun getElement(): SVGElement?
    open var options: ImageOverlayOptions
}
