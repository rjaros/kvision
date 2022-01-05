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

package externals.leaflet.layer.vector

import externals.leaflet.layer.InteractiveLayerOptions
import externals.leaflet.layer.Layer
import externals.leaflet.layer.vector.Path.PathOptions
import org.w3c.dom.Element


/**
 * An abstract class that contains options and constants shared between vector overlays
 * ([Polygon], [Polyline], [Circle]). Do not use it directly.
 */
abstract external class Path<T : PathOptions>(
    options: T = definedExternally
) : Layer<T> {

    open fun redraw(): Path<T> /* this */
    open fun setStyle(style: PathOptions): Path<T> /* this */
    open fun bringToFront(): Path<T> /* this */
    open fun bringToBack(): Path<T> /* this */
    open fun getElement(): Element?

    interface PathOptions : InteractiveLayerOptions {
        var stroke: Boolean?
        var color: String?
        var weight: Number?
        var opacity: Number?
        var lineCap: String? /* "butt" | "round" | "square" | "inherit" */
        var lineJoin: String? /* "miter" | "round" | "bevel" | "inherit" */
        var dashArray: dynamic /* String? | Array<Number>? */
        var dashOffset: String?
        var fill: Boolean?
        var fillColor: String?
        var fillOpacity: Number?
        var fillRule: String? /* "nonzero" | "evenodd" | "inherit" */
        var renderer: Renderer?
        var className: String?
    }

}
