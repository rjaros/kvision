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
package io.kvision.material.ripple

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.util.addBool
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.MdWidget
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.snabbdom.VNode
import org.w3c.dom.HTMLElement

/**
 * Ripples are state layers used to communicate the status of a component or interactive element.
 *
 * A state layer is a semi-transparent covering on an element that indicates its state.
 * A layer can be applied to an entire element or in a circular shape.
 *
 * See https://material-web.dev/components/ripple/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdRipple(
    disabled: Boolean = false,
    htmlFor: String? = null,
    control: HTMLElement? = null,
    className: String? = null,
    init: (MdRipple.() -> Unit)? = null
) : MdWidget(
    tag = "md-ripple",
    className = className
) {

    /**
     * Disables the ripple.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * The id of the element to control.
     */
    var htmlFor by keepOnUpdate(htmlFor)

    /**
     * The control element.
     */
    var control by keepOnUpdate(control)

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        htmlFor?.let { getElementD().htmlFor = it }
        control?.let { getElementD().control = it }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (disabled) {
            attributeSetBuilder.addBool("disabled")
        }
    }

    fun attach(control: HTMLElement) {
        requireElementD().attach(control)
    }

    fun detach() {
        requireElementD().detach()
    }
}

@ExperimentalMaterialApi
fun Container.ripple(
    disabled: Boolean = false,
    htmlFor: String? = null,
    control: HTMLElement? = null,
    className: String? = null,
    init: (MdRipple.() -> Unit)? = null
) = MdRipple(
    disabled = disabled,
    htmlFor = htmlFor,
    control = control,
    className = className,
    init = init
).also(this::add)
