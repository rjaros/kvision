package externals.leaflet.events


/** "zoomlevelschange" | "unload" | "viewreset" | "load" | "zoomstart" | "movestart" | "zoom" | "move" | "zoomend" | "moveend" | "autopanstart" | "dragstart" | "drag" | "add" | "remove" | "loading" | "error" | "update" | "down" | "predrag" */
typealias LeafletEventId = String

/** "baselayerchange" | "overlayadd" | "overlayremove" */
typealias LeafletControlEventId = String

/** "click" | "dblclick" | "mousedown" | "mouseup" | "mouseover" | "mouseout" | "mousemove" | "contextmenu" | "preclick" */
typealias LeafletMouseEventId = String

/** "layeradd" | "layerremove" */
typealias LeafletLayerEventId = String

/** "popupopen" | "popupclose" */
typealias LeafletPopupEventId = String

/** "tileunload" | "tileloadstart" | "tileload" | "tileerror" */
typealias LeafletTileEventId = String

/** "baselayerchange" | "overlayadd" | "overlayremove" | "layeradd" | "layerremove" | "zoomlevelschange" | "unload" | "viewreset" | "load" | "zoomstart" | "movestart" | "zoom" | "move" | "zoomend" | "moveend" | "autopanstart" | "dragstart" | "drag" | "add" | "remove" | "loading" | "error" | "update" | "down" | "predrag" | "resize" | "popupopen" | "popupclose" | "tooltipopen" | "tooltipclose" | "locationerror" | "locationfound" | "click" | "dblclick" | "mousedown" | "mouseup" | "mouseover" | "mouseout" | "mousemove" | "contextmenu" | "preclick" | "keypress" | "keydown" | "keyup" | "zoomanim" | "dragend" | "tileunload" | "tileloadstart" | "tileload" | "tileerror" */
typealias AnyEventId = String
