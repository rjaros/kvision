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
package pl.treksoft.kvision

internal val kVManagerUploadInit = KVManagerUpload.init()

/**
 * Internal singleton object which initializes and configures KVision upload module.
 */
internal object KVManagerUpload {

    init {
        require("bootstrap-fileinput/css/fileinput.min.css")
        require("bootstrap-fileinput/themes/explorer-fas/theme.min.css")
        require("bootstrap-fileinput")
        require("./js/locales/bootstrap-fileinput/ar.js")
        require("./js/locales/bootstrap-fileinput/az.js")
        require("./js/locales/bootstrap-fileinput/bg.js")
        require("./js/locales/bootstrap-fileinput/ca.js")
        require("./js/locales/bootstrap-fileinput/cr.js")
        require("./js/locales/bootstrap-fileinput/cs.js")
        require("./js/locales/bootstrap-fileinput/da.js")
        require("./js/locales/bootstrap-fileinput/de.js")
        require("./js/locales/bootstrap-fileinput/el.js")
        require("./js/locales/bootstrap-fileinput/es.js")
        require("./js/locales/bootstrap-fileinput/et.js")
        require("./js/locales/bootstrap-fileinput/fa.js")
        require("./js/locales/bootstrap-fileinput/fi.js")
        require("./js/locales/bootstrap-fileinput/fr.js")
        require("./js/locales/bootstrap-fileinput/gl.js")
        require("./js/locales/bootstrap-fileinput/id.js")
        require("./js/locales/bootstrap-fileinput/it.js")
        require("./js/locales/bootstrap-fileinput/ja.js")
        require("./js/locales/bootstrap-fileinput/ka.js")
        require("./js/locales/bootstrap-fileinput/kr.js")
        require("./js/locales/bootstrap-fileinput/kz.js")
        require("./js/locales/bootstrap-fileinput/lt.js")
        require("./js/locales/bootstrap-fileinput/nl.js")
        require("./js/locales/bootstrap-fileinput/no.js")
        require("./js/locales/bootstrap-fileinput/pl.js")
        require("./js/locales/bootstrap-fileinput/pt.js")
        require("./js/locales/bootstrap-fileinput/ro.js")
        require("./js/locales/bootstrap-fileinput/ru.js")
        require("./js/locales/bootstrap-fileinput/sk.js")
        require("./js/locales/bootstrap-fileinput/sl.js")
        require("./js/locales/bootstrap-fileinput/sv.js")
        require("./js/locales/bootstrap-fileinput/th.js")
        require("./js/locales/bootstrap-fileinput/tr.js")
        require("./js/locales/bootstrap-fileinput/uk.js")
        require("./js/locales/bootstrap-fileinput/vi.js")
        require("./js/locales/bootstrap-fileinput/zh.js")
        require("bootstrap-fileinput/themes/explorer-fas/theme.min.js")
        require("bootstrap-fileinput/themes/fas/theme.min.js")
    }

    internal fun init() {}
}
