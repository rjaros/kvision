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

package io.kvision.onsenui.core

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.onsenui.BackButtonEvent
import io.kvision.onsenui.dialog.Dialog
import io.kvision.onsenui.splitter.SplitterContent
import io.kvision.onsenui.splitter.SplitterSide
import io.kvision.onsenui.tabbar.Tab
import io.kvision.onsenui.toolbar.Toolbar
import io.kvision.panel.Root
import io.kvision.panel.SimplePanel
import io.kvision.utils.obj
import kotlin.collections.set

/**
 * A page component.
 *
 * @constructor Creates a page component.
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class Page(className: String? = null, init: (Page.() -> Unit)? = null) :
    SimplePanel((className?.let { "$it " } ?: "") + "page") {

    /**
     * The page toolbar.
     */
    var toolbarPanel: Toolbar? = null
        set(value) {
            field = value
            value?.parent = this
        }

    /**
     * The page background.
     */
    val backgroundPanel = SimplePanel("page__background")

    /**
     * The page content.
     */
    val contentPanel = SimplePanel("page__content")

    /**
     * Fixed content.
     */
    val fixedPanel = Span()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * A dynamic parameter object passed to the page when pushed to the stack.
     */
    val data: dynamic
        get() = getElement()?.asDynamic()?.data

    /**
     * Infinite scroll event listener function.
     */
    protected var onInfiniteScrollCallback: ((() -> Unit) -> Unit)? = null

    /**
     * Device back button event listener function.
     */
    protected var onDeviceBackButtonCallback: ((BackButtonEvent) -> Unit)? = null

    init {
        backgroundPanel.parent = this
        contentPanel.parent = this
        fixedPanel.parent = this
        init?.invoke(this)
    }

    override fun render(): VNode {
        val toolbarArr = toolbarPanel?.let { arrayOf(it.renderVNode()) } ?: emptyArray()
        return render(
            "ons-page",
            toolbarArr + arrayOf(backgroundPanel.renderVNode(), contentPanel.renderVNode(), fixedPanel.renderVNode())
        )
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    override fun afterInsert(node: VNode) {
        if (onInfiniteScrollCallback != null) {
            getElement()?.asDynamic()?.onInfiniteScroll = onInfiniteScrollCallback
        }
        if (onDeviceBackButtonCallback != null) {
            getElement()?.asDynamic()?.onDeviceBackButton = onDeviceBackButtonCallback
        }
    }

    override fun add(child: Component): Page {
        contentPanel.add(child)
        return this
    }

    override fun add(position: Int, child: Component): Page {
        contentPanel.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): Page {
        contentPanel.addAll(children)
        return this
    }

    override fun remove(child: Component): Page {
        contentPanel.remove(child)
        return this
    }

    override fun removeAt(position: Int): Page {
        contentPanel.removeAt(position)
        return this
    }

    override fun removeAll(): Page {
        contentPanel.removeAll()
        return this
    }

    override fun disposeAll(): Page {
        contentPanel.disposeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return contentPanel.getChildren()
    }

    /**
     * DSL builder function to add fixed elements to the page.
     * @param builder a builder extension function
     */
    open fun fixed(builder: Container.() -> Unit) {
        fixedPanel.builder()
    }

    /**
     * Sets infinite scroll event listener.
     * @param callback an event listener
     */
    open fun onInfiniteScroll(callback: (done: () -> Unit) -> Unit) {
        onInfiniteScrollCallback = callback
        getElement()?.asDynamic()?.onInfiniteScroll = callback
    }

    /**
     * Clears infinite scroll event listener.
     */
    open fun onInfiniteScrollClear() {
        onInfiniteScrollCallback = null
        getElement()?.asDynamic()?.onInfiniteScroll = undefined
    }

    /**
     * Sets device back button event listener.
     * @param callback an event listener
     */
    open fun onDeviceBackButton(callback: (event: BackButtonEvent) -> Unit) {
        onDeviceBackButtonCallback = callback
        getElement()?.asDynamic()?.onDeviceBackButton = callback
    }

    /**
     * Clears device back button event listener.
     */
    open fun onDeviceBackButtonClear() {
        onDeviceBackButtonCallback = null
        getElement()?.asDynamic()?.onDeviceBackButton = undefined
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun dispatchHideEvent() {
        this.dispatchEvent("hide", obj { })
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun dispatchDestroyEvent() {
        this.dispatchEvent("destroy", obj { })
    }

    override fun dispose() {
        super.dispose()
        toolbarPanel?.dispose()
        backgroundPanel.dispose()
        contentPanel.dispose()
        fixedPanel.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Root.page(
    className: String? = null,
    init: (Page.() -> Unit)? = null
): Page {
    val page = Page(className, init)
    this.add(page)
    return page
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Navigator.page(
    pageId: String? = null,
    className: String? = null,
    init: (Page.() -> Unit)? = null
): Page {
    val page = Page(className, init)
    if (pageId == null || this.getChildren().isEmpty()) {
        this.add(page)
    }
    if (pageId != null) this.pagesMap[pageId] = page
    return page
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun SplitterSide.page(
    pageId: String? = null,
    className: String? = null,
    init: (Page.() -> Unit)? = null
): Page {
    val page = Page(className, init)
    if (pageId == null || this.getChildren().isEmpty()) {
        this.add(page)
    }
    if (pageId != null) this.pagesMap[pageId] = page
    return page
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun SplitterContent.page(
    pageId: String? = null,
    className: String? = null,
    init: (Page.() -> Unit)? = null
): Page {
    val page = Page(className, init)
    if (pageId == null || this.getChildren().isEmpty()) {
        this.add(page)
    }
    if (pageId != null) this.pagesMap[pageId] = page
    return page
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Tab.page(
    className: String? = null,
    init: (Page.() -> Unit)? = null
): Page {
    val page = Page(className, init)
    this.add(page)
    return page
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Dialog.page(
    className: String? = null,
    init: (Page.() -> Unit)? = null
): Page {
    val page = Page(className, init)
    this.add(page)
    return page
}
