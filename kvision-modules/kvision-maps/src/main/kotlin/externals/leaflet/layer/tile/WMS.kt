@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.tile

open external class WMS(baseUrl: String, options: WMSOptions) : TileLayer<WMSOptions> {
    open fun setParams(params: WMSParams, noRedraw: Boolean = definedExternally): WMS /* this */
    open var wmsParams: WMSParams
    override var options: WMSOptions
}
