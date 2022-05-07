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
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.geometry.Point
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.tile.GridLayer.GridLayerOptions
import org.w3c.dom.HTMLElement

/**
 * Generic class for handling a tiled grid of HTML elements. This is the base class for all tile
 * layers and replaces [Canvas][io.kvision.maps.externals.leaflet.layer.vector.Canvas].
 *
 * GridLayer can be extended to create a tiled grid of HTML elements like `<canvas>`, `<img>` or
 * `<div>`. GridLayer will handle creating and animating these DOM elements for you.
 *
 * [`https://leafletjs.com/reference.html#gridlayer`](https://leafletjs.com/reference.html#gridlayer)
 */
abstract external class GridLayer<T : GridLayerOptions>(
    options: T = definedExternally
) : Layer<T> {

    open fun bringToFront(): GridLayer<*> /* this */
    open fun bringToBack(): GridLayer<*> /* this */
    open fun getContainer(): HTMLElement?
    open fun setOpacity(opacity: Number): GridLayer<*> /* this */
    open fun setZIndex(zIndex: Number): GridLayer<*> /* this */
    open fun isLoading(): Boolean
    open fun redraw(): GridLayer<*> /* this */
    open fun getTileSize(): Point
    open fun createTile(coords: Coords, done: DoneCallback): HTMLElement

    interface GridLayerOptions : LayerOptions {
        /**
         * Width and height of tiles in the grid. Use a number if width and height are equal, or
         * [`Point(width, height)`][Point] otherwise.
         */
        var tileSize: dynamic /* Number? | Point? */
        var opacity: Number?
        var updateWhenIdle: Boolean?
        var updateWhenZooming: Boolean?
        var updateInterval: Number?
        var zIndex: Number?
        var bounds: LatLngBounds?
        /** The minimum zoom level down to which this layer will be displayed (inclusive). */
        var minZoom: Number?
        /** The maximum zoom level up to which this layer will be displayed (inclusive). */
        var maxZoom: Number?
        /**
         * 	Maximum zoom number the tile source has available. If it is specified, the tiles on
         * 	all zoom levels higher than maxNativeZoom will be loaded from maxNativeZoom level and
         * 	auto-scaled.
         */
        var maxNativeZoom: Number?
        /**
         * 	Minimum zoom number the tile source has available. If it is specified, the tiles on
         * 	all zoom levels lower than minNativeZoom will be loaded from minNativeZoom level and
         * 	auto-scaled.
         */
        var minNativeZoom: Number?
        var noWrap: Boolean?
        var className: String?
        var keepBuffer: Number?
    }

}
