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

package io.kvision.cordova

import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Main object for Cordova network.
 */
@Deprecated("The kvision-cordova module is deprecated and will be removed in a future version.")
object Network {

    /**
     * Connection types
     */
    enum class ConnectionType {
        UNKNOWN,
        ETHERNET,
        WIFI,
        CELL_2G,
        CELL_3G,
        CELL_4G,
        CELL,
        NONE
    }

    /**
     * Network status events
     */
    enum class NetworkEvent(internal val type: String) {
        OFFLINE("offline"),
        ONLINE("online")
    }

    /**
     * Get network connection type.
     */
    suspend fun getConnectionType(): ConnectionType {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                val connectionType = when (window.navigator.asDynamic().connection.type) {
                    js("Connection.ETHERNET") -> ConnectionType.ETHERNET
                    js("Connection.WIFI") -> ConnectionType.WIFI
                    js("Connection.CELL_2G") -> ConnectionType.CELL_2G
                    js("Connection.CELL_3G") -> ConnectionType.CELL_3G
                    js("Connection.CELL_4G") -> ConnectionType.CELL_4G
                    js("Connection.CELL") -> ConnectionType.CELL
                    js("Connection.NONE") -> ConnectionType.NONE
                    else -> ConnectionType.UNKNOWN
                }
                continuation.resume(connectionType)
            }
        }
    }

    /**
     * Add listeners for network status Cordova events.
     */
    fun addStatusListener(event: NetworkEvent, listener: () -> Unit) {
        addDeviceReadyListener {
            document.addEventListener(event.type, {
                listener()
            }, false)
        }
    }

}
