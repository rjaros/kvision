/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:JsModule("geojson")

package io.kvision.maps.externals.geojson

/** [`https://github.com/DefinitelyTyped/DefinitelyTyped/blob/fbb677e41830566b714ab656ecfe656060391af2/types/geojson/index.d.ts#L79`](https://github.com/DefinitelyTyped/DefinitelyTyped/blob/fbb677e41830566b714ab656ecfe656060391af2/types/geojson/index.d.ts#L79) */
external interface GeoJsonGeometry

/** [`https://github.com/DefinitelyTyped/DefinitelyTyped/blob/41493aa535f573c0747a1e59c89fc941c6334e0d/types/geojson/index.d.ts#L145`](https://github.com/DefinitelyTyped/DefinitelyTyped/blob/41493aa535f573c0747a1e59c89fc941c6334e0d/types/geojson/index.d.ts#L145) */
external interface GeoJsonProperties

/**
 * Bounding box
 *
 * [`https://tools.ietf.org/html/rfc7946#section-5`](https://tools.ietf.org/html/rfc7946#section-5)
 */
// export type = [number, number, number, number] | [number, number, number, number, number, number];
// JsTuple<Number, Number, Number, Number>        | JsTuple<Number, Number, Number, Number, Number, Number>
external interface BBoxType

/** One of [Point], [MultiPoint], [LineString], [MultiLineString], [Polygon], [MultiPolygon], [GeometryCollection] */
external interface GeoJsonObject {

    /** Specifies the type of GeoJSON object. */
    val type: String

    /**
     * Bounding box of the coordinate range of the object's Geometries, Features, or Feature Collections.
     * The value of the bbox member is an array of length 2*n where n is the number of dimensions
     * represented in the contained geometries, with all axes of the most southwesterly point
     * followed by all axes of the more northeasterly point.
     *
     * The axes order of a bbox follows the axes order of geometries.
     *
     * [`https://tools.ietf.org/html/rfc7946#section-5`](https://tools.ietf.org/html/rfc7946#section-5)
     */
    var bbox: BBoxType
}

// type = Point
external interface Point : GeoJsonObject, GeoJsonGeometry {
    var coordinates: Position
}

/* ********************************************************************************************** */
//<editor-fold desc="GeoJson objects with multiple coordinates">

external interface GeoJsonCoordinatedGeometry : GeoJsonObject, GeoJsonGeometry {
    var coordinates: dynamic
}

/** One of [LineString] or [MultiLineString] */
external interface GeoJsonSingleOrMultiLineString : GeoJsonCoordinatedGeometry
/** One of [Polygon] or [MultiPolygon] */
external interface GeoJsonSingleOrMultiPolygon : GeoJsonCoordinatedGeometry

external interface GeoJson1DGeometry : GeoJsonCoordinatedGeometry {
    override var coordinates: Array<Position>
}

external interface GeoJson2DGeometry : GeoJsonCoordinatedGeometry {
    override var coordinates: Array<Array<Position>>
}

external interface GeoJson3DGeometry : GeoJsonCoordinatedGeometry {
    override var coordinates: Array<Array<Array<Position>>>
}

// type = MultiPoint
external interface MultiPoint : GeoJson1DGeometry, GeoJsonObject

// type = LineString
external interface LineString : GeoJson1DGeometry, GeoJsonObject, GeoJsonSingleOrMultiLineString

// type = MultiLineString
external interface MultiLineString : GeoJson2DGeometry, GeoJsonObject,
    GeoJsonSingleOrMultiLineString

// type = Polygon
external interface Polygon : GeoJson2DGeometry, GeoJsonObject, GeoJsonSingleOrMultiPolygon

// type = MultiPolygon
external interface MultiPolygon : GeoJson3DGeometry, GeoJsonObject, GeoJsonSingleOrMultiPolygon

//</editor-fold>
/* ********************************************************************************************** */


// type = GeometryCollection
external interface GeometryCollection : GeoJsonObject {
    var geometries: Array<GeoJsonObject>
}

// type = Feature
external interface Feature<G : GeoJsonGeometry?> : GeoJsonObject {

    /** The feature's geometry. */
    var geometry: G

    /**
     * A value that uniquely identifies this feature.
     *
     * [`https://tools.ietf.org/html/rfc7946#section-3.2.`](https://tools.ietf.org/html/rfc7946#section-3.2.)
     */
    var id: dynamic /* String? | Number? */

    /** Properties associated with this feature. */
    var properties: GeoJsonProperties?
}

// type = FeatureCollection
external interface FeatureCollection<G : GeoJsonGeometry?> : GeoJsonObject {
    var features: Array<Feature<G>>
}
