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

package externals.leaflet.geometry

open external class Bounds {
    constructor()
    constructor(topLeft: Point, bottomRight: Point)

    open var min: Point?
    open var max: Point?

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
}
