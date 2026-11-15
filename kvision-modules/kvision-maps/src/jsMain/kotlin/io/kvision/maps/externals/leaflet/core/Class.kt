@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

package io.kvision.maps.externals.leaflet.core

/**
 * [`L.Class`](https://leafletjs.com/SlavaUkraini/reference.html#class)
 * powers the OOP facilities of Leaflet and is used to create almost all the Leaflet classes
 * documented.
 *
 * In addition to implementing a simple classical inheritance model, it introduces several special
 * properties for convenient code organization — options, includes and statics.
 */
open external class Class {
    /** Runs all constructor hooks of the class chain once. */
    fun callInitHooks()

    companion object {
        /** Includes a mixin into the current class. */
        fun include(props: dynamic): dynamic

        /** Merges options into the defaults of the class. */
        fun mergeOptions(props: dynamic): dynamic

        /** Deprecated alias of [mergeOptions]. */
        fun setDefaultOptions(props: dynamic): dynamic

        /** Adds a constructor hook to the class. */
        fun addInitHook(initHookFn: () -> Unit): dynamic
        fun addInitHook(methodName: String, vararg args: dynamic): dynamic
    }
}
