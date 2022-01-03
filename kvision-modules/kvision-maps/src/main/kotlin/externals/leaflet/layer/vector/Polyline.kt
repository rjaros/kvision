@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.geojson.Feature
import externals.geojson.GeoJsonCoordinatedGeometry
import externals.geojson.GeoJsonSingleOrMultiLineString
import externals.leaflet.geo.LatLng
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point

/**
 * A class for drawing polyline overlays on a map. Extends [Path].
 *
 * See [`https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js`](https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js)
 *
 * @param T [externals.geojson.LineString] or [externals.geojson.MultiLineString]
 */
open external class Polyline<T : GeoJsonSingleOrMultiLineString> : Path {

    /** Create a polyline from an array of [LatLng] points */
    constructor(
        latlngs: Array<LatLng>,
        options: PolylineOptions = definedExternally
    )

    /** @param[latlngs] pass a multi-dimensional array to represent a `MultiPolyline` shape */
    constructor(
        latlngs: Array<Array<LatLng>>,
        options: PolylineOptions = definedExternally
    )

    open var feature: Feature<GeoJsonCoordinatedGeometry>?
    override var options: PolylineOptions

    open fun toGeoJSON(precision: Number = definedExternally): Feature<T>

    open fun getLatLngs(): dynamic /* Array<LatLng> | Array<Array<LatLng>> | Array<Array<Array<LatLng>>> */

    open fun setLatLngs(latlngs: Array<LatLng>): Polyline<T> /* this */
    open fun setLatLngs(latlngs: Array<Array<LatLng>>): Polyline<T> /* this */
    open fun setLatLngs(latlngs: Array<Array<Array<LatLng>>>): Polyline<T> /* this */

    open fun addLatLng(
        latlng: LatLng,
        latlngs: Array<LatLng> = definedExternally
    ): Polyline<T> /* this */

    open fun addLatLng(
        latlng: Array<LatLng>,
        latlngs: Array<LatLng> = definedExternally
    ): Polyline<T> /* this */

    open fun isEmpty(): Boolean
    open fun getCenter(): LatLng
    open fun getBounds(): LatLngBounds

    open fun closestLayerPoint(p: Point): Point

    interface PolylineOptions : PathOptions {
        /**
         * How much to simplify the polyline on each zoom level. More means better performance and
         * smoother look, and less means more accurate representation.
         */
        var smoothFactor: Number?
        /** Disable polyline clipping. */
        var noClip: Boolean?
    }

}
