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

import com.github.snabbdom.VNode
import io.kvision.core.Container
import io.kvision.core.Widget
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import io.kvision.KVManagerMaps.leaflet as L

/**
 * Maps component.
 *
 * @constructor
 * @param lat initial latitude value
 * @param lng initial longitude value
 * @param zoom initial zoom
 * @param showMarker show marker in the initial position
 * @param baseLayerProvider tile providing service
 * @param showLayersList show layers selection list
 * @param crs Coordinate Reference System
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Maps(
    private val lat: Number,
    private val lng: Number,
    private val zoom: Number,
    private val showMarker: Boolean = false,
    val baseLayerProvider: BaseLayerProvider,
    private val showLayersList: Boolean = true,
    val crs: CRS,
    className: String? = null,
    init: (Maps.() -> Unit)? = null
) : Widget(className) {

    private var map: dynamic = null
    private val mapObjects = mutableListOf<dynamic>()
    private val featureGroup: dynamic = L.featureGroup()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        createMaps()
        mapObjects.forEach {
            it.addTo(map)
        }
    }

    /**
     * Create a native map instance.
     */
    @Suppress("UnsafeCastFromDynamic")
    fun createMaps() {
        (this.getElement() as? HTMLElement)?.let {
            map = L.map(it)
            val center = arrayOf(lat, lng)
            map.setView(center, zoom)

            val tileLayer = buildTileLayer(baseLayerProvider)
            tileLayer.addTo(map)

            if (showMarker) {
                addMarker(LatLng(lat, lng))
            }
            featureGroup.addTo(map)
            if (showLayersList) {
                val baseLayers = buildBaseLayerList()
                val mapInfo = { }.asDynamic()
                mapInfo["position"] = Position.TOP_LEFT.position
                L.control.layers(baseLayers, null, mapInfo).addTo(map)
                fitAllMarkers()
            }
        }
    }

    private fun buildBaseLayerList(): dynamic {
        val obj = {}.asDynamic()
        BaseLayerProvider.values().forEach {
            obj[it.label] = buildTileLayer(it)
        }
        return obj
    }

    private fun buildTileLayer(provider: BaseLayerProvider): dynamic {
        val obj = {}.asDynamic()
        obj["attribution"] = provider.attribution
        return L.tileLayer(provider.url, obj)
    }

    /**
     * Adds an image overlay.
     * @param url an image url
     * @param bounds a rectangle bounds
     * @param options an overlay options
     */
    fun imageOverlay(url: String, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
        val overlay = L.imageOverlay(url, bounds.toArray(), options?.toJs())
        if (map != null) {
            overlay.addTo(map)
        }
        mapObjects.add(overlay)
    }

    /**
     * Adds a SVG overlay.
     * @param svgElement a SVG element
     * @param bounds a rectangle bounds
     * @param options an overlay options
     */
    fun svgOverlay(svgElement: Element, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
        val overlay = L.svgOverlay(svgElement, bounds.toArray(), options?.toJs())
        if (map != null) {
            overlay.addTo(map)
        }
        mapObjects.add(overlay)
    }

    /**
     * Adds a marker.
     * @param latLng marker coordinates
     * @param htmlPopup a popup HTML content
     */
    fun addMarker(latLng: LatLng, htmlPopup: String? = null) {
        val marker = L.marker(latLng.toArray()).addTo(featureGroup)
        if (htmlPopup != null) marker.bindPopup(htmlPopup)
        if (map != null)
            fitAllMarkers()
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun fitAllMarkers() {
        if (featureGroup.getBounds().isValid()) map.fitBounds(featureGroup.getBounds())
    }

    override fun afterDestroy() {
        map?.remove()
    }

}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
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
    val maps = Maps(lat, lng, zoom, showMarker, baseLayerProvider, showLayersList, crs, className, init)
    this.add(maps)
    return maps
}
