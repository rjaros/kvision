@file:JsModule("leaflet")
@file:JsNonModule


package externals.leaflet.layer.tile

import externals.leaflet.geo.CRS

external interface WMSOptions : TileLayerOptions {
    var layers: String?
    var styles: String?
    var format: String?
    var transparent: Boolean?
    var version: String?
    var crs: CRS?
    var uppercase: Boolean?
}
