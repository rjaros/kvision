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

internal val kVManagerSelectInit = KVManagerSelect.init()

/**
 * Internal singleton object which initializes and configures KVision select module.
 */
internal object KVManagerSelect {
    internal const val AJAX_REQUEST_DELAY = 300
    internal const val KVNULL = "#kvnull"

    init {
        require("bootstrap-select/dist/css/bootstrap-select.min.css")
        require("bootstrap-select/dist/js/bootstrap-select.min.js")
        require("./js/locales/bootstrap-select/bootstrap-select-i18n.min.js")
        require("ajax-bootstrap-select/dist/css/ajax-bootstrap-select.min.css")
        require("ajax-bootstrap-select/dist/js/ajax-bootstrap-select.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.cs-CZ.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.de-DE.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.es-ES.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.fr-FR.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.it-IT.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ja-JP.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ko-KR.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.nl-NL.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.pl-PL.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.pt-BR.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ru-RU.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.sr-SP.min.js")
        require("./js/locales/ajax-bootstrap-select/ajax-bootstrap-select.tr-TR.min.js")
        js("$.fn.selectpicker.Constructor.BootstrapVersion = '4';")
        js("$.fn.selectpicker.Constructor.DEFAULTS.styleBase = 'form-control';");
        js("$.fn.selectpicker.Constructor.DEFAULTS.style = '';")
    }

    internal fun init() {}
}
