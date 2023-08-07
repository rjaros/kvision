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

package io.kvision.form.text

import org.w3c.dom.HTMLElement

/**
 * Generic text input mask controller.
 */
interface Mask {
    /**
     * Get current, unmasked value.
     */
    fun getValue(): String?

    /**
     * Install callback when the input text value is changed.
     */
    fun onChange(callback: (String?) -> Unit)

    /**
     * Refresh the mask controller after external change.
     */
    fun refresh()

    /**
     * Destroy and cleanup.
     */
    fun destroy()
}

/**
 * Generic text input mask options.
 */
interface MaskOptions {
    fun maskNumericValue(value: String): String
}

/**
 * Text input mask controller factory.
 */
interface MaskFactory {
    /**
     * Create text input mask controller.
     */
    fun createMask(element: HTMLElement, options: MaskOptions): Mask
}

/**
 * Text input mask controller manager.
 */
object MaskManager {
    /**
     * Current text input mask controller factory
     */
    var factory: MaskFactory? = null
}
