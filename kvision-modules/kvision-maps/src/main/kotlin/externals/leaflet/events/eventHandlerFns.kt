package externals.leaflet.events

typealias LeafletEventHandlerFn = (event: LeafletEvent) -> Unit

typealias LayersControlEventHandlerFn = (event: LayersControlEvent) -> Unit

typealias LayerEventHandlerFn = (event: LayerEvent) -> Unit

typealias ResizeEventHandlerFn = (event: ResizeEvent) -> Unit

typealias PopupEventHandlerFn = (event: PopupEvent) -> Unit

typealias TooltipEventHandlerFn = (event: TooltipEvent) -> Unit

typealias ErrorEventHandlerFn = (event: ErrorEvent) -> Unit

typealias LocationEventHandlerFn = (event: LocationEvent) -> Unit

typealias LeafletMouseEventHandlerFn = (event: LeafletMouseEvent) -> Unit

typealias LeafletKeyboardEventHandlerFn = (event: LeafletKeyboardEvent) -> Unit

typealias ZoomAnimEventHandlerFn = (event: ZoomAnimEvent) -> Unit

typealias DragEndEventHandlerFn = (event: DragEndEvent) -> Unit

typealias TileEventHandlerFn = (event: TileEvent) -> Unit

typealias TileErrorEventHandlerFn = (event: TileErrorEvent) -> Unit
