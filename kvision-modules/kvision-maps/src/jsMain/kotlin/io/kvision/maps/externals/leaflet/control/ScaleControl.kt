@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

import io.kvision.maps.externals.leaflet.control.ScaleControl.ScaleControlOptions

/**
 * A simple scale control that shows the scale of the current center of screen in metric (m/km) and
 * imperial (mi/ft) systems.
 */
open external class ScaleControl(options: ScaleControlOptions = definedExternally) : Control<ScaleControlOptions> {

    interface ScaleControlOptions : ControlOptions {
        /**
         * Maximum width of the control in pixels. The width is set dynamically to show round
         * values (e.g. 100, 200, 500).
         */
        var maxWidth: Int?
        /** Whether to show the metric scale line (m/km). */
        var metric: Boolean?
        /** Whether to show the imperial scale line (mi/ft). */
        var imperial: Boolean?
        /**
         * If `true`, the control is updated on `moveend`, otherwise it's
         * always up-to-date (updated on `move`).
         */
        var updateWhenIdle: Boolean?
    }

}
