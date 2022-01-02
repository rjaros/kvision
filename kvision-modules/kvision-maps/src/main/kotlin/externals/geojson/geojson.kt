@file:JsModule("geojson")
@file:JsNonModule

package externals.geojson

/** [`https://github.com/DefinitelyTyped/DefinitelyTyped/blob/fbb677e41830566b714ab656ecfe656060391af2/types/geojson/index.d.ts#L79`](https://github.com/DefinitelyTyped/DefinitelyTyped/blob/fbb677e41830566b714ab656ecfe656060391af2/types/geojson/index.d.ts#L79) */
external interface GeoJsonGeometry

external interface GeoJsonObject {
    val type: String?
        get() = definedExternally
    var bbox: dynamic /* JsTuple<Number, Number, Number, Number> | JsTuple<Number, Number, Number, Number, Number, Number> */
}

external interface Point : GeoJsonObject, GeoJsonGeometry {
    //    override var type: String /* "Point" */
    var coordinates: Position
}

external interface MultiPoint : GeoJsonObject, GeoJsonGeometry {
    //    override var type: String /* "MultiPoint" */
    var coordinates: Array<Position>
}

external interface LineStringMultiLineStringUnion

external interface LineString : GeoJsonObject, GeoJsonGeometry, LineStringMultiLineStringUnion {
    //    override var type: String /* "LineString" */
    var coordinates: Array<Position>
}

external interface MultiLineString : GeoJsonObject, GeoJsonGeometry, LineStringMultiLineStringUnion {
    //    override var type: String /* "MultiLineString" */
    var coordinates: Array<Array<Position>>
}

external interface Polygon : GeoJsonObject, GeoJsonGeometry {
    //    override var type: String /* "Polygon" */
    var coordinates: Array<Array<Position>>
}

external interface MultiPolygon : GeoJsonObject, GeoJsonGeometry {
    //    override var type: String /* "MultiPolygon" */
    var coordinates: Array<Array<Array<Position>>>
}

external interface GeometryCollection : GeoJsonObject, GeoJsonGeometry {
    //    override var type: String /* "GeometryCollection" */
    var geometries: Array<GeoJsonObject /* Point | MultiPoint | LineString | MultiLineString | Polygon | MultiPolygon | GeometryCollection */>
}

external interface Feature<G, P> : GeoJsonObject {
    //    override var type: String /* "Feature" */
    var geometry: G
    var id: dynamic /* String? | Number? */
    var properties: P
}

//external interface Feature__0 : Feature<dynamic /* Point | MultiPoint | LineString | MultiLineString | Polygon | MultiPolygon | GeometryCollection */, Json?>

external interface FeatureCollection<G, P> : GeoJsonObject {
    //    override var type: String /* "FeatureCollection" */
    var features: Array<Feature<G, P>>
}

//external interface FeatureCollection__0 : FeatureCollection<dynamic /* Point | MultiPoint | LineString | MultiLineString | Polygon | MultiPolygon | GeometryCollection */, Json?>
