package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeoJsonGeometry
import externals.leaflet.layer.vector.Path

typealias StyleFunction = (feature: Feature<GeoJsonGeometry>) -> Path.PathOptions
