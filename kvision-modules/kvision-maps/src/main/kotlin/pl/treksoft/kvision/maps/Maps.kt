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
import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.KVManagerMaps
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set

data class LatLng(val lat: Number, val lng: Number)

fun LatLng.toArray() = arrayOf(lat, lng)

data class LatLngBounds(val corner1: LatLng, val corner2: LatLng)

fun LatLngBounds.toArray() = arrayOf(corner1.toArray(), corner2.toArray())

data class ImageOverlayOptions(
    val opacity: Number? = null,
    val alt: String? = null,
    val interactive: Boolean? = null,
    val crossOrigin: Boolean? = null
    // other options ...
)

fun ImageOverlayOptions.toJs(): dynamic {
    return obj {
        if (opacity != null) this.opacity = opacity
        if (alt != null) this.alt = alt
        if (interactive != null) this.interactive = interactive
        if (crossOrigin != null) this.crossOrigin = crossOrigin
        // other options ...
    }
}

/**
 * Maps component.
 *
 * @constructor
 * @param lat initial latitude value
 * @param lng initial longitude value
 * @param zoom initial zoom
 * @param showMarker show marker in the initial position
 * @param classes a set of CSS class names
 */
open class Maps(
    val lat: Number,
    val lng: Number,
    val zoom: Number,
    val showMarker: Boolean = false,
    classes: Set<String> = setOf(),
    init: (Maps.() -> Unit)? = null
) : Widget(classes) {

    var jsMaps: dynamic = null

    private var mapObjects = mutableListOf<dynamic>()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun afterInsert(node: VNode) {
        createMaps()
        mapObjects.forEach {
            it.addTo(jsMaps)
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    protected fun createMaps() {
        (this.getElement() as? HTMLElement)?.let {
            jsMaps = KVManagerMaps.leaflet.map(it, obj {
                this.center = arrayOf(lat, lng)
                this.zoom = zoom
            })
            KVManagerMaps.leaflet.marker(arrayOf(lat, lng)).addTo(jsMaps)
            KVManagerMaps.leaflet.tileLayer(
                "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                obj {
                    this.attribution =
                        "&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors"
                }).addTo(jsMaps)
        }
    }

    fun imageOverlay(url: String, bounds: LatLngBounds, options: ImageOverlayOptions? = null) {
        val overlay = KVManagerMaps.leaflet.imageOverlay(url, bounds.toArray(), options?.toJs())
        if (jsMaps != null) {
            overlay.addTo(jsMaps)
        }
        mapObjects.add(overlay)
    }

    override fun afterDestroy() {
        jsMaps?.remove()
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
    classes: Set<String>? = null,
    className: String? = null,
    init: (Maps.() -> Unit)? = null
): Maps {
    val maps = Maps(lat, lng, zoom, showMarker, classes ?: className.set, init)
    this.add(maps)
    return maps
}
