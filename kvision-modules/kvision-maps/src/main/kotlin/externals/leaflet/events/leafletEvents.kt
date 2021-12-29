@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.events

import externals.leaflet.geo.Coords
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.layer.overlay.Popup
import externals.leaflet.layer.overlay.Tooltip
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

external interface LeafletEvent {
    var type: String
    var target: Any
    var sourceTarget: Any
    /** Replacement for `var layer: Any` */
    var propagatedFrom: Any
}

external interface LeafletMouseEvent : LeafletEvent {
    var latlng: LatLng
    var layerPoint: Point
    var containerPoint: Point
    var originalEvent: MouseEvent
}

external interface LeafletKeyboardEvent : LeafletEvent {
    var originalEvent: KeyboardEvent
}

external interface LocationEvent : LeafletEvent {
    var latlng: LatLng
    var bounds: LatLngBounds
    var accuracy: Number
    var altitude: Number
    var altitudeAccuracy: Number
    var heading: Number
    var speed: Number
    var timestamp: Number
}

external interface ErrorEvent : LeafletEvent {
    var message: String
    var code: Number
}

external interface LayerEvent : LeafletEvent {
    var layer: Layer
}

external interface LayersControlEvent : LayerEvent {
    var name: String
}

external interface TileEvent : LeafletEvent {
    var tile: HTMLImageElement
    var coords: Coords
}

external interface TileErrorEvent : TileEvent {
    var error: Error
}

external interface ResizeEvent : LeafletEvent {
    var oldSize: Point
    var newSize: Point
}

external interface GeoJSONEvent : LeafletEvent {
    var layer: Layer
    var properties: Any
    var geometryType: String
    var id: String
}

external interface PopupEvent : LeafletEvent {
    var popup: Popup
}

external interface TooltipEvent : LeafletEvent {
    var tooltip: Tooltip
}

external interface DragEndEvent : LeafletEvent {
    var distance: Number
}

external interface ZoomAnimEvent : LeafletEvent {
    var center: LatLng
    var zoom: Number
    var noUpdate: Boolean?
}
