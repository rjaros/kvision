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
package io.kvision.material.chips

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.widget.MdListWidget
import io.kvision.material.widget.toItemWidget
import io.kvision.material.widget.toItemWidgetArrayOrDefault
import io.kvision.core.Container
import io.kvision.core.onEvent
import io.kvision.utils.event
import org.w3c.dom.events.Event

/**
 * Chips should always appear in a set.
 * Chip set are toolbars that can display any type of chip or other toolbar items.
 *
 * https://material-web.dev/components/chip/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdChipSet(
    className: String? = null,
    init: (MdChipSet.() -> Unit)? = null
) : MdListWidget<MdChip>(
    tag = "md-chip-set",
    className = className
) {

    private val chipRemoveEventListenerIds by lazy { mutableMapOf<MdChip, Int>() }

    /**
     * The chips of this chip set.
     */
    val chips: Array<MdChip>
        get() = toItemWidgetArrayOrDefault(getElementD()?.chips, listDelegate::items)

    init {
        listDelegate.onAdded = ::onChipAdded
        listDelegate.onRemoved = ::onChipRemoved
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Notifies of addition of a chip.
     */
    private fun onChipAdded(chip: MdChip) {
        if (chip.canBeRemoved) {
            chipRemoveEventListenerIds[chip] = chip.onEvent {
                event("remove", ::onChipRemovedFromUserInteraction)
            }
        }
    }

    /**
     * Notifies of removal of a chip.
     */
    private fun onChipRemoved(chip: MdChip) {
        if (chip.canBeRemoved) {
            chipRemoveEventListenerIds[chip]
                ?.let(chip::removeEventListener)
        }
    }

    /**
     * Handles chip remove event.
     *
     * Prevents the default remove action of the underlying material HTML element and instead remove
     * the [MdChip] instance from the [listDelegate] which in turns will trigger a rerendering.
     */
    private fun onChipRemovedFromUserInteraction(event: Event) {
        val chip = toItemWidget<MdChip>(event.target) ?: return
        event.preventDefault()
        listDelegate.remove(chip)
    }
}

@ExperimentalMaterialApi
fun Container.chipSet(
    className: String? = null,
    init: (MdChipSet.() -> Unit)? = null
) = MdChipSet(
    className = className,
    init = init
).also(this::add)
