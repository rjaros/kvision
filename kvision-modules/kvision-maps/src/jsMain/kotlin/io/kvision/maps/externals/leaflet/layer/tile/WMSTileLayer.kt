@file:Suppress("INTERFACE_WITH_SUPERCLASS")
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

package io.kvision.maps.externals.leaflet.layer.tile

/**
 * Used to display WMS services as tile layers on the map.
 */
open external class WMSTileLayer(
    baseUrl: String,
    options: WMSTileLayerOptions,
) : TileLayer<WMSTileLayer.WMSTileLayerOptions> {

    open var wmsParams: WMSParams

    open fun setParams(params: dynamic, noRedraw: Boolean = definedExternally): WMSTileLayer /* this */

    interface WMSTileLayerOptions : TileLayerOptions {
        var layers: String?
        var styles: String?
        var format: String?
        var transparent: Boolean?
        var version: String?
        var crs: dynamic
        var uppercase: Boolean?
    }

    interface WMSParams {
        var format: String?
        var layers: String
        var request: String?
        var service: String?
        var styles: String?
        var version: String?
        var transparent: Boolean?
        var width: Int?
        var height: Int?
    }

}
