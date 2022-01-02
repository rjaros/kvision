@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("TileLayer")

package externals.leaflet.layer.tile

open external class WMS(
    baseUrl: String,
    options: WMSOptions,
) : TileLayer {
    open fun setParams(params: WMSParams, noRedraw: Boolean = definedExternally): WMS /* this */
    open var wmsParams: WMSParams
    override var options: WMSOptions
}
