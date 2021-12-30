@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.map

import externals.leaflet.layer.marker.Icon
import externals.leaflet.layer.marker.Marker
import externals.leaflet.layer.overlay.ImageOverlay
import externals.leaflet.layer.overlay.Popup
import externals.leaflet.layer.overlay.Tooltip
import externals.leaflet.layer.overlay.VideoOverlay
import externals.leaflet.layer.tile.GridLayer
import externals.leaflet.layer.tile.TileLayer
import externals.leaflet.layer.vector.Polygon
import externals.leaflet.layer.vector.Polyline
import org.w3c.dom.HTMLElement

/**
 * See [`https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/map/Map.js#L1137`](https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/map/Map.js#L1137)
 */
external interface DefaultMapPanes {
    /** Pane that contains all other map panes */
    var mapPane: HTMLElement
    /** Pane for [GridLayer]s and [TileLayer]s */
    var tilePane: HTMLElement
    /** Pane for vectors (`Path`s, like [Polyline]s and [Polygon]s), [ImageOverlay]s and [VideoOverlay]s */
    var overlayPane: HTMLElement
    /** Pane for overlay shadows (e.g. [Marker] shadows) */
    var shadowPane: HTMLElement
    /** Pane for [Icon]s of [Marker]s */
    var markerPane: HTMLElement
    /** Pane for [Tooltip]s. */
    var tooltipPane: HTMLElement
    /** Pane for [Popup]s. */
    var popupPane: HTMLElement
}
