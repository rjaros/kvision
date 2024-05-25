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
package io.kvision.material.progress

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.widget.MdWidget
import io.kvision.material.util.add
import io.kvision.material.util.addBool
import io.kvision.core.AttributeSetBuilder

/**
 * Progress indicators inform users about the status of ongoing processes, such as loading an app
 * or submitting a form.
 *
 * See https://material-web.dev/components/progress/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdProgress internal constructor(
    tag: String,
    value: Number,
    max: Number,
    indeterminate: Boolean,
    fourColor: Boolean,
    className: String?
) : MdWidget(
    tag = tag,
    className = className
) {

    /**
     * Progress to display, a fraction between 0 and [max].
     */
    var value by syncOnUpdate(value)

    /**
     * Maximum progress to display.
     */
    var max by syncOnUpdate(max)

    /**
     * Whether or not to display indeterminate progress, which gives no indication to how long an
     * activity will take.
     */
    var indeterminate by refreshOnUpdate(indeterminate)

    /**
     * Whether or not to render indeterminate mode using 4 colors instead of one.
     */
    var fourColor by refreshOnUpdate(fourColor)

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (indeterminate) {
            attributeSetBuilder.addBool("indeterminate")
        } else {
            attributeSetBuilder.add("value", value)
            attributeSetBuilder.add("max", max)
        }

        if (fourColor) {
            attributeSetBuilder.addBool("four-color")
        }
    }
}
