@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

import externals.leaflet.PositionsUnion

external interface ControlOptions {
    var position: PositionsUnion
}
