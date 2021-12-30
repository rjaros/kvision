@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

import externals.leaflet.PositionsUnion
import externals.leaflet.core.Class
import externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

open external class Control(
    options: ControlOptions = definedExternally
) : Class {
    open fun getPosition(): PositionsUnion
    open fun setPosition(position: PositionsUnion): Control /* this */
    open fun getContainer(): HTMLElement?
    open fun addTo(map: LeafletMap): Control /* this */
    open fun remove(): Control /* this */
    open val onAdd: ((map: LeafletMap) -> HTMLElement)?
    open val onRemove: ((map: LeafletMap) -> Unit)?
    open var options: dynamic // ControlOptions

//    companion object {
//        fun <T : Any?> extend(props: T): Any /* Any & Any */
//    }
}
