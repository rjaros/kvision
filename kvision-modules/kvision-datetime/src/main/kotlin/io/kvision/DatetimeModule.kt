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

package io.kvision

import io.kvision.utils.createInstance

/**
 * Initializer for KVision datetime module.
 */
object DatetimeModule : ModuleInitializer {

    internal val tempusDominus: dynamic
    internal val locales = js("{}")

    init {
        require("@eonasdan/tempus-dominus/dist/css/tempus-dominus.min.css")
        tempusDominus = require("@eonasdan/tempus-dominus")
        val localeAr = require("@eonasdan/tempus-dominus/dist/locales/ar.js")
        locales[localeAr.name] = localeAr.localization
        val localeDe = require("@eonasdan/tempus-dominus/dist/locales/de.js")
        locales[localeDe.name] = localeDe.localization
        val localeEs = require("@eonasdan/tempus-dominus/dist/locales/es.js")
        locales[localeEs.name] = localeEs.localization
        val localeFi = require("@eonasdan/tempus-dominus/dist/locales/fi.js")
        locales[localeFi.name] = localeFi.localization
        val localeFr = require("@eonasdan/tempus-dominus/dist/locales/fr.js")
        locales[localeFr.name] = localeFr.localization
        val localeIt = require("@eonasdan/tempus-dominus/dist/locales/it.js")
        locales[localeIt.name] = localeIt.localization
        val localeNl = require("@eonasdan/tempus-dominus/dist/locales/nl.js")
        locales[localeNl.name] = localeNl.localization
        val localePl = require("@eonasdan/tempus-dominus/dist/locales/pl.js")
        locales[localePl.name] = localePl.localization
        val localeRo = require("@eonasdan/tempus-dominus/dist/locales/ro.js")
        locales[localeRo.name] = localeRo.localization
        val localeRu = require("@eonasdan/tempus-dominus/dist/locales/ru.js")
        locales[localeRu.name] = localeRu.localization
        val localeSl = require("@eonasdan/tempus-dominus/dist/locales/sl.js")
        locales[localeSl.name] = localeSl.localization
        val localeTr = require("@eonasdan/tempus-dominus/dist/locales/tr.js")
        locales[localeTr.name] = localeTr.localization
        val customDateFormatPlugin = require("@eonasdan/tempus-dominus/dist/plugins/customDateFormat.js")
        var tdClasses: dynamic = null

        @Suppress("UNUSED_PARAMETER")
        fun tdClassesGetter(a: dynamic, fields: dynamic, b: dynamic) {
            tdClasses = fields
        }
        tempusDominus.extend(::tdClassesGetter)
        // workaround some bugs in tempus dominus custom date format plugin
        tdClasses.ErrorMessages = tdClasses.ErrorMessages.unsafeCast<Any>().createInstance<Any>()
        customDateFormatPlugin(null, tdClasses, tempusDominus)
        val oldParseInput = tdClasses.Dates.prototype.parseInput
        tdClasses.Dates.prototype.parseInput = { input: String ->
            try {
                oldParseInput.apply(js("this"), arrayOf(input))
            } catch (e: Throwable) {
                tdClasses.DateTime.unsafeCast<Any>().createInstance<Any>()
            }
        }
        customDateFormatPlugin.installed = true
    }

    override fun initialize() {
    }
}
