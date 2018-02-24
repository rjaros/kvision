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
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget

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
    divider: Boolean = false, disabled: Boolean = false,
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

    override fun render(): VNode {
        return if (!divider) {
            render("option", arrayOf(label ?: value))
        } else {
            render("option")
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        if (!divider) {
            value?.let {
                sn.add("value" to it)
            }
            subtext?.let {
                sn.add("data-subtext" to it)
            }
            icon?.let {
                if (it.startsWith("fa-")) {
                    sn.add("data-icon" to "fa $it")
                } else {
                    sn.add("data-icon" to "glyphicon-$it")
                }
            }
            if (disabled) {
                sn.add("disabled" to "disabled")
            }
        } else {
            sn.add("data-divider" to "true")
        }
        return sn
    }
}
