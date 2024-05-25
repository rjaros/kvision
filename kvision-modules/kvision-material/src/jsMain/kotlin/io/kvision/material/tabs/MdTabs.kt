/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 Maanrifa Bacar Ali <dev.manrif@gmail.com>
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
package io.kvision.material.tabs

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.addBool
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.MdListWidget
import io.kvision.material.widget.toItemWidget
import io.kvision.material.widget.toItemWidgetArrayOrDefault
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.snabbdom.VNode
import kotlin.js.Promise

/**
 * Tabs organize groups of related content that are at the same level of hierarchy.
 *
 * See https://material-web.dev/components/tabs/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdTabs(
    autoActivate: Boolean = false,
    activeTabIndex: Int = -1,
    className: String? = null,
    init: (MdTabs.() -> Unit)? = null
) : MdListWidget<MdTab>(
    tag = "md-tabs",
    className = className
) {

    /**
     * Whether or not to automatically select a tab when it is focused.
     */
    var autoActivate by refreshOnUpdate(autoActivate)

    /**
     * Index of the current active tab.
     */
    var activeTabIndex by syncOnUpdate(activeTabIndex)

    /**
     * The tabs of this tab bar.
     */
    val tabs: Array<MdTab>
        get() = toItemWidgetArrayOrDefault(getElementD()?.tabs, listDelegate::items)

    /**
     * Current active tab.
     *
     * Note: setting active tab from an instance of [MdTab] would be quite complicated due to DOM
     * element lifecycle. Favor the use of [activeTabIndex] to set the active tab.
     */
    val activeTab: MdTab?
        get() = toItemWidget(getElementD()?.activeTab)

    init {
        init?.let { this.it() }

        setInternalEventListener<MdTabs> {
            change = {
                self.activeTabIndex = getElementD().activeTabIndex.unsafeCast<Int>()
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        getElementD().activeTabIndex = activeTabIndex
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (autoActivate) {
            attributeSetBuilder.addBool("auto-activate")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Scrolling
    ///////////////////////////////////////////////////////////////////////////

    /**
     *  Scrolls the toolbar, if overflowing, to the active tab.
     */
    fun scrollToTab(): Promise<Unit> = doScrollToTab(null)

    /**
     * Scrolls the toolbar, if overflowing, to the tab at [index].
     */
    fun scrollToTab(index: Int): Promise<Unit> = doScrollToTab {
        listDelegate.items[index]
    }

    /**
     * Scrolls the toolbar, if overflowing, to the [tab].
     */
    fun scrollToTab(tab: MdTab): Promise<Unit> = doScrollToTab { tab }

    /**
     * Scrolls to the tab provided by [tab]. If the tab's DOM element is not available, the scrolls
     * target will be the active tab.
     */
    private fun doScrollToTab(tab: (() -> MdTab?)?): Promise<Unit> {
        if (!visible) {
            return Promise.reject(IllegalStateException("Tabs is not visible"))
        }

        return requireElementD()
            .scrollToTab(tab?.invoke()?.getElement() ?: undefined)
            .unsafeCast<Promise<Unit>>()
    }
}

@ExperimentalMaterialApi
fun Container.tabs(
    autoActivate: Boolean = false,
    activeTabIndex: Int = -1,
    className: String? = null,
    init: (MdTabs.() -> Unit)? = null
) = MdTabs(
    autoActivate = autoActivate,
    activeTabIndex = activeTabIndex,
    className = className,
    init = init
).also(this::add)
