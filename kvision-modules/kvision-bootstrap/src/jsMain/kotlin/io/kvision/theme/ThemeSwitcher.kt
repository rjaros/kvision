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

package io.kvision.theme

import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.ButtonType

/**
 * Color theme switcher component.
 * @param title switcher title
 * @param style button style
 * @param round use round switcher style
 * @param disabled determines if the button is disabled on start
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class ThemeSwitcher(
    title: String? = "Switch color theme",
    style: ButtonStyle = ButtonStyle.SECONDARY,
    round: Boolean = false,
    disabled: Boolean = false,
    className: String? = null,
    init: (ThemeSwitcher.() -> Unit)? = null
) : Button("", icon = null, style, ButtonType.BUTTON, disabled, className = className) {

    /**
     * Round switcher style.
     */
    var round by refreshOnUpdate(round)

    init {
        this.icon = when (ThemeManager.theme) {
            Theme.AUTO -> "fas fa-circle-half-stroke"
            Theme.LIGHT -> "fas fa-moon"
            Theme.DARK -> "fas fa-sun"
        }
        this.title = title
        @Suppress("LeakingThis")
        onClick {
            if (ThemeManager.theme == Theme.DARK) {
                ThemeManager.theme = Theme.LIGHT
                icon = "fas fa-moon"
            } else {
                ThemeManager.theme = Theme.DARK
                icon = "fas fa-sun"
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (round) classSetBuilder.add("rounded-circle")
    }

}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.themeSwitcher(
    title: String? = "Switch color theme",
    style: ButtonStyle = ButtonStyle.SECONDARY,
    round: Boolean = false,
    disabled: Boolean = false,
    className: String? = null,
    init: (ThemeSwitcher.() -> Unit)? = null
): ThemeSwitcher {
    val themeSwitcher = ThemeSwitcher(title, style, round, disabled, className, init)
    this.add(themeSwitcher)
    return themeSwitcher
}
