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

internal val KVManagerDatetimeInit = KVManagerDatetime.init()

/**
 * Internal singleton object which initializes and configures KVision datetime module.
 */
@Suppress("EmptyCatchBlock", "TooGenericExceptionCaught")
internal object KVManagerDatetime {
    fun init() {}

    private val bootstrapDateTimePickerCss = try {
        require("bootstrap-datetime-picker/css/bootstrap-datetimepicker.min.css")
    } catch (e: Throwable) {
    }
    private val bootstrapDateTimePicker = try {
        require("bootstrap-datetime-picker/js/bootstrap-datetimepicker.min.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ar.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.az.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.bg.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.bn.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ca.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.cs.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.da.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.de.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ee.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.el.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.es.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.fi.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.fr.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.he.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.hr.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.hu.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.hy.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.id.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.is.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.it.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ja.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ko.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.lt.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.lv.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.nl.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.no.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.pl.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.pt.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ro.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.rs.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ru.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.sk.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.sl.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.sv.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.th.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.tr.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.ua.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.uk.js")
        require("./js/locales/bootstrap-datetime-picker/bootstrap-datetimepicker.zh.js")
    } catch (e: Throwable) {
    }

}
