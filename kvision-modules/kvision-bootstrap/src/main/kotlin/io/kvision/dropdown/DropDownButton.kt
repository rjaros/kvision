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
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.ButtonType
import io.kvision.snabbdom.VNode

/**
 * A drop down button component.
 *
 * @constructor
 * @param id the id of the element
 * @param text the dropdown button text
 * @param icon the icon of the dropdown button
 * @param style the style of the dropdown button
 * @param disabled determines if the component is disabled on start
 * @param forNavbar determines if the component will be used in a navbar
 * @param forDropDown determines if the component will be used in a dropdown
 * @param autoClose the auto closing mode of the dropdown menu
 * @param className CSS class names
 */
open class DropDownButton(
    id: String,
    text: String,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    disabled: Boolean = false,
    val forNavbar: Boolean = false,
    val forDropDown: Boolean = false,
    autoClose: AutoClose = AutoClose.TRUE,
    className: String? = null
) :
    Button(text, icon, style, ButtonType.BUTTON, disabled, null, true, className) {

    /**
     * Whether to automatically close dropdown menu.
     */
    var autoClose by refreshOnUpdate(autoClose)

    init {
        this.id = id
        if (!forNavbar && !forDropDown) this.role = "button"
        setInternalEventListener<DropDownButton> {
            click = { e ->
                if (parent?.parent is ContextMenu) {
                    e.asDynamic().dropDownCM = true
                } else if (forDropDown) {
                    e.stopPropagation()
                }
            }
        }
    }

    override fun render(): VNode {
        val text = createLabelWithIcon(text, icon, image)
        return if (forNavbar || forDropDown) {
            render("a", text)
        } else {
            render("button", text)
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        classSetBuilder.add("dropdown-toggle")
        when {
            forNavbar -> classSetBuilder.add("nav-link")
            forDropDown -> classSetBuilder.run { super.buildClassSet(this); add("dropdown-item") }
            else -> super.buildClassSet(classSetBuilder)
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(
            if (forDropDown || forNavbar) {
                object : AttributeSetBuilder {
                    override fun add(name: String, value: String) {
                        if (name != "type") attributeSetBuilder.add(name, value)
                    }
                }
            } else {
                attributeSetBuilder
            }
        )
        attributeSetBuilder.add("data-bs-toggle", "dropdown")
        attributeSetBuilder.add("aria-haspopup", "true")
        attributeSetBuilder.add("aria-expanded", "false")
        attributeSetBuilder.add("href", "javascript:void(0)")
        attributeSetBuilder.add(autoClose)
    }
}
