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

package externals.leaflet.layer.overlay

import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.Popup.PopupOptions
import externals.leaflet.map.LeafletMap

open external class Popup(
    source: Layer<*> = definedExternally,
    options: PopupOptions = definedExternally,
) : DivOverlay<PopupOptions> {

    open fun openOn(map: LeafletMap): Popup /* this */

    interface PopupOptions : DivOverlayOptions {
        var maxWidth: Number?
        var minWidth: Number?
        var maxHeight: Number?
        var keepInView: Boolean?
        var closeButton: Boolean?
        var autoPan: Boolean?
        var autoPanPaddingTopLeft: Point
        var autoPanPaddingBottomRight: Point
        var autoPanPadding: Point
        var autoClose: Boolean?
        var closeOnClick: Boolean?
        var closeOnEscapeKey: Boolean?
    }

}
