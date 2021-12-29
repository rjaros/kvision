 @file:JsModule("leaflet")
@file:JsNonModule
package externals.leaflet.control

import externals.leaflet.core.Class
import externals.leaflet.PositionsUnion
import externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

open external class Control<OptionsType : ControlOptions>(
    options: ControlOptions = definedExternally
) : Class {
    open fun getPosition(): PositionsUnion
    open fun setPosition(position: PositionsUnion): Control<OptionsType> /* this */
    open fun getContainer(): HTMLElement?
    open fun addTo(map: LeafletMap): Control<OptionsType> /* this */
    open fun remove(): Control<OptionsType> /* this */
    open val onAdd: ((map: LeafletMap) -> HTMLElement)?
    open val onRemove: ((map: LeafletMap) -> Unit)?
    open var options: OptionsType

//    companion object {
//        fun <T : Any?> extend(props: T): Any /* Any & Any */
//    }
}
