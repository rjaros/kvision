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

package io.kvision.window

import org.w3c.dom.Element

/**
 * Resize observer box size.
 */
external class BoxSize {
    val blockSize: Number
    val inlineSize: Number
}

/**
 * Resize observer entry.
 */
external class ResizeObserverEntry {
    val target: Element
    val borderBoxSize: Array<BoxSize>
    val contentBoxSize: Array<BoxSize>
    val devicePixelContentBoxSize: Array<BoxSize>
}

/**
 * Native JavaScript resize observer.
 */
external class ResizeObserver(callback: (Array<ResizeObserverEntry>, ResizeObserver) -> Unit) {
    fun observe(target: Element, options: dynamic = definedExternally)
    fun unobserve(target: Element)
    fun disconnect()
}
