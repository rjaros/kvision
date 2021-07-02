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
 * Internal singleton object which initializes and configures KVision upload module.
 */
internal object KVManagerUpload {

    init {
        val bootstrap = require("bootstrap")
        jQuery.fn.asDynamic()["modal"] = bootstrap.Modal.jQueryInterface
        jQuery.fn.asDynamic()["modal"].Constructor = bootstrap.Modal
        require("bootstrap-fileinput/css/fileinput.min.css")
        require("bootstrap-fileinput/themes/explorer-fas/theme.min.css")
        require("bootstrap-fileinput")
        require("kvision-assets/js/locales/bootstrap-fileinput/ar.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/az.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/bg.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/ca.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/cr.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/cs.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/da.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/de.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/el.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/es.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/et.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/fa.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/fi.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/fr.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/gl.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/id.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/it.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/ja.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/ka.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/kr.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/kz.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/lt.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/nl.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/no.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/pl.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/pt.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/ro.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/ru.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/sk.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/sl.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/sv.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/th.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/tr.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/uk.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/vi.js")
        require("kvision-assets/js/locales/bootstrap-fileinput/zh.js")
        require("bootstrap-fileinput/themes/explorer-fas/theme.js")
        require("bootstrap-fileinput/themes/bs5/theme.js")
    }

    internal fun init() {}
}
