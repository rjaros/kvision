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

import com.github.snabbdom.Snabbdom
import com.github.snabbdom.VNode
import com.github.snabbdom.attributesModule
import com.github.snabbdom.classModule
import com.github.snabbdom.datasetModule
import com.github.snabbdom.eventListenersModule
import com.github.snabbdom.propsModule
import com.github.snabbdom.styleModule
import org.w3c.dom.asList
import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.i18n.I18n
import pl.treksoft.kvision.utils.isIE11
import pl.treksoft.kvision.utils.obj
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.clear

/**
 * @suppress
 * External function for loading CommonJS modules.
 */
external fun require(name: String): dynamic

/**
 * Internal singleton object which initializes and configures KVision framework.
 */
@Suppress("EmptyCatchBlock", "TooGenericExceptionCaught")
internal object KVManager {
    internal const val AJAX_REQUEST_DELAY = 300
    internal const val KVNULL = "#kvnull"

    private val links = document.getElementsByTagName("link")
    private val bootstrapWebpack = try {
        val bootswatch = links.asList().find { it.getAttribute("href")?.contains("bootstrap.min.css") ?: false }
        if (bootswatch != null) {
            if (bootswatch.getAttribute("href")?.contains("/paper/") == true) {
                require("./css/paper.css")
            }
            require("bootstrap-webpack!./js/bootstrap.config.js")
        } else {
            require("bootstrap-webpack")
        }
    } catch (e: Throwable) {
    }
    private val fontAwesomeWebpack = try {
        require("font-awesome-webpack")
    } catch (e: Throwable) {
    }
    private val awesomeBootstrapCheckbox = try {
        require("awesome-bootstrap-checkbox")
    } catch (e: Throwable) {
    }
    private val bootstrapSelectCss = try {
        require("bootstrap-select/dist/css/bootstrap-select.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapSelect = try {
        require("bootstrap-select/dist/js/bootstrap-select.min.js")
        require("./js/bootstrap-select-i18n.min.js")
    } catch (e: Throwable) {
    }
    private val bootstrapSelectAjaxCss = try {
        require("ajax-bootstrap-select/dist/css/ajax-bootstrap-select.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapSelectAjax = try {
        require("ajax-bootstrap-select/dist/js/ajax-bootstrap-select.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.de-DE.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.es-ES.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.fr-FR.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.it-IT.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ja-JP.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ko-KR.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.nl-NL.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.pl-PL.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.pt-BR.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.ru-RU.min.js")
        require("../../js/js/locales/ajax-bootstrap-select/ajax-bootstrap-select.tr-TR.min.js")
    } catch (e: Throwable) {
    }
    private val trixCss = try {
        require("trix/dist/trix.css")
    } catch (e: Throwable) {
    }
    private val trix = try {
        val trix = require("trix")
        window.asDynamic().Trix = trix
        trix.config.languages = obj {}
        trix.config.languages["en"] = obj {}
        for (key in js("Object").keys(trix.config.lang)) {
            trix.config.languages["en"][key] = trix.config.lang[key]
        }
        val orig = trix.config.toolbar.getDefaultHTML
        trix.config.toolbar.getDefaultHTML = {
            val config = if (trix.config.languages[I18n.language] != undefined) {
                trix.config.languages[I18n.language]
            } else {
                trix.config.languages["en"]
            }
            for (key in js("Object").keys(trix.config.lang)) {
                trix.config.lang[key] = config[key]
            }
            orig()
        }
        require("../../js/js/locales/trix/trix.pl.js")
    } catch (e: Throwable) {
    }
    private val bootstrapDateTimePickerCss = try {
        require("bootstrap-datetime-picker/css/bootstrap-datetimepicker.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapDateTimePicker = try {
        require("bootstrap-datetime-picker/js/bootstrap-datetimepicker.min.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ar.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.az.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.bg.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.bn.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ca.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.cs.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.da.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.de.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ee.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.el.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.es.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.fi.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.fr.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.he.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.hr.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.hu.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.hy.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.id.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.is.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.it.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ja.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ko.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.lt.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.lv.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.nl.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.no.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.pl.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.pt.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ro.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.rs.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ru.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.sk.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.sl.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.sv.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.th.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.tr.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ua.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.uk.js")
        require("../../js/js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.zh.js")
    } catch (e: Throwable) {
    }
    private val bootstrapTouchspinCss = try {
        require("bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapTouchspin = try {
        require("bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js")
    } catch (e: Throwable) {
    }
    private val elementResizeEvent = try {
        require("element-resize-event")
    } catch (e: Throwable) {
    }
    private val bootstrapFileinputCss = try {
        require("bootstrap-fileinput/css/fileinput.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapFileinputCssFa = try {
        require("bootstrap-fileinput/themes/explorer-fa/theme.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapFileinput = try {
        require("bootstrap-fileinput")
        require("../../js/js/locales/bootstrap-fileinput/ar.js")
        require("../../js/js/locales/bootstrap-fileinput/az.js")
        require("../../js/js/locales/bootstrap-fileinput/bg.js")
        require("../../js/js/locales/bootstrap-fileinput/ca.js")
        require("../../js/js/locales/bootstrap-fileinput/cr.js")
        require("../../js/js/locales/bootstrap-fileinput/cs.js")
        require("../../js/js/locales/bootstrap-fileinput/da.js")
        require("../../js/js/locales/bootstrap-fileinput/de.js")
        require("../../js/js/locales/bootstrap-fileinput/el.js")
        require("../../js/js/locales/bootstrap-fileinput/es.js")
        require("../../js/js/locales/bootstrap-fileinput/et.js")
        require("../../js/js/locales/bootstrap-fileinput/fa.js")
        require("../../js/js/locales/bootstrap-fileinput/fi.js")
        require("../../js/js/locales/bootstrap-fileinput/fr.js")
        require("../../js/js/locales/bootstrap-fileinput/gl.js")
        require("../../js/js/locales/bootstrap-fileinput/id.js")
        require("../../js/js/locales/bootstrap-fileinput/it.js")
        require("../../js/js/locales/bootstrap-fileinput/ja.js")
        require("../../js/js/locales/bootstrap-fileinput/ka.js")
        require("../../js/js/locales/bootstrap-fileinput/ko.js")
        require("../../js/js/locales/bootstrap-fileinput/kz.js")
        require("../../js/js/locales/bootstrap-fileinput/lt.js")
        require("../../js/js/locales/bootstrap-fileinput/nl.js")
        require("../../js/js/locales/bootstrap-fileinput/no.js")
        require("../../js/js/locales/bootstrap-fileinput/pl.js")
        require("../../js/js/locales/bootstrap-fileinput/pt.js")
        require("../../js/js/locales/bootstrap-fileinput/ro.js")
        require("../../js/js/locales/bootstrap-fileinput/ru.js")
        require("../../js/js/locales/bootstrap-fileinput/sk.js")
        require("../../js/js/locales/bootstrap-fileinput/sl.js")
        require("../../js/js/locales/bootstrap-fileinput/sv.js")
        require("../../js/js/locales/bootstrap-fileinput/th.js")
        require("../../js/js/locales/bootstrap-fileinput/tr.js")
        require("../../js/js/locales/bootstrap-fileinput/uk.js")
        require("../../js/js/locales/bootstrap-fileinput/vi.js")
        require("../../js/js/locales/bootstrap-fileinput/zh.js")
    } catch (e: Throwable) {
    }
    private val bootstrapFileinputFa = try {
        require("bootstrap-fileinput/themes/explorer-fa/theme.min.js")
    } catch (e: Throwable) {
    }
    private val resizable = try {
        require("jquery-resizable-dom")
    } catch (e: Throwable) {
    }
    private val handlebars = try {
        require("handlebars/dist/handlebars.runtime.min.js")
    } catch (e: Throwable) {
    }
    private val jed = require("jed")
    internal val fecha = require("fecha")
    private val sdPatch = Snabbdom.init(
        arrayOf(
            classModule, attributesModule, propsModule, styleModule,
            eventListenersModule, datasetModule
        )
    )
    private val sdVirtualize = require("snabbdom-virtualize/strings").default
    private val styleCss = require("./css/style.css")

    internal fun patch(id: String, vnode: VNode): VNode {
        val container = document.getElementById(id)
        container?.clear()
        return sdPatch(container, vnode)
    }

    internal fun patch(oldVNode: VNode, newVNode: VNode): VNode {
        return sdPatch(oldVNode, newVNode)
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun virtualize(html: String): VNode {
        return sdVirtualize(html)
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun setResizeEvent(component: Component, callback: () -> Unit) {
        if (!isIE11()) {
            component.getElement()?.let {
                elementResizeEvent(it, callback)
            }
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun clearResizeEvent(component: Component) {
        if (!isIE11()) {
            if (component.getElement()?.asDynamic()?.__resizeTrigger__?.contentDocument != null) {
                component.getElement()?.let {
                    elementResizeEvent.unbind(it)
                }
            }
        }
    }
}
