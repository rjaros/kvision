 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.layer

/**
 * Used to group several layers and handle them as one.
 * If you add it to the map, any layers added or removed from the group will be
 * added/removed on the map as well. Extends [Layer].
 */
open external class LayerGroup<P : Any>(
    layers: Array<Layer> = definedExternally,
    options: LayerOptions = definedExternally
) : Layer {
    /**
     * Returns a GeoJSON representation of the layer group (as a GeoJSON GeometryCollection, GeoJSONFeatureCollection or Multipoint).
     */
    open fun toGeoJSON(precision: Number = definedExternally): dynamic
    /* geojson.FeatureCollection<dynamic /* typealias GeometryObject = dynamic */, P> | geojson.Feature<geojson.MultiPoint, P> | geojson.GeometryCollection */

    /** Adds the given layer to the group. */
    open fun addLayer(layer: Layer): LayerGroup<P> /* this */

    /** Removes the layer with the given internal ID from the group. */
    open fun removeLayer(layer: Number): LayerGroup<P> /* this */

    /** Removes the given layer from the group. */
    open fun removeLayer(layer: Layer): LayerGroup<P> /* this */

    /** Returns true if the given layer is currently added to the group. */
    open fun hasLayer(layer: Layer): Boolean

    /** Removes all the layers from the group. */
    open fun clearLayers(): LayerGroup<P> /* this */

    /**
     * Calls [methodName] on every layer contained in this group, passing any additional parameters.
     * Has no effect if the layers contained do not implement methodName.
     */
    open fun invoke(methodName: String, vararg params: Any): LayerGroup<P> /* this */

    /**
     * Iterates over the layers of the group, optionally specifying context of the iterator function.
     */
    open fun eachLayer(
        fn: (layer: Layer) -> Unit,
        context: Any = definedExternally
    ): LayerGroup<P> /* this */

    open fun getLayer(id: Number): Layer?
    open fun getLayers(): Array<Layer>
    open fun setZIndex(zIndex: Number): LayerGroup<P> /* this */
    open fun getLayerId(layer: Layer): Number
    /**
     * Type is one of:
     * * `geojson.FeatureCollection<geojson.GeometryObject, P>`
     * * `geojson.Feature<geojson.MultiPoint, P>`
     * * `geojson.GeometryCollection`
     * * `undefined`
     */
    open var feature: dynamic
}
