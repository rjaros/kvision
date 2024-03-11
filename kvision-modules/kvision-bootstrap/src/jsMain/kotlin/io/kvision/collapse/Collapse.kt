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
package io.kvision.collapse

import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.panel.SimplePanel

/**
 * Collapsable component. Needs to be triggered by a button. See [Button.forCollapse].
 * @param id of element
 * @param groupId of element; needed for multi trigger buttons
 * @param horizontal transition direction
 * @param opened component is pre-opened if true
 * @param init an initializer extension function
 * @see <a href="https://getbootstrap.com/docs/5.3/components/collapse/"/>Bootstrap documentation</a>
 * @author André Harnisch
 */
class Collapse(
    id: String,
    groupId: String? = null,
    horizontal: Boolean = false,
    opened: Boolean = false,
    init: SimplePanel.() -> Unit
): SimplePanel("collapse") {

    init {
        this.id = id
        if (groupId != null) addCssClass(groupId)
        if (horizontal) addCssClass("collapse-horizontal")
        if (opened) addCssClass("show")
        init()
    }
}

/**
 * DSL builder extension function.
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.collapse(
    id: String,
    groupId: String? = null,
    horizontal: Boolean = false,
    opened: Boolean = false,
    init: SimplePanel.() -> Unit
) = Collapse(id, groupId, horizontal, opened, init).also { add(it) }

/**
 * Transforms this button to a trigger for the given collapse-id.
 * @param id of the collapsable
 */
fun Button.forCollapse(id: String) {
    this.setAttribute("data-bs-toggle", "collapse")
    this.setAttribute("data-bs-target", "#$id")
    this.setAttribute("aria-controls", id)
    this.setAttribute("aria-expanded", "false")
}

/**
 * Transforms this button to a trigger for the given group and collapse-ids.
 * @param groupClass name of class group
 * @param ids all ids of the group
 */
fun Button.forCollapse(groupClass: String, vararg ids: String) {
    this.setAttribute("data-bs-toggle", "collapse")
    this.setAttribute("data-bs-target", ".$groupClass")
    this.setAttribute("aria-controls", ids.joinToString(" "))
    this.setAttribute("aria-expanded", "false")
}
