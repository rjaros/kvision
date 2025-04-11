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
package io.kvision

import io.kvision.maps.externals.leaflet.layer.marker.Icon
import io.kvision.utils.delete
import io.kvision.utils.obj
import io.kvision.utils.useModule

@JsModule("leaflet/dist/leaflet.css")
internal external val leafletCss: dynamic

@JsModule("leaflet/dist/images/marker-icon-2x.png")
internal external val markerIcon2xUrl: dynamic

@JsModule("leaflet/dist/images/marker-icon.png")
internal external val markerIconUrl: dynamic

@JsModule("leaflet/dist/images/marker-shadow.png")
internal external val markerShadowUrl: dynamic

/**
 * Initializer for KVision maps module.
 */
object MapsModule : ModuleInitializer {

    init {
        setDefaultIcon()
    }

    private fun setDefaultIcon() {
        Icon.Default.imagePath = ""
        delete(Icon.Default.asDynamic().prototype._getIconUrl)
        Icon.Default.asDynamic().mergeOptions(obj<Icon.IconOptions> {
            iconRetinaUrl = markerIcon2xUrl.unsafeCast<String>()
            iconUrl = markerIconUrl.unsafeCast<String>()
            shadowUrl = markerShadowUrl.unsafeCast<String>()
        })
    }

    override fun initialize() {
        useModule(leafletCss)
    }

}
