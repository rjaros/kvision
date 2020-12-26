/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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

package pl.treksoft.kvision.core

import com.github.snabbdom.Attrs
import pl.treksoft.kvision.utils.snAttrs

interface AttributeSetBuilder {
    fun add(name: String, value: String = name)
    fun addAll(attributes: Map<String, String>) {
        attributes.forEach { (key, value) -> add(key, value) }
    }
    fun add(attribute: DomAttribute?) {
        if (attribute != null) {
            add(attribute.attributeName, attribute.attributeValue)
        }
    }
}

internal class AttributeSetBuilderImpl : AttributeSetBuilder {
    val attributes: Attrs
        get() = snAttrs(_attributes)

    private val _attributes: MutableMap<String, String> = HashMap()

    override fun add(name: String, value: String) {
        _attributes[name] = value
    }

    override fun addAll(attributes: Map<String, String>) {
        this._attributes.putAll(attributes)
    }
}

fun buildAttributeSet(delegate: (builder: AttributeSetBuilder) -> Unit): Attrs =
    AttributeSetBuilderImpl().also { delegate(it) }.attributes
