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

package io.kvision.tabulator.js

import io.kvision.tabulator.js.Tabulator.CellComponent
import io.kvision.tabulator.js.Tabulator.ColumnComponent
import io.kvision.tabulator.js.Tabulator.FilterParams
import io.kvision.tabulator.js.Tabulator.GroupComponent
import io.kvision.tabulator.js.Tabulator.RowComponent
import org.w3c.dom.events.UIEvent

typealias FilterFunction = (field: String, type: String /* "=" | "!=" | "like" | "<" | ">" | "<=" | ">=" | "in" | "regex" | "starts" | "ends" */, value: Any, filterParams: FilterParams) -> Unit

typealias GroupValuesArg = Array<Array<Any>>

typealias CustomMutator = (value: Any, data: Any, type: String /* "data" | "edit" */, mutatorParams: Any, cell: CellComponent) -> Any

typealias CustomAccessor = (value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, AccessorParams: Any, column: ColumnComponent, row: RowComponent) -> Any

typealias ColumnCalcParams = (values: Any, data: Any) -> Any

typealias ValueStringCallback = (value: Any) -> String

typealias ValueBooleanCallback = (value: Any) -> Boolean

typealias ValueVoidCallback = (value: Any) -> Unit

typealias EmptyCallback = (callback: () -> Unit) -> Unit

typealias CellEventCallback = (e: UIEvent, cell: CellComponent) -> Unit

typealias CellEditEventCallback = (cell: CellComponent) -> Unit

typealias ColumnEventCallback = (e: UIEvent, column: ColumnComponent) -> Unit

typealias RowEventCallback = (e: UIEvent, row: RowComponent) -> Unit

typealias RowChangedCallback = (row: RowComponent) -> Unit

typealias GroupEventCallback = (e: UIEvent, group: GroupComponent) -> Unit

typealias ColumnSorterParamLookupFunction = (column: ColumnComponent, dir: String /* "asc" | "desc" */) -> Any
