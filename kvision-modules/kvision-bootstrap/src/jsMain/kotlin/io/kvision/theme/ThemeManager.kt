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

import io.kvision.panel.Root
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window

/**
 * Bootstrap color themes.
 */
enum class Theme(internal val theme: String) {
    AUTO("auto"),
    LIGHT("light"),
    DARK("dark")
}

/**
 * Color theme manager.
 */
object ThemeManager {

    private var remember = true

    /**
     * Current color theme.
     */
    var theme: Theme = Theme.AUTO
        set(value) {
            field = value
            setStoredTheme(value)
            if (value == Theme.AUTO) {
                document.documentElement?.setAttribute("data-bs-theme", getPreferredTheme().theme)
            } else {
                document.documentElement?.setAttribute("data-bs-theme", value.theme)
            }
            Root.roots.forEach { it.restart() }
        }

    /**
     * Initialize the theme manager with the preferred color theme.
     * @param remember remember selected theme in the local storage
     */
    fun init(initialTheme: Theme? = null, remember: Boolean = true) {
        this.remember = remember
        this.theme = initialTheme ?: getStoredTheme() ?: getPreferredTheme()
        if (theme == Theme.AUTO) {
            document.documentElement?.setAttribute("data-bs-theme", getPreferredTheme().theme)
        } else {
            document.documentElement?.setAttribute("data-bs-theme", theme.theme)
        }
        window.matchMedia("(prefers-color-scheme: dark)").addEventListener("change", {
            val storedTheme = getStoredTheme() ?: Theme.AUTO
            if (storedTheme == Theme.AUTO) {
                document.documentElement?.setAttribute("data-bs-theme", getPreferredTheme().theme)
                Root.roots.forEach { it.restart() }
            }
        })
    }

    private fun getStoredTheme(): Theme? {
        return if (remember) {
            localStorage.getItem("kvision-bootstrap-theme")?.let { theme ->
                return Theme.entries.find { theme == it.theme }
            }
        } else null
    }

    private fun setStoredTheme(theme: Theme) {
        if (remember) localStorage.setItem("kvision-bootstrap-theme", theme.theme)
    }

    private fun getPreferredTheme(): Theme {
        return if (window.matchMedia("(prefers-color-scheme: dark)").matches) Theme.DARK else Theme.LIGHT
    }

}
