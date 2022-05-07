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
@file:JsQualifier("Control")

package io.kvision.maps.externals.leaflet.control


open external class Attribution(
    options: AttributionOptions = definedExternally
) : Control<Attribution.AttributionOptions> {

    /** The HTML text shown before the attributions. Pass `false` to disable. */
    open fun setPrefix(prefix: String): Attribution /* this */
    /** The HTML text shown before the attributions. Pass `false` to disable. */
    open fun setPrefix(prefix: Boolean): Attribution /* this */
    /** Adds an attribution text (e.g. `Vector data &copy; Mapbox`).*/
    open fun addAttribution(text: String): Attribution /* this */
    /** Removes an attribution text. */
    open fun removeAttribution(text: String): Attribution /* this */

    interface AttributionOptions : ControlOptions {
        /**
         * The HTML text shown before the attributions. Pass false to disable.
         *
         * Type: `String` or `false`
         */
        var prefix: dynamic
    }

}
