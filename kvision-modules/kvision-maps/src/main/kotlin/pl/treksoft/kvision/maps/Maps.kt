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

/**
 * Maps component.
 *
 * @constructor
 * @param lat initial latitude value
 * @param lng initial longitude value
 * @param zoom initial zoom
 * @param classes a set of CSS class names
 */
open class Maps(
    val lat: Number,
    val lng: Number,
    val zoom: Number,
    classes: Set<String> = setOf(),
    init: (Maps.() -> Unit)? = null
) : Widget(classes) {

    var jsMaps: dynamic = null

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun afterInsert(node: VNode) {
        createMaps()
    }

    @Suppress("UnsafeCastFromDynamic")
    protected fun createMaps() {
        (this.getElement() as? HTMLElement)?.let {
            jsMaps = KVManagerMaps.leaflet.map(it, obj {
                this.center = arrayOf(lat, lng)
                this.zoom = zoom
            })
            KVManagerMaps.leaflet.tileLayer(
                "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                obj {
                    this.attribution =
                        "&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors"
                }).addTo(jsMaps)
        }
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
    classes: Set<String> = setOf(),
    init: (Maps.() -> Unit)? = null
): Maps {
    val maps = Maps(lat, lng, zoom, classes, init)
    this.add(maps)
    return maps
}
