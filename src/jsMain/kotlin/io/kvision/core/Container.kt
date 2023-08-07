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

import io.kvision.html.TextNode

/**
 * Base interface for all containers.
 */
interface Container : Component {
    /**
     * Adds given component to the current container.
     * @param child child component
     */
    fun add(child: Component)

    /**
     * Adds given component to the current container at the given position.
     * @param position the position to insert child component
     * @param child the child component
     */
    fun add(position: Int, child: Component)

    /**
     * Adds a list of components to the current container.
     * @param children list of child components
     */
    fun addAll(children: List<Component>)

    /**
     * Operator function for adding children in a DSL style.
     * @param children children components
     */
    operator fun invoke(vararg children: Component) {
        addAll(children.asList())
    }

    /**
     * Removes given component from the current container.
     * @param child child component
     */
    fun remove(child: Component)

    /**
     * Removes child component from the current container at the given position.
     * @param position the position to be removed
     */
    fun removeAt(position: Int)

    /**
     * Removes all children from the current container.
     */
    fun removeAll()

    /**
     * Removes all children from the current container and disposes them.
     */
    fun disposeAll()

    /**
     * Returns a list of children of the current container.
     * @return list of children
     */
    fun getChildren(): List<Component>

    /**
     * An operator to add a text node to the container.
     */
    operator fun String.unaryPlus() {
        add(TextNode(this))
    }
}
