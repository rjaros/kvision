@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.control

import externals.leaflet.map.PanOptions

external interface ZoomPanOptions : ZoomOptions, PanOptions
