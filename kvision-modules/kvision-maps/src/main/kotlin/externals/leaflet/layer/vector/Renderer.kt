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

import externals.leaflet.layer.Layer
import externals.leaflet.layer.vector.Renderer.RendererOptions

/**
 * Base class for vector renderer implementations ([SVG], [Canvas]). Handles the DOM container of
 * the renderer, its bounds, and its zoom animation.
 *
 * A [Renderer] works as an implicit layer group for all [Path]s - the renderer itself can be added
 * or removed to the map. All paths use a renderer, which can be implicit (the map will decide
 * the type of renderer and use it automatically) or explicit (using the renderer option of the
 * path).
 *
 * Do not use this class directly, use [SVG] and [Canvas] instead.
 */
abstract external class Renderer(
    options: RendererOptions = definedExternally
) : Layer<RendererOptions> {

    interface RendererOptions : LayerOptions {
        var padding: Number?
        var tolerance: Number?
    }

}
