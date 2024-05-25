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
import io.kvision.material.container.MdListContainer
import io.kvision.material.util.add
import io.kvision.material.util.addBool
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.Corner
import io.kvision.material.widget.FocusState
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container

internal const val DEFAULT_TYPEAHEAD_DELAY = 200

enum class MenuPositioning(internal val value: String) {
    Absolute("absolute"),
    Fixed("fixed"),
    Document("document"),
    Popover("popover")
}

/**
 * Menus display a list of choices on a temporary surface.
 *
 * See https://material-web.dev/components/menu/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdMenu(
    anchor: String? = null,
    positioning: MenuPositioning = MenuPositioning.Absolute,
    quick: Boolean = false,
    hasOverflow: Boolean = false,
    xOffset: Int = 0,
    yOffset: Int = 0,
    typeaheadDelay: Int = DEFAULT_TYPEAHEAD_DELAY,
    anchorCorner: Corner = Corner.EndStart,
    corner: Corner = Corner.StartStart,
    stayOpenOnOutsideClick: Boolean = false,
    stayOpenOnFocusout: Boolean = false,
    skipRestoreFocus: Boolean = false,
    defaultFocus: FocusState = FocusState.FirstItem,
    noNavigationWrap: Boolean = false,
    className: String? = null,
    init: (@MaterialMenuDsl MdMenu.() -> Unit)? = null
) : MdListContainer<MdMenuItem>(
    tag = "md-menu",
    className = className
) {

    /**
     * The ID of the element in the same root node in which the menu should align to.
     * Overrides setting anchorElement = elementReference.
     *
     * NOTE: anchor or anchorElement must either be an HTMLElement or resolve to an HTMLElement in
     * order for menu to open.
     */
    var anchor by refreshOnUpdate(anchor)

    /**
     * Whether the positioning algorithim should calculate relative to the parent of the anchor
     * element (absolute) or relative to the window (fixed).
     *
     * NOTE: Fixed menus will not scroll with the page and will be fixed to the window instead.
     */
    var positioning by refreshOnUpdate(positioning)

    /**
     * Skips the opening and closing animations.
     */
    var quick by refreshOnUpdate(quick)

    /**
     * Displays overflow content like a submenu.
     * Not required in most cases when [positioning] is [MenuPositioning.Popover].
     *
     * NOTE: This may cause adverse effects if you set max-height and have items overflowing items
     * in the "y" direction.
     */
    var hasOverflow by refreshOnUpdate(hasOverflow)

    /**
     * Offsets the menu's inline alignment from the anchor by the given number in pixels. This value
     * is direction aware and will follow the LTR / RTL direction.
     *
     * e.g. LTR: positive -> right, negative -> left
     *      RTL: positive -> left, negative -> right
     */
    var xOffset by refreshOnUpdate(xOffset)

    /**
     * Offsets the menu's block alignment from the anchor by the given number in pixels.
     *
     * e.g. positive -> down, negative -> up
     */
    var yOffset by refreshOnUpdate(yOffset)

    /**
     * The max time between the keystrokes of the typeahead menu behavior before it clears the
     * typeahead buffer.
     */
    var typeaheadDelay by refreshOnUpdate(typeaheadDelay)

    /**
     * The corner of the anchor which to align the menu in the standard logical property style of
     * <block>-<inline> e.g. `'end-start'`.
     *
     * NOTE: This value may not be respected by the menu positioning algorithm if the menu would
     * render outisde the viewport.
     */
    var anchorCorner by refreshOnUpdate(anchorCorner)

    /**
     * The corner of the menu which to align the anchor in the standard logical property style of
     * <block>-<inline> e.g. `'start-start'`.
     *
     * NOTE: This value may not be respected by the menu positioning algorithm if the menu would
     * render outisde the viewport.
     */
    var menuCorner by refreshOnUpdate(corner)

    /**
     * Keeps the user clicks outside the menu.
     *
     * NOTE: clicking outside may still cause focusout to close the menu so see
     * [stayOpenOnFocusout].
     */
    var stayOpenOnOutsideClick by refreshOnUpdate(stayOpenOnOutsideClick)

    /**
     * Keeps the menu open when focus leaves the menu's composed subtree.
     *
     * NOTE: Focusout behavior will stop propagation of the focusout event. Set this property to
     * true to opt-out of menu's focusout handling altogether.
     */
    var stayOpenOnFocusout by refreshOnUpdate(stayOpenOnFocusout)

    /**
     * After closing, does not restore focus to the last focused element before the menu was opened.
     */
    var skipRestoreFocus by refreshOnUpdate(skipRestoreFocus)

    /**
     * The element that should be focused by default once opened.
     *
     * NOTE: When setting default focus to [FocusState.ListRoot], remember to change
     * [tabindex] to `0` and change md-menu's display to something other than `display: contents`
     * when necessary.
     */
    var defaultFocus by refreshOnUpdate(defaultFocus)

    /**
     * Turns off navigation wrapping. By default, navigating past the end of the
     * menu items will wrap focus back to the beginning and vice versa. Use this
     * for ARIA patterns that do not wrap focus, like combobox.
     */
    var noNavigationWrap by refreshOnUpdate(noNavigationWrap)

    /**
     * Indicates that the menu is in an open state.
     */
    val open: Boolean
        get() = getElementD()?.open == true

    init {
        init?.let { this.it() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        anchor?.let {
            attributeSetBuilder.add("anchor", it)
        }

        attributeSetBuilder.add("positioning", positioning.value)

        if (quick) {
            attributeSetBuilder.addBool("quick")
        }

        if (hasOverflow) {
            attributeSetBuilder.addBool("has-overflow")
        }

        if (xOffset > 0) {
            attributeSetBuilder.add("x-offset", xOffset)
        }

        if (xOffset > 0) {
            attributeSetBuilder.add("y-offset", yOffset)
        }

        attributeSetBuilder.add("typeahead-delay", typeaheadDelay)
        attributeSetBuilder.add("anchor-corner", anchorCorner.value)
        attributeSetBuilder.add("menu-corner", menuCorner.value)

        if (stayOpenOnOutsideClick) {
            attributeSetBuilder.addBool("stay-open-on-outside-click")
        }

        if (stayOpenOnFocusout) {
            attributeSetBuilder.addBool("stay-open-on-focusout")
        }

        if (skipRestoreFocus) {
            attributeSetBuilder.addBool("skip-restore-focus")
        }

        attributeSetBuilder.add("default-focus", defaultFocus.value)

        if (noNavigationWrap) {
            attributeSetBuilder.add("no-navigation-wrap")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Display
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Shows the menu.
     */
    @JsName("showMenu")
    fun open() {
        requireElementD().show()
    }

    /**
     * Closes the menu.
     */
    @JsName("closeMenu")
    fun close() {
        requireElementD().close()
    }

    /**
     * Toggles the menu open state.
     */
    fun toggle() {
        if (open) close() else open()
    }
}

@ExperimentalMaterialApi
fun Container.menu(
    anchor: String? = null,
    positioning: MenuPositioning = MenuPositioning.Absolute,
    quick: Boolean = false,
    hasOverflow: Boolean = false,
    xOffset: Int = 0,
    yOffset: Int = 0,
    typeaheadDelay: Int = DEFAULT_TYPEAHEAD_DELAY,
    anchorCorner: Corner = Corner.EndStart,
    corner: Corner = Corner.StartStart,
    stayOpenOnOutsideClick: Boolean = false,
    stayOpenOnFocusout: Boolean = false,
    skipRestoreFocus: Boolean = false,
    defaultFocus: FocusState = FocusState.FirstItem,
    noNavigationWrap: Boolean = false,
    className: String? = null,
    init: (@MaterialMenuDsl MdMenu.() -> Unit)? = null
) = MdMenu(
    anchor = anchor,
    positioning = positioning,
    quick = quick,
    hasOverflow = hasOverflow,
    xOffset = xOffset,
    yOffset = yOffset,
    typeaheadDelay = typeaheadDelay,
    anchorCorner = anchorCorner,
    corner = corner,
    stayOpenOnOutsideClick = stayOpenOnOutsideClick,
    stayOpenOnFocusout = stayOpenOnFocusout,
    skipRestoreFocus = skipRestoreFocus,
    defaultFocus = defaultFocus,
    noNavigationWrap = noNavigationWrap,
    className = className,
    init = init
).also(this::add)
