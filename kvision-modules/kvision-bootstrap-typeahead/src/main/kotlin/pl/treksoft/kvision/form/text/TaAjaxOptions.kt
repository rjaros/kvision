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

import pl.treksoft.jquery.JQueryXHR

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
 * Data class for Typeahead AJAX options.
 *
 * @constructor
 * @param url the url address
 * @param preprocessQuery a function to process query string for sending
 * @param preprocessData a function to process received data
 * @param beforeSend
 * [JQuery ajax.beforeSend](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param httpType
 * [JQuery ajax.type](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param dataType
 * [JQuery ajax.dataType](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 * @param processData
 * [JQuery ajax.processData](http://api.jquery.com/jquery.ajax/#jQuery-ajax-settings) option
 */
data class TaAjaxOptions(
    val url: String,
    val preprocessQuery: ((String) -> dynamic)? = null,
    val preprocessData: ((dynamic) -> Array<String>)? = null,
    val beforeSend: ((JQueryXHR, dynamic) -> dynamic)? = null,
    val httpType: HttpType = HttpType.GET,
    val dataType: DataType = DataType.JSON,
    val processData: Boolean = true
)
