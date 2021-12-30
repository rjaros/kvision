@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker


external interface DivIconOptions : BaseIconOptions {
    var html: dynamic /* String? | HTMLElement? | Boolean? */
    var bgPos: dynamic /* Point? | JsTuple<Number, Number> */
    override var iconSize: dynamic /* Point? | JsTuple<Number, Number> */
    override var iconAnchor: dynamic /* Point? | JsTuple<Number, Number> */
    override var popupAnchor: dynamic /* Point? | JsTuple<Number, Number> */
    override var className: String?
}
