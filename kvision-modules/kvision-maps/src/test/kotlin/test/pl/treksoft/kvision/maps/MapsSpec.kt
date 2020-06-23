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

import pl.treksoft.kvision.maps.Maps
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.px
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class MapsSpec : DomSpec {

//    @Test
    fun renderResponsive() {
        val imageUrl = "https://svn.apache.org/repos/asf/comdev/project-logos/originals/isis.svg"
        val svgElement = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg")
        svgElement.setAttribute("viewBox", "0 0 200 200")
        svgElement.innerHTML = "<rect width='200' height='200'/><rect x='75' y='23' width='50' height='50' style='fill:red'/><rect x='75' y='123' width='50' height='50' style='fill:#0013ff'/>"
        val svgElementBounds = arrayOf(arrayOf(32, -130), arrayOf(13, -100))
        //      KVManagerMaps.leaflet.svgOverlay(svgElement, svgElementBounds).addTo(map);

        run {
            val root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
            val maps = Maps(53.65425, 10.1545, 15)
                    .apply {
                        width = 300.px
                        height = 600.px
                    }
            maps.imageOverlay(imageUrl, svgElementBounds, maps)
            root.add(maps)
            val element = document.getElementById("test")
            assertEqualsHtml(
                    "<div style=\"width: 300px; height: 600px;\"><canvas height=\"0\" class=\"chartjs-render-monitor\" width=\"0\" style=\"display: block; width: 0px; height: 0px;\"></canvas></div>",
                    element?.innerHTML,
                    "Should render correct responsive chart"
            )
        }
    }

}
