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

package io.kvision.onsenui.carousel

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.panel.SimplePanel
import io.kvision.utils.asString
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
 * @param className CSS class names
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
    className: String? = null,
    init: (Carousel.() -> Unit)? = null
) : SimplePanel(className) {

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
    val swiperPanel = SimplePanel("ons-swiper-target")

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
        useSnabbdomDistinctKey()
        swiperPanel.parent = this
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-carousel", arrayOf(swiperPanel.renderVNode()))
    }

    override fun afterInsert(node: VNode) {
        if (onSwipeCallback != null) {
            getElement()?.asDynamic()?.onSwipe = onSwipeCallback
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        direction?.let {
            attributeSetBuilder.add("direction", it.type)
        }
        if (fullscreen == true) {
            attributeSetBuilder.add("fullscreen")
        }
        if (overscrollable == true) {
            attributeSetBuilder.add("overscrollable")
        }
        if (animation == false) {
            attributeSetBuilder.add("animation", "none")
        }
        if (swipeable == true) {
            attributeSetBuilder.add("swipeable")
        }
        initialIndex?.let {
            attributeSetBuilder.add("initial-index", "$it")
        }
        if (centered == true) {
            attributeSetBuilder.add("centered")
        }
        itemWidth?.let {
            attributeSetBuilder.add("item-width", it.asString())
        }
        itemHeight?.let {
            attributeSetBuilder.add("item-height", it.asString())
        }
        if (autoScroll == true) {
            attributeSetBuilder.add("auto-scroll")
        }
        autoScrollRatio?.let {
            attributeSetBuilder.add("auto-scroll-ratio", "$it")
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
        if (autoRefresh == true) {
            attributeSetBuilder.add("auto-refresh")
        }
    }

    override fun add(child: Component): Carousel {
        swiperPanel.add(child)
        return this
    }

    override fun add(position: Int, child: Component): Carousel {
        swiperPanel.add(position, child)
        return this
    }

    override fun addAll(children: List<Component>): Carousel {
        swiperPanel.addAll(children)
        return this
    }

    override fun remove(child: Component): Carousel {
        swiperPanel.remove(child)
        return this
    }

    override fun removeAt(position: Int): Carousel {
        swiperPanel.removeAt(position)
        return this
    }

    override fun removeAll(): Carousel {
        swiperPanel.removeAll()
        return this
    }

    override fun disposeAll(): Carousel {
        swiperPanel.disposeAll()
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
        className,
        init
    )
    this.add(carousel)
    return carousel
}
