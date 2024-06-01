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

import io.kvision.core.Component
import io.kvision.form.FormControl
import io.kvision.form.FormInput
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.tabulator.EditorRoot.disposeTimer
import io.kvision.tabulator.EditorRoot.root
import io.kvision.tabulator.js.Tabulator
import io.kvision.utils.obj
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import kotlin.js.Promise
import kotlin.reflect.KClass

/**
 * Column align.
 */
enum class Align(internal val align: String) {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right")
}

/**
 * Column align.
 */
enum class VAlign(internal val valign: String) {
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom")
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
    HANDLE("handle"),
    ROWSELECTION("rowSelection"),
    RESPONSIVECOLLAPSE("responsiveCollapse"),
    RESPONSIVECOLLAPSEAUTO("responsiveCollapseAuto"),
    TOGGLE("toggle"),
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
    LIST("list"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
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
    REGEX("regex"),
    ALPHANUMERIC("alphanumeric")
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
    COUNT("count"),
    UNIQUE("unique")
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
    FITCOLUMNS("fitColumns"),
    FITDATASTRETCH("fitDataStretch"),
    FITDATATABLE("fitDataTable"),
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
 * Sort modes.
 */
enum class SortMode(internal val mode: String) {
    LOCAL("local"),
    REMOTE("remote")
}

/**
 * Filter modes.
 */
enum class FilterMode(internal val mode: String) {
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
 * Text direction.
 */
enum class TextDirection(internal val dir: String) {
    AUTO("auto"),
    LTR("ltr"),
    RTL("rtl")
}

/**
 * Virtual DOM options.
 */
enum class RenderType(internal val type: String) {
    BASIC("basic"),
    VIRTUAL("virtual")
}

/**
 * Import formats.
 */
enum class ImportFormat(internal val type: String) {
    JSON("json"),
    CSV("csv"),
    ARRAY("array")
}

/**
 * Import readers.
 */
enum class ImportReader(internal val type: String) {
    TEXT("text"),
    BUFFER("buffer"),
    BINARY("binary"),
    URL("url")
}

/**
 * Possible header sort click elements.
 */
enum class HeaderSortClickElement(internal val element: String) {
    HEADER("header"),
    ICON("icon")
}

/**
 * Possible edit trigger events.
 */
enum class EditTriggerEvent(internal val event: String) {
    FOCUS("focus"),
    CLICK("click"),
    DBLCLICK("dblclick"),
}

/**
 * Download config options.
 */
data class DownloadConfig(
    val columnGroups: Boolean? = null,
    val rowGroups: Boolean? = null,
    val columnCalcs: Boolean? = null,
    val rowHeaders: Boolean? = null
)

/**
 * An extension function to convert download config class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
fun DownloadConfig.toJs(): Tabulator.AddditionalExportOptions {
    return obj {
        if (columnGroups != null) this.columnGroups = columnGroups
        if (rowGroups != null) this.rowGroups = rowGroups
        if (columnCalcs != null) this.columnCalcs = columnCalcs
        if (rowHeaders != null) this.rowHeaders = rowHeaders
    } as Tabulator.AddditionalExportOptions
}

/**
 * Column definition options.
 */
data class ColumnDefinition<T : Any>(
    val title: String,
    val field: String? = null,
    val columns: List<ColumnDefinition<T>>? = null,
    val visible: Boolean? = null,
    val align: Align? = null,
    val width: String? = null,
    val minWidth: Int? = null,
    val widthGrow: Int? = null,
    val widthShrink: Int? = null,
    val resizable: dynamic = null,
    val frozen: Boolean? = null,
    val responsive: Int? = null,
    val tooltip: dynamic = null,
    val cssClass: String? = null,
    val rowHandle: Boolean? = null,
    val hideInHtml: Boolean? = null,
    val sorter: Sorter? = null,
    val sorterFunction: ((
        a: Any?, b: Any?, aRow: Tabulator.RowComponent, bRow: Tabulator.RowComponent,
        column: Tabulator.ColumnComponent, dir: SortingDir, sorterParams: Any?
    ) -> Number)? = null,
    val sorterParams: dynamic = null,
    val formatter: Formatter? = null,
    val formatterFunction: ((
        cell: Tabulator.CellComponent, formatterParams: Any?,
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
        success: (value: Any?) -> Unit, cancel: (value: Any?) -> Unit, editorParams: Any?
    ) -> dynamic)? = null,
    val editorComponentFunction: ((
        cell: Tabulator.CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: Any?) -> Unit, cancel: (value: Any?) -> Unit, data: T
    ) -> Component)? = null,
    val editorParams: dynamic = null,
    val validator: Validator? = null,
    val validatorFunction: dynamic = null,
    val validatorParams: String? = null,
    val download: dynamic = null,
    val titleDownload: String? = null,
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
    val headerClick: ((e: Any?, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerDblClick: ((e: Any?, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerContext: ((e: Any?, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerTap: ((e: Any?, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerDblTap: ((e: Any?, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerTapHold: ((e: Any?, column: Tabulator.ColumnComponent) -> Unit)? = null,
    val headerTooltip: dynamic = null,
    val headerVertical: Boolean? = null,
    val editableTitle: Boolean? = null,
    val titleFormatter: Formatter? = null,
    val titleFormatterFunction: ((
        cell: Tabulator.CellComponent, formatterParams: Any?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> dynamic)? = null,
    val titleFormatterComponentFunction: ((
        cell: Tabulator.CellComponent, onRendered: (callback: () -> Unit) -> Unit
    ) -> Component)? = null,
    val titleFormatterParams: dynamic = null,
    val headerFilter: Editor? = null,
    val headerFilterParams: dynamic = null,
    val headerFilterCustom: ((
        cell: Tabulator.CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: Any?) -> Unit, cancel: (value: Any?) -> Unit, editorParams: Any?
    ) -> dynamic)? = null,
    val headerFilterPlaceholder: String? = null,
    val headerFilterEmptyCheck: ((value: Any) -> Boolean)? = null,
    val headerFilterFunc: Filter? = null,
    val headerFilterFuncCustom: ((headerValue: Any?, rowValue: Any?, rowData: Any?, filterParams: Any?) -> Boolean)? = null,
    val headerFilterFuncParams: dynamic = null,
    val headerFilterLiveFilter: Boolean? = null,
    val htmlOutput: dynamic = null,
    val print: dynamic = null,
    val formatterPrint: ((
        cell: Tabulator.CellComponent,
        formatterParams: Any?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> dynamic)? = null,
    val formatterPrintParams: dynamic = null,
    val cellClick: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellDblClick: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellContext: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellTap: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellDblTap: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellTapHold: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseEnter: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseLeave: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseOver: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseOut: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellMouseMove: ((e: Any?, cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellEditing: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellEdited: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    val cellEditCancelled: ((cell: Tabulator.CellComponent) -> Unit)? = null,
    val headerMenu: dynamic = null,
    val headerContextMenu: dynamic = null,
    val contextMenu: dynamic = null,
    val hozAlign: Align? = null,
    val vertAlign: VAlign? = null,
    val clickMenu: dynamic = null,
    val headerHozAlign: Align? = null,
    val accessor: dynamic = null,
    val accessorParams: dynamic = null,
    val maxWidth: Int? = null,
    val mutatorData: ((value: Any?, data: Any?, type: String, params: Any?, cell: Tabulator.CellComponent) -> Any)? = null,
    val mutatorDataParams: dynamic = null,
    val mutatorEdit: ((value: Any?, data: Any?, type: String, params: Any?, cell: Tabulator.CellComponent) -> Any)? = null,
    val mutatorEditParams: dynamic = null,
    val mutatorClipboard: ((value: Any?, data: Any?, type: String, params: Any?, cell: Tabulator.CellComponent) -> Any)? = null,
    val mutatorClipboardParams: dynamic = null,
    val mutator: ((value: Any?, data: Any?, type: String, params: Any?, cell: Tabulator.CellComponent) -> Any)? = null,
    val mutatorParams: dynamic = null,
    val maxInitialWidth: Int? = null,
    val cellPopup: dynamic = null,
    val headerPopup: dynamic = null,
    val headerPopupIcon: dynamic = null,
    val headerContextPopup: dynamic = null,
    val clickPopup: dynamic = null,
    val contextPopup: dynamic = null,
    val headerMenuIcon: dynamic = null,
    val headerWordWrap: Boolean? = null,
    val dblClickPopup: dynamic = null,
    val headerClickPopup: dynamic = null,
    val headerDblClickPopup: dynamic = null,
    val headerClickMenu: dynamic = null,
    val headerDblClickMenu: dynamic = null,
    val dblClickMenu: dynamic = null,
    val headerColumnsMenu: Boolean? = null,
    val headerColumnsMenuTitle: String? = null,
    val headerColumnsMenuResetTitle: String? = null,
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
    tabulator: io.kvision.tabulator.Tabulator<T>,
    i18nTranslator: (String) -> (String),
    kClass: KClass<T>?
): Tabulator.ColumnDefinition {
    val tmpEditorFunction = editorComponentFunction?.let {
        { cell: Tabulator.CellComponent,
          onRendered: (callback: () -> Unit) -> Unit,
          success: (value: dynamic) -> Unit, cancel: (value: dynamic) -> Unit, _: dynamic ->
            if (cell.getElement().asDynamic() != false) cell.getElement().style.asDynamic().overflow = "visible"
            var onRenderedCallback: (() -> Unit)? = null
            val data: dynamic = if (kClass != null) {
                tabulator.toKotlinObj(cell.getData())
            } else {
                cell.getData()
            }

            @Suppress("UnsafeCastFromDynamic")
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
            @Suppress("SENSELESS_COMPARISON")
            if (onRendered != undefined) {
                onRendered {
                    if (root != null) {
                        disposeTimer?.let { window.clearTimeout(it) }
                        root?.dispose()
                    }
                    root = Root(rootElement, ContainerType.NONE, false)
                    EditorRoot.cancel = cancel
                    root?.add(component)
                    (component as? FormControl)?.focus()
                    (component as? FormInput)?.focus()
                    if (cell.asDynamic().checkHeight != undefined) cell.checkHeight()
                    (root?.getElement()?.parentElement as? HTMLDivElement)?.style?.overflowX = "visible"
                    (root?.getElement()?.parentElement as? HTMLDivElement)?.style?.overflowY = "visible"
                    onRenderedCallback?.invoke()
                }
            }
            rootElement
        }
    }

    val tmpFormatterFunction = formatterComponentFunction?.let {
        { cell: Tabulator.CellComponent, _: dynamic,
          onRendered: (callback: () -> Unit) -> Unit ->
            var onRenderedCallback: (() -> Unit)? = null
            val data: dynamic = if (kClass != null) {
                tabulator.toKotlinObj(cell.getData())
            } else {
                cell.getData()
            }

            @Suppress("UnsafeCastFromDynamic")
            val component = it(cell, { callback ->
                onRenderedCallback = callback
            }, data)
            val rootElement = document.createElement("div") as HTMLElement
            @Suppress("SENSELESS_COMPARISON")
            if (onRendered != undefined) {
                onRendered {
                    val root = Root(rootElement, ContainerType.NONE, false)
                    tabulator.addCustomRoot(root)
                    root.add(component)
                    if (cell.asDynamic().checkHeight != undefined) cell.checkHeight()
                    (root.getElement()?.parentElement as? HTMLDivElement)?.style?.overflowX = "visible"
                    (root.getElement()?.parentElement as? HTMLDivElement)?.style?.overflowY = "visible"
                    onRenderedCallback?.invoke()
                }
            }
            rootElement
        }
    }

    val tmpTitleFormatterFunction = titleFormatterComponentFunction?.let {
        { cell: Tabulator.CellComponent, _: dynamic,
          onRendered: (callback: () -> Unit) -> Unit ->
            var onRenderedCallback: (() -> Unit)? = null
            val component = it(cell) { callback ->
                onRenderedCallback = callback
            }
            val rootElement = document.createElement("div") as HTMLElement
            @Suppress("SENSELESS_COMPARISON")
            if (onRendered != undefined) {
                onRendered {
                    val root = Root(rootElement, ContainerType.NONE, false)
                    tabulator.addCustomRoot(root)
                    root.add(component)
                    (root.getElement()?.parentElement as? HTMLDivElement)?.style?.overflowX = "visible"
                    (root.getElement()?.parentElement as? HTMLDivElement)?.style?.overflowY = "visible"
                    (root.getElement()?.parentElement?.parentElement?.parentElement?.parentElement as? HTMLDivElement)?.style?.overflowX =
                        "visible"
                    (root.getElement()?.parentElement?.parentElement?.parentElement?.parentElement as? HTMLDivElement)?.style?.overflowY =
                        "visible"
                    (root.getElement()?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement as? HTMLDivElement)?.style?.overflowX =
                        "visible"
                    (root.getElement()?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement as? HTMLDivElement)?.style?.overflowY =
                        "visible"
                    (root.getElement()?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement as? HTMLDivElement)?.style?.overflowX =
                        "visible"
                    (root.getElement()?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement as? HTMLDivElement)?.style?.overflowY =
                        "visible"
                    onRenderedCallback?.invoke()
                }
            }
            rootElement
        }
    }
    val tmpHeaderColumnsMenu: ((Event) -> Array<dynamic>)? = if (this.headerColumnsMenu == true) {
        { _ ->
            val resetTitle = this.headerColumnsMenuResetTitle ?: "Default columns"
            fun resetColumns() {
                val persistenceID =
                    tabulator.jsTabulator?.options?.persistenceID?.let { "tabulator-$it" } ?: "tabulator"
                localStorage.removeItem("$persistenceID-columns")
                window.location.reload()
            }

            val columns = tabulator.jsTabulator?.getColumns(false)?.filter {
                it.getDefinition().title.isNotEmpty()
            }?.map {
                val responsiveHiddenColumns =
                    (tabulator.jsTabulator?.modules?.asDynamic()?.responsiveLayout?.hiddenColumns as Array<Tabulator.ColumnComponent>).map {
                        it.getField()
                    }
                val icon = document.createElement("i")
                icon.classList.add("far")
                icon.classList.add(if (!it.isVisible() && !responsiveHiddenColumns.contains(it.getField())) "fa-square" else "fa-check-square")
                val label = document.createElement("span")
                val title = document.createElement("span")
                title.textContent = " " + it.getDefinition().title
                label.appendChild(icon)
                label.appendChild(title)
                obj {
                    this.label = label
                    this.action = { e: Event ->
                        e.stopPropagation()
                        if (it.isVisible()) {
                            it.hide()
                            icon.classList.remove("fa-check-square")
                            icon.classList.add("fa-square")
                        } else if (responsiveHiddenColumns.contains(it.getField())) {
                            it.show()
                            it.hide()
                            icon.classList.remove("fa-check-square")
                            icon.classList.add("fa-square")
                        } else {
                            it.show()
                            icon.classList.remove("fa-square")
                            icon.classList.add("fa-check-square")
                        }
                        tabulator.jsTabulator?.redraw(true)
                    }
                }
            } ?: emptyList()
            (columns + listOf(obj {
                separator = true
            }, obj {
                val icon = document.createElement("i")
                icon.classList.add("fas")
                icon.classList.add("fa-rotate")
                val label = document.createElement("span")
                val title = document.createElement("span")
                title.textContent = " $resetTitle"
                label.appendChild(icon)
                label.appendChild(title)
                this.label = label
                this.action = { e: Event ->
                    e.stopPropagation()
                    resetColumns()
                }
            })).toTypedArray()
        }
    } else null
    val headerColumnsMenuTitle = this.headerColumnsMenuTitle ?: "Customize"

    val responsiveCollapseAuto = this.formatter == Formatter.RESPONSIVECOLLAPSEAUTO

    return obj {
        if (responsiveCollapseAuto) {
            this.formatter = "responsiveCollapse"
            this.titleFormatter = "tickCross"
            this.titleFormatterParams = obj {
                crossElement = "<i class='fas fa-arrows-up-down'></i>"
            }
            this.width = "40"
            this.headerSort = false
            this.responsive = 0
            this.headerHozAlign = "center"
            this.headerClick = {
                val columnsOpened = document.querySelectorAll("div.tabulator-responsive-collapse-toggle")
                    .asList().firstOrNull()?.let {
                        (it as HTMLElement).classList.contains("open")
                    } ?: false
                if (columnsOpened) {
                    document.querySelectorAll("div.tabulator-responsive-collapse-toggle.open").asList()
                        .forEach {
                            (it as HTMLElement).click()
                        }
                } else {
                    document.querySelectorAll("div.tabulator-responsive-collapse-toggle:not(.open)").asList()
                        .forEach {
                            (it as HTMLElement).click()
                        }
                }
            }
        } else {
            when {
                tmpFormatterFunction != null -> this.formatter = tmpFormatterFunction
                formatterFunction != null -> this.formatter = formatterFunction
                formatter != null -> this.formatter = formatter.formatter
            }
            if (formatterParams != null) this.formatterParams = formatterParams
        }
        if (tmpHeaderColumnsMenu != null) {
            this.headerHozAlign = "center"
            this.headerMenu = tmpHeaderColumnsMenu
            this.headerMenuIcon = "<i class='far fa-square-caret-down'></i> $headerColumnsMenuTitle"
        }
        this.title = i18nTranslator(title)
        if (field != null) this.field = field
        if (columns != null) this.columns = columns.map { it.toJs(tabulator, i18nTranslator, kClass) }.toTypedArray()
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
        if (variableHeight != null) this.variableHeight = variableHeight
        if (editable != null) this.editable = editable
        when {
            tmpEditorFunction != null -> this.editor = tmpEditorFunction
            editorFunction != null -> this.editor = editorFunction
            editor != null -> this.editor = editor.editor
        }
        if (editorParams != null) this.editorParams = editorParams
        if (validatorFunction != null) {
            this.validator = validatorFunction
        } else if (validator != null) {
            this.validator = validator.validator
        }
        if (validatorParams != null) this.validatorParams = validatorParams
        if (download != null) this.download = download
        if (titleDownload != null) this.titleDownload = i18nTranslator(titleDownload)
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
        when {
            tmpTitleFormatterFunction != null -> this.titleFormatter = tmpTitleFormatterFunction
            titleFormatterFunction != null -> this.titleFormatter = titleFormatterFunction
            titleFormatter != null -> this.titleFormatter = titleFormatter.formatter
        }
        if (titleFormatterParams != null) this.titleFormatterParams = titleFormatterParams
        if (headerFilterCustom != null) {
            this.headerFilter = headerFilterCustom
        } else if (headerFilter != null) {
            this.headerFilter = headerFilter.editor
        }
        if (headerFilterParams != null) this.headerFilterParams = headerFilterParams
        if (headerFilterPlaceholder != null) this.headerFilterPlaceholder = i18nTranslator(headerFilterPlaceholder)
        if (headerFilterEmptyCheck != null) this.headerFilterEmptyCheck = headerFilterEmptyCheck
        if (headerFilterFuncCustom != null) {
            this.headerFilterFunc = headerFilterFuncCustom
        } else if (headerFilterFunc != null) {
            this.headerFilterFunc = headerFilterFunc.filter
        }
        if (headerFilterFuncParams != null) this.headerFilterFuncParams = headerFilterFuncParams
        if (headerFilterLiveFilter != null) this.headerFilterLiveFilter = headerFilterLiveFilter
        if (htmlOutput != null) this.htmlOutput = htmlOutput
        if (print != null) this.print = print
        if (formatterPrint != null) this.formatterPrint = formatterPrint else {
            if (formatterComponentFunction != null) {
                this.formatterPrint = { cell: Tabulator.CellComponent, _: dynamic, _: () -> Unit ->
                    cell.getValue()
                }
            }
        }
        if (formatterPrintParams != null) this.formatterPrintParams = formatterPrintParams
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
        if (headerMenu != null) this.headerMenu = headerMenu
        if (headerContextMenu != null) this.headerContextMenu = headerContextMenu
        if (contextMenu != null) this.contextMenu = contextMenu
        if (hozAlign != null) this.hozAlign = hozAlign.align
        if (vertAlign != null) this.vertAlign = vertAlign.valign
        if (clickMenu != null) this.clickMenu = clickMenu
        if (headerHozAlign != null) this.headerHozAlign = headerHozAlign.align
        if (accessor != null) this.accessor = accessor
        if (accessorParams != null) this.accessorParams = accessorParams
        if (maxWidth != null) this.maxWidth = maxWidth
        if (mutatorData != null) this.mutatorData = mutatorData
        if (mutatorDataParams != null) this.mutatorDataParams = mutatorDataParams
        if (mutatorEdit != null) this.mutatorEdit = mutatorEdit
        if (mutatorEditParams != null) this.mutatorEditParams = mutatorEditParams
        if (mutatorClipboard != null) this.mutatorClipboard = mutatorClipboard
        if (mutatorClipboardParams != null) this.mutatorClipboardParams = mutatorClipboardParams
        if (mutator != null) this.mutator = mutator
        if (mutatorParams != null) this.mutatorParams = mutatorParams
        if (maxInitialWidth != null) this.maxInitialWidth = maxInitialWidth
        if (cellPopup != null) this.cellPopup = cellPopup
        if (headerPopup != null) this.headerPopup = headerPopup
        if (headerPopupIcon != null) this.headerPopupIcon = headerPopupIcon
        if (headerContextPopup != null) this.headerContextPopup = headerContextPopup
        if (clickPopup != null) this.clickPopup = clickPopup
        if (contextPopup != null) this.contextPopup = contextPopup
        if (headerMenuIcon != null) this.headerMenuIcon = headerMenuIcon
        if (headerWordWrap != null) this.headerWordWrap = headerWordWrap
        if (headerClickPopup != null) this.headerClickPopup = headerClickPopup
        if (headerDblClickPopup != null) this.headerDblClickPopup = headerDblClickPopup
        if (headerClickMenu != null) this.headerClickMenu = headerClickMenu
        if (headerDblClickMenu != null) this.headerDblClickMenu = headerDblClickMenu
        if (dblClickMenu != null) this.dblClickMenu = dblClickMenu
    } as Tabulator.ColumnDefinition
}

/**
 * Tabulator options.
 */
data class TabulatorOptions<T : Any>(
    val height: String? = null,
    val placeholder: String? = null,
    val placeholderFunc: (() -> String?)? = null,
    val placeholderHeaderFilter: String? = null,
    val footerElement: String? = null,
    val history: Boolean? = null,
    val keybindings: dynamic = null,
    val downloadDataFormatter: dynamic = null,
    val downloadConfig: DownloadConfig? = null,
    val reactiveData: Boolean? = null,
    val autoResize: Boolean? = null,
    val columns: List<ColumnDefinition<T>>? = null,
    val autoColumns: Boolean? = null,
    val autoColumnsFull: Boolean? = null,
    val layout: Layout? = null,
    val layoutColumnsOnNewData: Boolean? = null,
    val responsiveLayout: ResponsiveLayout? = null,
    val responsiveLayoutCollapseStartOpen: Boolean? = null,
    val responsiveLayoutCollapseUseFormatters: Boolean? = null,
    val movableColumns: Boolean? = null,
    val scrollToColumnPosition: ColumnPosition? = null,
    val scrollToColumnIfVisible: Boolean? = null,
    val rowFormatter: ((row: Tabulator.RowComponent) -> Unit)? = null,
    val addRowPos: RowPos? = null,
    val selectableRows: dynamic = null,
    val selectableRowsRangeMode: RangeMode? = null,
    val selectableRowsRollingSelection: Boolean? = null,
    val selectableRowsPersistence: Boolean? = null,
    val selectableRowsCheck: ((row: Tabulator.RowComponent) -> Boolean)? = null,
    val movableRows: Boolean? = null,
    val movableRowsConnectedTables: dynamic = null,
    val movableRowsSender: dynamic = null,
    val movableRowsReceiver: dynamic = null,
    val resizableRows: Boolean? = null,
    val scrollToRowPosition: RowPosition? = null,
    val scrollToRowIfVisible: Boolean? = null,
    val index: String? = null,
    @Suppress("ArrayInDataClass") var data: Array<T>? = null,
    var ajaxURL: String? = null,
    val ajaxParams: dynamic = null,
    val ajaxConfig: dynamic = null,
    val ajaxContentType: dynamic = null,
    val ajaxURLGenerator: ((url: String, config: Any?, params: Any?) -> String)? = null,
    var ajaxRequestFunc: ((url: String, config: Any?, params: Any?) -> Promise<Any>)? = null,
    val progressiveLoad: ProgressiveMode? = null,
    val progressiveLoadDelay: Int? = null,
    val progressiveLoadScrollMargin: Int? = null,
    val dataLoader: Boolean? = null,
    val dataLoaderLoading: String? = null,
    val dataLoaderError: String? = null,
    val initialSort: List<Tabulator.Sorter>? = null,
    val sortOrderReverse: Boolean? = null,
    val initialFilter: List<Tabulator.Filter>? = null,
    val initialHeaderFilter: List<Any?>? = null,
    val pagination: Boolean? = null,
    val paginationMode: PaginationMode? = null,
    val paginationSize: Int? = null,
    val paginationSizeSelector: dynamic = null,
    val paginationElement: dynamic = null,
    val dataReceiveParams: dynamic = null,
    val dataSendParams: dynamic = null,
    val paginationAddRow: AddRowMode? = null,
    val paginationButtonCount: Int? = null,
    val persistenceID: String? = null,
    val persistenceMode: Boolean? = null,
    val persistentLayout: Boolean? = null,
    val persistentSort: Boolean? = null,
    val persistentFilter: Boolean? = null,
    val locale: String? = null,
    var langs: dynamic = null,
    val localized: ((locale: String, lang: Any?) -> Unit)? = null,
    val headerVisible: Boolean? = null,
    val htmlOutputConfig: dynamic = null,
    val printAsHtml: Boolean? = null,
    val printConfig: dynamic = null,
    val printCopyStyle: Boolean? = null,
    val printVisibleRows: Boolean? = null,
    val printHeader: String? = null,
    val printFooter: String? = null,
    val printFormatter: ((tableHolder: Any?, table: Any?) -> Unit)? = null,
    val tabEndNewRow: dynamic = null,
    val invalidOptionWarnings: Boolean? = null,
    val dataTree: Boolean? = null,
    val dataTreeChildField: String? = null,
    val dataTreeCollapseElement: dynamic = null,
    val dataTreeExpandElement: dynamic = null,
    val dataTreeElementColumn: String? = null,
    val dataTreeBranchElement: dynamic = null,
    val dataTreeChildIndent: Number? = null,
    val dataTreeStartExpanded: ((row: Tabulator.RowComponent, level: Number) -> Boolean)? = null,
    val ajaxRequesting: ((url: String, params: Any?) -> Boolean)? = null,
    val ajaxResponse: ((url: String, params: Any?, response: Any?) -> Any)? = null,
    val persistence: dynamic = null,
    val persistenceReaderFunc: dynamic = null,
    val persistenceWriterFunc: dynamic = null,
    val paginationInitialPage: Int? = null,
    val columnHeaderVertAlign: VAlign? = null,
    val maxHeight: String? = null,
    val minHeight: String? = null,
    val rowContextMenu: dynamic = null,
    val dataTreeChildColumnCalcs: Boolean? = null,
    val dataTreeSelectPropagate: Boolean? = null,
    val headerFilterLiveFilterDelay: Int? = null,
    val textDirection: TextDirection? = null,
    val autoColumnsDefinitions: dynamic = null,
    val rowClickMenu: dynamic = null,
    val headerSortElement: dynamic = null,
    val dataTreeFilter: Boolean? = null,
    val dataTreeSort: Boolean? = null,
    val renderVertical: RenderType? = null,
    val renderVerticalBuffer: Int? = null,
    val renderHorizontal: RenderType? = null,
    val columnDefaults: ColumnDefinition<T>? = null,
    val sortMode: SortMode? = null,
    val filterMode: FilterMode? = null,
    val importFormat: ImportFormat? = null,
    val importReader: ImportReader? = null,
    val dataLoaderErrorTimeout: Int? = null,
    val popupContainer: dynamic = null,
    val paginationCounter: dynamic = null,
    val paginationCounterElement: dynamic = null,
    val rowClickPopup: dynamic = null,
    val rowContextPopup: dynamic = null,
    val resizableColumnFit: Boolean? = null,
    val rowHeight: Int? = null,
    val frozenRows: dynamic = null,
    val frozenRowsField: String? = null,
    val headerSortClickElement: HeaderSortClickElement? = null,
    val rowDblClickPopup: dynamic = null,
    val rowDblClickMenu: dynamic = null,
    val responsiveLayoutCollapseFormatter: ((data: Array<Any?>) -> Element)? = null,
    val selectableRange: dynamic = null,
    val selectableRangeColumns: Boolean? = null,
    val selectableRangeRows: Boolean? = null,
    val selectableRangeClearCells: Boolean? = null,
    val selectableRangeClearCellsValue: String? = null,
    val editTriggerEvent: EditTriggerEvent? = null,
    val rowHeader: dynamic = null,
    val spreadsheet: Boolean? = null,
    val spreadsheetColumns: Int? = null,
    val spreadsheetRows: Int? = null,
    val spreadsheetData: dynamic = null,
    val spreadsheetColumnDefinition: dynamic = null,
    val spreadsheetOutputFull: Boolean? = null,
    val spreadsheetSheets: dynamic = null,
    val spreadsheetSheetTabs: Boolean? = null,
    val resizableColumnGuide: Boolean? = null,
    val resizableRowGuide: Boolean? = null,
    val editorEmptyValue: dynamic = null,
    val editorEmptyValueFunc: ((Any?) -> Boolean)? = null,
)

/**
 * An extension function to convert tabulator options class to JS object.
 */
@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "ComplexMethod")
fun <T : Any> TabulatorOptions<T>.toJs(
    tabulator: io.kvision.tabulator.Tabulator<T>,
    i18nTranslator: (String) -> (String),
    kClass: KClass<T>?
): Tabulator.Options {
    val tmpPersistenceWriterFunc = persistenceWriterFunc ?: run {
        if (persistence?.columns?.unsafeCast<Array<String>>()
                ?.contains("field") == true && responsiveLayout == ResponsiveLayout.COLLAPSE
        ) {
            { id: String, type: String, value: dynamic ->
                if (type == "columns") {
                    val responsiveHiddenColumns =
                        (tabulator.jsTabulator?.modules?.asDynamic()?.responsiveLayout?.hiddenColumns as Array<Tabulator.ColumnComponent>).map {
                            it.getField()
                        }
                    val fixedValue = value.unsafeCast<Array<dynamic>>().map {
                        @Suppress("UnsafeCastFromDynamic")
                        if (it.visible == false && responsiveHiddenColumns.contains(it.field))
                            it.visible = true
                        it
                    }.toTypedArray()
                    localStorage.setItem("$id-$type", JSON.stringify(fixedValue))
                } else {
                    localStorage.setItem("$id-$type", JSON.stringify(value))
                }
            }
        } else null
    }

    return obj {
        if (height != null) this.height = height
        if (placeholderFunc != null) {
            this.placeholder = placeholderFunc
        } else if (placeholder != null) {
            this.placeholder = i18nTranslator(placeholder)
        }
        if (placeholderHeaderFilter != null) this.placeholderHeaderFilter = placeholderHeaderFilter
        if (footerElement != null) this.footerElement = i18nTranslator(footerElement)
        if (history != null) this.history = history
        if (keybindings != null) this.keybindings = keybindings
        if (downloadDataFormatter != null) this.downloadDataFormatter = downloadDataFormatter
        if (downloadConfig != null) this.downloadConfig = downloadConfig.toJs()
        if (reactiveData != null) this.reactiveData = reactiveData
        if (autoResize != null) this.autoResize = autoResize
        if (columns != null) this.columns = columns.map { it.toJs(tabulator, i18nTranslator, kClass) }.toTypedArray()
        if (autoColumnsFull == true) {
            this.autoColumns = "full"
        } else if (autoColumns != null) {
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
        if (movableColumns != null) this.movableColumns = movableColumns
        if (scrollToColumnPosition != null) this.scrollToColumnPosition = scrollToColumnPosition.position
        if (scrollToColumnIfVisible != null) this.scrollToColumnIfVisible = scrollToColumnIfVisible
        if (rowFormatter != null) this.rowFormatter = rowFormatter
        if (addRowPos != null) this.addRowPos = addRowPos.position
        if (selectableRows != null) this.selectableRows = selectableRows
        if (selectableRowsRangeMode != null) this.selectableRowsRangeMode = selectableRowsRangeMode.mode
        if (selectableRowsRollingSelection != null) this.selectableRowsRollingSelection = selectableRowsRollingSelection
        if (selectableRowsPersistence != null) this.selectableRowsPersistence = selectableRowsPersistence
        if (selectableRowsCheck != null) this.selectableRowsCheck = selectableRowsCheck
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
        if (filterMode != null) this.filterMode = filterMode.mode
        if (sortMode != null) this.sortMode = sortMode.mode
        if (progressiveLoad != null) this.progressiveLoad = progressiveLoad.mode
        if (progressiveLoadDelay != null) this.progressiveLoadDelay = progressiveLoadDelay
        if (progressiveLoadScrollMargin != null) this.progressiveLoadScrollMargin =
            progressiveLoadScrollMargin
        if (dataLoader != null) this.dataLoader = dataLoader
        if (dataLoaderLoading != null) this.dataLoaderLoading = i18nTranslator(dataLoaderLoading)
        if (dataLoaderError != null) this.dataLoaderError = i18nTranslator(dataLoaderError)
        if (initialSort != null) this.initialSort = initialSort.toTypedArray()
        if (sortOrderReverse != null) this.sortOrderReverse = sortOrderReverse
        if (initialFilter != null) this.initialFilter = initialFilter.toTypedArray()
        if (initialHeaderFilter != null) this.initialHeaderFilter = initialHeaderFilter.toTypedArray()
        if (pagination != null) this.pagination = pagination
        if (paginationMode != null) this.paginationMode = paginationMode.mode
        if (paginationSize != null) this.paginationSize = paginationSize
        if (paginationSizeSelector != null) this.paginationSizeSelector = paginationSizeSelector
        if (paginationElement != null) this.paginationElement = paginationElement
        if (dataReceiveParams != null) this.dataReceiveParams = dataReceiveParams
        if (dataSendParams != null) this.dataSendParams = dataSendParams
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
        if (headerVisible != null) this.headerVisible = headerVisible
        if (htmlOutputConfig != null) this.htmlOutputConfig = htmlOutputConfig
        if (printAsHtml != null) this.printAsHtml = printAsHtml
        if (printConfig != null) this.printConfig = printConfig
        if (printCopyStyle != null) this.printCopyStyle = printCopyStyle
        if (printVisibleRows != null) this.printVisibleRows = printVisibleRows
        if (printHeader != null) this.printHeader = printHeader
        if (printFooter != null) this.printFooter = printFooter
        if (printFormatter != null) this.printFormatter = printFormatter
        if (tabEndNewRow != null) this.tabEndNewRow = tabEndNewRow
        if (invalidOptionWarnings != null) this.invalidOptionWarnings = invalidOptionWarnings
        if (dataTree != null) this.dataTree = dataTree
        if (dataTreeChildField != null) this.dataTreeChildField = dataTreeChildField
        if (dataTreeCollapseElement != null) this.dataTreeCollapseElement = dataTreeCollapseElement
        if (dataTreeExpandElement != null) this.dataTreeExpandElement = dataTreeExpandElement
        if (dataTreeElementColumn != null) this.dataTreeElementColumn = dataTreeElementColumn
        if (dataTreeBranchElement != null) this.dataTreeBranchElement = dataTreeBranchElement
        if (dataTreeChildIndent != null) this.dataTreeChildIndent = dataTreeChildIndent
        if (dataTreeStartExpanded != null) this.dataTreeStartExpanded = dataTreeStartExpanded
        if (ajaxRequesting != null) this.ajaxRequesting = ajaxRequesting
        if (ajaxResponse != null) this.ajaxResponse = ajaxResponse
        if (persistence != null) this.persistence = persistence
        if (persistenceReaderFunc != null) this.persistenceReaderFunc = persistenceReaderFunc
        if (tmpPersistenceWriterFunc != null) this.persistenceWriterFunc = tmpPersistenceWriterFunc
        if (paginationInitialPage != null) this.paginationInitialPage = paginationInitialPage
        if (columnHeaderVertAlign != null) this.columnHeaderVertAlign = columnHeaderVertAlign.valign
        if (maxHeight != null) this.maxHeight = maxHeight
        if (minHeight != null) this.minHeight = minHeight
        if (rowContextMenu != null) this.rowContextMenu = rowContextMenu
        if (dataTreeChildColumnCalcs != null) this.dataTreeChildColumnCalcs = dataTreeChildColumnCalcs
        if (dataTreeSelectPropagate != null) this.dataTreeSelectPropagate = dataTreeSelectPropagate
        if (headerFilterLiveFilterDelay != null) this.headerFilterLiveFilterDelay = headerFilterLiveFilterDelay
        if (textDirection != null) this.textDirection = textDirection.dir
        if (autoColumnsDefinitions != null) this.autoColumnsDefinitions = autoColumnsDefinitions
        if (rowClickMenu != null) this.rowClickMenu = rowClickMenu
        if (headerSortElement != null) this.headerSortElement = headerSortElement
        if (dataTreeFilter != null) this.dataTreeFilter = dataTreeFilter
        if (dataTreeSort != null) this.dataTreeSort = dataTreeSort
        if (renderVertical != null) this.renderVertical = renderVertical.type
        if (renderVerticalBuffer != null) this.renderVerticalBuffer = renderVerticalBuffer
        if (renderHorizontal != null) this.renderHorizontal = renderHorizontal.type
        if (columnDefaults != null) this.columnDefaults = columnDefaults.toJs(tabulator, i18nTranslator, kClass)
        if (importFormat != null) this.importFormat = importFormat.type
        if (importReader != null) this.importReader = importReader.type
        if (dataLoaderErrorTimeout != null) this.dataLoaderErrorTimeout = dataLoaderErrorTimeout
        if (popupContainer != null) this.popupContainer = popupContainer
        if (paginationCounter != null) this.paginationCounter = paginationCounter
        if (paginationCounterElement != null) this.paginationCounterElement = paginationCounterElement
        if (rowClickPopup != null) this.rowClickPopup = rowClickPopup
        if (rowContextPopup != null) this.rowContextPopup = rowContextPopup
        if (resizableColumnFit != null) this.resizableColumnFit = resizableColumnFit
        if (rowHeight != null) this.rowHeight = rowHeight
        if (frozenRows != null) this.frozenRows = frozenRows
        if (frozenRowsField != null) this.frozenRowsField = frozenRowsField
        if (headerSortClickElement != null) this.headerSortClickElement = headerSortClickElement.element
        if (rowDblClickPopup != null) this.rowDblClickPopup = rowDblClickPopup
        if (rowDblClickMenu != null) this.rowDblClickMenu = rowDblClickMenu
        if (responsiveLayoutCollapseFormatter != null) this.responsiveLayoutCollapseFormatter =
            responsiveLayoutCollapseFormatter
        if (selectableRange != null) this.selectableRange = selectableRange
        if (selectableRangeColumns != null) this.selectableRangeColumns = selectableRangeColumns
        if (selectableRangeRows != null) this.selectableRangeRows = selectableRangeRows
        if (selectableRangeClearCells != null) this.selectableRangeClearCells = selectableRangeClearCells
        if (selectableRangeClearCellsValue != null) this.selectableRangeClearCellsValue = selectableRangeClearCellsValue
        if (editTriggerEvent != null) this.editTriggerEvent = editTriggerEvent.event
        if (rowHeader != null) this.rowHeader = rowHeader
        if (spreadsheet != null) this.spreadsheet = spreadsheet
        if (spreadsheetColumns != null) this.spreadsheetColumns = spreadsheetColumns
        if (spreadsheetRows != null) this.spreadsheetRows = spreadsheetRows
        if (spreadsheetData != null) this.spreadsheetData = spreadsheetData
        if (spreadsheetColumnDefinition != null) this.spreadsheetColumnDefinition = spreadsheetColumnDefinition
        if (spreadsheetOutputFull != null) this.spreadsheetOutputFull = spreadsheetOutputFull
        if (spreadsheetSheets != null) this.spreadsheetSheets = spreadsheetSheets
        if (spreadsheetSheetTabs != null) this.spreadsheetSheetTabs = spreadsheetSheetTabs
        if (resizableColumnGuide != null) this.resizableColumnGuide = resizableColumnGuide
        if (resizableRowGuide != null) this.resizableRowGuide = resizableRowGuide
        if (editorEmptyValue != null) this.editorEmptyValue = editorEmptyValue
        if (editorEmptyValueFunc != null) this.editorEmptyValueFunc = editorEmptyValueFunc
    } as Tabulator.Options
}
