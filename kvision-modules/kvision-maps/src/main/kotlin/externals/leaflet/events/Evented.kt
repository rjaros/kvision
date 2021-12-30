@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.events

import externals.leaflet.core.Class

// TODO tidy up
//      - check to make sure `context: Any = definedExternally` is an optional arg and so half these methods can be removed
//      - each event handler is a sub-type of LeafletEventHandlerFn, so just have one type?
//        And add a wrapper for this class so it works nicely in Kotlin, with an enum for event types

//@formatter:off
open external class Evented : Class {

    open fun on(type: LeafletEventId,                                                                 fn: LeafletEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: LeafletEventId,                                                                 fn: LeafletEventHandlerFn): Evented /* this */
    open fun on(type: LeafletControlEventId,                                                          fn: LayersControlEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: LeafletControlEventId,                                                          fn: LayersControlEventHandlerFn): Evented /* this */
    open fun on(type: LeafletLayerEventId,                                                              fn: LayerEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: LeafletLayerEventId,                                                              fn: LayerEventHandlerFn): Evented /* this */
    open fun on(type: String /* "resize" */,                                                          fn: ResizeEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "resize" */,                                                          fn: ResizeEventHandlerFn): Evented /* this */
    open fun on(type: LeafletPopupEventId,                                                              fn: PopupEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: LeafletPopupEventId,                                                              fn: PopupEventHandlerFn): Evented /* this */
    open fun on(type: String /* "tooltipopen" | "tooltipclose" */,                                    fn: TooltipEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "tooltipopen" | "tooltipclose" */,                                    fn: TooltipEventHandlerFn): Evented /* this */
    open fun on(type: String /* "locationerror" */,                                                   fn: ErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "locationerror" */,                                                   fn: ErrorEventHandlerFn): Evented /* this */
    open fun on(type: String /* "locationfound" */,                                                   fn: LocationEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "locationfound" */,                                                   fn: LocationEventHandlerFn): Evented /* this */
    open fun on(type: LeafletMouseEventId,                                                            fn: LeafletMouseEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: LeafletMouseEventId,                                                            fn: LeafletMouseEventHandlerFn): Evented /* this */
    open fun on(type: String /* "keypress" | "keydown" | "keyup" */,                                  fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "keypress" | "keydown" | "keyup" */,                                  fn: LeafletKeyboardEventHandlerFn): Evented /* this */
    open fun on(type: String /* "zoomanim" */,                                                        fn: ZoomAnimEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "zoomanim" */,                                                        fn: ZoomAnimEventHandlerFn): Evented /* this */
    open fun on(type: String /* "dragend" */,                                                         fn: DragEndEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "dragend" */,                                                         fn: DragEndEventHandlerFn): Evented /* this */
    open fun on(type: LeafletTileEventId,                                                               fn: TileEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: LeafletTileEventId,                                                               fn: TileEventHandlerFn): Evented /* this */
    open fun on(type: String /* "tileerror" */,                                                       fn: TileErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun on(type: String /* "tileerror" */,                                                       fn: TileErrorEventHandlerFn): Evented /* this */
    open fun on(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun off(type: LeafletEventId,                                                                fn: LeafletEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
    open fun off(type: AnyEventId): Evented /* this */
//    open fun off(type: LeafletEventId,                                                                fn: LeafletEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: LeafletControlEventId,                                                         fn: LayersControlEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: LeafletControlEventId,                                                         fn: LayersControlEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: LeafletLayerEventId,                                                             fn: LayerEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: LeafletLayerEventId,                                                             fn: LayerEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "resize" */,                                                         fn: ResizeEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "resize" */,                                                         fn: ResizeEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: LeafletPopupEventId,                                                             fn: PopupEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: LeafletPopupEventId,                                                             fn: PopupEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "tooltipopen" | "tooltipclose" */,                                   fn: TooltipEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "tooltipopen" | "tooltipclose" */,                                   fn: TooltipEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "locationerror" */,                                                  fn: ErrorEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "locationerror" */,                                                  fn: ErrorEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "locationfound" */,                                                  fn: LocationEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "locationfound" */,                                                  fn: LocationEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: LeafletMouseEventId,                                                           fn: LeafletMouseEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: LeafletMouseEventId,                                                           fn: LeafletMouseEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "keypress" | "keydown" | "keyup" */,                                 fn: LeafletKeyboardEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "keypress" | "keydown" | "keyup" */,                                 fn: LeafletKeyboardEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "zoomanim" */,                                                       fn: ZoomAnimEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "zoomanim" */,                                                       fn: ZoomAnimEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "dragend" */,                                                        fn: DragEndEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "dragend" */,                                                        fn: DragEndEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: LeafletTileEventId,                                                              fn: TileEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: LeafletTileEventId,                                                              fn: TileEventHandlerFn = definedExternally): Evented /* this */
    open fun off(type: String /* "tileerror" */,                                                      fn: TileErrorEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun off(type: String /* "tileerror" */,                                                      fn: TileErrorEventHandlerFn = definedExternally): Evented /* this */
    open fun off(eventMap: LeafletEventHandlerFnMap): Evented /* this */
    open fun off(): Evented /* this */

    open fun fire(type: String, data: Any = definedExternally, propagate: Boolean = definedExternally): Evented /* this */

    open fun listens(type: String): Boolean

    open fun once(type: LeafletEventId,                                                               fn: LeafletEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: LeafletEventId,                                                               fn: LeafletEventHandlerFn): Evented /* this */
    open fun once(type: LeafletControlEventId,                                                        fn: LayersControlEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: LeafletControlEventId,                                                        fn: LayersControlEventHandlerFn): Evented /* this */
    open fun once(type: LeafletLayerEventId,                                                            fn: LayerEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: LeafletLayerEventId,                                                            fn: LayerEventHandlerFn): Evented /* this */
    open fun once(type: String /* "resize" */,                                                        fn: ResizeEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "resize" */,                                                        fn: ResizeEventHandlerFn): Evented /* this */
    open fun once(type: LeafletPopupEventId,                                                            fn: PopupEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: LeafletPopupEventId,                                                            fn: PopupEventHandlerFn): Evented /* this */
    open fun once(type: String /* "tooltipopen" | "tooltipclose" */,                                  fn: TooltipEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "tooltipopen" | "tooltipclose" */,                                  fn: TooltipEventHandlerFn): Evented /* this */
    open fun once(type: String /* "locationerror" */,                                                 fn: ErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "locationerror" */,                                                 fn: ErrorEventHandlerFn): Evented /* this */
    open fun once(type: String /* "locationfound" */,                                                 fn: LocationEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "locationfound" */,                                                 fn: LocationEventHandlerFn): Evented /* this */
    open fun once(type: LeafletMouseEventId,                                                          fn: LeafletMouseEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: LeafletMouseEventId,                                                          fn: LeafletMouseEventHandlerFn): Evented /* this */
    open fun once(type: String /* "keypress" | "keydown" | "keyup" */,                                fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "keypress" | "keydown" | "keyup" */,                                fn: LeafletKeyboardEventHandlerFn): Evented /* this */
    open fun once(type: String /* "zoomanim" */,                                                      fn: ZoomAnimEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "zoomanim" */,                                                      fn: ZoomAnimEventHandlerFn): Evented /* this */
    open fun once(type: String /* "dragend" */,                                                       fn: DragEndEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: String /* "dragend" */,                                                       fn: DragEndEventHandlerFn): Evented /* this */
    open fun once(type: LeafletTileEventId,       fn: TileEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun once(type: LeafletTileEventId,       fn: TileEventHandlerFn): Evented /* this */
    open fun once(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun addEventParent(obj: Evented): Evented /* this */

    open fun removeEventParent(obj: Evented): Evented /* this */

    open fun addEventListener(type: LeafletEventId,                                                   fn: LeafletEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: LeafletEventId,                                                   fn: LeafletEventHandlerFn): Evented /* this */
    open fun addEventListener(type: LeafletControlEventId,                                            fn: LayersControlEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: LeafletControlEventId,                                            fn: LayersControlEventHandlerFn): Evented /* this */
    open fun addEventListener(type: LeafletLayerEventId,                                                fn: LayerEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: LeafletLayerEventId,                                                fn: LayerEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "resize" */,                                            fn: ResizeEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "resize" */,                                            fn: ResizeEventHandlerFn): Evented /* this */
    open fun addEventListener(type: LeafletPopupEventId,                                                fn: PopupEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: LeafletPopupEventId,                                                fn: PopupEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "tooltipopen" | "tooltipclose" */,                      fn: TooltipEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "tooltipopen" | "tooltipclose" */,                      fn: TooltipEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "locationerror" */,                                     fn: ErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "locationerror" */,                                     fn: ErrorEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "locationfound" */,                                     fn: LocationEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "locationfound" */,                                     fn: LocationEventHandlerFn): Evented /* this */
    open fun addEventListener(type: LeafletMouseEventId,                                              fn: LeafletMouseEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: LeafletMouseEventId,                                              fn: LeafletMouseEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "keypress" | "keydown" | "keyup" */,                    fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "keypress" | "keydown" | "keyup" */,                    fn: LeafletKeyboardEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "zoomanim" */,                                          fn: ZoomAnimEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "zoomanim" */,                                          fn: ZoomAnimEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "dragend" */,                                           fn: DragEndEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "dragend" */,                                           fn: DragEndEventHandlerFn): Evented /* this */
    open fun addEventListener(type: LeafletTileEventId,                                                 fn: TileEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: LeafletTileEventId,                                                 fn: TileEventHandlerFn): Evented /* this */
    open fun addEventListener(type: String /* "tileerror" */,                                         fn: TileErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addEventListener(type: String /* "tileerror" */,                                         fn: TileErrorEventHandlerFn): Evented /* this */
    open fun addEventListener(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun removeEventListener(type: LeafletEventId,                                                fn: LeafletEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: AnyEventId): Evented /* this */
//    open fun removeEventListener(type: LeafletEventId,                                                fn: LeafletEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletControlEventId,                                         fn: LayersControlEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: LeafletControlEventId,                                         fn: LayersControlEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletLayerEventId,                                             fn: LayerEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: LeafletLayerEventId,                                             fn: LayerEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "resize" */,                                         fn: ResizeEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "resize" */,                                         fn: ResizeEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletPopupEventId,                                             fn: PopupEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: LeafletPopupEventId,                                             fn: PopupEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "tooltipopen" | "tooltipclose" */,                   fn: TooltipEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "tooltipopen" | "tooltipclose" */,                   fn: TooltipEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "locationerror" */,                                  fn: ErrorEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "locationerror" */,                                  fn: ErrorEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "locationfound" */,                                  fn: LocationEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "locationfound" */,                                  fn: LocationEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletMouseEventId,                                           fn: LeafletMouseEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: LeafletMouseEventId,                                           fn: LeafletMouseEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "keypress" | "keydown" | "keyup" */,                 fn: LeafletKeyboardEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "keypress" | "keydown" | "keyup" */,                 fn: LeafletKeyboardEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "zoomanim" */,                                       fn: ZoomAnimEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "zoomanim" */,                                       fn: ZoomAnimEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "dragend" */,                                        fn: DragEndEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "dragend" */,                                        fn: DragEndEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletTileEventId,                                              fn: TileEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: LeafletTileEventId,                                              fn: TileEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(type: String /* "tileerror" */,                                      fn: TileErrorEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
//    open fun removeEventListener(type: String /* "tileerror" */,                                      fn: TileErrorEventHandlerFn = definedExternally): Evented /* this */
    open fun removeEventListener(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun clearAllEventListeners(): Evented /* this */

    open fun addOneTimeEventListener(type: LeafletEventId,                                            fn: LeafletEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: LeafletEventId,                                            fn: LeafletEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletControlEventId,                                     fn: LayersControlEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: LeafletControlEventId,                                     fn: LayersControlEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletLayerEventId,                                         fn: LayerEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: LeafletLayerEventId,                                         fn: LayerEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "resize" */,                                     fn: ResizeEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "resize" */,                                     fn: ResizeEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletPopupEventId,                                         fn: PopupEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: LeafletPopupEventId,                                         fn: PopupEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "tooltipopen" | "tooltipclose" */,               fn: TooltipEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "tooltipopen" | "tooltipclose" */,               fn: TooltipEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "locationerror" */,                              fn: ErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "locationerror" */,                              fn: ErrorEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "locationfound" */,                              fn: LocationEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "locationfound" */,                              fn: LocationEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletMouseEventId,                                       fn: LeafletMouseEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: LeafletMouseEventId,                                       fn: LeafletMouseEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "keypress" | "keydown" | "keyup" */,             fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "keypress" | "keydown" | "keyup" */,             fn: LeafletKeyboardEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "zoomanim" */,                                   fn: ZoomAnimEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "zoomanim" */,                                   fn: ZoomAnimEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "dragend" */,                                    fn: DragEndEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "dragend" */,                                    fn: DragEndEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletTileEventId,                                          fn: TileEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: LeafletTileEventId,                                          fn: TileEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(type: String /* "tileerror" */,                                  fn: TileErrorEventHandlerFn, context: Any = definedExternally): Evented /* this */
//    open fun addOneTimeEventListener(type: String /* "tileerror" */,                                  fn: TileErrorEventHandlerFn): Evented /* this */
    open fun addOneTimeEventListener(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun fireEvent(type: String, data: Any = definedExternally, propagate: Boolean = definedExternally): Evented /* this */

    open fun hasEventListeners(type: String): Boolean
}
