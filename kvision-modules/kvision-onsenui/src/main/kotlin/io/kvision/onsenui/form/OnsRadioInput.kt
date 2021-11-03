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
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.form.check.CheckInput
import io.kvision.form.check.CheckInputType
import kotlinx.browser.window
import org.w3c.dom.NodeList

/**
 * OnsenUI radio button input component.
 *
 * @constructor Creates a radio button input component.
 * @param value radio button input value
 * @param inputId the ID of the input element
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class OnsRadioInput(
    value: Boolean = false,
    inputId: String? = null,
    className: String? = null,
    init: (OnsRadioInput.() -> Unit)? = null
) : CheckInput(CheckInputType.RADIO, value, className) {

    /**
     * The ID of the input element.
     */
    var inputId: String? by refreshOnUpdate(inputId)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        useSnabbdomDistinctKey()
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-radio")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        inputId?.let {
            attributeSetBuilder.add("input-id", it)
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    override fun afterInsert(node: VNode) {
        if ((getElementD()?.querySelectorAll("input")?.unsafeCast<NodeList>()?.length ?: 0) > 0) {
            refreshState()
        } else {
            window.setTimeout({
                refreshState()
            }, 0)
        }
    }

    override fun refreshState() {
        if ((getElementD()?.querySelectorAll("input")?.unsafeCast<NodeList>()?.length ?: 0) > 0) {
            val v = getElementD()?.checked?.unsafeCast<Boolean>()
            if (this.value != v) {
                getElementD()?.checked = this.value
            }
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsRadioInput(
    value: Boolean = false,
    inputId: String? = null,
    className: String? = null,
    init: (OnsRadioInput.() -> Unit)? = null
): OnsRadioInput {
    val onsRadioInput = OnsRadioInput(value, inputId, className, init)
    this.add(onsRadioInput)
    return onsRadioInput
}
