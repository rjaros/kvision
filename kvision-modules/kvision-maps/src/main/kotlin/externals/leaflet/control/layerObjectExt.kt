package externals.leaflet.control

import externals.leaflet.layer.Layer

inline operator fun LayersObject.get(name: String): Layer? = asDynamic()[name]
inline operator fun LayersObject.set(name: String, value: Layer) {
    asDynamic()[name] = value
}
