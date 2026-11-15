@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

package io.kvision.maps.externals.leaflet.control

import io.kvision.maps.externals.leaflet.control.FullscreenControl.FullscreenControlOptions
import org.w3c.dom.HTMLElement

/** A button that toggles fullscreen mode for the map. */
open external class FullscreenControl(
    options: FullscreenControlOptions = definedExternally
) : Control<FullscreenControlOptions> {

    /** Returns `true` if the map is in fullscreen mode. */
    open fun isFullscreen(): Boolean

    /** Enters fullscreen mode, or exits it if the map is already fullscreen. */
    open fun toggle(): FullscreenControl /* this */

    /** Enters fullscreen mode if [on] is `true`, or exits it otherwise. */
    open fun setFullscreen(on: Boolean): FullscreenControl /* this */

    interface FullscreenControlOptions : ControlOptions {
        /** The text set on the button while the map is not fullscreen. */
        var enterText: String?

        /** The title set on the button while the map is not fullscreen. */
        var enterTitle: String?

        /** The text set on the button while the map is fullscreen. */
        var exitText: String?

        /** The title set on the button while the map is fullscreen. */
        var exitTitle: String?

        /** The element to make fullscreen. Defaults to the map container. */
        var container: HTMLElement?

        /** Whether to always fill the window with CSS instead of using the Fullscreen API. */
        var pseudo: Boolean?
    }
}
