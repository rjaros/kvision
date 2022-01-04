package io.kvision.maps

/**
 * > An extension to Leaflet that contains configurations for various free tile providers.
 *
 * See
 *
 * * [`https://github.com/leaflet-extras/leaflet-providers/`](https://github.com/leaflet-extras/leaflet-providers/)
 * * [`https://leaflet-extras.github.io/leaflet-providers/preview/`](https://leaflet-extras.github.io/leaflet-providers/preview/)
 *
 */
data class BaseTileLayer(
    val label: String,
    val url: String,
    val attribution: String,
    val subdomains: String? = "",
    val maxZoom: Int? = 19,
) {

    companion object {

        val Empty = BaseTileLayer(
            label = "Empty",
            url = "",
            attribution = "",
            subdomains = null,
            maxZoom = null,
        )

        /** [OpenStreetMap standard tile layer](https://wiki.openstreetmap.org/wiki/Standard_tile_layer)) */
        val OpenStreetMap = BaseTileLayer(
            label = "OpenStreetMap",
            url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors"""
        )

        val EsriWorldImagery = BaseTileLayer(
            label = "Esri.WorldImagery",
            url = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x})",
            attribution = """Tiles &copy; Esri &mdash; Source: Esri, i-cubed, USDA, USGS, AEX, GeoEye, Getmapping, Aerogrid, IGN, IGP, UPR-EGP, and the GIS User Community"""
        )

        val EsriWorldTopoMap = BaseTileLayer(
            label = "Esri.WorldTopoMap",
            url = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}",
            attribution = """Tiles &copy; Esri &mdash; Esri, DeLorme, NAVTEQ, TomTom, Intermap, iPC, USGS, FAO, NPS, NRCAN, GeoBase, Kadaster NL, Ordnance Survey, Esri Japan, METI, Esri China (Hong Kong), and the GIS User Community"""
        )

        /**
         * [Openmtbmap.org](https://openmtbmap.org/) – Mountain bike and Hiking Maps based on OpenStreetMap
         *
         * Routable Maps for Garmin GPS for outdoor sports
         */
        val OpenMtbMap = BaseTileLayer(
            label = "MtbMap",
            url = "https://tile.mtbmap.cz/mtbmap_tiles/{z}/{x}/{y}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &amp; USGS"""
        )

        /** [https://carto.com/about-carto/](https://carto.com/about-carto/) */
        val CartoDBVoyager = BaseTileLayer(
            label = "CartoDB.Voyager",
            url = "https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>""",
            subdomains = "abcd",
            maxZoom = 19
        )

        /** [Hike & Bike Map v2](https://wiki.openstreetmap.org/wiki/Hike_%26_Bike_Map) */
        val HikeAndBikeMap = BaseTileLayer(
            label = "Hike & Bike Map",
            url = "https://{s}.tiles.wmflabs.org/hikebike/{z}/{x}/{y}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors""",
            subdomains = "abc"
        )

        val defaultLayers: Set<BaseTileLayer> = setOf(
            Empty,
            OpenStreetMap,
            EsriWorldImagery,
            EsriWorldTopoMap,
            OpenMtbMap,
            CartoDBVoyager,
            HikeAndBikeMap,
        )

    }
}
