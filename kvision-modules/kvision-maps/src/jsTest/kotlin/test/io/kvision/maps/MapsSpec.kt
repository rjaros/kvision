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

import io.kvision.maps.DefaultTileLayers
import io.kvision.maps.Maps
import io.kvision.maps.Maps.Companion.L
import io.kvision.maps.externals.leaflet.geo.CRS
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.FeatureGroup
import io.kvision.maps.externals.leaflet.layer.overlay.SVGOverlay
import io.kvision.maps.maps
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.utils.px
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.svg.SVGElement


class MapsSpec : DomSpec {

    @Test
    fun renderSvg(): Unit = run {

        val svgElement: SVGElement = document
            .createElementNS("http://www.w3.org/2000/svg", "svg")
            as SVGElement
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
            configureLeafletMap {
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

        assertContainsHtml(
            svgString,
            element.innerHTML,
            "actual HTML must contain 3 SVG rectangles"
        )
    }

    @Test
    fun addMapToRoot() = run {
        val root = Root("test", containerType = ContainerType.FIXED)

        val map = Maps {
            width = 300.px
            height = 600.px

            configureLeafletMap {
                setView(LatLng(55, 33), 11)
                options.crs = CRS.Simple

                DefaultTileLayers.Empty.addTo(this)

                val featureGroup = FeatureGroup()
                featureGroup.addTo(this)

                val layers = L.layers(baseLayers = DefaultTileLayers.baseLayers) {
                    position = "topleft"
                }
                layers.addTo(this)

                if (featureGroup.getBounds().isValid()) {
                    fitBounds(featureGroup.getBounds())
                }
            }
        }
        root.add(map)
        val element: HTMLElement = document.getElementById("test") as HTMLElement

        assertEqualsHtml(
            expected = fullMapHtml,
            actual = element.innerHTML,
            "expect full map HTML is added to test element",
            normalizeHtml = true
        )
    }

    @Test
    fun addPolyLine(): Unit = run {

        val polyline = L.polyline(
            listOf(
                LatLng(55, 2),
                LatLng(65, 2),
                LatLng(65, 20),
                LatLng(55, 20),
                LatLng(55, 2),
            )
        ) {
            noClip = true
        }

        val root = Root("test", containerType = ContainerType.FIXED)
        val map = Maps {
            width = 300.px
            height = 600.px
            configureLeafletMap {
                setView(LatLng(0, 0), 11)
                options.crs = CRS.Simple

                addLayer(polyline)
            }
        }
        root.add(map)

        // must focus the map on the lines, so they are visible
        map.leafletMap { fitBounds(polyline.getBounds()) }

        val element: Element = document.getElementById("test")!!

        val expectedSvgLine =
            """<path class="leaflet-interactive" stroke="#3388ff" stroke-opacity="1" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" fill="none" d="M-9 5L-9 -5L9 -5L9 5L-9 5">"""
        assertContainsHtml(
            expectedSvgLine,
            element.innerHTML,
            "actual HTML must a path"
        )
    }

    @Test
    fun testAddImageOverlay() = run {
        //GIVEN
        val imageUrl = "https://www.w3.org/Icons/SVG/svg-logo-h.svg"
        val w3cLogoOverlay = L.imageOverlay(
            imageUrl,
            LatLngBounds(
                LatLng(0, 0),
                LatLng(0.1, 0.1)
            )
        )

        val map = Root("test", containerType = ContainerType.FIXED)
            .maps {
                width = 300.px
                height = 600.px
                configureLeafletMap {
                    setView(LatLng(0, 0), 11)
                    options.crs = CRS.Simple
                }
            }

        //WHEN
        map.leafletMap {
            w3cLogoOverlay.addTo(this)
            w3cLogoOverlay.bringToFront()
            fitBounds(w3cLogoOverlay.getBounds())
        }

        // THEN
        val element = document.getElementById("test")!!

        assertContainsHtml(
            imageUrl,
            element.innerHTML,
            "actual HTML must contain w3c SVG logo URL"
        )

    }

    companion object {
        /** The entire Leaflet map HTML - used to verify that KVision creates it correctly */
        //language=html
        private val fullMapHtml =
            """
<div class="leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom" tabindex="0" style="width: 300px; height: 600px; position: relative;">
  <div class="leaflet-pane leaflet-map-pane" style="transform: translate3d(0px, 0px, 0px);">
    <div class="leaflet-pane leaflet-tile-pane">
      <div class="leaflet-layer " style="z-index: 1;">
      </div>
    </div>
    <div class="leaflet-pane leaflet-overlay-pane">
    </div>
    <div class="leaflet-pane leaflet-shadow-pane">
    </div>
    <div class="leaflet-pane leaflet-marker-pane">
    </div>
    <div class="leaflet-pane leaflet-tooltip-pane">
    </div>
    <div class="leaflet-pane leaflet-popup-pane">
    </div>
    <div class="leaflet-proxy leaflet-zoom-animated" style="transform: translate3d(310204px, 165831px, 0px) scale(1024);">
    </div>
  </div>
  <div class="leaflet-control-container">
    <div class="leaflet-top leaflet-left">
      <div class="leaflet-control-zoom leaflet-bar leaflet-control">
        <a class="leaflet-control-zoom-in" href="#" title="Zoom in" role="button" aria-label="Zoom in" aria-disabled="false">
             <span aria-hidden="true">
             +
             </span>
        </a>
        <a class="leaflet-control-zoom-out" href="#" title="Zoom out" role="button" aria-label="Zoom out" aria-disabled="false">
             <span aria-hidden="true">
             −
             </span>
        </a>
      </div>
      <div class="leaflet-control-layers leaflet-control" aria-haspopup="true">
        <a class="leaflet-control-layers-toggle" href="#" title="Layers" role="button">
        </a>
        <section class="leaflet-control-layers-list">
          <div class="leaflet-control-layers-base">
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39" checked="checked" disabled="">
                <span>
                  Empty
                </span>
              </span>
            </label>
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39">
                <span>
                  Esri.WorldImagery
                </span>
              </span>
            </label>
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39">
                <span>
                  Esri.WorldTopoMap
                </span>
              </span>
            </label>
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39">
                <span>
                  OpenStreetMap
                </span>
              </span>
            </label>
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39">
                <span>
                  MtbMap
                </span>
              </span>
            </label>
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39">
                <span>
                  CartoDB.Voyager
                </span>
              </span>
            </label>
            <label>
              <span>
                <input type="radio" class="leaflet-control-layers-selector" name="leaflet-base-layers_39">
                <span>
                  Hike &amp; Bike Map
                </span>
              </span>
            </label>
          </div>
          <div class="leaflet-control-layers-separator" style="display: none;">
          </div>
          <div class="leaflet-control-layers-overlays">
          </div>
        </section>
      </div>
    </div>
    <div class="leaflet-top leaflet-right">
    </div>
    <div class="leaflet-bottom leaflet-left">
    </div>
    <div class="leaflet-bottom leaflet-right">
      <div class="leaflet-control-attribution leaflet-control">
        <a href="https://leafletjs.com" title="A JavaScript library for interactive maps">
          <svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="12" height="8" viewBox="0 0 12 8" class="leaflet-attribution-flag">
            <path fill="#4C7BE1" d="M0 0h12v4H0z">
            </path>
            <path fill="#FFD500" d="M0 4h12v3H0z">
            </path>
            <path fill="#E0BC00" d="M0 7h12v1H0z">
            </path>
          </svg>
          Leaflet
        </a>
      </div>
    </div>
  </div>
</div>
""".trimIndent()

    }
}
