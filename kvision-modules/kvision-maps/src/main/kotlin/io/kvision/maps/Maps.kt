/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020-present Jörg Rade
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

import externals.leaflet.layer.tile.TileLayer
import externals.leaflet.map.LeafletMap
import io.kvision.MapsModule
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode
import org.w3c.dom.HTMLElement

// TODO tidy this up

/**
 * Maps component.
 *
 * @constructor
 */
open class Maps(
    className: String? = null,
    init: (Maps.() -> Unit) = {}
) : Widget(className) {

    private lateinit var leafletMap: LeafletMap

    private var mapConfigurer: LeafletMap.() -> Unit = { }
    fun configureMap(configure: LeafletMap.() -> Unit) {
        mapConfigurer = configure
    }

    init {
        useSnabbdomDistinctKey()
        this.init()
    }

    /** Create a native map instance. */
    override fun afterInsert(node: VNode) {
        val thisElement: HTMLElement = requireNotNull(this.getElement() as? HTMLElement) {
            "$this - Unable to get Widget element"
        }

        leafletMap = LeafletMap(thisElement)
        leafletMap.mapConfigurer()
    }

    fun addTileLayer(
        urlTemplate: String,
        configure: TileLayer.() -> Unit = {}
    ) {
        MapsModule
            .createTileLayer(urlTemplate = urlTemplate)
            .apply(configure)
            .addTo(leafletMap)
    }


//    (this.getElement() as? HTMLElement)?.let {
//        map = L.map(it)
//        val center = arrayOf(lat, lng)
//        map.setView(center, zoom)
//
//        val tileLayer = buildTileLayer(baseLayerProvider)
//        tileLayer.addTo(map)
//
//        if (showMarker) {
//            addMarker(LatLng(lat, lng))
//        }
//        featureGroup.addTo(map)
//        if (showLayersList) {
//            val baseLayers = buildBaseLayerList()
//            val mapInfo = { }.asDynamic()
//            mapInfo["position"] = Position.TOP_LEFT.position
//            L.control.layers(baseLayers, null, mapInfo).addTo(map)
//            fitAllMarkers()
//        }
//    }
//}


//
//    private fun buildBaseLayerList(): dynamic {
//        val obj = {}.asDynamic()
//        BaseTileLayer.defaultLayers.forEach {
//            obj[it.label] = buildTileLayer(it)
//        }
//        return obj
//    }
//
//    private fun buildTileLayer(baseTileLayer: BaseTileLayer): dynamic {
//        val obj = {}.asDynamic()
//        obj["attribution"] = baseTileLayer.attribution
//        return L.tileLayer(baseTileLayer.url, obj)
//    }
//
//    /**
//     * Adds an image overlay.
//     * @param url an image url
//     * @param bounds a rectangle bounds
//     * @param options an overlay options
//     */
//    fun imageOverlay(url: String, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
//        val overlay = L.imageOverlay(url, bounds.toArray(), options?.toJs())
//        if (map != null) {
//            overlay.addTo(map)
//        }
//        mapObjects.add(overlay)
//    }
//
//    /**
//     * Adds a SVG overlay.
//     * @param svgElement a SVG element
//     * @param bounds a rectangle bounds
//     * @param options an overlay options
//     */
//    fun svgOverlay(svgElement: Element, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
//        val overlay = L.svgOverlay(svgElement, bounds.toArray(), options?.toJs())
//        if (map != null) {
//            overlay.addTo(map)
//        }
//        mapObjects.add(overlay)
//    }
//
//    /**
//     * Adds a marker.
//     * @param latLng marker coordinates
//     * @param htmlPopup a popup HTML content
//     */
//    fun addMarker(latLng: LatLng, htmlPopup: String? = null) {
//        val marker = L.marker(latLng.toArray()).addTo(featureGroup)
//        if (htmlPopup != null) marker.bindPopup(htmlPopup)
//        if (map != null)
//            fitAllMarkers()
//    }
//

    //    private fun fitAllMarkers() {
//        if (featureGroup.getBounds().isValid())
//            leafletMap.fitBounds(featureGroup.getBounds())
//    }
//
    override fun afterDestroy() {
        if (this::leafletMap.isInitialized) {
            leafletMap.remove()
        }
    }

    companion object {
        init {
            MapsModule.initialize()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.maps(
    className: String? = null,
    init: (Maps.() -> Unit) = { }
): Maps {
    val maps = Maps(className, init)
    this.add(maps)
    return maps
}
