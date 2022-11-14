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
@file:Suppress("DEPRECATION")

package io.kvision.form.select

import io.kvision.core.AttributeSetBuilder
import io.kvision.core.StringPair
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode

/**
 * The helper container for adding option groups to [Select].
 *
 * The option group can be populated directly from *options* parameter or manually by adding
 * [SelectOption] components to the container.
 *
 * @constructor
 * @param label the label of the group
 * @param options an optional list of options (label to value pairs) for the group
 * @param maxOptions maximal number of selected options in the group
 * @param disabled renders a disabled group
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SelectOptGroup(
    label: String, options: List<StringPair>? = null, maxOptions: Int? = null,
    disabled: Boolean = false, className: String? = null, init: (SelectOptGroup.() -> Unit)? = null
) : SimplePanel(className) {
    /**
     * A label for the group.
     */
    var label by refreshOnUpdate(label)

    /**
     * A list of options (label to value pairs) for the group.
     */
    var options by refreshOnUpdate(options) { setChildrenFromOptions() }

    /**
     * Maximal number of selected options in the group.
     */
    var maxOptions by refreshOnUpdate(maxOptions)

    /**
     * Determines if the group is disabled.
     */
    var disabled by refreshOnUpdate(disabled)

    init {
        setChildrenFromOptions()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("optgroup", childrenVNodes())
    }

    private fun setChildrenFromOptions() {
        this.removeAll()
        options?.let {
            val c = it.map {
                SelectOption(it.first, it.second)
            }
            this.addAll(c)
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("label", translate(label))
        maxOptions?.let {
            attributeSetBuilder.add("data-max-options", "" + it)
        }
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Select.selectOptGroup(
    label: String, options: List<StringPair>? = null, maxOptions: Int? = null,
    disabled: Boolean = false,
    className: String? = null,
    init: (SelectOptGroup.() -> Unit)? = null
): SelectOptGroup {
    val selectOptGroup =
        SelectOptGroup(label, options, maxOptions, disabled, className, init)
    this.add(selectOptGroup)
    return selectOptGroup
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun SelectInput.selectOptGroup(
    label: String, options: List<StringPair>? = null, maxOptions: Int? = null,
    disabled: Boolean = false,
    className: String? = null,
    init: (SelectOptGroup.() -> Unit)? = null
): SelectOptGroup {
    val selectOptGroup =
        SelectOptGroup(label, options, maxOptions, disabled, className, init)
    this.add(selectOptGroup)
    return selectOptGroup
}
