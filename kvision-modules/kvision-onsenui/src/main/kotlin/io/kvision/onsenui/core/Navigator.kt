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
import io.kvision.KVManagerOnsenui.ons
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Display
import io.kvision.core.DomAttribute
import io.kvision.onsenui.BackButtonEvent
import io.kvision.onsenui.splitter.SplitterContent
import io.kvision.onsenui.tabbar.Tab
import io.kvision.panel.Root
import io.kvision.panel.SimplePanel
import io.kvision.utils.createInstance
import io.kvision.utils.obj
import io.kvision.utils.set
import kotlin.js.Promise

/**
 * Navigator animation types.
 */
enum class NavAnimation(override val attributeValue: String) : DomAttribute {
    NONE("none"),
    FADE("fade"),
    LIFT("lift"),
    SLIDE("slide"),
    FADEMD("fade-md"),
    LIFTMD("lift-md"),
    SLIDEMD("slide-md"),
    FADEIOS("fade-ios"),
    LIFTIOS("lift-ios"),
    SLIDEIOS("slide-ios"),
    ;

    override val attributeName: String
        get() = "animation"
}

/**
 * A navigator component.
 *
 * @constructor Creates a navigator component.
 * @param animation an animation type.
 * @param swipeable an iOS swipe to pop feature
 * @param forceSwipeable force iOS swipe on Android platform
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Navigator(
    animation: NavAnimation? = null,
    swipeable: Boolean? = null,
    forceSwipeable: Boolean? = null,
    classes: Set<String> = setOf(),
    init: (Navigator.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * An animation type.
     */
    var animation: NavAnimation? by refreshOnUpdate(animation)

    /**
     * An iOS swipe to pop feature.
     */
    var swipeable: Boolean? by refreshOnUpdate(swipeable)

    /**
     * Force iOS swipe on Android platform.
     */
    var forceSwipeable: Boolean? by refreshOnUpdate(forceSwipeable)

    /**
     * The width of swipeable area calculated from the edge (in pixels).
     */
    var swipeTargetWidth: Number? by refreshOnUpdate()

    /**
     * Specify how much the page needs to be swiped before popping.
     */
    var swipeThreshold: Number? by refreshOnUpdate()

    /**
     * Device back button event listener function.
     */
    protected var onDeviceBackButtonCallback: ((BackButtonEvent) -> Unit)? = null

    /**
     * Swipe event listener function.
     */
    protected var onSwipeCallback: ((Number) -> Unit)? = null

    internal val pagesMap = mutableMapOf<String, Page>()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * A dynamic property returning current top page.
     */
    val topPage: dynamic
        get() = getElement()?.asDynamic()?.topPage

    /**
     * A dynamic property returning current pages stack.
     */
    val pages: dynamic
        get() = getElement()?.asDynamic()?.pages

    override fun render(): VNode {
        return render("ons-navigator", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add(animation)
        if (forceSwipeable == true) {
            attributeSetBuilder.add("swipeable", "force")
        } else if (swipeable == true) {
            attributeSetBuilder.add("swipeable")
        }
        swipeTargetWidth?.let {
            attributeSetBuilder.add("swipe-target-width", "${it}px")
        }
        swipeThreshold?.let {
            attributeSetBuilder.add("swipe-threshold", "$it")
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        node.elm.asDynamic().pageLoader = (ons.PageLoader as Any).createInstance<Any>({ o: dynamic, done: dynamic ->
            @Suppress("UnsafeCastFromDynamic")
            val page: Page = o.page
            done(page.getElement())
        }, { })
        if (onDeviceBackButtonCallback != null) {
            getElement()?.asDynamic()?.onDeviceBackButton = onDeviceBackButtonCallback
        }
        if (onSwipeCallback != null) {
            getElement()?.asDynamic()?.onSwipe = onSwipeCallback
        }
        this.getElementJQuery()?.on("prepush") { e, _ ->
            this.dispatchEvent("onsPrepush", obj { detail = e })
        }
        this.getElementJQuery()?.on("prepop") { e, _ ->
            this.dispatchEvent("onsPrepop", obj { detail = e })
        }
        this.getElementJQuery()?.on("postpush") { e, _ ->
            this.dispatchEvent("onsPostpush", obj { detail = e })
        }
        this.getElementJQuery()?.on("postpop") { e, _ ->
            (children.last() as? Page)?.dispatchDestroyEvent()
            children.removeAt(children.size - 1).clearParent()
            refreshPageStack()
            this.dispatchEvent("onsPostpop", obj { detail = e })
        }
    }

    /**
     * Pushes the specified page into the stack.
     * @param pageId a given page id
     * @param options a parameter object
     */
    open fun pushPage(pageId: String, options: dynamic = undefined): Promise<Unit>? {
        return pagesMap[pageId]?.let { pushPage(it, options) }
    }

    /**
     * Pushes the specified page into the stack.
     * @param page a given page
     * @param options a parameter object
     */
    open fun pushPage(page: Page, options: dynamic = undefined): Promise<Unit>? {
        page.display = null
        add(page)
        @Suppress("UnsafeCastFromDynamic")
        return getElement()?.asDynamic()?.pushPage(page, options).then {
            refreshPageStack()
        }
    }

    /**
     * Pops the current page from the page stack.
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun popPage(options: dynamic = undefined): Promise<Unit>? {
        return if (children.size > 1) {
            getElement()?.asDynamic()?.popPage(options)
        } else {
            null
        }
    }

    /**
     * Replaces the current top page with the specified one.
     * @param pageId a given page id
     * @param options a parameter object
     */
    open fun replacePage(pageId: String, options: dynamic = undefined): Promise<Unit>? {
        return pagesMap[pageId]?.let { replacePage(it, options) }
    }

    /**
     * Replaces the current top page with the specified one.
     * @param page a given page
     * @param options a parameter object
     */
    open fun replacePage(page: Page, options: dynamic = undefined): Promise<Unit>? {
        page.display = null
        add(page)
        @Suppress("UnsafeCastFromDynamic")
        return getElement()?.asDynamic()?.replacePage(page, options).then {
            if (children.size > 1) {
                (children.elementAt(children.size - 2) as? Page)?.dispatchDestroyEvent()
                children.removeAt(children.size - 2).clearParent()
            }
            refreshPageStack()
        }
    }

    /**
     * Insert the specified page into the stack with at a position defined by the index argument.
     * @param index an insertion index
     * @param pageId a given page id
     * @param options a parameter object
     */
    open fun insertPage(index: Int, pageId: String, options: dynamic = undefined): Promise<Unit>? {
        return pagesMap[pageId]?.let { insertPage(index, it, options) }
    }

    /**
     * Insert the specified page into the stack with at a position defined by the index argument.
     * @param index an insertion index
     * @param page a given page
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun insertPage(index: Int, page: Page, options: dynamic = undefined): Promise<Unit>? {
        return if (index >= 0 && index < children.size) {
            children.add(index, page)
            page.parent?.remove(page)
            page.parent = this
            refreshPageStack()
            getElement()?.asDynamic()?.insertPage(index, page, options)
        } else {
            null
        }
    }

    /**
     * Remove the specified page at a position in the stack defined by the index argument.
     * @param index index to delete
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun removePage(index: Int, options: dynamic = undefined): Promise<Unit>? {
        return if (index >= 0 && index < children.size && children.size > 1) {
            getElement()?.asDynamic()?.removePage(index, options).then {
                (children[index] as? Page)?.dispatchDestroyEvent()
                children.removeAt(index).clearParent()
                refreshPageStack()
            }
        } else {
            null
        }
    }

    /**
     * Clears page stack and adds the specified page to the stack.
     * @param pageId a given page id
     * @param options a parameter object
     */
    open fun resetToPage(pageId: String, options: dynamic = undefined): Promise<Unit>? {
        return pagesMap[pageId]?.let { resetToPage(it, options) }
    }

    /**
     * Clears page stack and adds the specified page to the stack.
     * @param page a given page
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun resetToPage(page: Page, options: dynamic = undefined): Promise<Unit>? {
        page.display = null
        add(page)
        return getElement()?.asDynamic()?.resetToPage(page, options).then {
            children.take(children.size - 1).forEach {
                (it as? Page)?.dispatchDestroyEvent()
                it.clearParent()
            }
            children.clear()
            children.add(page)
            refresh()
        }
    }

    /**
     * Brings the given page to the top of the page stack.
     * @param index index of a page to move
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun bringPageTop(index: Int, options: dynamic = undefined): Promise<Unit>? {
        return if (index >= 0 && index < children.size) {
            getElement()?.asDynamic()?.bringPageTop(index, options).then {
                val page = children.removeAt(index).clearParent()
                (page as? Page)?.display = null
                add(page)
            }
        } else {
            null
        }
    }

    protected open fun refreshPageStack() {
        if (children.isNotEmpty()) {
            children.take(children.size - 1).forEach { (it as? Page)?.display = Display.NONE }
            (children.lastOrNull() as? Page)?.display = null
        }
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

}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Root.navigator(
    animation: NavAnimation? = null,
    swipeable: Boolean? = null,
    forceSwipeable: Boolean? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Navigator.() -> Unit)? = null
): Navigator {
    val navigator = Navigator(animation, swipeable, forceSwipeable, classes ?: className.set, init)
    this.add(navigator)
    return navigator
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun SplitterContent.navigator(
    animation: NavAnimation? = null,
    swipeable: Boolean? = null,
    forceSwipeable: Boolean? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Navigator.() -> Unit)? = null
): Navigator {
    val navigator = Navigator(animation, swipeable, forceSwipeable, classes ?: className.set, init)
    this.add(navigator)
    return navigator
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Tab.navigator(
    animation: NavAnimation? = null,
    swipeable: Boolean? = null,
    forceSwipeable: Boolean? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Navigator.() -> Unit)? = null
): Navigator {
    val navigator = Navigator(animation, swipeable, forceSwipeable, classes ?: className.set, init)
    this.add(navigator)
    return navigator
}
