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

package io.kvision.core

import io.kvision.snabbdom.Classes

/**
 * A builder in order to create a set of CSS-classes
 */
interface ClassSetBuilder {
    fun add(value: String)

    fun add(value: CssClass?) {
        if (value != null) {
            add(value.className)
        }
    }

    fun addAll(values: Collection<String>)
}

internal class ClassSetBuilderImpl : ClassSetBuilder {
    @Suppress("UnsafeCastFromDynamic")
    val classes: Classes
        get() = js("Object").assign(js("{}"), _classes)

    private val _classes = js("{}")

    override fun add(value: String) {
        if (value.isNotEmpty()) {
            _classes[value] = true
        }
    }

    override fun addAll(values: Collection<String>) {
        values.forEach { add(it) }
    }
}

fun buildClassSet(delegate: (builder: ClassSetBuilder) -> Unit): Classes =
    ClassSetBuilderImpl().also { delegate(it) }.classes
