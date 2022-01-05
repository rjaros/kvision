/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2022-present aSemy
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

package io.kvision.test

object Formatting {

    /**
     * Format a HTML string in a standardized manner, with one HTML element per line.
     * This helps with highlighting HTML differences in test assertions.
     */
    fun normalizeHtml(raw: String): String {
        return raw
            .replace("<", "\n<")
            .replace(">", ">\n")
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .joinToString("\n")
    }

    /** @see [normalizeHtml] */
    fun normalizeHtml(raw: String?): String? {
        return if (raw == null) null else normalizeHtml(raw)
    }

}
