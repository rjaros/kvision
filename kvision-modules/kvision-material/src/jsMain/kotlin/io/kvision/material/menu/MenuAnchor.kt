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
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.Position
import io.kvision.core.Widget
import io.kvision.core.onClick
import io.kvision.html.Span
import io.kvision.panel.SimplePanel

private const val MENU_ANCHOR_ID_PREFIX = "kv_md_menu_anchor_"
private var IdCounter = 0

/**
 * Component which holds a menu and its anchor.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
interface MenuAnchor<W : Widget> : Component {
    val menu: MdMenu
    val anchor: W
}

/**
 * [MenuAnchor] implementation.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
private class MenuAnchorImpl<W : Widget>(
    position: Position,
    override val anchor: W,
    initMenu: SimplePanel.() -> MdMenu
) : Span(), MenuAnchor<W> {

    override val menu: MdMenu = initMenu()

    init {
        this.position = position
        add(anchor)
        add(menu)

        menu.anchor = anchor.id ?: (MENU_ANCHOR_ID_PREFIX + IdCounter++).also { generatedId ->
            anchor.id = generatedId
        }

        anchor.onClick<Widget> {
            menu.toggle()
        }
    }
}

/**
 * Factory for obtaining an instance of [MenuAnchor].
 *
 * The returned component is basically a [Component] that assign an id to [anchor] and affect
 * that id to the [MdMenu] computed by [initMenu].
 *
 * It also set a click listener on [anchor] that toggle the [MdMenu] visibility.
 *
 * This API is provided as a convenience for creating menu.
 */
@ExperimentalMaterialApi
fun <W : Widget> MenuAnchor(
    anchor: W,
    position: Position = Position.RELATIVE,
    initMenu: SimplePanel.() -> MdMenu
): MenuAnchor<W> = MenuAnchorImpl(
    position = position,
    anchor = anchor,
    initMenu = initMenu
)

/**
 * Factory for obtaining an instance of [MenuAnchor].
 * The returned component is added to this [Container].
 */
@ExperimentalMaterialApi
fun <W : Widget> Container.menuAnchor(
    anchor: W,
    position: Position = Position.RELATIVE,
    initMenu: SimplePanel.() -> MdMenu
): MenuAnchor<W> = MenuAnchor(
    position = position,
    anchor = anchor,
    initMenu = initMenu
).also(this::add)
