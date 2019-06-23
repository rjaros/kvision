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

package pl.treksoft.kvision.tabulator

import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.form.FormControl
import pl.treksoft.kvision.form.FormInput
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.tabulator.EditorRoot.disposeTimer
import pl.treksoft.kvision.tabulator.EditorRoot.root
import pl.treksoft.kvision.tabulator.js.Tabulator
import pl.treksoft.kvision.utils.obj
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Promise

/**
 * Tooltip generation mode.
 */
enum class TooltipGenerationMode(internal val mode: String) {
    LOAD("load"),
    HOVER("hover")
}

/**
 * Column align.
 */
enum class Align(internal val align: String) {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right")
}

/**
 * Built-in sorters.
 */
enum class Sorter(internal val sorter: String) {
    STRING("string"),
    NUMBER("number"),
    ALPHANUM("alphanum"),
    BOOLEAN("boolean"),
    EXISTS("exists"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
    ARRAY("array")
}

/**
 * Built-in formatters.
 */
enum class Formatter(internal val formatter: String) {
    PLAINTEXT("plaintext"),
    TEXTAREA("textarea"),
    HTML("html"),
    MONEY("money"),
    IMAGE("image"),
    LINK("link"),
    DATETIME("datetime"),
    DATETIMEDIFF("datetimediff"),
    TICKCROSS("tickCross"),
    COLOR("color"),
    STAR("star"),
    TRAFFIC("traffic"),
    PROGRESS("progress"),
    LOOKUP("lookup"),
    BUTTONTICK("buttonTick"),
    BUTTONCROSS("buttonCross"),
    ROWNUM("rownum"),
    HANDLE("handle")
}

/**
 * Built-in editors.
 */
enum class Editor(internal val editor: String) {
    INPUT("input"),
    TEXTAREA("textarea"),
    NUMBER("number"),
    RANGE("range"),
    TICK("tick"),
    STAR("star"),
    SELECT("select"),
    AUTOCOMPLETE("autocomplete")
}

/**
 * Built-in validators.
 */
enum class Validator(internal val validator: String) {
    REQUIRED("required"),
    UNIQUE("unique"),
    INTEGER("integer"),
    FLOAT("float"),
    NUMERIC("numeric"),
    STRING("string"),
    MIN("min"),
    MAX("max"),
    MINLENGTH("minLength"),
    MAXLENGTH("maxLength"),
    IN("in"),
    REGEX("regex")
}

/**
 * Built-in calc functions.
 */
enum class Calc(internal val calc: String) {
    AVG("avg"),
    MAX("max"),
    MIN("min"),
    SUM("sum"),
    CONCAT("concat"),
    COUNT("count")
}

/**
 * Sorting directions.
 */
enum class SortingDir(internal val dir: String) {
    ASC("asc"),
    DESC("desc")
}

/**
 * Filters.
 */
enum class Filter(internal val filter: String) {
    EQUAL("="),
    NOTEQUAL("!="),
    LIKE("like"),
    LESS("<"),
    LESSEQ("<="),
    GREATER(">"),
    GREATEREQ(">="),
    IN("in"),
    REGEX("regex")
}

/**
 * Table layouts.
 */
enum class Layout(internal val layout: String) {
    FITDATA("fitData"),
    FITDATAFILL("fitDataFill"),
    FITCOLUMNS("fitColumns")
}

/**
 * Responsive layout modes.
 */
enum class ResponsiveLayout(internal val layout: String) {
    HIDE("hide"),
    COLLAPSE("collapse")
}

/**
 * Column positions.
 */
enum class ColumnPosition(internal val position: String) {
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right")
}

/**
 * Row scroll positions .
 */
enum class RowPosition(internal val position: String) {
    BOTTOM("bottom"),
    TOP("top"),
    CENTER("center"),
    NEAREST("nearest")
}

/**
 * Row positions.
 */
enum class RowPos(internal val position: String) {
    BOTTOM("bottom"),
    TOP("top")
}

/**
 * Range select modes.
 */
enum class RangeMode(internal val mode: String) {
    CLICK("click")
}

/**
 * Progressive modes.
 */
enum class ProgressiveMode(internal val mode: String) {
    LOAD("load"),
    SCROLL("scroll")
}

/**
 * Pagination modes.
 */
enum class PaginationMode(internal val mode: String) {
    LOCAL("local"),
    REMOTE("remote")
}

/**
 * Add row modes.
 */
enum class AddRowMode(internal val mode: String) {
    TABLE("table"),
    PAGE("page")
}

/**
 * Download config options.
 */
data class DownloadConfig(
    val columnGroups: Boolean? = null,
    val rowGroups: Boolean? = null,
    val columnCalcs: Boolean? = null
)

/**
 * An extension function to convert download config class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
fun DownloadConfig.toJs(): Tabulator.DownloadConfig {
    return obj {
        if (columnGroups != null) this.columnGroups = columnGroups
        if (rowGroups != null) this.rowGroups = rowGroups
        if (columnCalcs != null) this.columnCalcs = columnCalcs
    } as Tabulator.DownloadConfig
}

/**
 * Column definition options.
 */
data class ColumnDefinition<T : Any>(
    val title: String,
    val field: String? = null,
    val visible: Boolean? = null,
    val align: Align? = null,
    val width: String? = null,
    val minWidth: Int? = null,
    val widthGrow: Int? = null,
    val widthShrink: Int? = null,
    val resizable: Boolean? = null,
    val frozen: Boolean? = null,
    val responsive: Int? = null,
    val tooltip: ((cell: Tabulator.CellComponent) -> String)? = null,
    val cssClass: String? = null,
    val rowHandle: Boolean? = null,
    val hideInHtml: Boolean? = null,
    val sorter: Sorter? = null,
    val sorterFunction: ((
        a: dynamic, b: dynamic, aRow: Tabulator.RowComponent, bRow: Tabulator.RowComponent,
        column: Tabulator.ColumnComponent, dir: SortingDir, sorterParams: dynamic
    ) -> Number)? = null,
    val sorterParams: dynamic = null,
    val formatter: Formatter? = null,
    val formatterFunction: ((
        cell: Tabulator.CellComponent, formatterParams: dynamic,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> dynamic)? = null,
    val formatterComponentFunction: ((
        cell: Tabulator.CellComponent, onRendered: (callback: () -> Unit) -> Unit, data: T
    ) -> Component)? = null,
    val formatterParams: dynamic = null,
    val variableHeight: Boolean? = null,
    val editable: ((cell: Tabulator.CellComponent) -> Boolean)? = null,
    val editor: Editor? = null,
    val editorFunction: ((
        cell: Tabulator.CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: dynamic) -> Unit, cancel: (value: dynamic) -> Unit, editorParams: dynamic
    ) -> dynamic)? = null,
    val editorComponentFunction: ((
        cell: Tabulator.CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: dynamic) -> Unit, cancel: (value: dynamic) -> Unit, data: T
    ) -> Component)? = null,
    val editorParams: dynamic = null,
    val validator: Validator? = null,
    val validatorFunction: dynamic = null,
    val validatorParams: String? = null,
    val download: Boolean? = null,
    val downloadTitle: String? = null,
    val topCalc: Calc? = null,
    val topCalcParams: dynamic = null,
    val topCalcFormatter: Formatter? = null,
    val topCalcFormatterParams: dynamic = null,
    val bottomCalc: Calc? = null,
    val bottomCalcParams: dynamic = null,
    val bottomCalcFormatter: Formatter? = null,
    val bottomCalcFormatterParams: dynamic = null,
    val headerSort: Boolean? = null,
    val headerSortStartingDir: SortingDir? = null,
    val headerSortTristate: Boolean? = null,
    val headerClick: ((e: dynamic, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerDblClick: ((e: dynamic, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerContext: ((e: dynamic, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerTap: ((e: dynamic, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerDblTap: ((e: dynamic, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerTapHold: ((e: dynamic, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerTooltip: ((column: Tabulator.ColumnComponent) -> String)? = null,
    val headerVertical: Boolean? = null,
    val editableTitle: Boolean? = null,
    val titleFormatter: Formatter? = null,
    val titleFormatterParams: dynamic = null,
    val headerFilter: Editor? = null,
    val headerFilterParams: dynamic = null,
    val headerFilterPlaceholder: String? = null,
    val headerFilterEmptyCheck: ((value: Any) -> Boolean)? = null,
    val headerFilterFunc: Filter? = null,
    val headerFilterFuncParams: dynamic = null,
    val headerFilterLiveFilter: Boolean? = null,
    val cellClick: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellDblClick: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellContext: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellTap: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellDblTap: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellTapHold: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseEnter: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseLeave: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseOver: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseOut: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseMove: ((e: dynamic, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellEditing: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellEdited: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellEditCancelled: ((cell: Tabulator.CellComponent) -> Unit)? = null
)

internal object EditorRoot {
    internal var root: Root? = null
    internal var cancel: ((value: dynamic) -> Unit)? = null
    internal var disposeTimer: Int? = null
}

/**
 * An extension function to convert column definition class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "ComplexMethod", "MagicNumber")
fun <T : Any> ColumnDefinition<T>.toJs(
    i18nTranslator: (String) -> (String)
): Tabulator.ColumnDefinition {
    val tmpEditorFunction = editorComponentFunction?.let {
        { cell: Tabulator.CellComponent,
          onRendered: (callback: () -> Unit) -> Unit,
          success: (value: dynamic) -> Unit, cancel: (value: dynamic) -> Unit, _: dynamic ->
            cell.getElement().style.asDynamic().overflow = "visible"
            var onRenderedCallback: (() -> Unit)? = null
            @Suppress("UNCHECKED_CAST") val data = cell.getData() as T
            val component = it(cell, { callback ->
                onRenderedCallback = callback
            }, { value ->
                success(value)
                disposeTimer = window.setTimeout({
                    root?.dispose()
                    disposeTimer = null
                    root = null
                    EditorRoot.cancel = null
                }, 500)
            }, cancel, data)
            val rootElement = document.createElement("div") as HTMLElement
            onRendered {
                if (root != null) {
                    disposeTimer?.let { window.clearTimeout(it) }
                    root?.dispose()
                }
                root = Root(element = rootElement)
                EditorRoot.cancel = cancel
                @Suppress("UnsafeCastFromDynamic")
                root?.add(component)
                (component as? FormControl)?.focus()
                (component as? FormInput)?.focus()
                cell.checkHeight()
                onRenderedCallback?.invoke()
            }
            rootElement
        }
    }

    val tmpFormatterFunction = formatterComponentFunction?.let {
        { cell: Tabulator.CellComponent, _: dynamic,
          onRendered: (callback: () -> Unit) -> Unit ->
            cell.getElement().style.asDynamic().overflow = "visible"
            var onRenderedCallback: (() -> Unit)? = null
            @Suppress("UNCHECKED_CAST") val data = cell.getData() as T
            val component = it(cell, { callback ->
                onRenderedCallback = callback
            }, data)
            val rootElement = document.createElement("div") as HTMLElement
            onRendered {
                val root = Root(element = rootElement)
                @Suppress("UnsafeCastFromDynamic")
                root.add(component)
                cell.checkHeight()
                onRenderedCallback?.invoke()
            }
            rootElement
        }
    }

    return obj {
        this.title = i18nTranslator(title)
        if (field != null) this.field = field
        if (visible != null) this.visible = visible
        if (align != null) this.align = align.align
        if (width != null) this.width = width
        if (minWidth != null) this.minWidth = minWidth
        if (widthGrow != null) this.widthGrow = widthGrow
        if (widthShrink != null) this.widthShrink = widthShrink
        if (resizable != null) this.resizable = resizable
        if (frozen != null) this.frozen = frozen
        if (responsive != null) this.responsive = responsive
        if (tooltip != null) this.tooltip = tooltip
        if (cssClass != null) this.cssClass = cssClass
        if (rowHandle != null) this.rowHandle = rowHandle
        if (hideInHtml != null) this.hideInHtml = hideInHtml
        if (sorterFunction != null) {
            this.sorter = sorterFunction
        } else if (sorter != null) {
            this.sorter = sorter.sorter
        }
        if (sorterParams != null) this.sorterParams = sorterParams
        when {
            tmpFormatterFunction != null -> this.formatter = tmpFormatterFunction
            formatterFunction != null -> this.formatter = formatterFunction
            formatter != null -> this.formatter = formatter.formatter
        }
        if (formatterParams != null) this.formatterParams = formatterParams
        if (variableHeight != null) this.variableHeight = variableHeight
        if (editable != null) this.editable = editable
        when {
            tmpEditorFunction != null -> this.editor = tmpEditorFunction
            editorFunction != null -> this.editor = editorFunction
            editor != null -> this.editor = editor.editor
        }
        if (editorParams != null) this.editorParams = editorParams
        if (validator != null) this.validator = validator.validator
        if (validatorParams != null) this.validatorParams = validatorParams
        if (download != null) this.download = download
        if (downloadTitle != null) this.downloadTitle = i18nTranslator(downloadTitle)
        if (topCalc != null) this.topCalc = topCalc.calc
        if (topCalcParams != null) this.topCalcParams = topCalcParams
        if (topCalcFormatter != null) this.topCalcFormatter = topCalcFormatter.formatter
        if (topCalcFormatterParams != null) this.topCalcFormatterParams = topCalcFormatterParams
        if (bottomCalc != null) this.bottomCalc = bottomCalc.calc
        if (bottomCalcParams != null) this.bottomCalcParams = bottomCalcParams
        if (bottomCalcFormatter != null) this.bottomCalcFormatter = bottomCalcFormatter.formatter
        if (bottomCalcFormatterParams != null) this.bottomCalcFormatterParams = bottomCalcFormatterParams
        if (headerSort != null) this.headerSort = headerSort
        if (headerSortStartingDir != null) this.headerSortStartingDir = headerSortStartingDir.dir
        if (headerSortTristate != null) this.headerSortTristate = headerSortTristate
        if (headerClick != null) this.headerClick = headerClick
        if (headerDblClick != null) this.headerDblClick = headerDblClick
        if (headerContext != null) this.headerContext = headerContext
        if (headerTap != null) this.headerTap = headerTap
        if (headerDblTap != null) this.headerDblTap = headerDblTap
        if (headerTapHold != null) this.headerTapHold = headerTapHold
        if (headerTooltip != null) this.headerTooltip = headerTooltip
        if (headerVertical != null) this.headerVertical = headerVertical
        if (editableTitle != null) this.editableTitle = editableTitle
        if (titleFormatter != null) this.titleFormatter = titleFormatter.formatter
        if (titleFormatterParams != null) this.titleFormatterParams = titleFormatterParams
        if (headerFilter != null) this.headerFilter = headerFilter.editor
        if (headerFilterParams != null) this.headerFilterParams = headerFilterParams
        if (headerFilterPlaceholder != null) this.headerFilterPlaceholder = i18nTranslator(headerFilterPlaceholder)
        if (headerFilterEmptyCheck != null) this.headerFilterEmptyCheck = headerFilterEmptyCheck
        if (headerFilterFunc != null) this.headerFilterFunc = headerFilterFunc.filter
        if (headerFilterFuncParams != null) this.headerFilterFuncParams = headerFilterFuncParams
        if (headerFilterLiveFilter != null) this.headerFilterLiveFilter = headerFilterLiveFilter
        if (cellClick != null) this.cellClick = cellClick
        if (cellDblClick != null) this.cellDblClick = cellDblClick
        if (cellContext != null) this.cellContext = cellContext
        if (cellTap != null) this.cellTap = cellTap
        if (cellDblTap != null) this.cellDblTap = cellDblTap
        if (cellTapHold != null) this.cellTapHold = cellTapHold
        if (cellMouseEnter != null) this.cellMouseEnter = cellMouseEnter
        if (cellMouseLeave != null) this.cellMouseLeave = cellMouseLeave
        if (cellMouseOver != null) this.cellMouseOver = cellMouseOver
        if (cellMouseOut != null) this.cellMouseOut = cellMouseOut
        if (cellMouseMove != null) this.cellMouseMove = cellMouseMove
        if (cellEditing != null) this.cellEditing = cellEditing
        if (cellEdited != null) this.cellEdited = cellEdited
        if (cellEditCancelled != null) {
            this.cellEditCancelled = cellEditCancelled
        } else if (tmpEditorFunction != null) {
            this.cellEditCancelled = { cell: Tabulator.CellComponent ->
                cell.checkHeight()
            }
        }
    } as Tabulator.ColumnDefinition
}

/**
 * Tabulator options.
 */
data class TabulatorOptions<T : Any>(
    val height: String? = null,
    val virtualDom: Boolean? = null,
    val virtualDomBuffer: Int? = null,
    val placeholder: String? = null,
    val footerElement: String? = null,
    val tooltips: ((cell: Tabulator.CellComponent) -> String)? = null,
    val tooltipGenerationMode: TooltipGenerationMode? = null,
    val history: Boolean? = null,
    val keybindings: dynamic = null,
    val downloadDataFormatter: dynamic = null,
    val downloadConfig: DownloadConfig? = null,
    val reactiveData: Boolean? = null,
    val autoResize: Boolean? = null,
    val columns: List<ColumnDefinition<T>>? = null,
    val autoColumns: Boolean? = null,
    val layout: Layout? = null,
    val layoutColumnsOnNewData: Boolean? = null,
    val responsiveLayout: ResponsiveLayout? = null,
    val responsiveLayoutCollapseStartOpen: Boolean? = null,
    val responsiveLayoutCollapseUseFormatters: Boolean? = null,
    val columnMinWidth: Int? = null,
    val resizableColumns: Boolean? = null,
    val movableColumns: Boolean? = null,
    val tooltipsHeader: Boolean? = null,
    val headerFilterPlaceholder: String? = null,
    val scrollToColumnPosition: ColumnPosition? = null,
    val scrollToColumnIfVisible: Boolean? = null,
    val rowFormatter: ((row: Tabulator.RowComponent) -> Unit)? = null,
    val addRowPos: RowPos? = null,
    val selectable: dynamic = null,
    val selectableRangeMode: RangeMode? = null,
    val selectableRollingSelection: Boolean? = null,
    val selectablePersistence: Boolean? = null,
    val selectableCheck: ((row: Tabulator.RowComponent) -> Boolean)? = null,
    val movableRows: Boolean? = null,
    val movableRowsConnectedTables: dynamic = null,
    val movableRowsSender: dynamic = null,
    val movableRowsReceiver: dynamic = null,
    val resizableRows: Boolean? = null,
    val scrollToRowPosition: RowPosition? = null,
    val scrollToRowIfVisible: Boolean? = null,
    val index: String? = null,
    @Suppress("ArrayInDataClass") var data: Array<T>? = null,
    val ajaxURL: String? = null,
    val ajaxParams: dynamic = null,
    val ajaxConfig: dynamic = null,
    val ajaxContentType: dynamic = null,
    val ajaxURLGenerator: ((url: String, config: dynamic, params: dynamic) -> String)? = null,
    val ajaxRequestFunc: ((url: String, config: dynamic, params: dynamic) -> Promise<Any>)? = null,
    val ajaxFiltering: Boolean? = null,
    val ajaxSorting: Boolean? = null,
    val ajaxProgressiveLoad: ProgressiveMode? = null,
    val ajaxProgressiveLoadDelay: Int? = null,
    val ajaxProgressiveLoadScrollMargin: Int? = null,
    val ajaxLoader: Boolean? = null,
    val ajaxLoaderLoading: String? = null,
    val ajaxLoaderError: String? = null,
    val initialSort: List<Tabulator.Sorter>? = null,
    val sortOrderReverse: Boolean? = null,
    val initialFilter: List<Tabulator.Filter>? = null,
    val initialHeaderFilter: List<Any?>? = null,
    val pagination: PaginationMode? = null,
    val paginationSize: Int? = null,
    val paginationSizeSelector: Boolean? = null,
    val paginationElement: dynamic = null,
    val paginationDataReceived: dynamic = null,
    val paginationDataSent: dynamic = null,
    val paginationAddRow: AddRowMode? = null,
    val paginationButtonCount: Int? = null,
    var persistenceID: String? = null,
    var persistenceMode: Boolean? = null,
    var persistentLayout: Boolean? = null,
    var persistentSort: Boolean? = null,
    var persistentFilter: Boolean? = null,
    val locale: String? = null,
    var langs: dynamic = null,
    val localized: ((locale: String, lang: dynamic) -> Unit)? = null,
    val dataTreeRowExpanded: ((row: Tabulator.RowComponent, level: Number) -> Unit)? = null,
    val dataTreeRowCollapsed: ((row: Tabulator.RowComponent, level: Number) -> Unit)? = null,
    val movableRowsSendingStart: ((toTables: Array<Any>) -> Unit)? = null,
    val movableRowsSent: ((
        fromRow: Tabulator.RowComponent,
        toRow: Tabulator.RowComponent, toTable: Tabulator
    ) -> Unit)? = null,
    val movableRowsSentFailed: ((
        fromRow: Tabulator.RowComponent,
        toRow: Tabulator.RowComponent, toTable: Tabulator
    ) -> Unit)? = null,
    val movableRowsSendingStop: ((toTables: Array<Any>) -> Unit)? = null,
    val movableRowsReceivingStart: ((fromRow: Tabulator.RowComponent, toTable: Tabulator) -> Unit)? = null,
    val movableRowsReceived: ((
        fromRow: Tabulator.RowComponent,
        toRow: Tabulator.RowComponent, fromTable: Tabulator
    ) -> Unit)? = null,
    val movableRowsReceivedFailed: ((
        fromRow: Tabulator.RowComponent,
        toRow: Tabulator.RowComponent, fromTable: Tabulator
    ) -> Unit)? = null,
    val movableRowsReceivingStop: ((fromTable: Tabulator) -> Unit)? = null,
    var rowClick: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowDblClick: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowContext: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowTap: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowDblTap: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowTapHold: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowMouseEnter: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowMouseLeave: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowMouseOver: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowMouseOut: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowMouseMove: ((e: dynamic, row: Tabulator.RowComponent) -> Unit)? = null,
    var rowAdded: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var rowUpdated: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var rowDeleted: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var rowMoved: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var rowResized: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var rowSelectionChanged: ((data: Array<Any>, rows: Array<Tabulator.RowComponent>) -> Unit)? = null,
    var rowSelected: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var rowDeselected: ((row: Tabulator.RowComponent) -> Unit)? = null,
    var cellClick: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellDblClick: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellContext: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellTap: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellDblTap: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellTapHold: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellMouseEnter: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellMouseLeave: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellMouseOver: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellMouseOut: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellMouseMove: ((e: Any, cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellEditing: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellEdited: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    var cellEditCancelled: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    var columnMoved: ((column: Tabulator.ColumnComponent, columns: Array<dynamic>) -> Unit)? = null,
    var columnResized: ((column: Tabulator.ColumnComponent) -> Unit)? = null,
    var columnVisibilityChanged: ((column: Tabulator.ColumnComponent, visible: Boolean) -> Unit)? = null,
    var columnTitleChanged: ((column: Tabulator.ColumnComponent) -> Unit)? = null,
    var tableBuilding: (() -> Unit)? = null,
    var tableBuilt: (() -> Unit)? = null,
    var renderStarted: (() -> Unit)? = null,
    var renderComplete: (() -> Unit)? = null,
    var htmlImporting: (() -> Unit)? = null,
    var htmlImported: (() -> Unit)? = null,
    var dataLoading: ((data: List<T>) -> Unit)? = null,
    var dataLoaded: ((data: List<T>) -> Unit)? = null,
    var dataEdited: ((data: List<T>) -> Unit)? = null,
    var pageLoaded: ((pageno: Int) -> Unit)? = null,
    var dataSorting: ((sorters: Array<Tabulator.Sorter>) -> Unit)? = null,
    var dataSorted: ((sorters: Array<Tabulator.Sorter>, rows: Array<Tabulator.RowComponent>) -> Unit)? = null,
    var dataFiltering: ((filters: Array<Tabulator.Filter>) -> Unit)? = null,
    var dataFiltered: ((filters: Array<Tabulator.Filter>, rows: Array<Tabulator.RowComponent>) -> Unit)? = null,
    var validationFailed: ((cell: Tabulator.CellComponent, value: Any, validators: dynamic) -> Unit)? = null,
    var ajaxRequesting: ((url: String, params: dynamic) -> Boolean)? = null,
    var ajaxResponse: ((url: String, params: dynamic, response: dynamic) -> Any)? = null,
    var ajaxError: ((xhr: dynamic, textStatus: String, errorThrown: dynamic) -> Unit)? = null
)

/**
 * An extension function to convert tabulator options class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "ComplexMethod")
fun <T : Any> TabulatorOptions<T>.toJs(
    i18nTranslator: (String) -> (String)
): Tabulator.Options {
    val tmpCellEditCancelled = this.columns?.find { it.editorComponentFunction != null }?.let {
        { cell: Tabulator.CellComponent ->
            cellEditCancelled?.invoke(cell)
            cell.getTable().redraw(true)
        }
    } ?: cellEditCancelled

    return obj {
        if (height != null) this.height = height
        if (virtualDom != null) this.virtualDom = virtualDom
        if (virtualDomBuffer != null) this.virtualDomBuffer = virtualDomBuffer
        if (placeholder != null) this.placeholder = i18nTranslator(placeholder)
        if (footerElement != null) this.footerElement = i18nTranslator(footerElement)
        if (tooltips != null) this.tooltips = tooltips
        if (tooltipGenerationMode != null) this.tooltipGenerationMode = tooltipGenerationMode.mode
        if (history != null) this.history = history
        if (keybindings != null) this.keybindings = keybindings
        if (downloadDataFormatter != null) this.downloadDataFormatter = downloadDataFormatter
        if (downloadConfig != null) this.downloadConfig = downloadConfig.toJs()
        if (reactiveData != null) this.reactiveData = reactiveData
        if (autoResize != null) this.autoResize = autoResize
        if (columns != null) this.columns = columns.map { it.toJs(i18nTranslator) }.toTypedArray()
        if (autoColumns != null) {
            this.autoColumns = autoColumns
        } else if (columns == null) {
            this.autoColumns = true
        }
        if (layout != null) this.layout = layout.layout
        if (layoutColumnsOnNewData != null) this.layoutColumnsOnNewData = layoutColumnsOnNewData
        if (responsiveLayout != null) this.responsiveLayout = responsiveLayout.layout
        if (responsiveLayoutCollapseStartOpen != null) this.responsiveLayoutCollapseStartOpen =
            responsiveLayoutCollapseStartOpen
        if (responsiveLayoutCollapseUseFormatters != null) this.responsiveLayoutCollapseUseFormatters =
            responsiveLayoutCollapseUseFormatters
        if (columnMinWidth != null) this.columnMinWidth = columnMinWidth
        if (resizableColumns != null) this.resizableColumns = resizableColumns
        if (movableColumns != null) this.movableColumns = movableColumns
        if (tooltipsHeader != null) this.tooltipsHeader = tooltipsHeader
        if (headerFilterPlaceholder != null) this.headerFilterPlaceholder = i18nTranslator(headerFilterPlaceholder)
        if (scrollToColumnPosition != null) this.scrollToColumnPosition = scrollToColumnPosition.position
        if (scrollToColumnIfVisible != null) this.scrollToColumnIfVisible = scrollToColumnIfVisible
        if (rowFormatter != null) this.rowFormatter = rowFormatter
        if (addRowPos != null) this.addRowPos = addRowPos.position
        if (selectable != null) this.selectable = selectable
        if (selectableRangeMode != null) this.selectableRangeMode = selectableRangeMode.mode
        if (selectableRollingSelection != null) this.selectableRollingSelection = selectableRollingSelection
        if (selectablePersistence != null) this.selectablePersistence = selectablePersistence
        if (selectableCheck != null) this.selectableCheck = selectableCheck
        if (movableRows != null) this.movableRows = movableRows
        if (movableRowsConnectedTables != null) this.movableRowsConnectedTables = movableRowsConnectedTables
        if (movableRowsSender != null) this.movableRowsSender = movableRowsSender
        if (movableRowsReceiver != null) this.movableRowsReceiver = movableRowsReceiver
        if (resizableRows != null) this.resizableRows = resizableRows
        if (scrollToRowPosition != null) this.scrollToRowPosition = scrollToRowPosition.position
        if (scrollToRowIfVisible != null) this.scrollToRowIfVisible = scrollToRowIfVisible
        if (index != null) this.index = index
        if (data != null) this.data = data
        if (ajaxURL != null) this.ajaxURL = ajaxURL
        if (ajaxParams != null) this.ajaxParams = ajaxParams
        if (ajaxConfig != null) this.ajaxConfig = ajaxConfig
        if (ajaxContentType != null) this.ajaxContentType = ajaxContentType
        if (ajaxURLGenerator != null) this.ajaxURLGenerator = ajaxURLGenerator
        if (ajaxRequestFunc != null) this.ajaxRequestFunc = ajaxRequestFunc
        if (ajaxFiltering != null) this.ajaxFiltering = ajaxFiltering
        if (ajaxSorting != null) this.ajaxSorting = ajaxSorting
        if (ajaxProgressiveLoad != null) this.ajaxProgressiveLoad = ajaxProgressiveLoad.mode
        if (ajaxProgressiveLoadDelay != null) this.ajaxProgressiveLoadDelay = ajaxProgressiveLoadDelay
        if (ajaxProgressiveLoadScrollMargin != null) this.ajaxProgressiveLoadScrollMargin =
            ajaxProgressiveLoadScrollMargin
        if (ajaxLoader != null) this.ajaxLoader = ajaxLoader
        if (ajaxLoaderLoading != null) this.ajaxLoaderLoading = i18nTranslator(ajaxLoaderLoading)
        if (ajaxLoaderError != null) this.ajaxLoaderError = i18nTranslator(ajaxLoaderError)
        if (initialSort != null) this.initialSort = initialSort
        if (sortOrderReverse != null) this.sortOrderReverse = sortOrderReverse
        if (initialFilter != null) this.initialFilter = initialFilter
        if (initialHeaderFilter != null) this.initialHeaderFilter = initialHeaderFilter
        if (pagination != null) this.pagination = pagination.mode
        if (paginationSize != null) this.paginationSize = paginationSize
        if (paginationSizeSelector != null) this.paginationSizeSelector = paginationSizeSelector
        if (paginationElement != null) this.paginationElement = paginationElement
        if (paginationDataReceived != null) this.paginationDataReceived = paginationDataReceived
        if (paginationDataSent != null) this.paginationDataSent = paginationDataSent
        if (paginationAddRow != null) this.paginationAddRow = paginationAddRow.mode
        if (paginationButtonCount != null) this.paginationButtonCount = paginationButtonCount
        if (persistenceID != null) this.persistenceID = persistenceID
        if (persistenceMode != null) this.persistenceMode = persistenceMode
        if (persistentLayout != null) this.persistentLayout = persistentLayout
        if (persistentSort != null) this.persistentSort = persistentSort
        if (persistentFilter != null) this.persistentFilter = persistentFilter
        if (locale != null) this.locale = locale
        if (langs != null) this.langs = langs
        if (localized != null) this.localized = localized
        if (dataTreeRowExpanded != null) this.dataTreeRowExpanded = dataTreeRowExpanded
        if (dataTreeRowCollapsed != null) this.dataTreeRowCollapsed = dataTreeRowCollapsed
        if (movableRowsSendingStart != null) this.movableRowsSendingStart = movableRowsSendingStart
        if (movableRowsSent != null) this.movableRowsSent = movableRowsSent
        if (movableRowsSentFailed != null) this.movableRowsSentFailed = movableRowsSentFailed
        if (movableRowsSendingStop != null) this.movableRowsSendingStop = movableRowsSendingStop
        if (movableRowsReceivingStart != null) this.movableRowsReceivingStart = movableRowsReceivingStart
        if (movableRowsReceived != null) this.movableRowsReceived = movableRowsReceived
        if (movableRowsReceivedFailed != null) this.movableRowsReceivedFailed = movableRowsReceivedFailed
        if (movableRowsReceivingStop != null) this.movableRowsReceivingStop = movableRowsReceivingStop
        if (rowClick != null) this.rowClick = rowClick
        if (rowDblClick != null) this.rowDblClick = rowDblClick
        if (rowContext != null) this.rowContext = rowContext
        if (rowTap != null) this.rowTap = rowTap
        if (rowDblTap != null) this.rowDblTap = rowDblTap
        if (rowTapHold != null) this.rowTapHold = rowTapHold
        if (rowMouseEnter != null) this.rowMouseEnter = rowMouseEnter
        if (rowMouseLeave != null) this.rowMouseLeave = rowMouseLeave
        if (rowMouseOver != null) this.rowMouseOver = rowMouseOver
        if (rowMouseOut != null) this.rowMouseOut = rowMouseOut
        if (rowMouseMove != null) this.rowMouseMove = rowMouseMove
        if (rowAdded != null) this.rowAdded = rowAdded
        if (rowUpdated != null) this.rowUpdated = rowUpdated
        if (rowDeleted != null) this.rowDeleted = rowDeleted
        if (rowMoved != null) this.rowMoved = rowMoved
        if (rowResized != null) this.rowResized = rowResized
        if (rowSelectionChanged != null) this.rowSelectionChanged = rowSelectionChanged
        if (rowSelected != null) this.rowSelected = rowSelected
        if (rowDeselected != null) this.rowDeselected = rowDeselected
        if (cellClick != null) this.cellClick = cellClick
        if (cellDblClick != null) this.cellDblClick = cellDblClick
        if (cellContext != null) this.cellContext = cellContext
        if (cellTap != null) this.cellTap = cellTap
        if (cellDblTap != null) this.cellDblTap = cellDblTap
        if (cellTapHold != null) this.cellTapHold = cellTapHold
        if (cellMouseEnter != null) this.cellMouseEnter = cellMouseEnter
        if (cellMouseLeave != null) this.cellMouseLeave = cellMouseLeave
        if (cellMouseOver != null) this.cellMouseOver = cellMouseOver
        if (cellMouseOut != null) this.cellMouseOut = cellMouseOut
        if (cellMouseMove != null) this.cellMouseMove = cellMouseMove
        if (cellEditing != null) this.cellEditing = cellEditing
        if (cellEdited != null) this.cellEdited = cellEdited
        if (tmpCellEditCancelled != null) {
            this.cellEditCancelled = tmpCellEditCancelled
        }
        if (columnMoved != null) this.columnMoved = columnMoved
        if (columnResized != null) this.columnResized = columnResized
        if (columnVisibilityChanged != null) this.columnVisibilityChanged = columnVisibilityChanged
        if (columnTitleChanged != null) this.columnTitleChanged = columnTitleChanged
        if (tableBuilding != null) this.tableBuilding = tableBuilding
        if (tableBuilt != null) this.tableBuilt = tableBuilt
        if (renderStarted != null) this.renderStarted = renderStarted
        if (renderComplete != null) this.renderComplete = renderComplete
        if (htmlImporting != null) this.htmlImporting = htmlImporting
        if (htmlImported != null) this.htmlImported = htmlImported
        val dataLoadingFun = dataLoading?.let {
            { data: Array<T> ->
                it(data.toList())
            }
        }
        if (dataLoadingFun != null) this.dataLoading = dataLoadingFun
        val dataLoadedFun = dataLoaded?.let {
            { data: Array<T> ->
                it(data.toList())
            }
        }
        if (dataLoadedFun != null) this.dataLoaded = dataLoadedFun
        val dataEditedFun = dataEdited?.let {
            { data: Array<T> ->
                it(data.toList())
            }
        }
        if (dataEditedFun != null) this.dataEdited = dataEditedFun
        if (pageLoaded != null) this.pageLoaded = pageLoaded
        if (dataSorting != null) this.dataSorting = dataSorting
        if (dataSorted != null) this.dataSorted = dataSorted
        if (dataFiltering != null) this.dataFiltering = dataFiltering
        if (dataFiltered != null) this.dataFiltered = dataFiltered
        if (validationFailed != null) this.validationFailed = validationFailed
        if (ajaxRequesting != null) this.ajaxRequesting = ajaxRequesting
        if (ajaxResponse != null) this.ajaxResponse = ajaxResponse
        if (ajaxError != null) this.ajaxError = ajaxError
    } as Tabulator.Options
}
