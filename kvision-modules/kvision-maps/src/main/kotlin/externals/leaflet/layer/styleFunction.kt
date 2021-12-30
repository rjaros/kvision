package externals.leaflet.layer

import externals.geojson.Feature
import externals.leaflet.layer.vector.PathOptions

typealias StyleFunction<P> = (feature: Feature<dynamic /* typealias GeometryObject = dynamic */, P>) -> PathOptions
