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

package io.kvision.jackson

import kotlin.reflect.KClass

/**
 * Multiplatform enum class for Jackson JsonTypeInfo annotation.
 */
@Deprecated("This enum is not required anymore")
enum class Id {
    CLASS
}

/**
 * Multiplatform enum class for Jackson JsonTypeInfo annotation.
 */
@Deprecated("This enum is not required anymore")
enum class As {
    PROPERTY
}

/**
 * Multiplatform version of Jackson JsonTypeInfo annotation.
 */
@Suppress("DEPRECATION")
@Deprecated("This annotation is not required anymore")
annotation class JsonTypeInfo(
    val use: Id,
    val include: As,
    val property: String,
    val defaultImpl: KClass<*>,
    val visible: Boolean
)
