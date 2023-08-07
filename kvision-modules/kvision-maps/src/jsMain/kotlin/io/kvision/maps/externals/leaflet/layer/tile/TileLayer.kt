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

package io.kvision.maps.externals.leaflet.layer.tile

import io.kvision.maps.externals.leaflet.DoneCallback
import io.kvision.maps.externals.leaflet.geo.Coords
import io.kvision.maps.externals.leaflet.layer.tile.TileLayer.TileLayerOptions
import org.w3c.dom.HTMLElement

/**
 * Used to load and display tile layers on the map. Note that most tile servers require
 * attribution, which you can set under [TileLayerOptions.attribution].
 *
 *
 * @param[urlTemplate] A string of the following form:
 * ```
 * http://{s}.somedomain.com/blabla/{z}/{x}/{y}{r}.png
 * ```
 * * `{s}` means one of the available subdomains (used sequentially to help with browser parallel
 *   requests per domain limitation; subdomain values are specified in options; `a`, `b` or `c` by
 *   default, can be omitted),
 * * `{z}` — zoom level,
 * * `{x}` and `{y}` — tile coordinates.
 * * `{r}` can be used to add `"@2x"` to the URL to load retina tiles.
 *
 *  You can use custom keys in the template, which will be evaluated from [TileLayer] options,
 *  like this:
 *
 *  ```kotlin
 *  LeafletObjectFactory.tileLayer("http://{s}.somedomain.com/{foo}/{z}/{x}/{y}.png") {
 *    asDynamic().foo = "bar"
 *  }
 *  ```
 */
open external class TileLayer<T : TileLayerOptions>(
    urlTemplate: String,
    options: T = definedExternally
) : GridLayer<T> {

    /** @return this */
    open fun setUrl(url: String, noRedraw: Boolean = definedExternally): TileLayer<*>

    interface TileLayerOptions : GridLayerOptions {
        /**
         * Subdomains of the tile service. Can be passed in the form of one string (where each
         * letter is a subdomain name) or an array of strings.
         */
        var subdomains: dynamic /* String? | Array<String>? */
        var errorTileUrl: String?
        var zoomOffset: Number?
        /**
         * If true, inverses Y axis numbering for tiles (turn this on for
         * [TMS](https://en.wikipedia.org/wiki/Tile_Map_Service) services)
         */
        var tms: Boolean?
        var zoomReverse: Boolean?
        var detectRetina: Boolean?
        var crossOrigin: dynamic /* Boolean? | String? */
        var referrerPolicy: dynamic /* Boolean? | String? */
    }

}
