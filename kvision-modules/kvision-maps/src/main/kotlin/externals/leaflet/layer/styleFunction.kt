package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeoJsonGeometry
import externals.leaflet.layer.vector.PathOptions

typealias StyleFunction = (feature: Feature<GeoJsonGeometry, Any>) -> PathOptions
