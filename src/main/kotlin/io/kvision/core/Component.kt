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
package io.kvision.core

import io.kvision.panel.Root
import io.kvision.snabbdom.VNode
import org.w3c.dom.HTMLElement

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
     */
    fun addCssClass(css: String)

    /**
     * Adds given style object to the set of CSS classes generated in html code of current component.
     * @param css CSS style object
     */
    fun addCssStyle(css: Style)

    /**
     * Removes given value from the set of CSS classes generated in html code of current component.
     * @param css CSS class name
     */
    fun removeCssClass(css: String)

    /**
     * Checks whether the given value is present in the set of CSS classes.
     * @param css CSS class name
     */
    fun hasCssClass(css: String): Boolean

    /**
     * Removes given style object from the set of CSS classes generated in html code of current component.
     * @param css CSS style object
     */
    fun removeCssStyle(css: Style)

    /**
     * Adds given value to the set of CSS classes generated in html code of parent component.
     * @param css CSS class name
     */
    fun addSurroundingCssClass(css: String)

    /**
     * Adds given style object to the set of CSS classes generated in html code of parent component.
     * @param css CSS style object
     */
    fun addSurroundingCssStyle(css: Style)

    /**
     * Removes given value from the set of CSS classes generated in html code of parent component.
     * @param css CSS class name
     */
    fun removeSurroundingCssClass(css: String)

    /**
     * Removes given style object from the set of CSS classes generated in html code of parent component.
     * @param css CSS style object
     */
    fun removeSurroundingCssStyle(css: Style)

    /**
     * Returns the value of an additional attribute.
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    fun getAttribute(name: String): String?

    /**
     * Sets the value of additional attribute.
     * @param name the name of the attribute
     * @param value the value of the attribute
     */
    fun setAttribute(name: String, value: String)

    /**
     * Removes the value of additional attribute.
     * @param name the name of the attribute
     */
    fun removeAttribute(name: String)

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
    fun getElement(): HTMLElement?

    /**
     * Returns DOM element bound to the current component as a *dynamic* type.
     * @return DOM element as a *dynamic* type
     */
    fun getElementD(): dynamic

    /**
     * @suppress
     * Internal function.
     * Sets **parent** property of current component to null.
     */
    fun clearParent()

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

    /**
     * Makes the element focused.
     */
    fun focus() {
        getElementD()?.focus()
    }

    /**
     * Makes the element blur.
     */
    fun blur() {
        getElementD()?.blur()
    }

    /**
     * The supplied function is called before the component is disposed.
     */
    fun addBeforeDisposeHook(hook: () -> Unit): Boolean

    /**
     * The supplied function is called after the component is removed from the DOM.
     */
    fun addAfterDestroyHook(hook: () -> Unit): Boolean

    /**
     * The supplied function is called after the component is inserted into the DOM.
     */
    fun addAfterInsertHook(hook: (VNode) -> Unit): Boolean

    /**
     * Executes given function within a single rendering process.
     */
    fun <T> singleRender(block: () -> T): T

    /**
     * Executes given function within a single rendering process asynchronously.
     */
    fun singleRenderAsync(block: () -> Unit)
}
