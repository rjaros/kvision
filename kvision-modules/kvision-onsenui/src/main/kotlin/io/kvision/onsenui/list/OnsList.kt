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

package io.kvision.onsenui.list

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * An Onsen UI list component.
 *
 * @constructor Creates a list component.
 * @param inset whether the list doesn’t cover the whole width of the parent
 * @param noborder whether the list has no borders at the top and bottom
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class OnsList(
    inset: Boolean = false,
    noborder: Boolean = false,
    classes: Set<String> = setOf(),
    init: (OnsList.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     *  Whether the list doesn’t cover the whole width of the parent.
     */
    var inset: Boolean? by refreshOnUpdate(inset)

    /**
     * Whether the list has no borders at the top and bottom.
     */
    var noborder: Boolean? by refreshOnUpdate(noborder)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-list", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        val modifiers = mutableListOf<String>()
        if (inset == true) {
            modifiers.add("inset")
        }
        if (noborder == true) {
            modifiers.add("noborder")
        }
        modifier?.let {
            modifiers.add(it)
        }
        if (modifiers.isNotEmpty()) {
            attributeSetBuilder.add("modifier", modifiers.joinToString(" "))
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.onsList(
    inset: Boolean = false,
    noborder: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsList.() -> Unit)? = null
): OnsList {
    val onsList = OnsList(inset, noborder, classes ?: className.set, init)
    this.add(onsList)
    return onsList
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.onsList(
    state: ObservableState<S>,
    inset: Boolean = false,
    noborder: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (OnsList.(S) -> Unit)
) = onsList(inset, noborder, classes, className).bind(state, true, init)
