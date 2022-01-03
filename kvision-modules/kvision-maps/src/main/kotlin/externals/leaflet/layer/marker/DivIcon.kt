@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker

import externals.leaflet.geometry.Point


open external class DivIcon(options: DivIconOptions = definedExternally) : Icon {
    override var options: DivIconOptions

    interface DivIconOptions : IconOptions {
        var html: dynamic /* String? | HTMLElement? | Boolean? */
        var bgPos: Point
    }

}
