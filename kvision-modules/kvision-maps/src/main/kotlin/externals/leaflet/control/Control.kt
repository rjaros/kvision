package externals.leaflet.control

import externals.leaflet.PositionsUnion
import externals.leaflet.control.Control.ControlOptions
import externals.leaflet.control.Control.LayersObject
import externals.leaflet.core.Class
import externals.leaflet.layer.Layer
import externals.leaflet.map.LeafletMap
import org.w3c.dom.HTMLElement

/**
 * `Control` is a base class for implementing map controls. Handles positioning. All other controls
 * extend from this class.
 *
 * See [`https://leafletjs.com/reference.html#control`](https://leafletjs.com/reference.html#control)
 */
@JsModule("leaflet")
@JsNonModule
abstract external class Control<T : ControlOptions>(
    options: T = definedExternally
) : Class {

    open var options: T // ControlOptions

    open fun getPosition(): PositionsUnion
    open fun setPosition(position: PositionsUnion): Control<T> /* this */
    open fun getContainer(): HTMLElement?
    open fun addTo(map: LeafletMap): Control<T> /* this */
    open fun remove(): Control<T> /* this */
    open val onAdd: ((map: LeafletMap) -> HTMLElement)?
    open val onRemove: ((map: LeafletMap) -> Unit)?

    interface ControlOptions {
        /**
         * The position of the control (one of the map corners).
         *
         * Possible values are `topleft`, `topright`, `bottomleft` or `bottomright`
         */
        var position: PositionsUnion
    }

    /**
     * Object literal with layer names as keys and [Layer] objects as values.
     *
     * @see [LayersObject.set]
     * @see [LayersObject.get]
     */
    interface LayersObject

//    companion object {
//        fun <T : Any?> extend(props: T): Any /* Any & Any */
//    }
}

/** Native getter for [LayersObject] */
inline operator fun LayersObject.get(name: String): Layer<*>? =
    asDynamic()[name] as Layer<*>?

/** Native setter for [LayersObject] */
inline operator fun LayersObject.set(name: String, value: Layer<*>) {
    asDynamic()[name] = value
}
