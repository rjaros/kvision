@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer

import externals.geojson.Feature
import externals.geojson.GeometryCollection
import externals.leaflet.layer.Layer.LayerOptions

/**
 * Used to group several layers and handle them as one.
 *
 * If you add it to the map, any layers added or removed from the group will be
 * added/removed on the map as well. Extends [Layer].
 */
open external class LayerGroup(
    layers: Array<Layer<*>> = definedExternally,
    options: LayerOptions = definedExternally
) : Layer<LayerOptions> {
    /**
     * Returns a GeoJSON representation of the layer group (as a GeoJSON [GeometryCollection],
     * [Feature<MultiPoint>][Feature] or [GeometryCollection]).
     */
    open fun toGeoJSON(precision: Number = definedExternally): dynamic
    /* geojson.FeatureCollection<GeoJsonGeometry, P> | geojson.Feature<geojson.MultiPoint, P> | geojson.GeometryCollection */

    /** Adds the given layer to the group. */
    open fun addLayer(layer: Layer<*>): LayerGroup /* this */

    /** Removes the layer with the given internal ID from the group. */
    open fun removeLayer(layer: Number): LayerGroup /* this */

    /** Removes the given layer from the group. */
    open fun removeLayer(layer: Layer<*>): LayerGroup /* this */

    /** Returns true if the given layer is currently added to the group. */
    open fun hasLayer(layer: Layer<*>): Boolean

    /** Removes all the layers from the group. */
    open fun clearLayers(): LayerGroup /* this */

    /**
     * Calls [methodName] on every layer contained in this group, passing any additional parameters.
     * Has no effect if the layers contained do not implement methodName.
     */
    open fun invoke(methodName: String, vararg params: Any): LayerGroup /* this */

    /**
     * Iterates over the layers of the group, optionally specifying context of the iterator function.
     */
    open fun eachLayer(
        fn: (layer: Layer<*>) -> Unit,
        context: Any = definedExternally
    ): LayerGroup /* this */

    open fun getLayer(id: Number): Layer<*>?
    open fun getLayers(): Array<Layer<*>>
    open fun setZIndex(zIndex: Number): LayerGroup /* this */
    open fun getLayerId(layer: Layer<*>): Number
    /**
     * Type is one of:
     * * `geojson.FeatureCollection<geojson.GeometryObject, P>`
     * * `geojson.Feature<geojson.MultiPoint, P>`
     * * `geojson.GeometryCollection`
     * * `undefined`
     */
    open var feature: dynamic
}
