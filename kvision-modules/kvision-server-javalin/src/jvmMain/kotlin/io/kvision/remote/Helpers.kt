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

package io.kvision.remote

import io.javalin.http.Context
import io.javalin.websocket.WsContext
import org.eclipse.jetty.websocket.api.CloseStatus
import org.eclipse.jetty.websocket.api.RemoteEndpoint
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.SuspendToken
import org.eclipse.jetty.websocket.api.UpgradeRequest
import org.eclipse.jetty.websocket.api.UpgradeResponse
import org.eclipse.jetty.websocket.api.WebSocketPolicy
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.security.Principal
import java.util.*
import javax.servlet.AsyncContext
import javax.servlet.DispatcherType
import javax.servlet.RequestDispatcher
import javax.servlet.ServletContext
import javax.servlet.ServletInputStream
import javax.servlet.ServletOutputStream
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import javax.servlet.http.HttpUpgradeHandler
import javax.servlet.http.Part

/**
 * Empty subclass of the Context class
 */
internal class KVContext : Context(KVHttpServletRequest(), KVHttpServletResponse())

/**
 * Empty implementation of the HttpServletRequest interface
 */
internal class KVHttpServletRequest : HttpServletRequest {
    override fun isUserInRole(role: String?): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun startAsync(): AsyncContext {
        throw IllegalStateException("Empty implementation")
    }

    override fun startAsync(servletRequest: ServletRequest?, servletResponse: ServletResponse?): AsyncContext {
        throw IllegalStateException("Empty implementation")
    }

    override fun getPathInfo(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getProtocol(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getCookies(): Array<Cookie> {
        throw IllegalStateException("Empty implementation")
    }

    override fun getParameterMap(): MutableMap<String, Array<String>> {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRequestURL(): StringBuffer {
        throw IllegalStateException("Empty implementation")
    }

    override fun getAttributeNames(): Enumeration<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun setCharacterEncoding(env: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getParameterValues(name: String?): Array<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRemoteAddr(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun isAsyncStarted(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getContentLengthLong(): Long {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocales(): Enumeration<Locale> {
        throw IllegalStateException("Empty implementation")
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getRealPath(path: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun login(username: String?, password: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getContextPath(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun isRequestedSessionIdValid(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getServerPort(): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun getAttribute(name: String?): Any {
        throw IllegalStateException("Empty implementation")
    }

    override fun getDateHeader(name: String?): Long {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRemoteHost(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRequestedSessionId(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getServletPath(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getSession(create: Boolean): HttpSession {
        throw IllegalStateException("Empty implementation")
    }

    override fun getSession(): HttpSession {
        throw IllegalStateException("Empty implementation")
    }

    override fun getServerName(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocalAddr(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun isSecure(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun <T : HttpUpgradeHandler?> upgrade(handlerClass: Class<T>?): T {
        throw IllegalStateException("Empty implementation")
    }

    override fun isRequestedSessionIdFromCookie(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getPart(name: String?): Part {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRemoteUser(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocale(): Locale {
        throw IllegalStateException("Empty implementation")
    }

    override fun getMethod(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun isRequestedSessionIdFromURL(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocalPort(): Int {
        throw IllegalStateException("Empty implementation")
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun isRequestedSessionIdFromUrl(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getServletContext(): ServletContext {
        throw IllegalStateException("Empty implementation")
    }

    override fun getQueryString(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getDispatcherType(): DispatcherType {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHeaders(name: String?): Enumeration<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun getUserPrincipal(): Principal {
        throw IllegalStateException("Empty implementation")
    }

    override fun getParts(): MutableCollection<Part> {
        throw IllegalStateException("Empty implementation")
    }

    override fun getReader(): BufferedReader {
        throw IllegalStateException("Empty implementation")
    }

    override fun getScheme(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun logout() {
        throw IllegalStateException("Empty implementation")
    }

    override fun getInputStream(): ServletInputStream {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocalName(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun isAsyncSupported(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getAuthType(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getCharacterEncoding(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getParameterNames(): Enumeration<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun authenticate(response: HttpServletResponse?): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun removeAttribute(name: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getPathTranslated(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getContentLength(): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHeader(name: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getIntHeader(name: String?): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun changeSessionId(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getContentType(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getAsyncContext(): AsyncContext {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRequestURI(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRequestDispatcher(path: String?): RequestDispatcher {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHeaderNames(): Enumeration<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun setAttribute(name: String?, o: Any?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getParameter(name: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRemotePort(): Int {
        throw IllegalStateException("Empty implementation")
    }

}

/**
 * Empty implementation of the HttpServletResponse interface
 */
internal class KVHttpServletResponse : HttpServletResponse {
    override fun encodeURL(url: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun encodeUrl(url: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun addIntHeader(name: String?, value: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun addCookie(cookie: Cookie?) {
        throw IllegalStateException("Empty implementation")
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun encodeRedirectUrl(url: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun flushBuffer() {
        throw IllegalStateException("Empty implementation")
    }

    override fun encodeRedirectURL(url: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun sendRedirect(location: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setBufferSize(size: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocale(): Locale {
        throw IllegalStateException("Empty implementation")
    }

    override fun sendError(sc: Int, msg: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun sendError(sc: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setContentLengthLong(len: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setCharacterEncoding(charset: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun addDateHeader(name: String?, date: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setLocale(loc: Locale?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHeaders(name: String?): MutableCollection<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun addHeader(name: String?, value: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun setContentLength(len: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getBufferSize(): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun resetBuffer() {
        throw IllegalStateException("Empty implementation")
    }

    override fun reset() {
        throw IllegalStateException("Empty implementation")
    }

    override fun setDateHeader(name: String?, date: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getStatus(): Int {
        throw IllegalStateException("Empty implementation")
    }

    override fun getCharacterEncoding(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun isCommitted(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun setStatus(sc: Int) {
        throw IllegalStateException("Empty implementation")
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun setStatus(sc: Int, sm: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHeader(name: String?): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getContentType(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getWriter(): PrintWriter {
        throw IllegalStateException("Empty implementation")
    }

    override fun containsHeader(name: String?): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun setIntHeader(name: String?, value: Int) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getHeaderNames(): MutableCollection<String> {
        throw IllegalStateException("Empty implementation")
    }

    override fun setHeader(name: String?, value: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getOutputStream(): ServletOutputStream {
        throw IllegalStateException("Empty implementation")
    }

    override fun setContentType(type: String?) {
        throw IllegalStateException("Empty implementation")
    }

}

/**
 * Empty subclass of the WsContext class
 */
internal class KVWsContext : WsContext("EMPTY", KVSession())

/**
 * Empty implementation of the Session interface
 */
internal class KVSession : Session {
    override fun getRemote(): RemoteEndpoint {
        throw IllegalStateException("Empty implementation")
    }

    override fun getLocalAddress(): InetSocketAddress {
        throw IllegalStateException("Empty implementation")
    }

    override fun disconnect() {
        throw IllegalStateException("Empty implementation")
    }

    override fun getProtocolVersion(): String {
        throw IllegalStateException("Empty implementation")
    }

    override fun getUpgradeResponse(): UpgradeResponse {
        throw IllegalStateException("Empty implementation")
    }

    override fun setIdleTimeout(ms: Long) {
        throw IllegalStateException("Empty implementation")
    }

    override fun getPolicy(): WebSocketPolicy {
        throw IllegalStateException("Empty implementation")
    }

    override fun getUpgradeRequest(): UpgradeRequest {
        throw IllegalStateException("Empty implementation")
    }

    override fun suspend(): SuspendToken {
        throw IllegalStateException("Empty implementation")
    }

    override fun isOpen(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getIdleTimeout(): Long {
        throw IllegalStateException("Empty implementation")
    }

    override fun close() {
        throw IllegalStateException("Empty implementation")
    }

    override fun close(closeStatus: CloseStatus?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun close(statusCode: Int, reason: String?) {
        throw IllegalStateException("Empty implementation")
    }

    override fun isSecure(): Boolean {
        throw IllegalStateException("Empty implementation")
    }

    override fun getRemoteAddress(): InetSocketAddress {
        throw IllegalStateException("Empty implementation")
    }

}
