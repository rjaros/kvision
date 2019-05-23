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

package pl.treksoft.kvision.cordova

import kotlin.browser.document
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Device information class.
 */
external class Device {
    val cordova: String = definedExternally
    val model: String = definedExternally
    val platform: String = definedExternally
    val uuid: String = definedExternally
    val version: String = definedExternally
    val manufacturer: String = definedExternally
    val isVirtual: Boolean = definedExternally
    val serial: String = definedExternally
}

/**
 * External device information object.
 */
external val device: Device

private var intDevice: Device? = null

/**
 * Add listeners for 'deviceready' Cordova event.
 */
fun addDeviceReadyListener(listener: (Device) -> Unit) {
    document.addEventListener("deviceready", {
        listener(device)
    }, false)
}

/**
 * Suspending function to return device information object.
 */
suspend fun getDevice(): Device {
    return intDevice ?: suspendCoroutine { continuation ->
        addDeviceReadyListener {
            intDevice = device
            continuation.resume(device)
        }
    }
}
