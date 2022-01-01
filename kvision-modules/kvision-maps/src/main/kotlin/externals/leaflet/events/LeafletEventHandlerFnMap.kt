@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.events


external interface LeafletEventHandlerFnMap {
    // control events
    var baselayerchange: LayersControlEventHandlerFn?
    var overlayadd: LayersControlEventHandlerFn?
    var overlayremove: LayersControlEventHandlerFn?

    // layer events
    var layeradd: LayerEventHandlerFn?
    var layerremove: LayerEventHandlerFn?

    // leaflet events
    var zoomlevelschange: LeafletEventHandlerFn?
    var unload: LeafletEventHandlerFn?
    var viewreset: LeafletEventHandlerFn?
    var load: LeafletEventHandlerFn?
    var zoomstart: LeafletEventHandlerFn?
    var movestart: LeafletEventHandlerFn?
    var zoom: LeafletEventHandlerFn?
    var move: LeafletEventHandlerFn?
    var zoomend: LeafletEventHandlerFn?
    var moveend: LeafletEventHandlerFn?
    var autopanstart: LeafletEventHandlerFn?
    var dragstart: LeafletEventHandlerFn?
    var drag: LeafletEventHandlerFn?
    var add: LeafletEventHandlerFn?
    var remove: LeafletEventHandlerFn?
    var loading: LeafletEventHandlerFn?
    var error: LeafletEventHandlerFn?
    var update: LeafletEventHandlerFn?
    var down: LeafletEventHandlerFn?
    var predrag: LeafletEventHandlerFn?

    // resize events
    var resize: ResizeEventHandlerFn?

    // popup evetns
    var popupopen: PopupEventHandlerFn?
    var popupclose: PopupEventHandlerFn?

    // tooltip events
    var tooltipopen: TooltipEventHandlerFn?
    var tooltipclose: TooltipEventHandlerFn?

    // error events
    var locationerror: ErrorEventHandlerFn?

    // location events
    var locationfound: LocationEventHandlerFn?

    // mouse events
    var click: LeafletMouseEventHandlerFn?
    var dblclick: LeafletMouseEventHandlerFn?
    var mousedown: LeafletMouseEventHandlerFn?
    var mouseup: LeafletMouseEventHandlerFn?
    var mouseover: LeafletMouseEventHandlerFn?
    var mouseout: LeafletMouseEventHandlerFn?
    var mousemove: LeafletMouseEventHandlerFn?
    var contextmenu: LeafletMouseEventHandlerFn?
    var preclick: LeafletMouseEventHandlerFn?

    // keyboard events
    var keypress: LeafletKeyboardEventHandlerFn?
    var keydown: LeafletKeyboardEventHandlerFn?
    var keyup: LeafletKeyboardEventHandlerFn?

    // zoom animation events
    var zoomanim: ZoomAnimEventHandlerFn?

    // drag events
    var dragend: DragEndEventHandlerFn?

    // tile events
    var tileunload: TileEventHandlerFn?
    var tileloadstart: TileEventHandlerFn?
    var tileload: TileEventHandlerFn?

    // tile error events
    var tileerror: TileErrorEventHandlerFn?
}
