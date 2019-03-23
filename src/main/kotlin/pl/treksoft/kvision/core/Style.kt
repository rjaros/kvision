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
package pl.treksoft.kvision.core

import com.github.snabbdom.VNode
import com.github.snabbdom.h
import org.w3c.dom.Node
import pl.treksoft.jquery.JQuery
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.panel.Root

/**
 * CSS style object.
 *
 * @constructor
 * @param className optional name of the CSS class, it will be generated if not specified
 * @param parentStyle parent CSS style object
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class Style(className: String? = null, parentStyle: Style? = null, init: (Style.() -> Unit)? = null) :
    StyledComponent() {

    override var parent: Container? = Root.getFirstRoot()

    private val newClassName: String = if (parentStyle == null) {
        className ?: "kv_styleclass_${counter++}"
    } else {
        "${parentStyle.className} " + (className ?: ".kv_styleclass_${counter++}")
    }

    /**
     * The name of the CSS class.
     */
    var className: String by refreshOnUpdate(newClassName)

    init {
        styles.add(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override var visible: Boolean = true
        set(value) {
            val oldField = field
            field = value
            if (oldField != field) refresh()
        }

    private var vnode: VNode? = null

    override fun addCssClass(css: String): Component {
        return this
    }

    override fun removeCssClass(css: String): Component {
        return this
    }

    override fun addSurroundingCssClass(css: String): Component {
        return this
    }

    override fun removeSurroundingCssClass(css: String): Component {
        return this
    }

    override fun addCssClass(css: Style): Component {
        return this
    }

    override fun removeCssClass(css: Style): Component {
        return this
    }

    override fun addSurroundingCssClass(css: Style): Component {
        return this
    }

    override fun removeSurroundingCssClass(css: Style): Component {
        return this
    }

    override fun renderVNode(): VNode {
        return h("style", arrayOf(generateStyle()))
    }

    internal fun generateStyle(): String {
        val styles = getSnStyle()
        return ".$className {\n" + styles.joinToString("\n") {
            "${it.first}: ${it.second};"
        } + "\n}"
    }

    override fun getElement(): Node? {
        return this.vnode?.elm
    }

    override fun getElementJQuery(): JQuery? {
        return getElement()?.let { jQuery(it) }
    }

    override fun getElementJQueryD(): dynamic {
        return getElement()?.let { jQuery(it).asDynamic() }
    }

    override fun clearParent(): Component {
        this.parent = null
        return this
    }

    override fun getRoot(): Root? {
        return this.parent?.getRoot()
    }

    override fun dispose() {
        styles.remove(this)
    }

    companion object {
        internal var counter = 0
        internal var styles = mutableListOf<Style>()

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Widget.style(className: String? = null, init: (Style.() -> Unit)? = null): Style {
            val style = Style(className, null, init)
            this.addCssClass(style)
            return style
        }

        /**
         * DSL builder extension function for cascading styles.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Style.style(className: String? = null, init: (Style.() -> Unit)? = null): Style {
            return Style(className, this, init)
        }
    }

}
