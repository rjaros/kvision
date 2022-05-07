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

@file:JsModule("leaflet")
@file:JsNonModule

package io.kvision.maps.externals.leaflet.geometry

/**
 * Represents a point with `x` and `y` coordinates in pixels.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#point
 */
open external class Point(
    x: Number,
    y: Number,
    round: Boolean? = definedExternally
) {
    var x: Number
    var y: Number

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
    fun trunc(): Point
    fun distanceTo(otherPoint: Point): Number
    fun contains(otherPoint: Point): Boolean
    @Suppress("CovariantEquals") // 'equals' is external, we can't change it, so the warning isn't useful
    fun equals(otherPoint: Point): Boolean
}
