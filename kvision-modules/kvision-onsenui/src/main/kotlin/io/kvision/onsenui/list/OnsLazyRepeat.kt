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

package io.kvision.onsenui.list

import com.github.snabbdom.VNode
import org.w3c.dom.HTMLElement
import io.kvision.core.Widget
import io.kvision.utils.obj
import kotlinx.browser.window

/**
 * An Onsen UI lazy repeat helper component.
 *
 * @constructor Creates a lazy repeat helper component.
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsLazyRepeat(
    className: String? = null,
    init: (OnsLazyRepeat.() -> Unit)? = null
) : Widget(className) {

    /**
     * A callback function for creating new list items.
     */
    protected var createItemContentCallback: ((Int) -> HTMLElement)? = null

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

    private var countItems: Int = 0

    init {
        useSnabbdomDistinctKey()
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
                createItemContentCallback!!.invoke(index)
            }
        }
        if (countItemsCallback != null) {
            delegate.countItems = {
                val count = countItemsCallback?.invoke()
                countItems = count ?: 0
                count
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
            window.setTimeout({
                val childCount = parent?.getElement()?.childNodes?.length
                if (countItems > 0) {
                    if (childCount == 1 ||
                        (childCount == 2 &&
                                (parent?.getElement()?.lastChild as? HTMLElement)?.style?.visibility == "hidden")
                    ) {
                        (parent as? OnsList)?.hide()
                        (parent as? OnsList)?.show()
                    }
                }
            }, 1000)
        }, 0)
    }

    /**
     * Sets a callback function for creating new list items.
     * @param callback a callback function
     */
    open fun createItemContent(callback: (index: Int) -> HTMLElement) {
        this.createItemContentCallback = callback
        val delegate = getElement()?.asDynamic()?.delegate
        if (delegate != null) {
            delegate.createItemContent = { index: Int ->
                createItemContentCallback!!.invoke(index)
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
    className: String? = null,
    init: (OnsLazyRepeat.() -> Unit)? = null
): OnsLazyRepeat {
    val onsLazyRepeat = OnsLazyRepeat(className, init)
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
    className: String? = null,
    createItemContentCallback: (index: Int) -> HTMLElement
): OnsLazyRepeat {
    val onsLazyRepeat = OnsLazyRepeat(className) {
        countItems { itemsCount }
        createItemContent(createItemContentCallback)
    }
    this.add(onsLazyRepeat)
    return onsLazyRepeat
}
