/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.events

import externals.leaflet.core.Class

// Note: this can be simplified
//      - each event handler is a subtype of LeafletEventHandlerFn, so just have one type?
//      - add a wrapper for this class, so it works nicely in Kotlin, with an enum for event types

//@formatter:off
open external class Evented : Class {

    open fun on(type: LeafletEventId,                               fn: LeafletEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletControlEventId,                        fn: LayersControlEventHandlerFn,   context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletLayerEventId,                          fn: LayerEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletResizeEventId,                         fn: ResizeEventHandlerFn,          context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletPopupEventId,                          fn: PopupEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletTooltipEventId,                        fn: TooltipEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletErrorEventId,                          fn: ErrorEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletLocationEventId,                       fn: LocationEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletMouseEventId,                          fn: LeafletMouseEventHandlerFn,    context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletKeyboardEventId,                       fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletZoomAnimEventId,                       fn: ZoomAnimEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletDragEndEventId,                        fn: DragEndEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletTileEventId,                           fn: TileEventHandlerFn,            context: Any = definedExternally): Evented /* this */
    open fun on(type: LeafletTileErrorId,                           fn: TileErrorEventHandlerFn,       context: Any = definedExternally): Evented /* this */
    open fun on(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun off(type: LeafletEventId,                              fn: LeafletEventHandlerFn = definedExternally,         context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletAnyEventId): Evented /* this */
    open fun off(type: LeafletControlEventId,                       fn: LayersControlEventHandlerFn = definedExternally,   context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletLayerEventId,                         fn: LayerEventHandlerFn = definedExternally,           context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletResizeEventId,                        fn: ResizeEventHandlerFn = definedExternally,          context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletPopupEventId,                         fn: PopupEventHandlerFn = definedExternally,           context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletTooltipEventId,                       fn: TooltipEventHandlerFn = definedExternally,         context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletErrorEventId,                         fn: ErrorEventHandlerFn = definedExternally,           context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletLocationEventId,                      fn: LocationEventHandlerFn = definedExternally,        context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletMouseEventId,                         fn: LeafletMouseEventHandlerFn = definedExternally,    context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletKeyboardEventId,                      fn: LeafletKeyboardEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletZoomAnimEventId,                      fn: ZoomAnimEventHandlerFn = definedExternally,        context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletDragEndEventId,                       fn: DragEndEventHandlerFn = definedExternally,         context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletTileEventId,                          fn: TileEventHandlerFn = definedExternally,            context: Any = definedExternally): Evented /* this */
    open fun off(type: LeafletTileErrorId,                          fn: TileErrorEventHandlerFn = definedExternally,       context: Any = definedExternally): Evented /* this */
    open fun off(eventMap: LeafletEventHandlerFnMap): Evented /* this */
    open fun off(): Evented /* this */

    open fun fire(type: String, data: Any = definedExternally, propagate: Boolean = definedExternally): Evented /* this */

    open fun listens(type: String): Boolean

    open fun once(type: LeafletEventId,                             fn: LeafletEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletControlEventId,                      fn: LayersControlEventHandlerFn,   context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletLayerEventId,                        fn: LayerEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletResizeEventId,                       fn: ResizeEventHandlerFn,          context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletPopupEventId,                        fn: PopupEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletTooltipEventId,                      fn: TooltipEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletErrorEventId,                        fn: ErrorEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletLocationEventId,                     fn: LocationEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletMouseEventId,                        fn: LeafletMouseEventHandlerFn,    context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletKeyboardEventId,                     fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletZoomAnimEventId,                     fn: ZoomAnimEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletDragEndEventId,                      fn: DragEndEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun once(type: LeafletTileEventId,                         fn: TileEventHandlerFn,            context: Any = definedExternally): Evented /* this */
    open fun once(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun addEventParent(obj: Evented): Evented /* this */

    open fun removeEventParent(obj: Evented): Evented /* this */

    open fun addEventListener(type: LeafletEventId,                 fn: LeafletEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletControlEventId,          fn: LayersControlEventHandlerFn,   context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletLayerEventId,            fn: LayerEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletResizeEventId,           fn: ResizeEventHandlerFn,          context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletPopupEventId,            fn: PopupEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletTooltipEventId,          fn: TooltipEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletErrorEventId,            fn: ErrorEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletLocationEventId,         fn: LocationEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletMouseEventId,            fn: LeafletMouseEventHandlerFn,    context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletKeyboardEventId,         fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletZoomAnimEventId,         fn: ZoomAnimEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletDragEndEventId,          fn: DragEndEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletTileEventId,             fn: TileEventHandlerFn,            context: Any = definedExternally): Evented /* this */
    open fun addEventListener(type: LeafletTileErrorId,             fn: TileErrorEventHandlerFn,       context: Any = definedExternally): Evented /* this */
    open fun addEventListener(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun removeEventListener(type: LeafletEventId,              fn: LeafletEventHandlerFn = definedExternally,         context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletAnyEventId): Evented /* this */
    open fun removeEventListener(type: LeafletControlEventId,       fn: LayersControlEventHandlerFn = definedExternally,   context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletLayerEventId,         fn: LayerEventHandlerFn = definedExternally,           context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletResizeEventId,        fn: ResizeEventHandlerFn = definedExternally,          context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletPopupEventId,         fn: PopupEventHandlerFn = definedExternally,           context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletTooltipEventId,       fn: TooltipEventHandlerFn = definedExternally,         context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletErrorEventId,         fn: ErrorEventHandlerFn = definedExternally,           context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletLocationEventId,      fn: LocationEventHandlerFn = definedExternally,        context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletMouseEventId,         fn: LeafletMouseEventHandlerFn = definedExternally,    context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletKeyboardEventId,      fn: LeafletKeyboardEventHandlerFn = definedExternally, context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletZoomAnimEventId,      fn: ZoomAnimEventHandlerFn = definedExternally,        context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletDragEndEventId,       fn: DragEndEventHandlerFn = definedExternally,         context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletTileEventId,          fn: TileEventHandlerFn = definedExternally,            context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(type: LeafletTileErrorId,          fn: TileErrorEventHandlerFn = definedExternally,       context: Any = definedExternally): Evented /* this */
    open fun removeEventListener(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun clearAllEventListeners(): Evented /* this */

    open fun addOneTimeEventListener(type: LeafletEventId,          fn: LeafletEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletControlEventId,   fn: LayersControlEventHandlerFn,   context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletLayerEventId,     fn: LayerEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletResizeEventId,    fn: ResizeEventHandlerFn,          context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletPopupEventId,     fn: PopupEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletTooltipEventId,   fn: TooltipEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletErrorEventId,     fn: ErrorEventHandlerFn,           context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletLocationEventId,  fn: LocationEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletMouseEventId,     fn: LeafletMouseEventHandlerFn,    context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletKeyboardEventId,  fn: LeafletKeyboardEventHandlerFn, context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletZoomAnimEventId,  fn: ZoomAnimEventHandlerFn,        context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletDragEndEventId,   fn: DragEndEventHandlerFn,         context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletTileEventId,      fn: TileEventHandlerFn,            context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(type: LeafletTileErrorId,      fn: TileErrorEventHandlerFn,       context: Any = definedExternally): Evented /* this */
    open fun addOneTimeEventListener(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    open fun fireEvent(type: String, data: Any = definedExternally, propagate: Boolean = definedExternally): Evented /* this */

    open fun hasEventListeners(type: String): Boolean
}
