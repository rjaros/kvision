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

import externals.geojson.LineString
import externals.geojson.MultiLineString
import externals.geojson.MultiPolygon
import externals.leaflet.control.Attribution
import externals.leaflet.control.Attribution.AttributionOptions
import externals.leaflet.control.Control.LayersObject
import externals.leaflet.control.Layers
import externals.leaflet.control.Layers.LayersOptions
import externals.leaflet.control.Scale
import externals.leaflet.control.Scale.ScaleOptions
import externals.leaflet.control.Zoom
import externals.leaflet.control.Zoom.ZoomOptions
import externals.leaflet.control.set
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.FeatureGroup
import externals.leaflet.layer.Layer
import externals.leaflet.layer.Layer.LayerOptions
import externals.leaflet.layer.marker.DivIcon
import externals.leaflet.layer.marker.DivIcon.DivIconOptions
import externals.leaflet.layer.marker.Icon
import externals.leaflet.layer.marker.Icon.IconOptions
import externals.leaflet.layer.marker.Marker
import externals.leaflet.layer.marker.Marker.MarkerOptions
import externals.leaflet.layer.overlay.DivOverlay
import externals.leaflet.layer.overlay.DivOverlay.DivOverlayOptions
import externals.leaflet.layer.overlay.ImageOverlay
import externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import externals.leaflet.layer.overlay.Popup
import externals.leaflet.layer.overlay.Popup.PopupOptions
import externals.leaflet.layer.overlay.Tooltip
import externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import externals.leaflet.layer.tile.GridLayer
import externals.leaflet.layer.tile.GridLayer.GridLayerOptions
import externals.leaflet.layer.tile.TileLayer
import externals.leaflet.layer.tile.TileLayer.TileLayerOptions
import externals.leaflet.layer.tile.WMS
import externals.leaflet.layer.tile.WMS.WMSOptions
import externals.leaflet.layer.vector.Canvas
import externals.leaflet.layer.vector.Circle
import externals.leaflet.layer.vector.CircleMarker
import externals.leaflet.layer.vector.CircleMarker.CircleMarkerOptions
import externals.leaflet.layer.vector.Polygon
import externals.leaflet.layer.vector.Polyline
import externals.leaflet.layer.vector.Polyline.PolylineOptions
import externals.leaflet.layer.vector.Rectangle
import externals.leaflet.layer.vector.Renderer.RendererOptions
import externals.leaflet.layer.vector.SVG
import externals.leaflet.map.LeafletMap
import io.kvision.MapsModule
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode
import io.kvision.utils.obj
import org.w3c.dom.HTMLElement

/**
 * Maps component.
 */
open class Maps(
    className: String? = null,
    init: (Maps.() -> Unit) = {}
) : Widget(className) {

    private lateinit var leafletMap: LeafletMap

    /** Configuration for [leafletMap], in case it has not yet been initialised. */
    private var initLeafletMap: LeafletMap.() -> Any = { }

    /**
     * Apply some configuration to [Leaflet Map][LeafletMap].
     *
     * If [leafletMap] has been initialised and inserted into the DOM (as in, [afterInsert] has
     * been invoked), then the configuration will be applied immediately.
     *
     * If not, then this configuration will be stored and applied in [afterInsert]
     * (note: in these instances then invoking this method will overwrite previously stored
     * configurations).
     */
    fun initLeafletMap(configure: LeafletMap.() -> Unit) {
        if (this::leafletMap.isInitialized) {
            leafletMap.configure()
        } else {
            initLeafletMap = configure
        }
    }

    /**
     * Perform some action with the initialised [LeafletMap] instance
     *
     * @throws IllegalArgumentException if [leafletMap] is not yet initialized - it must first be
     * added as a component.
     */
    operator fun <T : Any?> invoke(block: LeafletMap.() -> T): T {
        require(this::leafletMap.isInitialized) {
            "LeafletMap is not initialised - the Maps widget must be added to the DOM"
        }
        return leafletMap.block()
    }

    init {
        useSnabbdomDistinctKey()
        this.init()
    }

    /** Create a native map instance. */
    override fun afterInsert(node: VNode) {
        val thisElement: HTMLElement = requireNotNull(this.getElement() as? HTMLElement) {
            "$this - Unable to get HTMLElement"
        }

        leafletMap = LeafletMap(thisElement)
        leafletMap.initLeafletMap()
    }

    override fun afterDestroy() {
        if (this::leafletMap.isInitialized) {
            leafletMap.remove()
        }
    }

    companion object {
        init {
            MapsModule.initialize()
        }

        fun createPolyline(
            latLngs: Collection<LatLng>,
            configure: PolylineOptions.() -> Unit = {},
        ): Polyline<LineString> = Polyline(
            latLngs.toTypedArray(),
            options = obj<PolylineOptions>(configure),
        )

        fun createMultiPolyline(
            latLngs: Collection<Collection<LatLng>>,
            configure: PolylineOptions.() -> Unit = {},
        ): Polyline<MultiLineString> = Polyline(
            latLngs.map { it.toTypedArray() }.toTypedArray(),
            options = obj<PolylineOptions>(configure),
        )

        fun createTileLayer(
            urlTemplate: String,
            configure: TileLayerOptions.() -> Unit = {}
        ): TileLayer = TileLayer(
            urlTemplate = urlTemplate,
            options = obj<TileLayerOptions>(configure),
        )

        fun createLayersObject(tileLayers: Collection<BaseTileLayer>): LayersObject {
            return tileLayers
                .associate { base -> base.label to MapsModule.convertTileLayer(base) }
                .entries
                .fold(object : LayersObject {}) { acc, (label, layer) ->
                    acc[label] = layer
                    acc
                }
        }

        fun createLayers(
            baseLayers: Collection<BaseTileLayer> = emptyList(),
            overlays: LayersObject = createLayersObject(emptyList()),
            configure: LayersOptions.() -> Unit = {},
        ): Layers = Layers(
            baseLayers = createLayersObject(baseLayers),
            overlays = overlays,
            options = obj<LayersOptions>(configure),
        )

        fun createImageOverlay(
            imageUrl: String,
            bounds: LatLngBounds,
            configure: ImageOverlayOptions.() -> Unit = {},
        ): ImageOverlay = ImageOverlay(
            imageUrl = imageUrl,
            bounds = bounds,
            options = obj<ImageOverlayOptions>(configure),
        )

        fun createAttribution(
            configure: AttributionOptions.() -> Unit = {},
        ) = Attribution(options = obj<AttributionOptions>(configure))

        fun createScale(
            configure: ScaleOptions.() -> Unit = {},
        ) = Scale(options = obj<ScaleOptions>(configure))

        fun createZoom(
            configure: ZoomOptions.() -> Unit = {},
        ) = Zoom(options = obj<ZoomOptions>(configure))

        fun createDivIcon(
            configure: DivIconOptions.() -> Unit = {},
        ) = DivIcon(options = obj<DivIconOptions>(configure))

        fun createIcon(
            configure: IconOptions.() -> Unit = {},
        ) = Icon(options = obj<IconOptions>(configure))

        fun createMarker(
            latlng: LatLng,
            configure: MarkerOptions.() -> Unit = {},
        ) = Marker(latlng, options = obj<MarkerOptions>(configure))

        fun createDivOverlay(
            source: Layer,
            configure: DivOverlayOptions.() -> Unit = {},
        ) = DivOverlay(source = source, options = obj<DivOverlayOptions>(configure))

        fun createPopup(
            source: Layer,
            configure: PopupOptions.() -> Unit = {},
        ) = Popup(source = source, options = obj<PopupOptions>(configure))

        fun createTooltip(
            source: Layer,
            configure: TooltipOptions.() -> Unit = {},
        ) = Tooltip(source = source, options = obj<TooltipOptions>(configure))

        fun createVideoOverlay(
            source: Layer,
            configure: TooltipOptions.() -> Unit = {},
        ) = Tooltip(source = source, options = obj<TooltipOptions>(configure))

        fun createGridLayer(
            configure: GridLayerOptions.() -> Unit = {},
        ) = GridLayer(options = obj<GridLayerOptions>(configure))

        fun createWMS(
            baseUrl: String,
            configure: WMSOptions.() -> Unit = {},
        ) = WMS(baseUrl = baseUrl, options = obj<WMSOptions>(configure))

        fun createCanvas(
            configure: RendererOptions.() -> Unit = {},
        ) = Canvas(options = obj<RendererOptions>(configure))

        fun createCircleMarker(
            latlng: LatLng,
            configure: CircleMarkerOptions.() -> Unit = {},
        ) = CircleMarker(latlng = latlng, options = obj<CircleMarkerOptions>(configure))

        fun createCircle(
            latlng: LatLng,
            configure: CircleMarkerOptions.() -> Unit = {},
        ) = Circle(latlng = latlng, options = obj<CircleMarkerOptions>(configure))

        /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
        fun createPolygon(
            latlngs: Collection<LatLng>,
            configure: PolylineOptions.() -> Unit = {},
        ): Polygon<externals.geojson.Polygon> =
            Polygon(latlngs = latlngs.toTypedArray(), options = obj<PolylineOptions>(configure))

        /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
        fun createMultiPolygon(
            latlngs: Collection<Collection<Collection<LatLng>>>,
            configure: PolylineOptions.() -> Unit = {},
        ): Polygon<MultiPolygon> =
            Polygon(
                latlngs = latlngs
                    .map {
                        it.map(Collection<LatLng>::toTypedArray).toTypedArray()
                    }
                    .toTypedArray(),
                options = obj<PolylineOptions>(configure)
            )

        fun createRectangle(
            latLngBounds: LatLngBounds,
            configure: PolylineOptions.() -> Unit = {},
        ) = Rectangle(latLngBounds = latLngBounds, options = obj<PolylineOptions>(configure))

        fun createSVG(
            latLngBounds: LatLngBounds,
            configure: RendererOptions.() -> Unit = {},
        ) = SVG(options = obj<RendererOptions>(configure))

        fun createFeatureGroup(
            layers: Array<Layer>,
            configure: LayerOptions.() -> Unit = {},
        ) = FeatureGroup(layers = layers, options = obj<RendererOptions>(configure))

    }


}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the [Maps] component.
 */
fun Container.maps(
    className: String? = null,
    init: (Maps.() -> Unit) = { }
): Maps {
    val maps = Maps(className, init)
    this.add(maps)
    return maps
}
