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

import io.kvision.jquery.jQuery

/**
 * Initializer for KVision Bootstrap spinner module.
 */
object BootstrapUploadModule : ModuleInitializer {
    init {
        val bootstrap = require("bootstrap")
        jQuery.fn.asDynamic()["modal"] = bootstrap.Modal.jQueryInterface
        jQuery.fn.asDynamic()["modal"].Constructor = bootstrap.Modal
    }

    override fun initialize() {
        require("bootstrap-fileinput/css/fileinput.min.css")
        require("bootstrap-fileinput/themes/explorer-fa6/theme.min.css")
        require("bootstrap-fileinput")
        require("bootstrap-fileinput/js/locales/ar.js")
        require("bootstrap-fileinput/js/locales/az.js")
        require("bootstrap-fileinput/js/locales/bg.js")
        require("bootstrap-fileinput/js/locales/ca.js")
        require("bootstrap-fileinput/js/locales/cr.js")
        require("bootstrap-fileinput/js/locales/cs.js")
        require("bootstrap-fileinput/js/locales/da.js")
        require("bootstrap-fileinput/js/locales/de.js")
        require("bootstrap-fileinput/js/locales/el.js")
        require("bootstrap-fileinput/js/locales/es.js")
        require("bootstrap-fileinput/js/locales/et.js")
        require("bootstrap-fileinput/js/locales/fa.js")
        require("bootstrap-fileinput/js/locales/fi.js")
        require("bootstrap-fileinput/js/locales/fr.js")
        require("bootstrap-fileinput/js/locales/gl.js")
        require("bootstrap-fileinput/js/locales/he.js")
        require("bootstrap-fileinput/js/locales/hu.js")
        require("bootstrap-fileinput/js/locales/id.js")
        require("bootstrap-fileinput/js/locales/it.js")
        require("bootstrap-fileinput/js/locales/ja.js")
        require("bootstrap-fileinput/js/locales/ka.js")
        require("bootstrap-fileinput/js/locales/kr.js")
        require("bootstrap-fileinput/js/locales/kz.js")
        require("bootstrap-fileinput/js/locales/lt.js")
        require("bootstrap-fileinput/js/locales/lv.js")
        require("bootstrap-fileinput/js/locales/nl.js")
        require("bootstrap-fileinput/js/locales/no.js")
        require("bootstrap-fileinput/js/locales/pl.js")
        require("bootstrap-fileinput/js/locales/pt.js")
        require("bootstrap-fileinput/js/locales/ro.js")
        require("bootstrap-fileinput/js/locales/ru.js")
        require("bootstrap-fileinput/js/locales/sk.js")
        require("bootstrap-fileinput/js/locales/sl.js")
        require("bootstrap-fileinput/js/locales/sr-latn.js")
        require("bootstrap-fileinput/js/locales/sv.js")
        require("bootstrap-fileinput/js/locales/th.js")
        require("bootstrap-fileinput/js/locales/tr.js")
        require("bootstrap-fileinput/js/locales/uk.js")
        require("bootstrap-fileinput/js/locales/uz.js")
        require("bootstrap-fileinput/js/locales/vi.js")
        require("bootstrap-fileinput/js/locales/zh.js")
        require("bootstrap-fileinput/themes/explorer-fa6/theme.js")
        require("bootstrap-fileinput/themes/fa6/theme.js")
    }
}
