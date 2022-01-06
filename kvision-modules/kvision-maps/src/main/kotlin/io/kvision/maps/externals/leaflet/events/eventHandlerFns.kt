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

package io.kvision.maps.externals.leaflet.events

/** "baselayerchange" | "overlayadd" | "overlayremove" | "layeradd" | "layerremove" | "zoomlevelschange" | "unload" | "viewreset" | "load" | "zoomstart" | "movestart" | "zoom" | "move" | "zoomend" | "moveend" | "autopanstart" | "dragstart" | "drag" | "add" | "remove" | "loading" | "error" | "update" | "down" | "predrag" | "resize" | "popupopen" | "popupclose" | "tooltipopen" | "tooltipclose" | "locationerror" | "locationfound" | "click" | "dblclick" | "mousedown" | "mouseup" | "mouseover" | "mouseout" | "mousemove" | "contextmenu" | "preclick" | "keypress" | "keydown" | "keyup" | "zoomanim" | "dragend" | "tileunload" | "tileloadstart" | "tileload" | "tileerror" */
typealias LeafletAnyEventId = String


/** "zoomlevelschange" | "unload" | "viewreset" | "load" | "zoomstart" | "movestart" | "zoom" | "move" | "zoomend" | "moveend" | "autopanstart" | "dragstart" | "drag" | "add" | "remove" | "loading" | "error" | "update" | "down" | "predrag" */
typealias LeafletEventId = String
typealias LeafletEventHandlerFn = (event: LeafletEvent) -> Unit


/** "baselayerchange" | "overlayadd" | "overlayremove" */
typealias LeafletControlEventId = String
typealias LayersControlEventHandlerFn = (event: LayersControlEvent) -> Unit


/** "layeradd" | "layerremove" */
typealias LeafletLayerEventId = String
typealias LayerEventHandlerFn = (event: LayerEvent) -> Unit


/** "resize" */
typealias LeafletResizeEventId = String
typealias ResizeEventHandlerFn = (event: ResizeEvent) -> Unit


/** "popupopen" | "popupclose" */
typealias LeafletPopupEventId = String
typealias PopupEventHandlerFn = (event: PopupEvent) -> Unit


/** "tooltipopen" | "tooltipclose" */
typealias LeafletTooltipEventId = String
typealias TooltipEventHandlerFn = (event: TooltipEvent) -> Unit


/** "locationerror" */
typealias LeafletErrorEventId = String
typealias ErrorEventHandlerFn = (event: ErrorEvent) -> Unit


/** "locationfound" */
typealias LeafletLocationEventId = String
typealias LocationEventHandlerFn = (event: LocationEvent) -> Unit


/** "click" | "dblclick" | "mousedown" | "mouseup" | "mouseover" | "mouseout" | "mousemove" | "contextmenu" | "preclick" */
typealias LeafletMouseEventId = String
typealias LeafletMouseEventHandlerFn = (event: LeafletMouseEvent) -> Unit


/** "keypress" | "keydown" | "keyup" */
typealias LeafletKeyboardEventId = String
typealias LeafletKeyboardEventHandlerFn = (event: LeafletKeyboardEvent) -> Unit


/** "zoomanim" */
typealias LeafletZoomAnimEventId = String
typealias ZoomAnimEventHandlerFn = (event: ZoomAnimEvent) -> Unit


/** "dragend" */
typealias LeafletDragEndEventId = String
typealias DragEndEventHandlerFn = (event: DragEndEvent) -> Unit


/** "tileunload" | "tileloadstart" | "tileload" | "tileerror" */
typealias LeafletTileEventId = String
typealias TileEventHandlerFn = (event: TileEvent) -> Unit


/** "tileerror" */
typealias LeafletTileErrorId = String
typealias TileErrorEventHandlerFn = (event: TileErrorEvent) -> Unit
