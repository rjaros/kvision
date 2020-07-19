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

@file:JsModule("url")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.url

import pl.treksoft.kvision.electron.querystring.ParsedUrlQuery

external interface UrlObject {
    var auth: String?
        get() = definedExternally
        set(value) = definedExternally
    var hash: String?
        get() = definedExternally
        set(value) = definedExternally
    var host: String?
        get() = definedExternally
        set(value) = definedExternally
    var hostname: String?
        get() = definedExternally
        set(value) = definedExternally
    var href: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var pathname: String?
        get() = definedExternally
        set(value) = definedExternally
    var protocol: String?
        get() = definedExternally
        set(value) = definedExternally
    var search: String?
        get() = definedExternally
        set(value) = definedExternally
    var slashes: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var port: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var query: dynamic /* String? | ParsedUrlQueryInput? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface Url {
    var auth: String?
    var hash: String?
    var host: String?
    var hostname: String?
    var href: String
    var path: String?
    var pathname: String?
    var protocol: String?
    var search: String?
    var slashes: Boolean?
    var port: String?
    var query: dynamic /* String? | ParsedUrlQuery? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface UrlWithParsedQuery : Url {
    override var query: ParsedUrlQuery
}

external interface UrlWithStringQuery : Url {
    override var query: String?
}

external fun parse(urlStr: String): UrlWithStringQuery

external fun parse(
    urlStr: String,
    parseQueryString: Boolean?,
    slashesDenoteHost: Boolean = definedExternally
): UrlWithStringQuery

external fun parse(
    urlStr: String,
    parseQueryString: Boolean,
    slashesDenoteHost: Boolean = definedExternally
): UrlWithParsedQuery

external fun parse(urlStr: String, parseQueryString: Boolean, slashesDenoteHost: Boolean = definedExternally): Url

external fun format(URL: URL, options: URLFormatOptions = definedExternally): String

external fun format(urlObject: UrlObject): String

external fun format(urlObject: String): String

external fun resolve(from: String, to: String): String

external fun domainToASCII(domain: String): String

external fun domainToUnicode(domain: String): String

external fun fileURLToPath(url: String): String

external fun fileURLToPath(url: URL): String

external fun pathToFileURL(url: String): URL

external interface URLFormatOptions {
    var auth: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var fragment: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var search: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var unicode: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external open class URL {
    constructor(input: String, base: String = definedExternally)
    constructor(input: String, base: URL = definedExternally)

    open var hash: String
    open var host: String
    open var hostname: String
    open var href: String
    open var origin: String
    open var password: String
    open var pathname: String
    open var port: String
    open var protocol: String
    open var search: String
    open var searchParams: URLSearchParams
    open var username: String
    override fun toString(): String
    open fun toJSON(): String
}

external interface `T$61` {
    @nativeGetter
    operator fun get(key: String): dynamic /* String? | Array<String>? */

    @nativeSetter
    operator fun set(key: String, value: String?)

    @nativeSetter
    operator fun set(key: String, value: Array<String>?)
}

external open class URLSearchParams {
    constructor(init: URLSearchParams = definedExternally)
    constructor(init: String = definedExternally)
    constructor(init: `T$61` = definedExternally)
    constructor(init: Iterable<dynamic /* JsTuple<String, String> */> = definedExternally)
    constructor(init: Array<dynamic /* JsTuple<String, String> */> = definedExternally)

    open fun append(name: String, value: String)
    open fun delete(name: String)
    open fun entries(): Iterator<dynamic /* JsTuple<String, String> */>
    open fun forEach(callback: (value: String, name: String, searchParams: URLSearchParams /* this */) -> Unit)
    open fun get(name: String): String?
    open fun getAll(name: String): Array<String>
    open fun has(name: String): Boolean
    open fun keys(): Iterator<String>
    open fun set(name: String, value: String)
    open fun sort()
    override fun toString(): String
    open fun values(): Iterator<String>
}