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
package pl.treksoft.kvision.i18n

external class Jed(json: dynamic) {
    fun gettext(key: String): String
    fun ngettext(singularKey: String, pluralKey: String, value: Int): String
    fun sprintf(format: String, value: Int): String
}

class DefaultI18nManager(translations: Map<String, dynamic>) : I18nManager {

    private val cache: Map<String, Jed> = translations.map { it.key to Jed(it.value) }.toMap()

    override fun gettext(key: String): String {
        return cache[I18n.language]?.gettext(key) ?: key
    }

    override fun ngettext(singularKey: String, pluralKey: String, value: Int): String {
        return cache[I18n.language]?.run {
            sprintf(ngettext(singularKey, pluralKey, value), value)
        } ?: if (value == 1) singularKey else pluralKey
    }

}
