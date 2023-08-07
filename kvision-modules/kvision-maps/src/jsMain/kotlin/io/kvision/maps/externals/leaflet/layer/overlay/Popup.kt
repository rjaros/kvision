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

package io.kvision.maps.externals.leaflet.layer.overlay

import io.kvision.maps.externals.leaflet.geometry.Point
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.Popup.PopupOptions
import io.kvision.maps.externals.leaflet.map.LeafletMap

/**
 * Used to open popups in certain places of the map. Use [LeafletMap.openPopup] to open popups
 * while making sure that only one popup is open at one time (recommended for usability), or use
 * [LeafletMap.addLayer] to open as many as you want.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#popup
 */
open external class Popup(
    source: Layer<*> = definedExternally,
    options: PopupOptions = definedExternally,
) : DivOverlay<PopupOptions> {

    interface PopupOptions : DivOverlayOptions {
        /** Max width of the popup, in pixels.*/
        var maxWidth: Number?
        /** Min width of the popup, in pixels.*/
        var minWidth: Number?
        /**
         * If set, creates a scrollable container of the given height inside a popup if its content
         * exceeds it.
         */
        var maxHeight: Number?
        /**
         * Set it to false if you don't want the map to do panning animation to fit the opened
         * popup.
         */
        var autoPan: Boolean?
        /**
         * The margin between the popup and the top left corner of the map view after
         * auto-panning was performed.
         */
        var autoPanPaddingTopLeft: Point?
        /**
         * The margin between the popup and the bottom right corner of the map view after
         * auto-panning was performed.
         */
        var autoPanPaddingBottomRight: Point?
        /**
         * Equivalent of setting both top left and bottom right auto-pan padding to the same value.
         */
        var autoPanPadding: Point?
        /**
         * Set it to true if you want to prevent users from panning the popup off of the screen
         * while it is open.
         * */
        var keepInView: Boolean?
        /** Controls the presence of a close button in the popup.*/
        var closeButton: Boolean?
        /**
         * Set it to false if you want to override the default behavior of the popup closing when
         * another popup is opened.
         */
        var autoClose: Boolean?
        /**
         * Set it to `false` if you want to override the default behavior of the ESC key for
         * closing of the popup.
         */
        var closeOnEscapeKey: Boolean?
        /**
         * Set it if you want to override the default behavior of the popup closing when user
         * clicks on the map. Defaults to the map's
         * [`closePopupOnClick`][LeafletMap.LeafletMapOptions.closePopupOnClick] option.
         */
        var closeOnClick: Boolean?
    }
}
