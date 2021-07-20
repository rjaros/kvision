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
package io.kvision.tabulator

import com.github.snabbdom.VNode
import io.kvision.KVManagerTabulator
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.i18n.I18n
import io.kvision.panel.Root
import io.kvision.state.ObservableList
import io.kvision.state.ObservableState
import io.kvision.types.DateSerializer
import io.kvision.utils.createInstance
import io.kvision.utils.obj
import io.kvision.utils.syncWithList
import io.kvision.utils.toKotlinObj
import io.kvision.utils.toPlainObj
import kotlinx.browser.window
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import kotlin.js.Date
import kotlin.reflect.KClass
import io.kvision.tabulator.js.Tabulator as JsTabulator

/**
 * Tabulator table types.
 */
enum class TableType(val type: String) {
    STRIPED("table-striped"),
    BORDERED("table-bordered"),
    BORDERLESS("table-borderless"),
    HOVER("table-hover"),
    SMALL("table-sm")
}

/**
 * Tabulator row range lookup set option.
 */
enum class RowRangeLookup(internal val set: String) {
    ALL("all"),
    VISIBLE("visible"),
    SELECTED("selected"),
    ACTIVE("active")
}

/**
 * Tabulator component.
 *
 * @constructor
 * @param T type
 * @param data a list of objects
 * @param dataUpdateOnEdit determines if the data model is automatically updated after tabulator edit action
 * @param options tabulator options
 * @param types a set of table types
 * @param className CSS class names
 * @param kClass Kotlin class
 * @param serializer the serializer for type T
 * @param serializersModule optional serialization module with custom serializers
 */
@Suppress("LargeClass", "TooManyFunctions")
open class Tabulator<T : Any>(
    protected val data: List<T>? = null,
    protected val dataUpdateOnEdit: Boolean = true,
    val options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    protected val kClass: KClass<T>? = null,
    protected val serializer: KSerializer<T>? = null,
    protected val module: SerializersModule? = null
) : Widget(className) {

    protected val jsonHelper = if (serializer != null) Json {
        ignoreUnknownKeys = true
        isLenient = true
        serializersModule = SerializersModule {
            contextual(Date::class, DateSerializer)
            module?.let { this.include(it) }
        }
    } else null

    /**
     * Table types.
     */
    var types by refreshOnUpdate(types)

    /**
     * Native Tabulator object.
     */
    var jsTabulator: JsTabulator? = null

    private var pageSize: Number? = null
    private var currentPage: Number? = null
    private var customRoots = mutableListOf<Root>()

    protected var filter: ((T) -> Boolean)? = null

    init {
        if (data != null) {
            @Suppress("UnsafeCastFromDynamic")
            options.data = data.map { toPlainObjTabulator(it) }.toTypedArray()
            if (data is ObservableList) {
                data.onUpdate += {
                    replaceData(data.toTypedArray())
                }
            }
        }
        if (options.langs == null) {
            options.langs = obj {
                default = obj {
                    groups = obj {
                        item = ""
                        items = ""
                    }
                    ajax = obj {
                        loading = "..."
                        error = "!!!"
                    }
                    pagination = obj {
                        page_size = "↕"
                        first = "<<"
                        first_title = "<<"
                        last = ">>"
                        last_title = ">>"
                        prev = "<"
                        prev_title = "<"
                        next = ">"
                        next_title = ">"
                    }
                    headerFilters = obj {
                        default = "..."
                    }
                }
            }
        }
        if (options.rowClick == null) {
            options.rowClick = { _, row ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("rowClickTabulator", obj { detail = row })
            }
        }
        if (options.rowDblClick == null) {
            options.rowDblClick = { _, row ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("rowDblClickTabulator", obj { detail = row })
            }
        }
        if (options.rowSelectionChanged == null) {
            options.rowSelectionChanged = { _, rows ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("rowSelectionChangedTabulator", obj { detail = rows })
            }
        }
        if (options.rowSelected == null) {
            options.rowSelected = { row ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("rowSelectedTabulator", obj { detail = row })
            }
        }
        if (options.rowDeselected == null) {
            options.rowDeselected = { row ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("rowDeselectedTabulator", obj { detail = row })
            }
        }
        if (options.cellClick == null) {
            options.cellClick = { _, cell ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("cellClickTabulator", obj { detail = cell })
            }
        }
        if (options.cellDblClick == null) {
            options.cellDblClick = { _, cell ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("cellDblClickTabulator", obj { detail = cell })
            }
        }
        if (options.cellEditing == null) {
            options.cellEditing = { cell ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("cellEditingTabulator", obj { detail = cell })
            }
        }
        if (options.cellEdited == null) {
            options.cellEdited = { cell ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("cellEditedTabulator", obj { detail = cell })
            }
        }
        if (options.cellEditCancelled == null) {
            options.cellEditCancelled = { cell ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("cellEditCancelledTabulator", obj { detail = cell })
            }
        }
        if (options.dataLoading == null) {
            options.dataLoading = { data ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("dataLoadingTabulator", obj { detail = data })
            }
        }
        if (options.dataLoaded == null) {
            options.dataLoaded = { data ->
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("dataLoadedTabulator", obj { detail = data })
            }
        }
        if (options.dataChanged == null) {
            options.dataChanged = { data ->
                val fixedData = fixData(data)!!
                @Suppress("UnsafeCastFromDynamic")
                this.dispatchEvent("dataEditedTabulator", obj { detail = fixedData })
                if (dataUpdateOnEdit && this.data is MutableList<T>) {
                    window.setTimeout({
                        this.data.syncWithList(fixedData)
                    }, 0)
                }
            }
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        types.forEach {
            classSetBuilder.add(it.type)
        }
    }

    /**
     * Creates internal JS Tabulator object
     */
    protected open fun createJsTabulator() {
        (this.getElement() as? HTMLElement)?.let {
            jsTabulator =
                KVManagerTabulator.getConstructor()
                    .createInstance(it, options.toJs(this, this::translate, kClass))
            if (currentPage != null) {
                jsTabulator?.setPageSize(pageSize ?: 0)
                jsTabulator?.setPage(currentPage)
            }
        }
    }

    override fun render(): VNode {
        if (lastLanguage != null && lastLanguage != I18n.language) {
            jsTabulator?.destroy()
            customRoots.forEach { it.dispose() }
            customRoots.clear()
            createJsTabulator()
        }
        return render("div")
    }

    override fun afterInsert(node: VNode) {
        createJsTabulator()
    }

    override fun afterDestroy() {
        val page = jsTabulator?.getPage()
        if (page != null && page != false) {
            pageSize = jsTabulator?.getPageSize()
            currentPage = page as Number
        }
        jsTabulator?.destroy()
        customRoots.forEach { it.dispose() }
        customRoots.clear()
        jsTabulator = null
    }

    /**
     * Silently replaces the data in a table.
     * @param data new data
     */
    open fun replaceData(data: Array<T>) {
        val jsData = data.map { toPlainObjTabulator(it) }.toTypedArray()
        options.data = jsData
        if ((getElement()?.unsafeCast<Element>()?.querySelectorAll(".tabulator-editing")?.length ?: 0) > 0) {
            this.removeCustomEditors()
        }
        jsTabulator?.replaceData(jsData, null, null)
    }

    /**
     * Sets new data in a table.
     * @param data new data
     */
    open fun setData(data: Array<T>) {
        val jsData = data.map { toPlainObjTabulator(it) }.toTypedArray()
        options.data = jsData
        jsTabulator?.setData(jsData, null, null)
    }

    /**
     * Returns the current data in the table.
     * @param rowRangeLookup selected data set
     * @return current data
     */
    @Suppress("UNCHECKED_CAST")
    open fun getData(rowRangeLookup: RowRangeLookup? = null): List<T>? {
        return if (jsTabulator != null) {
            fixData(jsTabulator?.getData(rowRangeLookup?.set)?.toList() as? List<T>)
        } else {
            data
        }
    }

    /**
     * Returns the selected data in the table.
     * @return selected data
     */
    @Suppress("UNCHECKED_CAST")
    open fun getSelectedData(): List<T> {
        return if (jsTabulator != null) {
            fixData(jsTabulator?.getSelectedData()?.toList() as List<T>)!!
        } else {
            listOf()
        }
    }

    /**
     * Clears the data in the table.
     */
    open fun clearData() = jsTabulator?.clearData()

    /**
     * Undo the last user action.
     */
    open fun undo(): Boolean = jsTabulator?.undo() ?: false

    /**
     * Redo the last undone user action.
     */
    open fun redo(): Boolean = jsTabulator?.redo() ?: false

    /**
     * Get the number of history undo actions available.
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getHistoryUndoSize(): Int = jsTabulator?.getHistoryUndoSize() ?: 0

    /**
     * Get the number of history redo actions available.
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getHistoryRedoSize(): Int = jsTabulator?.getHistoryRedoSize() ?: 0

    /**
     * Get the number of data rows.
     * @param rowRangeLookup selected data set
     * @return the number of data rows
     */
    open fun getDataCount(rowRangeLookup: RowRangeLookup? = null): Int {
        return jsTabulator?.getDataCount(rowRangeLookup?.set)?.toInt() ?: 0
    }

    /**
     * Get the HTML code of the table.
     * @param rowRangeLookup the selected range of rows
     * @param isStyled return styled output
     * @param htmlOutputConfig override output configuration
     * @return the HTML code of the table
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getHtml(
        rowRangeLookup: RowRangeLookup,
        isStyled: Boolean = false,
        htmlOutputConfig: dynamic = null
    ): String? = jsTabulator?.getHtml(rowRangeLookup.set, isStyled, htmlOutputConfig)

    /**
     * Print the table.
     * @param rowRangeLookup the selected range of rows
     * @param isStyled styled output
     * @param printConfig override print configuration
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun print(
        rowRangeLookup: RowRangeLookup,
        isStyled: Boolean = false,
        printConfig: dynamic = null
    ): Unit? = jsTabulator?.print(rowRangeLookup.set, isStyled, printConfig)

    /**
     * Download the table content as CSV
     * @param fileName downloaded file name
     * @param dataSet a data set configuration
     * @param delimiter CSV delimiter
     * @param includeBOM determines if BOM should be included
     * @param newTab determines if download should be open in a new tab
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun downloadCSV(
        fileName: String? = null,
        dataSet: RowRangeLookup = RowRangeLookup.ACTIVE,
        delimiter: Char = ',',
        includeBOM: Boolean = false,
        newTab: Boolean = false
    ): Unit? {
        return if (newTab) {
            jsTabulator?.downloadToTab("csv", fileName, obj {
                this.delimiter = delimiter
                this.bom = includeBOM
            }, dataSet.set)
        } else {
            jsTabulator?.download("csv", fileName, obj {
                this.delimiter = delimiter
                this.bom = includeBOM
            }, dataSet.set)
        }
    }

    /**
     * Download the table content as JSON
     * @param fileName downloaded file name
     * @param dataSet a data set configuration
     * @param newTab determines if download should be open in a new tab
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun downloadJSON(
        fileName: String? = null,
        dataSet: RowRangeLookup = RowRangeLookup.ACTIVE,
        newTab: Boolean = false
    ): Unit? {
        return if (newTab) {
            jsTabulator?.downloadToTab("json", fileName, obj {}, dataSet.set)
        } else {
            jsTabulator?.download("json", fileName, obj {}, dataSet.set)
        }
    }

    /**
     * Download the table content as HTML
     * @param fileName downloaded file name
     * @param dataSet a data set configuration
     * @param style download a html table with matching styling
     * @param newTab determines if download should be open in a new tab
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun downloadHTML(
        fileName: String? = null,
        dataSet: RowRangeLookup = RowRangeLookup.ACTIVE,
        style: Boolean = false,
        newTab: Boolean = false
    ): Unit? {
        return if (newTab) {
            jsTabulator?.downloadToTab("html", fileName, obj {
                this.style = style
            }, dataSet.set)
        } else {
            jsTabulator?.download("html", fileName, obj {
                this.style = style
            }, dataSet.set)
        }
    }

    /**
     * Scroll to the row given by id.
     * @param row id of the row
     * @param position the scrolling position
     * @param ifVisible scroll to already visible row
     */
    open fun scrollToRow(
        row: Int,
        position: RowPosition? = null,
        ifVisible: Boolean? = null
    ) {
        jsTabulator?.scrollToRow(row, position?.position, ifVisible)
    }

    /**
     * Redraw the table (e.g. after a resize).
     * @param force rerender all rows and columns
     */
    open fun redraw(force: Boolean = false) {
        jsTabulator?.redraw(force)
    }

    /**
     * Reload table data.
     */
    open fun reload() {
        jsTabulator?.setData(null, null, null)
    }

    /**
     * Change the height of the table.
     * @param height new heigth of the table
     */
    open fun setHeight(height: Int) {
        jsTabulator?.setHeight(height)
    }

    /**
     * Clears current sort.
     */
    open fun clearSort() {
        jsTabulator?.clearSort()
    }

    /**
     * Sets the external filter for the data.
     * @param filter a filtering function
     */
    open fun setFilter(filter: (T) -> Boolean) {
        this.filter = filter
        applyFilter()
    }

    /**
     * Applies the current filter.
     */
    open fun applyFilter() {
        if (filter != null) {
            jsTabulator?.setFilter({ data: dynamic, _: dynamic ->
                filter?.let {
                    @Suppress("UnsafeCastFromDynamic")
                    it(fixData(data as T))
                }
            }, null, null)
        }
    }

    /**
     * Clears current filters.
     * @param includeHeaderFilters clear also the header filters
     */
    open fun clearFilter(includeHeaderFilters: Boolean = true) {
        jsTabulator?.clearFilter(includeHeaderFilters)
    }

    /**
     * Clears header filters.
     */
    open fun clearHeaderFilter() {
        jsTabulator?.clearHeaderFilter()
    }

    /**
     * Select the row.
     * @param row row id
     */
    open fun selectRow(row: dynamic = undefined) {
        jsTabulator?.selectRow(row)
    }

    /**
     * Deselect the row.
     * @param row row id
     */
    open fun deselectRow(row: dynamic = undefined) {
        jsTabulator?.deselectRow(row)
    }

    /**
     * Toggle selection status of the row.
     * @param row row id
     */
    open fun toggleSelectRow(row: dynamic = undefined) {
        jsTabulator?.toggleSelectRow(row)
    }

    /**
     * Returns the selected rows.
     */
    open fun getSelectedRows(): List<JsTabulator.RowComponent> {
        return jsTabulator?.getSelectedRows()?.asList() ?: listOf()
    }

    /**
     * Shows given page.
     * @param page page number
     */
    open fun setPage(page: Int) {
        jsTabulator?.setPage(page)
    }

    /**
     * Shows page with a row given by id.
     * @param row row id
     */
    open fun setPageToRow(row: Int) {
        jsTabulator?.setPageToRow(row)
    }

    /**
     * Set the size of a page.
     * @param size page size
     */
    open fun setPageSize(size: Int) {
        jsTabulator?.setPageSize(size)
    }

    /**
     * Returns the size of a page.
     */
    open fun getPageSize(): Int = jsTabulator?.getPageSize()?.toInt() ?: 0

    /**
     * Navigate to the previous page.
     */
    open fun previousPage() {
        jsTabulator?.previousPage()
    }

    /**
     * Navigate to the next page.
     */
    open fun nextPage() {
        jsTabulator?.nextPage()
    }

    /**
     * Returns current page number.
     */
    open fun getPage(): Int = (jsTabulator?.getPage() as? Number)?.toInt() ?: -1

    /**
     * Returns number of pages.
     */
    open fun getPageMax(): Int = (jsTabulator?.getPageMax() as? Number)?.toInt() ?: -1

    /**
     * Navigate to the previous cell.
     */
    open fun navigatePrev() {
        jsTabulator?.navigatePrev()
    }

    /**
     * Navigate to the next cell.
     */
    open fun navigateNext() {
        jsTabulator?.navigateNext()
    }

    /**
     * Navigate to the cell on the left.
     */
    open fun navigateLeft() {
        jsTabulator?.navigateLeft()
    }

    /**
     * Navigate to the cell on the right.
     */
    open fun navigateRight() {
        jsTabulator?.navigateRight()
    }

    /**
     * Navigate to the same cell in the row above.
     */
    open fun navigateUp() {
        jsTabulator?.navigateUp()
    }

    /**
     * Navigate to the same cell in the row below.
     */
    open fun navigateDown() {
        jsTabulator?.navigateDown()
    }

    /**
     * Get column component by name.
     * @param name column name
     * @return column component
     */
    open fun getColumn(name: String): JsTabulator.ColumnComponent? {
        return jsTabulator?.getColumn(name)
    }

    /**
     * Delete column by name.
     * @param name column name
     */
    open fun deleteColumn(name: String) {
        jsTabulator?.deleteColumn(name)
    }

    /**
     * Add new column to the tabulator.
     * @param columnDefinition column definition
     * @param insertRightOfTarget determines how to position the new column
     * @param positionTarget the field to insert the new column next to
     */
    open fun addColumn(
        columnDefinition: ColumnDefinition<T>,
        insertRightOfTarget: Boolean? = null,
        positionTarget: String? = null
    ) {
        jsTabulator?.addColumn(
            columnDefinition.toJs(this, this::translate, kClass),
            insertRightOfTarget,
            positionTarget
        )
    }

    /**
     * Add new column to the tabulator.
     * @param columnDefinition column definition native object
     * @param insertRightOfTarget determines how to position the new column
     * @param positionTarget the field to insert the new column next to
     */
    open fun addColumn(
        columnDefinition: JsTabulator.ColumnDefinition,
        insertRightOfTarget: Boolean? = null,
        positionTarget: String? = null
    ) {
        jsTabulator?.addColumn(columnDefinition, insertRightOfTarget, positionTarget)
    }

    internal fun removeCustomEditors() {
        EditorRoot.cancel?.invoke(null)
        EditorRoot.disposeTimer?.let { window.clearTimeout(it) }
        EditorRoot.root?.dispose()
        EditorRoot.root = null
    }

    protected open fun fixData(data: List<dynamic>?): List<T>? {
        return if (kClass != null) {
            data?.map {
                toKotlinObjTabulator(it, kClass)
            }
        } else {
            data
        }
    }

    protected open fun fixData(data: dynamic): T {
        @Suppress("UnsafeCastFromDynamic")
        return if (kClass != null) {
            toKotlinObjTabulator(data, kClass)
        } else {
            data
        }
    }

    internal fun toKotlinObjTabulator(data: dynamic, kClass: KClass<T>): T {
        if (data._children != null) {
            data._children =
                data._children.unsafeCast<Array<dynamic>>().map { toKotlinObjTabulator(it, kClass) }.toTypedArray()
        }
        return if (jsonHelper == null || serializer == null) {
            toKotlinObj(data, kClass)
        } else {
            jsonHelper.decodeFromString(serializer, JSON.stringify(data))
        }
    }

    internal fun toPlainObjTabulator(data: T): T {
        val obj = if (jsonHelper == null || serializer == null) {
            toPlainObj(data)
        } else {
            JSON.parse(jsonHelper.encodeToString(serializer, data))
        }
        if (obj._children != null) {
            obj._children = obj._children.unsafeCast<Array<T>>().map { toPlainObjTabulator(it) }.toTypedArray()
        }
        @Suppress("UnsafeCastFromDynamic")
        return obj
    }

    internal fun addCustomRoot(root: Root) {
        customRoots.add(root)
    }

    override fun dispose() {
        jsTabulator?.destroy()
        customRoots.forEach { it.dispose() }
        customRoots.clear()
        jsTabulator = null
        super.dispose()
    }

    companion object {
        /**
         * A helper function to create a Tabulator object.
         */
        inline fun <reified T : Any> create(
            data: List<T>? = null,
            dataUpdateOnEdit: Boolean = true,
            options: TabulatorOptions<T> = TabulatorOptions(),
            types: Set<TableType> = setOf(),
            className: String? = null,
            serializer: KSerializer<T>? = null,
            serializersModule: SerializersModule? = null,
            noinline init: (Tabulator<T>.() -> Unit)? = null
        ): Tabulator<T> {
            val tabulator =
                Tabulator(data, dataUpdateOnEdit, options, types, className, T::class, serializer, serializersModule)
            init?.invoke(tabulator)
            return tabulator
        }

        /**
         * A helper function to create a Tabulator object with a general observable store.
         */
        inline fun <reified T : Any, S : Any> create(
            store: ObservableState<S>,
            noinline dataFactory: (S) -> List<T>,
            options: TabulatorOptions<T> = TabulatorOptions(),
            types: Set<TableType> = setOf(),
            className: String? = null,
            serializer: KSerializer<T>? = null,
            serializersModule: SerializersModule? = null,
            noinline init: (Tabulator<T>.() -> Unit)? = null
        ): Tabulator<T> {
            val data = dataFactory(store.getState())
            val tabulator = Tabulator(data, false, options, types, className, T::class, serializer, serializersModule)
            init?.invoke(tabulator)
            tabulator.addBeforeDisposeHook(store.subscribe { s ->
                tabulator.replaceData(dataFactory(s).toTypedArray())
            })
            return tabulator
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
inline fun <reified T : Any> Container.tabulator(
    data: List<T>? = null,
    dataUpdateOnEdit: Boolean = true,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    serializer: KSerializer<T>? = null,
    serializersModule: SerializersModule? = null,
    noinline init: (Tabulator<T>.() -> Unit)? = null
): Tabulator<T> {
    val tabulator = Tabulator.create(data, dataUpdateOnEdit, options, types, className, serializer, serializersModule)
    init?.invoke(tabulator)
    this.add(tabulator)
    return tabulator
}

/**
 * DSL builder extension function for a general observable store.
 */
inline fun <reified T : Any, S : Any> Container.tabulator(
    store: ObservableState<S>,
    noinline dataFactory: (S) -> List<T>,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    serializer: KSerializer<T>? = null,
    serializersModule: SerializersModule? = null,
    noinline init: (Tabulator<T>.() -> Unit)? = null
): Tabulator<T> {
    val tabulator = Tabulator.create(store, dataFactory, options, types, className, serializer, serializersModule)
    init?.invoke(tabulator)
    this.add(tabulator)
    return tabulator
}

/**
 * DSL builder extension function for dynamic data (send within options parameter).
 */
inline fun <reified T : Any> Container.tabulator(
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    noinline init: (Tabulator<T>.() -> Unit)? = null
): Tabulator<T> {
    val tabulator =
        Tabulator(
            dataUpdateOnEdit = false,
            options = options,
            types = types,
            className = className,
            kClass = T::class
        )
    init?.invoke(tabulator)
    this.add(tabulator)
    return tabulator
}
