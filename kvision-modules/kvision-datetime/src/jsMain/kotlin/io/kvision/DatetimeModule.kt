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

import io.kvision.utils.useModule

@JsModule("@eonasdan/tempus-dominus/dist/css/tempus-dominus.min.css")
internal external object tempusDominusCss

@JsModule("@eonasdan/tempus-dominus/dist/locales/ar.js")
internal external val localeAr: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/ca.js")
internal external val localeCa: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/de.js")
internal external val localeDe: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/es.js")
internal external val localeEs: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/fi.js")
internal external val localeFi: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/fr.js")
internal external val localeFr: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/it.js")
internal external val localeIt: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/nl.js")
internal external val localeNl: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/pl.js")
internal external val localePl: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/pt-PT.js")
internal external val localePt: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/ro.js")
internal external val localeRo: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/ru.js")
internal external val localeRu: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/sk.js")
internal external val localeSk: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/sl.js")
internal external val localeSl: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/tr.js")
internal external val localeTr: dynamic

@JsModule("@eonasdan/tempus-dominus/dist/locales/zh-CN.js")
internal external val localeZh: dynamic

@JsModule("zzz-kvision-assets/css/kv-tempus-dominus.css")
internal external object kvTempusDominusCss

/**
 * Initializer for KVision datetime module.
 */
object DatetimeModule : ModuleInitializer {

    internal val locales = js("{}")

    init {
        useModule(tempusDominusCss)
        locales[localeAr.name] = localeAr.localization
        locales[localeCa.name] = localeCa.localization
        locales[localeDe.name] = localeDe.localization
        locales[localeEs.name] = localeEs.localization
        locales[localeFi.name] = localeFi.localization
        locales[localeFr.name] = localeFr.localization
        locales[localeIt.name] = localeIt.localization
        locales[localeNl.name] = localeNl.localization
        locales[localePl.name] = localePl.localization
        locales[localePt.name] = localePt.localization
        locales[localeRo.name] = localeRo.localization
        locales[localeRu.name] = localeRu.localization
        locales[localeSk.name] = localeSk.localization
        locales[localeSl.name] = localeSl.localization
        locales[localeTr.name] = localeTr.localization
        locales[localeZh.name] = localeZh.localization
    }

    override fun initialize() {
        useModule(kvTempusDominusCss)
    }
}
