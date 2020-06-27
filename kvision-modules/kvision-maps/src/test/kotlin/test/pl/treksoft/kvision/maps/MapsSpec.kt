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
package test.pl.treksoft.kvision.maps

import pl.treksoft.kvision.maps.*
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.px
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class MapsSpec : DomSpec {

    @Test
    fun renderSvg() {
        run {
            val svgElement = document.createElementNS("http://www.w3.org/2000/svg", "svg")
            svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg")
            svgElement.setAttribute("viewBox", "0 0 200 200")
            val svgString = "<rect width='200' height='200'/><rect x='75' y='23' width='50' height='50' style='fill:red'/><rect x='75' y='123' width='50' height='50' style='fill:#0013ff'/>"
            svgElement.innerHTML = svgString
            val bounds = LatLngBounds(
                    LatLng(0, 0),
                    LatLng(0.1, 0.1))

            val root = Root("test", containerType = ContainerType.FIXED)
            val map = Maps(
                    0,
                    0,
                    11,
                    baseLayerProvider = BaseLayerProvider.EMPTY,
                    crs = CRS.Simple
            )
                    .apply {
                        width = 300.px
                        height = 600.px
                    }
            map.svgOverlay(svgElement, bounds)
            root.add(map)
            val element = document.getElementById("test")!!
            assertTrue(
                    element.innerHTML.contains(svgString),
                    "Must contain svg xml passed in")
        }
    }

    @Test
    fun renderImage() {
        run {
            //GIVEN
            val imageUrl = "https://www.w3.org/Icons/SVG/svg-logo-h.svg"

            val bounds = LatLngBounds(
                    LatLng(0, 0),
                    LatLng(0.1, 0.1))

            val root = Root("test", containerType = ContainerType.FIXED)

            //WHEN
            val map = Maps(
                    0,
                    0,
                    11,
                    baseLayerProvider = BaseLayerProvider.EMPTY,
                    crs = CRS.Simple
            )
                    .apply {
                        width = 300.px
                        height = 600.px
                    }
            map.imageOverlay(imageUrl, bounds)
            root.add(map)

            // then
            val expected = "<title>SVG logo combined with the W3C logo, set horizontally</title>"
            val element = document.getElementById("test")!!
            assertTrue(
                    element.innerHTML.contains(expected),
                    "Must contain expected string")
        }
    }

}
