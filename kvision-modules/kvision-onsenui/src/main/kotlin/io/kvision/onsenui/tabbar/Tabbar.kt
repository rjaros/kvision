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

package io.kvision.onsenui.tabbar

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.DomAttribute
import io.kvision.core.StringPair
import io.kvision.core.Widget
import io.kvision.onsenui.core.Page
import io.kvision.panel.SimplePanel
import org.w3c.dom.NodeList
import org.w3c.dom.get
import kotlin.js.Promise

/**
 * Tab bar position.
 */
enum class TabsPosition(override val attributeValue: String) : DomAttribute {
    AUTO("auto"),
    TOP("top"),
    BOTTOM("bottom"),
    ;

    override val attributeName: String
        get() = "position"
}

/**
 * A tab bar component.
 *
 * @constructor Creates a tab bar component.
 * @param tabPosition the tab bar position
 * @param animation determines if the transitions are animated
 * @param swipeable determines if the tab bar can be scrolled by drag or swipe
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Tabbar(
    tabPosition: TabsPosition? = null,
    animation: Boolean? = null,
    swipeable: Boolean? = null,
    className: String? = null,
    init: (Tabbar.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * The tab bar position.
     */
    var tabPosition: TabsPosition? by refreshOnUpdate(tabPosition)

    /**
     *  Determines if the transitions are animated.
     */
    var animation: Boolean? by refreshOnUpdate(animation)

    /**
     * Determines if the tab bar can be scrolled by drag or swipe.
     */
    var swipeable: Boolean? by refreshOnUpdate(swipeable)

    /**
     * Distance in pixels from both edges. Swiping on these areas will prioritize parent components.
     */
    var ignoreEdgeWidth: Number? by refreshOnUpdate()

    /**
     * Whether to hide the tabs.
     */
    var hideTabs: Boolean? by refreshOnUpdate()

    /**
     * Whether the tabs show a dynamic bottom border. Only works for iOS flat design since the border is always visible in Material Design.
     */
    var tabBorder: Boolean? by refreshOnUpdate()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * A dynamic property returning visibility of the tab bar.
     */
    val isVisible: dynamic
        get() = getElement()?.asDynamic()?.visible

    /**
     * Swipe event listener function.
     */
    protected var onSwipeCallback: ((Number) -> Unit)? = null

    /**
     * The tab bar panel styling callback.
     */
    protected var tabbarStyleCallback: (Widget.(Int) -> Unit)? = null

    init {
        this.id = "kv_ons_tabbar_${counter++}"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-tabbar", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add(tabPosition)
        if (animation == false) {
            attributeSetBuilder.add("animation", "none")
        }
        if (swipeable == true) {
            attributeSetBuilder.add("swipeable")
        }
        ignoreEdgeWidth?.let {
            attributeSetBuilder.add("ignore-edge-width", "${it}px")
        }
        if (hideTabs == true) {
            attributeSetBuilder.add("hide-tabs")
        }
        if (tabBorder == true) {
            attributeSetBuilder.add("tab-border")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        if (onSwipeCallback != null) {
            getElement()?.asDynamic()?.onSwipe = onSwipeCallback
        }
        this.getElement()?.addEventListener("prechange", { e ->
            if (tabbarStyleCallback != null) {
                val widget = Widget()
                tabbarStyleCallback?.let { widget.it(e.asDynamic().detail.index) }
                val styles = widget.getSnStyle()
                val stylesList = mutableListOf<StringPair>()
                for (key in js("Object").keys(styles)) {
                    @Suppress("UnsafeCastFromDynamic")
                    stylesList.add(key.unsafeCast<String>() to styles[key])
                }
                val style = stylesList.joinToString(";") { (key, value) -> "$key: $value" }
                val element = getElementD()?.querySelectorAll(".tabbar")?.unsafeCast<NodeList>()?.get(0)?.asDynamic()
                element?.setAttribute("style", style)
            }
            e.stopPropagation()
        })
        if (tabbarStyleCallback != null) {
            val activeIndex = if (getActiveTabIndex().toInt() >= 0) {
                getActiveTabIndex().toInt()
            } else {
                val childIndex = getChildren().indexOfFirst { it is Tab && it.active == true }
                if (childIndex >= 0) {
                    childIndex
                } else {
                    0
                }
            }
            val widget = Widget()
            tabbarStyleCallback?.let { widget.it(activeIndex) }
            val styles = widget.getSnStyle()
            val stylesList = mutableListOf<StringPair>()
            for (key in js("Object").keys(styles)) {
                @Suppress("UnsafeCastFromDynamic")
                stylesList.add(key.unsafeCast<String>() to styles[key])
            }
            val style = stylesList.joinToString(";") { (key, value) -> "$key: $value" }
            val element = getElementD()?.querySelectorAll(".tabbar")?.unsafeCast<NodeList>()?.get(0)?.asDynamic()
            element?.setAttribute("style", style)
        }
    }

    override fun afterDestroy() {
        children?.forEach {
            if (it is Page) remove(it)
        }
    }

    /**
     * Shows specified tab page.
     * @param index the tab index
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun setActiveTab(index: Int, options: dynamic = undefined): Promise<Unit>? {
        return getElement()?.asDynamic()?.setActiveTab(index, options)
    }

    /**
     * Shows or hides the tab bar.
     * @param visible whether the tab bar is visible
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun setTabbarVisibility(visible: Boolean) {
        getElement()?.asDynamic()?.setTabbarVisibility(visible)
    }

    /**
     * Gets the active tab index.
     * @return active tab index
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getActiveTabIndex(): Number {
        return getElement()?.asDynamic()?.getActiveTabIndex() ?: -1
    }

    /**
     * Sets swipe event listener.
     * @param callback an event listener
     */
    open fun onSwipe(callback: (ratio: Number) -> Unit) {
        onSwipeCallback = callback
        getElement()?.asDynamic()?.onSwipe = callback
    }

    /**
     * Clears swipe event listener.
     */
    open fun onSwipeClear() {
        onSwipeCallback = null
        getElement()?.asDynamic()?.onSwipe = undefined
    }

    /**
     * Sets the tab bar panel styling callback.
     * @param callback a styling callback
     */
    open fun tabbarStyle(callback: Widget.(index: Int) -> Unit) {
        tabbarStyleCallback = callback
    }

    /**
     * Clears the tab bar panel styling callback.
     */
    open fun tabbarStyleClear() {
        tabbarStyleCallback = null
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Page.tabbar(
    tabPosition: TabsPosition? = null,
    animation: Boolean = true,
    swipeable: Boolean? = null,
    className: String? = null,
    init: (Tabbar.() -> Unit)? = null
): Tabbar {
    val tabbar = Tabbar(tabPosition, animation, swipeable, className, init)
    this.add(tabbar)
    return tabbar
}
