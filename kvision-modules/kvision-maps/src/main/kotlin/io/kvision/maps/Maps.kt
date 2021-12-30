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

import externals.leaflet.geo.LatLng
import externals.leaflet.layer.tile.TileLayerOptions
import externals.leaflet.layer.vector.PolylineOptions
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
            "$this - Unable to get HTMLElement"
        }

        leafletMap = LeafletMap(thisElement)
        leafletMap.mapConfigurer()
    }

    fun addTileLayer(
        urlTemplate: String,
        configure: TileLayerOptions.() -> Unit = {}
    ) {
        MapsModule
            .createTileLayer(urlTemplate = urlTemplate, configure)
            .addTo(leafletMap)
    }

    fun addPolyline(
        latLngs: Collection<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ) {
        MapsModule
            .createPolyline(latLngs, configure)
            .addTo(leafletMap)
    }

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
