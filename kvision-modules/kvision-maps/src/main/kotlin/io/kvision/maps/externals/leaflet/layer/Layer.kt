/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:JsModule("leaflet")
@file:JsNonModule

package io.kvision.maps.externals.leaflet.layer

import io.kvision.maps.externals.leaflet.events.Evented
import io.kvision.maps.externals.leaflet.events.LeafletEventHandlerFn
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.layer.Layer.LayerOptions
import io.kvision.maps.externals.leaflet.layer.overlay.Popup
import io.kvision.maps.externals.leaflet.layer.overlay.Popup.PopupOptions
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import io.kvision.maps.externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

/**
 * A set of methods from the Layer base class that all Leaflet layers use.
 * Inherits all methods, options and events from [Evented].
 */
abstract external class Layer<T : LayerOptions>(
    options: T = definedExternally
) : Evented {

    open var options: T

    open fun addTo(map: LeafletMap): Layer<T> /* this */
    open fun addTo(map: LayerGroup): Layer<T> /* this */
    open fun remove(): Layer<T> /* this */
    open fun removeFrom(map: LeafletMap): Layer<T> /* this */
    open fun getEvents(): LeafletEventHandlerFn?
    open fun getAttribution(): String?
    open fun beforeAdd(map: LeafletMap): Layer<T>? /* this */

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
        /**
         * By default, the layer will be added to the map's
         * [overlay pane][io.kvision.maps.externals.leaflet.map.DefaultMapPanes.overlayPane].
         * Overriding this option will cause the layer to be placed on another pane by default.
         */
        var pane: String?
        /**
         * String to be shown in the attribution control, e.g. "© OpenStreetMap contributors".
         * It describes the layer data and is often a legal obligation towards copyright holders
         * and tile providers.
         */
        var attribution: String?
    }

}
