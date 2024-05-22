/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 Maanrifa Bacar Ali <dev.manrif@gmail.com>
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
package io.kvision.material.menu

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.utils.SnOn
import io.kvision.utils.event
import org.w3c.dom.events.Event

/**
 * Requests the parent menu to deselect other items when a submenu opens.
 * [Event.bubbles], [Event.composed].
 */
@ExperimentalMaterialApi
fun SnOn<MdSubMenu>.deactivateItems(block: (Event) -> Unit) {
    event("deactivate-items", block)
}

/**
 * Requests the parent to make the slotted item focusable and focus the item.
 * [Event.bubbles], [Event.composed].
 */
@ExperimentalMaterialApi
fun SnOn<MdSubMenu>.requestActivation(block: (Event) -> Unit) {
    event("request-activation", block)
}

/**
 * Requests the parent menu to deactivate the typeahead functionality when a submenu opens.
 * [Event.bubbles], [Event.composed].
 */
@ExperimentalMaterialApi
fun SnOn<MdSubMenu>.deactivateTypeahead(block: (Event) -> Unit) {
    event("deactivate-typeahead", block)
}

/**
 * Requests the parent menu to activate the typeahead functionality when a submenu closes.
 * [Event.bubbles], [Event.composed].
 */
@ExperimentalMaterialApi
fun SnOn<MdSubMenu>.activateTypeahead(block: (Event) -> Unit) {
    event("activate-typeahead", block)
}
