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

import kotlinx.browser.window

/**
 * Initializer for KVision Bootstrap select module.
 */
@Deprecated("Use TomSelectModule instead from the kvision-tom-select module. The library that this component is based on is no longer maintained and this module will be removed in KVision 6.")
object BootstrapSelectModule : ModuleInitializer {
    internal val jquery = require("jquery")
    internal val bootstrap = require("bootstrap")
    init {
        window.asDynamic()["$"] = jquery
        window.asDynamic()["jQuery"] = jquery
        window.asDynamic()["bootstrap"] = bootstrap
        js("if ($.fn.dropdown === undefined) { $.fn.dropdown={'Constructor' : {'VERSION' : '5.0.0'}}; };")
    }

    internal const val AJAX_REQUEST_DELAY = 300
    internal const val KVNULL = "#kvnull"

    override fun initialize() {
        require("@rjaros/bootstrap-select/dist/css/bootstrap-select.min.css")
        require("@rjaros/bootstrap-select")
        require("kvision-assets/js/locales/bootstrap-select/bootstrap-select-i18n.min.js")
        require("ajax-bootstrap-select/dist/css/ajax-bootstrap-select.min.css")
        require("ajax-bootstrap-select")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.cs-CZ.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.de-DE.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.es-ES.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.fr-FR.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.it-IT.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ja-JP.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ko-KR.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.nl-NL.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.pl-PL.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.pt-BR.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ru-RU.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.sr-SP.min.js")
        require("kvision-assets/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.tr-TR.min.js")
        js("$.fn.selectpicker.Constructor.BootstrapVersion = '5';")
        js("$.fn.selectpicker.Constructor.DEFAULTS.styleBase = 'form-control';")
        js("$.fn.selectpicker.Constructor.DEFAULTS.style = '';")
    }
}
