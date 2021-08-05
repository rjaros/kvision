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
package io.kvision.form.select

import io.kvision.jquery.JQueryAjaxSettings
import io.kvision.jquery.JQueryXHR
import io.kvision.BootstrapSelectModule.AJAX_REQUEST_DELAY
import io.kvision.BootstrapSelectModule.KVNULL
import io.kvision.i18n.I18n
import io.kvision.utils.obj

/**
 * HTTP protocol type for the AJAX call.
 */
enum class HttpType(internal val type: String) {
    GET("GET"),
    POST("POST")
}

/**
 * Data type for the AJAX call.
 */
enum class DataType(internal val type: String) {
    JSON("json"),
    JSONP("jsonp"),
    XML("xml"),
    TEXT("text"),
    SCRIPT("script")
}

/**
 * Data class for AJAX options.
 *
 * @constructor
 * @param url the url address
 * @param preprocessData
 * [AjaxBootstrapSelect preprocessOption](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionspreprocessdata)
 * option
 * @param beforeSend
 * [JQuery ajax.beforeSend](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param data
 * [JQuery ajax.data](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param httpType
 * [JQuery ajax.type](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param dataType
 * [JQuery ajax.dataType](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param minLength
 * [AjaxBootstrapSelect minLength](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionsminlength) option
 * @param cache
 * [AjaxBootstrapSelect cache](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionscache) option
 * @param clearOnEmpty
 * [AjaxBootstrapSelect clearOnEmpty](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionsclearonempty) option
 * @param clearOnError
 * [AjaxBootstrapSelect clearOnError](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionsclearonerror) option
 * @param emptyRequest
 * [AjaxBootstrapSelect emptyRequest](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionsemptyrequest) option
 * @param requestDelay
 * [AjaxBootstrapSelect requestDelay](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionsrequestdelay) option
 * @param restoreOnError
 * [AjaxBootstrapSelect restoreOnError](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionsrestoreonerror)
 * @param preserveSelected
 * [AjaxBootstrapSelect preserveSelected](https://github.com/truckingsim/Ajax-Bootstrap-Select#optionspreserveselected)
 * option
 * @param processData
 * [JQuery ajax.processData](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 */
data class AjaxOptions(
    val url: String? = null,
    val preprocessData: ((dynamic) -> dynamic)? = null,
    val beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> dynamic)? = null,
    val data: dynamic = null,
    val httpType: HttpType = HttpType.GET,
    val dataType: DataType = DataType.JSON,
    val minLength: Int = 0,
    val cache: Boolean = true,
    val clearOnEmpty: Boolean = true,
    val clearOnError: Boolean = true,
    val emptyRequest: Boolean = false,
    val requestDelay: Int = AJAX_REQUEST_DELAY,
    val restoreOnError: Boolean = false,
    val preserveSelected: Boolean = false,
    val processData: Boolean = false
)

/**
 * Convert AjaxOptions to JavaScript JSON object.
 * @param emptyOption add an empty position as the first select option
 * @return JSON object
 */
fun AjaxOptions.toJs(emptyOption: Boolean): dynamic {
    val procData = { data: dynamic ->
        val processedData = this.preprocessData?.invoke(data) ?: data
        if (emptyOption) {
            val ret = mutableListOf(obj {
                this.value = KVNULL
                this.text = ""
            })
            @Suppress("UnsafeCastFromDynamic")
            ret.addAll((processedData as Array<dynamic>).asList())
            ret.toTypedArray()
        } else {
            processedData
        }
    }
    val language = I18n.language
    return obj {
        this.ajax = obj {
            this.url = url ?: "/"
            this.method = httpType.type
            this.dataType = dataType.type
            this.data = data
            this.beforeSend = beforeSend
            this.contentType = "application/json"
        }
        this.preprocessData = procData
        this.minLength = minLength
        this.cache = cache
        this.clearOnEmpty = clearOnEmpty
        this.clearOnError = clearOnError
        this.emptyRequest = emptyRequest
        this.preserveSelected = preserveSelected
        this.requestDelay = requestDelay
        this.restoreOnError = restoreOnError
        this.langCode = language
        this.processData = processData
        this.preserveSelectedOrder = true
    }
}
