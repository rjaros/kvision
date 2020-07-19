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

@file:JsModule("constants")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.constants

external var E2BIG: Number

external var EACCES: Number

external var EADDRINUSE: Number

external var EADDRNOTAVAIL: Number

external var EAFNOSUPPORT: Number

external var EAGAIN: Number

external var EALREADY: Number

external var EBADF: Number

external var EBADMSG: Number

external var EBUSY: Number

external var ECANCELED: Number

external var ECHILD: Number

external var ECONNABORTED: Number

external var ECONNREFUSED: Number

external var ECONNRESET: Number

external var EDEADLK: Number

external var EDESTADDRREQ: Number

external var EDOM: Number

external var EEXIST: Number

external var EFAULT: Number

external var EFBIG: Number

external var EHOSTUNREACH: Number

external var EIDRM: Number

external var EILSEQ: Number

external var EINPROGRESS: Number

external var EINTR: Number

external var EINVAL: Number

external var EIO: Number

external var EISCONN: Number

external var EISDIR: Number

external var ELOOP: Number

external var EMFILE: Number

external var EMLINK: Number

external var EMSGSIZE: Number

external var ENAMETOOLONG: Number

external var ENETDOWN: Number

external var ENETRESET: Number

external var ENETUNREACH: Number

external var ENFILE: Number

external var ENOBUFS: Number

external var ENODATA: Number

external var ENODEV: Number

external var ENOENT: Number

external var ENOEXEC: Number

external var ENOLCK: Number

external var ENOLINK: Number

external var ENOMEM: Number

external var ENOMSG: Number

external var ENOPROTOOPT: Number

external var ENOSPC: Number

external var ENOSR: Number

external var ENOSTR: Number

external var ENOSYS: Number

external var ENOTCONN: Number

external var ENOTDIR: Number

external var ENOTEMPTY: Number

external var ENOTSOCK: Number

external var ENOTSUP: Number

external var ENOTTY: Number

external var ENXIO: Number

external var EOPNOTSUPP: Number

external var EOVERFLOW: Number

external var EPERM: Number

external var EPIPE: Number

external var EPROTO: Number

external var EPROTONOSUPPORT: Number

external var EPROTOTYPE: Number

external var ERANGE: Number

external var EROFS: Number

external var ESPIPE: Number

external var ESRCH: Number

external var ETIME: Number

external var ETIMEDOUT: Number

external var ETXTBSY: Number

external var EWOULDBLOCK: Number

external var EXDEV: Number

external var WSAEINTR: Number

external var WSAEBADF: Number

external var WSAEACCES: Number

external var WSAEFAULT: Number

external var WSAEINVAL: Number

external var WSAEMFILE: Number

external var WSAEWOULDBLOCK: Number

external var WSAEINPROGRESS: Number

external var WSAEALREADY: Number

external var WSAENOTSOCK: Number

external var WSAEDESTADDRREQ: Number

external var WSAEMSGSIZE: Number

external var WSAEPROTOTYPE: Number

external var WSAENOPROTOOPT: Number

external var WSAEPROTONOSUPPORT: Number

external var WSAESOCKTNOSUPPORT: Number

external var WSAEOPNOTSUPP: Number

external var WSAEPFNOSUPPORT: Number

external var WSAEAFNOSUPPORT: Number

external var WSAEADDRINUSE: Number

external var WSAEADDRNOTAVAIL: Number

external var WSAENETDOWN: Number

external var WSAENETUNREACH: Number

external var WSAENETRESET: Number

external var WSAECONNABORTED: Number

external var WSAECONNRESET: Number

external var WSAENOBUFS: Number

external var WSAEISCONN: Number

external var WSAENOTCONN: Number

external var WSAESHUTDOWN: Number

external var WSAETOOMANYREFS: Number

external var WSAETIMEDOUT: Number

external var WSAECONNREFUSED: Number

external var WSAELOOP: Number

external var WSAENAMETOOLONG: Number

external var WSAEHOSTDOWN: Number

external var WSAEHOSTUNREACH: Number

external var WSAENOTEMPTY: Number

external var WSAEPROCLIM: Number

external var WSAEUSERS: Number

external var WSAEDQUOT: Number

external var WSAESTALE: Number

external var WSAEREMOTE: Number

external var WSASYSNOTREADY: Number

external var WSAVERNOTSUPPORTED: Number

external var WSANOTINITIALISED: Number

external var WSAEDISCON: Number

external var WSAENOMORE: Number

external var WSAECANCELLED: Number

external var WSAEINVALIDPROCTABLE: Number

external var WSAEINVALIDPROVIDER: Number

external var WSAEPROVIDERFAILEDINIT: Number

external var WSASYSCALLFAILURE: Number

external var WSASERVICE_NOT_FOUND: Number

external var WSATYPE_NOT_FOUND: Number

external var WSA_E_NO_MORE: Number

external var WSA_E_CANCELLED: Number

external var WSAEREFUSED: Number

external var SIGHUP: Number

external var SIGINT: Number

external var SIGILL: Number

external var SIGABRT: Number

external var SIGFPE: Number

external var SIGKILL: Number

external var SIGSEGV: Number

external var SIGTERM: Number

external var SIGBREAK: Number

external var SIGWINCH: Number

external var SSL_OP_ALL: Number

external var SSL_OP_ALLOW_UNSAFE_LEGACY_RENEGOTIATION: Number

external var SSL_OP_CIPHER_SERVER_PREFERENCE: Number

external var SSL_OP_CISCO_ANYCONNECT: Number

external var SSL_OP_COOKIE_EXCHANGE: Number

external var SSL_OP_CRYPTOPRO_TLSEXT_BUG: Number

external var SSL_OP_DONT_INSERT_EMPTY_FRAGMENTS: Number

external var SSL_OP_EPHEMERAL_RSA: Number

external var SSL_OP_LEGACY_SERVER_CONNECT: Number

external var SSL_OP_MICROSOFT_BIG_SSLV3_BUFFER: Number

external var SSL_OP_MICROSOFT_SESS_ID_BUG: Number

external var SSL_OP_MSIE_SSLV2_RSA_PADDING: Number

external var SSL_OP_NETSCAPE_CA_DN_BUG: Number

external var SSL_OP_NETSCAPE_CHALLENGE_BUG: Number

external var SSL_OP_NETSCAPE_DEMO_CIPHER_CHANGE_BUG: Number

external var SSL_OP_NETSCAPE_REUSE_CIPHER_CHANGE_BUG: Number

external var SSL_OP_NO_COMPRESSION: Number

external var SSL_OP_NO_QUERY_MTU: Number

external var SSL_OP_NO_SESSION_RESUMPTION_ON_RENEGOTIATION: Number

external var SSL_OP_NO_SSLv2: Number

external var SSL_OP_NO_SSLv3: Number

external var SSL_OP_NO_TICKET: Number

external var SSL_OP_NO_TLSv1: Number

external var SSL_OP_NO_TLSv1_1: Number

external var SSL_OP_NO_TLSv1_2: Number

external var SSL_OP_PKCS1_CHECK_1: Number

external var SSL_OP_PKCS1_CHECK_2: Number

external var SSL_OP_SINGLE_DH_USE: Number

external var SSL_OP_SINGLE_ECDH_USE: Number

external var SSL_OP_SSLEAY_080_CLIENT_DH_BUG: Number

external var SSL_OP_SSLREF2_REUSE_CERT_TYPE_BUG: Number

external var SSL_OP_TLS_BLOCK_PADDING_BUG: Number

external var SSL_OP_TLS_D5_BUG: Number

external var SSL_OP_TLS_ROLLBACK_BUG: Number

external var ENGINE_METHOD_DSA: Number

external var ENGINE_METHOD_DH: Number

external var ENGINE_METHOD_RAND: Number

external var ENGINE_METHOD_ECDH: Number

external var ENGINE_METHOD_ECDSA: Number

external var ENGINE_METHOD_CIPHERS: Number

external var ENGINE_METHOD_DIGESTS: Number

external var ENGINE_METHOD_STORE: Number

external var ENGINE_METHOD_PKEY_METHS: Number

external var ENGINE_METHOD_PKEY_ASN1_METHS: Number

external var ENGINE_METHOD_ALL: Number

external var ENGINE_METHOD_NONE: Number

external var DH_CHECK_P_NOT_SAFE_PRIME: Number

external var DH_CHECK_P_NOT_PRIME: Number

external var DH_UNABLE_TO_CHECK_GENERATOR: Number

external var DH_NOT_SUITABLE_GENERATOR: Number

external var RSA_PKCS1_PADDING: Number

external var RSA_SSLV23_PADDING: Number

external var RSA_NO_PADDING: Number

external var RSA_PKCS1_OAEP_PADDING: Number

external var RSA_X931_PADDING: Number

external var RSA_PKCS1_PSS_PADDING: Number

external var POINT_CONVERSION_COMPRESSED: Number

external var POINT_CONVERSION_UNCOMPRESSED: Number

external var POINT_CONVERSION_HYBRID: Number

external var O_RDONLY: Number

external var O_WRONLY: Number

external var O_RDWR: Number

external var S_IFMT: Number

external var S_IFREG: Number

external var S_IFDIR: Number

external var S_IFCHR: Number

external var S_IFBLK: Number

external var S_IFIFO: Number

external var S_IFSOCK: Number

external var S_IRWXU: Number

external var S_IRUSR: Number

external var S_IWUSR: Number

external var S_IXUSR: Number

external var S_IRWXG: Number

external var S_IRGRP: Number

external var S_IWGRP: Number

external var S_IXGRP: Number

external var S_IRWXO: Number

external var S_IROTH: Number

external var S_IWOTH: Number

external var S_IXOTH: Number

external var S_IFLNK: Number

external var O_CREAT: Number

external var O_EXCL: Number

external var O_NOCTTY: Number

external var O_DIRECTORY: Number

external var O_NOATIME: Number

external var O_NOFOLLOW: Number

external var O_SYNC: Number

external var O_DSYNC: Number

external var O_SYMLINK: Number

external var O_DIRECT: Number

external var O_NONBLOCK: Number

external var O_TRUNC: Number

external var O_APPEND: Number

external var F_OK: Number

external var R_OK: Number

external var W_OK: Number

external var X_OK: Number

external var COPYFILE_EXCL: Number

external var COPYFILE_FICLONE: Number

external var COPYFILE_FICLONE_FORCE: Number

external var UV_UDP_REUSEADDR: Number

external var SIGQUIT: Number

external var SIGTRAP: Number

external var SIGIOT: Number

external var SIGBUS: Number

external var SIGUSR1: Number

external var SIGUSR2: Number

external var SIGPIPE: Number

external var SIGALRM: Number

external var SIGCHLD: Number

external var SIGSTKFLT: Number

external var SIGCONT: Number

external var SIGSTOP: Number

external var SIGTSTP: Number

external var SIGTTIN: Number

external var SIGTTOU: Number

external var SIGURG: Number

external var SIGXCPU: Number

external var SIGXFSZ: Number

external var SIGVTALRM: Number

external var SIGPROF: Number

external var SIGIO: Number

external var SIGPOLL: Number

external var SIGPWR: Number

external var SIGSYS: Number

external var SIGUNUSED: Number

external var defaultCoreCipherList: String

external var defaultCipherList: String

external var ENGINE_METHOD_RSA: Number

external var ALPN_ENABLED: Number