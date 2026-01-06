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

import io.kvision.i18n.I18n
import io.kvision.utils.obj
import io.kvision.utils.useModule
import kotlinx.browser.window

@JsModule("trix/dist/trix.esm.js")
internal external val trixModule: dynamic

@JsModule("trix/dist/trix.css")
internal external object trixCss

@JsModule("zzz-kvision-assets/css/kv-trix.css")
internal external object kvTrixCss

/**
 * Initializer for KVision RichText module.
 */
object RichTextModule : ModuleInitializer {

    init {
        val trix = trixModule
        val trixLocales = obj {}
        trixLocales["en"] = getTrixLocaleEn()
        trixLocales["pl"] = getTrixLocalePl()
        val orig = trix.config.toolbar.getDefaultHTML
        trix.config.toolbar.getDefaultHTML = {
            val config = if (trixLocales[I18n.language] != undefined) {
                trixLocales[I18n.language]
            } else {
                trixLocales["en"]
            }
            for (key in js("Object").keys(trix.config.lang)) {
                trix.config.lang[key] = config[key]
            }
            orig()
        }
        window.asDynamic().Trix = obj {
            config = obj {
                languages = trixLocales
            }
        }
    }

    override fun initialize() {
        useModule(trixCss)
        useModule(kvTrixCss)
    }
}
