@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker

import externals.leaflet.geometry.Point
import externals.leaflet.layer.marker.DivIcon.DivIconOptions


open external class DivIcon(options: DivIconOptions = definedExternally) : Icon<DivIconOptions> {

    interface DivIconOptions : IconOptions {
        var html: dynamic /* String? | HTMLElement? | Boolean? */
        var bgPos: Point
    }

}
