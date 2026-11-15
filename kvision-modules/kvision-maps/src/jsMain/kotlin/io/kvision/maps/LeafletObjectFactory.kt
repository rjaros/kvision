/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

import io.kvision.maps.externals.leaflet.control.AttributionControl
import io.kvision.maps.externals.leaflet.control.AttributionControl.AttributionControlOptions
import io.kvision.maps.externals.leaflet.control.LayersControl
import io.kvision.maps.externals.leaflet.control.LayersControl.LayersControlOptions
import io.kvision.maps.externals.leaflet.control.ScaleControl
import io.kvision.maps.externals.leaflet.control.ScaleControl.ScaleControlOptions
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.FeatureGroup
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.Layer.LayerOptions
import io.kvision.maps.externals.leaflet.layer.marker.DivIcon
import io.kvision.maps.externals.leaflet.layer.marker.DivIcon.DivIconOptions
import io.kvision.maps.externals.leaflet.layer.marker.Icon
import io.kvision.maps.externals.leaflet.layer.marker.Icon.IconOptions
import io.kvision.maps.externals.leaflet.layer.marker.Marker
import io.kvision.maps.externals.leaflet.layer.marker.Marker.MarkerOptions
import io.kvision.maps.externals.leaflet.layer.overlay.DivOverlay
import io.kvision.maps.externals.leaflet.layer.overlay.DivOverlay.DivOverlayOptions
import io.kvision.maps.externals.leaflet.layer.overlay.ImageOverlay
import io.kvision.maps.externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import io.kvision.maps.externals.leaflet.layer.overlay.Popup
import io.kvision.maps.externals.leaflet.layer.overlay.Popup.PopupOptions
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip
import io.kvision.maps.externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import io.kvision.maps.externals.leaflet.layer.overlay.VideoOverlay
import io.kvision.maps.externals.leaflet.layer.overlay.VideoOverlay.VideoOverlayOptions
import io.kvision.maps.externals.leaflet.layer.tile.TileLayer
import io.kvision.maps.externals.leaflet.layer.tile.TileLayer.TileLayerOptions
import io.kvision.maps.externals.leaflet.layer.vector.Canvas
import io.kvision.maps.externals.leaflet.layer.vector.Circle
import io.kvision.maps.externals.leaflet.layer.vector.CircleMarker
import io.kvision.maps.externals.leaflet.layer.vector.CircleMarker.CircleMarkerOptions
import io.kvision.maps.externals.leaflet.layer.vector.Polyline
import io.kvision.maps.externals.leaflet.layer.vector.Polyline.PolylineOptions
import io.kvision.maps.externals.leaflet.layer.vector.Rectangle
import io.kvision.maps.externals.leaflet.layer.vector.Renderer.RendererOptions
import io.kvision.maps.externals.leaflet.layer.vector.SVG
import io.kvision.maps.externals.leaflet.map.LeafletMap
import io.kvision.maps.externals.leaflet.map.LeafletMap.LeafletMapOptions
import io.kvision.utils.obj
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLVideoElement

/**
 * Leaflet constructors.
 *
 * Equivalent to Leaflet's `L` shortcut.
 *
 * See [`https://leafletjs.com/reference.html#class-class-factories`](https://leafletjs.com/reference.html#class-class-factories)
 */
object LeafletObjectFactory {

    fun map(
        element: HTMLElement,
        options: LeafletMapOptions?,
    ): LeafletMap =
        LeafletMap(
            element = element,
            options = options ?: obj {}
        )

    fun map(
        element: HTMLElement,
        configure: LeafletMapOptions.() -> Unit = {},
    ): LeafletMap =
        LeafletMap(
            element = element,
            options = obj(configure)
        )

    fun polyline(
        latLngs: List<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ): Polyline = Polyline(
        latLngs.toTypedArray(),
        options = obj(configure),
    )

    fun multiPolyline(
        latLngs: List<List<LatLng>>,
        configure: PolylineOptions.() -> Unit = {},
    ): Polyline = Polyline(
        latLngs.map { it.toTypedArray() }.toTypedArray(),
        options = obj(configure),
    )

    fun tileLayer(
        urlTemplate: String,
        configure: TileLayerOptions.() -> Unit = {}
    ): TileLayer<TileLayerOptions> = TileLayer(
        urlTemplate = urlTemplate,
        options = obj(configure),
    )

    fun layers(
        baseLayers: dynamic = layersObject(emptyMap()),
        overlays: dynamic = layersObject(emptyMap()),
        configure: LayersControlOptions.() -> Unit = {},
    ): LayersControl = LayersControl(
        baseLayers = baseLayers,
        overlays = overlays,
        options = obj(configure),
    )

    /**
     * @param[tileLayers] Map display names to tile layers. The names can contain HTML, which
     * allows you to add additional styling to the items.
     */
    fun layersObject(tileLayers: Map<String, TileLayer<*>>): dynamic =
        tileLayers
            .entries
            .fold(obj {}) { acc, (name, layer) ->
                acc[name] = layer
                acc
            }

    fun imageOverlay(
        imageUrl: String,
        bounds: LatLngBounds,
        configure: ImageOverlayOptions.() -> Unit = {},
    ): ImageOverlay<ImageOverlayOptions> = ImageOverlay(
        imageUrl = imageUrl,
        bounds = bounds,
        options = obj(configure),
    )

    fun attribution(
        configure: AttributionControlOptions.() -> Unit = {},
    ): AttributionControl = AttributionControl(options = obj(configure))

    fun scale(
        configure: ScaleControlOptions.() -> Unit = {},
    ): ScaleControl = ScaleControl(options = obj(configure))

    fun divIcon(
        configure: DivIconOptions.() -> Unit = {},
    ): DivIcon = DivIcon(options = obj(configure))

    fun icon(
        configure: IconOptions.() -> Unit = {},
    ): Icon<IconOptions> = Icon(options = obj(configure))

    fun marker(
        latlng: LatLng,
        configure: MarkerOptions.() -> Unit = {},
    ): Marker = Marker(latlng, options = obj(configure))

    fun divOverlay(
        source: Layer<*>,
        configure: DivOverlayOptions.() -> Unit = {},
    ): DivOverlay<DivOverlayOptions> =
        DivOverlay(source = source, options = obj(configure))

    fun popup(
        source: Layer<*>,
        configure: PopupOptions.() -> Unit = {},
    ): Popup = Popup(source = source, options = obj(configure))

    fun tooltip(
        source: Layer<*>? = null,
        configure: TooltipOptions.() -> Unit = {},
    ): Tooltip = when (source) {
        null -> Tooltip(options = obj(configure))
        else -> Tooltip(options = obj(configure), source = source)
    }

    fun videoOverlay(
        video: String,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj(configure))

    fun videoOverlay(
        video: List<String>,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(
            video = video.map { it }.toTypedArray(),
            bounds = bounds,
            options = obj(configure)
        )

    fun videoOverlay(
        video: HTMLVideoElement,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj(configure))

    fun canvas(
        configure: Canvas.CanvasOptions.() -> Unit = {},
    ): Canvas = Canvas(options = obj(configure))

    fun circleMarker(
        latlng: LatLng,
        configure: CircleMarkerOptions.() -> Unit = {},
    ): CircleMarker = CircleMarker(latlng = latlng, options = obj(configure))

    fun circle(
        latlng: LatLng,
        configure: CircleMarkerOptions.() -> Unit = {},
    ): Circle = Circle(latlng = latlng, options = obj(configure))

    /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
    fun polygon(
        latlngs: List<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ): io.kvision.maps.externals.leaflet.layer.vector.Polygon =
        io.kvision.maps.externals.leaflet.layer.vector.Polygon(
            latlngs = latlngs.toTypedArray(),
            options = obj(configure)
        )

    /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
    fun multiPolygon(
        latlngs: List<List<List<LatLng>>>,
        configure: PolylineOptions.() -> Unit = {},
    ): io.kvision.maps.externals.leaflet.layer.vector.Polygon =
        io.kvision.maps.externals.leaflet.layer.vector.Polygon(
            latlngs = latlngs
                .map {
                    it.map { it.toTypedArray() }.toTypedArray()
                }.toTypedArray(),
            options = obj(configure)
        )

    fun rectangle(
        latLngBounds: LatLngBounds,
        configure: PolylineOptions.() -> Unit = {},
    ): Rectangle = Rectangle(latLngBounds = latLngBounds, options = obj(configure))

    fun svg(
        configure: RendererOptions.() -> Unit = {},
    ): SVG = SVG(options = obj(configure))

    fun featureGroup(
        layers: Array<Layer<*>>,
        configure: LayerOptions.() -> Unit = {},
    ): FeatureGroup = FeatureGroup(layers = layers, options = obj(configure))

    fun latLng(
        latitude: Double,
        longitude: Double
    ): LatLng = LatLng(latitude, longitude)

    fun latLng(
        latitude: Double,
        longitude: Double,
        altitude: Double,
    ): LatLng = LatLng(latitude, longitude, altitude)

    fun latLng(latLng: Pair<Double, Double>, altitude: Double? = null): LatLng =
        when (altitude) {
            null -> LatLng(latLng.first, latLng.second)
            else -> LatLng(latLng.first, latLng.second, altitude)
        }

    fun latLngBounds(
        southWest: LatLng,
        northEast: LatLng,
    ): LatLngBounds = LatLngBounds(southWest, northEast)

    fun latLngBounds(
        southWestToNorthEast: Pair<LatLng, LatLng>,
    ): LatLngBounds = latLngBounds(southWestToNorthEast.first, southWestToNorthEast.second)

}
