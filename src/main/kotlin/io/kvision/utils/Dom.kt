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

package io.kvision.utils

import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.Node

fun Node.toggle() {
    if (this.asDynamic().style.display == "none") {
        this.asDynamic().style.display = ""
    } else {
        this.asDynamic().style.display = "none"
    }
}

fun Node.width(): Int {
    return window.getComputedStyle(this.unsafeCast<Element>()).width.replace("px", "").toIntOrNull() ?: 0
}

fun Node.height(): Int {
    return window.getComputedStyle(this.unsafeCast<Element>()).height.replace("px", "").toIntOrNull() ?: 0
}

fun Node.offsetLeft(): Int {
    return this.asDynamic().offsetLeft?.unsafeCast<Int>() ?: 0
}

fun Node.offsetTop(): Int {
    return this.asDynamic().offsetTop?.unsafeCast<Int>() ?: 0
}

fun Node.offsetWidth(): Int {
    return this.asDynamic().offsetWidth?.unsafeCast<Int>() ?: 0
}

fun Node.offsetHeight(): Int {
    return this.asDynamic().offsetHeight?.unsafeCast<Int>() ?: 0
}
