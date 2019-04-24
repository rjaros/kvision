@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
    "unused", "PropertyName", "TooManyFunctions", "VariableNaming", "MaxLineLength"
)

package pl.treksoft.kvision.tabulator.js

import org.w3c.dom.HTMLElement
import kotlin.js.Promise

@Suppress("UNREACHABLE_CODE", "LargeClass")
open external class Tabulator {
    constructor(selector: String, options: Options? = definedExternally /* null */)
    constructor(selector: HTMLElement, options: Options? = definedExternally /* null */)

    open var columnManager: Any = definedExternally
    open var rowManager: Any = definedExternally
    open var footerManager: Any = definedExternally
    open var browser: String = definedExternally
    open var browserSlow: Boolean = definedExternally
    open var modules: Any = definedExternally
    open var options: Options = definedExternally
    open fun download(
        downloadType: dynamic /* String /* "json" */ | String /* "csv" */ | String /* "xlsx" */ | String /* "pdf" */ | (columns: Array<Tabulator.ColumnDefinition>, data: Any, options: Any, setFileContents: Any) -> Any */,
        fileName: String,
        params: DownloadOptions? /*= null*/
    ): Unit =
        definedExternally

    open fun downloadToTab(
        downloadType: dynamic /* String /* "json" */ | String /* "csv" */ | String /* "xlsx" */ | String /* "pdf" */ */,
        fileName: String,
        params: DownloadOptions? /*= null*/
    ): Unit =
        definedExternally

    open fun copyToClipboard(type: dynamic /* String /* "table" */ | String /* "selection" */ */): Unit =
        definedExternally

    open fun undo(): Boolean = definedExternally
    open fun getHistoryUndoSize(): dynamic /* Number | Boolean */ = definedExternally
    open fun redo(): Boolean = definedExternally
    open fun getHistoryRedoSize(): dynamic /* Number | Boolean */ = definedExternally
    open fun destroy(): Unit = definedExternally
    open fun setDataFromLocalFile(extensions: String): Unit = definedExternally
    open fun setData(data: dynamic, params: Any? /*= null*/, config: Any? /*= null*/): Promise<Unit> = definedExternally
    open fun clearData(): Unit = definedExternally
    open fun getData(activeOnly: Boolean? /*= null*/): Array<Any> = definedExternally
    open fun getDataCount(activeOnly: Boolean? /*= null*/): Number = definedExternally
    open fun searchRows(
        field: String,
        type: dynamic /* String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ */,
        value: Any
    ): Array<RowComponent> =
        definedExternally

    open fun searchData(
        field: String,
        type: dynamic /* String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ */,
        value: Any
    ): Array<Any> =
        definedExternally

    open fun getHtml(activeOnly: Boolean? /*= null*/): String = definedExternally
    open fun getAjaxUrl(): String = definedExternally
    open fun replaceData(
        data: dynamic /* String | Array<Any?> */ /*= null*/,
        params: Any? /*= null*/,
        config: Any? /*= null*/
    ): Promise<Unit> =
        definedExternally

    open fun updateData(data: Array<Any>): Promise<Unit> = definedExternally
    open fun addData(
        data: Array<Any>? /*= null*/,
        addToTop: Boolean? /*= null*/,
        positionTarget: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */ /*= null*/
    ): Promise<RowComponent> =
        definedExternally

    open fun updateOrAddData(data: Array<Any>): Promise<Array<RowComponent>> = definedExternally
    open fun getRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */): RowComponent =
        definedExternally

    open fun getRowFromPosition(position: Number, activeOnly: Boolean? /*= null*/): Unit = definedExternally
    open fun deleteRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */): Unit =
        definedExternally

    open fun addRow(
        data: Any? /*= null*/,
        addToTop: Boolean? /*= null*/,
        positionTarget: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */ /*= null*/
    ): Promise<RowComponent> =
        definedExternally

    open fun updateOrAddRow(
        row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */,
        data: Any
    ): Promise<RowComponent> =
        definedExternally

    open fun updateRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */, data: Any): Boolean =
        definedExternally

    open fun scrollToRow(
        row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */,
        position: dynamic /* String /* "bottom" */ | String /* "top" */ | String /* "center" */ | String /* "nearest" */ */ /*= null*/,
        ifVisible: Boolean? /*= null*/
    ): Promise<Unit> =
        definedExternally

    open fun moveRow(
        fromRow: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */,
        toRow: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */,
        placeAboveTarget: Boolean? /*= null*/
    ): Unit =
        definedExternally

    open fun getRows(activeOnly: Boolean? /*= null*/): Array<RowComponent> = definedExternally
    open fun getRowPosition(
        row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */,
        activeOnly: Boolean? /*= null*/
    ): Number =
        definedExternally

    open fun setColumns(definitions: Array<ColumnDefinition>): Unit = definedExternally
    open fun getColumns(includeColumnGroups: Boolean? /*= null*/): dynamic /* Array<Tabulator.GroupComponent> | Array<Tabulator.ColumnComponent> */ =
        definedExternally

    open fun getColumn(column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */): ColumnComponent =
        definedExternally

    open fun getColumnDefinitions(): Array<ColumnDefinition> = definedExternally
    open fun getColumnLayout(): Array<ColumnLayout> = definedExternally
    open fun setColumnLayout(layout: ColumnLayout): Unit = definedExternally
    open fun showColumn(column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */ /*= null*/): Unit =
        definedExternally

    open fun hideColumn(column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */ /*= null*/): Unit =
        definedExternally

    open fun toggleColumn(column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */ /*= null*/): Unit =
        definedExternally

    open fun addColumn(
        definition: ColumnDefinition,
        insertRightOfTarget: Boolean? /*= null*/,
        positionTarget: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */ /*= null*/
    ): Unit =
        definedExternally

    open fun deleteColumn(column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */): Unit =
        definedExternally

    open fun scrollToColumn(
        column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */,
        position: dynamic /* String /* "center" */ | String /* "middle" */ | String /* "left" */ | String /* "right" */ */ /*= null*/,
        ifVisible: Boolean? /*= null*/
    ): Promise<Unit> =
        definedExternally

    open fun setLocale(locale: dynamic /* String | Boolean */): Unit = definedExternally
    open fun getLocale(): String = definedExternally
    open fun getLang(locale: String? /*= null*/): Any = definedExternally
    open fun redraw(force: Boolean? /*= null*/): Unit = definedExternally
    open fun setHeight(height: Number): Unit = definedExternally
    open fun setSort(
        sortList: dynamic /* String | Array<Tabulator.Sorter> */,
        dir: dynamic /* String /* "asc" */ | String /* "desc" */ */ /*= null*/
    ): Unit =
        definedExternally

    open fun getSorters(): Unit = definedExternally
    open fun clearSort(): Unit = definedExternally
    open fun setFilter(
        p1: dynamic /* String | Array<Any> | Array<Tabulator.Filter> | (data: Any, filterParams: Any) -> Boolean */,
        p2: dynamic /* Any? | String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ */ /*= null*/,
        value: Any? /*= null*/
    ): Unit =
        definedExternally

    open fun addFilter(
        field: String,
        type: dynamic /* String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ */,
        value: Any
    ): Unit =
        definedExternally

    open fun getFilters(includeHeaderFilters: Boolean): Array<Filter> = definedExternally
    open fun setHeaderFilterValue(
        column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */,
        value: String
    ): Unit =
        definedExternally

    open fun setHeaderFilterFocus(column: dynamic /* String | HTMLElement | Tabulator.ColumnDefinition | Tabulator.ColumnComponent */): Unit =
        definedExternally

    open fun getHeaderFilters(): Array<Filter> = definedExternally
    open fun removeFilter(
        field: String,
        type: dynamic /* String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ */,
        value: Any
    ): Unit =
        definedExternally

    open fun clearFilter(includeHeaderFilters: Boolean): Unit = definedExternally
    open fun clearHeaderFilter(): Unit = definedExternally
    open fun selectRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */ /*= null*/): Unit =
        definedExternally

    open fun deselectRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */ /*= null*/): Unit =
        definedExternally

    open fun toggleSelectRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */ /*= null*/): Unit =
        definedExternally

    open fun getSelectedRows(): Array<RowComponent> = definedExternally
    open fun getSelectedData(): Array<Any> = definedExternally
    open fun setMaxPage(max: Number): Unit = definedExternally
    open fun setPage(page: dynamic /* Number | String /* "first" */ | String /* "prev" */ | String /* "next" */ | String /* "last" */ */): Promise<Unit> =
        definedExternally

    open fun setPageToRow(row: dynamic /* String | Number | HTMLElement | Tabulator.RowComponent */): Promise<Unit> =
        definedExternally

    open fun setPageSize(size: Number): Unit = definedExternally
    open fun getPageSize(): Number = definedExternally
    open fun previousPage(): Promise<Unit> = definedExternally
    open fun nextPage(): Promise<Unit> = definedExternally
    open fun getPage(): dynamic /* Number | Boolean */ = definedExternally
    open fun getPageMax(): dynamic /* Number | Boolean */ = definedExternally
    open fun setGroupBy(groups: dynamic /* String | (data: Any) -> Any */): Unit = definedExternally
    open fun setGroupStartOpen(values: dynamic /* Boolean | (value: Any, count: Number, data: Any, group: Tabulator.GroupComponent) -> Boolean */): Unit =
        definedExternally

    open fun setGroupHeader(values: dynamic /* (value: Any, count: Number, data: Any, group: Tabulator.GroupComponent) -> String | Array<(value: Any, count: Number, data: Any): String> */): Unit =
        definedExternally

    open fun getGroups(): Array<GroupComponent> = definedExternally
    open fun getGroupedData(activeOnly: Boolean? /*= null*/): Any = definedExternally
    open fun getCalcResults(): Any = definedExternally
    open fun navigatePrev(): Unit = definedExternally
    open fun navigateNext(): Unit = definedExternally
    open fun navigateLeft(): Unit = definedExternally
    open fun navigateRight(): Unit = definedExternally
    open fun navigateUp(): Unit = definedExternally
    open fun navigateDown(): Unit = definedExternally
    open fun extendModule(name: String, property: String, values: Any): Unit = definedExternally

    interface Options : OptionsGeneral, OptionsHistory, OptionsLocale, OptionsDownload, OptionsColumns, OptionsRows,
        OptionsData, OptionsSorting, OptionsFiltering, OptionsRowGrouping, OptionsPagination,
        OptionsPersistentConfiguration, OptionsClipboard, OptionsDataTree, OptionsCell

    interface OptionsCells : CellCallbacks {
        var validationFailed: ((cell: CellComponent, value: Any, validators: dynamic /* Array<Validator> | Array<dynamic /* String /* "string" */ | String /* "required" */ | String /* "unique" */ | String /* "integer" */ | String /* "float" */ | String /* "numeric" */ */> */) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsDataTree {
        var dataTree: Boolean? get() = definedExternally; set(value) = definedExternally
        var dataTreeElementColumn: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var dataTreeBranchElement: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var dataTreeChildIndent: Number? get() = definedExternally; set(value) = definedExternally
        var dataTreeChildField: String? get() = definedExternally; set(value) = definedExternally
        var dataTreeCollapseElement: dynamic /* String | Boolean | HTMLElement */ get() = definedExternally; set(value) = definedExternally
        var dataTreeExpandElement: dynamic /* String | Boolean | HTMLElement */ get() = definedExternally; set(value) = definedExternally
        var dataTreeStartExpanded: dynamic /* Boolean | Array<Boolean> | (row: RowComponent, level: Number) -> Boolean */ get() = definedExternally; set(value) = definedExternally
    }

    interface ClipboardConfig {
        var columnHeaders: Boolean? get() = definedExternally; set(value) = definedExternally
        var rowGroups: Boolean? get() = definedExternally; set(value) = definedExternally
        var columnCalcs: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsClipboard {
        var clipboard: dynamic /* Boolean | String /* "copy" */ | String /* "paste" */ */ get() = definedExternally; set(value) = definedExternally
        var clipboardCopySelector: dynamic /* String /* "active" */ | String /* "table" */ | String /* "selected" */ */ get() = definedExternally; set(value) = definedExternally
        var clipboardCopyFormatter: dynamic /* String /* "table" */ | (rowData: Array<Any>) -> String */ get() = definedExternally; set(value) = definedExternally
        var clipboardCopyHeader: Boolean? get() = definedExternally; set(value) = definedExternally
        var clipboardPasteParser: dynamic /* String | (clipboard: Any) -> Array<Any> */ get() = definedExternally; set(value) = definedExternally
        var clipboardPasteAction: dynamic /* String /* "insert" */ | String /* "update" */ | String /* "replace" */ */ get() = definedExternally; set(value) = definedExternally
        var clipboardCopyStyled: Boolean? get() = definedExternally; set(value) = definedExternally
        var clipboardCopyConfig: dynamic /* Boolean | ClipboardConfig */ get() = definedExternally; set(value) = definedExternally
        var clipboardCopied: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var clipboardPasted: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var clipboardPasteError: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsPersistentConfiguration {
        var persistenceID: String? get() = definedExternally; set(value) = definedExternally
        var persistenceMode: dynamic /* Boolean | String /* "local" */ | String /* "cookie" */ */ get() = definedExternally; set(value) = definedExternally
        var persistentLayout: Boolean? get() = definedExternally; set(value) = definedExternally
        var persistentSort: Boolean? get() = definedExternally; set(value) = definedExternally
        var persistentFilter: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsPagination {
        var pagination: dynamic /* String /* "local" */ | String /* "remote" */ */ get() = definedExternally; set(value) = definedExternally
        var paginationSize: Number? get() = definedExternally; set(value) = definedExternally
        var paginationSizeSelector: dynamic /* Boolean | Array<Number> */ get() = definedExternally; set(value) = definedExternally
        var paginationElement: dynamic /* String /* "string" */ | HTMLElement */ get() = definedExternally; set(value) = definedExternally
        var paginationDataReceived: Any? get() = definedExternally; set(value) = definedExternally
        var paginationDataSent: Any? get() = definedExternally; set(value) = definedExternally
        var paginationAddRow: dynamic /* String /* "table" */ | String /* "page" */ */ get() = definedExternally; set(value) = definedExternally
        var paginationButtonCount: Number? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsRowGrouping {
        var groupBy: dynamic /* String | (data: Any) -> Any */ get() = definedExternally; set(value) = definedExternally
        var groupValues: Array<Array<Any>>? get() = definedExternally; set(value) = definedExternally
        var groupHeader: dynamic /* (value: Any, count: Number, data: Any, group: GroupComponent) -> String | Array<(value: Any, count: Number, data: Any) -> String> */ get() = definedExternally; set(value) = definedExternally
        var groupStartOpen: dynamic /* Boolean | (value: Any, count: Number, data: Any, group: GroupComponent) -> Boolean */ get() = definedExternally; set(value) = definedExternally
        var groupToggleElement: dynamic /* Boolean | String /* "arrow" */ | String /* "header" */ */ get() = definedExternally; set(value) = definedExternally
        var groupClosedShowCalcs: Boolean? get() = definedExternally; set(value) = definedExternally
        var dataGrouping: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataGrouped: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupVisibilityChanged: ((group: GroupComponent, visible: Boolean) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupClick: ((e: Any, group: GroupComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupDblClick: ((e: Any, group: GroupComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupContext: ((e: Any, group: GroupComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupTap: ((e: Any, group: GroupComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupDblTap: ((e: Any, group: GroupComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var groupTapHold: ((e: Any, group: GroupComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface Filter {
        var field: String
        var type: dynamic /* String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ */
        var value: Any
    }

    interface OptionsFiltering {
        var initialFilter: Array<Filter>? get() = definedExternally; set(value) = definedExternally
        var initialHeaderFilter: Array<Any?>? get() = definedExternally; set(value) = definedExternally
        var dataFiltering: ((filters: Array<Filter>) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataFiltered: ((filters: Array<Filter>, rows: Array<RowComponent>) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsSorting {
        var initialSort: Array<Sorter>? get() = definedExternally; set(value) = definedExternally
        var sortOrderReverse: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface Sorter {
        var column: String
        var dir: dynamic /* String /* "asc" */ | String /* "desc" */ */
    }

    interface OptionsData {
        var index: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
        var data: Array<Any>? get() = definedExternally; set(value) = definedExternally
        var ajaxURL: String? get() = definedExternally; set(value) = definedExternally
        var ajaxParams: Any? get() = definedExternally; set(value) = definedExternally
        var ajaxConfig: dynamic /* String /* "GET" */ | String /* "POST" */ | AjaxConfig */ get() = definedExternally; set(value) = definedExternally
        var ajaxContentType: dynamic /* String /* "form" */ | String /* "json" */ | AjaxContentType */ get() = definedExternally; set(value) = definedExternally
        var ajaxURLGenerator: ((url: String, config: Any, params: Any) -> String)? get() = definedExternally; set(value) = definedExternally
        var ajaxRequestFunc: ((url: String, config: Any, params: Any) -> Promise<Any>)? get() = definedExternally; set(value) = definedExternally
        var ajaxFiltering: Boolean? get() = definedExternally; set(value) = definedExternally
        var ajaxSorting: Boolean? get() = definedExternally; set(value) = definedExternally
        var ajaxProgressiveLoad: dynamic /* String /* "load" */ | String /* "scroll" */ */ get() = definedExternally; set(value) = definedExternally
        var ajaxProgressiveLoadDelay: Number? get() = definedExternally; set(value) = definedExternally
        var ajaxProgressiveLoadScrollMargin: Number? get() = definedExternally; set(value) = definedExternally
        var ajaxLoader: dynamic /* Boolean | () -> Boolean */ get() = definedExternally; set(value) = definedExternally
        var ajaxLoaderLoading: String? get() = definedExternally; set(value) = definedExternally
        var ajaxLoaderError: String? get() = definedExternally; set(value) = definedExternally
        var ajaxRequesting: ((url: String, params: Any) -> Boolean)? get() = definedExternally; set(value) = definedExternally
        var ajaxResponse: ((url: String, params: Any, response: Any) -> Any)? get() = definedExternally; set(value) = definedExternally
        var ajaxError: ((xhr: Any, textStatus: Any, errorThrown: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface AjaxContentType {
        var headers: Any?
        var body: (url: String, config: Any, params: Any) -> Any
    }

    interface AjaxConfig {
        var method: dynamic /* String /* "GET" */ | String /* "POST" */ */ get() = definedExternally; set(value) = definedExternally
        var headers: Any? get() = definedExternally; set(value) = definedExternally
        var mode: String? get() = definedExternally; set(value) = definedExternally
        var credentials: String? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsRows {
        var rowFormatter: ((row: RowComponent) -> Any)? get() = definedExternally; set(value) = definedExternally
        var addRowPos: dynamic /* String /* "bottom" */ | String /* "top" */ */ get() = definedExternally; set(value) = definedExternally
        var selectable: dynamic /* Number | Boolean | String /* "highlight" */ */ get() = definedExternally; set(value) = definedExternally
        var selectableRangeMode: String? /* "click" */ get() = definedExternally; set(value) = definedExternally
        var selectableRollingSelection: Boolean? get() = definedExternally; set(value) = definedExternally
        var selectablePersistence: Boolean? get() = definedExternally; set(value) = definedExternally
        var selectableCheck: ((row: RowComponent) -> Boolean)? get() = definedExternally; set(value) = definedExternally
        var movableRows: Boolean? get() = definedExternally; set(value) = definedExternally
        var movableRowsConnectedTables: dynamic /* String | HTMLElement | Array<String> | Array<HTMLElement> */ get() = definedExternally; set(value) = definedExternally
        var movableRowsSender: dynamic /* Boolean | String /* "delete" */ | (fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Any */ get() = definedExternally; set(value) = definedExternally
        var movableRowsReceiver: dynamic /* String /* "insert" */ | String /* "update" */ | String /* "replace" */ | String /* "add" */ | (fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Any */ get() = definedExternally; set(value) = definedExternally
        var resizableRows: Boolean? get() = definedExternally; set(value) = definedExternally
        var scrollToRowPosition: dynamic /* String /* "bottom" */ | String /* "top" */ | String /* "center" */ | String /* "nearest" */ */ get() = definedExternally; set(value) = definedExternally
        var scrollToRowIfVisible: Boolean? get() = definedExternally; set(value) = definedExternally
        var dataTreeRowExpanded: ((row: RowComponent, level: Number) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataTreeRowCollapsed: ((row: RowComponent, level: Number) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsSendingStart: ((toTables: Array<Any>) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsSent: ((fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsSentFailed: ((fromRow: RowComponent, toRow: RowComponent, toTable: Tabulator) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsSendingStop: ((toTables: Array<Any>) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsReceivingStart: ((fromRow: RowComponent, toTable: Tabulator) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsReceived: ((fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsReceivedFailed: ((fromRow: RowComponent, toRow: RowComponent, fromTable: Tabulator) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var movableRowsReceivingStop: ((fromTable: Tabulator) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowClick: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowDblClick: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowContext: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowTap: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowDblTap: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowTapHold: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowMouseEnter: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowMouseLeave: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowMouseOver: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowMouseOut: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowMouseMove: ((e: Any, row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowAdded: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowUpdated: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowDeleted: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowMoved: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowResized: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowSelectionChanged: ((data: Array<Any>, rows: Array<RowComponent>) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowSelected: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var rowDeselected: ((row: RowComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsColumns {
        var columns: Array<ColumnDefinition>? get() = definedExternally; set(value) = definedExternally
        var autoColumns: Boolean? get() = definedExternally; set(value) = definedExternally
        var layout: dynamic /* String /* "fitData" */ | String /* "fitColumns" */ | String /* "fitDataFill" */ */ get() = definedExternally; set(value) = definedExternally
        var layoutColumnsOnNewData: Boolean? get() = definedExternally; set(value) = definedExternally
        var responsiveLayout: dynamic /* Boolean | String /* "hide" */ | String /* "collapse" */ */ get() = definedExternally; set(value) = definedExternally
        var responsiveLayoutCollapseStartOpen: Boolean? get() = definedExternally; set(value) = definedExternally
        var responsiveLayoutCollapseUseFormatters: Boolean? get() = definedExternally; set(value) = definedExternally
        var responsiveLayoutCollapseFormatter: ((data: Array<Any>) -> Any)? get() = definedExternally; set(value) = definedExternally
        var columnMinWidth: Number? get() = definedExternally; set(value) = definedExternally
        var resizableColumns: dynamic /* Boolean | String /* "header" */ | String /* "cell" */ */ get() = definedExternally; set(value) = definedExternally
        var movableColumns: Boolean? get() = definedExternally; set(value) = definedExternally
        var tooltipsHeader: Boolean? get() = definedExternally; set(value) = definedExternally
        var columnVertAlign: dynamic /* String /* "bottom" */ | String /* "top" */ | String /* "middle" */ */ get() = definedExternally; set(value) = definedExternally
        var headerFilterPlaceholder: String? get() = definedExternally; set(value) = definedExternally
        var scrollToColumnPosition: dynamic /* String /* "center" */ | String /* "middle" */ | String /* "left" */ | String /* "right" */ */ get() = definedExternally; set(value) = definedExternally
        var scrollToColumnIfVisible: Boolean? get() = definedExternally; set(value) = definedExternally
        var columnCalcs: dynamic /* Boolean | String /* "table" */ | String /* "both" */ | String /* "group" */ */ get() = definedExternally; set(value) = definedExternally
        var nestedFieldSeparator: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var columnHeaderSortMulti: Boolean? get() = definedExternally; set(value) = definedExternally
        var columnMoved: ((column: ColumnComponent, columns: Array<Any>) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var columnResized: ((column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var columnVisibilityChanged: ((column: ColumnComponent, visible: Boolean) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var columnTitleChanged: ((column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsCell {
        var cellClick: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellDblClick: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellContext: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellTap: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellDblTap: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellTapHold: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseEnter: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseLeave: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseOver: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseOut: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseMove: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellEditing: ((cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellEdited: ((cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellEditCancelled: ((cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsGeneral {
        var height: dynamic /* String | Number | Boolean */ get() = definedExternally; set(value) = definedExternally
        var virtualDom: Boolean? get() = definedExternally; set(value) = definedExternally
        var virtualDomBuffer: Boolean? get() = definedExternally; set(value) = definedExternally
        var placeholder: dynamic /* String | HTMLElement */ get() = definedExternally; set(value) = definedExternally
        var footerElement: dynamic /* String | HTMLElement */ get() = definedExternally; set(value) = definedExternally
        var tooltips: dynamic /* Boolean | (cell: CellComponent) -> String */ get() = definedExternally; set(value) = definedExternally
        var tooltipGenerationMode: String? /* "load" */ get() = definedExternally; set(value) = definedExternally
        var keybindings: dynamic /* Boolean | KeyBinding */ get() = definedExternally; set(value) = definedExternally
        var reactiveData: Boolean? get() = definedExternally; set(value) = definedExternally
        var autoResize: Boolean? get() = definedExternally; set(value) = definedExternally
        var tableBuilding: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var tableBuilt: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var renderStarted: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var renderComplete: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var htmlImporting: ((callback: () -> Unit) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var htmlImported: ((callback: () -> Unit) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataLoading: ((data: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataLoaded: ((data: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataEdited: ((data: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var pageLoaded: ((pageno: Number) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataSorting: ((sorters: Array<Sorter>) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var dataSorted: ((sorters: Array<Sorter>, rows: Array<RowComponent>) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface DownloadOptions : DownloadCSV, DownloadXLXS, DownloadPDF {
        var downloadType: dynamic /* String /* "json" */ | String /* "csv" */ | String /* "xlsx" */ | String /* "pdf" */ */
        var fileName: String
    }

    interface DownloadCSV {
        var delimiter: String? /* "string" */ get() = definedExternally; set(value) = definedExternally
        var bom: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface DownloadXLXS {
        var sheetName: String? get() = definedExternally; set(value) = definedExternally
    }

    interface DownloadPDF {
        var orientation: dynamic /* String /* "portrait" */ | String /* "landscape" */ */ get() = definedExternally; set(value) = definedExternally
        var title: String? get() = definedExternally; set(value) = definedExternally
        var rowGroupStyles: Any? get() = definedExternally; set(value) = definedExternally
        var rowCalcStyles: Any? get() = definedExternally; set(value) = definedExternally
        var jsPDF: Any? get() = definedExternally; set(value) = definedExternally
        var autoTable: dynamic /* Any? | (doc: Any) -> Any */ get() = definedExternally; set(value) = definedExternally
    }

    interface DownloadConfig {
        var columnGroups: Boolean? get() = definedExternally; set(value) = definedExternally
        var rowGroups: Boolean? get() = definedExternally; set(value) = definedExternally
        var columnCalcs: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsDownload {
        var downloadDataFormatter: ((data: Array<Any>) -> Any)? get() = definedExternally; set(value) = definedExternally
        var downloadReady: ((fileContents: Any, blob: Any) -> Any)? get() = definedExternally; set(value) = definedExternally
        var downloadComplete: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
        var downloadConfig: DownloadConfig? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsLocale {
        var locale: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var langs: Any? get() = definedExternally; set(value) = definedExternally
        var localized: ((locale: String, lang: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface OptionsHistory {
        var history: Boolean? get() = definedExternally; set(value) = definedExternally
        var historyUndo: ((action: dynamic /* String /* "cellEdit" */ | String /* "rowAdd" */ | String /* "rowDelete" */ | String /* "rowMoved" */ */, component: dynamic /* CellComponent | RowComponent */, data: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var historyRedo: ((action: dynamic /* String /* "cellEdit" */ | String /* "rowAdd" */ | String /* "rowDelete" */ | String /* "rowMoved" */ */, component: dynamic /* CellComponent | RowComponent */, data: Any) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface ColumnLayout {
        var title: String
        var field: String
        var visible: Boolean? get() = definedExternally; set(value) = definedExternally
        var width: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    }

    interface ColumnDefinition : ColumnLayout, CellCallbacks {
        var align: dynamic /* String /* "center" */ | String /* "left" */ | String /* "right" */ */ get() = definedExternally; set(value) = definedExternally
        var minWidth: Number? get() = definedExternally; set(value) = definedExternally
        var widthGrow: Number? get() = definedExternally; set(value) = definedExternally
        var widthShrink: Number? get() = definedExternally; set(value) = definedExternally
        var resizable: Boolean? get() = definedExternally; set(value) = definedExternally
        var frozen: Boolean? get() = definedExternally; set(value) = definedExternally
        var responsive: Number? get() = definedExternally; set(value) = definedExternally
        var tooltip: dynamic /* String | Boolean | (cell: CellComponent) -> String */ get() = definedExternally; set(value) = definedExternally
        var cssClass: String? get() = definedExternally; set(value) = definedExternally
        var rowHandle: Boolean? get() = definedExternally; set(value) = definedExternally
        var hideInHtml: Boolean? get() = definedExternally; set(value) = definedExternally
        var sorter: dynamic /* String /* "string" */ | String /* "number" */ | String /* "boolean" */ | String /* "alphanum" */ | String /* "exists" */ | String /* "date" */ | String /* "time" */ | String /* "datetime" */ | String /* "array" */ | (a: Any, b: Any, aRow: RowComponent, bRow: RowComponent, column: ColumnComponent, dir: dynamic /* String /* "asc" */ | String /* "desc" */ */, sorterParams: Any) -> Number */ get() = definedExternally; set(value) = definedExternally
        var sorterParams: dynamic /* ColumnDefinitionSorterParams | (column: ColumnComponent, dir: dynamic /* String /* "asc" */ | String /* "desc" */ */) -> Any */ get() = definedExternally; set(value) = definedExternally
        var formatter: dynamic /* String /* "datetime" */ | String /* "plaintext" */ | String /* "textarea" */ | String /* "html" */ | String /* "money" */ | String /* "image" */ | String /* "datetimediff" */ | String /* "link" */ | String /* "tickCross" */ | String /* "color" */ | String /* "star" */ | String /* "traffic" */ | String /* "progress" */ | String /* "lookup" */ | String /* "buttonTick" */ | String /* "buttonCross" */ | String /* "rownum" */ | String /* "handle" */ | (cell: CellComponent, formatterParams: Any, onRendered: (callback: () -> Unit) -> Unit) -> dynamic /* String | HTMLElement */ */ get() = definedExternally; set(value) = definedExternally
        var formatterParams: dynamic /* Any? | MoneyParams | ImageParams | LinkParams | DateTimeParams | DateTimeDifferenceParams | TickCrossParams | TrafficParams | StarRatingParams | (cell: CellComponent) -> Any */ get() = definedExternally; set(value) = definedExternally
        var variableHeight: Boolean? get() = definedExternally; set(value) = definedExternally
        var editable: dynamic /* Boolean | (cell: CellComponent) -> Boolean */ get() = definedExternally; set(value) = definedExternally
        var editor: dynamic /* Boolean | String /* "number" */ | String /* "textarea" */ | String /* "tickCross" */ | String /* "star" */ | String /* "input" */ | String /* "range" */ | String /* "select" */ | String /* "autocomplete" */ | (cell: CellComponent, onRendered: (callback: () -> Unit) -> Unit, success: (value: Any) -> Unit, cancel: (value: Any) -> Unit, editorParams: Any) -> dynamic /* Boolean | HTMLElement */ */ get() = definedExternally; set(value) = definedExternally
        var editorParams: dynamic /* NumberParams | CheckboxParams | SelectParams | AutoCompleteParams | (cell: CellComponent) -> Any */ get() = definedExternally; set(value) = definedExternally
        var validator: dynamic /* String /* "string" */ | Validator | Array<Validator> | String /* "required" */ | String /* "unique" */ | String /* "integer" */ | String /* "float" */ | String /* "numeric" */ | Array<dynamic /* String /* "string" */ | String /* "required" */ | String /* "unique" */ | String /* "integer" */ | String /* "float" */ | String /* "numeric" */ */> */ get() = definedExternally; set(value) = definedExternally
        var mutator: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, mutatorParams: Any, cell: CellComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var mutatorParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, cell: CellComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var mutatorData: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, mutatorParams: Any, cell: CellComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var mutatorDataParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, cell: CellComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var mutatorEdit: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, mutatorParams: Any, cell: CellComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var mutatorEditParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, cell: CellComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var mutatorClipboard: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, mutatorParams: Any, cell: CellComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var mutatorClipboardParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "edit" */ */, cell: CellComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var accessor: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "download" */ | String /* "clipboard" */ */, AccessorParams: Any, column: ColumnComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var accessorParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "download" */ | String /* "clipboard" */ */, column: ColumnComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var accessorDownload: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "download" */ | String /* "clipboard" */ */, AccessorParams: Any, column: ColumnComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var accessorDownloadParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "download" */ | String /* "clipboard" */ */, column: ColumnComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var accessorClipboard: ((value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "download" */ | String /* "clipboard" */ */, AccessorParams: Any, column: ColumnComponent? /*= null*/) -> Any)? get() = definedExternally; set(value) = definedExternally
        var accessorClipboardParams: dynamic /* Any? | (value: Any, data: Any, type: dynamic /* String /* "data" */ | String /* "download" */ | String /* "clipboard" */ */, column: ColumnComponent? /*= null*/) -> Any */ get() = definedExternally; set(value) = definedExternally
        var download: Boolean? get() = definedExternally; set(value) = definedExternally
        var downloadTitle: String? get() = definedExternally; set(value) = definedExternally
        var topCalc: dynamic /* String /* "avg" */ | String /* "max" */ | String /* "min" */ | String /* "sum" */ | String /* "concat" */ | String /* "count" */ | (values: Array<Any>, data: Array<Any>, calcParams: Any) -> Number */ get() = definedExternally; set(value) = definedExternally
        var topCalcParams: ((values: Any, data: Any) -> Any)? get() = definedExternally; set(value) = definedExternally
        var topCalcFormatter: dynamic /* String /* "datetime" */ | String /* "plaintext" */ | String /* "textarea" */ | String /* "html" */ | String /* "money" */ | String /* "image" */ | String /* "datetimediff" */ | String /* "link" */ | String /* "tickCross" */ | String /* "color" */ | String /* "star" */ | String /* "traffic" */ | String /* "progress" */ | String /* "lookup" */ | String /* "buttonTick" */ | String /* "buttonCross" */ | String /* "rownum" */ | String /* "handle" */ | (cell: CellComponent, formatterParams: Any, onRendered: (callback: () -> Unit) -> Unit) -> dynamic /* String | HTMLElement */ */ get() = definedExternally; set(value) = definedExternally
        var topCalcFormatterParams: dynamic /* Any? | MoneyParams | ImageParams | LinkParams | DateTimeParams | DateTimeDifferenceParams | TickCrossParams | TrafficParams | StarRatingParams | (cell: CellComponent) -> Any */ get() = definedExternally; set(value) = definedExternally
        var bottomCalc: dynamic /* String /* "avg" */ | String /* "max" */ | String /* "min" */ | String /* "sum" */ | String /* "concat" */ | String /* "count" */ | (values: Array<Any>, data: Array<Any>, calcParams: Any) -> Number */ get() = definedExternally; set(value) = definedExternally
        var bottomCalcParams: ((values: Any, data: Any) -> Any)? get() = definedExternally; set(value) = definedExternally
        var bottomCalcFormatter: dynamic /* String /* "datetime" */ | String /* "plaintext" */ | String /* "textarea" */ | String /* "html" */ | String /* "money" */ | String /* "image" */ | String /* "datetimediff" */ | String /* "link" */ | String /* "tickCross" */ | String /* "color" */ | String /* "star" */ | String /* "traffic" */ | String /* "progress" */ | String /* "lookup" */ | String /* "buttonTick" */ | String /* "buttonCross" */ | String /* "rownum" */ | String /* "handle" */ | (cell: CellComponent, formatterParams: Any, onRendered: (callback: () -> Unit) -> Unit) -> dynamic /* String | HTMLElement */ */ get() = definedExternally; set(value) = definedExternally
        var bottomCalcFormatterParams: dynamic /* Any? | MoneyParams | ImageParams | LinkParams | DateTimeParams | DateTimeDifferenceParams | TickCrossParams | TrafficParams | StarRatingParams | (cell: CellComponent) -> Any */ get() = definedExternally; set(value) = definedExternally
        var headerSort: Boolean? get() = definedExternally; set(value) = definedExternally
        var headerSortStartingDir: dynamic /* String /* "asc" */ | String /* "desc" */ */ get() = definedExternally; set(value) = definedExternally
        var headerSortTristate: Boolean? get() = definedExternally; set(value) = definedExternally
        var headerClick: ((e: Any, column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var headerDblClick: ((e: Any, column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var headerContext: ((e: Any, column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var headerTap: ((e: Any, column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var headerDblTap: ((e: Any, column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var headerTapHold: ((e: Any, column: ColumnComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var headerTooltip: dynamic /* String | Boolean | (column: ColumnComponent) -> String */ get() = definedExternally; set(value) = definedExternally
        var headerVertical: dynamic /* Boolean | String /* "flip" */ */ get() = definedExternally; set(value) = definedExternally
        var editableTitle: Boolean? get() = definedExternally; set(value) = definedExternally
        var titleFormatter: dynamic /* String /* "datetime" */ | String /* "plaintext" */ | String /* "textarea" */ | String /* "html" */ | String /* "money" */ | String /* "image" */ | String /* "datetimediff" */ | String /* "link" */ | String /* "tickCross" */ | String /* "color" */ | String /* "star" */ | String /* "traffic" */ | String /* "progress" */ | String /* "lookup" */ | String /* "buttonTick" */ | String /* "buttonCross" */ | String /* "rownum" */ | String /* "handle" */ | (cell: CellComponent, formatterParams: Any, onRendered: (callback: () -> Unit) -> Unit) -> dynamic /* String | HTMLElement */ */ get() = definedExternally; set(value) = definedExternally
        var titleFormatterParams: dynamic /* Any? | MoneyParams | ImageParams | LinkParams | DateTimeParams | DateTimeDifferenceParams | TickCrossParams | TrafficParams | StarRatingParams | (cell: CellComponent) -> Any */ get() = definedExternally; set(value) = definedExternally
        var headerFilter: dynamic /* Boolean | String /* "number" */ | String /* "textarea" */ | String /* "tickCross" */ | String /* "star" */ | String /* "input" */ | String /* "range" */ | String /* "select" */ | String /* "autocomplete" */ | (cell: CellComponent, onRendered: (callback: () -> Unit) -> Unit, success: (value: Any) -> Unit, cancel: (value: Any) -> Unit, editorParams: Any) -> dynamic /* Boolean | HTMLElement */ */ get() = definedExternally; set(value) = definedExternally
        var headerFilterParams: dynamic /* NumberParams | CheckboxParams | SelectParams | AutoCompleteParams | (cell: CellComponent) -> Any */ get() = definedExternally; set(value) = definedExternally
        var headerFilterPlaceholder: String? get() = definedExternally; set(value) = definedExternally
        var headerFilterEmptyCheck: ((value: Any) -> Boolean)? get() = definedExternally; set(value) = definedExternally
        var headerFilterFunc: dynamic /* String /* "=" */ | String /* "!=" */ | String /* "like" */ | String /* "<" */ | String /* ">" */ | String /* "<=" */ | String /* ">=" */ | String /* "in" */ | String /* "regex" */ | (headerValue: Any, rowValue: Any, rowdata: Any, filterparams: Any) -> Boolean */ get() = definedExternally; set(value) = definedExternally
        var headerFilterFuncParams: Any? get() = definedExternally; set(value) = definedExternally
        var headerFilterLiveFilter: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface CellCallbacks {
        var cellClick: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellDblClick: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellContext: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellTap: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellDblTap: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellTapHold: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseEnter: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseLeave: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseOver: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseOut: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellMouseMove: ((e: Any, cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellEditing: ((cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellEdited: ((cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
        var cellEditCancelled: ((cell: CellComponent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    }

    interface ColumnDefinitionSorterParams {
        var format: String? get() = definedExternally; set(value) = definedExternally
        var locale: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var alignEmptyValues: dynamic /* String /* "bottom" */ | String /* "top" */ */ get() = definedExternally; set(value) = definedExternally
        var type: dynamic /* String /* "avg" */ | String /* "max" */ | String /* "min" */ | String /* "sum" */ | String /* "length" */ */ get() = definedExternally; set(value) = definedExternally
    }

    interface MoneyParams {
        var decimal: String? get() = definedExternally; set(value) = definedExternally
        var thousand: String? get() = definedExternally; set(value) = definedExternally
        var symbol: String? get() = definedExternally; set(value) = definedExternally
        var symbolAfter: Boolean? get() = definedExternally; set(value) = definedExternally
        var precision: dynamic /* Number | Boolean */ get() = definedExternally; set(value) = definedExternally
    }

    interface ImageParams {
        var height: String? get() = definedExternally; set(value) = definedExternally
        var width: String? get() = definedExternally; set(value) = definedExternally
    }

    interface LinkParams {
        var labelField: String? get() = definedExternally; set(value) = definedExternally
        var label: String? get() = definedExternally; set(value) = definedExternally
        var urlPrefix: String? get() = definedExternally; set(value) = definedExternally
        var urlField: String? get() = definedExternally; set(value) = definedExternally
        var url: String? get() = definedExternally; set(value) = definedExternally
        var target: String? get() = definedExternally; set(value) = definedExternally
    }

    interface DateTimeParams {
        var inputFormat: String? get() = definedExternally; set(value) = definedExternally
        var outputFormat: String? get() = definedExternally; set(value) = definedExternally
        var invalidPlaceholder: dynamic /* String | Number | Boolean | (value: Any) -> String */ get() = definedExternally; set(value) = definedExternally
    }

    interface DateTimeDifferenceParams : DateTimeParams {
        var date: Any? get() = definedExternally; set(value) = definedExternally
        var humanize: Boolean? get() = definedExternally; set(value) = definedExternally
        var unit: dynamic /* String /* "years" */ | String /* "months" */ | String /* "weeks" */ | String /* "days" */ | String /* "hours" */ | String /* "minutes" */ | String /* "seconds" */ */ get() = definedExternally; set(value) = definedExternally
        var suffix: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface TickCrossParams {
        var allowEmpty: Boolean? get() = definedExternally; set(value) = definedExternally
        var allowTruthy: Boolean? get() = definedExternally; set(value) = definedExternally
        var tickElement: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var crossElement: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
    }

    interface TrafficParams {
        var min: Number? get() = definedExternally; set(value) = definedExternally
        var max: Number? get() = definedExternally; set(value) = definedExternally
        var color: dynamic /* String | Array<Any> | (value: Any) -> String */ get() = definedExternally; set(value) = definedExternally
    }

    interface ProgressBarParams : TrafficParams {
        var legend: dynamic /* String | Boolean | (value: Any) -> String */ get() = definedExternally; set(value) = definedExternally
        var legendColor: dynamic /* String | Array<Any> | (value: Any) -> String */ get() = definedExternally; set(value) = definedExternally
        var legendAlign: dynamic /* String /* "center" */ | String /* "left" */ | String /* "right" */ | String /* "justify" */ */ get() = definedExternally; set(value) = definedExternally
    }

    interface StarRatingParams {
        var stars: Number? get() = definedExternally; set(value) = definedExternally
    }

    interface NumberParams {
        var min: Number? get() = definedExternally; set(value) = definedExternally
        var max: Number? get() = definedExternally; set(value) = definedExternally
        var step: Number? get() = definedExternally; set(value) = definedExternally
    }

    interface CheckboxParams {
        var tristate: Boolean? get() = definedExternally; set(value) = definedExternally
        var indeterminateValue: String? get() = definedExternally; set(value) = definedExternally
    }

    interface SelectParams {
        var values: dynamic /* Boolean | Any? | Array<String> | Array<SelectParamsGroup> */
        var listItemFormatter: ((value: String, text: String) -> String)? get() = definedExternally; set(value) = definedExternally
    }

    interface SelectParamsGroup {
        var label: String
        var value: dynamic /* String | Number | Boolean */ get() = definedExternally; set(value) = definedExternally
        var options: Array<SelectLabelValue>? get() = definedExternally; set(value) = definedExternally
    }

    interface SelectLabelValue {
        var label: String
        var value: dynamic /* String | Number | Boolean */
    }

    interface AutoCompleteParams {
        var values: dynamic /* Boolean | Any? | Array<String> */
        var listItemFormatter: ((value: String, text: String) -> String)? get() = definedExternally; set(value) = definedExternally
        var searchFunc: ((term: String, values: Array<String>) -> Array<String>)? get() = definedExternally; set(value) = definedExternally
        var allowEmpty: Boolean? get() = definedExternally; set(value) = definedExternally
        var freetext: Boolean? get() = definedExternally; set(value) = definedExternally
        var showListOnEmpty: Boolean? get() = definedExternally; set(value) = definedExternally
    }

    interface Validator {
        var type: dynamic /* String /* "string" */ | String /* "required" */ | String /* "unique" */ | String /* "integer" */ | String /* "float" */ | String /* "numeric" */ | (cell: CellComponent, value: Any, parameters: Any? /*= null*/) -> Boolean */
        var parameters: Any? get() = definedExternally; set(value) = definedExternally
    }

    interface KeyBinding {
        var navPrev: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var navNext: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var navLeft: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var navRight: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var navUp: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var navDown: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var undo: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var redo: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var scrollPageUp: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var scrollPageDown: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var scrollToStart: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var scrollToEnd: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
        var copyToClipboard: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
    }

    interface CellNavigation {
        var prev: () -> Boolean
        var next: () -> Boolean
        var left: () -> Boolean
        var right: () -> Boolean
        var up: () -> Unit
        var down: () -> Unit
    }

    interface RowComponent {
        var getData: () -> Any
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getNextRow: () -> dynamic /* Boolean | RowComponent */
        var getPrevRow: () -> dynamic /* Boolean | RowComponent */
        var getCells: () -> Array<CellComponent>
        var getCell: (column: dynamic /* String | HTMLElement | ColumnComponent */) -> CellComponent
        var getIndex: () -> Any
        var getPosition: (filteredPosition: Boolean? /*= null*/) -> Number
        var getGroup: () -> GroupComponent
        var delete: () -> Promise<Unit>
        var scrollTo: () -> Promise<Unit>
        var pageTo: () -> Promise<Unit>
        var move: (lookup: dynamic /* Number | HTMLElement | RowComponent */, belowTarget: Boolean? /*= null*/) -> Unit
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
        var getTreeParent: () -> dynamic /* Boolean | RowComponent */
        var getTreeChildren: () -> Array<RowComponent>
    }

    interface GroupComponent {
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getKey: () -> Any
        var getRows: () -> Array<RowComponent>
        var getSubGroups: () -> Array<GroupComponent>
        var getParentGroup: () -> dynamic /* Boolean | GroupComponent */
        var getVisibility: () -> Boolean
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
        var getNextColumn: () -> dynamic /* Boolean | ColumnComponent */
        var getPrevColumn: () -> dynamic /* Boolean | ColumnComponent */
        var getVisibility: () -> Boolean
        var show: () -> Unit
        var hide: () -> Unit
        var toggle: () -> Unit
        var delete: () -> Unit
        var scrollTo: () -> Promise<Unit>
        var getSubColumns: () -> Array<ColumnComponent>
        var getParentColumn: () -> dynamic /* Boolean | ColumnComponent */
        var headerFilterFocus: () -> Unit
        var setHeaderFilterValue: (value: Any) -> Unit
        var reloadHeaderFilter: () -> Unit
    }

    interface CellComponent {
        var getValue: () -> Any?
        var getOldValue: () -> Any?
        var restoreOldValue: () -> Any
        var getElement: () -> HTMLElement
        var getTable: () -> Tabulator
        var getRow: () -> RowComponent
        var getColumn: () -> ColumnComponent
        var getData: () -> Any
        var getField: () -> String
        var setValue: (value: Any, mutate: Boolean? /*= null*/) -> Unit
        var checkHeight: () -> Unit
        var edit: (ignoreEditable: Boolean? /*= null*/) -> Unit
        var cancelEdit: () -> Unit
        var nav: () -> CellNavigation
    }
}
