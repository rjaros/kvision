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

package io.kvision.core

import io.kvision.utils.toCamelCase

enum class Easing(internal val easing: String) {
    SWING("swing"),
    LINEAR("linear")
}

/**
 * Shows current widget with animation effect.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @return current widget
 */
fun Widget.showAnim(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null
): Widget {
    this.display = Display.NONE
    this.visible = true
    val jq = getElementJQuery()
    if (jq != null) {
        jq.show(duration, easing.easing) {
            this.display = null
            complete?.invoke()
        }
    } else {
        this.display = null
        complete?.invoke()
    }
    return this
}

/**
 * Hides current widget with animation effect.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @return current widget
 */
fun Widget.hideAnim(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null
): Widget {
    val jq = getElementJQuery()
    if (jq != null) {
        jq.hide(duration, easing.easing) {
            this.visible = false
            complete?.invoke()
        }
    } else {
        this.visible = false
        complete?.invoke()
    }
    return this
}

/**
 * Shows current widget with slide down effect.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @return current widget
 */
fun Widget.slideDown(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null
): Widget {
    this.display = Display.NONE
    this.visible = true
    val jq = getElementJQuery()
    if (jq != null) {
        jq.slideDown(duration, easing.easing) {
            this.display = null
            complete?.invoke()
        }
    } else {
        this.display = null
        complete?.invoke()
    }
    return this
}

/**
 * Hides current widget with slide up effect.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @return current widget
 */
fun Widget.slideUp(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null
): Widget {
    val jq = getElementJQuery()
    if (jq != null) {
        jq.slideUp(duration, easing.easing) {
            this.visible = false
            complete?.invoke()
        }
    } else {
        this.visible = false
        complete?.invoke()
    }
    return this
}

/**
 * Shows current widget with fade in effect.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @return current widget
 */
fun Widget.fadeIn(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null
): Widget {
    this.display = Display.NONE
    this.visible = true
    val jq = getElementJQuery()
    if (jq != null) {
        jq.fadeIn(duration, easing.easing) {
            this.display = null
            complete?.invoke()
        }
    } else {
        this.display = null
        complete?.invoke()
    }
    return this
}

/**
 * Hides current widget with fade out effect.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @return current widget
 */
fun Widget.fadeOut(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null
): Widget {
    val jq = getElementJQuery()
    if (jq != null) {
        jq.fadeOut(duration, easing.easing) {
            this.visible = false
            complete?.invoke()
        }
    } else {
        this.visible = false
        complete?.invoke()
    }
    return this
}

/**
 * Animate the widget changing CSS properties.
 * @param duration a duration of the animation
 * @param easing an easing function to use
 * @param complete a callback function called after the animation completes
 * @param styles changing properties values
 */
fun Widget.animate(
    duration: Int = 400,
    easing: Easing = Easing.SWING,
    complete: (() -> Unit)? = null,
    styles: StyledComponent.() -> Unit
) {
    val widget = Widget()
    widget.styles()
    val stylesObj = widget.getSnStyle()
    val obj = js("{}")
    for (key in js("Object").keys(stylesObj)) {
        @Suppress("UnsafeCastFromDynamic")
        obj[key.unsafeCast<String>().toCamelCase()] = stylesObj[key]
    }
    @Suppress("UnsafeCastFromDynamic")
    getElementJQuery()?.animate(obj, duration, easing.easing) {
        widget.dispose()
        this.styles()
        complete?.invoke()
    }
}
