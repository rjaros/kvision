@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.leaflet.geo.LatLngBounds
import externals.leaflet.layer.vector.PathOptions

open external class FeatureGroup<P: Any>(
    layers: Array<Layer> = definedExternally,
    options: LayerOptions = definedExternally
) : LayerGroup<P> {
    open fun setStyle(style: PathOptions): FeatureGroup<P> /* this */
    open fun bringToFront(): FeatureGroup<P> /* this */
    open fun bringToBack(): FeatureGroup<P> /* this */
    open fun getBounds(): LatLngBounds
}
