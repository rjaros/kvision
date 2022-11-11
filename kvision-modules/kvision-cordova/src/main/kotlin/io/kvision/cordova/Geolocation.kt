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

import io.kvision.utils.obj
import kotlinx.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Geolocation coordinates values.
 */
external class Coordinates {
    val latitude: Number
    val longitude: Number
    val altitude: Number
    val accuracy: Number
    val altitudeAccuracy: Number?
    val heading: Number?
    val speed: Number
}

/**
 * Geolocation position value.
 */
external class Position {
    val coords: Coordinates
    val timestamp: Number
}

/**
 * Geolocaton error codes.
 */
enum class PositionError {
    PERMISSION_DENIED,
    POSITION_UNAVAILABLE,
    TIMEOUT
}

private fun codeToEnum(code: Int): PositionError {
    return when (code) {
        1 -> PositionError.PERMISSION_DENIED
        2 -> PositionError.POSITION_UNAVAILABLE
        else -> PositionError.TIMEOUT
    }
}

/**
 * Exception class for geolocation errors.
 */
class GeolocationException(val code: PositionError, message: String) : Exception(message)

/**
 * Main geolocation object based on webview api.
 */
object Geolocation {

    /**
     * Get current location.
     * @param enableHighAccuracy provides a hint that the application needs the best possible results
     * @param timeout the maximum length of time (milliseconds) that is allowed to pass before getting the result
     * @param maximumAge accept a cached position whose age is no greater than the specified time in milliseconds
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun getCurrentPosition(
        enableHighAccuracy: Boolean = true,
        timeout: Number? = null,
        maximumAge: Number? = null
    ): Result<Position, GeolocationException> {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                window.navigator.asDynamic().geolocation.getCurrentPosition({ position: Position ->
                    continuation.resume(Result.success(position))
                }, { error ->
                    continuation.resume(Result.error(GeolocationException(codeToEnum(error.code), error.message)))
                }, obj {
                    this.enableHighAccuracy = enableHighAccuracy
                    if (timeout != null) this.timeout = timeout
                    if (maximumAge != null) this.maximumAge = maximumAge
                })
            }
        }
    }

    /**
     * Watch location changes.
     * @param enableHighAccuracy provides a hint that the application needs the best possible results
     * @param timeout the maximum length of time (milliseconds) that is allowed to pass before getting the result
     * @param maximumAge accept a cached position whose age is no greater than the specified time in milliseconds
     * @param resultCallback a callback function receiving the result
     * @return watch identifier (can be removed with a [clearWatch] function)
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun watchPosition(
        enableHighAccuracy: Boolean = true,
        timeout: Number? = null,
        maximumAge: Number? = null,
        resultCallback: (Result<Position, GeolocationException>) -> Unit
    ): String? {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                val watchId = window.navigator.asDynamic().geolocation.watchPosition({ position: Position ->
                    resultCallback(Result.success(position))
                }, { error ->
                    resultCallback(Result.error(GeolocationException(codeToEnum(error.code), error.message)))
                }, obj {
                    this.enableHighAccuracy = enableHighAccuracy
                    if (timeout != null) this.timeout = timeout
                    if (maximumAge != null) this.maximumAge = maximumAge
                })
                continuation.resume(watchId)
            }
        }
    }

    /**
     * Clear the given watch.
     * @param watchId watch identifier returned from [watchPosition] function
     */
    fun clearWatch(watchId: String) {
        if (window.navigator.asDynamic().geolocation != null) {
            window.navigator.asDynamic().geolocation.clearWatch(watchId)
        }
    }

}

/**
 * Main geolocation object based on Google location services api.
 */
object Locationservices {

    /**
     * Location services priority.
     */
    enum class Priority {
        PRIORITY_HIGH_ACCURACY,
        PRIORITY_BALANCED_POWER_ACCURACY,
        PRIORITY_LOW_POWER,
        PRIORITY_NO_POWER
    }

    /**
     * Get current location.
     * @param enableHighAccuracy provides a hint that the application needs the best possible results
     * @param timeout the maximum length of time (milliseconds) that is allowed to pass before getting the result
     * @param maximumAge accept a cached position whose age is no greater than the specified time in milliseconds
     * @param priority the priority of the request is a strong hint for which location sources to use
     * @param interval set the desired interval for active location updates, in milliseconds
     * @param fastInterval explicitly set the fastest interval for location updates, in milliseconds
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun getCurrentPosition(
        enableHighAccuracy: Boolean = true,
        timeout: Number? = null,
        maximumAge: Number? = null,
        priority: Priority? = null,
        interval: Number? = null,
        fastInterval: Number? = null
    ): Result<Position, GeolocationException> {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                window.asDynamic()
                    .cordova.plugins.locationServices.geolocation.getCurrentPosition({ position: Position ->
                    continuation.resume(Result.success(position))
                }, { error ->
                    continuation.resume(Result.error(GeolocationException(codeToEnum(error.code), error.message)))
                }, obj {
                    this.enableHighAccuracy = enableHighAccuracy
                    if (timeout != null) this.timeout = timeout
                    if (maximumAge != null) this.maximumAge = maximumAge
                    if (priority != null) this.priority = getJsPriority(priority)
                    if (interval != null) this.interval = interval
                    if (fastInterval != null) this.fastInterval = fastInterval
                })
            }
        }
    }

    /**
     * Watch location changes.
     * @param enableHighAccuracy provides a hint that the application needs the best possible results
     * @param timeout the maximum length of time (milliseconds) that is allowed to pass before getting the result
     * @param maximumAge accept a cached position whose age is no greater than the specified time in milliseconds
     * @param priority the priority of the request is a strong hint for which location sources to use
     * @param interval set the desired interval for active location updates, in milliseconds
     * @param fastInterval explicitly set the fastest interval for location updates, in milliseconds
     * @param resultCallback a callback function receiving the result
     * @return watch identifier (can be removed with a [clearWatch] function)
     */
    @Suppress("UnsafeCastFromDynamic")
    suspend fun watchPosition(
        enableHighAccuracy: Boolean = true,
        timeout: Number? = null,
        maximumAge: Number? = null,
        priority: Priority? = null,
        interval: Number? = null,
        fastInterval: Number? = null,
        resultCallback: (Result<Position, GeolocationException>) -> Unit
    ): String? {
        return suspendCoroutine { continuation ->
            addDeviceReadyListener {
                val watchId =
                    window.asDynamic()
                        .cordova.plugins.locationServices.geolocation.watchPosition({ position: Position ->
                        resultCallback(Result.success(position))
                    }, { error ->
                        resultCallback(Result.error(GeolocationException(codeToEnum(error.code), error.message)))
                    }, obj {
                        this.enableHighAccuracy = enableHighAccuracy
                        if (timeout != null) this.timeout = timeout
                        if (maximumAge != null) this.maximumAge = maximumAge
                        if (priority != null) this.priority = getJsPriority(priority)
                        if (interval != null) this.interval = interval
                        if (fastInterval != null) this.fastInterval = fastInterval
                    })
                continuation.resume(watchId)
            }
        }
    }

    /**
     * Clear the given watch.
     * @param watchId watch identifier returned from [watchPosition] function
     */
    fun clearWatch(watchId: String) {
        if (window.asDynamic().cordova.plugins.locationServices.geolocation != null) {
            window.asDynamic().cordova.plugins.locationServices.geolocation.clearWatch(watchId)
        }
    }

    @Suppress("MagicNumber")
    private fun getJsPriority(priority: Priority): dynamic {
        return when (priority) {
            Priority.PRIORITY_HIGH_ACCURACY -> 100
            Priority.PRIORITY_BALANCED_POWER_ACCURACY -> 102
            Priority.PRIORITY_LOW_POWER -> 104
            Priority.PRIORITY_NO_POWER -> 105
        }
    }

}
