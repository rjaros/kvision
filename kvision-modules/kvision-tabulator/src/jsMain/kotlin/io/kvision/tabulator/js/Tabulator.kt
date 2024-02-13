@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
    "unused", "PropertyName", "TooManyFunctions", "VariableNaming", "MaxLineLength"
)

package io.kvision.tabulator.js

import io.kvision.tabulator.js.Tabulator.*
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.UIEvent
import kotlin.js.Promise

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

external interface EventCallBackMethods {
    var validationFailed: (cell: CellComponent, value: Any, validators: Array<Validator>) -> Unit
    var scrollHorizontal: (left: Number) -> Unit
    var scrollVertical: (top: Number) -> Unit
    var rowAdded: (row: RowComponent) -> Unit
    var rowDeleted: (row: RowComponent) -> Unit
    var rowMoved: (row: RowComponent) -> Unit
    var rowUpdated: (row: RowComponent) -> Unit
    var rowSelectionChanged: () -> Unit
    var rowSelected: (row: RowComponent) -> Unit
    var rowDeselected: (row: RowComponent) -> Unit
    var rowResized: (row: RowComponent) -> Unit
    var rowClick: (event: UIEvent, row: RowComponent) -> Unit
    var rowDblClick: (event: UIEvent, row: RowComponent) -> Unit
    var rowContext: (event: UIEvent, row: RowComponent) -> Unit
    var rowTap: (event: UIEvent, row: RowComponent) -> Unit
    var rowDblTap: (event: UIEvent, row: RowComponent) -> Unit
    var rowTapHold: (event: UIEvent, row: RowComponent) -> Unit
    var rowMouseEnter: (event: UIEvent, row: RowComponent) -> Unit
    var rowMouseLeave: (event: UIEvent, row: RowComponent) -> Unit
    var rowMouseOver: (event: UIEvent, row: RowComponent) -> Unit
    var rowMouseOut: (event: UIEvent, row: RowComponent) -> Unit
    var rowMouseMove: (event: UIEvent, row: RowComponent) -> Unit
    var htmlImporting: () -> Unit
    var htmlImported: () -> Unit
    var ajaxError: () -> Unit
    var clipboardCopied: (clipboard: String) -> Unit
    var clipboardPasted: (clipboard: String, rowData: Array<Any>, rows: Array<RowComponent>) -> Unit
    var clipboardPasteError: (clipboard: String) -> Unit
    var downloadComplete: () -> Unit
    var dataTreeRowExpanded: (row: RowComponent, level: Number) -> Unit
    var dataTreeRowCollapsed: (row: RowComponent, level: Number) -> Unit
    var pageLoaded: (pageNo: Number) -> Unit
    var headerClick: (event: UIEvent, column: ColumnComponent) -> Unit
    var headerDblClick: (event: UIEvent, column: ColumnComponent) -> Unit
    var headerContext: (event: UIEvent, column: ColumnComponent) -> Unit
    var headerTap: (event: UIEvent, column: ColumnComponent) -> Unit
    var headerDblTap: (event: UIEvent, column: ColumnComponent) -> Unit
    var headerTapHold: (event: UIEvent, column: ColumnComponent) -> Unit
    var groupClick: (event: UIEvent, group: GroupComponent) -> Unit
    var groupDblClick: (event: UIEvent, group: GroupComponent) -> Unit
    var groupContext: (event: UIEvent, group: GroupComponent) -> Unit
    var groupTap: (event: UIEvent, group: GroupComponent) -> Unit
    var groupDblTap: (event: UIEvent, group: GroupComponent) -> Unit
    var groupTapHold: (event: UIEvent, group: GroupComponent) -> Unit
    var tableBuilding: () -> Unit
    var tableBuilt: () -> Unit
    var dataLoading: (data: Array<Any>) -> Unit
    var dataLoaded: (data: Array<Any>) -> Unit
    var dataChanged: (data: Array<Any>) -> Unit
    var dataFiltering: (filters: Array<Filter>) -> Unit
    var dataFiltered: (filters: Array<Filter>, rows: Array<RowComponent>) -> Unit
    var dataSorting: (sorters: Sorter) -> Unit
    var dataSorted: (sorters: Sorter, rows: Array<RowComponent>) -> Unit
    var movableRowsSendingStart: (toTables: Array<Tabulator>) -> Unit
    var movableRowsSent: (fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Unit
    var movableRowsSentFailed: (fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Unit
    var movableRowsSendingStop: (toTables: Array<Tabulator>) -> Unit
    var movableRowsReceivingStart: (fromRow: RowComponent, fromTable: Tabulator) -> Unit
    var movableRowsReceived: (fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Unit
    var movableRowsReceivedFailed: (fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Unit
    var movableRowsReceivingStop: (fromTable: Tabulator) -> Unit
    var movableRowsElementDrop: (event: UIEvent, element: Element, row: RowComponent) -> Unit
    var dataGrouping: () -> Unit
    var dataGrouped: (groups: Array<GroupComponent>) -> Unit
    var groupVisibilityChanged: (group: GroupComponent, visible: Boolean) -> Unit
    var localized: (locale: String, lang: Any) -> Unit
    var renderStarted: () -> Unit
    var renderComplete: () -> Unit
    var columnMoved: (column: ColumnComponent, columns: Array<ColumnComponent>) -> Unit
    var columnResized: (column: ColumnComponent) -> Unit
    var columnTitleChanged: (column: ColumnComponent) -> Unit
    var columnVisibilityChanged: (column: ColumnComponent, visible: Boolean) -> Unit
    var historyUndo: (action: String /* "cellEdit" | "rowAdd" | "rowDelete" | "rowMoved" */, component: Any, data: Array<Any>) -> Unit
    var historyRedo: (action: String /* "cellEdit" | "rowAdd" | "rowDelete" | "rowMoved" */, component: Any, data: Array<Any>) -> Unit
    var cellEditing: (cell: CellComponent) -> Unit
    var cellEdited: (cell: CellComponent) -> Unit
    var cellEditCancelled: (cell: CellComponent) -> Unit
    var cellClick: (event: UIEvent, cell: CellComponent) -> Unit
    var cellDblClick: (event: UIEvent, cell: CellComponent) -> Unit
    var cellContext: (event: UIEvent, cell: CellComponent) -> Unit
    var cellTap: (event: UIEvent, cell: CellComponent) -> Unit
    var cellDblTap: (event: UIEvent, cell: CellComponent) -> Unit
    var cellTapHold: (event: UIEvent, cell: CellComponent) -> Unit
    var cellMouseEnter: (event: UIEvent, cell: CellComponent) -> Unit
    var cellMouseLeave: (event: UIEvent, cell: CellComponent) -> Unit
    var cellMouseOver: (event: UIEvent, cell: CellComponent) -> Unit
    var cellMouseOut: (event: UIEvent, cell: CellComponent) -> Unit
    var cellMouseMove: (event: UIEvent, cell: CellComponent) -> Unit
    var dataLoadError: (error: Error) -> Unit
    var dataProcessing: () -> Unit
    var dataProcessed: () -> Unit
}

open external class Tabulator {
    constructor(selector: String, options: Options = definedExternally)
    constructor(selector: String)
    constructor(selector: HTMLElement, options: Options = definedExternally)
    constructor(selector: HTMLElement)

    open var columnManager: Any
    open var rowManager: Any
    open var footerManager: Any
    open var browser: String
    open var browserSlow: Boolean
    open var modules: Any
    open var options: Options
    open var element: HTMLElement

    open fun download(
        downloadType: dynamic /* 'csv' | 'json' | 'xlsx' | 'pdf' | 'html' | (columns: Array<Tabulator.ColumnDefinition>, data: Any, options: Any, setFileContents: Any) -> Any */,
        fileName: String?,
        params: DownloadOptions,
        filter: String /* 'visible' | 'active' | 'selected' | 'all' */
    ): Unit = definedExternally

    open fun downloadToTab(
        downloadType: String /* 'csv' | 'json' | 'xlsx' | 'pdf' | 'html' */,
        fileName: String?,
        params: DownloadOptions,
        filter: String? = definedExternally
    ): Unit = definedExternally

    open fun copyToClipboard(rowRangeLookup: String /* 'visible' | 'active' | 'selected' | 'all' */): Unit =
        definedExternally

    open fun undo(): Boolean = definedExternally
    open fun getHistoryUndoSize(): dynamic = definedExternally
    open fun redo(): Boolean = definedExternally
    open fun getHistoryRedoSize(): dynamic = definedExternally
    open fun getEditedCells(): Array<CellComponent> = definedExternally
    open fun clearCellEdited(clear: dynamic /* Tabulator.CellComponent | Array<Tabulator.CellComponent> */): Unit =
        definedExternally

    open fun destroy(): Unit = definedExternally
    open fun import(importer: dynamic, extensions: String): Unit = definedExternally
    open fun setData(data: Any?, params: Any?, config: Any?): Promise<Unit> = definedExternally
    open fun clearData(): Unit = definedExternally
    open fun getData(activeOnly: String? /* 'active' | 'visible' */): Array<Any> = definedExternally
    open fun getDataCount(activeOnly: String? /* 'active' | 'visible' */): Number = definedExternally
    open fun searchRows(
        field: String,
        type: String /* '=' | '!=' | 'like' | '<' | '>' | '<=' | '>=' | 'in' | 'regex' | 'starts' | 'ends' */,
        value: Any
    ): Array<RowComponent> = definedExternally

    open fun searchData(
        field: String,
        type: String /* '=' | '!=' | 'like' | '<' | '>' | '<=' | '>=' | 'in' | 'regex' | 'starts' | 'ends' */,
        value: Any
    ): Array<Any> = definedExternally

    open fun getHtml(
        rowRangeLookup: String /* 'visible' | 'active' | 'selected' | 'all' */,
        style: Boolean,
        config: AddditionalExportOptions
    ): String? = definedExternally

    open fun print(
        rowRangeLookup: String /* 'visible' | 'active' | 'selected' | 'all' */,
        style: Boolean,
        config: AddditionalExportOptions
    ): Unit? = definedExternally

    open fun getAjaxUrl(): String = definedExternally
    open fun replaceData(data: dynamic /* Array<Any> | String */, params: Any?, config: Any?): Promise<Unit> =
        definedExternally

    open fun updateData(data: Array<Any>): Promise<Unit> = definedExternally
    open fun addData(
        data: Array<Any>,
        addToTop: Boolean,
        positionTarget: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */
    ): Promise<RowComponent> = definedExternally

    open fun updateOrAddData(data: Array<Any>): Promise<Array<RowComponent>> = definedExternally
    open fun getRow(row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */): RowComponent =
        definedExternally

    open fun getRowFromPosition(position: Number): RowComponent = definedExternally
    open fun deleteRow(index: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> | Array<dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */> */): Unit =
        definedExternally

    open fun addRow(
        data: Any,
        addToTop: Boolean,
        positionTarget: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */
    ): Promise<RowComponent> = definedExternally

    open fun updateOrAddRow(
        row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */,
        data: Any
    ): Promise<RowComponent> = definedExternally

    open fun updateRow(
        row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */,
        data: Any
    ): Boolean = definedExternally

    open fun scrollToRow(
        row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */,
        position: String? /* 'top' | 'center' | 'bottom' | 'nearest' */,
        ifVisible: Boolean?
    ): Promise<Unit> = definedExternally

    open fun moveRow(
        fromRow: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */,
        toRow: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */,
        placeAboveTarget: Boolean
    ): Unit = definedExternally

    open fun getRows(activeOnly: String /* 'active' | 'visible' */): Array<RowComponent> = definedExternally
    open fun getRowPosition(
        row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */
    ): dynamic = definedExternally

    open fun setColumns(definitions: Array<ColumnDefinition>): Unit = definedExternally
    open fun getColumns(includeColumnGroups: Boolean): Array<ColumnComponent> = definedExternally
    open fun getColumn(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): ColumnComponent =
        definedExternally

    open fun getColumnDefinitions(): Array<ColumnDefinition> = definedExternally
    open fun getColumnLayout(): Array<ColumnLayout> = definedExternally
    open fun setColumnLayout(layout: ColumnLayout): Unit = definedExternally
    open fun showColumn(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): Unit =
        definedExternally

    open fun hideColumn(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): Unit =
        definedExternally

    open fun toggleColumn(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): Unit =
        definedExternally

    open fun addColumn(
        definition: ColumnDefinition,
        insertRightOfTarget: Boolean?,
        positionTarget: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */
    ): Promise<Unit> = definedExternally

    open fun deleteColumn(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): Promise<Unit> =
        definedExternally

    open fun moveColumn(
        fromColumn: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        toColumn: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        after: Boolean
    ): Unit = definedExternally

    open fun scrollToColumn(
        column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        position: String /* 'left' | 'center' | 'middle' | 'right' */,
        ifVisible: Boolean
    ): Promise<Unit> = definedExternally

    open fun updateColumnDefinition(
        column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        definition: ColumnDefinition
    ): Promise<Unit> = definedExternally

    open fun setLocale(locale: dynamic /* String | Boolean */): Unit = definedExternally
    open fun getLocale(): String = definedExternally
    open fun getLang(locale: String): Any = definedExternally
    open fun redraw(force: Boolean): Unit = definedExternally
    open fun blockRedraw(): Unit = definedExternally
    open fun restoreRedraw(): Unit = definedExternally
    open fun setHeight(height: dynamic /* Number | String */): Unit = definedExternally
    open fun setSort(sortList: dynamic /* String | Array<Tabulator.Sorter> */, dir: String /* 'asc' | 'desc' */): Unit =
        definedExternally

    open fun getSorters(): Array<SorterFromTable> = definedExternally
    open fun clearSort(): Unit = definedExternally
    open fun setFilter(
        p1: dynamic /* String | Array<Tabulator.Filter> | Array<Any> | (data: Any, filterParams: Any):Boolean */,
        p2: dynamic /* '=' | '!=' | 'like' | '<' | '>' | '<=' | '>=' | 'in' | 'regex' | 'starts' | 'ends' | Any */,
        value: Any?,
        filterParams: FilterParams? = definedExternally
    ): Unit = definedExternally

    open fun addFilter(): FilterFunction = definedExternally
    open fun getFilters(includeHeaderFilters: Boolean): Array<Filter> = definedExternally
    open fun setHeaderFilterValue(
        column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        value: String
    ): Unit = definedExternally

    open fun setHeaderFilterFocus(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): Unit =
        definedExternally

    open fun getHeaderFilters(): Array<Filter> = definedExternally
    open fun getHeaderFilterValue(column: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */): String =
        definedExternally

    open fun removeFilter(): FilterFunction = definedExternally
    open fun clearFilter(includeHeaderFilters: Boolean): Unit = definedExternally
    open fun clearHeaderFilter(): Unit = definedExternally
    open fun selectRow(lookup: dynamic /* Array<dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */> | 'all' | 'active' | 'visible' | Boolean */): Unit =
        definedExternally

    open fun deselectRow(row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */): Unit =
        definedExternally

    open fun toggleSelectRow(row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */): Unit =
        definedExternally

    open fun getSelectedRows(): Array<RowComponent> = definedExternally
    open fun getSelectedData(): Array<Any> = definedExternally
    open fun setMaxPage(max: Number): Unit = definedExternally
    open fun setPage(page: dynamic /* Number | 'first' | 'prev' | 'next' | 'last' */): Promise<Unit> = definedExternally
    open fun setPageToRow(row: dynamic /* RowComponent | HTMLElement | String | Number | Array<Number> | Array<String> */): Promise<Unit> =
        definedExternally

    open fun setPageSize(size: Number): Unit = definedExternally
    open fun getPageSize(): Number = definedExternally
    open fun previousPage(): Promise<Unit> = definedExternally
    open fun nextPage(): Promise<Unit> = definedExternally
    open fun getPage(): dynamic = definedExternally
    open fun getPageMax(): dynamic = definedExternally
    open fun setGroupBy(groups: dynamic /* String | (data: Any):Any */): Unit = definedExternally
    open fun setGroupStartOpen(values: dynamic /* Boolean | (value: Any, count: Number, data: Any, group: Tabulator.GroupComponent):Boolean */): Unit =
        definedExternally

    open fun setGroupHeader(values: dynamic /* (value: Any, count: Number, data: Any, group: Tabulator.GroupComponent):String | Array<(value: Any, count: Number, data: Any):String> */): Unit =
        definedExternally

    open fun getGroups(): Array<GroupComponent> = definedExternally
    open fun getGroupedData(activeOnly: Boolean): Any = definedExternally
    open fun getCalcResults(): Any = definedExternally
    open fun recalc(): Unit = definedExternally
    open fun navigatePrev(): Unit = definedExternally
    open fun navigateNext(): Unit = definedExternally
    open fun navigateLeft(): Unit = definedExternally
    open fun navigateRight(): Unit = definedExternally
    open fun navigateUp(): Unit = definedExternally
    open fun navigateDown(): Unit = definedExternally
    open fun getInvalidCells(): Array<CellComponent> = definedExternally
    open fun clearCellValidation(clearType: dynamic /* Tabulator.CellComponent | Array<Tabulator.CellComponent> */): Unit =
        definedExternally

    open fun validate(): dynamic = definedExternally
    open fun refreshFilters(): dynamic = definedExternally
    open fun clearHistory(): dynamic = definedExternally

    open fun setGroupValues(data: GroupValuesArg): Unit = definedExternally
    open fun on(event: String, callback: Any): Unit = definedExternally
    open fun off(event: String, callback: Any): Unit = definedExternally

    open fun alert(message: String, style: String = definedExternally): Unit = definedExternally
    open fun clearAlert(): Unit = definedExternally

    open fun addRange(topLeft: dynamic, bottomRight: dynamic): dynamic = definedExternally
    open fun getRanges(): dynamic = definedExternally
    open fun getRangesData(): dynamic = definedExternally

    interface Options : OptionsGeneral, OptionsMenu, OptionsHistory, OptionsLocale, OptionsDownload, OptionsColumns,
        OptionsRows, OptionsData, OptionsSorting, OptionsFiltering, OptionsRowGrouping, OptionsPagination,
        OptionsPersistentConfiguration, OptionsClipboard, OptionsDataTree, OptionsCell, OptionsDebug, OptionsHTML

    interface OptionsDebug {
        var invalidOptionWarning: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var debugInvalidOptions: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsCells : CellCallbacks {
        var validationFailed: ((cell: CellComponent, value: Any, validators: dynamic /* Array<Validator> | Array<String /* "required" | "unique" | "integer" | "float" | "numeric" | "string" */> */) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsDataTree {
        var dataTree: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeElementColumn: dynamic /* Boolean? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeBranchElement: dynamic /* Boolean? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeChildIndent: Number?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeChildField: String?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeCollapseElement: dynamic /* String? | HTMLElement? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeExpandElement: dynamic /* String? | HTMLElement? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeStartExpanded: dynamic /* Boolean? | Array<Boolean>? | ((row: RowComponent, level: Number) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeSelectPropagate: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeFilter: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeSort: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsClipboard {
        var clipboard: dynamic /* Boolean? | "copy" | "paste" */
            get() = definedExternally
            set(value) = definedExternally
        var clipboardCopyRowRange: String? /* "visible" | "active" | "selected" | "all" */
            get() = definedExternally
            set(value) = definedExternally
        var clipboardCopyFormatter: dynamic /* "table" | ((type: String /* "plain" | "html" */, output: String) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var clipboardCopyHeader: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var clipboardPasteParser: dynamic /* String? | ((clipboard: Any) -> Array<Any>)? */
            get() = definedExternally
            set(value) = definedExternally
        var clipboardPasteAction: String? /* "insert" | "update" | "replace" */
            get() = definedExternally
            set(value) = definedExternally
        var clipboardCopyStyled: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var clipboardCopyConfig: dynamic /* AddditionalExportOptions? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var clipboardCopied: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var clipboardPasted: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var clipboardPasteError: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var groupHeaderClipboard: dynamic /* ((value: Any, count: Number, data: Any, group: GroupComponent) -> String)? | Array<(value: Any, count: Number, data: Any) -> String>? */
            get() = definedExternally
            set(value) = definedExternally
        var groupHeaderHtmlOutput: dynamic /* ((value: Any, count: Number, data: Any, group: GroupComponent) -> String)? | Array<(value: Any, count: Number, data: Any) -> String>? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsPersistentConfiguration {
        var persistenceID: String?
            get() = definedExternally
            set(value) = definedExternally
        var persistenceMode: dynamic /* "local" | "cookie" | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var persistentLayout: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var persistentSort: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var persistentFilter: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var persistence: dynamic /* Boolean? | PersistenceOptions? */
            get() = definedExternally
            set(value) = definedExternally
        var persistenceWriterFunc: ((id: String, type: String? /* "sort" | "filter" | "group" | "page" | "columns" */, data: Any) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var persistenceReaderFunc: ((id: String, type: String? /* "sort" | "filter" | "group" | "page" | "columns" */) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface PersistenceOptions {
        var sort: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var filter: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var group: dynamic /* Boolean? | PersistenceGroupOptions? */
            get() = definedExternally
            set(value) = definedExternally
        var page: dynamic /* Boolean? | PersistencePageOptions? */
            get() = definedExternally
            set(value) = definedExternally
        var columns: dynamic /* Boolean? | Array<String>? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface PersistenceGroupOptions {
        var groupBy: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var groupStartOpen: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var groupHeader: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface PersistencePageOptions {
        var size: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var page: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsPagination {
        var pagination: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var paginationMode: String? /* "remote" | "local" */
            get() = definedExternally
            set(value) = definedExternally
        var paginationSize: Number?
            get() = definedExternally
            set(value) = definedExternally
        var paginationSizeSelector: dynamic /* Boolean? | Array<Number>? | Array<Any>? */
            get() = definedExternally
            set(value) = definedExternally
        var paginationElement: dynamic /* HTMLElement? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var dataReceiveParams: dynamic
            get() = definedExternally
            set(value) = definedExternally
        var dataSendParams: dynamic
            get() = definedExternally
            set(value) = definedExternally
        var paginationAddRow: String? /* "table" | "page" */
            get() = definedExternally
            set(value) = definedExternally
        var paginationButtonCount: Number?
            get() = definedExternally
            set(value) = definedExternally
        var paginationInitialPage: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsRowGrouping {
        var groupBy: dynamic /* String? | Array<String>? | ((data: Any) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var groupValues: GroupValuesArg?
            get() = definedExternally
            set(value) = definedExternally
        var groupHeader: dynamic /* ((value: Any, count: Number, data: Any, group: GroupComponent) -> String)? | Array<(value: Any, count: Number, data: Any) -> String>? */
            get() = definedExternally
            set(value) = definedExternally
        var groupHeaderPrint: dynamic /* ((value: Any, count: Number, data: Any, group: GroupComponent) -> String)? | Array<(value: Any, count: Number, data: Any) -> String>? */
            get() = definedExternally
            set(value) = definedExternally
        var groupStartOpen: dynamic /* Boolean? | ((value: Any, count: Number, data: Any, group: GroupComponent) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var groupToggleElement: dynamic /* "arrow" | "header" | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var groupClosedShowCalcs: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataGrouping: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var dataGrouped: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var groupVisibilityChanged: ((group: GroupComponent, visible: Boolean) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var groupClick: GroupEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var groupDblClick: GroupEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var groupContext: GroupEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var groupTap: GroupEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var groupDblTap: GroupEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var groupTapHold: GroupEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var groupUpdateOnCellEdit: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface Filter {
        var field: String
        var type: String /* "=" | "!=" | "like" | "<" | ">" | "<=" | ">=" | "in" | "regex" | "starts" | "ends" */
        var value: Any
    }

    interface FilterParams {
        var separator: String?
            get() = definedExternally
            set(value) = definedExternally
        var matchAll: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsFiltering {
        var initialFilter: Array<Filter>?
            get() = definedExternally
            set(value) = definedExternally
        var initialHeaderFilter: Array<dynamic>?
            get() = definedExternally
            set(value) = definedExternally
        var dataFiltering: ((filters: Array<Filter>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var dataFiltered: ((filters: Array<Filter>, rows: Array<RowComponent>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterLiveFilterDelay: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsSorting {
        var initialSort: Array<Sorter>?
            get() = definedExternally
            set(value) = definedExternally
        var sortOrderReverse: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface Sorter {
        var column: String
        var dir: String /* "asc" | "desc" */
    }

    interface SorterFromTable {
        var column: ColumnComponent
        var field: String
        var dir: String /* "asc" | "desc" */
    }

    interface OptionsData {
        var index: dynamic /* Number? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var data: Array<Any>?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxURL: String?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxParams: Any?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxConfig: dynamic /* "GET" | "POST" | AjaxConfig? */
            get() = definedExternally
            set(value) = definedExternally
        var ajaxContentType: dynamic /* "form" | "json" | AjaxContentType? */
            get() = definedExternally
            set(value) = definedExternally
        var ajaxURLGenerator: ((url: String, config: Any, params: Any) -> String)?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxRequestFunc: ((url: String, config: Any, params: Any) -> Promise<Any>)?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxFiltering: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxSorting: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var progressiveLoad: String? /* "load" | "scroll" */
            get() = definedExternally
            set(value) = definedExternally
        var progressiveLoadDelay: Number?
            get() = definedExternally
            set(value) = definedExternally
        var progressiveLoadScrollMargin: Number?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxLoader: dynamic /* Boolean? | (() -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var ajaxLoaderLoading: String?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxLoaderError: String?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxRequesting: ((url: String, params: Any) -> Boolean)?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxResponse: ((url: String, params: Any, response: Any) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var ajaxError: ((xhr: Any, textStatus: Any, errorThrown: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var dataLoader: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataLoaderLoading: String?
            get() = definedExternally
            set(value) = definedExternally
        var dataLoaderError: String?
            get() = definedExternally
            set(value) = definedExternally
        var sortMode: String? /* "remote" | "local" */
            get() = definedExternally
            set(value) = definedExternally
        var filterMode: String? /* "remote" | "local" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface AjaxContentType {
        var headers: dynamic
        var body: (url: String, config: Any, params: Any) -> Any
    }

    interface AjaxConfig {
        var method: String? /* "GET" | "POST" */
            get() = definedExternally
            set(value) = definedExternally
        var headers: dynamic
            get() = definedExternally
            set(value) = definedExternally
        var mode: String?
            get() = definedExternally
            set(value) = definedExternally
        var credentials: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsRows {
        var rowFormatter: ((row: RowComponent) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var rowFormatterPrint: dynamic /* Boolean? | ((row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var rowFormatterHtmlOutput: dynamic /* Boolean? | ((row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var rowFormatterClipboard: dynamic /* Boolean? | ((row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var addRowPos: String? /* "bottom" | "top" */
            get() = definedExternally
            set(value) = definedExternally
        var selectable: dynamic /* Boolean? | Number? | "highlight" */
            get() = definedExternally
            set(value) = definedExternally
        var selectableRangeMode: String? /* "click" */
            get() = definedExternally
            set(value) = definedExternally
        var selectableRollingSelection: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var selectablePersistence: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var selectableCheck: ((row: RowComponent) -> Boolean)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRows: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsConnectedTables: dynamic /* String? | Array<String>? | HTMLElement? | Array<HTMLElement>? */
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsSender: dynamic /* Boolean? | "delete" | ((fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsReceiver: dynamic /* "insert" | "add" | "update" | "replace" | ((fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsConnectedElements: dynamic /* String? | HTMLElement? */
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsElementDrop: ((e: MouseEvent, element: HTMLElement, row: RowComponent) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var resizableRows: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var scrollToRowPosition: String? /* "top" | "center" | "bottom" | "nearest" */
            get() = definedExternally
            set(value) = definedExternally
        var scrollToRowIfVisible: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeRowExpanded: ((row: RowComponent, level: Number) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var dataTreeRowCollapsed: ((row: RowComponent, level: Number) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsSendingStart: ((toTables: Array<Any>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsSent: ((fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsSentFailed: ((fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsSendingStop: ((toTables: Array<Any>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsReceivingStart: ((fromRow: RowComponent, toTable: Tabulator) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsReceived: ((fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsReceivedFailed: ((fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var movableRowsReceivingStop: ((fromTable: Tabulator) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var rowClick: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowDblClick: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowContext: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowTap: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowDblTap: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowTapHold: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowMouseEnter: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowMouseLeave: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowMouseOver: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowMouseOut: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowMouseMove: RowEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowAdded: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowUpdated: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowDeleted: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowMoved: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowResized: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowSelectionChanged: ((data: Array<Any>, rows: Array<RowComponent>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var rowSelected: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var rowDeselected: RowChangedCallback?
            get() = definedExternally
            set(value) = definedExternally
        var tabEndNewRow: dynamic /* Boolean? | JSONRecord? | ((row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsColumns {
        var columns: Array<ColumnDefinition>?
            get() = definedExternally
            set(value) = definedExternally
        var autoColumns: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var autoColumnsDefinitions: dynamic /* ((columnDefinitions: Array<ColumnDefinition>) -> Array<ColumnDefinition>)? | Array<ColumnDefinition>? | Record<String, ColumnDefinitionPartial>? */
            get() = definedExternally
            set(value) = definedExternally
        var layout: String? /* "fitData" | "fitColumns" | "fitDataFill" | "fitDataStretch" | "fitDataTable" */
            get() = definedExternally
            set(value) = definedExternally
        var layoutColumnsOnNewData: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var responsiveLayout: dynamic /* Boolean? | "hide" | "collapse" */
            get() = definedExternally
            set(value) = definedExternally
        var responsiveLayoutCollapseStartOpen: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var responsiveLayoutCollapseUseFormatters: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var responsiveLayoutCollapseFormatter: ((data: Array<Any>) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var movableColumns: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columnHeaderVertAlign: String? /* "top" | "middle" | "bottom" */
            get() = definedExternally
            set(value) = definedExternally
        var scrollToColumnPosition: String? /* "left" | "center" | "middle" | "right" */
            get() = definedExternally
            set(value) = definedExternally
        var scrollToColumnIfVisible: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columnCalcs: dynamic /* Boolean? | "both" | "table" | "group" */
            get() = definedExternally
            set(value) = definedExternally
        var nestedFieldSeparator: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var columnHeaderSortMulti: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columnMoved: ((column: ColumnComponent, columns: Array<Any>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var columnResized: ((column: ColumnComponent) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var columnVisibilityChanged: ((column: ColumnComponent, visible: Boolean) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var columnTitleChanged: ((column: ColumnComponent) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var headerVisible: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var print: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var headerSort: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var headerSortElement: String?
            get() = definedExternally
            set(value) = definedExternally
        var columnDefaults: ColumnDefinition?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsCell {
        var cellClick: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellDblClick: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellContext: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellTap: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellDblTap: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellTapHold: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseEnter: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseLeave: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseOver: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseOut: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseMove: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEditing: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEdited: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEditCancelled: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsGeneral {
        var height: dynamic /* String? | Number? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var maxHeight: dynamic /* String? | Number? */
            get() = definedExternally
            set(value) = definedExternally
        var minHeight: dynamic /* String? | Number? */
            get() = definedExternally
            set(value) = definedExternally
        var renderVertical: dynamic /* "virtual" | "basic" | Renderer? */
            get() = definedExternally
            set(value) = definedExternally
        var renderHorizontal: dynamic /* "virtual" | "basic" | Renderer? */
            get() = definedExternally
            set(value) = definedExternally
        var renderVerticalBuffer: dynamic /* Boolean? | Number? */
            get() = definedExternally
            set(value) = definedExternally
        var placeholder: dynamic /* String? | HTMLElement? */
            get() = definedExternally
            set(value) = definedExternally
        var footerElement: dynamic /* String? | HTMLElement? */
            get() = definedExternally
            set(value) = definedExternally
        var tooltipGenerationMode: String? /* "load" | "hover" */
            get() = definedExternally
            set(value) = definedExternally
        var keybindings: dynamic /* Boolean? | KeyBinding? */
            get() = definedExternally
            set(value) = definedExternally
        var reactiveData: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var autoResize: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var tableBuilding: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var tableBuilt: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var renderStarted: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var renderComplete: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var htmlImporting: EmptyCallback?
            get() = definedExternally
            set(value) = definedExternally
        var htmlImported: EmptyCallback?
            get() = definedExternally
            set(value) = definedExternally
        var dataChanged: ((data: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var pageLoaded: ((pageno: Number) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var dataSorting: ((sorters: Array<Sorter>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var dataSorted: ((sorters: Array<Sorter>, rows: Array<RowComponent>) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var invalidOptionWarnings: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var scrollVertical: ((top: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var scrollHorizontal: ((left: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var validationMode: String? /* "blocking" | "highlight" | "manual" */
            get() = definedExternally
            set(value) = definedExternally
        var textDirection: String? /* "auto" | "ltr" | "rtl" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsMenu {
        var rowContextMenu: dynamic /* Array<dynamic /* MenuObject<RowComponent> | MenuSeparator */>? | ((component: RowComponent, e: MouseEvent) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var rowClickMenu: dynamic /* Array<dynamic /* MenuObject<RowComponent> | MenuSeparator */>? | ((component: RowComponent, e: MouseEvent) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var groupClickMenu: dynamic /* Array<dynamic /* MenuObject<GroupComponent> | MenuSeparator */>? | ((component: GroupComponent, e: MouseEvent) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var groupContextMenu: Array<MenuObject<GroupComponent>>?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface MenuObject<T> {
        var label: dynamic /* String | HTMLElement | (component: T) -> dynamic */
            get() = definedExternally
            set(value) = definedExternally
        var action: ((e: Any, component: T) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var disabled: dynamic /* Boolean? | ((component: T) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var menu: Array<MenuObject<T>>?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface MenuSeparator {
        var separator: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DownloadOptions : DownloadCSV, DownloadXLXS, DownloadPDF, DownloadHTML {
        override var documentProcessing: ((input: Any) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DownloadCSV {
        var delimiter: String?
            get() = definedExternally
            set(value) = definedExternally
        var bom: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DownloadHTML {
        var style: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DownloadXLXS {
        var sheetName: String?
            get() = definedExternally
            set(value) = definedExternally
        var documentProcessing: ((input: Any) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var writeOptions: dynamic
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DownloadPDF {
        var orientation: String? /* "portrait" | "landscape" */
            get() = definedExternally
            set(value) = definedExternally
        var title: String?
            get() = definedExternally
            set(value) = definedExternally
        var rowGroupStyles: Any?
            get() = definedExternally
            set(value) = definedExternally
        var rowCalcStyles: Any?
            get() = definedExternally
            set(value) = definedExternally
        var jsPDF: Any?
            get() = definedExternally
            set(value) = definedExternally
        var autoTable: dynamic /* Any? | ((doc: Any) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var documentProcessing: ((doc: Any) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsDownload {
        var downloadEncoder: ((fileContents: Any, mimeType: String) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var downloadComplete: (() -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var downloadConfig: AddditionalExportOptions?
            get() = definedExternally
            set(value) = definedExternally
        var downloadRowRange: String? /* "visible" | "active" | "selected" | "all" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsHTML {
        var htmlOutputConfig: AddditionalExportOptions?
            get() = definedExternally
            set(value) = definedExternally
        var printAsHtml: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var printConfig: AddditionalExportOptions?
            get() = definedExternally
            set(value) = definedExternally
        var printStyled: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var printRowRange: dynamic /* "visible" | "active" | "selected" | "all" | (() -> Array<RowComponent>)? */
            get() = definedExternally
            set(value) = definedExternally
        var printHeader: dynamic /* String? | HTMLElement? | (() -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var printFooter: dynamic /* String? | HTMLElement? | (() -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var printFormatter: ((tableHolderElement: Any, tableElement: Any) -> Any)?
            get() = definedExternally
            set(value) = definedExternally
        var groupHeaderDownload: dynamic /* ((value: Any, count: Number, data: Any, group: GroupComponent) -> String)? | Array<(value: Any, count: Number, data: Any) -> String>? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface AddditionalExportOptions {
        var columnHeaders: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columnGroups: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var rowGroups: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columnCalcs: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var dataTree: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var formatCells: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsLocale {
        var locale: dynamic /* Boolean? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var langs: Any?
            get() = definedExternally
            set(value) = definedExternally
        var localized: ((locale: String, lang: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface OptionsHistory {
        var history: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var historyUndo: ((action: String? /* "cellEdit" | "rowAdd" | "rowDelete" | "rowMoved" */, component: dynamic /* CellComponent | RowComponent */, data: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var historyRedo: ((action: String? /* "cellEdit" | "rowAdd" | "rowDelete" | "rowMoved" */, component: dynamic /* CellComponent | RowComponent */, data: Any) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ColumnLayout {
        var title: String
        var field: String?
            get() = definedExternally
            set(value) = definedExternally
        var visible: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var width: dynamic /* Number? | String? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ColumnLayoutPartial {
        var title: String?
            get() = definedExternally
            set(value) = definedExternally
        var field: String?
            get() = definedExternally
            set(value) = definedExternally
        var visible: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var width: dynamic /* Number? | String? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ColumnDefinition : ColumnLayout, CellCallbacks {
        var hozAlign: String? /* "left" | "center" | "right" */
            get() = definedExternally
            set(value) = definedExternally
        var headerHozAlign: String? /* "left" | "center" | "right" */
            get() = definedExternally
            set(value) = definedExternally
        var vertAlign: String? /* "top" | "middle" | "bottom" */
            get() = definedExternally
            set(value) = definedExternally
        var minWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var widthGrow: Number?
            get() = definedExternally
            set(value) = definedExternally
        var widthShrink: Number?
            get() = definedExternally
            set(value) = definedExternally
        var resizable: dynamic /* Boolean? | "header" | "cell" */
            get() = definedExternally
            set(value) = definedExternally
        var frozen: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var responsive: Number?
            get() = definedExternally
            set(value) = definedExternally
        var tooltip: dynamic /* String? | Boolean? | ((cell: CellComponent) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var cssClass: String?
            get() = definedExternally
            set(value) = definedExternally
        var rowHandle: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var hideInHtml: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var sorter: dynamic /* "string" | "number" | "alphanum" | "boolean" | "exists" | "date" | "time" | "datetime" | "array" | ((a: Any, b: Any, aRow: RowComponent, bRow: RowComponent, column: ColumnComponent, dir: String /* "asc" | "desc" */, sorterParams: Any) -> Number)? */
            get() = definedExternally
            set(value) = definedExternally
        var sorterParams: dynamic /* ColumnDefinitionSorterParams? | ColumnSorterParamLookupFunction? */
            get() = definedExternally
            set(value) = definedExternally
        var formatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var variableHeight: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var editable: dynamic /* Boolean? | ((cell: CellComponent) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var editor: dynamic /* Boolean? | "input" | "textarea" | "number" | "range" | "tickCross" | "star" | "select" | "autocomplete" | ((cell: CellComponent, onRendered: EmptyCallback, success: ValueBooleanCallback, cancel: ValueVoidCallback, editorParams: Any) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var editorParams: dynamic /* NumberParams? | CheckboxParams? | SelectParams? | AutoCompleteParams? | InputParams? | TextAreaParams? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var validator: dynamic /* "required" | "unique" | "integer" | "float" | "numeric" | "string" | Array<String /* "required" | "unique" | "integer" | "float" | "numeric" | "string" */>? | Validator? | Array<Validator>? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var mutator: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var mutatorData: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorDataParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var mutatorEdit: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorEditParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var mutatorClipboard: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorClipboardParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessor: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorDownload: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorDownloadParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorClipboard: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorClipboardParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var download: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var titleDownload: String?
            get() = definedExternally
            set(value) = definedExternally
        var topCalc: dynamic /* "avg" | "max" | "min" | "sum" | "concat" | "count" | ((values: Array<Any>, data: Array<Any>, calcParams: Any) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var topCalcParams: ColumnCalcParams?
            get() = definedExternally
            set(value) = definedExternally
        var topCalcFormatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var topCalcFormatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalc: dynamic /* "avg" | "max" | "min" | "sum" | "concat" | "count" | ((values: Array<Any>, data: Array<Any>, calcParams: Any) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalcParams: ColumnCalcParams?
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalcFormatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalcFormatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerSort: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var headerSortStartingDir: String? /* "asc" | "desc" */
            get() = definedExternally
            set(value) = definedExternally
        var headerSortTristate: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var headerClick: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerDblClick: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerContext: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerTap: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerDblTap: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerTapHold: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerTooltip: dynamic /* Boolean? | String? | ((column: ColumnComponent) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerVertical: dynamic /* Boolean? | "flip" */
            get() = definedExternally
            set(value) = definedExternally
        var editableTitle: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var titleFormatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var titleFormatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilter: dynamic /* Boolean? | "input" | "textarea" | "number" | "range" | "tickCross" | "star" | "select" | "autocomplete" | ((cell: CellComponent, onRendered: EmptyCallback, success: ValueBooleanCallback, cancel: ValueVoidCallback, editorParams: Any) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterParams: dynamic /* NumberParams? | CheckboxParams? | SelectParams? | AutoCompleteParams? | InputParams? | TextAreaParams? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterPlaceholder: String?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterEmptyCheck: ValueBooleanCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterFunc: dynamic /* "=" | "!=" | "like" | "<" | ">" | "<=" | ">=" | "in" | "regex" | "starts" | "ends" | ((headerValue: Any, rowValue: Any, rowdata: Any, filterparams: Any) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterFuncParams: Any?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterLiveFilter: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var htmlOutput: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var clipboard: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columns: Array<ColumnDefinition>?
            get() = definedExternally
            set(value) = definedExternally
        var headerMenu: Array<dynamic /* MenuObject<ColumnComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var headerContextMenu: Array<dynamic /* MenuObject<ColumnComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var contextMenu: Array<dynamic /* MenuObject<CellComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var clickMenu: Array<dynamic /* MenuObject<CellComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var formatterClipboard: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterClipboardParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterPrint: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterPrintParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorPrint: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorPrintParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorHtmlOutput: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorHtmlOutputParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterHtmlOutput: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterHtmlOutputParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var titleClipboard: String?
            get() = definedExternally
            set(value) = definedExternally
        var titleHtmlOutput: String?
            get() = definedExternally
            set(value) = definedExternally
        var titlePrint: String?
            get() = definedExternally
            set(value) = definedExternally
        var maxWidth: dynamic /* Number? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ColumnDefinitionPartial : ColumnLayoutPartial, CellCallbacksPartial {
        var hozAlign: String? /* "left" | "center" | "right" */
            get() = definedExternally
            set(value) = definedExternally
        var headerHozAlign: String? /* "left" | "center" | "right" */
            get() = definedExternally
            set(value) = definedExternally
        var vertAlign: String? /* "top" | "middle" | "bottom" */
            get() = definedExternally
            set(value) = definedExternally
        var minWidth: Number?
            get() = definedExternally
            set(value) = definedExternally
        var widthGrow: Number?
            get() = definedExternally
            set(value) = definedExternally
        var widthShrink: Number?
            get() = definedExternally
            set(value) = definedExternally
        var resizable: dynamic /* Boolean? | "header" | "cell" */
            get() = definedExternally
            set(value) = definedExternally
        var frozen: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var responsive: Number?
            get() = definedExternally
            set(value) = definedExternally
        var tooltip: dynamic /* String? | Boolean? | ((cell: CellComponent) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var cssClass: String?
            get() = definedExternally
            set(value) = definedExternally
        var rowHandle: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var hideInHtml: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var sorter: dynamic /* "string" | "number" | "alphanum" | "boolean" | "exists" | "date" | "time" | "datetime" | "array" | ((a: Any, b: Any, aRow: RowComponent, bRow: RowComponent, column: ColumnComponent, dir: String /* "asc" | "desc" */, sorterParams: Any) -> Number)? */
            get() = definedExternally
            set(value) = definedExternally
        var sorterParams: dynamic /* ColumnDefinitionSorterParams? | ColumnSorterParamLookupFunction? */
            get() = definedExternally
            set(value) = definedExternally
        var formatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var variableHeight: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var editable: dynamic /* Boolean? | ((cell: CellComponent) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var editor: dynamic /* Boolean? | "input" | "textarea" | "number" | "range" | "tickCross" | "star" | "select" | "autocomplete" | ((cell: CellComponent, onRendered: EmptyCallback, success: ValueBooleanCallback, cancel: ValueVoidCallback, editorParams: Any) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var editorParams: dynamic /* NumberParams? | CheckboxParams? | SelectParams? | AutoCompleteParams? | InputParams? | TextAreaParams? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var validator: dynamic /* "required" | "unique" | "integer" | "float" | "numeric" | "string" | Array<String /* "required" | "unique" | "integer" | "float" | "numeric" | "string" */>? | Validator? | Array<Validator>? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var mutator: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var mutatorData: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorDataParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var mutatorEdit: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorEditParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var mutatorClipboard: CustomMutator?
            get() = definedExternally
            set(value) = definedExternally
        var mutatorClipboardParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "edit" */, cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessor: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorDownload: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorDownloadParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorClipboard: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorClipboardParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var download: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var titleDownload: String?
            get() = definedExternally
            set(value) = definedExternally
        var topCalc: dynamic /* "avg" | "max" | "min" | "sum" | "concat" | "count" | ((values: Array<Any>, data: Array<Any>, calcParams: Any) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var topCalcParams: ColumnCalcParams?
            get() = definedExternally
            set(value) = definedExternally
        var topCalcFormatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var topCalcFormatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalc: dynamic /* "avg" | "max" | "min" | "sum" | "concat" | "count" | ((values: Array<Any>, data: Array<Any>, calcParams: Any) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalcParams: ColumnCalcParams?
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalcFormatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var bottomCalcFormatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerSort: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var headerSortStartingDir: String? /* "asc" | "desc" */
            get() = definedExternally
            set(value) = definedExternally
        var headerSortTristate: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var headerClick: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerDblClick: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerContext: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerTap: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerDblTap: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerTapHold: ColumnEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerTooltip: dynamic /* Boolean? | String? | ((column: ColumnComponent) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerVertical: dynamic /* Boolean? | "flip" */
            get() = definedExternally
            set(value) = definedExternally
        var editableTitle: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var titleFormatter: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var titleFormatterParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilter: dynamic /* Boolean? | "input" | "textarea" | "number" | "range" | "tickCross" | "star" | "select" | "autocomplete" | ((cell: CellComponent, onRendered: EmptyCallback, success: ValueBooleanCallback, cancel: ValueVoidCallback, editorParams: Any) -> dynamic)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterParams: dynamic /* NumberParams? | CheckboxParams? | SelectParams? | AutoCompleteParams? | InputParams? | TextAreaParams? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterPlaceholder: String?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterEmptyCheck: ValueBooleanCallback?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterFunc: dynamic /* "=" | "!=" | "like" | "<" | ">" | "<=" | ">=" | "in" | "regex" | "starts" | "ends" | ((headerValue: Any, rowValue: Any, rowdata: Any, filterparams: Any) -> Boolean)? */
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterFuncParams: Any?
            get() = definedExternally
            set(value) = definedExternally
        var headerFilterLiveFilter: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var htmlOutput: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var clipboard: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var columns: Array<ColumnDefinition>?
            get() = definedExternally
            set(value) = definedExternally
        var headerMenu: Array<dynamic /* MenuObject<ColumnComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var headerContextMenu: Array<dynamic /* MenuObject<ColumnComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var contextMenu: Array<dynamic /* MenuObject<CellComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var clickMenu: Array<dynamic /* MenuObject<CellComponent> | MenuSeparator */>?
            get() = definedExternally
            set(value) = definedExternally
        var formatterClipboard: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterClipboardParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterPrint: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterPrintParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorPrint: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorPrintParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var accessorHtmlOutput: CustomAccessor?
            get() = definedExternally
            set(value) = definedExternally
        var accessorHtmlOutputParams: dynamic /* Any? | ((value: Any, data: Any, type: String /* "data" | "download" | "clipboard" */, column: ColumnComponent, row: RowComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterHtmlOutput: dynamic /* "plaintext" | "textarea" | "html" | "money" | "image" | "datetime" | "datetimediff" | "link" | "tickCross" | "color" | "star" | "traffic" | "progress" | "lookup" | "buttonTick" | "buttonCross" | "rownum" | "handle" | "rowSelection" | "responsiveCollapse" | ((cell: CellComponent, formatterParams: Any, onRendered: EmptyCallback) -> dynamic)? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var formatterHtmlOutputParams: dynamic /* MoneyParams? | ImageParams? | LinkParams? | DateTimeParams? | DateTimeDifferenceParams? | TickCrossParams? | TrafficParams? | ProgressBarParams? | StarRatingParams? | RowSelectionParams? | JSONRecord? | ((cell: CellComponent) -> Any)? */
            get() = definedExternally
            set(value) = definedExternally
        var titleClipboard: String?
            get() = definedExternally
            set(value) = definedExternally
        var titleHtmlOutput: String?
            get() = definedExternally
            set(value) = definedExternally
        var titlePrint: String?
            get() = definedExternally
            set(value) = definedExternally
        var maxWidth: dynamic /* Number? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface CellCallbacks {
        var cellClick: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellDblClick: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellContext: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellTap: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellDblTap: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellTapHold: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseEnter: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseLeave: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseOver: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseOut: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseMove: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEditing: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEdited: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEditCancelled: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface CellCallbacksPartial {
        var cellClick: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellDblClick: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellContext: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellTap: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellDblTap: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellTapHold: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseEnter: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseLeave: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseOver: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseOut: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellMouseMove: CellEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEditing: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEdited: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
        var cellEditCancelled: CellEditEventCallback?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ColumnDefinitionSorterParams {
        var format: String?
            get() = definedExternally
            set(value) = definedExternally
        var locale: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var alignEmptyValues: String? /* "top" | "bottom" */
            get() = definedExternally
            set(value) = definedExternally
        var type: String? /* "length" | "sum" | "max" | "min" | "avg" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface MoneyParams {
        var decimal: String?
            get() = definedExternally
            set(value) = definedExternally
        var thousand: String?
            get() = definedExternally
            set(value) = definedExternally
        var symbol: String?
            get() = definedExternally
            set(value) = definedExternally
        var symbolAfter: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var precision: dynamic /* Boolean? | Number? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ImageParams {
        var height: String?
            get() = definedExternally
            set(value) = definedExternally
        var width: String?
            get() = definedExternally
            set(value) = definedExternally
        var urlPrefix: String?
            get() = definedExternally
            set(value) = definedExternally
        var urlSuffix: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface LinkParams {
        var labelField: String?
            get() = definedExternally
            set(value) = definedExternally
        var label: dynamic /* String? | ((cell: CellComponent) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var urlPrefix: String?
            get() = definedExternally
            set(value) = definedExternally
        var urlField: String?
            get() = definedExternally
            set(value) = definedExternally
        var url: dynamic /* String? | ((cell: CellComponent) -> String)? */
            get() = definedExternally
            set(value) = definedExternally
        var target: String?
            get() = definedExternally
            set(value) = definedExternally
        var download: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DateTimeParams {
        var inputFormat: String?
            get() = definedExternally
            set(value) = definedExternally
        var outputFormat: String?
            get() = definedExternally
            set(value) = definedExternally
        var invalidPlaceholder: dynamic /* Boolean? | String? | Number? | ValueStringCallback? */
            get() = definedExternally
            set(value) = definedExternally
        var timezone: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface DateTimeDifferenceParams : DateTimeParams {
        var date: Any?
            get() = definedExternally
            set(value) = definedExternally
        var humanize: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var unit: String? /* "years" | "months" | "weeks" | "days" | "hours" | "minutes" | "seconds" */
            get() = definedExternally
            set(value) = definedExternally
        var suffix: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface TickCrossParams {
        var allowEmpty: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var allowTruthy: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var tickElement: dynamic /* Boolean? | String? */
            get() = definedExternally
            set(value) = definedExternally
        var crossElement: dynamic /* Boolean? | String? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface TrafficParams {
        var min: Number?
            get() = definedExternally
            set(value) = definedExternally
        var max: Number?
            get() = definedExternally
            set(value) = definedExternally
        var color: dynamic /* String? | Array<Any>? | ValueStringCallback? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface ProgressBarParams : TrafficParams {
        var legend: dynamic /* String? | Boolean? | ValueStringCallback? */
            get() = definedExternally
            set(value) = definedExternally
        var legendColor: dynamic /* String? | Array<Any>? | ValueStringCallback? */
            get() = definedExternally
            set(value) = definedExternally
        var legendAlign: String? /* "center" | "left" | "right" | "justify" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface StarRatingParams {
        var stars: Number?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface RowSelectionParams {
        var rowRange: String? /* "visible" | "active" | "selected" | "all" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface SharedEditorParams {
        var elementAttributes: dynamic
            get() = definedExternally
            set(value) = definedExternally
        var mask: String?
            get() = definedExternally
            set(value) = definedExternally
        var maskAutoFill: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var maskLetterChar: String?
            get() = definedExternally
            set(value) = definedExternally
        var maskNumberChar: String?
            get() = definedExternally
            set(value) = definedExternally
        var maskWildcardChar: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface NumberParams : SharedEditorParams {
        var min: Number?
            get() = definedExternally
            set(value) = definedExternally
        var max: Number?
            get() = definedExternally
            set(value) = definedExternally
        var step: Number?
            get() = definedExternally
            set(value) = definedExternally
        var verticalNavigation: String? /* "editor" | "table" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface InputParams : SharedEditorParams {
        var search: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface TextAreaParams : SharedEditorParams {
        var verticalNavigation: String? /* "editor" | "table" | "hybrid" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface CheckboxParams : SharedEditorParams {
        var tristate: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var indeterminateValue: String?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface SharedSelectAutoCompleteEditorParams {
        var defaultValue: String?
            get() = definedExternally
            set(value) = definedExternally
        var sortValuesList: String? /* "asc" | "desc" */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface SelectParams : SharedEditorParams, SharedSelectAutoCompleteEditorParams {
        var values: dynamic /* Boolean | Array<String> | JSONRecord | Array<SelectParamsGroup> | String */
            get() = definedExternally
            set(value) = definedExternally
        var listItemFormatter: ((value: String, text: String) -> String)?
            get() = definedExternally
            set(value) = definedExternally
        var verticalNavigation: String? /* "editor" | "table" | "hybrid" */
            get() = definedExternally
            set(value) = definedExternally
        var multiselect: dynamic /* Boolean? | Number? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface SelectParamsGroup {
        var label: String
        var value: dynamic /* String? | Number? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var options: Array<SelectLabelValue>?
            get() = definedExternally
            set(value) = definedExternally
        var elementAttributes: Any?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface SelectLabelValue {
        var label: String
        var value: dynamic /* String | Number | Boolean */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface AutoCompleteParams : SharedEditorParams, SharedSelectAutoCompleteEditorParams {
        var values: dynamic /* Boolean | Array<String> | JSONRecord | String | Array<Any> */
            get() = definedExternally
            set(value) = definedExternally
        var listItemFormatter: ((value: String, text: String) -> String)?
            get() = definedExternally
            set(value) = definedExternally
        var searchFunc: ((term: String, values: Array<String>) -> dynamic)?
            get() = definedExternally
            set(value) = definedExternally
        var allowEmpty: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var freetext: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var showListOnEmpty: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var verticalNavigation: String? /* "editor" | "table" | "hybrid" */
            get() = definedExternally
            set(value) = definedExternally
        var searchingPlaceholder: dynamic /* String? | HTMLElement? */
            get() = definedExternally
            set(value) = definedExternally
        var emptyPlaceholder: dynamic /* String? | HTMLElement? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface Validator {
        var type: dynamic /* "required" | "unique" | "integer" | "float" | "numeric" | "string" | (cell: CellComponent, value: Any, parameters: Any) -> Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var parameters: Any?
            get() = definedExternally
            set(value) = definedExternally
    }

    interface KeyBinding {
        var navPrev: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var navNext: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var navLeft: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var navRight: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var navUp: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var navDown: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var undo: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var redo: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var scrollPageUp: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var scrollPageDown: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var scrollToStart: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var scrollToEnd: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
        var copyToClipboard: dynamic /* String? | Boolean? */
            get() = definedExternally
            set(value) = definedExternally
    }

    interface CalculationComponent {
        var getData: () -> Any
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getCells: () -> Array<CellComponent>
        var getCell: (column: dynamic /* ColumnComponent | HTMLElement | String */) -> CellComponent
    }

    interface RowComponent : CalculationComponent {
        var getNextRow: () -> dynamic
        var getPrevRow: () -> dynamic
        var getIndex: () -> Any
        var getPosition: () -> dynamic
        var getGroup: () -> GroupComponent
        var delete: () -> Promise<Unit>
        var scrollTo: () -> Promise<Unit>
        var pageTo: () -> Promise<Unit>
        var move: (lookup: dynamic /* RowComponent | HTMLElement | Number */, belowTarget: Boolean) -> Unit
        var update: (data: Any) -> Promise<Unit>
        var select: () -> Unit
        var deselect: () -> Unit
        var toggleSelect: () -> Unit
        var isSelected: () -> Boolean
        var normalizeHeight: () -> Unit
        var reformat: () -> Unit
        var freeze: () -> Unit
        var unfreeze: () -> Unit
        var treeExpand: () -> Unit
        var treeCollapse: () -> Unit
        var treeToggle: () -> Unit
        var getTreeParent: () -> dynamic
        var getTreeChildren: () -> Array<RowComponent>
        var addTreeChild: (rowData: Any, position: Boolean, existingRow: RowComponent) -> Unit
        var validate: () -> dynamic
        var isFrozen: () -> Boolean
        var isTreeExpanded: () -> Boolean
    }

    interface GroupComponent {
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getKey: () -> Any
        var getField: () -> String
        var getRows: () -> Array<RowComponent>
        var getSubGroups: () -> Array<GroupComponent>
        var getParentGroup: () -> dynamic
        var isVisible: () -> Boolean
        var show: () -> Unit
        var hide: () -> Unit
        var toggle: () -> Unit
    }

    interface ColumnComponent {
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getDefinition: () -> ColumnDefinition
        var getField: () -> String
        var getCells: () -> Array<CellComponent>
        var getNextColumn: () -> dynamic
        var getPrevColumn: () -> dynamic
        var move: (toColumn: dynamic /* ColumnComponent | ColumnDefinition | HTMLElement | String */, after: Boolean) -> Unit
        var isVisible: () -> Boolean
        var show: () -> Unit
        var hide: () -> Unit
        var toggle: () -> Unit
        var delete: () -> Promise<Unit>
        var scrollTo: () -> Promise<Unit>
        var getSubColumns: () -> Array<ColumnComponent>
        var getParentColumn: () -> dynamic
        var headerFilterFocus: () -> Unit
        var setHeaderFilterValue: (value: Any) -> Unit
        var reloadHeaderFilter: () -> Unit
        var getHeaderFilterValue: () -> Any
        var updateDefinition: (definition: ColumnDefinition) -> Promise<Unit>
        var getWidth: () -> Number
        var setWidth: (width: dynamic /* Number | Boolean */) -> Unit
        var validate: () -> dynamic
    }

    interface CellComponent {
        var getValue: () -> Any?
        var getOldValue: () -> Any?
        var restoreOldValue: () -> Any?
        var getInitialValue: () -> Any?
        var restoreInitialValue: () -> Any?
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getRow: () -> RowComponent
        var getColumn: () -> ColumnComponent
        var getData: () -> Any
        var getField: () -> String
        var setValue: (value: Any, mutate: Boolean) -> Unit
        var checkHeight: () -> Unit
        var edit: (ignoreEditable: Boolean) -> Unit
        var cancelEdit: () -> Unit
        var navigatePrev: () -> Boolean
        var navigateNext: () -> Boolean
        var navigateLeft: () -> Boolean
        var navigateRight: () -> Boolean
        var navigateUp: () -> Unit
        var navigateDown: () -> Unit
        var isEdited: () -> Boolean
        var clearEdited: () -> Unit
        var isValid: () -> dynamic
        var clearValidation: () -> Unit
        var validate: () -> Boolean
        var getType: () -> String
    }

    companion object {
        var defaultOptions: Options
        var extendModule: (name: String, property: String, values: Any) -> Unit
        var findTable: (query: String) -> Array<Tabulator>
        var registerModule: (module: Module) -> Unit
        var bindModules: (__0: Any) -> Unit
    }
}

open external class Module(table: Tabulator) {
    companion object {
        var moduleName: String
    }
}

open external class AccessorModule

open external class AjaxModule

open external class ClipboardModule

open external class ColumnCalcsModule

open external class DataTreeModule

open external class DownloadModule

open external class EditModule

open external class ExportModule

open external class FilterModule

open external class FormatModule

open external class FrozenColumnsModule

open external class FrozenRowsModule

open external class GroupRowsModule

open external class HistoryModule

open external class HtmlTableImportModule

open external class InteractionModule

open external class KeybindingsModule

open external class MenuModule

open external class MoveColumnsModule

open external class MoveRowsModule

open external class MutatorModule

open external class PageModule

open external class PersistenceModule

open external class PrintModule

open external class PseudoRow

open external class ReactiveDataModule

open external class Renderer

open external class ResizeColumnsModule

open external class ResizeRowsModule

open external class ResizeTableModule

open external class ResponsiveLayoutModule

open external class SelectRowModule

open external class SortModule

open external class TabulatorFull

open external class ValidateModule
