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
import io.kvision.material.util.add
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.Corner
import io.kvision.material.widget.MdWidget
import io.kvision.material.widget.Slot
import io.kvision.core.AttributeSetBuilder
import kotlin.js.Promise

private const val DEFAULT_HOVER_OPEN_DELAY = 400
private const val DEFAULT_HOVER_CLOSE_DELAY = 400

/**
 * Submenus is nested inside a menu and display a list of choices on a temporary surface.
 *
 * See https://material-web.dev/components/menu/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdSubMenu(
    anchorCorner: Corner = Corner.EndStart,
    corner: Corner = Corner.StartStart,
    hoverOpenDelay: Int = DEFAULT_HOVER_OPEN_DELAY,
    hoverCloseDelay: Int = DEFAULT_HOVER_CLOSE_DELAY,
    className: String? = null,
    init: (@MaterialMenuDsl MdSubMenu.() -> Unit)? = null
) : MdWidget(
    tag = "md-sub-menu",
    className = className,
) {

    /**
     * The [Corner] to set on the submenu.
     */
    var anchorCorner by refreshOnUpdate(anchorCorner)

    /**
     * The [Corner] to set on the submenu.
     */
    var menuCorner by refreshOnUpdate(corner)

    /**
     * The delay between mouseenter and submenu opening.
     */
    var hoverOpenDelay by refreshOnUpdate(hoverOpenDelay)

    /**
     * The delay between ponterleave and the submenu closing.
     */
    var hoverCloseDelay by refreshOnUpdate(hoverCloseDelay)

    /**
     * The submenu [MdMenuItem].
     */
    var item: MdMenuItem? = null
        set(value) {
            field = value
            Slot.Item(value)
        }

    /**
     * The submenu [MdMenu].
     */
    var menu: MdMenu? = null
        set(value) {
            field = value
            Slot.Menu(value)
        }

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("anchor-corner", anchorCorner.value)
        attributeSetBuilder.add("menu-corner", menuCorner.value)
        attributeSetBuilder.add("hover-open-delay", hoverOpenDelay)
        attributeSetBuilder.add("hover-close-delay", hoverCloseDelay)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Display
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Shows the submenu.
     */
    @JsName("showMenu")
    fun open(): Promise<Unit> {
        if (!visible) {
            return Promise.reject(IllegalStateException("Submenu is not visible"))
        }

        return requireElementD().show() as Promise<Unit>
    }

    /**
     * Closes the submenu.
     */
    @JsName("closeMenu")
    fun close(returnValue: String?): Promise<Unit> {
        if (!visible) {
            return Promise.reject(IllegalStateException("Submenu is not visible"))
        }

        return requireElementD().close(returnValue) as Promise<Unit>
    }
}

@ExperimentalMaterialApi
fun MdMenu.submenu(
    anchorCorner: Corner = Corner.EndStart,
    corner: Corner = Corner.StartStart,
    hoverOpenDelay: Int = DEFAULT_HOVER_OPEN_DELAY,
    hoverCloseDelay: Int = DEFAULT_HOVER_CLOSE_DELAY,
    className: String? = null,
    init: (@MaterialMenuDsl MdSubMenu.() -> Unit)? = null
) = MdSubMenu(
    anchorCorner = anchorCorner,
    corner = corner,
    hoverOpenDelay = hoverOpenDelay,
    hoverCloseDelay = hoverCloseDelay,
    className = className,
    init = init
).also(this::add)
