@file:JsModule("leaflet")
@file:JsNonModule


package externals.leaflet.layer.tile

import externals.leaflet.DoneCallback
import externals.leaflet.geo.*
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement


open external class GridLayer(options: GridLayerOptions = definedExternally) : Layer {
    open fun bringToFront(): GridLayer /* this */
    open fun bringToBack(): GridLayer /* this */
    open fun getContainer(): HTMLElement?
    open fun setOpacity(opacity: Number): GridLayer /* this */
    open fun setZIndex(zIndex: Number): GridLayer /* this */
    open fun isLoading(): Boolean
    open fun redraw(): GridLayer /* this */
    open fun getTileSize(): Point
    open fun createTile(coords: Coords, done: DoneCallback): HTMLElement
    open fun _tileCoordsToKey(coords: Coords): String
    open fun _wrapCoords(parameter: Coords): Coords
    open var _tiles: InternalTiles
    open var _tileZoom: Number?
}