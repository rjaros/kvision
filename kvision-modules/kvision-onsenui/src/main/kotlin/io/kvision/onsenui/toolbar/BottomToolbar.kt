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

package io.kvision.onsenui.toolbar

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.onsenui.core.Page
import io.kvision.panel.SimplePanel

/**
 * An toolbar component located at the bottom of the page.
 *
 * @constructor Creates a bottom toolbar component.
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class BottomToolbar(
    className: String? = null,
    init: (BottomToolbar.() -> Unit)? = null
) : SimplePanel(className) {

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

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
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
    className: String? = null,
    init: (BottomToolbar.() -> Unit)? = null
): BottomToolbar {
    val bottomToolbar = BottomToolbar(className, init)
    this.add(bottomToolbar)
    return bottomToolbar
}
