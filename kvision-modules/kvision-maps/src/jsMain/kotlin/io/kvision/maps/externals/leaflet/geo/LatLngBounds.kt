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

@file:JsModule("leaflet/dist/leaflet-src.esm.js")

package io.kvision.maps.externals.leaflet.geo


/**
 * Represents a rectangular geographical area on a map.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#latlngbounds
 */
open external class LatLngBounds(southWest: LatLng, northEast: LatLng) {

    open fun extend(latLng: LatLng): LatLngBounds /* this */
    open fun extend(latLngBounds: LatLngBounds): LatLngBounds /* this */

    open fun pad(bufferRatio: Number): LatLngBounds

    open fun getCenter(): LatLng
    open fun getSouthWest(): LatLng
    open fun getNorthEast(): LatLng
    open fun getNorthWest(): LatLng
    open fun getSouthEast(): LatLng

    open fun getWest(): Number
    open fun getSouth(): Number
    open fun getEast(): Number
    open fun getNorth(): Number

    open fun contains(otherBounds: LatLngBounds): Boolean
    open fun contains(latlng: LatLng): Boolean
    open fun intersects(otherBounds: LatLngBounds): Boolean
    open fun overlaps(otherBounds: LatLngBounds): Boolean

    open fun toBBoxString(): String

    @Suppress("CovariantEquals") // 'equals' is external, we can't change it, so the warning isn't useful
    open fun equals(otherBounds: LatLngBounds): Boolean
    open fun isValid(): Boolean
}
