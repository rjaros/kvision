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
package test.io.kvision.maps

import externals.leaflet.geo.CRS
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.FeatureGroup
import externals.leaflet.layer.overlay.SVGOverlay
import io.kotest.matchers.shouldBe
import io.kvision.MapsModule
import io.kvision.maps.BaseTileLayer
import io.kvision.maps.Maps
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.utils.px
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.browser.document
import org.w3c.dom.svg.SVGElement

// TODO tidy up

class MapsSpec : DomSpec {

    @Test
    fun renderSvg(): Unit = run {

        val svgElement: SVGElement = document
            .createElementNS("http://www.w3.org/2000/svg", "svg") as SVGElement
        svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg")
        svgElement.setAttribute("viewBox", "0 0 200 200")
        val svgString = listOf(
            """ <rect width="200" height="200"></rect>                                   """,
            """ <rect x="75" y="23" width="50" height="50" style="fill:red"></rect>      """,
            """ <rect x="75" y="123" width="50" height="50" style="fill:#0013ff"></rect> """,
        ).joinToString("") { it.trim() }
        svgElement.innerHTML = svgString

        val bounds = LatLngBounds(
            LatLng(0, 0),
            LatLng(0.1, 0.1)
        )

        val root = Root("test", containerType = ContainerType.FIXED)
        val map = Maps {
                width = 300.px
                height = 600.px
                configureMap {
                    setView(LatLng(0, 0), 11)
                    options.crs = CRS.Simple
                    addLayer(SVGOverlay(svgElement, bounds))
                }
            }
        root.add(map)

        val element = document.getElementById("test")!!
        assertTrue(
            element.innerHTML.contains(svgString),
            "Must contain svg xml passed in"
        )
    }

    @Test
    fun addMapToRoot() = run {
        // TODO fix test

        val root = Root("test", containerType = ContainerType.FIXED)

        val map = Maps {
            width = 300.px
            height = 600.px

            configureMap {
                setView(LatLng(55, 33), 11)
                options.crs = CRS.Simple


//                val baseLayer = MapsModule.createTileLayer("")
//                baseLayer.addTo(this)

                val featureGroup = FeatureGroup<Any>()
                featureGroup.addTo(this)

                addTileLayer("")

                val layers = MapsModule.createLayers(
                    baseLayers = BaseTileLayer.defaultLayers
                ) {
                    position = "topleft"
                }
                layers.addTo(this)
//                addControl(layers)
            }
        }
        root.add(map)
        val element = document.getElementById("test")!!

        val expectedHtml: String = """
            <div class="leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom" tabindex="0" style="width: 300px; height: 600px; position: relative;">
              <div class="leaflet-pane leaflet-map-pane" style="transform: translate3d(0px, 0px, 0px);">
                <div class="leaflet-pane leaflet-tile-pane">
                  <div class="leaflet-layer " style="z-index: 1;">
                    <div class="leaflet-tile-container leaflet-zoom-animated" style="z-index: 18; transform: translate3d(0px, 0px, 0px) scale(1);">
                      <img alt="" role="presentation" src="" class="leaflet-tile" style="width: 256px; height: 256px; transform: translate3d(-188px, -199px, 0px);">
                    </div>
                  </div>
                </div>
                <div class="leaflet-pane leaflet-shadow-pane"></div>
                <div class="leaflet-pane leaflet-overlay-pane"></div>
                <div class="leaflet-pane leaflet-marker-pane"></div>
                <div class="leaflet-pane leaflet-tooltip-pane"></div>
                <div class="leaflet-pane leaflet-popup-pane"></div>
                <div class="leaflet-proxy leaflet-zoom-animated" style="transform: translate3d(310204px, 165831px, 0px) scale(1024);"></div>
              </div>
              <div class="leaflet-control-container">
                <div class="leaflet-top leaflet-left">
                  <div class="leaflet-control-zoom leaflet-bar leaflet-control">
                    <a class="leaflet-control-zoom-in" href="#" title="Zoom in" role="button" aria-label="Zoom in">+</a>
                    <a class="leaflet-control-zoom-out" href="#" title="Zoom out" role="button" aria-label="Zoom out">−</a>
                  </div>
                  <div class="leaflet-control-layers leaflet-control" aria-haspopup="true">
                    <a class="leaflet-control-layers-toggle" href="#" title="Layers"></a>
                    <section class="leaflet-control-layers-list">
                      <div class="leaflet-control-layers-base">
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> Empty</span>
                          </div>
                        </label>
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> OpenStreetMap</span>
                          </div>
                        </label>
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> Esri.WorldImagery</span>
                          </div>
                        </label>
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> Esri.WorldTopoMap</span>
                          </div>
                        </label>
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> MtbMap</span>
                          </div>
                        </label>
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> CartoDB.Voyager</span>
                          </div>
                        </label>
                        <label>
                          <div>
                            <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_62">
                            <span> Hike Bike</span>
                          </div>
                        </label>
                      </div>
                      <div class="leaflet-control-layers-separator" style="display: none;"></div>
                      <div class="leaflet-control-layers-overlays"></div>
                    </section>
                  </div>
                </div>
                <div class="leaflet-top leaflet-right"></div>
                <div class="leaflet-bottom leaflet-left"></div>
                <div class="leaflet-bottom leaflet-right">
                  <div class="leaflet-control-attribution leaflet-control">
                    <a href="https://leafletjs.com" title="A JS library for interactive maps">Leaflet</a>
                  </div>
                </div>
              </div>
            </div>
            """.trimIndent()

        val expectedHtmlMinified: String = expectedHtml.split("\n").joinToString("") { it.trim() }

        element.innerHTML.replace(">", ">\n") shouldBe expectedHtmlMinified.replace(">", ">\n")

    }


/*    @Test
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
            console.log(element.innerHTML)
            assertTrue(
                    element.innerHTML.contains(expected),
                    "Must contain expected string")
        }
    }*/

}
