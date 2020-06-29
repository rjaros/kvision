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

import com.github.snabbdom.VNode
import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set
import kotlin.browser.document
import kotlin.browser.window

/**
 * An Onsen UI lazy repeat helper component.
 *
 * @constructor Creates a lazy repeat helper component.
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsLazyRepeat(
    classes: Set<String> = setOf(),
    init: (OnsLazyRepeat.() -> Unit)? = null
) : Widget(classes) {

    /**
     * A callback function for creating new list items.
     */
    protected var createItemContentCallback: ((Int) -> Widget)? = null

    /**
     * A callback function returning a number of items.
     */
    protected var countItemsCallback: (() -> Int)? = null

    /**
     * A callback function for calculating item height.
     */
    protected var calculateItemHeightCallback: ((Int) -> Int)? = null

    /**
     * A callback function for destroying removed HTML elements.
     */
    protected var destroyItemCallback: ((Int, HTMLElement) -> Unit)? = null

    /**
     * @suppress
     * Internal variable
     */
    protected val root: Root

    init {
        val el = document.createElement("div") as HTMLElement
        root = Root(el, containerType = ContainerType.NONE, addRow = false)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-lazy-repeat")
    }

    override fun afterInsert(node: VNode) {
        val delegate = obj {}
        if (createItemContentCallback != null) {
            delegate.createItemContent = { index: Int ->
                val widget = createItemContentCallback!!.invoke(index)
                root.add(widget)
                val element = widget.getElement()
                root.removeAll()
                element
            }
        }
        if (countItemsCallback != null) {
            delegate.countItems = {
                countItemsCallback?.invoke()
            }
        }
        if (calculateItemHeightCallback != null) {
            delegate.calculateItemHeight = { index: Int ->
                calculateItemHeightCallback?.invoke(index)
            }
        }
        if (destroyItemCallback != null) {
            delegate.destroyItem = { index: Int, element: HTMLElement ->
                destroyItemCallback?.invoke(index, element)
            }
        }
        window.setTimeout({
            getElement()?.asDynamic()?.delegate = delegate
        }, 0)
    }

    /**
     * Sets a callback function for creating new list items.
     * @param callback a callback function
     */
    open fun createItemContent(callback: (index: Int) -> Widget) {
        this.createItemContentCallback = callback
        val delegate = getElement()?.asDynamic()?.delegate
        if (delegate != null) {
            delegate.createItemContent = { index: Int ->
                val widget = createItemContentCallback!!.invoke(index)
                root.add(widget)
                val element = widget.getElement()
                root.removeAll()
                element
            }
        }
    }

    /**
     * Sets a callback function returning a number of items.
     * @param callback a callback function
     */
    open fun countItems(callback: () -> Int) {
        this.countItemsCallback = callback
        val delegate = getElement()?.asDynamic()?.delegate
        if (delegate != null) {
            delegate.countItems = {
                countItemsCallback?.invoke()
            }
        }
    }

    /**
     * Sets a callback function for calculating item height.
     * @param callback a callback function
     */
    open fun calculateItemHeight(callback: (index: Int) -> Int) {
        this.calculateItemHeightCallback = callback
        val delegate = getElement()?.asDynamic()?.delegate
        if (delegate != null) {
            delegate.calculateItemHeight = { index: Int ->
                calculateItemHeightCallback?.invoke(index)
            }
        }
    }

    /**
     * Sets a callback function for destroying removed HTML elements.
     * @param callback a callback function
     */
    open fun destroyItemCallback(callback: (index: Int, element: HTMLElement) -> Unit) {
        this.destroyItemCallback = callback
        val delegate = getElement()?.asDynamic()?.delegate
        if (delegate != null) {
            delegate.destroyItem = { index: Int, element: HTMLElement ->
                destroyItemCallback?.invoke(index, element)
            }
        }
    }

    /**
     * Refreshes the list.
     */
    open fun refreshList() {
        getElement()?.asDynamic()?.refresh()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun OnsList.onsLazyRepeat(
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsLazyRepeat.() -> Unit)? = null
): OnsLazyRepeat {
    val onsLazyRepeat = OnsLazyRepeat(classes ?: className.set, init)
    this.add(onsLazyRepeat)
    return onsLazyRepeat
}

/**
 * DSL builder extension function for simple configuration.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun OnsList.onsLazyRepeat(
    itemsCount: Int,
    classes: Set<String>? = null,
    className: String? = null,
    createItemContentCallback: (index: Int) -> Widget
): OnsLazyRepeat {
    val onsLazyRepeat = OnsLazyRepeat(classes ?: className.set) {
        countItems { itemsCount }
        createItemContent(createItemContentCallback)
    }
    this.add(onsLazyRepeat)
    return onsLazyRepeat
}
