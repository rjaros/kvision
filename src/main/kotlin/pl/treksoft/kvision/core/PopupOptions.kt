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
package pl.treksoft.kvision.core

import pl.treksoft.kvision.utils.obj

/**
 * Tooltip / Popover placements.
 */
enum class Placement(internal val placement: String) {
    AUTO("auto"),
    TOP("top"),
    BOTTOM("bottom"),
    LEFT("left"),
    RIGHT("right")
}

/**
 * Tooltip / Popover triggers.
 */
enum class Trigger(internal val trigger: String) {
    CLICK("click"),
    HOVER("hover"),
    FOCUS("focus"),
    MANUAL("manual")
}

/**
 * Tooltip options.
 */
data class TooltipOptions(
    val title: String? = null,
    val rich: Boolean? = null,
    val animation: Boolean? = null,
    val delay: Int? = null,
    val placement: Placement? = null,
    val triggers: List<Trigger>? = null,
    val sanitize: Boolean? = null
)

/**
 * Convert TooltipOptions to JavaScript JSON object.
 * @return JSON object
 */
fun TooltipOptions.toJs(): dynamic {
    val trigger = this.triggers?.map { it.trigger }?.joinToString(" ")
    return obj {
        if (this@toJs.title != null) this.title = this@toJs.title
        if (this@toJs.rich != null) this.html = this@toJs.rich
        if (this@toJs.animation != null) this.animation = this@toJs.animation
        if (this@toJs.delay != null) this.delay = this@toJs.delay
        if (this@toJs.placement != null) this.placement = this@toJs.placement.placement
        if (trigger != null) this.trigger = trigger
        if (this@toJs.sanitize != null) this.sanitize = this@toJs.sanitize
    }
}

/**
 * Popover options.
 */
data class PopoverOptions(
    val content: String? = null,
    val title: String? = null,
    val rich: Boolean? = null,
    val animation: Boolean? = null,
    val delay: Int? = null,
    val placement: Placement? = null,
    val triggers: List<Trigger>? = null,
    val sanitize: Boolean? = null
)

/**
 * Convert PopoverOptions to JavaScript JSON object.
 * @return JSON object
 */
fun PopoverOptions.toJs(): dynamic {
    val trigger = this.triggers?.map { it.trigger }?.joinToString(" ")
    return obj {
        if (this@toJs.content != null) this.content = this@toJs.content
        if (this@toJs.title != null) this.title = this@toJs.title
        if (this@toJs.rich != null) this.html = this@toJs.rich
        if (this@toJs.animation != null) this.animation = this@toJs.animation
        if (this@toJs.delay != null) this.delay = this@toJs.delay
        if (this@toJs.placement != null) this.placement = this@toJs.placement.placement
        if (trigger != null) this.trigger = trigger
        if (this@toJs.sanitize != null) this.sanitize = this@toJs.sanitize
    }
}
