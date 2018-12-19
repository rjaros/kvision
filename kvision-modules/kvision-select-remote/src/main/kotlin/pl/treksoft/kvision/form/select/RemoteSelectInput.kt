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
package pl.treksoft.kvision.form.select

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.list
import kotlinx.serialization.stringify
import pl.treksoft.kvision.remote.JsonRpcRequest
import pl.treksoft.kvision.remote.RemoteSelectOption
import pl.treksoft.kvision.remote.SpringServiceManager
import pl.treksoft.kvision.utils.JSON
import pl.treksoft.kvision.utils.obj
import kotlin.js.JSON as NativeJSON

external fun decodeURIComponent(encodedURI: String): String

@UseExperimental(ImplicitReflectionSerializer::class)
open class RemoteSelectInput<T : Any>(
    value: String? = null,
    serviceManager: SpringServiceManager<T>,
    function: T.(String) -> List<RemoteSelectOption>,
    multiple: Boolean = false,
    classes: Set<String> = setOf()
) : SelectInput(null, value, multiple, null, classes) {
    init {
        val (url, method) =
                serviceManager.getCalls()[function.toString().replace("\\s".toRegex(), "")]
                    ?: throw IllegalStateException("Function not specified!")
        val data = obj {
            q = "{{{q}}}"
        }
        ajaxOptions = AjaxOptions(url, preprocessData = {
            JSON.plain.parse(RemoteSelectOption.serializer().list, it.result as String)
        }, data = data, beforeSend = { _, b ->
            val q = decodeURIComponent(b.data.substring(2))
            b.data = JSON.plain.stringify(JsonRpcRequest(0, url, listOf(q)))
            true
        }, httpType = HttpType.valueOf(method.name))
    }

}
