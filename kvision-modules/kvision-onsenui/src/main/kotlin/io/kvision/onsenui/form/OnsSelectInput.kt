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

package io.kvision.onsenui.form

import com.github.snabbdom.VNode
import kotlinx.browser.window
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.getElementJQuery
import io.kvision.core.getElementJQueryD
import io.kvision.form.select.SimpleSelectInput
import io.kvision.utils.set

/**
 * OnsenUI select input component.
 *
 * @constructor Creates a select input component.
 * @param options an optional list of options (value to label pairs) for the select control
 * @param value text input value
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param selectId the ID of the select element
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsSelectInput(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    selectId: String? = null,
    classes: Set<String> = setOf(),
    init: (OnsSelectInput.() -> Unit)? = null
) : SimpleSelectInput(options, value, emptyOption, multiple, selectSize, classes + "kv-ons-form-control") {

    /**
     * The ID of the select element.
     */
    var selectId: String? by refreshOnUpdate(selectId)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun calculateValue(v: Any): String? {
        val vInt = getElementJQuery()?.find("select")?.`val`()
        return vInt?.let { super.calculateValue(it) }
    }

    override fun render(): VNode {
        return render("ons-select", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        selectId?.let {
            attributeSetBuilder.add("select-id", it)
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    override fun afterInsert(node: VNode) {
        if ((getElementJQuery()?.find("select")?.length?.toInt() ?: 0) > 0) {
            refreshState()
        } else {
            window.setTimeout({
                refreshState()
            }, 0)
        }
    }

    override fun refreshState() {
        if ((getElementJQuery()?.find("select")?.length?.toInt() ?: 0) > 0) {
            value?.let {
                if (this.multiple) {
                    getElementJQuery()?.find("select")?.`val`(it.split(",").toTypedArray())
                } else {
                    getElementJQuery()?.`val`(it)
                }
            } ?: getElementJQueryD()?.`val`(null)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsSelectInput(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    inputId: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsSelectInput.() -> Unit)? = null
): OnsSelectInput {
    val onsSelectInput =
        OnsSelectInput(options, value, emptyOption, multiple, selectSize, inputId, classes ?: className.set, init)
    this.add(onsSelectInput)
    return onsSelectInput
}
