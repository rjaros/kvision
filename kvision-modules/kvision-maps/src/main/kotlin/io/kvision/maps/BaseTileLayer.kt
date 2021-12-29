package io.kvision.maps

/**
 * > An extension to Leaflet that contains configurations for various free tile providers.
 *
 * See
 *
 * * https://github.com/leaflet-extras/leaflet-providers/
 * * https://leaflet-extras.github.io/leaflet-providers/preview/
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

        val EMPTY = BaseTileLayer(
            label = "Empty",
            url = "",
            attribution = "",
            subdomains = null,
            maxZoom = null,
        )
        val OSM = BaseTileLayer(
            label = "OpenStreetMap",
            url = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors"""
        )
        val EWI = BaseTileLayer(
            label = "Esri.WorldImagery",
            url = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x})",
            attribution = """Tiles &copy; Esri &mdash; Source: Esri, i-cubed, USDA, USGS, AEX, GeoEye, Getmapping, Aerogrid, IGN, IGP, UPR-EGP, and the GIS User Community"""
        )
        val EWT = BaseTileLayer(
            label = "Esri.WorldTopoMap",
            url = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}",
            attribution = """Tiles &copy; Esri &mdash; Esri, DeLorme, NAVTEQ, TomTom, Intermap, iPC, USGS, FAO, NPS, NRCAN, GeoBase, Kadaster NL, Ordnance Survey, Esri Japan, METI, Esri China (Hong Kong), and the GIS User Community"""
        )
        val MTB = BaseTileLayer(
            label = "MtbMap",
            url = "https://tile.mtbmap.cz/mtbmap_tiles/{z}/{x}/{y}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &amp; USGS"""
        )
        val CDBV = BaseTileLayer(
            label = "CartoDB.Voyager",
            url = "https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>""",
            subdomains = "abcd",
            maxZoom = 19
        )
        val HB = BaseTileLayer(
            label = "Hike Bike",
            url = "https://tiles.wmflabs.org/hikebike/{z}/{x}/{y}.png",
            attribution = """&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors"""
        )

        val defaultLayers: Set<BaseTileLayer> = setOf(
            EMPTY,
            OSM,
            EWI,
            EWT,
            MTB,
            CDBV,
            HB,
        )

    }
}
