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

import io.kvision.maps.externals.leaflet.DirectionsUnion
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip.TooltipOptions

open external class Tooltip(
    source: Layer<*> = definedExternally,
    options: TooltipOptions? = definedExternally,
) : DivOverlay<TooltipOptions> {

    open fun setOpacity(param_val: Number)

    interface TooltipOptions : DivOverlayOptions {
        /**
         * Direction where to open the tooltip. Possible values are: `right`, `left`, `top`,
         * `bottom`, `center`, `auto`.
         *
         * `auto` will dynamically switch between `right` and `left` according to the tooltip
         * position on the map.
         */
        var direction: DirectionsUnion?
        /** Whether to open the tooltip permanently or only on mouseover. */
        var permanent: Boolean?
        /** If true, the tooltip will follow the mouse instead of being fixed at the feature center. */
        var sticky: Boolean?
        /** Tooltip container opacity. */
        var opacity: Number?
    }
}
