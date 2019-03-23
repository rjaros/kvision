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
import org.w3c.dom.Node
import pl.treksoft.jquery.JQuery
import pl.treksoft.kvision.panel.Root

/**
 * Base interface for all components.
 */
@Suppress("TooManyFunctions")
interface Component {
    /**
     * Parent of the current component.
     */
    var parent: Container?
    /**
     * Visibility state of the current component.
     */
    var visible: Boolean

    /**
     * Adds given value to the set of CSS classes generated in html code of current component.
     * @param css CSS class name
     * @return current component
     */
    fun addCssClass(css: String): Component

    /**
     * Adds given style object to the set of CSS classes generated in html code of current component.
     * @param css CSS style object
     * @return current component
     */
    fun addCssClass(css: Style): Component

    /**
     * Removes given value from the set of CSS classes generated in html code of current component.
     * @param css CSS class name
     * @return current component
     */
    fun removeCssClass(css: String): Component

    /**
     * Removes given style object from the set of CSS classes generated in html code of current component.
     * @param css CSS style object
     * @return current component
     */
    fun removeCssClass(css: Style): Component

    /**
     * Adds given value to the set of CSS classes generated in html code of parent component.
     * @param css CSS class name
     * @return current component
     */
    fun addSurroundingCssClass(css: String): Component

    /**
     * Adds given style object to the set of CSS classes generated in html code of parent component.
     * @param css CSS style object
     * @return current component
     */
    fun addSurroundingCssClass(css: Style): Component

    /**
     * Removes given value from the set of CSS classes generated in html code of parent component.
     * @param css CSS class name
     * @return current component
     */
    fun removeSurroundingCssClass(css: String): Component

    /**
     * Removes given style object from the set of CSS classes generated in html code of parent component.
     * @param css CSS style object
     * @return current component
     */
    fun removeSurroundingCssClass(css: Style): Component

    /**
     * @suppress
     * Internal function
     * Renders current component as a Snabbdom vnode.
     * @return Snabbdom vnode
     */
    fun renderVNode(): VNode

    /**
     * Returns DOM element bound to the current component.
     * @return DOM element
     */
    fun getElement(): Node?

    /**
     * Returns JQuery element bound to the current component.
     * @return JQuery element
     */
    fun getElementJQuery(): JQuery?

    /**
     * Returns JQuery element bound to the current component as a *dynamic* type.
     * @return JQuery element as a *dynamic* type
     */
    fun getElementJQueryD(): dynamic

    /**
     * @suppress
     * Internal function.
     * Sets **parent** property of current component to null.
     * @return current component
     */
    fun clearParent(): Component

    /**
     * @suppress
     * Internal function.
     * Returns root component - the root node of components tree
     * @return root component
     */
    fun getRoot(): Root?

    /**
     * @suppress
     * Internal function
     * Cleans resources allocated by the current component.
     */
    fun dispose()
}
