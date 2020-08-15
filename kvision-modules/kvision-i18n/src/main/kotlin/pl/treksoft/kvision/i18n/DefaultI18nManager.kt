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

import pl.treksoft.kvision.require

class DefaultI18nManager(translations: Map<String, dynamic>) : I18nManager {

    private val i18n = require("gettext.js").default()

    init {
        translations.forEach {
            val json = it.value
            json[""].language = it.key
            @Suppress("UnsafeCastFromDynamic")
            i18n.loadJSON(json, "messages")
        }
    }

    override fun gettext(key: String): String {
        i18n.setLocale(I18n.language)
        @Suppress("UnsafeCastFromDynamic")
        return i18n.gettext(key) ?: key
    }

    override fun ngettext(singularKey: String, pluralKey: String, value: Int): String {
        i18n.setLocale(I18n.language)
        @Suppress("UnsafeCastFromDynamic")
        return i18n.ngettext(singularKey, pluralKey, value) ?: if (value == 1) singularKey else pluralKey
    }

}
