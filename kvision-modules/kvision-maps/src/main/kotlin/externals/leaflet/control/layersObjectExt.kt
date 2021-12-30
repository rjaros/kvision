package externals.leaflet.control

import externals.leaflet.layer.Layer

/** Native getter for [LayersObject] */
inline operator fun LayersObject.get(name: String): Layer? =
    asDynamic()[name] as Layer?

/** Native setter for [LayersObject] */
inline operator fun LayersObject.set(name: String, value: Layer) {
    asDynamic()[name] = value
}
