@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.tile


external interface TileLayerOptions : GridLayerOptions {
    var id: String?
    var accessToken: String?
    override var minZoom: Number?
    override var maxZoom: Number?
    override var maxNativeZoom: Number?
    override var minNativeZoom: Number?
    var subdomains: dynamic /* String? | Array<String>? */
    var errorTileUrl: String?
    var zoomOffset: Number?
    var tms: Boolean?
    var zoomReverse: Boolean?
    var detectRetina: Boolean?
    var crossOrigin: dynamic /* Boolean? | String? */
}
