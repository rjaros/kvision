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

@file:JsModule("https")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.https

import pl.treksoft.kvision.electron.http.ClientRequest
import pl.treksoft.kvision.electron.http.IncomingMessage
import pl.treksoft.kvision.electron.http.RequestListener
import pl.treksoft.kvision.electron.http.RequestOptions
import pl.treksoft.kvision.electron.tls.ConnectionOptions
import pl.treksoft.kvision.electron.tls.SecureContextOptions
import pl.treksoft.kvision.electron.url.URL

external interface `T$68` {
    var rejectUnauthorized: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var servername: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AgentOptions : pl.treksoft.kvision.electron.http.AgentOptions, ConnectionOptions {
    override var rejectUnauthorized: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maxCachedSessions: Number?
        get() = definedExternally
        set(value) = definedExternally
    override var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Agent(options: AgentOptions = definedExternally) : pl.treksoft.kvision.electron.http.Agent {
    open var options: AgentOptions
}

external open class Server : pl.treksoft.kvision.electron.tls.Server {
    constructor(requestListener: RequestListener = definedExternally)
    constructor(options: SecureContextOptions, requestListener: RequestListener = definedExternally)

    open fun setTimeout(callback: () -> Unit): Server /* this */
    open fun setTimeout(msecs: Number = definedExternally, callback: () -> Unit = definedExternally): Server /* this */
    open var maxHeadersCount: Number?
    open var timeout: Number
    open var headersTimeout: Number
    open var keepAliveTimeout: Number
}

external fun createServer(requestListener: RequestListener = definedExternally): Server

external fun createServer(
    options: SecureContextOptions /* tls.SecureContextOptions & tls.TlsOptions & http.ServerOptions */,
    requestListener: RequestListener = definedExternally
): Server

external fun request(
    options: RequestOptions /* http.RequestOptions & tls.SecureContextOptions & `T$68` */,
    callback: (res: IncomingMessage) -> Unit = definedExternally
): ClientRequest

external fun request(options: String, callback: (res: IncomingMessage) -> Unit = definedExternally): ClientRequest

external fun request(options: URL, callback: (res: IncomingMessage) -> Unit = definedExternally): ClientRequest

external fun request(
    url: String,
    options: RequestOptions /* http.RequestOptions & tls.SecureContextOptions & `T$68` */,
    callback: (res: IncomingMessage) -> Unit = definedExternally
): ClientRequest

external fun request(
    url: URL,
    options: RequestOptions /* http.RequestOptions & tls.SecureContextOptions & `T$68` */,
    callback: (res: IncomingMessage) -> Unit = definedExternally
): ClientRequest

external fun get(
    options: RequestOptions /* http.RequestOptions & tls.SecureContextOptions & `T$68` */,
    callback: (res: IncomingMessage) -> Unit = definedExternally
): ClientRequest

external fun get(options: String, callback: (res: IncomingMessage) -> Unit = definedExternally): ClientRequest

external fun get(options: URL, callback: (res: IncomingMessage) -> Unit = definedExternally): ClientRequest

external fun get(
    url: String,
    options: RequestOptions /* http.RequestOptions & tls.SecureContextOptions & `T$68` */,
    callback: (res: IncomingMessage) -> Unit = definedExternally
): ClientRequest

external fun get(
    url: URL,
    options: RequestOptions /* http.RequestOptions & tls.SecureContextOptions & `T$68` */,
    callback: (res: IncomingMessage) -> Unit = definedExternally
): ClientRequest

external var globalAgent: Agent