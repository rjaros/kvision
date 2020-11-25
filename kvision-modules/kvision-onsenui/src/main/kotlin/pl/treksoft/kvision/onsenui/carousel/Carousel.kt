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

package pl.treksoft.kvision.onsenui.carousel

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.asString
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set
import kotlin.js.Promise

/**
 * The carousel directions.
 */
enum class CarouselDirection(internal val type: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical")
}

/**
 * A carousel component.
 *
 * @constructor Creates a carousel component.
 * @param direction a carousel direction
 * @param fullscreen whether the carousel should fill the whole screen
 * @param overscrollable whether the carousel should scroll past first and last items
 * @param autoScroll whether the carousel will be automatically scrolled to the closest item border
 * @param animation determines if the transitions are animated
 * @param swipeable determines if the carousel can be scrolled by drag or swipe
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class Carousel(
    direction: CarouselDirection? = null,
    fullscreen: Boolean? = null,
    overscrollable: Boolean? = null,
    autoScroll: Boolean? = null,
    animation: Boolean? = null,
    swipeable: Boolean? = null,
    initialIndex: Int? = null,
    classes: Set<String> = setOf(),
    init: (Carousel.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * A carousel direction.
     */
    var direction: CarouselDirection? by refreshOnUpdate(direction)

    /**
     * Whether the carousel should fill the whole screen.
     */
    var fullscreen: Boolean? by refreshOnUpdate(fullscreen)

    /**
     * Whether the carousel should scroll past first and last items.
     */
    var overscrollable: Boolean? by refreshOnUpdate(overscrollable)

    /**
     * Determines if the transitions are animated.
     */
    var animation: Boolean? by refreshOnUpdate(animation)

    /**
     * Determines if the carousel can be scrolled by drag or swipe.
     */
    var swipeable: Boolean? by refreshOnUpdate(swipeable)

    /**
     * The index of the item to show on start.
     */
    var initialIndex: Int? by refreshOnUpdate(initialIndex)

    /**
     * Whether the selected item will be in the center of the carousel instead of the beginning.
     */
    var centered: Boolean? by refreshOnUpdate()

    /**
     * The item width (only for horizontal direction).
     */
    var itemWidth: CssSize? by refreshOnUpdate()

    /**
     * The item height (only for vertical direction).
     */
    var itemHeight: CssSize? by refreshOnUpdate()

    /**
     * Whether the carousel will be automatically scrolled to the closest item border.
     */
    var autoScroll: Boolean? by refreshOnUpdate(autoScroll)

    /**
     * Specifies how much the user must drag the carousel in order for it to auto scroll to the next item.
     */
    var autoScrollRatio: Number? by refreshOnUpdate()

    /**
     * Whether the carousel is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * Whether the carousel will automatically refresh when the number of child nodes change.
     */
    var autoRefresh: Boolean? by refreshOnUpdate()

    /**
     * The swiper inner container.
     */
    val swiperPanel = SimplePanel(setOf("ons-swiper-target"))

    /**
     * A dynamic property returning the number of carousel items.
     */
    val itemCount: dynamic
        get() = getElement()?.asDynamic()?.itemCount

    /**
     * Swipe event listener function.
     */
    protected var onSwipeCallback: ((Number) -> Unit)? = null

    init {
        swiperPanel.parent = this
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-carousel", arrayOf(swiperPanel.renderVNode()))
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        if (onSwipeCallback != null) {
            getElement()?.asDynamic()?.onSwipe = onSwipeCallback
        }
        this.getElementJQuery()?.on("prechange") { e, _ ->
            this.dispatchEvent("onsPrechange", obj { detail = e })
            e.stopPropagation()
        }
        this.getElementJQuery()?.on("postchange") { e, _ ->
            this.dispatchEvent("onsPostchange", obj { detail = e })
            e.stopPropagation()
        }
        this.getElementJQuery()?.on("refresh") { e, _ ->
            this.dispatchEvent("onsRefresh", obj { detail = e })
        }
        this.getElementJQuery()?.on("overscroll") { e, _ ->
            this.dispatchEvent("onsOverscroll", obj { detail = e })
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        direction?.let {
            sn.add("direction" to it.type)
        }
        if (fullscreen == true) {
            sn.add("fullscreen" to "fullscreen")
        }
        if (overscrollable == true) {
            sn.add("overscrollable" to "overscrollable")
        }
        if (animation == false) {
            sn.add("animation" to "none")
        }
        if (swipeable == true) {
            sn.add("swipeable" to "swipeable")
        }
        initialIndex?.let {
            sn.add("initial-index" to "$it")
        }
        if (centered == true) {
            sn.add("centered" to "centered")
        }
        itemWidth?.let {
            sn.add("item-width" to it.asString())
        }
        itemHeight?.let {
            sn.add("item-height" to it.asString())
        }
        if (autoScroll == true) {
            sn.add("auto-scroll" to "auto-scroll")
        }
        autoScrollRatio?.let {
            sn.add("auto-scroll-ratio" to "$it")
        }
        if (disabled == true) {
            sn.add("disabled" to "disabled")
        }
        if (autoRefresh == true) {
            sn.add("auto-refresh" to "auto-refresh")
        }
        return sn
    }

    override fun add(child: Component): SimplePanel {
        swiperPanel.add(child)
        return this
    }

    override fun addAll(children: List<Component>): SimplePanel {
        swiperPanel.addAll(children)
        return this
    }

    override fun remove(child: Component): SimplePanel {
        swiperPanel.remove(child)
        return this
    }

    override fun removeAll(): SimplePanel {
        swiperPanel.removeAll()
        return this
    }

    override fun getChildren(): List<Component> {
        return swiperPanel.getChildren()
    }

    /**
     * Shows specified carousel item.
     * @param index the carousel item index
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun setActiveIndex(index: Int, options: dynamic = undefined): Promise<Unit>? {
        return getElement()?.asDynamic()?.setActiveIndex(index, options)
    }

    /**
     * Gets the active item index.
     * @return active item index
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getActiveIndex(): Number {
        return getElement()?.asDynamic()?.getActiveIndex() ?: -1
    }

    /**
     * Shows next carousel item.
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun next(options: dynamic = undefined): Promise<Unit> {
        return getElement()?.asDynamic()?.next(options)
    }

    /**
     * Shows previous carousel item.
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun prev(options: dynamic = undefined): Promise<Unit> {
        return getElement()?.asDynamic()?.prev(options)
    }

    /**
     * Shows first carousel item.
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun first(options: dynamic = undefined): Promise<Unit> {
        return getElement()?.asDynamic()?.first(options)
    }

    /**
     * Shows last carousel item.
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun last(options: dynamic = undefined): Promise<Unit> {
        return getElement()?.asDynamic()?.last(options)
    }

    /**
     * Updates the layout of the carousel.
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun refreshCarousel() {
        getElement()?.asDynamic()?.refresh()
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

    override fun dispose() {
        super.dispose()
        swiperPanel.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.carousel(
    direction: CarouselDirection? = null,
    fullscreen: Boolean? = null,
    overscrollable: Boolean? = null,
    autoScroll: Boolean? = null,
    animation: Boolean? = null,
    swipeable: Boolean? = null,
    initialIndex: Int? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Carousel.() -> Unit)? = null
): Carousel {
    val carousel = Carousel(
        direction,
        fullscreen,
        overscrollable,
        autoScroll,
        animation,
        swipeable,
        initialIndex,
        classes ?: className.set,
        init
    )
    this.add(carousel)
    return carousel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.carousel(
    state: ObservableState<S>,
    direction: CarouselDirection? = null,
    fullscreen: Boolean? = null,
    overscrollable: Boolean? = null,
    autoScroll: Boolean? = null,
    animation: Boolean? = null,
    swipeable: Boolean? = null,
    initialIndex: Int? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Carousel.(S) -> Unit)
) = carousel(
    direction,
    fullscreen,
    overscrollable,
    autoScroll,
    animation,
    swipeable,
    initialIndex,
    classes,
    className
).bind(state, true, init)
