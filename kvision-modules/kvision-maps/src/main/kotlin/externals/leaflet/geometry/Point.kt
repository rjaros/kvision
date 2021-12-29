@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.geometry

open external class Point(
    x: Number,
    y: Number,
    round: Boolean? = definedExternally
) {
    fun clone(): Point
    /** non-destructive, returns a new point */
    fun add(otherPoint: Point): Point
    fun subtract(otherPoint: Point): Point
    fun divideBy(num: Number): Point
    fun multiplyBy(num: Number): Point
    fun scaleBy(scale: Point): Point
    fun unscaleBy(scale: Point): Point
    fun round(): Point
    fun floor(): Point
    fun ceil(): Point
    fun distanceTo(otherPoint: Point): Number
    fun contains(otherPoint: Point): Boolean
    var x: Number
    var y: Number
}
