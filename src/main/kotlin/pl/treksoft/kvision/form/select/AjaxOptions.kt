package pl.treksoft.kvision.form.select

import pl.treksoft.kvision.core.KVManager.AJAX_REQUEST_DELAY
import pl.treksoft.kvision.core.KVManager.KVNULL
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
                       val requestDelay: Int = AJAX_REQUEST_DELAY, val restoreOnError: Boolean = false)

fun AjaxOptions.toJs(emptyOption: Boolean): dynamic {
    val procData = { data: dynamic ->
        val processedData = this.processData(data)
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
    return obj {
        this.ajax = obj {
            this.url = url
            this.type = httpType.type
            this.dataType = dataType.type
            this.data = processParams
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
    }
}
