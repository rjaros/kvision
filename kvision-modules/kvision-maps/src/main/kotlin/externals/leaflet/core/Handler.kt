@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.core

import externals.leaflet.map.LeafletMap

open external class Handler(map: LeafletMap) : Class {
    open fun enable(): Handler /* this */
    open fun disable(): Handler /* this */
    open fun enabled(): Boolean
    open val addHooks: (() -> Unit)?
    open val removeHooks: (() -> Unit)?
}
