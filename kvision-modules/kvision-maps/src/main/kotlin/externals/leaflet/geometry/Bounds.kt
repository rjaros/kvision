@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geometry

open external class Bounds {
    constructor()
    constructor(topLeft: Point, bottomRight: Point)

    open fun extend(point: Point): Bounds /* this */
    open fun getCenter(round: Boolean = definedExternally): Point
    open fun getBottomLeft(): Point
    open fun getBottomRight(): Point
    open fun getTopLeft(): Point
    open fun getTopRight(): Point
    open fun getSize(): Point
    open fun contains(pointOrBounds: Bounds): Boolean
    open fun contains(pointOrBounds: Point): Boolean
    open fun intersects(otherBounds: Bounds): Boolean
    open fun overlaps(otherBounds: Bounds): Boolean
    open fun isValid(): Boolean
    open var min: Point?
    open var max: Point?
}
