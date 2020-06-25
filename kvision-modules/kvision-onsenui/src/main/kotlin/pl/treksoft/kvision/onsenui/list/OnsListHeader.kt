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

package pl.treksoft.kvision.onsenui.list

import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.CustomTag
import pl.treksoft.kvision.utils.set

/**
 * An Onsen UI list header component.
 *
 * @constructor Creates a list header component.
 * @param content the content of the component.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsListHeader(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String> = setOf(),
    init: (OnsListHeader.() -> Unit)? = null
) : CustomTag("ons-list-header", content, rich, align, classes) {

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        modifier?.let {
            sn.add("modifier" to it)
        }
        return sn
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun OnsList.header(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsListHeader.() -> Unit)? = null
): OnsListHeader {
    val onsListHeader = OnsListHeader(content, rich, align, classes ?: className.set, init)
    this.add(onsListHeader)
    return onsListHeader
}
