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

package pl.treksoft.kvision.onsenui.toolbar

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.onsenui.core.Page
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.set

/**
 * An toolbar component located at the bottom of the page.
 *
 * @constructor Creates a bottom toolbar component.
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class BottomToolbar(
    classes: Set<String> = setOf(),
    init: (BottomToolbar.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-bottom-toolbar", childrenVNodes())
    }

    override fun buildAttributesSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributesSet(attributeSetBuilder)
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Page.bottomToolbar(
    classes: Set<String>? = null,
    className: String? = null,
    init: (BottomToolbar.() -> Unit)? = null
): BottomToolbar {
    val bottomToolbar = BottomToolbar(classes ?: className.set, init)
    this.add(bottomToolbar)
    return bottomToolbar
}
