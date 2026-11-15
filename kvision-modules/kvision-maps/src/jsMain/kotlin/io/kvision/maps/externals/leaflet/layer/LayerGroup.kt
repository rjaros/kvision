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

package io.kvision.maps.externals.leaflet.layer

import io.kvision.maps.externals.leaflet.layer.Layer.LayerOptions

/**
 * Used to group several layers and handle them as one.
 *
 * If you add it to the map, any layers added or removed from the group will be
 * added/removed on the map as well. Extends [Layer].
 */
open external class LayerGroup(
    layers: Array<Layer<*>> = definedExternally,
    options: LayerOptions = definedExternally
) : Layer<LayerOptions> {
    /**
     * Returns a GeoJSON representation of the layer group.
     */
    open fun toGeoJSON(precision: Double = definedExternally): dynamic

    /** Adds the given layer to the group. */
    open fun addLayer(layer: Layer<*>): LayerGroup /* this */

    /** Removes the layer with the given internal ID from the group. */
    open fun removeLayer(layer: Int): LayerGroup /* this */

    /** Removes the given layer from the group. */
    open fun removeLayer(layer: Layer<*>): LayerGroup /* this */

    /** Returns true if the given layer is currently added to the group. */
    open fun hasLayer(layer: Layer<*>): Boolean

    /** Removes all the layers from the group. */
    open fun clearLayers(): LayerGroup /* this */

    /**
     * Calls [methodName] on every layer contained in this group, passing any additional parameters.
     * Has no effect if the layers contained do not implement methodName.
     */
    open fun invoke(methodName: String, vararg params: dynamic): LayerGroup /* this */

    /**
     * Iterates over the layers of the group, optionally specifying context of the iterator function.
     */
    open fun eachLayer(
        fn: (layer: Layer<*>) -> Unit,
        context: dynamic = definedExternally
    ): LayerGroup /* this */

    open fun getLayer(id: Int): Layer<*>?
    open fun getLayers(): Array<Layer<*>>
    open fun setZIndex(zIndex: Int): LayerGroup /* this */
    open fun getLayerId(layer: Layer<*>): Int

    open var feature: dynamic
}
