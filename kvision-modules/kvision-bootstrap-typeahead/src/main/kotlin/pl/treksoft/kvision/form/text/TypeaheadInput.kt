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
package pl.treksoft.kvision.form.text

import com.github.snabbdom.VNode
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set

enum class ShowHintOnFocus {
    NO,
    YES,
    ALL
}

/**
 * The basic component for typeahead control.
 *
 * @constructor
 * @param options a static list of options
 * @param taAjaxOptions AJAX options for remote data source
 * @param source source function for data source
 * @param items the max number of items to display in the dropdown
 * @param minLength the minimum character length needed before triggering dropdown
 * @param delay a delay between lookups
 * @param type text input type (default "text")
 * @param value text input value
 * @param classes a set of CSS class names
 */
@Suppress("TooManyFunctions")
open class TypeaheadInput(
    options: List<String>? = null, taAjaxOptions: TaAjaxOptions? = null,
    source: ((String, (Array<String>) -> Unit) -> Unit)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null, classes: Set<String> = setOf()
) : TextInput(type, value, classes) {

    /**
     * A static list of options for a typeahead control
     */
    var options by refreshOnUpdate(options) { refreshTypeahead() }

    /**
     * AJAX options for remote data source
     */
    var taAjaxOptions by refreshOnUpdate(taAjaxOptions) { refreshTypeahead() }

    /**
     * Source function for data source
     */
    var source by refreshOnUpdate(source) { refreshTypeahead() }

    /**
     * The max number of items to display in the dropdown
     */
    var items by refreshOnUpdate(items) { refreshTypeahead() }

    /**
     * The minimum character length needed before triggering dropdown
     */
    var minLength by refreshOnUpdate(minLength) { refreshTypeahead() }

    /**
     * Determines if hints should be shown as soon as the input gets focus.
     */
    var showHintOnFocus by refreshOnUpdate(ShowHintOnFocus.NO) { refreshTypeahead() }

    /**
     * Determines if the first suggestion is selected automatically.
     */
    var autoSelect by refreshOnUpdate(true) { refreshTypeahead() }

    /**
     * A delay between lookups.
     */
    var delay by refreshOnUpdate(delay) { refreshTypeahead() }

    /**
     * Determines if the menu is the same size as the input it is attached to.
     */
    var fitToElement by refreshOnUpdate(false) { refreshTypeahead() }

    init {
        autocomplete = false
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        getElementJQueryD()?.typeahead(getSettingsObj())
        refreshState()
    }

    override fun afterDestroy() {
        getElementJQueryD()?.typeahead("destroy")
    }

    @Suppress("UnsafeCastFromDynamic")
    protected fun getSettingsObj(): dynamic {
        val sourceOpt = when {
            options != null -> {
                options?.toTypedArray()?.asDynamic()
            }
            taAjaxOptions != null -> { query: String, callback: (Array<String>) -> Unit ->
                taAjaxOptions?.let { ajaxOptions ->
                    val data = ajaxOptions.preprocessQuery?.invoke(query) ?: obj {
                        this.query = query
                    }
                    jQuery.ajax(ajaxOptions.url!!, obj {
                        this.contentType = "application/json"
                        this.data = data
                        this.method = ajaxOptions.httpType.type
                        this.beforeSend = ajaxOptions.beforeSend
                        this.dataType = ajaxOptions.dataType.type
                        this.processData = ajaxOptions.processData
                        this.success =
                            { data: dynamic, _: Any, _: Any ->
                                val processedData = ajaxOptions.preprocessData?.invoke(data) ?: data
                                callback(processedData)
                            }
                        this.error =
                            { xhr: JQueryXHR, _: String, errorText: String ->
                                val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                                    JSON.stringify(xhr.responseJSON)
                                } else if (xhr.responseText != undefined) {
                                    xhr.responseText
                                } else {
                                    errorText
                                }
                                console.log(message)
                                callback(emptyArray())
                            }
                    })
                }
            }
            source != null -> source
            else -> {
                emptyArray<String>().asDynamic()
            }
        }
        val showHint = when (showHintOnFocus) {
            ShowHintOnFocus.NO -> false
            ShowHintOnFocus.YES -> true
            ShowHintOnFocus.ALL -> "all"
        }
        return obj {
            this.source = sourceOpt
            this.items = items
            this.minLength = minLength
            this.showHintOnFocus = showHint
            this.autoSelect = autoSelect
            this.delay = delay
            this.fitToElement = fitToElement
            this.afterSelect = { v: String ->
                this@TypeaheadInput.value = v
            }
        }
    }

    protected fun refreshTypeahead() {
        getElementJQueryD()?.typeahead("destroy")
        getElementJQueryD()?.typeahead(getSettingsObj())
    }

}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.typeaheadInput(
    options: List<String>? = null, taAjaxOptions: TaAjaxOptions? = null,
    source: ((String, (Array<String>) -> Unit) -> Unit)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TypeaheadInput.() -> Unit)? = null
): TypeaheadInput {
    val typeaheadInput = TypeaheadInput(
        options,
        taAjaxOptions,
        source,
        items,
        minLength,
        delay,
        type,
        value,
        classes ?: className.set
    ).apply {
        init?.invoke(
            this
        )
    }
    this.add(typeaheadInput)
    return typeaheadInput
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.typeaheadInput(
    state: ObservableState<S>,
    options: List<String>? = null, taAjaxOptions: TaAjaxOptions? = null,
    source: ((String, (Array<String>) -> Unit) -> Unit)? = null,
    items: Int? = 8, minLength: Int = 1, delay: Int = 0,
    type: TextInputType = TextInputType.TEXT, value: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (TypeaheadInput.(S) -> Unit)
) = typeaheadInput(
    options,
    taAjaxOptions,
    source,
    items,
    minLength,
    delay,
    type,
    value,
    classes,
    className
).bind(state, true, init)
