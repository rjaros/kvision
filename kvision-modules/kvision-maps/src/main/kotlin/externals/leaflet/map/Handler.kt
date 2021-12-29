@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.map

import externals.leaflet.core.Class

open external class Handler(map: LeafletMap) : Class {
    open fun enable(): Handler /* this */
    open fun disable(): Handler /* this */
    open fun enabled(): Boolean
    open val addHooks: (() -> Unit)?
    open val removeHooks: (() -> Unit)?
}
