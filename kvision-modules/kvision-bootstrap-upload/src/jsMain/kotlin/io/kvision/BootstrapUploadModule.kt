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

import io.kvision.core.Bootstrap
import io.kvision.jquery.jQuery
import io.kvision.utils.useModule

@JsModule("bootstrap-fileinput")
internal external val fileinputModule: dynamic

@JsModule("bootstrap-fileinput/css/fileinput.min.css")
internal external val fileinputCss: dynamic

@JsModule("bootstrap-fileinput/themes/explorer-fa6/theme.min.css")
internal external val fileinputExplorerFa6ThemeCss: dynamic

@JsModule("bootstrap-fileinput/js/locales/ar.js")
internal external val fileinputLocaleAr: dynamic

@JsModule("bootstrap-fileinput/js/locales/az.js")
internal external val fileinputLocaleAz: dynamic

@JsModule("bootstrap-fileinput/js/locales/bg.js")
internal external val fileinputLocaleBg: dynamic

@JsModule("bootstrap-fileinput/js/locales/ca.js")
internal external val fileinputLocaleCa: dynamic

@JsModule("bootstrap-fileinput/js/locales/cr.js")
internal external val fileinputLocaleCr: dynamic

@JsModule("bootstrap-fileinput/js/locales/cs.js")
internal external val fileinputLocaleCs: dynamic

@JsModule("bootstrap-fileinput/js/locales/da.js")
internal external val fileinputLocaleDa: dynamic

@JsModule("bootstrap-fileinput/js/locales/de.js")
internal external val fileinputLocaleDe: dynamic

@JsModule("bootstrap-fileinput/js/locales/el.js")
internal external val fileinputLocaleEl: dynamic

@JsModule("bootstrap-fileinput/js/locales/es.js")
internal external val fileinputLocaleEs: dynamic

@JsModule("bootstrap-fileinput/js/locales/et.js")
internal external val fileinputLocaleEt: dynamic

@JsModule("bootstrap-fileinput/js/locales/fa.js")
internal external val fileinputLocaleFa: dynamic

@JsModule("bootstrap-fileinput/js/locales/fi.js")
internal external val fileinputLocaleFi: dynamic

@JsModule("bootstrap-fileinput/js/locales/fr.js")
internal external val fileinputLocaleFr: dynamic

@JsModule("bootstrap-fileinput/js/locales/gl.js")
internal external val fileinputLocaleGl: dynamic

@JsModule("bootstrap-fileinput/js/locales/he.js")
internal external val fileinputLocaleHe: dynamic

@JsModule("bootstrap-fileinput/js/locales/hu.js")
internal external val fileinputLocaleHu: dynamic

@JsModule("bootstrap-fileinput/js/locales/id.js")
internal external val fileinputLocaleId: dynamic

@JsModule("bootstrap-fileinput/js/locales/it.js")
internal external val fileinputLocaleIt: dynamic

@JsModule("bootstrap-fileinput/js/locales/ja.js")
internal external val fileinputLocaleJa: dynamic

@JsModule("bootstrap-fileinput/js/locales/ka.js")
internal external val fileinputLocaleKa: dynamic

@JsModule("bootstrap-fileinput/js/locales/kr.js")
internal external val fileinputLocaleKr: dynamic

@JsModule("bootstrap-fileinput/js/locales/kz.js")
internal external val fileinputLocaleKz: dynamic

@JsModule("bootstrap-fileinput/js/locales/lt.js")
internal external val fileinputLocaleLt: dynamic

@JsModule("bootstrap-fileinput/js/locales/lv.js")
internal external val fileinputLocaleLv: dynamic

@JsModule("bootstrap-fileinput/js/locales/nl.js")
internal external val fileinputLocaleNl: dynamic

@JsModule("bootstrap-fileinput/js/locales/no.js")
internal external val fileinputLocaleNo: dynamic

@JsModule("bootstrap-fileinput/js/locales/pl.js")
internal external val fileinputLocalePl: dynamic

@JsModule("bootstrap-fileinput/js/locales/pt.js")
internal external val fileinputLocalePt: dynamic

@JsModule("bootstrap-fileinput/js/locales/ro.js")
internal external val fileinputLocaleRo: dynamic

@JsModule("bootstrap-fileinput/js/locales/ru.js")
internal external val fileinputLocaleRu: dynamic

@JsModule("bootstrap-fileinput/js/locales/sk.js")
internal external val fileinputLocaleSk: dynamic

@JsModule("bootstrap-fileinput/js/locales/sl.js")
internal external val fileinputLocaleSl: dynamic

@JsModule("bootstrap-fileinput/js/locales/sr-latn.js")
internal external val fileinputLocaleSrLatn: dynamic

@JsModule("bootstrap-fileinput/js/locales/sv.js")
internal external val fileinputLocaleSv: dynamic

@JsModule("bootstrap-fileinput/js/locales/th.js")
internal external val fileinputLocaleTh: dynamic

@JsModule("bootstrap-fileinput/js/locales/tr.js")
internal external val fileinputLocaleTr: dynamic

@JsModule("bootstrap-fileinput/js/locales/uk.js")
internal external val fileinputLocaleUk: dynamic

@JsModule("bootstrap-fileinput/js/locales/uz.js")
internal external val fileinputLocaleUz: dynamic

@JsModule("bootstrap-fileinput/js/locales/vi.js")
internal external val fileinputLocaleVi: dynamic

@JsModule("bootstrap-fileinput/js/locales/zh.js")
internal external val fileinputLocaleZh: dynamic

@JsModule("bootstrap-fileinput/themes/explorer-fa6/theme.js")
internal external val fileinputExplorerFa6ThemeJs: dynamic

@JsModule("bootstrap-fileinput/themes/fa6/theme.js")
internal external val fileinputFa6ThemeJs: dynamic

@JsModule("zzz-kvision-assets/css/kv-bootstrap-upload.css")
internal external val kvBootstrapUploadCss: dynamic

/**
 * Initializer for KVision Bootstrap spinner module.
 */
object BootstrapUploadModule : ModuleInitializer {
    init {
        jQuery.fn.asDynamic()["modal"] = Bootstrap.Modal.jQueryInterface
        jQuery.fn.asDynamic()["modal"].Constructor = Bootstrap.Modal
    }

    override fun initialize() {
        useModule(fileinputCss)
        useModule(fileinputExplorerFa6ThemeCss)
        useModule(fileinputModule)
        useModule(fileinputLocaleAr)
        useModule(fileinputLocaleAz)
        useModule(fileinputLocaleBg)
        useModule(fileinputLocaleCa)
        useModule(fileinputLocaleCr)
        useModule(fileinputLocaleCs)
        useModule(fileinputLocaleDa)
        useModule(fileinputLocaleDe)
        useModule(fileinputLocaleEl)
        useModule(fileinputLocaleEs)
        useModule(fileinputLocaleEt)
        useModule(fileinputLocaleFa)
        useModule(fileinputLocaleFi)
        useModule(fileinputLocaleFr)
        useModule(fileinputLocaleGl)
        useModule(fileinputLocaleHe)
        useModule(fileinputLocaleHu)
        useModule(fileinputLocaleId)
        useModule(fileinputLocaleIt)
        useModule(fileinputLocaleJa)
        useModule(fileinputLocaleKa)
        useModule(fileinputLocaleKr)
        useModule(fileinputLocaleKz)
        useModule(fileinputLocaleLt)
        useModule(fileinputLocaleLv)
        useModule(fileinputLocaleNl)
        useModule(fileinputLocaleNo)
        useModule(fileinputLocalePl)
        useModule(fileinputLocalePt)
        useModule(fileinputLocaleRo)
        useModule(fileinputLocaleRu)
        useModule(fileinputLocaleSk)
        useModule(fileinputLocaleSl)
        useModule(fileinputLocaleSrLatn)
        useModule(fileinputLocaleSv)
        useModule(fileinputLocaleTh)
        useModule(fileinputLocaleTr)
        useModule(fileinputLocaleUk)
        useModule(fileinputLocaleUz)
        useModule(fileinputLocaleVi)
        useModule(fileinputLocaleZh)
        useModule(fileinputExplorerFa6ThemeJs)
        useModule(fileinputFa6ThemeJs)
        useModule(kvBootstrapUploadCss)
    }
}
