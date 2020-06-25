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

package pl.treksoft.kvision.onsenui.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.form.select.SimpleSelectInput
import pl.treksoft.kvision.utils.set
import kotlin.browser.window

/**
 * OnsenUI select input component.
 *
 * @constructor Creates a select input component.
 * @param options an optional list of options (value to label pairs) for the select control
 * @param value text input value
 * @param emptyOption determines if an empty option is automatically generated
 * @param inputId the ID of the input element
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsSelectInput(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    inputId: String? = null,
    classes: Set<String> = setOf(),
    init: (OnsSelectInput.() -> Unit)? = null
) : SimpleSelectInput(options, value, emptyOption, classes + "kv-ons-form-control") {

    /**
     * The ID of the input element.
     */
    var inputId: String? by refreshOnUpdate(inputId)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-select", childrenVNodes())
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        inputId?.let {
            sn.add("input-id" to it)
        }
        modifier?.let {
            sn.add("modifier" to it)
        }
        return sn
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
                getElementJQuery()?.`val`(it)
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
    inputId: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsSelectInput.() -> Unit)? = null
): OnsSelectInput {
    val onsSelectInput =
        OnsSelectInput(options, value, emptyOption, inputId, classes ?: className.set, init)
    this.add(onsSelectInput)
    return onsSelectInput
}
