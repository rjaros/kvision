@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.events


external interface LeafletEventHandlerFnMap {
    var baselayerchange: LayersControlEventHandlerFn?
    var overlayadd: LayersControlEventHandlerFn?
    var overlayremove: LayersControlEventHandlerFn?
    var layeradd: LayerEventHandlerFn?
    var layerremove: LayerEventHandlerFn?
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
    var resize: ResizeEventHandlerFn?
    var popupopen: PopupEventHandlerFn?
    var popupclose: PopupEventHandlerFn?
    var tooltipopen: TooltipEventHandlerFn?
    var tooltipclose: TooltipEventHandlerFn?
    var locationerror: ErrorEventHandlerFn?
    var locationfound: LocationEventHandlerFn?
    var click: LeafletMouseEventHandlerFn?
    var dblclick: LeafletMouseEventHandlerFn?
    var mousedown: LeafletMouseEventHandlerFn?
    var mouseup: LeafletMouseEventHandlerFn?
    var mouseover: LeafletMouseEventHandlerFn?
    var mouseout: LeafletMouseEventHandlerFn?
    var mousemove: LeafletMouseEventHandlerFn?
    var contextmenu: LeafletMouseEventHandlerFn?
    var preclick: LeafletMouseEventHandlerFn?
    var keypress: LeafletKeyboardEventHandlerFn?
    var keydown: LeafletKeyboardEventHandlerFn?
    var keyup: LeafletKeyboardEventHandlerFn?
    var zoomanim: ZoomAnimEventHandlerFn?
    var dragend: DragEndEventHandlerFn?
    var tileunload: TileEventHandlerFn?
    var tileloadstart: TileEventHandlerFn?
    var tileload: TileEventHandlerFn?
    var tileerror: TileErrorEventHandlerFn?
}
