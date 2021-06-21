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

package io.kvision.onsenui.visual

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Color
import io.kvision.core.Container
import io.kvision.core.DomAttribute
import io.kvision.core.Widget

enum class RippleSize(override val attributeValue: String) : DomAttribute {
    COVER("cover"),
    CONTAIN("contain"),
    ;

    override val attributeName: String
        get() = "size"
}

/**
 * A ripple effect component.
 *
 * @constructor Creates a ripple effect component.
 * @param rippleColor the color of the ripple effect
 * @param rippleBackground the color of the background
 * @param size sizing of the wave on ripple effect
 * @param center whether the wave effect position is moved to the center of the target element
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Ripple(
    rippleColor: Color? = null,
    rippleBackground: Color? = null,
    size: RippleSize? = null,
    center: Boolean? = null,
    className: String? = null,
    init: (Ripple.() -> Unit)? = null
) : Widget(className) {

    /**
     * The color of the ripple effect.
     */
    var rippleColor: Color? by refreshOnUpdate(rippleColor)

    /**
     * The color of the background.
     */
    var rippleBackground: Color? by refreshOnUpdate(rippleBackground)

    /**
     * Sizing of the wave on the ripple effect.
     */
    var size: RippleSize? by refreshOnUpdate(size)

    /**
     * Whether the wave effect position is moved to the center of the target element.
     */
    var center: Boolean? by refreshOnUpdate(center)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * Whether the ripple effect is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-ripple")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        rippleColor?.let {
            attributeSetBuilder.add("color", it.asString())
        }
        rippleBackground?.let {
            attributeSetBuilder.add("background", it.asString())
        }
        attributeSetBuilder.add(size)
        if (center == true) {
            attributeSetBuilder.add("center")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.ripple(
    rippleColor: Color? = null,
    rippleBackground: Color? = null,
    size: RippleSize? = null,
    center: Boolean? = null,
    className: String? = null,
    init: (Ripple.() -> Unit)? = null
): Ripple {
    val ripple = Ripple(rippleColor, rippleBackground, size, center, className, init)
    this.add(ripple)
    return ripple
}
