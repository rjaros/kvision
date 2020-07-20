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

@file:JsModule("os")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.os

import node.buffer.Buffer
import pl.treksoft.kvision.electron.child_process.`T$21`

external interface `T$69` {
    var user: Number
    var nice: Number
    var sys: Number
    var idle: Number
    var irq: Number
}

external interface CpuInfo {
    var model: String
    var speed: Number
    var times: `T$69`
}

external interface NetworkInterfaceBase {
    var address: String
    var netmask: String
    var mac: String
    var internal: Boolean
    var cidr: String?
}

external interface NetworkInterfaceInfoIPv4 : NetworkInterfaceBase {
    var family: String /* "IPv4" */
}

external interface NetworkInterfaceInfoIPv6 : NetworkInterfaceBase {
    var family: String /* "IPv6" */
    var scopeid: Number
}

external interface UserInfo<T> {
    var username: T
    var uid: Number
    var gid: Number
    var shell: T
    var homedir: T
}

external fun hostname(): String

external fun loadavg(): Array<Number>

external fun uptime(): Number

external fun freemem(): Number

external fun totalmem(): Number

external fun cpus(): Array<CpuInfo>

external fun type(): String

external fun release(): String

external interface `T$70` {
    @nativeGetter
    operator fun get(index: String): Array<dynamic /* NetworkInterfaceInfoIPv4 | NetworkInterfaceInfoIPv6 */>?

    @nativeSetter
    operator fun set(index: String, value: Array<dynamic /* NetworkInterfaceInfoIPv4 | NetworkInterfaceInfoIPv6 */>)
}

external fun networkInterfaces(): `T$70`

external fun homedir(): String

external interface `T$71` {
    var encoding: String /* 'buffer' */
}

external fun userInfo(options: `T$71`): UserInfo<Buffer>

external fun userInfo(options: `T$21` = definedExternally): UserInfo<String>

external interface `T$72` {
    var SIGHUP: Number
    var SIGINT: Number
    var SIGQUIT: Number
    var SIGILL: Number
    var SIGTRAP: Number
    var SIGABRT: Number
    var SIGIOT: Number
    var SIGBUS: Number
    var SIGFPE: Number
    var SIGKILL: Number
    var SIGUSR1: Number
    var SIGSEGV: Number
    var SIGUSR2: Number
    var SIGPIPE: Number
    var SIGALRM: Number
    var SIGTERM: Number
    var SIGCHLD: Number
    var SIGSTKFLT: Number
    var SIGCONT: Number
    var SIGSTOP: Number
    var SIGTSTP: Number
    var SIGBREAK: Number
    var SIGTTIN: Number
    var SIGTTOU: Number
    var SIGURG: Number
    var SIGXCPU: Number
    var SIGXFSZ: Number
    var SIGVTALRM: Number
    var SIGPROF: Number
    var SIGWINCH: Number
    var SIGIO: Number
    var SIGPOLL: Number
    var SIGLOST: Number
    var SIGPWR: Number
    var SIGINFO: Number
    var SIGSYS: Number
    var SIGUNUSED: Number
}

external interface `T$73` {
    var E2BIG: Number
    var EACCES: Number
    var EADDRINUSE: Number
    var EADDRNOTAVAIL: Number
    var EAFNOSUPPORT: Number
    var EAGAIN: Number
    var EALREADY: Number
    var EBADF: Number
    var EBADMSG: Number
    var EBUSY: Number
    var ECANCELED: Number
    var ECHILD: Number
    var ECONNABORTED: Number
    var ECONNREFUSED: Number
    var ECONNRESET: Number
    var EDEADLK: Number
    var EDESTADDRREQ: Number
    var EDOM: Number
    var EDQUOT: Number
    var EEXIST: Number
    var EFAULT: Number
    var EFBIG: Number
    var EHOSTUNREACH: Number
    var EIDRM: Number
    var EILSEQ: Number
    var EINPROGRESS: Number
    var EINTR: Number
    var EINVAL: Number
    var EIO: Number
    var EISCONN: Number
    var EISDIR: Number
    var ELOOP: Number
    var EMFILE: Number
    var EMLINK: Number
    var EMSGSIZE: Number
    var EMULTIHOP: Number
    var ENAMETOOLONG: Number
    var ENETDOWN: Number
    var ENETRESET: Number
    var ENETUNREACH: Number
    var ENFILE: Number
    var ENOBUFS: Number
    var ENODATA: Number
    var ENODEV: Number
    var ENOENT: Number
    var ENOEXEC: Number
    var ENOLCK: Number
    var ENOLINK: Number
    var ENOMEM: Number
    var ENOMSG: Number
    var ENOPROTOOPT: Number
    var ENOSPC: Number
    var ENOSR: Number
    var ENOSTR: Number
    var ENOSYS: Number
    var ENOTCONN: Number
    var ENOTDIR: Number
    var ENOTEMPTY: Number
    var ENOTSOCK: Number
    var ENOTSUP: Number
    var ENOTTY: Number
    var ENXIO: Number
    var EOPNOTSUPP: Number
    var EOVERFLOW: Number
    var EPERM: Number
    var EPIPE: Number
    var EPROTO: Number
    var EPROTONOSUPPORT: Number
    var EPROTOTYPE: Number
    var ERANGE: Number
    var EROFS: Number
    var ESPIPE: Number
    var ESRCH: Number
    var ESTALE: Number
    var ETIME: Number
    var ETIMEDOUT: Number
    var ETXTBSY: Number
    var EWOULDBLOCK: Number
    var EXDEV: Number
    var WSAEINTR: Number
    var WSAEBADF: Number
    var WSAEACCES: Number
    var WSAEFAULT: Number
    var WSAEINVAL: Number
    var WSAEMFILE: Number
    var WSAEWOULDBLOCK: Number
    var WSAEINPROGRESS: Number
    var WSAEALREADY: Number
    var WSAENOTSOCK: Number
    var WSAEDESTADDRREQ: Number
    var WSAEMSGSIZE: Number
    var WSAEPROTOTYPE: Number
    var WSAENOPROTOOPT: Number
    var WSAEPROTONOSUPPORT: Number
    var WSAESOCKTNOSUPPORT: Number
    var WSAEOPNOTSUPP: Number
    var WSAEPFNOSUPPORT: Number
    var WSAEAFNOSUPPORT: Number
    var WSAEADDRINUSE: Number
    var WSAEADDRNOTAVAIL: Number
    var WSAENETDOWN: Number
    var WSAENETUNREACH: Number
    var WSAENETRESET: Number
    var WSAECONNABORTED: Number
    var WSAECONNRESET: Number
    var WSAENOBUFS: Number
    var WSAEISCONN: Number
    var WSAENOTCONN: Number
    var WSAESHUTDOWN: Number
    var WSAETOOMANYREFS: Number
    var WSAETIMEDOUT: Number
    var WSAECONNREFUSED: Number
    var WSAELOOP: Number
    var WSAENAMETOOLONG: Number
    var WSAEHOSTDOWN: Number
    var WSAEHOSTUNREACH: Number
    var WSAENOTEMPTY: Number
    var WSAEPROCLIM: Number
    var WSAEUSERS: Number
    var WSAEDQUOT: Number
    var WSAESTALE: Number
    var WSAEREMOTE: Number
    var WSASYSNOTREADY: Number
    var WSAVERNOTSUPPORTED: Number
    var WSANOTINITIALISED: Number
    var WSAEDISCON: Number
    var WSAENOMORE: Number
    var WSAECANCELLED: Number
    var WSAEINVALIDPROCTABLE: Number
    var WSAEINVALIDPROVIDER: Number
    var WSAEPROVIDERFAILEDINIT: Number
    var WSASYSCALLFAILURE: Number
    var WSASERVICE_NOT_FOUND: Number
    var WSATYPE_NOT_FOUND: Number
    var WSA_E_NO_MORE: Number
    var WSA_E_CANCELLED: Number
    var WSAEREFUSED: Number
}

external interface `T$74` {
    var PRIORITY_LOW: Number
    var PRIORITY_BELOW_NORMAL: Number
    var PRIORITY_NORMAL: Number
    var PRIORITY_ABOVE_NORMAL: Number
    var PRIORITY_HIGH: Number
    var PRIORITY_HIGHEST: Number
}

external object constants {
    var UV_UDP_REUSEADDR: Number
    var signals: `T$72`
    var errno: `T$73`
    var priority: `T$74`
}

external fun arch(): String

external fun platform(): String /* 'aix' | 'android' | 'darwin' | 'freebsd' | 'linux' | 'openbsd' | 'sunos' | 'win32' | 'cygwin' | 'netbsd' */

external fun tmpdir(): String

external var EOL: String

external fun endianness(): String /* "BE" | "LE" */

external fun getPriority(pid: Number = definedExternally): Number

external fun setPriority(priority: Number)

external fun setPriority(pid: Number, priority: Number)