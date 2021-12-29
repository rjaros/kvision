@file:JsModule("leaflet")
@file:JsNonModule


package externals.leaflet.layer.tile


external interface WMSParams {
    var format: String?
    var layers: String
    var request: String?
    var service: String?
    var styles: String?
    var version: String?
    var transparent: Boolean?
    var width: Number?
    var height: Number?
}
