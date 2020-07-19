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

@file:JsQualifier("pl.treksoft.kvision.electron.dns.promises")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.dns.promises

import pl.treksoft.kvision.electron.dns.*
import kotlin.js.Promise

external fun getServers(): Array<String>

external fun lookup(hostname: String, family: Number): Promise<LookupAddress>

external fun lookup(hostname: String, options: LookupOneOptions): Promise<LookupAddress>

external fun lookup(hostname: String, options: LookupAllOptions): Promise<Array<LookupAddress>>

external fun lookup(
    hostname: String,
    options: LookupOptions
): Promise<dynamic /* LookupAddress | Array<LookupAddress> */>

external fun lookup(hostname: String): Promise<LookupAddress>

external fun lookupService(address: String, port: Number): Promise<dynamic>

external fun resolve(hostname: String): Promise<Array<String>>

external fun resolve(hostname: String, rrtype: String /* "A" */): Promise<Array<String>>

external fun resolve(hostname: String, rrtype: String /* "AAAA" */): Promise<Array<String>>

external fun resolve(
    hostname: String,
    rrtype: String /* "ANY" */
): Promise<Array<dynamic /* AnyARecord | AnyAaaaRecord | AnyCnameRecord | AnyMxRecord | AnyNaptrRecord | AnyNsRecord | AnyPtrRecord | AnySoaRecord | AnySrvRecord | AnyTxtRecord */>>

external fun resolve(hostname: String, rrtype: String /* "CNAME" */): Promise<Array<String>>

external fun resolve(hostname: String, rrtype: String /* "MX" */): Promise<Array<MxRecord>>

external fun resolve(hostname: String, rrtype: String /* "NAPTR" */): Promise<Array<NaptrRecord>>

external fun resolve(hostname: String, rrtype: String /* "NS" */): Promise<Array<String>>

external fun resolve(hostname: String, rrtype: String /* "PTR" */): Promise<Array<String>>

external fun resolve(hostname: String, rrtype: String /* "SOA" */): Promise<SoaRecord>

external fun resolve(hostname: String, rrtype: String /* "SRV" */): Promise<Array<SrvRecord>>

external fun resolve(hostname: String, rrtype: String /* "TXT" */): Promise<Array<Array<String>>>

external fun resolve(
    hostname: String,
    rrtype: String
): Promise<dynamic /* Array<String> | Array<MxRecord> | Array<NaptrRecord> | SoaRecord | Array<SrvRecord> | Array<Array<String>> | Array<dynamic /* AnyARecord | AnyAaaaRecord | AnyCnameRecord | AnyMxRecord | AnyNaptrRecord | AnyNsRecord | AnyPtrRecord | AnySoaRecord | AnySrvRecord | AnyTxtRecord */> */>

external fun resolve4(hostname: String): Promise<Array<String>>

external fun resolve4(hostname: String, options: ResolveWithTtlOptions): Promise<Array<RecordWithTtl>>

external fun resolve4(
    hostname: String,
    options: ResolveOptions
): Promise<dynamic /* Array<String> | Array<RecordWithTtl> */>

external fun resolve6(hostname: String): Promise<Array<String>>

external fun resolve6(hostname: String, options: ResolveWithTtlOptions): Promise<Array<RecordWithTtl>>

external fun resolve6(
    hostname: String,
    options: ResolveOptions
): Promise<dynamic /* Array<String> | Array<RecordWithTtl> */>

external fun resolveAny(hostname: String): Promise<Array<dynamic /* AnyARecord | AnyAaaaRecord | AnyCnameRecord | AnyMxRecord | AnyNaptrRecord | AnyNsRecord | AnyPtrRecord | AnySoaRecord | AnySrvRecord | AnyTxtRecord */>>

external fun resolveCname(hostname: String): Promise<Array<String>>

external fun resolveMx(hostname: String): Promise<Array<MxRecord>>

external fun resolveNaptr(hostname: String): Promise<Array<NaptrRecord>>

external fun resolveNs(hostname: String): Promise<Array<String>>

external fun resolvePtr(hostname: String): Promise<Array<String>>

external fun resolveSoa(hostname: String): Promise<SoaRecord>

external fun resolveSrv(hostname: String): Promise<Array<SrvRecord>>

external fun resolveTxt(hostname: String): Promise<Array<Array<String>>>

external fun reverse(ip: String): Promise<Array<String>>

external fun setServers(servers: Array<String>)

external open class Resolver {
    open var getServers: Any
    open var resolve: Any
    open var resolve4: Any
    open var resolve6: Any
    open var resolveAny: Any
    open var resolveCname: Any
    open var resolveMx: Any
    open var resolveNaptr: Any
    open var resolveNs: Any
    open var resolvePtr: Any
    open var resolveSoa: Any
    open var resolveSrv: Any
    open var resolveTxt: Any
    open var reverse: Any
    open var setServers: Any
}