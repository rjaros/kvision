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
package io.kvision

import externals.leaflet.control.Layers
import externals.leaflet.control.LayersObject
import externals.leaflet.control.LayersOptions
import externals.leaflet.control.set
import externals.leaflet.layer.tile.TileLayer
import io.kvision.maps.BaseTileLayer
import io.kvision.utils.delete
import io.kvision.utils.obj

/**
 * Initializer for KVision maps module.
 */
object MapsModule : ModuleInitializer {

    internal val leaflet = require("leaflet")

    init {
        leaflet.Icon.Default.imagePath = ""
        delete(leaflet.Icon.Default.prototype._getIconUrl)
        leaflet.Icon.Default.mergeOptions(obj {
            iconRetinaUrl = require("leaflet/dist/images/marker-icon-2x.png")
            iconUrl = require("leaflet/dist/images/marker-icon.png")
            shadowUrl = require("leaflet/dist/images/marker-shadow.png")
        })
    }

    override fun initialize() {
        require("leaflet/dist/leaflet.css")
    }
    //
    fun createTileLayer(
        urlTemplate: String,
        configure: TileLayer.() -> Unit = {}
    ): TileLayer =
        TileLayer(
            urlTemplate = urlTemplate,
            options = js("{}"),
        ).apply(configure)

    // TODO move to more suitable location
    private fun convertTileLayer(base: BaseTileLayer): TileLayer {
        val tileLayer = TileLayer(
            urlTemplate = base.url,
            options = js("{}"),
        )
//        println("\n-\n\ntileLayer: $tileLayer")
//        println("\n-\n\ntileLayer.options: ${tileLayer.options}")
//        println("\n-\n\ntileLayer.options.minZoom: ${tileLayer.options.minZoom}")
//        println("\n-\n\ntileLayer.options.maxZoom: ${tileLayer.options.maxZoom}")
        tileLayer.options.attribution = base.attribution
        tileLayer.options.id = base.label

        return tileLayer
    }

    fun createLayerObject(tileLayers: Collection<BaseTileLayer>): LayersObject {
//        return tileLayers.associate { it.label to createTileLayer(it) }.toMutableMap()

        return tileLayers
            .associate { base -> base.label to convertTileLayer(base) }
            .entries
            .fold(object : LayersObject {}) { acc, (label, layer) ->
                acc[label] = layer
                acc
            }

    }

    fun createLayers(
        baseLayers: Collection<BaseTileLayer> = emptyList(),
        overlays: LayersObject = createLayerObject(emptyList()),
        configure: LayersOptions.() -> Unit = {}
    ): Layers {
        val layers = Layers(
            baseLayers = createLayerObject(baseLayers),
            options = js("{}"),
        )
        layers.options.configure()
        return layers
    }
}
