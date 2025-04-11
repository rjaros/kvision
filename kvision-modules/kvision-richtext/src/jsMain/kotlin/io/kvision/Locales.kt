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

import io.kvision.utils.obj

internal external class TrixLocale {
    var GB: String
    var KB: String
    var MB: String
    var PB: String
    var TB: String
    var bold: String
    var bullets: String
    var byte: String
    var bytes: String
    var captionPlaceholder: String
    var code: String
    var heading1: String
    var indent: String
    var italic: String
    var link: String
    var numbers: String
    var outdent: String
    var quote: String
    var redo: String
    var remove: String
    var strike: String
    var undo: String
    var unlink: String
    var urlPlaceholder: String
    var url: String
    var attachFiles: String
}

internal fun getTrixLocaleEn(): TrixLocale {
    val trixLocalesEn = obj {}
    for (key in js("Object").keys(trixModule.config.lang)) {
        trixLocalesEn[key] = trixModule.config.lang[key]
    }
    return trixLocalesEn
}

internal fun getTrixLocalePl(): TrixLocale {
    return obj<TrixLocale> {
        GB = "GB"
        KB = "KB"
        MB = "MB"
        PB = "PB"
        TB = "TB"
        bold = "Pogrubienie"
        bullets = "Wypunktowanie"
        byte = "Bajt"
        bytes = "Bajty"
        captionPlaceholder = "Dodaj tytuł…"
        code = "Kod źródłowy"
        heading1 = "Nagłówek"
        indent = "Zwiększ poziom"
        italic = "Pochylenie"
        link = "Link"
        numbers = "Numerowanie"
        outdent = "Zmniejsz poziom"
        quote = "Cytat"
        redo = "Ponów"
        remove = "Usuń"
        strike = "Przekreślenie"
        undo = "Cofnij"
        unlink = "Usuń link"
        urlPlaceholder = "Wprowadź adres URL…"
        url = "URL"
        attachFiles = "Załącz pliki"
    }
}
