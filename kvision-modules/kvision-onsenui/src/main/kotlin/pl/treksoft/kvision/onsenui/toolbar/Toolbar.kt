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
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.onsenui.core.Page
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.set

/**
 * A toolbar component.
 *
 * @constructor Creates a toolbar component.
 * @param label a label placed automatically in the center section of the toolbar
 * @param inline display the toolbar as an inline element
 * @param static static toolbars are not animated by ons-navigator when pushing or popping pages
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class Toolbar(
    label: String? = null,
    inline: Boolean? = null,
    static: Boolean? = null,
    classes: Set<String> = setOf(),
    init: (Toolbar.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * The left section of the toolbar.
     */
    val leftPanel = Div(classes = setOf("left", "toolbar__left"))

    /**
     * The center section of the toolbar.
     */
    val centerPanel = Div(label, classes = setOf("center", "toolbar__center"))

    /**
     * The right section of the toolbar.
     */
    val rightPanel = Div(classes = setOf("right", "toolbar__right"))

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

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        if (inline == true) {
            sn.add("inline" to "inline")
        }
        if (static == true) {
            sn.add("static" to "static")
        }
        modifier?.let {
            sn.add("modifier" to it)
        }
        return sn
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
    classes: Set<String>? = null,
    className: String? = null,
    init: (Toolbar.() -> Unit)? = null
): Toolbar {
    val toolbar = Toolbar(label, inline, static, classes ?: className.set, init)
    this.toolbarPanel = toolbar
    return toolbar
}
