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
package io.kvision.panel

import com.github.snabbdom.VNode
import io.kvision.core.Container
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * The HTML fieldset container.
 *
 * @constructor
 * @param legend the legend of the fieldset
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class FieldsetPanel(
    legend: String? = null,
    classes: Set<String> = setOf(),
    init: (FieldsetPanel.() -> Unit)? = null
) :
    SimplePanel(classes = classes + "kv_fieldset") {

    /**
     * The legend of the fieldset.
     */
    var legend
        get() = legendComponent.content
        set(value) {
            legendComponent.content = value
        }

    /**
     * The legend component.
     */
    protected val legendComponent = Tag(TAG.LEGEND, legend)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        val childrenVNodes = childrenVNodes()
        childrenVNodes.asDynamic().unshift(legendComponent.renderVNode())
        return render("fieldset", childrenVNodes)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.fieldsetPanel(
    legend: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (FieldsetPanel.() -> Unit)? = null
): FieldsetPanel {
    val fieldsetPanel = FieldsetPanel(legend, classes ?: className.set, init)
    this.add(fieldsetPanel)
    return fieldsetPanel
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.fieldsetPanel(
    state: ObservableState<S>,
    legend: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (FieldsetPanel.(S) -> Unit)
) = fieldsetPanel(legend, classes, className).bind(state, true, init)
