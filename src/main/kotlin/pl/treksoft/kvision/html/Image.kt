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
package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget

/**
 * Image shapes.
 */
enum class IMAGESHAPE(internal val className: String) {
    ROUNDED("img-rounded"),
    CIRCLE("img-circle"),
    THUMBNAIL("img-thumbnail")
}

/**
 * Image component.
 *
 * @constructor
 * @param src image URL
 * @param alt alternative text
 * @param responsive determines if the image is rendered as responsive
 * @param shape image shape
 * @param centered determines if the image is rendered centered
 * @param classes a set of CSS class names
 */
open class Image(
    src: ResString, alt: String? = null, responsive: Boolean = false, shape: IMAGESHAPE? = null,
    centered: Boolean = false, classes: Set<String> = setOf()
) : Widget(classes) {
    /**
     * URL of the image.
     */
    var src = src
        set(value) {
            field = value
            refresh()
        }
    /**
     * The alternative text of the image.
     */
    var alt = alt
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the image is rendered as responsive.
     */
    var responsive = responsive
        set(value) {
            field = value
            refresh()
        }
    /**
     * The shape of the image.
     */
    var shape = shape
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the image is rendered as centered.
     */
    var centered = centered
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return render("img")
    }

    override fun getSnAttrs(): List<StringPair> {
        val pr = super.getSnAttrs().toMutableList()
        pr.add("src" to src)
        alt?.let {
            pr.add("alt" to it)
        }
        return pr
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (responsive) {
            cl.add("img-responsive" to true)
        }
        if (centered) {
            cl.add("center-block" to true)
        }
        shape?.let {
            cl.add(it.className to true)
        }
        return cl
    }
}
