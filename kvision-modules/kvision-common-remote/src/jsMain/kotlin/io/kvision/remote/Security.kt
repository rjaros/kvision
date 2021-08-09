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

import kotlinx.coroutines.asDeferred
import kotlinx.serialization.Serializable

/**
 * A security exception.
 */
class SecurityException(message: String) : Exception(message)

/**
 * Username and password credentials.
 */
@Serializable
data class Credentials(val username: String? = null, val password: String? = null)

/**
 * Form login dispatcher.
 */
class LoginService(val loginEndpoint: String) {
    val loginAgent = CallAgent()

    /**
     * Login with a form.
     * @param credentials username and password credentials
     */
    suspend fun login(credentials: Credentials?): Boolean =
        if (credentials?.username != null) {
            loginAgent.remoteCall(loginEndpoint, obj {
                this.username = credentials.username
                this.password = credentials.password
            }, HttpMethod.POST, "application/x-www-form-urlencoded", ResponseBodyType.READABLE_STREAM).then { _: dynamic -> true }.asDeferred().await()
        } else {
            throw SecurityException("Credentials cannot be empty")
        }
}

/**
 * Form login dispatcher.
 */
abstract class SecurityMgr {

    private var isLoggedIn = false

    /**
     * Executes given block of code after successful authentication.
     * @param block a block of code
     */
    @Suppress("NestedBlockDepth", "TooGenericExceptionCaught")
    suspend fun <T> withAuth(block: suspend () -> T): T {
        return try {
            block().also {
                if (!isLoggedIn) {
                    isLoggedIn = true
                    afterLogin()
                }
            }
        } catch (e: Exception) {
            if (e is SecurityException || !isLoggedIn) {
                afterError()
                isLoggedIn = false
                while (!isLoggedIn) {
                    try {
                        login()
                        isLoggedIn = true
                        afterLogin()
                    } catch (e: Throwable) {
                        console.log(e)
                    }
                }
                block()
            } else {
                throw e
            }
        }
    }

    /**
     * Login user.
     * @return true if login is successful
     * @throws SecurityException if login is not successful
     */
    abstract suspend fun login(): Boolean

    /**
     * Method called after successful login.
     */
    open suspend fun afterLogin() {}

    /**
     * Method called after error.
     */
    open suspend fun afterError() {}
}
