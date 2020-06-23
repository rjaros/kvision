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

import pl.treksoft.kvision.utils.obj

data class LatLng(val lat: Number, val lng: Number)

fun LatLng.toArray() = arrayOf(lat, lng)

data class LatLngBounds(val corner1: LatLng, val corner2: LatLng)

fun LatLngBounds.toArray() = arrayOf(corner1.toArray(), corner2.toArray())

private val b = false

data class Point(val x: Number, val y: Number, val round: Boolean = false)

data class ICRS(val crs: String)

interface ILayer {}

data class AttributionOptions(
        val position: String = "bottomright",
        val prefix: String = "Powered by Leaflet")

data class ClassExtendOptions(
        val includes: Any? = null,
        val options: Any? = null,
        val static: Any? = null)

data class ControlOptions(
        val position: String = "topright")

data class DivIconOptions(
        val className: String = "leaflet-div-icon",
        val html: String = "",
        val iconAnchor: Point,
        val iconSize: Point)

data class FitBoundsOptions(
        val animate: Boolean?,
        val padding: Point = Point(0, 0),
        val paddingBottomRight: Point = Point(0, 0),
        val paddingTopLeft: Point = Point(0, 0),
        val pan: PanOptions,
        val reset: Boolean = false,
        val zoom: ZoomOptions)

//TODO rework function definitions http://definitelytyped.org/docs/leaflet--leaflet/interfaces/l.geojsonoptions.html
/*
obj {
    this.style = { featureData: dynamic ->
    // ...
    }
    this.pointToLayer = { featureData: dynamic, latlng: dynamic ->
    // ...
    }
    // other props
}
 */
data class GeoJSONOptions(
        val coordsToLatLng: Function<Array<Any>>,
        val filter: Function<Array<Any>>,
        val onEachFeature: Function<Array<Any>>,
        val pointToLayer: Function<Array<Any>>,
        val style: Function<Array<Any>>)

data class IconOptions(
        val className: String = "",
        val iconAnchor: Point? = null,
        val iconRetinaUrl: String = "",
        val iconSize: Point? = null,
        val iconUrl: String = "",
        val popupAnchor: Point? = null,
        val shadowAnchor: Point? = null,
        val shadowRetinaUrl: String = "",
        val shadowSize: Point? = null,
        val shadowUrl: String = "")

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

data class LayersOptions(
        val autoZIndex: Boolean = true,
        val collapsed: Boolean = true,
        val position: String = "topright")

data class LocateOptions(
        val enableHighAccuracy: Boolean = false,
        val maxZoom: Number = Float.POSITIVE_INFINITY,
        val maximumAge: Number = 0,
        val setView: Boolean = false,
        val timeout: Number = 10000,
        val watch: Boolean = false)

data class MapOption(
        val attributionControl: Boolean = true,
        val boxZoom: Boolean = true,
        val center: LatLng? = null,
        val closePopupOnClick: Boolean = true,
        val crs: ICRS = ICRS("L.CRS.EPSG3857"),
        val doubleClickZoom: Boolean = true,
        val dragging: Boolean = true,
        val fadeAnimation: Boolean?,
        val inertia: Boolean = true,
        val inertiaDeceleration: Number = 3000,
        val inertiaMaxSpeed: Number = 1500,
        val inertiaThreshold: Number = 14, // 32 for touch devices
        val keyboard: Boolean = true,
        val keyboardPanOffset: Number = 80,
        val keyboardZoomOffset: Number = 1,
        val layers: Array<ILayer>,
        val markerZoomAnimation: Boolean?,
        val maxBounds: LatLngBounds?,
        val maxZoom: Number?,
        val minZoom: Number?,
        val scrollWheelZoom: Boolean = true,
        val tap: Boolean = true,
        val tapTolerance: Number = 15,
        val touchZoom: Boolean = true,
        val trackResize: Boolean = true,
        val worldCopyJump: Boolean = false,
        val zoom: Number?,
        val zoomAnimation: Number?,
        val zoomAnimationThreshold: Number = 4,
        val zoomControl: Boolean = true)

data class MarkerOptions(
        val clickable: Boolean = true,
        val draggable: Boolean = false,
        //TODO   val icon : Icon = L.Icon.Default(),
        val keyboard: Boolean = true,
        val opacity: Number = 1.0,
        val riseOffset: Number = 250,
        val riseOnHover: Boolean = false,
        val title: String = "",
        val zIndexOffset: Number = 0)

data class PanOptions(
        val animate: Boolean?,
        val duration: Number = 0.25,
        val easeLinearity: Number = 0.25,
        val noMoveStart: Boolean = false)

data class PathOptions(
        val clickable: Boolean = true,
        val color: String = "#03f",
        val dashArray: String?,
        val fill: Boolean?,
        val fillColor: String, // same as color
        val fillOpacity: Number = 0.2,
        val opacity: Number = 0.5,
        val pointerEvents: Boolean?,
        val stroke: Boolean = true,
        val weight: Number = 5)

data class PolylineOptions(
        val noClip: Boolean = false,
        val smoothFactor: Number = 1.0)

data class PopupOptions(
        val autoPan: Boolean = true,
        val autoPanPadding: Point = Point(5, 5),
        val closeButton: Boolean = true,
        val closeOnClick: Boolean?,
        val maxHeight: Number?,
        val maxWidth: Number = 300,
        val minWidth: Number = 50,
        val offset: Point = Point(0, 6),
        val zoomAnimation: Boolean = true)

data class ScaleOptions(
        val imperial: Boolean = true,
        val maxWidth: Number = 100,
        val metric: Boolean = true,
        val position: String = "bottomleft",
        val updateWhenIdle: Boolean = false)

data class TileLayerOptions(
        val attribution: String = "",
        val continuousWorld: Boolean = false,
        val detectRetina: Boolean = true,
        val errorTileUrl: String = "",
        val maxZoom: Number = 18,
        val minZoom: Number = 0,
        val noWrap: Boolean = false,
        val opacity: Number = 1.0,
        val reuseTiles: Boolean = false,
        val subdomains: Array<String> = arrayOf("abc"),
        val tileSize: Number = 256,
        val tms: Boolean = false,
        val unloadInvisibleTiles: Boolean = false, //WHEN mobile WebKit THEN true
        val updateWhenIdle: Boolean = false,
        val zIndex: Number?,
        val zoomOffset: Number = 0,
        val zoomReverse: Boolean = false)

data class WMSOptions(
        val format: String = "false",
        val layers: String = "",
        val styles: String = "image/jpeg",
        val transparent: Boolean = false, // "1.1.1" What's this?
        val version: String?)

data class ZoomOptions(
        val position: String = "topright"
)

data class ZoomPanOptions(
        val animate: Boolean?,
        val pan: PanOptions?,
        val reset: Boolean = false,
        val zoom: ZoomOptions?)
