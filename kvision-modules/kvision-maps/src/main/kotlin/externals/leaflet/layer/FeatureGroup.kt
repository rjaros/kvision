@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.vector.PathOptions

open external class FeatureGroup(
    layers: Array<Layer> = definedExternally,
    options: LayerOptions = definedExternally
) : LayerGroup {
    open fun setStyle(style: PathOptions): FeatureGroup /* this */
    open fun bringToFront(): FeatureGroup /* this */
    open fun bringToBack(): FeatureGroup /* this */
    open fun getBounds(): LatLngBounds
}
