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
package io.kvision.dropdown

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.panel.SimplePanel

/**
 * A drop down menu container.
 *
 * @constructor
 * @param ariaId the aria id of the element
 * @param dark the dark mode of the dropdown menu
 * @param rightAligned right aligned dropdown menu
 */
open class DropDownMenu(private val ariaId: String, dark: Boolean = false, rightAligned: Boolean = false) : SimplePanel(
    "dropdown-menu"
) {
    /**
     * The dark mode of the dropdown menu.
     */
    var dark by refreshOnUpdate(dark)

    /**
     * Right aligned dropdown menu.
     */
    var rightAligned by refreshOnUpdate(rightAligned)

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("aria-labelledby", ariaId)
        if (dark) attributeSetBuilder.add("data-bs-theme", "dark")
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (rightAligned) classSetBuilder.add("dropdown-menu-end")
    }
}
