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

@file:JsModule("tls")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.tls

import node.buffer.Buffer
import pl.treksoft.kvision.electron.net.LookupFunction
import pl.treksoft.kvision.electron.net.Socket

external var CLIENT_RENEG_LIMIT: Number

external var CLIENT_RENEG_WINDOW: Number

external interface Certificate {
    var C: String
    var ST: String
    var L: String
    var O: String
    var OU: String
    var CN: String
}

external interface `T$66` {
    @nativeGetter
    operator fun get(index: String): Array<String>?

    @nativeSetter
    operator fun set(index: String, value: Array<String>?)
}

external interface PeerCertificate {
    var subject: Certificate
    var issuer: Certificate
    var subjectaltname: String
    var infoAccess: `T$66`
    var modulus: String
    var exponent: String
    var valid_from: String
    var valid_to: String
    var fingerprint: String
    var fingerprint256: String
    var ext_key_usage: Array<String>
    var serialNumber: String
    var raw: Buffer
}

external interface DetailedPeerCertificate : PeerCertificate {
    var issuerCertificate: DetailedPeerCertificate
}

external interface CipherNameAndProtocol {
    var name: String
    var version: String
}

external interface EphemeralKeyInfo {
    var type: String
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var size: Number
}

external interface KeyObject {
    var pem: dynamic /* String | Buffer */
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PxfObject {
    var buf: dynamic /* String | Buffer */
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TLSSocketOptions : SecureContextOptions, CommonConnectionOptions {
    var isServer: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var server: pl.treksoft.kvision.electron.net.Server?
        get() = definedExternally
        set(value) = definedExternally
    var session: Buffer?
        get() = definedExternally
        set(value) = definedExternally
    var requestOCSP: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$67` {
    var rejectUnauthorized: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var requestCert: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external open class TLSSocket(socket: Socket, options: TLSSocketOptions = definedExternally) : Socket {
    open var authorized: Boolean
    open var authorizationError: Error
    open var encrypted: Boolean
    open var alpnProtocol: String
    open fun getCertificate(): dynamic /* PeerCertificate? | Any? */
    open fun getCipher(): CipherNameAndProtocol
    open fun getEphemeralKeyInfo(): dynamic /* EphemeralKeyInfo? | Any? */
    open fun getFinished(): Buffer?
    open fun getPeerCertificate(detailed: Boolean): dynamic /* DetailedPeerCertificate | PeerCertificate | PeerCertificate | DetailedPeerCertificate */
    open fun getPeerFinished(): Buffer?
    open fun getProtocol(): String?
    open fun getSession(): Buffer?
    open fun getSharedSigalgs(): Array<String>
    open fun getTLSTicket(): Buffer?
    open fun isSessionReused(): Boolean
    open fun renegotiate(options: `T$67`, callback: (err: Error?) -> Unit): Boolean?
    open fun setMaxSendFragment(size: Number): Boolean
    open fun disableRenegotiation()
    open fun enableTrace()
    override fun addListener(event: String, listener: (args: Any) -> Unit): TLSSocket /* this */
    override fun addListener(
        event: String /* "OCSPResponse" */,
        listener: (response: Buffer) -> Unit
    ): TLSSocket /* this */

    override fun addListener(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */
    override fun addListener(event: String /* "session" */, listener: (session: Buffer) -> Unit): TLSSocket /* this */
    override fun addListener(event: String /* "keylog" */, listener: (line: Buffer) -> Unit): TLSSocket /* this */
    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    override fun emit(event: String /* "OCSPResponse" | "session" | "keylog" */, response: Buffer): Boolean
    override fun emit(event: String /* "secureConnect" */): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): TLSSocket /* this */
    override fun on(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */
    override fun on(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */
    override fun on(event: String /* "session" */, listener: (session: Buffer) -> Unit): TLSSocket /* this */
    override fun on(event: String /* "keylog" */, listener: (line: Buffer) -> Unit): TLSSocket /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): TLSSocket /* this */
    override fun once(event: String /* "OCSPResponse" */, listener: (response: Buffer) -> Unit): TLSSocket /* this */
    override fun once(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */
    override fun once(event: String /* "session" */, listener: (session: Buffer) -> Unit): TLSSocket /* this */
    override fun once(event: String /* "keylog" */, listener: (line: Buffer) -> Unit): TLSSocket /* this */
    override fun prependListener(event: String, listener: (args: Any) -> Unit): TLSSocket /* this */
    override fun prependListener(
        event: String /* "OCSPResponse" */,
        listener: (response: Buffer) -> Unit
    ): TLSSocket /* this */

    override fun prependListener(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */
    override fun prependListener(
        event: String /* "session" */,
        listener: (session: Buffer) -> Unit
    ): TLSSocket /* this */

    override fun prependListener(event: String /* "keylog" */, listener: (line: Buffer) -> Unit): TLSSocket /* this */
    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): TLSSocket /* this */
    override fun prependOnceListener(
        event: String /* "OCSPResponse" */,
        listener: (response: Buffer) -> Unit
    ): TLSSocket /* this */

    override fun prependOnceListener(event: String /* "secureConnect" */, listener: () -> Unit): TLSSocket /* this */
    override fun prependOnceListener(
        event: String /* "session" */,
        listener: (session: Buffer) -> Unit
    ): TLSSocket /* this */

    override fun prependOnceListener(
        event: String /* "keylog" */,
        listener: (line: Buffer) -> Unit
    ): TLSSocket /* this */
}

external interface CommonConnectionOptions {
    var secureContext: SecureContext?
        get() = definedExternally
        set(value) = definedExternally
    var enableTrace: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var requestCert: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ALPNProtocols: dynamic /* Array<String>? | Array<Uint8Array>? | Uint8Array? */
        get() = definedExternally
        set(value) = definedExternally
    var SNICallback: ((servername: String, cb: (err: Error?, ctx: SecureContext) -> Unit) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var rejectUnauthorized: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TlsOptions : SecureContextOptions, CommonConnectionOptions {
    var handshakeTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var sessionTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var ticketKeys: Buffer?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConnectionOptions : SecureContextOptions, CommonConnectionOptions {
    var host: String?
        get() = definedExternally
        set(value) = definedExternally
    var port: Number?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var socket: Socket?
        get() = definedExternally
        set(value) = definedExternally
    var checkServerIdentity: Any?
        get() = definedExternally
        set(value) = definedExternally
    var servername: String?
        get() = definedExternally
        set(value) = definedExternally
    var session: Buffer?
        get() = definedExternally
        set(value) = definedExternally
    var minDHSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var lookup: LookupFunction?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Server : pl.treksoft.kvision.electron.net.Server {
    open fun addContext(hostName: String, credentials: SecureContextOptions)
    open fun getTicketKeys(): Buffer
    open fun setSecureContext(details: SecureContextOptions)
    open fun setTicketKeys(keys: Buffer)
    override fun addListener(event: String, listener: (args: Any) -> Unit): Server /* this */
    open fun addListener(
        event: String /* "tlsClientError" */,
        listener: (err: Error, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun addListener(
        event: String /* "newSession" */,
        listener: (sessionId: Buffer, sessionData: Buffer, callback: (err: Error, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun addListener(
        event: String /* "OCSPRequest" */,
        listener: (certificate: Buffer, issuer: Buffer, callback: (err: Error?, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun addListener(
        event: String /* "resumeSession" */,
        listener: (sessionId: Buffer, callback: (err: Error, sessionData: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun addListener(
        event: String /* "secureConnection" */,
        listener: (tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun addListener(
        event: String /* "keylog" */,
        listener: (line: Buffer, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun emit(event: String /* "tlsClientError" */, err: Error, tlsSocket: TLSSocket): Boolean
    open fun emit(
        event: String /* "newSession" */,
        sessionId: Buffer,
        sessionData: Buffer,
        callback: (err: Error, resp: Buffer) -> Unit
    ): Boolean

    open fun emit(
        event: String /* "OCSPRequest" */,
        certificate: Buffer,
        issuer: Buffer,
        callback: (err: Error?, resp: Buffer) -> Unit
    ): Boolean

    open fun emit(
        event: String /* "resumeSession" */,
        sessionId: Buffer,
        callback: (err: Error, sessionData: Buffer) -> Unit
    ): Boolean

    open fun emit(event: String /* "secureConnection" */, tlsSocket: TLSSocket): Boolean
    open fun emit(event: String /* "keylog" */, line: Buffer, tlsSocket: TLSSocket): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): Server /* this */
    open fun on(
        event: String /* "tlsClientError" */,
        listener: (err: Error, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun on(
        event: String /* "newSession" */,
        listener: (sessionId: Buffer, sessionData: Buffer, callback: (err: Error, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun on(
        event: String /* "OCSPRequest" */,
        listener: (certificate: Buffer, issuer: Buffer, callback: (err: Error?, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun on(
        event: String /* "resumeSession" */,
        listener: (sessionId: Buffer, callback: (err: Error, sessionData: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun on(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
    open fun on(event: String /* "keylog" */, listener: (line: Buffer, tlsSocket: TLSSocket) -> Unit): Server /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Server /* this */
    open fun once(
        event: String /* "tlsClientError" */,
        listener: (err: Error, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun once(
        event: String /* "newSession" */,
        listener: (sessionId: Buffer, sessionData: Buffer, callback: (err: Error, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun once(
        event: String /* "OCSPRequest" */,
        listener: (certificate: Buffer, issuer: Buffer, callback: (err: Error?, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun once(
        event: String /* "resumeSession" */,
        listener: (sessionId: Buffer, callback: (err: Error, sessionData: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun once(event: String /* "secureConnection" */, listener: (tlsSocket: TLSSocket) -> Unit): Server /* this */
    open fun once(
        event: String /* "keylog" */,
        listener: (line: Buffer, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): Server /* this */
    open fun prependListener(
        event: String /* "tlsClientError" */,
        listener: (err: Error, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun prependListener(
        event: String /* "newSession" */,
        listener: (sessionId: Buffer, sessionData: Buffer, callback: (err: Error, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun prependListener(
        event: String /* "OCSPRequest" */,
        listener: (certificate: Buffer, issuer: Buffer, callback: (err: Error?, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun prependListener(
        event: String /* "resumeSession" */,
        listener: (sessionId: Buffer, callback: (err: Error, sessionData: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun prependListener(
        event: String /* "secureConnection" */,
        listener: (tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun prependListener(
        event: String /* "keylog" */,
        listener: (line: Buffer, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): Server /* this */
    open fun prependOnceListener(
        event: String /* "tlsClientError" */,
        listener: (err: Error, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun prependOnceListener(
        event: String /* "newSession" */,
        listener: (sessionId: Buffer, sessionData: Buffer, callback: (err: Error, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun prependOnceListener(
        event: String /* "OCSPRequest" */,
        listener: (certificate: Buffer, issuer: Buffer, callback: (err: Error?, resp: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun prependOnceListener(
        event: String /* "resumeSession" */,
        listener: (sessionId: Buffer, callback: (err: Error, sessionData: Buffer) -> Unit) -> Unit
    ): Server /* this */

    open fun prependOnceListener(
        event: String /* "secureConnection" */,
        listener: (tlsSocket: TLSSocket) -> Unit
    ): Server /* this */

    open fun prependOnceListener(
        event: String /* "keylog" */,
        listener: (line: Buffer, tlsSocket: TLSSocket) -> Unit
    ): Server /* this */
}

external interface SecurePair {
    var encrypted: TLSSocket
    var cleartext: TLSSocket
}

external interface SecureContextOptions {
    var ca: dynamic /* String? | Buffer? | Array<dynamic /* String | Buffer */>? */
        get() = definedExternally
        set(value) = definedExternally
    var cert: dynamic /* String? | Buffer? | Array<dynamic /* String | Buffer */>? */
        get() = definedExternally
        set(value) = definedExternally
    var sigalgs: String?
        get() = definedExternally
        set(value) = definedExternally
    var ciphers: String?
        get() = definedExternally
        set(value) = definedExternally
    var clientCertEngine: String?
        get() = definedExternally
        set(value) = definedExternally
    var crl: dynamic /* String? | Buffer? | Array<dynamic /* String | Buffer */>? */
        get() = definedExternally
        set(value) = definedExternally
    var dhparam: dynamic /* String? | Buffer? */
        get() = definedExternally
        set(value) = definedExternally
    var ecdhCurve: String?
        get() = definedExternally
        set(value) = definedExternally
    var honorCipherOrder: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var key: dynamic /* String? | Buffer? | Array<dynamic /* Buffer | KeyObject */>? */
        get() = definedExternally
        set(value) = definedExternally
    var privateKeyEngine: String?
        get() = definedExternally
        set(value) = definedExternally
    var privateKeyIdentifier: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxVersion: String? /* 'TLSv1.3' | 'TLSv1.2' | 'TLSv1.1' | 'TLSv1' */
        get() = definedExternally
        set(value) = definedExternally
    var minVersion: String? /* 'TLSv1.3' | 'TLSv1.2' | 'TLSv1.1' | 'TLSv1' */
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: String?
        get() = definedExternally
        set(value) = definedExternally
    var pfx: dynamic /* String? | Buffer? | Array<dynamic /* String | Buffer | PxfObject */>? */
        get() = definedExternally
        set(value) = definedExternally
    var secureOptions: Number?
        get() = definedExternally
        set(value) = definedExternally
    var secureProtocol: String?
        get() = definedExternally
        set(value) = definedExternally
    var sessionIdContext: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SecureContext {
    var context: Any
}

external fun checkServerIdentity(host: String, cert: PeerCertificate): Error?

external fun createServer(secureConnectionListener: (socket: TLSSocket) -> Unit = definedExternally): Server

external fun createServer(
    options: TlsOptions,
    secureConnectionListener: (socket: TLSSocket) -> Unit = definedExternally
): Server

external fun connect(options: ConnectionOptions, secureConnectListener: () -> Unit = definedExternally): TLSSocket

external fun connect(
    port: Number,
    host: String = definedExternally,
    options: ConnectionOptions = definedExternally,
    secureConnectListener: () -> Unit = definedExternally
): TLSSocket

external fun connect(
    port: Number,
    options: ConnectionOptions = definedExternally,
    secureConnectListener: () -> Unit = definedExternally
): TLSSocket

external fun createSecurePair(
    credentials: SecureContext = definedExternally,
    isServer: Boolean = definedExternally,
    requestCert: Boolean = definedExternally,
    rejectUnauthorized: Boolean = definedExternally
): SecurePair

external fun createSecureContext(details: SecureContextOptions): SecureContext

external fun getCiphers(): Array<String>

external var DEFAULT_ECDH_CURVE: String

external var DEFAULT_MAX_VERSION: String /* 'TLSv1.3' | 'TLSv1.2' | 'TLSv1.1' | 'TLSv1' */

external var DEFAULT_MIN_VERSION: String /* 'TLSv1.3' | 'TLSv1.2' | 'TLSv1.1' | 'TLSv1' */

external var rootCertificates: Array<String>