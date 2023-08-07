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

package io.kvision.maps.externals.leaflet.layer.marker

import io.kvision.maps.externals.leaflet.geometry.Point
import io.kvision.maps.externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement

/**
 * Represents an icon to provide when creating a marker.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#icon
 */
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
        /** **(required)** The URL to the icon image (absolute or relative to your script path). */
        var iconUrl: String
        var iconRetinaUrl: String?
        var iconSize: Point?
        var iconAnchor: Point?
        var popupAnchor: Point
        var tooltipAnchor: Point
        var shadowUrl: String?
        var shadowRetinaUrl: String?
        /** Size of the shadow image in pixels. */
        var shadowSize: Point?
        /**
         * The coordinates of the "tip" of the shadow (relative to its top left corner)
         * (the same as [iconAnchor] if not specified).
         */
        var shadowAnchor: Point?
        /** A custom class name to assign to both icon and shadow images. Empty by default. */
        var className: String?
        /**
         * Whether the crossOrigin attribute will be added to the tiles. If a String is provided,
         * all tiles will have their crossOrigin attribute set to the String provided. This is needed if you want to access tile pixel data. Refer to CORS Settings for valid String values.
         */
        var crossOrigin: Any? /* String? | Boolean? */
    }

}
