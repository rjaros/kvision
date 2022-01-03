package externals.leaflet.control

import externals.leaflet.PositionsUnion
import externals.leaflet.control.Control.LayersObject
import externals.leaflet.core.Class
import externals.leaflet.layer.Layer
import externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

@JsModule("leaflet")
@JsNonModule
abstract external class Control(
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

    interface ControlOptions {
        var position: PositionsUnion
    }

    /**
     * Object literal with layer names as keys and [Layer] objects as values.
     *
     * @see [set]
     * @see [get]
     */
    interface LayersObject

//    companion object {
//        fun <T : Any?> extend(props: T): Any /* Any & Any */
//    }
}

/** Native getter for [LayersObject] */
inline operator fun LayersObject.get(name: String): Layer? =
    asDynamic()[name] as Layer?

/** Native setter for [LayersObject] */
inline operator fun LayersObject.set(name: String, value: Layer) {
    asDynamic()[name] = value
}
