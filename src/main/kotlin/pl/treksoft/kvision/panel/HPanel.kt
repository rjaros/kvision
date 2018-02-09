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
package pl.treksoft.kvision.panel

/**
 * The container with horizontal layout.
 *
 * This is a special case of the flexbox layout.
 *
 * @constructor
 * @param wrap flexbox wrap
 * @param justify flexbox content justification
 * @param alignItems flexbox items alignment
 * @param spacing spacing between columns/rows
 * @param classes a set of CSS class names
 */
open class HPanel(
    wrap: FLEXWRAP? = null, justify: FLEXJUSTIFY? = null, alignItems: FLEXALIGNITEMS? = null, spacing: Int? = null,
    classes: Set<String> = setOf()
) : FlexPanel(
    null,
    wrap, justify, alignItems, null, spacing, classes
)
