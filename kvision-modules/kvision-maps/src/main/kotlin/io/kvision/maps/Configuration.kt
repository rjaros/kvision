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

// TODO replace with external defs

//data class LatLng(val lat: Number, val lng: Number)
//
//fun LatLng.toArray() = arrayOf(lat, lng)
//
//data class LatLngBounds(val corner1: LatLng, val corner2: LatLng)
//
//fun LatLngBounds.toArray() = arrayOf(corner1.toArray(), corner2.toArray())
//
//data class Point(val x: Number, val y: Number, val round: Boolean = false)
//
//private val ORIGIN = Point(0, 0)
//
//data class ICRS(val crs: String)
//enum class CRS(internal val crs: String) {
//    EPSG3395("EPSG3395"),
//    EPSG3857("EPSG3857"),
//    EPSG4326("EPSG4326"),
//    Simple("Simple")
//}
//
//interface ILayer //TODO flesh out with subclasses and functions?
//
//enum class Position(val position: String) {
//    TOP_LEFT("topleft"),
//    TOP_RIGHT("topright"),
//    BOTTOM_LEFT("bottomleft"),
//    BOTTOM_RIGHT("bottomright")
//}
//
//data class Icon(val iconName: String)
//
//private val DEFAULT_ICON = Icon("DefaultIcon_TBD") //TODO L.Icon.Default()
//
//data class AttributionOptions(
//    val position: Position = Position.BOTTOM_RIGHT,
//    val prefix: String = "Powered by Leaflet"
//)
//
//data class ClassExtendOptions(
//    val includes: Any? = null,
//    val options: Any? = null,
//    val static: Any? = null
//)
//
//data class ControlOptions(
//    val position: Position = Position.TOP_RIGHT
//)
//
//data class DivIconOptions(
//    val className: String = "leaflet-div-icon",
//    val html: String = "",
//    val iconAnchor: Point? = null,
//    val iconSize: Point? = null
//)
//
//data class FitBoundsOptions(
//    val animate: Boolean? = null,
//    val padding: Point = ORIGIN,
//    val paddingBottomRight: Point = ORIGIN,
//    val paddingTopLeft: Point = ORIGIN,
//    val pan: PanOptions? = null,
//    val reset: Boolean = false,
//    val zoom: ZoomOptions? = null
//)
//
//data class IconOptions(
//    val className: String = "",
//    val iconAnchor: Point? = null,
//    val iconRetinaUrl: String = "",
//    val iconSize: Point? = null,
//    val iconUrl: String = "",
//    val popupAnchor: Point? = null,
//    val shadowAnchor: Point? = null,
//    val shadowRetinaUrl: String = "",
//    val shadowSize: Point? = null,
//    val shadowUrl: String = ""
//)
//
//data class ImageOverlayOptions(
//    val opacity: Number? = null,
//    val alt: String? = null,
//    val interactive: Boolean? = null,
//    val crossOrigin: Boolean? = null
//    // other options ...
//)
//
//fun ImageOverlayOptions.toJs(): dynamic {
//    return obj {
//        if (opacity != null) this.opacity = opacity
//        if (alt != null) this.alt = alt
//        if (interactive != null) this.interactive = interactive
//        if (crossOrigin != null) this.crossOrigin = crossOrigin
//        // other options ...
//    }
//}
//
//data class LayersOptions(
//    val autoZIndex: Boolean = true,
//    val collapsed: Boolean = true,
//    val position: Position = Position.TOP_RIGHT
//)
//
//data class LocateOptions(
//    val enableHighAccuracy: Boolean = false,
//    val maxZoom: Number = Float.POSITIVE_INFINITY,
//    val maximumAge: Number = 0,
//    val setView: Boolean = false,
//    val timeout: Number = 10000,
//    val watch: Boolean = false
//)
//
//data class MapOption(
//    val attributionControl: Boolean = true,
//    val boxZoom: Boolean = true,
//    val center: LatLng? = null,
//    val closePopupOnClick: Boolean = true,
//    val crs: ICRS = ICRS("L.CRS.EPSG3857"),
//    val doubleClickZoom: Boolean = true,
//    val dragging: Boolean = true,
//    val fadeAnimation: Boolean?,
//    val inertia: Boolean = true,
//    val inertiaDeceleration: Number = 3000,
//    val inertiaMaxSpeed: Number = 1500,
//    val inertiaThreshold: Number = 14, // 32 for touch devices
//    val keyboard: Boolean = true,
//    val keyboardPanOffset: Number = 80,
//    val keyboardZoomOffset: Number = 1,
//    val layers: Array<ILayer>? = null,
//    val markerZoomAnimation: Boolean? = null,
//    val maxBounds: LatLngBounds? = null,
//    val maxZoom: Number? = null,
//    val minZoom: Number? = null,
//    val scrollWheelZoom: Boolean = true,
//    val tap: Boolean = true,
//    val tapTolerance: Number = 15,
//    val touchZoom: Boolean = true,
//    val trackResize: Boolean = true,
//    val worldCopyJump: Boolean = false,
//    val zoom: Number? = null,
//    val zoomAnimation: Number? = null,
//    val zoomAnimationThreshold: Number = 4,
//    val zoomControl: Boolean = true
//) {
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is MapOption) return false
//
//        if (layers != null) {
//            if (other.layers == null) return false
//            if (!layers.contentEquals(other.layers)) return false
//        } else if (other.layers != null) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        return layers?.contentHashCode() ?: 0
//    }
//}
//
//data class MarkerOptions(
//    val clickable: Boolean = true,
//    val draggable: Boolean = false,
//    val icon: Icon? = DEFAULT_ICON,
//    val keyboard: Boolean = true,
//    val opacity: Number = 1.0,
//    val riseOffset: Number = 250,
//    val riseOnHover: Boolean = false,
//    val title: String = "",
//    val zIndexOffset: Number = 0
//)
//
//data class PanOptions(
//    val animate: Boolean? = null,
//    val duration: Number = 0.25,
//    val easeLinearity: Number = 0.25,
//    val noMoveStart: Boolean = false
//)
//
//data class PathOptions(
//    val clickable: Boolean = true,
//    val color: String = "#03f",
//    val dashArray: String?,
//    val fill: Boolean? = null,
//    val fillColor: String = color, // default to color
//    val fillOpacity: Number = 0.2,
//    val opacity: Number = 0.5,
//    val pointerEvents: Boolean? = null,
//    val stroke: Boolean = true,
//    val weight: Number = 5
//)
//
//data class PolylineOptions(
//    val noClip: Boolean = false,
//    val smoothFactor: Number = 1.0
//)
//
//data class PopupOptions(
//    val autoPan: Boolean = true,
//    val autoPanPadding: Point = Point(5, 5),
//    val closeButton: Boolean = true,
//    val closeOnClick: Boolean? = null,
//    val maxHeight: Number? = null,
//    val maxWidth: Number = 300,
//    val minWidth: Number = 50,
//    val offset: Point = Point(0, 6),
//    val zoomAnimation: Boolean = true
//)
//
//data class ScaleOptions(
//    val imperial: Boolean = true,
//    val maxWidth: Number = 100,
//    val metric: Boolean = true,
//    val position: Position = Position.BOTTOM_LEFT,
//    val updateWhenIdle: Boolean = false
//)
//
//data class TileLayerOptions(
//    val attribution: String = "",
//    val continuousWorld: Boolean = false,
//    val detectRetina: Boolean = true,
//    val errorTileUrl: String = "",
//    val maxZoom: Number = 18,
//    val minZoom: Number = 0,
//    val noWrap: Boolean = false,
//    val opacity: Number = 1.0,
//    val reuseTiles: Boolean = false,
//    val subdomains: Array<String> = arrayOf("abc"),
//    val tileSize: Number = 256,
//    val tms: Boolean = false,
//    val unloadInvisibleTiles: Boolean = false, //WHEN mobile WebKit THEN true
//    val updateWhenIdle: Boolean = false,
//    val zIndex: Number? = null,
//    val zoomOffset: Number = 0,
//    val zoomReverse: Boolean = false
//) {
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is TileLayerOptions) return false
//
//        if (!subdomains.contentEquals(other.subdomains)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        return subdomains.contentHashCode()
//    }
//}
//
//data class WMSOptions(
//    val format: String? = "false",
//    val layers: String? = "",
//    val styles: String? = "image/jpeg",
//    val transparent: Boolean = false, // "1.1.1" What's this?
//    val version: String? = null
//)
//
//data class ZoomOptions(
//    val position: Position = Position.TOP_RIGHT
//)
//
//data class ZoomPanOptions(
//    val animate: Boolean? = null,
//    val pan: PanOptions? = null,
//    val reset: Boolean = false,
//    val zoom: ZoomOptions? = null
//)
