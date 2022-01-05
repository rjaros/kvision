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
package io.kvision.maps

import externals.leaflet.geo.CRS
import externals.leaflet.geo.LatLng
import externals.leaflet.layer.FeatureGroup
import externals.leaflet.layer.tile.TileLayer
import io.kvision.core.Container
import io.kvision.maps.Maps.Companion.L

@Suppress("DEPRECATION")
@Deprecated("Added for compatibility with earlier version. Use new DSL instead.")
enum class BaseLayerProvider(
    val tileLayer: TileLayer<TileLayer.TileLayerOptions>
) {
    EMPTY(DefaultTileLayers.Empty),
    OSM(DefaultTileLayers.OpenStreetMap),
    EWI(DefaultTileLayers.EsriWorldImagery),
    EWT(DefaultTileLayers.EsriWorldTopoMap),
    MTB(DefaultTileLayers.OpenMtbMap),
    CDBV(DefaultTileLayers.CartoDBVoyager),
    HB(DefaultTileLayers.HikeAndBikeMap)
}

/**
 * DSL builder extension function.
 */
@Suppress("DEPRECATION")
@Deprecated("Added for compatibility with earlier version. Use new DSL instead.")
fun Container.maps(
    lat: Number,
    lng: Number,
    zoom: Number,
    showMarker: Boolean = false,
    baseLayerProvider: BaseLayerProvider = BaseLayerProvider.OSM,
    showLayersList: Boolean = true,
    crs: CRS = CRS.EPSG3857,
    className: String? = null,
    init: (Maps.() -> Unit)? = null
): Maps {
    return maps(className) {
        init?.invoke(this)
        configureLeafletMap {
            setView(LatLng(lat, lng), zoom)
            val featureGroup = FeatureGroup()
            featureGroup.addTo(this)
            if (showMarker) {
                L.marker(LatLng(lat, lng)).addTo(featureGroup)
            }
            baseLayerProvider.tileLayer.addTo(this)
            if (showLayersList) {
                val layers = L.layers(baseLayers = DefaultTileLayers.baseLayers) {
                    position = "topleft"
                }
                layers.addTo(this)
            }
            if (featureGroup.getBounds().isValid()) {
                fitBounds(featureGroup.getBounds())
            }
        }
    }
}
