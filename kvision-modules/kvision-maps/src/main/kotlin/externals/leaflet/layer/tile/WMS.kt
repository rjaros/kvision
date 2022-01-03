@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("TileLayer")

package externals.leaflet.layer.tile

import externals.leaflet.geo.CRS

open external class WMS(
    baseUrl: String,
    options: WMSOptions,
) : TileLayer {

    override var options: WMSOptions

    open fun setParams(params: WMSParams, noRedraw: Boolean = definedExternally): WMS /* this */
    open var wmsParams: WMSParams

    /** @see WMS.options */
    interface WMSOptions : TileLayerOptions {
        var layers: String?
        var styles: String?
        var format: String?
        var transparent: Boolean?
        var version: String?
        var crs: CRS?
        var uppercase: Boolean?
    }

    /** @see WMS.wmsParams */
    interface WMSParams {
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

}
