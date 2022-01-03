@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.leaflet.events.Evented
import externals.leaflet.events.LeafletEventHandlerFn
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.Layer.LayerOptions
import externals.leaflet.layer.overlay.Popup
import externals.leaflet.layer.overlay.Popup.PopupOptions
import externals.leaflet.layer.overlay.Tooltip
import externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

abstract external class Layer<T : LayerOptions>(
    options: T = definedExternally
) : Evented {

    open var options: T

    open val getEvents: (() -> LeafletEventHandlerFn)?
    open val getAttribution: (() -> String?)?
    open val beforeAdd: ((map: LeafletMap) -> Layer<T>)?
    open var _map: LeafletMap

    open fun addTo(map: LeafletMap): Layer<T> /* this */
    open fun addTo(map: LayerGroup): Layer<T> /* this */
    open fun remove(): Layer<T> /* this */
    open fun removeFrom(map: LeafletMap): Layer<T> /* this */

    open fun getPane(name: String = definedExternally): HTMLElement?

    open fun bindPopup(
        content: (layer: Layer<T>) -> Any,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    open fun bindPopup(
        content: String,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    open fun bindPopup(
        content: HTMLElement,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    open fun bindPopup(
        content: Popup,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    open fun unbindPopup(): Layer<T> /* this */
    open fun openPopup(latlng: LatLng = definedExternally): Layer<T> /* this */
    open fun openPopup(): Layer<T> /* this */
    open fun closePopup(): Layer<T> /* this */
    open fun togglePopup(): Layer<T> /* this */
    open fun isPopupOpen(): Boolean
    open fun setPopupContent(content: String): Layer<T> /* this */
    open fun setPopupContent(content: HTMLElement): Layer<T> /* this */
    open fun setPopupContent(content: Popup): Layer<T> /* this */
    open fun getPopup(): Popup?

    open fun bindTooltip(
        content: (layer: Layer<T>) -> Any,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    open fun bindTooltip(
        content: Tooltip,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    open fun bindTooltip(
        content: String,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    open fun bindTooltip(
        content: HTMLElement,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    open fun unbindTooltip(): Layer<T> /* this */
    open fun openTooltip(latlng: LatLng = definedExternally): Layer<T> /* this */
    open fun openTooltip(): Layer<T> /* this */
    open fun closeTooltip(): Layer<T> /* this */
    open fun toggleTooltip(): Layer<T> /* this */
    open fun isTooltipOpen(): Boolean
    open fun setTooltipContent(content: String): Layer<T> /* this */
    open fun setTooltipContent(content: HTMLElement): Layer<T> /* this */
    open fun setTooltipContent(content: Tooltip): Layer<T> /* this */
    open fun getTooltip(): Tooltip?

    open fun onAdd(map: LeafletMap): Layer<T> /* this */
    open fun onRemove(map: LeafletMap): Layer<T> /* this */

    interface LayerOptions {
        var pane: String?
        var attribution: String?
    }

}
