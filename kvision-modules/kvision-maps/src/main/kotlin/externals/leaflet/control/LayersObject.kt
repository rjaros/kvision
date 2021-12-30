@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control

import externals.leaflet.layer.Layer

/**
 * Object literal with layer names as keys and [Layer] objects as values.
 *
 * @see [set]
 * @see [get]
 */
external interface LayersObject
