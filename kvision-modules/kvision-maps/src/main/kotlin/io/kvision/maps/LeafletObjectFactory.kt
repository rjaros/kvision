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

package io.kvision.maps

import io.kvision.maps.externals.geojson.LineString
import io.kvision.maps.externals.geojson.MultiLineString
import io.kvision.maps.externals.geojson.MultiPolygon
import io.kvision.maps.externals.geojson.Polygon
import io.kvision.maps.externals.leaflet.control.Attribution
import io.kvision.maps.externals.leaflet.control.Attribution.AttributionOptions
import io.kvision.maps.externals.leaflet.control.Control.LayersObject
import io.kvision.maps.externals.leaflet.control.Layers
import io.kvision.maps.externals.leaflet.control.Layers.LayersOptions
import io.kvision.maps.externals.leaflet.control.Scale
import io.kvision.maps.externals.leaflet.control.Scale.ScaleOptions
import io.kvision.maps.externals.leaflet.control.Zoom
import io.kvision.maps.externals.leaflet.control.Zoom.ZoomOptions
import io.kvision.maps.externals.leaflet.control.set
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
import io.kvision.maps.externals.leaflet.layer.tile.WMS
import io.kvision.maps.externals.leaflet.layer.tile.WMS.WMSOptions
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
        configure: LeafletMapOptions.() -> Unit = {},
    ): LeafletMap =
        LeafletMap(
            element = element,
            options = obj<LeafletMapOptions>(configure)
        )

    fun polyline(
        latLngs: Collection<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ): Polyline<LineString> = Polyline(
        latLngs.toTypedArray(),
        options = obj<PolylineOptions>(configure),
    )

    fun multiPolyline(
        latLngs: Collection<Collection<LatLng>>,
        configure: PolylineOptions.() -> Unit = {},
    ): Polyline<MultiLineString> = Polyline(
        latLngs.map { it.toTypedArray() }.toTypedArray(),
        options = obj<PolylineOptions>(configure),
    )

    fun tileLayer(
        urlTemplate: String,
        configure: TileLayerOptions.() -> Unit = {}
    ): TileLayer<TileLayerOptions> = TileLayer(
        urlTemplate = urlTemplate,
        options = obj<TileLayerOptions>(configure),
    )

    fun layers(
        baseLayers: LayersObject = layersObject(emptyMap()),
        overlays: LayersObject = layersObject(emptyMap()),
        configure: LayersOptions.() -> Unit = {},
    ): Layers = Layers(
        baseLayers = baseLayers,
        overlays = overlays,
        options = obj<LayersOptions>(configure),
    )

    /**
     * @param[tileLayers] Map display names to tile layers. The names can contain HTML, which
     * allows you to add additional styling to the items.
     */
    fun layersObject(tileLayers: Map<String, TileLayer<*>>): LayersObject =
        tileLayers
            .entries
            .fold(obj<LayersObject> {}) { acc, (name, layer) ->
                acc[name] = layer
                acc
            }

    fun imageOverlay(
        imageUrl: String,
        bounds: LatLngBounds,
        configure: ImageOverlayOptions.() -> Unit = {},
    ): ImageOverlay = ImageOverlay(
        imageUrl = imageUrl,
        bounds = bounds,
        options = obj<ImageOverlayOptions>(configure),
    )

    fun attribution(
        configure: AttributionOptions.() -> Unit = {},
    ): Attribution = Attribution(options = obj<AttributionOptions>(configure))

    fun scale(
        configure: ScaleOptions.() -> Unit = {},
    ): Scale = Scale(options = obj<ScaleOptions>(configure))

    fun zoom(
        configure: ZoomOptions.() -> Unit = {},
    ): Zoom = Zoom(options = obj<ZoomOptions>(configure))

    fun divIcon(
        configure: DivIconOptions.() -> Unit = {},
    ): DivIcon = DivIcon(options = obj<DivIconOptions>(configure))

    fun icon(
        configure: IconOptions.() -> Unit = {},
    ): Icon<IconOptions> = Icon(options = obj<IconOptions>(configure))

    fun marker(
        latlng: LatLng,
        configure: MarkerOptions.() -> Unit = {},
    ): Marker = Marker(latlng, options = obj<MarkerOptions>(configure))

    fun divOverlay(
        source: Layer<*>,
        configure: DivOverlayOptions.() -> Unit = {},
    ): DivOverlay<DivOverlayOptions> =
        DivOverlay(source = source, options = obj<DivOverlayOptions>(configure))

    fun popup(
        source: Layer<*>,
        configure: PopupOptions.() -> Unit = {},
    ): Popup = Popup(source = source, options = obj<PopupOptions>(configure))

    fun tooltip(
        source: Layer<*>? = null,
        configure: TooltipOptions.() -> Unit = {},
    ): Tooltip = when (source) {
        null -> Tooltip(options = obj<TooltipOptions>(configure))
        else -> Tooltip(options = obj<TooltipOptions>(configure), source = source)
    }

    fun videoOverlay(
        video: String,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj<VideoOverlayOptions>(configure))

    fun videoOverlay(
        video: Array<String>,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj<VideoOverlayOptions>(configure))

    fun videoOverlay(
        video: HTMLVideoElement,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj<VideoOverlayOptions>(configure))

    fun wms(
        baseUrl: String,
        configure: WMSOptions.() -> Unit = {},
    ): WMS = WMS(baseUrl = baseUrl, options = obj<WMSOptions>(configure))

    fun canvas(
        configure: RendererOptions.() -> Unit = {},
    ): Canvas = Canvas(options = obj<RendererOptions>(configure))

    fun circleMarker(
        latlng: LatLng,
        configure: CircleMarkerOptions.() -> Unit = {},
    ): CircleMarker = CircleMarker(latlng = latlng, options = obj<CircleMarkerOptions>(configure))

    fun circle(
        latlng: LatLng,
        configure: CircleMarkerOptions.() -> Unit = {},
    ): Circle = Circle(latlng = latlng, options = obj<CircleMarkerOptions>(configure))

    /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
    fun polygon(
        latlngs: Collection<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ): io.kvision.maps.externals.leaflet.layer.vector.Polygon<Polygon> =
        io.kvision.maps.externals.leaflet.layer.vector.Polygon(
            latlngs = latlngs.toTypedArray(),
            options = obj<PolylineOptions>(configure)
        )

    /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
    fun multiPolygon(
        latlngs: Collection<Collection<Collection<LatLng>>>,
        configure: PolylineOptions.() -> Unit = {},
    ): io.kvision.maps.externals.leaflet.layer.vector.Polygon<MultiPolygon> =
        io.kvision.maps.externals.leaflet.layer.vector.Polygon(
            latlngs = latlngs
                .map {
                    it.map(Collection<LatLng>::toTypedArray).toTypedArray()
                }
                .toTypedArray(),
            options = obj<PolylineOptions>(configure)
        )

    fun rectangle(
        latLngBounds: LatLngBounds,
        configure: PolylineOptions.() -> Unit = {},
    ): Rectangle = Rectangle(latLngBounds = latLngBounds, options = obj<PolylineOptions>(configure))

    fun svg(
        configure: RendererOptions.() -> Unit = {},
    ): SVG = SVG(options = obj<RendererOptions>(configure))

    fun featureGroup(
        layers: Array<Layer<*>>,
        configure: LayerOptions.() -> Unit = {},
    ): FeatureGroup = FeatureGroup(layers = layers, options = obj<LayerOptions>(configure))

    fun latLng(
        latitude: Number,
        longitude: Number
    ): LatLng = LatLng(latitude, longitude)

    fun latLng(
        latitude: Number,
        longitude: Number,
        altitude: Number,
    ): LatLng = LatLng(latitude, longitude, altitude)

    fun latLng(latLng: Pair<Number, Number>, altitude: Number? = null): LatLng =
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
