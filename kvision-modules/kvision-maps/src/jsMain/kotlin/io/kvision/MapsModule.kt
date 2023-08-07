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

/**
 * Initializer for KVision maps module.
 */
object MapsModule : ModuleInitializer {

    internal val leaflet = require("leaflet")

    init {
        setDefaultIcon()
    }

    private fun setDefaultIcon() {
        leaflet.Icon.Default.imagePath = ""
        delete(leaflet.Icon.Default.prototype._getIconUrl)
        leaflet.Icon.Default.mergeOptions(obj<Icon.IconOptions> {
            iconRetinaUrl = require("leaflet/dist/images/marker-icon-2x.png").unsafeCast<String>()
            iconUrl = require("leaflet/dist/images/marker-icon.png").unsafeCast<String>()
            shadowUrl = require("leaflet/dist/images/marker-shadow.png").unsafeCast<String>()
        })
    }

    override fun initialize() {
        require("leaflet/dist/leaflet.css")
    }

}
