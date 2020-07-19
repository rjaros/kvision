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

@file:JsQualifier("pl.treksoft.kvision.electron.crypto.constants")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.crypto.constants

external var OPENSSL_VERSION_NUMBER: Number

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

external var ENGINE_METHOD_RSA: Number

external var ENGINE_METHOD_DSA: Number

external var ENGINE_METHOD_DH: Number

external var ENGINE_METHOD_RAND: Number

external var ENGINE_METHOD_EC: Number

external var ENGINE_METHOD_CIPHERS: Number

external var ENGINE_METHOD_DIGESTS: Number

external var ENGINE_METHOD_PKEY_METHS: Number

external var ENGINE_METHOD_PKEY_ASN1_METHS: Number

external var ENGINE_METHOD_ALL: Number

external var ENGINE_METHOD_NONE: Number

external var DH_CHECK_P_NOT_SAFE_PRIME: Number

external var DH_CHECK_P_NOT_PRIME: Number

external var DH_UNABLE_TO_CHECK_GENERATOR: Number

external var DH_NOT_SUITABLE_GENERATOR: Number

external var ALPN_ENABLED: Number

external var RSA_PKCS1_PADDING: Number

external var RSA_SSLV23_PADDING: Number

external var RSA_NO_PADDING: Number

external var RSA_PKCS1_OAEP_PADDING: Number

external var RSA_X931_PADDING: Number

external var RSA_PKCS1_PSS_PADDING: Number

external var RSA_PSS_SALTLEN_DIGEST: Number

external var RSA_PSS_SALTLEN_MAX_SIGN: Number

external var RSA_PSS_SALTLEN_AUTO: Number

external var POINT_CONVERSION_COMPRESSED: Number

external var POINT_CONVERSION_UNCOMPRESSED: Number

external var POINT_CONVERSION_HYBRID: Number

external var defaultCoreCipherList: String

external var defaultCipherList: String