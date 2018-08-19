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

import org.w3c.xhr.XMLHttpRequest
import pl.treksoft.kvision.panel.Root
import kotlin.browser.window
import kotlin.js.Promise

external class Jed(json: dynamic) {
    fun gettext(key: String): String
    fun ngettext(singularKey: String, pluralKey: String, value: Int): String
    fun sprintf(format: String, value: Int): String
}

private const val I18N_SINGLE_DELIMITER = "###KvI18nS###"
private const val I18N_PLURAL_DELIMITER = "###KvI18nP###"

/**
 * A singleton object used for translations.
 */
object I18n {

    private val defaultLanguage = window.navigator.language.split("-")[0]

    /**
     * Main language of the application.
     */
    var language = defaultLanguage
        set(value) {
            field = value
            Root.roots.forEach { it.reRender() }
        }

    private val cache = mutableMapOf<String, Jed>()

    /**
     * I18n initialization function.
     * Should be called in the main function of the application.
     * @param languages a list of supported languages.
     * @param initCallback a code to run after the initialization process is complete.
     */
    fun init(vararg languages: String, initCallback: () -> Unit) {
        val promises = languages.map {
            I18n.readMessages(it)
        }.toTypedArray()
        Promise.all(promises).then { initCallback() }
    }

    private fun readMessages(language: String): Promise<Jed> {
        return Promise { resolve, _ ->
            val xmlHttpRequest = XMLHttpRequest()
            xmlHttpRequest.overrideMimeType("application/json")
            xmlHttpRequest.open("GET", "js/messages-$language.json", true)
            xmlHttpRequest.onreadystatechange = {
                if (xmlHttpRequest.readyState.toInt() == 4 && (xmlHttpRequest.status.toInt() == 200 ||
                            xmlHttpRequest.status.toInt() == 0)
                ) {
                    val json = JSON.parse<dynamic>(xmlHttpRequest.responseText)
                    val jed = Jed(json)
                    cache[language] = jed
                    resolve(jed)
                }
            }
            xmlHttpRequest.send()
        }
    }

    /**
     * A static translation function for a singular form.
     * @param key a translation key.
     * @return translated text.
     */
    fun gettext(key: String): String {
        return cache[language]?.gettext(key) ?: key
    }

    /**
     * A static translation function for a plural form.
     * @param singularKey a translation key for a singular form.
     * @param pluralKey a translation key for a plural form.
     * @param value a count value.
     * @return translated text.
     */
    fun ngettext(singularKey: String, pluralKey: String, value: Int): String {
        return cache[language]?.run {
            sprintf(ngettext(singularKey, pluralKey, value), value)
        } ?: if (value == 1) singularKey else pluralKey
    }

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

    internal fun trans(text: String): String {
        return if (text.startsWith(I18N_SINGLE_DELIMITER)) {
            gettext(text.substring(I18N_SINGLE_DELIMITER.length))
        } else if (text.startsWith(I18N_PLURAL_DELIMITER)) {
            val tab = text.substring(I18N_PLURAL_DELIMITER.length).split(I18N_PLURAL_DELIMITER)
            if (tab.size == 3) {
                ngettext(tab[0], tab[1], tab[2].toIntOrNull() ?: 1)
            } else {
                text
            }
        } else {
            text
        }
    }

    internal fun trans(text: String?): String? {
        return text?.let {
            trans(it)
        }
    }
}
