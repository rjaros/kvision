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
package io.kvision.i18n

import io.kvision.panel.Root
import kotlinx.browser.window

/**
 * A singleton object used for translations.
 */
object I18n : I18nManager {

    var manager: I18nManager = SimpleI18nManager()

    private val defaultLanguage = window.navigator.language.split("-")[0]

    /**
     * Main language of the application.
     */
    var language = defaultLanguage
        set(value) {
            field = value
            Root.roots.forEach { it.restart() }
        }

    override fun gettext(key: String, vararg args: Any?): String {
        return manager.gettext(key, *args)
    }

    override fun ngettext(singularKey: String, pluralKey: String, value: Int, vararg args: Any?): String {
        return manager.ngettext(singularKey, pluralKey, value, *args)
    }

    fun detectDecimalSeparator(): Char {
        return try {
            (1.1).asDynamic().toLocaleString(language).unsafeCast<String>().dropLast(1).last()
        } catch (e: Exception) {
            '.'
        }
    }
}

/**
 * A top level helper function for translations.
 */
fun tr(key: String): String = I18n.tr(key)

/**
 * A top level helper function for translations.
 */
fun gettext(key: String, vararg args: Any?): String = I18n.gettext(key, *args)
