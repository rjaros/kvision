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

package externals.leaflet.layer.marker

import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement

/** Represents an icon to provide when creating a marker. */
open external class Icon<T : Icon.IconOptions>(options: IconOptions) : Layer<T> {

    open fun createIcon(oldIcon: HTMLElement = definedExternally): HTMLElement
    open fun createShadow(oldIcon: HTMLElement = definedExternally): HTMLElement

    interface DefaultIconOptions : IconOptions {
        var imagePath: String?
    }

    open class Default(options: DefaultIconOptions = definedExternally) : Icon<DefaultIconOptions> {

        companion object {
            var imagePath: String?
        }
    }

    interface IconOptions : LayerOptions {
        var iconUrl: String?
        var iconRetinaUrl: String?
        var iconSize: Point
        var iconAnchor: Point
        var popupAnchor: Point
        var tooltipAnchor: Point
        var shadowUrl: String?
        var shadowRetinaUrl: String?
        var shadowSize: Point
        var shadowAnchor: Point
        var className: String?
    }

}
