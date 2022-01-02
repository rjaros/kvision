package externals.leaflet.layer.tile

import externals.leaflet.geo.Coords
import externals.leaflet.layer.Layer
import kotlin.js.Date
import org.w3c.dom.HTMLElement

@JsModule("leaflet")
@JsNonModule
external interface InternalTiles {
    var active: Boolean?
    var coords: Coords
    var current: Boolean?
    var el: HTMLElement
    var loaded: Date?
    var retain: Boolean?
}

/** Native getter for [InternalTiles] */
inline operator fun InternalTiles.get(name: String): Layer? =
    asDynamic()[name] as Layer?

/** Native setter for [InternalTiles] */
inline operator fun InternalTiles.set(name: String, value: Layer) {
    asDynamic()[name] = value
}
