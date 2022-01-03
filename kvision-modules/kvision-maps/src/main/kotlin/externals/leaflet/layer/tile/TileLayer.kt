@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.tile

import externals.leaflet.DoneCallback
import externals.leaflet.geo.Coords
import externals.leaflet.layer.tile.TileLayer.TileLayerOptions
import org.w3c.dom.HTMLElement

open external class TileLayer<T : TileLayerOptions>(
    urlTemplate: String,
    options: T = definedExternally
) : GridLayer<T> {

    /** @return this */
    open fun setUrl(url: String, noRedraw: Boolean = definedExternally): TileLayer<*>

    open fun getTileUrl(coords: Coords): String

    open fun _tileOnLoad(done: DoneCallback, tile: HTMLElement)
    open fun _tileOnError(done: DoneCallback, tile: HTMLElement, e: Error)
    open fun _abortLoading()
    open fun _getZoomForUrl(): Number

    interface TileLayerOptions : GridLayerOptions {
        var id: String?
        var accessToken: String?
        var subdomains: dynamic /* String? | Array<String>? */
        var errorTileUrl: String?
        var zoomOffset: Number?
        var tms: Boolean?
        var zoomReverse: Boolean?
        var detectRetina: Boolean?
        var crossOrigin: dynamic /* Boolean? | String? */
    }

}
