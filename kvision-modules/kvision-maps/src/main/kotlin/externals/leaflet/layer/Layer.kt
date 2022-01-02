@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.leaflet.events.Evented
import externals.leaflet.events.LeafletEventHandlerFn
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.overlay.Popup
import externals.leaflet.layer.overlay.PopupOptions
import externals.leaflet.layer.overlay.Tooltip
import externals.leaflet.layer.overlay.TooltipOptions
import externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

abstract external class Layer(options: LayerOptions = definedExternally) : Evented {

    open val getEvents: (() -> LeafletEventHandlerFn)?
    open val getAttribution: (() -> String?)?
    open val beforeAdd: ((map: LeafletMap) -> Layer)?
    open var _map: LeafletMap

    open fun addTo(map: LeafletMap): Layer /* this */
    open fun addTo(map: LayerGroup): Layer /* this */
    open fun remove(): Layer /* this */
    open fun removeFrom(map: LeafletMap): Layer /* this */

    open fun getPane(name: String = definedExternally): HTMLElement?

    open fun bindPopup(content: (layer: Layer) -> Any, options: PopupOptions = definedExternally): Layer /* this */
    open fun bindPopup(content: String, options: PopupOptions = definedExternally): Layer /* this */
    open fun bindPopup(content: HTMLElement, options: PopupOptions = definedExternally): Layer /* this */
    open fun bindPopup(content: Popup, options: PopupOptions = definedExternally): Layer /* this */
    open fun unbindPopup(): Layer /* this */
    open fun openPopup(latlng: LatLng = definedExternally): Layer /* this */
    open fun openPopup(): Layer /* this */
    open fun closePopup(): Layer /* this */
    open fun togglePopup(): Layer /* this */
    open fun isPopupOpen(): Boolean
    open fun setPopupContent(content: String): Layer /* this */
    open fun setPopupContent(content: HTMLElement): Layer /* this */
    open fun setPopupContent(content: Popup): Layer /* this */
    open fun getPopup(): Popup?

    open fun bindTooltip(content: (layer: Layer) -> Any, options: TooltipOptions = definedExternally): Layer /* this */
    open fun bindTooltip(content: Tooltip, options: TooltipOptions = definedExternally): Layer /* this */
    open fun bindTooltip(content: String, options: TooltipOptions = definedExternally): Layer /* this */
    open fun bindTooltip(content: HTMLElement, options: TooltipOptions = definedExternally): Layer /* this */
    open fun unbindTooltip(): Layer /* this */
    open fun openTooltip(latlng: LatLng = definedExternally): Layer /* this */
    open fun openTooltip(): Layer /* this */
    open fun closeTooltip(): Layer /* this */
    open fun toggleTooltip(): Layer /* this */
    open fun isTooltipOpen(): Boolean
    open fun setTooltipContent(content: String): Layer /* this */
    open fun setTooltipContent(content: HTMLElement): Layer /* this */
    open fun setTooltipContent(content: Tooltip): Layer /* this */
    open fun getTooltip(): Tooltip?

    open fun onAdd(map: LeafletMap): Layer /* this */
    open fun onRemove(map: LeafletMap): Layer /* this */
}
