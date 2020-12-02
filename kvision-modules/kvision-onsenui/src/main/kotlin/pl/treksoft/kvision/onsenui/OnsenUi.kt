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

package pl.treksoft.kvision.onsenui

import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.KVManagerOnsenui.ons
import pl.treksoft.kvision.core.Widget

/**
 * Floating directions.
 */
enum class FloatDirection(internal val type: String) {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right")
}

/**
 * Floating positions.
 */
enum class FloatPosition(internal val type: String) {
    TOP_LEFT("top left"),
    TOP_RIGHT("top right"),
    BOTTOM_LEFT("bottom left"),
    BOTTOM_RIGHT("bottom right")
}

/**
 * Grid row and column vertical align.
 */
enum class GridVerticalAlign(internal val type: String) {
    TOP("top"),
    BOTTOM("bottom"),
    CENTER("center")
}

/**
 * Back button event type.
 */
external interface BackButtonEvent {
    fun callParentHandler()
}

/**
 * Change orientation event event type.
 */
external interface OrientationEvent {
    val isPortrait: Boolean
}

/**
 * Platform types.
 */
enum class Platform(internal val type: String) {
    OPERA("opera"),
    FIREFOX("firefox"),
    SAFARI("safari"),
    CHROME("chrome"),
    IE("ie"),
    EDGE("edge"),
    ANDROID("android"),
    BLACKBERRY("blackberry"),
    IOS("ios"),
    WP("wp")
}

/**
 * Contains global Onsen UI functions.
 */
@Suppress("UnsafeCastFromDynamic")
object OnsenUi {

    /**
     * Whether OnsenUI engine is loaded and ready.
     */
    fun isReady(): Boolean {
        return ons.isReady()
    }

    /**
     * Whether app is running inside Cordova.
     */
    fun isWebView(): Boolean {
        return ons.isWebView()
    }

    /**
     * A function called when engine is loaded and ready.
     */
    fun ready(callback: () -> Unit) {
        ons.ready(callback)
    }

    /**
     * Set default listener for the device back button event.
     */
    fun setDefaultDeviceBackButtonListener(listener: (event: dynamic) -> Unit) {
        ons.setDefaultDeviceBackButtonListener(listener)
    }

    /**
     * Disable default handler for the device back button.
     */
    fun disableDeviceBackButtonHandler() {
        ons.disableDeviceBackButtonHandler()
    }

    /**
     * Enable default handler for the device back button.
     */
    fun enableDeviceBackButtonHandler() {
        ons.enableDeviceBackButtonHandler()
    }

    /**
     * Enable status bar fill on IOS7 and above (except iPhone X)
     */
    fun enableAutoStatusBarFill() {
        ons.enableAutoStatusBarFill()
    }

    /**
     * Disable status bar fill on IOS7 and above (except iPhone X)
     */
    fun disableAutoStatusBarFill() {
        ons.disableAutoStatusBarFill()
    }

    /**
     * Creates a static element similar to iOS/Android status bar.
     */
    fun mockStatusBar() {
        ons.mockStatusBar()
    }

    /**
     * Disable all animations.
     */
    fun disableAnimations() {
        ons.disableAnimations()
    }

    /**
     * Enable all animations.
     */
    fun enableAnimations() {
        ons.enableAnimations()
    }

    /**
     * Disable automatic styling.
     */
    fun disableAutoStyling() {
        ons.disableAutoStyling()
    }

    /**
     * Enable automatic styling.
     */
    fun enableAutoStyling() {
        ons.enableAutoStyling()
    }

    /**
     * Disable adding fa- prefix for icons.
     */
    fun disableIconAutoPrefix() {
        ons.disableIconAutoPrefix()
    }

    /**
     * Applies a scroll fix for iOS UIWebView.
     */
    fun forceUIWebViewScrollFix(force: Boolean) {
        ons.forceUIWebViewScrollFix(force)
    }

    /**
     * Force styling for the given platform.
     */
    fun forcePlatformStyling(platform: Platform) {
        ons.forcePlatformStyling(platform.type)
    }

    /**
     * Set styling for the given platform (preferred)
     */
    fun platformSelect(platform: Platform) {
        ons.platform.select(platform.type)
    }

    /**
     * Whether device is iPhone.
     * @param forceActualPlatform return the actual platform.
     */
    fun isIOS(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isIOS(forceActualPlatform)
    }

    /**
     * Whether device is Android.
     * @param forceActualPlatform return the actual platform.
     */
    fun isAndroid(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isAndroid(forceActualPlatform)
    }

    /**
     * Whether device is Android phone.
     */
    fun isAndroidPhone(): Boolean {
        return ons.platform.isAndroidPhone()
    }

    /**
     * Whether device is Android tablet.
     */
    fun isAndroidTablet(): Boolean {
        return ons.platform.isAndroidTablet()
    }

    /**
     * Whether device is UIWebView.
     */
    fun isUIWebView(): Boolean {
        return ons.platform.isUIWebView()
    }

    /**
     * Whether device is iOS Safari.
     */
    fun isIOSSafari(): Boolean {
        return ons.platform.isIOSSafari()
    }

    /**
     * Whether device is WKWebView.
     */
    fun isWKWebView(): Boolean {
        return ons.platform.isWKWebView()
    }

    /**
     * Whether device is iPhone.
     */
    fun isIPhone(): Boolean {
        return ons.platform.isIPhone()
    }

    /**
     * Whether device is iPhone X, XS, XS Max, or XR.
     */
    fun isIPhoneX(): Boolean {
        return ons.platform.isIPhoneX()
    }

    /**
     * Whether device is iPad.
     */
    fun isIPad(): Boolean {
        return ons.platform.isIPad()
    }

    /**
     * Whether device is BlackBerry.
     * @param forceActualPlatform return the actual platform.
     */
    fun isBlackBerry(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isBlackBerry(forceActualPlatform)
    }

    /**
     * Whether browser is Opera.
     * @param forceActualPlatform return the actual platform.
     */
    fun isOpera(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isOpera(forceActualPlatform)
    }

    /**
     * Whether browser is Firefox.
     * @param forceActualPlatform return the actual platform.
     */
    fun isFirefox(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isFirefox(forceActualPlatform)
    }

    /**
     * Whether browser is Safari.
     * @param forceActualPlatform return the actual platform.
     */
    fun isSafari(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isSafari(forceActualPlatform)
    }

    /**
     * Whether browser is Chrome.
     * @param forceActualPlatform return the actual platform.
     */
    fun isChrome(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isChrome(forceActualPlatform)
    }

    /**
     * Whether browser is IE.
     * @param forceActualPlatform return the actual platform.
     */
    fun isIE(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isIE(forceActualPlatform)
    }

    /**
     * Whether device is iOS 7 or above.
     */
    fun isIOS7above(): Boolean {
        return ons.platform.isIOS7above()
    }

    /**
     * Whether browser is Edge.
     * @param forceActualPlatform return the actual platform.
     */
    fun isEdge(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isEdge(forceActualPlatform)
    }

    /**
     * Whether device is Windows Phone.
     * @param forceActualPlatform return the actual platform.
     */
    fun isWP(forceActualPlatform: Boolean = false): Boolean {
        return ons.platform.isWP(forceActualPlatform)
    }

    /**
     * Function called on orientation change event.
     */
    fun onOrientationChange(callback: (OrientationEvent) -> Unit) {
        ons.orientation.on("change", callback)
    }

    /**
     * Function called on a single orientation change event.
     */
    fun onOrientationChangeOnce(callback: (OrientationEvent) -> Unit) {
        ons.orientation.once("change", callback)
    }

    /**
     * Remove orientation change event listener/all listeners.
     */
    fun offOrientationChange(callback: ((OrientationEvent) -> Unit)? = undefined) {
        ons.orientation.off("change", callback)
    }

    /**
     * Whether orientation is portrait.
     */
    fun isPortrait(): Boolean {
        return ons.orientation.isPortrait()
    }

    /**
     * Whether orientation is landscape.
     */
    fun isLandscape(): Boolean {
        return ons.orientation.isLandscape()
    }

    /**
     * Create HTML element from a given template.
     * @param template a HTML template string
     */
    fun createElement(template: String): HTMLElement {
        return ons.createElement(template)
    }
}

/**
 * Enable gesture detector for a given Widget.
 */
@Suppress("UnsafeCastFromDynamic")
fun Widget.enableGestureDetector() {
    if (this.getElement() != null) {
        ons.GestureDetector(this.getElement())
    } else {
        this.addAfterInsertHook {
            ons.GestureDetector(it.elm)
        }
    }
}
