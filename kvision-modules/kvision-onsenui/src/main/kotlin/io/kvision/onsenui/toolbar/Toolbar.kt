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
import io.kvision.html.Div
import io.kvision.onsenui.core.Page
import io.kvision.panel.SimplePanel

/**
 * A toolbar component.
 *
 * @constructor Creates a toolbar component.
 * @param label a label placed automatically in the center section of the toolbar
 * @param inline display the toolbar as an inline element
 * @param static static toolbars are not animated by ons-navigator when pushing or popping pages
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class Toolbar(
    label: String? = null,
    inline: Boolean? = null,
    static: Boolean? = null,
    className: String? = null,
    init: (Toolbar.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * The left section of the toolbar.
     */
    val leftPanel = Div(className = "left toolbar__left")

    /**
     * The center section of the toolbar.
     */
    val centerPanel = Div(label, className = "center toolbar__center")

    /**
     * The right section of the toolbar.
     */
    val rightPanel = Div(className = "right toolbar__right")

    /**
     *  Display the toolbar as an inline element.
     */
    var inline: Boolean? by refreshOnUpdate(inline)

    /**
     * Static toolbars are not animated by ons-navigator when pushing or popping pages.
     */
    var static: Boolean? by refreshOnUpdate(static)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        leftPanel.parent = this
        centerPanel.parent = this
        rightPanel.parent = this
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render(
            "ons-toolbar",
            arrayOf(leftPanel.renderVNode(), centerPanel.renderVNode(), rightPanel.renderVNode())
        )
    }

    /**
     * A DSL builder for the left section of the toolbar.
     */
    open fun left(builder: Div.() -> Unit) {
        leftPanel.builder()
    }

    /**
     * A DSL builder for the center section of the toolbar.
     */
    open fun center(builder: Div.() -> Unit) {
        centerPanel.builder()
    }

    /**
     * A DSL builder for the right section of the toolbar.
     */
    open fun right(builder: Div.() -> Unit) {
        rightPanel.builder()
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        if (inline == true) {
            attributeSetBuilder.add("inline")
        }
        if (static == true) {
            attributeSetBuilder.add("static")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    /**
     * Shows or hides the toolbar.
     * @param visible whether the toolbar is visible
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun setVisibility(visible: Boolean) {
        return getElement()?.asDynamic()?.setVisibility(visible)
    }

    /**
     * Shows the toolbar.
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun showToolbar() {
        return getElement()?.asDynamic()?.show()
    }

    /**
     * Hides the toolbar.
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun hideToolbar() {
        return getElement()?.asDynamic()?.hide()
    }

    override fun dispose() {
        super.dispose()
        leftPanel.dispose()
        centerPanel.dispose()
        rightPanel.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Page.toolbar(
    label: String? = null,
    inline: Boolean? = null,
    static: Boolean? = null,
    className: String? = null,
    init: (Toolbar.() -> Unit)? = null
): Toolbar {
    val toolbar = Toolbar(label, inline, static, className, init)
    this.toolbarPanel = toolbar
    return toolbar
}
