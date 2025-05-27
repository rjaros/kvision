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

private const val I18N_SINGLE_DELIMITER = "###KvI18nS###"
private const val I18N_PLURAL_DELIMITER = "###KvI18nP###"

interface I18nManager {
    /**
     * A static translation function for a singular form.
     * @param key a translation key.
     * @param args additional parameters for substitution.
     * @return translated text.
     */
    fun gettext(key: String, vararg args: Any?): String

    /**
     * A static translation function for a plural form.
     * @param singularKey a translation key for a singular form.
     * @param pluralKey a translation key for a plural form.
     * @param value a count value.
     * @param args additional parameters for substitution.
     * @return translated text.
     */
    fun ngettext(singularKey: String, pluralKey: String, value: Int, vararg args: Any?): String

    /**
     * A dynamic translation function for a singular form.
     * @param key a translation key.
     * @return text marked for a dynamic translation.
     */
    fun tr(key: String): String {
        return I18N_SINGLE_DELIMITER + key
    }

    /**
     * A dynamic translation function for a plural form.
     * @param singularKey a translation key for a singular form.
     * @param pluralKey a translation key for a plural form.
     * @param value a count value.
     * @return text marked for a dynamic translation.
     */
    fun ntr(singularKey: String, pluralKey: String, value: Int): String {
        return I18N_PLURAL_DELIMITER + singularKey + I18N_PLURAL_DELIMITER + pluralKey + I18N_PLURAL_DELIMITER + value
    }

    /**
     * A dynamic translation function.
     * @param text text marked for a dynamic translation.
     * @return translated text.
     */
    fun trans(text: String): String {
        return if (text.startsWith(I18N_SINGLE_DELIMITER)) {
            gettext(text.substring(I18N_SINGLE_DELIMITER.length))
        } else if (text.startsWith(I18N_PLURAL_DELIMITER)) {
            val tab = text.substring(I18N_PLURAL_DELIMITER.length).split(I18N_PLURAL_DELIMITER)
            @Suppress("MagicNumber")
            if (tab.size == 3) {
                ngettext(tab[0], tab[1], tab[2].toIntOrNull() ?: 1, tab[2].toIntOrNull() ?: 1)
            } else {
                text
            }
        } else {
            text
        }
    }

    /**
     * A dynamic translation function.
     * @param text text marked for a dynamic translation.
     * @return translated text.
     */
    fun trans(text: String?): String? {
        return text?.let {
            trans(it)
        }
    }

}
