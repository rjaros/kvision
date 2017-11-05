package pl.treksoft.kvision.form.select

import pl.treksoft.kvision.snabbdom.obj

enum class HttpType(val type: String) {
    GET("GET"),
    POST("POST")
}

enum class DataType(val type: String) {
    JSON("json"),
    JSONP("jsonp"),
    XML("xml"),
    TEXT("text"),
    SCRIPT("script")
}

data class AjaxOptions(val url: String, val processData: (dynamic) -> dynamic,
                       val processParams: dynamic = null, val httpType: HttpType = HttpType.GET,
                       val dataType: DataType = DataType.JSON, val minLength: Int = 0,
                       val cache: Boolean = true, val clearOnEmpty: Boolean = true, val clearOnError: Boolean = true,
                       val emptyRequest: Boolean = false, val preserveSelected: Boolean = true,
                       val requestDelay: Int = 300, val restoreOnError: Boolean = false)

fun AjaxOptions.toJs(): dynamic {
    return obj {
        this.ajax = obj {
            this.url = url
            this.type = httpType.type
            this.data = processParams
        }
        this.preprocessData = processData
    }
}

data class AjaxData(val value: String, val text: String? = null)
