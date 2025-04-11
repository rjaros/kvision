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

@file:JsModule("leaflet/dist/leaflet-src.esm.js")

package io.kvision.maps.externals.leaflet.layer

import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.vector.Path.PathOptions


/**
 * Extended [LayerGroup] that makes it easier to do the same thing to all its member layers:
 *
 * * [Layer.bindPopup] binds a popup to all the layers at once (likewise with [Layer.bindTooltip])
 * * Events are propagated to the [FeatureGroup], so if the group has an event handler, it will
 *   handle events from any of the layers. This includes mouse events and custom events.
 * * Has `layeradd` and `layerremove` events
 */
open external class FeatureGroup(
    layers: Array<Layer<*>> = definedExternally,
    options: LayerOptions = definedExternally
) : LayerGroup {
    open fun setStyle(style: PathOptions): FeatureGroup /* this */
    open fun bringToFront(): FeatureGroup /* this */
    open fun bringToBack(): FeatureGroup /* this */
    open fun getBounds(): LatLngBounds
}
