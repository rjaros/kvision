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

@file:JsQualifier("pl.treksoft.kvision.electron.http2.constants")
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.http2.constants

external var NGHTTP2_SESSION_SERVER: Number

external var NGHTTP2_SESSION_CLIENT: Number

external var NGHTTP2_STREAM_STATE_IDLE: Number

external var NGHTTP2_STREAM_STATE_OPEN: Number

external var NGHTTP2_STREAM_STATE_RESERVED_LOCAL: Number

external var NGHTTP2_STREAM_STATE_RESERVED_REMOTE: Number

external var NGHTTP2_STREAM_STATE_HALF_CLOSED_LOCAL: Number

external var NGHTTP2_STREAM_STATE_HALF_CLOSED_REMOTE: Number

external var NGHTTP2_STREAM_STATE_CLOSED: Number

external var NGHTTP2_NO_ERROR: Number

external var NGHTTP2_PROTOCOL_ERROR: Number

external var NGHTTP2_INTERNAL_ERROR: Number

external var NGHTTP2_FLOW_CONTROL_ERROR: Number

external var NGHTTP2_SETTINGS_TIMEOUT: Number

external var NGHTTP2_STREAM_CLOSED: Number

external var NGHTTP2_FRAME_SIZE_ERROR: Number

external var NGHTTP2_REFUSED_STREAM: Number

external var NGHTTP2_CANCEL: Number

external var NGHTTP2_COMPRESSION_ERROR: Number

external var NGHTTP2_CONNECT_ERROR: Number

external var NGHTTP2_ENHANCE_YOUR_CALM: Number

external var NGHTTP2_INADEQUATE_SECURITY: Number

external var NGHTTP2_HTTP_1_1_REQUIRED: Number

external var NGHTTP2_ERR_FRAME_SIZE_ERROR: Number

external var NGHTTP2_FLAG_NONE: Number

external var NGHTTP2_FLAG_END_STREAM: Number

external var NGHTTP2_FLAG_END_HEADERS: Number

external var NGHTTP2_FLAG_ACK: Number

external var NGHTTP2_FLAG_PADDED: Number

external var NGHTTP2_FLAG_PRIORITY: Number

external var DEFAULT_SETTINGS_HEADER_TABLE_SIZE: Number

external var DEFAULT_SETTINGS_ENABLE_PUSH: Number

external var DEFAULT_SETTINGS_INITIAL_WINDOW_SIZE: Number

external var DEFAULT_SETTINGS_MAX_FRAME_SIZE: Number

external var MAX_MAX_FRAME_SIZE: Number

external var MIN_MAX_FRAME_SIZE: Number

external var MAX_INITIAL_WINDOW_SIZE: Number

external var NGHTTP2_DEFAULT_WEIGHT: Number

external var NGHTTP2_SETTINGS_HEADER_TABLE_SIZE: Number

external var NGHTTP2_SETTINGS_ENABLE_PUSH: Number

external var NGHTTP2_SETTINGS_MAX_CONCURRENT_STREAMS: Number

external var NGHTTP2_SETTINGS_INITIAL_WINDOW_SIZE: Number

external var NGHTTP2_SETTINGS_MAX_FRAME_SIZE: Number

external var NGHTTP2_SETTINGS_MAX_HEADER_LIST_SIZE: Number

external var PADDING_STRATEGY_NONE: Number

external var PADDING_STRATEGY_MAX: Number

external var PADDING_STRATEGY_CALLBACK: Number

external var HTTP2_HEADER_STATUS: String

external var HTTP2_HEADER_METHOD: String

external var HTTP2_HEADER_AUTHORITY: String

external var HTTP2_HEADER_SCHEME: String

external var HTTP2_HEADER_PATH: String

external var HTTP2_HEADER_ACCEPT_CHARSET: String

external var HTTP2_HEADER_ACCEPT_ENCODING: String

external var HTTP2_HEADER_ACCEPT_LANGUAGE: String

external var HTTP2_HEADER_ACCEPT_RANGES: String

external var HTTP2_HEADER_ACCEPT: String

external var HTTP2_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN: String

external var HTTP2_HEADER_AGE: String

external var HTTP2_HEADER_ALLOW: String

external var HTTP2_HEADER_AUTHORIZATION: String

external var HTTP2_HEADER_CACHE_CONTROL: String

external var HTTP2_HEADER_CONNECTION: String

external var HTTP2_HEADER_CONTENT_DISPOSITION: String

external var HTTP2_HEADER_CONTENT_ENCODING: String

external var HTTP2_HEADER_CONTENT_LANGUAGE: String

external var HTTP2_HEADER_CONTENT_LENGTH: String

external var HTTP2_HEADER_CONTENT_LOCATION: String

external var HTTP2_HEADER_CONTENT_MD5: String

external var HTTP2_HEADER_CONTENT_RANGE: String

external var HTTP2_HEADER_CONTENT_TYPE: String

external var HTTP2_HEADER_COOKIE: String

external var HTTP2_HEADER_DATE: String

external var HTTP2_HEADER_ETAG: String

external var HTTP2_HEADER_EXPECT: String

external var HTTP2_HEADER_EXPIRES: String

external var HTTP2_HEADER_FROM: String

external var HTTP2_HEADER_HOST: String

external var HTTP2_HEADER_IF_MATCH: String

external var HTTP2_HEADER_IF_MODIFIED_SINCE: String

external var HTTP2_HEADER_IF_NONE_MATCH: String

external var HTTP2_HEADER_IF_RANGE: String

external var HTTP2_HEADER_IF_UNMODIFIED_SINCE: String

external var HTTP2_HEADER_LAST_MODIFIED: String

external var HTTP2_HEADER_LINK: String

external var HTTP2_HEADER_LOCATION: String

external var HTTP2_HEADER_MAX_FORWARDS: String

external var HTTP2_HEADER_PREFER: String

external var HTTP2_HEADER_PROXY_AUTHENTICATE: String

external var HTTP2_HEADER_PROXY_AUTHORIZATION: String

external var HTTP2_HEADER_RANGE: String

external var HTTP2_HEADER_REFERER: String

external var HTTP2_HEADER_REFRESH: String

external var HTTP2_HEADER_RETRY_AFTER: String

external var HTTP2_HEADER_SERVER: String

external var HTTP2_HEADER_SET_COOKIE: String

external var HTTP2_HEADER_STRICT_TRANSPORT_SECURITY: String

external var HTTP2_HEADER_TRANSFER_ENCODING: String

external var HTTP2_HEADER_TE: String

external var HTTP2_HEADER_UPGRADE: String

external var HTTP2_HEADER_USER_AGENT: String

external var HTTP2_HEADER_VARY: String

external var HTTP2_HEADER_VIA: String

external var HTTP2_HEADER_WWW_AUTHENTICATE: String

external var HTTP2_HEADER_HTTP2_SETTINGS: String

external var HTTP2_HEADER_KEEP_ALIVE: String

external var HTTP2_HEADER_PROXY_CONNECTION: String

external var HTTP2_METHOD_ACL: String

external var HTTP2_METHOD_BASELINE_CONTROL: String

external var HTTP2_METHOD_BIND: String

external var HTTP2_METHOD_CHECKIN: String

external var HTTP2_METHOD_CHECKOUT: String

external var HTTP2_METHOD_CONNECT: String

external var HTTP2_METHOD_COPY: String

external var HTTP2_METHOD_DELETE: String

external var HTTP2_METHOD_GET: String

external var HTTP2_METHOD_HEAD: String

external var HTTP2_METHOD_LABEL: String

external var HTTP2_METHOD_LINK: String

external var HTTP2_METHOD_LOCK: String

external var HTTP2_METHOD_MERGE: String

external var HTTP2_METHOD_MKACTIVITY: String

external var HTTP2_METHOD_MKCALENDAR: String

external var HTTP2_METHOD_MKCOL: String

external var HTTP2_METHOD_MKREDIRECTREF: String

external var HTTP2_METHOD_MKWORKSPACE: String

external var HTTP2_METHOD_MOVE: String

external var HTTP2_METHOD_OPTIONS: String

external var HTTP2_METHOD_ORDERPATCH: String

external var HTTP2_METHOD_PATCH: String

external var HTTP2_METHOD_POST: String

external var HTTP2_METHOD_PRI: String

external var HTTP2_METHOD_PROPFIND: String

external var HTTP2_METHOD_PROPPATCH: String

external var HTTP2_METHOD_PUT: String

external var HTTP2_METHOD_REBIND: String

external var HTTP2_METHOD_REPORT: String

external var HTTP2_METHOD_SEARCH: String

external var HTTP2_METHOD_TRACE: String

external var HTTP2_METHOD_UNBIND: String

external var HTTP2_METHOD_UNCHECKOUT: String

external var HTTP2_METHOD_UNLINK: String

external var HTTP2_METHOD_UNLOCK: String

external var HTTP2_METHOD_UPDATE: String

external var HTTP2_METHOD_UPDATEREDIRECTREF: String

external var HTTP2_METHOD_VERSION_CONTROL: String

external var HTTP_STATUS_CONTINUE: Number

external var HTTP_STATUS_SWITCHING_PROTOCOLS: Number

external var HTTP_STATUS_PROCESSING: Number

external var HTTP_STATUS_OK: Number

external var HTTP_STATUS_CREATED: Number

external var HTTP_STATUS_ACCEPTED: Number

external var HTTP_STATUS_NON_AUTHORITATIVE_INFORMATION: Number

external var HTTP_STATUS_NO_CONTENT: Number

external var HTTP_STATUS_RESET_CONTENT: Number

external var HTTP_STATUS_PARTIAL_CONTENT: Number

external var HTTP_STATUS_MULTI_STATUS: Number

external var HTTP_STATUS_ALREADY_REPORTED: Number

external var HTTP_STATUS_IM_USED: Number

external var HTTP_STATUS_MULTIPLE_CHOICES: Number

external var HTTP_STATUS_MOVED_PERMANENTLY: Number

external var HTTP_STATUS_FOUND: Number

external var HTTP_STATUS_SEE_OTHER: Number

external var HTTP_STATUS_NOT_MODIFIED: Number

external var HTTP_STATUS_USE_PROXY: Number

external var HTTP_STATUS_TEMPORARY_REDIRECT: Number

external var HTTP_STATUS_PERMANENT_REDIRECT: Number

external var HTTP_STATUS_BAD_REQUEST: Number

external var HTTP_STATUS_UNAUTHORIZED: Number

external var HTTP_STATUS_PAYMENT_REQUIRED: Number

external var HTTP_STATUS_FORBIDDEN: Number

external var HTTP_STATUS_NOT_FOUND: Number

external var HTTP_STATUS_METHOD_NOT_ALLOWED: Number

external var HTTP_STATUS_NOT_ACCEPTABLE: Number

external var HTTP_STATUS_PROXY_AUTHENTICATION_REQUIRED: Number

external var HTTP_STATUS_REQUEST_TIMEOUT: Number

external var HTTP_STATUS_CONFLICT: Number

external var HTTP_STATUS_GONE: Number

external var HTTP_STATUS_LENGTH_REQUIRED: Number

external var HTTP_STATUS_PRECONDITION_FAILED: Number

external var HTTP_STATUS_PAYLOAD_TOO_LARGE: Number

external var HTTP_STATUS_URI_TOO_LONG: Number

external var HTTP_STATUS_UNSUPPORTED_MEDIA_TYPE: Number

external var HTTP_STATUS_RANGE_NOT_SATISFIABLE: Number

external var HTTP_STATUS_EXPECTATION_FAILED: Number

external var HTTP_STATUS_TEAPOT: Number

external var HTTP_STATUS_MISDIRECTED_REQUEST: Number

external var HTTP_STATUS_UNPROCESSABLE_ENTITY: Number

external var HTTP_STATUS_LOCKED: Number

external var HTTP_STATUS_FAILED_DEPENDENCY: Number

external var HTTP_STATUS_UNORDERED_COLLECTION: Number

external var HTTP_STATUS_UPGRADE_REQUIRED: Number

external var HTTP_STATUS_PRECONDITION_REQUIRED: Number

external var HTTP_STATUS_TOO_MANY_REQUESTS: Number

external var HTTP_STATUS_REQUEST_HEADER_FIELDS_TOO_LARGE: Number

external var HTTP_STATUS_UNAVAILABLE_FOR_LEGAL_REASONS: Number

external var HTTP_STATUS_INTERNAL_SERVER_ERROR: Number

external var HTTP_STATUS_NOT_IMPLEMENTED: Number

external var HTTP_STATUS_BAD_GATEWAY: Number

external var HTTP_STATUS_SERVICE_UNAVAILABLE: Number

external var HTTP_STATUS_GATEWAY_TIMEOUT: Number

external var HTTP_STATUS_HTTP_VERSION_NOT_SUPPORTED: Number

external var HTTP_STATUS_VARIANT_ALSO_NEGOTIATES: Number

external var HTTP_STATUS_INSUFFICIENT_STORAGE: Number

external var HTTP_STATUS_LOOP_DETECTED: Number

external var HTTP_STATUS_BANDWIDTH_LIMIT_EXCEEDED: Number

external var HTTP_STATUS_NOT_EXTENDED: Number

external var HTTP_STATUS_NETWORK_AUTHENTICATION_REQUIRED: Number