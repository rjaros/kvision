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
package pl.treksoft.kvision.maps

import com.github.snabbdom.VNode
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.utils.set
import pl.treksoft.kvision.KVManagerMaps.leaflet as L

/**
 * Maps component.
 *
 * @constructor
 * @param lat initial latitude value
 * @param lng initial longitude value
 * @param zoom initial zoom
 * @param showMarker show marker in the initial position
 * @param baseLayerProvider tile providing service
 * @param crs Coordinate Reference System
 * @param classes a set of CSS class names
 */
open class Maps(
        private val lat: Number,
        private val lng: Number,
        private val zoom: Number,
        private val showMarker: Boolean = false,
        val baseLayerProvider: BaseLayerProvider,
        val crs: CRS,
        classes: Set<String> = setOf(),
        init: (Maps.() -> Unit)? = null
) : Widget(classes) {

    private var map: dynamic = null
    private val mapObjects = mutableListOf<dynamic>()
    private val featureGroup: dynamic = L.featureGroup()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun afterInsert(node: VNode) {
        createMaps()
        mapObjects.forEach {
            it.addTo(map) as Unit
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    fun createMaps() {
        (this.getElement() as? HTMLElement)?.let {
            map = L.map(it)
            val center = arrayOf(lat, lng)
            map.setView(center, zoom)

            val tileLayer = buildTileLayer(baseLayerProvider)
            tileLayer.addTo(map)

            if (baseLayerProvider != BaseLayerProvider.EMPTY) {
                featureGroup.addTo(map)
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

    fun imageOverlay(url: String, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
        val overlay = L.imageOverlay(url, bounds.toArray(), options?.toJs())
        if (map != null) {
            overlay.addTo(map)
        }
        mapObjects.add(overlay)
    }

    fun svgOverlay(svgElement: Element, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
        val overlay = L.svgOverlay(svgElement, bounds.toArray(), options?.toJs())
        if (map != null) {
            overlay.addTo(map)
        }
        mapObjects.add(overlay)
    }

    fun addMarker(latLng: LatLng, htmlPopup: String? = "not set") {
        L.marker(latLng.toArray()).addTo(featureGroup).bindPopup(htmlPopup)
        if (map != null)
            fitAllMarkers()
    }

    private fun fitAllMarkers() {
        map.fitBounds(featureGroup.getBounds())
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
        crs: CRS = CRS.EPSG3857,
        classes: Set<String>? = null,
        className: String? = null,
        init: (Maps.() -> Unit)? = null
): Maps {
    val maps = Maps(lat, lng, zoom, showMarker, baseLayerProvider, crs, classes ?: className.set, init)
    this.add(maps)
    return maps
}
