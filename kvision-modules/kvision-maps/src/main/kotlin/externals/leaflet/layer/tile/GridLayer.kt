@file:JsModule("leaflet")
@file:JsNonModule


package externals.leaflet.layer.tile

import externals.leaflet.DoneCallback
import externals.leaflet.geo.Coords
import externals.leaflet.geo.LatLngBounds
import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import externals.leaflet.layer.tile.GridLayer.GridLayerOptions
import org.w3c.dom.HTMLElement

/**
 * Generic class for handling a tiled grid of HTML elements. This is the base class for all tile
 * layers and replaces [Canvas][externals.leaflet.layer.vector.Canvas].
 *
 * GridLayer can be extended to create a tiled grid of HTML elements like `<canvas>`, `<img>` or
 * `<div>`. GridLayer will handle creating and animating these DOM elements for you.
 *
 * [`https://leafletjs.com/reference.html#gridlayer`](https://leafletjs.com/reference.html#gridlayer)
 */
abstract external class GridLayer<T: GridLayerOptions>(
    options: T = definedExternally
) : Layer<T> {

    open var _tiles: InternalTiles
    open var _tileZoom: Number?

    open fun bringToFront(): GridLayer<*> /* this */
    open fun bringToBack(): GridLayer<*> /* this */
    open fun getContainer(): HTMLElement?
    open fun setOpacity(opacity: Number): GridLayer<*> /* this */
    open fun setZIndex(zIndex: Number): GridLayer<*> /* this */
    open fun isLoading(): Boolean
    open fun redraw(): GridLayer<*> /* this */
    open fun getTileSize(): Point
    open fun createTile(coords: Coords, done: DoneCallback): HTMLElement

    open fun _tileCoordsToKey(coords: Coords): String
    open fun _wrapCoords(parameter: Coords): Coords

    interface GridLayerOptions : LayerOptions {
        /**
         * Width and height of tiles in the grid. Use a number if width and height are equal, or
         * [`Point(width, height)`][Point] otherwise.
         */
        var tileSize: dynamic /* Number? | Point? */
        var opacity: Number?
        var updateWhenIdle: Boolean?
        var updateWhenZooming: Boolean?
        var updateInterval: Number?
        var zIndex: Number?
        var bounds: LatLngBounds?
        var minZoom: Number?
        var maxZoom: Number?
        var maxNativeZoom: Number?
        var minNativeZoom: Number?
        var noWrap: Boolean?
        var className: String?
        var keepBuffer: Number?
    }

}
