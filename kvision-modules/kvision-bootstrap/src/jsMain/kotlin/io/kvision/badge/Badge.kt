/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 André Harnisch
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
package io.kvision.badge

import io.kvision.core.BsColor
import io.kvision.core.BsRounded
import io.kvision.core.Container
import io.kvision.html.Span

/**
 * Helper class for positioning of badges.
 * @author André Harnisch
 */
class BadgePosition(internal vararg val positionClasses: String) {
    companion object {
        val TOP_RIGHT = BadgePosition("position-absolute", "top-0", "start-100", "translate-middle")
        val TOP_LEFT = BadgePosition("position-absolute", "top-0", "start-0", "translate-middle")
        val TOP_CENTER = BadgePosition("position-absolute", "top-0", "start-50", "translate-middle")
        val BOTTOM_RIGHT = BadgePosition("position-absolute","top-100", "start-100", "translate-middle")
        val BOTTOM_LEFT = BadgePosition("position-absolute","top-100", "start-0", "translate-middle")
        val BOTTOM_CENTER = BadgePosition("position-absolute","top-100", "start-50", "translate-middle")
        val MIDDLE_RIGHT = BadgePosition("position-absolute","top-50", "start-100", "translate-middle")
        val MIDDLE_LEFT = BadgePosition("position-absolute","top-50", "start-0", "translate-middle")
        val MIDDLE_CENTER = BadgePosition("position-absolute","top-50", "start-50", "translate-middle")
    }
}

/**
 * Creates a badge for the parent component. One component can have multiple badges.
 * @param content element text
 * @param bsColor color theming
 * @param rounded more rounded style
 * @param position relative position of the badge; parent component need to have class 'position-relative'
 * @param init an initializer extension function
 * @see <a href="https://getbootstrap.com/docs/5.3/components/badge/"/>Bootstrap documentation</a>
 * @author André Harnisch
 */
open class Badge(
    override var content: String? = null,
    bsColor: BsColor = BsColor.SECONDARYBG,
    rounded: Boolean = false,
    position: BadgePosition? = null,
    init: Badge.() -> Unit = {}
): Span(className = "badge") {

    init {
        @Suppress("LeakingThis")
        addCssClass(bsColor.className)
        @Suppress("LeakingThis")
        if (rounded) addCssClass(BsRounded.ROUNDEDPILL.className)
        @Suppress("LeakingThis")
        position?.positionClasses?.forEach { addCssClass(it) }

        init()
    }
}

/**
 * DSL builder extension function.
 *
 * Automatically adds 'position-relative' class to [Container]
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.badge(
    content: String? = null,
    bsColor: BsColor = BsColor.SECONDARYBG,
    rounded: Boolean = false,
    position: BadgePosition? = null,
    init: Badge.() -> Unit = {}
) = Badge(content, bsColor, rounded, position, init).also {
    if(position != null) addCssClass("position-relative")
    add(it)
}
