@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

package io.kvision.maps.externals.leaflet.events

import io.kvision.maps.externals.leaflet.core.Class

// Note: this can be simplified
//      - each event handler is a subtype of LeafletEventHandlerFn, so just have one type?
//      - add a wrapper for this class, so it works nicely in Kotlin, with an enum for event types

//@formatter:off
open external class Evented : Class {

    open fun on(type: LeafletEventId,                               fn: LeafletEventHandlerFn,         context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletControlEventId,                        fn: LayersControlEventHandlerFn,   context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletLayerEventId,                          fn: LayerEventHandlerFn,           context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletResizeEventId,                         fn: ResizeEventHandlerFn,          context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletPopupEventId,                          fn: PopupEventHandlerFn,           context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletTooltipEventId,                        fn: TooltipEventHandlerFn,         context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletErrorEventId,                          fn: ErrorEventHandlerFn,           context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletLocationEventId,                       fn: LocationEventHandlerFn,        context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletPointerEventId,                          fn: LeafletPointerEventHandlerFn,    context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletKeyboardEventId,                       fn: LeafletKeyboardEventHandlerFn, context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletZoomAnimEventId,                       fn: ZoomAnimEventHandlerFn,        context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletDragEndEventId,                        fn: DragEndEventHandlerFn,         context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletTileEventId,                           fn: TileEventHandlerFn,            context: dynamic = definedExternally): Evented /* this */
    open fun on(type: LeafletTileErrorId,                           fn: TileErrorEventHandlerFn,       context: dynamic = definedExternally): Evented /* this */
    open fun on(eventMap: dynamic,                                    context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletEventId,                              fn: LeafletEventHandlerFn = definedExternally,         context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletAnyEventId): Evented /* this */
    open fun off(type: LeafletControlEventId,                       fn: LayersControlEventHandlerFn = definedExternally,   context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletLayerEventId,                         fn: LayerEventHandlerFn = definedExternally,           context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletResizeEventId,                        fn: ResizeEventHandlerFn = definedExternally,          context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletPopupEventId,                         fn: PopupEventHandlerFn = definedExternally,           context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletTooltipEventId,                       fn: TooltipEventHandlerFn = definedExternally,         context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletErrorEventId,                         fn: ErrorEventHandlerFn = definedExternally,           context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletLocationEventId,                      fn: LocationEventHandlerFn = definedExternally,        context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletPointerEventId,                         fn: LeafletPointerEventHandlerFn = definedExternally,    context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletKeyboardEventId,                      fn: LeafletKeyboardEventHandlerFn = definedExternally, context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletZoomAnimEventId,                      fn: ZoomAnimEventHandlerFn = definedExternally,        context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletDragEndEventId,                       fn: DragEndEventHandlerFn = definedExternally,         context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletTileEventId,                          fn: TileEventHandlerFn = definedExternally,            context: dynamic = definedExternally): Evented /* this */
    open fun off(type: LeafletTileErrorId,                          fn: TileErrorEventHandlerFn = definedExternally,       context: dynamic = definedExternally): Evented /* this */
    open fun off(eventMap: dynamic,                                   context: dynamic = definedExternally): Evented /* this */
    open fun off(): Evented /* this */

    open fun fire(type: String, data: dynamic = definedExternally, propagate: Boolean = definedExternally): Evented /* this */

    open fun listens(type: String): Boolean

    open fun once(type: LeafletEventId,                             fn: LeafletEventHandlerFn,         context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletControlEventId,                      fn: LayersControlEventHandlerFn,   context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletLayerEventId,                        fn: LayerEventHandlerFn,           context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletResizeEventId,                       fn: ResizeEventHandlerFn,          context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletPopupEventId,                        fn: PopupEventHandlerFn,           context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletTooltipEventId,                      fn: TooltipEventHandlerFn,         context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletErrorEventId,                        fn: ErrorEventHandlerFn,           context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletLocationEventId,                     fn: LocationEventHandlerFn,        context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletPointerEventId,                        fn: LeafletPointerEventHandlerFn,    context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletKeyboardEventId,                     fn: LeafletKeyboardEventHandlerFn, context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletZoomAnimEventId,                     fn: ZoomAnimEventHandlerFn,        context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletDragEndEventId,                      fn: DragEndEventHandlerFn,         context: dynamic = definedExternally): Evented /* this */
    open fun once(type: LeafletTileEventId,                         fn: TileEventHandlerFn,            context: dynamic = definedExternally): Evented /* this */
    open fun once(eventMap: dynamic,                                  context: dynamic = definedExternally): Evented /* this */
    open fun addEventParent(obj: Evented): Evented /* this */

    open fun removeEventParent(obj: Evented): Evented /* this */
}
