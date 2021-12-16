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
package io.kvision.html

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import io.kvision.i18n.I18n
import io.kvision.utils.Serialization.toObj

/**
 * Handlebars templates helper interface.
 */
interface Template {
    var content: String?
    var rich: Boolean

    /**
     * @suppress
     * Internal property
     */
    var templateDataObj: Any?
    var template: ((Any?) -> String)?
    var templates: Map<String, (Any?) -> String>

    /**
     * Handlebars template data object.
     */
    var templateData: Any?
        get() {
            return templateDataObj
        }
        set(value) {
            if (!rich) rich = true
            templateDataObj = value
            content = template?.invoke(value) ?: templates[I18n.language]?.invoke(value)
        }
}

/**
 * Extension function to set serializable object as a template data.
 */
fun <K> Template.setData(obj: K, serializer: SerializationStrategy<K>) {
    @Suppress("UnsafeCastFromDynamic")
    this.templateData = obj.toObj(serializer)
}

/**
 * Extension function to set serializable object as a template data.
 */
inline fun <reified K : Any> Template.setData(obj: K) {
    this.setData(obj, serializer())
}
