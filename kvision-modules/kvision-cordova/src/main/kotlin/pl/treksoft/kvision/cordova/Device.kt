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

import org.w3c.dom.events.Event
import kotlinx.browser.document
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Device information class.
 */
external class Device {
    val cordova: String
    val model: String
    val platform: String
    val uuid: String
    val version: String
    val manufacturer: String
    val isVirtual: Boolean
    val serial: String
}

/**
 * Pending result class.
 */
external class PendingResult {
    val pluginServiceName: String
    val pluginStatus: String
    val result: dynamic
}

/**
 * Resume event class.
 */
external class ResumeEvent {
    val action: String?
    val pendingResult: PendingResult?
}

/**
 * Cordova event types.
 */
enum class CordovaEvent(internal val type: String) {
    DEVICEREADY("deviceready"),
    PAUSE("pause"),
    RESUME("resume"),
    BACKBUTTON("backbutton"),
    MENUBUTTON("menubutton"),
    SEARCHBUTTON("searchbutton"),
    STARTCALLBUTTON("startcallbutton"),
    ENDCALLBUTTON("endcallbutton"),
    VOLUMEDOWNBUTTON("volumedownbutton"),
    VOLUMEUPBUTTON("volumeupbutton"),
    ACTIVATED("activated")
}

private external val device: Device

/**
 * Cordova device information object.
 */
var cordovaDevice: Device? = null
    private set

/**
 * Add listeners for 'deviceready' Cordova event.
 */
fun addDeviceReadyListener(listener: (Device) -> Unit) {
    document.addEventListener(CordovaEvent.DEVICEREADY.type, {
        listener(device)
    }, false)
}

/**
 * Add listeners for 'pause' Cordova event.
 */
fun addPauseListener(listener: () -> Unit) {
    addDeviceReadyListener {
        document.addEventListener(CordovaEvent.PAUSE.type, {
            listener()
        }, false)
    }
}

/**
 * Add listeners for 'resume' Cordova event.
 */
fun addResumeListener(listener: (ResumeEvent) -> Unit) {
    addDeviceReadyListener {
        document.addEventListener(CordovaEvent.RESUME.type, { e ->
            @Suppress("UnsafeCastFromDynamic")
            listener(e.asDynamic())
        }, false)
    }
}

/**
 * Add listeners for a Cordova events.
 */
fun addCordovaEventListener(event: CordovaEvent, listener: (Event) -> Unit) {
    addDeviceReadyListener {
        document.addEventListener(event.type, { e ->
            listener(e)
        }, false)
    }
}

/**
 * Suspending function to return device information object.
 */
suspend fun getDevice(): Device {
    return cordovaDevice ?: suspendCoroutine { continuation ->
        addDeviceReadyListener {
            cordovaDevice = device
            continuation.resume(device)
        }
    }
}
