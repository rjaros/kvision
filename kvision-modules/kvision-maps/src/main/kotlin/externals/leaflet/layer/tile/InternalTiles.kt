@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.tile

import externals.leaflet.geo.Coords
import kotlin.js.Date
import org.w3c.dom.HTMLElement

external interface InternalTiles {
    var active: Boolean?
    var coords: Coords
    var current: Boolean?
    var el: HTMLElement
    var loaded: Date?
    var retain: Boolean?
}

//external interface InternalTiles {
//    @nativeGetter
//    operator fun get(key: String): `T$3`?
//    @nativeSetter
//    operator fun set(key: String, value: `T$3`)
//}
