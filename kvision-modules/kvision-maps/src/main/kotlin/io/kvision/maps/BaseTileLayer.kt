package io.kvision.maps

import externals.leaflet.layer.tile.TileLayer
import io.kvision.maps.LeafletObjectFactory.tileLayer

/**
 * Some default [TileLayer]s from publicly available tile providers.
 *
 * See
 *
 * * [`https://github.com/leaflet-extras/leaflet-providers/`](https://github.com/leaflet-extras/leaflet-providers/)
 * * [`https://leaflet-extras.github.io/leaflet-providers/preview/`](https://leaflet-extras.github.io/leaflet-providers/preview/)
 *
 */
object DefaultTileLayers {

    val Empty =
        tileLayer("") {
            attribution = ""
            subdomains = null
            maxZoom = null
        }

    /** OpenStreetMap standard tile layer https://wiki.openstreetmap.org/wiki/Standard_tile_layer */
    val OpenStreetMap =
        tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png") {
            attribution =
                """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors"""
        }

    val EsriWorldImagery =
        tileLayer("https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x})") {
            attribution =
                """Tiles &copy; Esri &mdash; Source: Esri, i-cubed, USDA, USGS, AEX, GeoEye, Getmapping, Aerogrid, IGN, IGP, UPR-EGP, and the GIS User Community"""
        }

    val EsriWorldTopoMap =
        tileLayer("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}") {
            attribution =
                """Tiles &copy; Esri &mdash; Esri, DeLorme, NAVTEQ, TomTom, Intermap, iPC, USGS, FAO, NPS, NRCAN, GeoBase, Kadaster NL, Ordnance Survey, Esri Japan, METI, Esri China (Hong Kong), and the GIS User Community"""
        }

    /**
     * https://openmtbmap.org/ – Mountain bike and Hiking Maps based on OpenStreetMap
     *
     * Routable Maps for Garmin GPS for outdoor sports
     */
    val OpenMtbMap =
        tileLayer("https://tile.mtbmap.cz/mtbmap_tiles/{z}/{x}/{y}.png") {
            attribution =
                """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &amp; USGS"""

        }

    /** [https://carto.com/about-carto/](https://carto.com/about-carto/) */
    val CartoDBVoyager =
        tileLayer("https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png") {
            attribution =
                """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>"""
            subdomains = "abcd"
            maxZoom = 19
        }

    /** [Hike & Bike Map v2](https://wiki.openstreetmap.org/wiki/Hike_%26_Bike_Map) */
    val HikeAndBikeMap = tileLayer("https://{s}.tiles.wmflabs.org/hikebike/{z}/{x}/{y}.png") {
        attribution =
            """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors"""
        subdomains = "abc"
        asDynamic().foo = "bar"
    }

    val baseLayers = LeafletObjectFactory.layersObject(
        mapOf(
            "Empty" to Empty,
            "Esri.WorldImagery" to EsriWorldImagery,
            "Esri.WorldTopoMap" to EsriWorldTopoMap,
            "OpenStreetMap" to OpenStreetMap,
            "MtbMap" to OpenMtbMap,
            "CartoDB.Voyager" to CartoDBVoyager,
            "Hike & Bike Map" to HikeAndBikeMap,
        )
    )
}
