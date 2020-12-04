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
package pl.treksoft.kvision.form.select

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.utils.set

/**
 * The helper component for adding options to [Select] or [SelectOptGroup].
 *
 * @constructor
 * @param value the value of the option
 * @param label the label of the option
 * @param subtext the small subtext after the label of the option
 * @param icon the icon before the label of the option
 * @param divider renders this option as a divider
 * @param disabled renders a disabled option
 * @param classes a set of CSS class names
 */
open class SelectOption(
    value: String? = null, label: String? = null, subtext: String? = null, icon: String? = null,
    divider: Boolean = false, disabled: Boolean = false, selected: Boolean = false,
    classes: Set<String> = setOf()
) : Widget(classes) {

    /**
     * The value of the option.
     */
    var value by refreshOnUpdate(value)

    /**
     * The label of the option.
     */
    var label by refreshOnUpdate(label)

    /**
     * The subtext after the label of the option.
     */
    var subtext by refreshOnUpdate(subtext)

    /**
     * The icon before the label of the option.
     */
    var icon by refreshOnUpdate(icon)

    /**
     * Determines if the option should be rendered as divider.
     */
    var divider by refreshOnUpdate(divider)

    /**
     * Determines if the option should be disabled.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * Determines if the option is selected.
     */
    var selected by refreshOnUpdate(selected)

    override fun render(): VNode {
        return if (!divider) {
            render("option", arrayOf(translate(label) ?: value))
        } else {
            render("option")
        }
    }

    @Suppress("ComplexMethod")
    override fun buildAttributesSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributesSet(attributeSetBuilder)
        if (!divider) {
            value?.let {
                attributeSetBuilder.add("value", it)
            }
            subtext?.let {
                attributeSetBuilder.add("data-subtext", translate(it))
            }
            icon?.let {
                attributeSetBuilder.add("data-icon", it)
            }
            if (disabled) {
                attributeSetBuilder.add("disabled")
            }
            if (selected) {
                attributeSetBuilder.add("selected")
            }
        } else {
            attributeSetBuilder.add("data-divider", "true")
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Select.selectOption(
    value: String? = null, label: String? = null, subtext: String? = null, icon: String? = null,
    divider: Boolean = false, disabled: Boolean = false, selected: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SelectOption.() -> Unit)? = null
): SelectOption {
    val selectOption =
        SelectOption(value, label, subtext, icon, divider, disabled, selected, classes ?: className.set).apply {
            init?.invoke(
                this
            )
        }
    this.add(selectOption)
    return selectOption
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun SelectInput.selectOption(
    value: String? = null, label: String? = null, subtext: String? = null, icon: String? = null,
    divider: Boolean = false, disabled: Boolean = false, selected: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SelectOption.() -> Unit)? = null
): SelectOption {
    val selectOption =
        SelectOption(value, label, subtext, icon, divider, disabled, selected, classes ?: className.set).apply {
            init?.invoke(
                this
            )
        }
    this.add(selectOption)
    return selectOption
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun SelectOptGroup.selectOption(
    value: String? = null, label: String? = null, subtext: String? = null, icon: String? = null,
    divider: Boolean = false, disabled: Boolean = false, selected: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SelectOption.() -> Unit)? = null
): SelectOption {
    val selectOption =
        SelectOption(value, label, subtext, icon, divider, disabled, selected, classes ?: className.set).apply {
            init?.invoke(
                this
            )
        }
    this.add(selectOption)
    return selectOption
}
