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
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
    "UNUSED", "PropertyName", "TooManyFunctions", "VariableNaming", "MaxLineLength", "LargeClass"
)
@file:JsModule("electron")
@file:JsNonModule

package pl.treksoft.kvision.electron

import node.ReadableStream
import node.buffer.Buffer
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.EventListener
import kotlin.js.Date
import kotlin.js.Promise

external interface EventListenerObject {
    fun handleEvent(evt: Event)
}

open external class EventEmitter {
    open fun addListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun on(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun once(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun removeListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun removeAllListeners(event: String? = definedExternally /* null */): EventEmitter /* this */ =
        definedExternally

    open fun setMaxListeners(n: Number): EventEmitter /* this */ = definedExternally
    open fun getMaxListeners(): Number = definedExternally
    open fun listeners(event: String): Array<Function<*>> = definedExternally
    open fun emit(event: String, vararg args: Any): Boolean = definedExternally
    open fun listenerCount(type: String): Number = definedExternally
    open fun prependListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun prependOnceListener(event: String, listener: Function<*>): EventEmitter /* this */ = definedExternally
    open fun eventNames(): Array<String> = definedExternally
}

open external class Accelerator
external interface Event {
    var preventDefault: () -> Unit
    var sender: WebContents
    var returnValue: Any
    var ctrlKey: Boolean? get() = definedExternally; set(value) = definedExternally
    var metaKey: Boolean? get() = definedExternally; set(value) = definedExternally
    var shiftKey: Boolean? get() = definedExternally; set(value) = definedExternally
    var altKey: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface CommonInterface {
    var clipboard: Clipboard
    var crashReporter: CrashReporter
    var nativeImage: Any?
    var screen: Screen
    var shell: Shell
}

external interface MainInterface : CommonInterface {
    var app: App
    var autoUpdater: AutoUpdater
    var BrowserView: Any?
    var BrowserWindow: Any?
    var ClientRequest: Any?
    var contentTracing: ContentTracing
    var Cookies: Any?
    var Debugger: Any?
    var dialog: Dialog
    var DownloadItem: Any?
    var globalShortcut: GlobalShortcut
    var inAppPurchase: InAppPurchase
    var IncomingMessage: Any?
    var ipcMain: IpcMain
    var Menu: Any?
    var MenuItem: Any?
    var net: Net
    var Notification: Any?
    var powerMonitor: PowerMonitor
    var powerSaveBlocker: PowerSaveBlocker
    var protocol: Protocol
    var session: Any?
    var systemPreferences: SystemPreferences
    var TouchBar: Any?
    var Tray: Any?
    var webContents: Any?
    var WebRequest: Any?
}

external interface RendererInterface : CommonInterface {
    var BrowserWindowProxy: Any?
    var desktopCapturer: DesktopCapturer
    var ipcRenderer: IpcRenderer
    var remote: Remote
    var webFrame: WebFrame
    var webviewTag: WebviewTag
}

external interface AllElectron : MainInterface,
    RendererInterface

external var app: App = definedExternally
external var autoUpdater: AutoUpdater = definedExternally
external var clipboard: Clipboard = definedExternally
external var contentTracing: ContentTracing = definedExternally
external var crashReporter: CrashReporter = definedExternally
external var desktopCapturer: DesktopCapturer = definedExternally
external var dialog: Dialog = definedExternally
external var globalShortcut: GlobalShortcut = definedExternally
external var inAppPurchase: InAppPurchase = definedExternally
external var ipcMain: IpcMain = definedExternally
external var ipcRenderer: IpcRenderer = definedExternally
external var nativeImage: Any? = definedExternally
external var net: Net = definedExternally
external var powerMonitor: PowerMonitor = definedExternally
external var powerSaveBlocker: PowerSaveBlocker = definedExternally
external var protocol: Protocol = definedExternally
external var remote: Remote = definedExternally
external var screen: Screen = definedExternally
external var session: Any? = definedExternally
external var shell: Shell = definedExternally
external var systemPreferences: SystemPreferences = definedExternally
external var webContents: Any? = definedExternally
external var webFrame: WebFrame = definedExternally
external var webviewTag: WebviewTag = definedExternally

external interface App : EventEmitter {
    fun on(
        event: String /* "accessibility-support-changed" */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun once(
        event: String /* "accessibility-support-changed" */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "accessibility-support-changed" */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "accessibility-support-changed" */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun on(event: String /* "activate" */, listener: (event: Event, hasVisibleWindows: Boolean) -> Unit): App /* this */
    fun once(
        event: String /* "activate" */,
        listener: (event: Event, hasVisibleWindows: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "activate" */,
        listener: (event: Event, hasVisibleWindows: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "activate" */,
        listener: (event: Event, hasVisibleWindows: Boolean) -> Unit
    ): App /* this */

    fun on(
        event: String /* "activity-was-continued" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun once(
        event: String /* "activity-was-continued" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "activity-was-continued" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "activity-was-continued" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun on(event: String /* "before-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun once(event: String /* "before-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun addListener(event: String /* "before-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun removeListener(event: String /* "before-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun on(
        event: String /* "browser-window-blur" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun once(
        event: String /* "browser-window-blur" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "browser-window-blur" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "browser-window-blur" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun on(
        event: String /* "browser-window-created" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun once(
        event: String /* "browser-window-created" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "browser-window-created" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "browser-window-created" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun on(
        event: String /* "browser-window-focus" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun once(
        event: String /* "browser-window-focus" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "browser-window-focus" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "browser-window-focus" */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun on(
        event: String /* "certificate-error" */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun once(
        event: String /* "certificate-error" */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "certificate-error" */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "certificate-error" */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun on(
        event: String /* "continue-activity" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun once(
        event: String /* "continue-activity" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "continue-activity" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "continue-activity" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun on(
        event: String /* "continue-activity-error" */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun once(
        event: String /* "continue-activity-error" */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "continue-activity-error" */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "continue-activity-error" */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun on(event: String /* "gpu-process-crashed" */, listener: (event: Event, killed: Boolean) -> Unit): App /* this */
    fun once(
        event: String /* "gpu-process-crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "gpu-process-crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "gpu-process-crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): App /* this */

    fun on(
        event: String /* "login" */,
        listener: (event: Event, webContents: WebContents, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun once(
        event: String /* "login" */,
        listener: (event: Event, webContents: WebContents, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "login" */,
        listener: (event: Event, webContents: WebContents, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "login" */,
        listener: (event: Event, webContents: WebContents, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun on(event: String /* "new-window-for-tab" */, listener: (event: Event) -> Unit): App /* this */
    fun once(event: String /* "new-window-for-tab" */, listener: (event: Event) -> Unit): App /* this */
    fun addListener(event: String /* "new-window-for-tab" */, listener: (event: Event) -> Unit): App /* this */
    fun removeListener(event: String /* "new-window-for-tab" */, listener: (event: Event) -> Unit): App /* this */
    fun on(event: String /* "open-file" */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun once(event: String /* "open-file" */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun addListener(event: String /* "open-file" */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun removeListener(event: String /* "open-file" */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun on(event: String /* "open-url" */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun once(event: String /* "open-url" */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun addListener(event: String /* "open-url" */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun removeListener(event: String /* "open-url" */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun on(event: String /* "quit" */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun once(event: String /* "quit" */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun addListener(event: String /* "quit" */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun removeListener(event: String /* "quit" */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun on(event: String /* "ready" */, listener: (launchInfo: Any) -> Unit): App /* this */
    fun once(event: String /* "ready" */, listener: (launchInfo: Any) -> Unit): App /* this */
    fun addListener(event: String /* "ready" */, listener: (launchInfo: Any) -> Unit): App /* this */
    fun removeListener(event: String /* "ready" */, listener: (launchInfo: Any) -> Unit): App /* this */
    fun on(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate? /*= null*/) -> Unit) -> Unit
    ): App /* this */

    fun once(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate? /*= null*/) -> Unit) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate? /*= null*/) -> Unit) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate? /*= null*/) -> Unit) -> Unit
    ): App /* this */

    fun on(
        event: String /* "update-activity-state" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun once(
        event: String /* "update-activity-state" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "update-activity-state" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "update-activity-state" */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun on(
        event: String /* "web-contents-created" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun once(
        event: String /* "web-contents-created" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "web-contents-created" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "web-contents-created" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun on(event: String /* "will-continue-activity" */, listener: (event: Event, type: String) -> Unit): App /* this */
    fun once(
        event: String /* "will-continue-activity" */,
        listener: (event: Event, type: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* "will-continue-activity" */,
        listener: (event: Event, type: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* "will-continue-activity" */,
        listener: (event: Event, type: String) -> Unit
    ): App /* this */

    override fun on(event: String /* "will-finish-launching" */, listener: Function<*>): App /* this */
    override fun once(event: String /* "will-finish-launching" */, listener: Function<*>): App /* this */
    override fun addListener(event: String /* "will-finish-launching" */, listener: Function<*>): App /* this */
    override fun removeListener(event: String /* "will-finish-launching" */, listener: Function<*>): App /* this */
    fun on(event: String /* "will-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun once(event: String /* "will-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun addListener(event: String /* "will-quit" */, listener: (event: Event) -> Unit): App /* this */
    fun removeListener(event: String /* "will-quit" */, listener: (event: Event) -> Unit): App /* this */
    override fun on(event: String /* "window-all-closed" */, listener: Function<*>): App /* this */
    override fun once(event: String /* "window-all-closed" */, listener: Function<*>): App /* this */
    override fun addListener(event: String /* "window-all-closed" */, listener: Function<*>): App /* this */
    override fun removeListener(event: String /* "window-all-closed" */, listener: Function<*>): App /* this */
    fun addRecentDocument(path: String)
    fun clearRecentDocuments()
    fun disableDomainBlockingFor3DAPIs()
    fun disableHardwareAcceleration()
    fun enableMixedSandbox()
    fun exit(exitCode: Number? = definedExternally /* null */)
    fun focus()
    fun getAppMetrics(): Array<ProcessMetric>
    fun getAppPath(): String
    fun getBadgeCount(): Number
    fun getCurrentActivityType(): String
    fun getFileIcon(path: String, callback: (error: Error, icon: NativeImage) -> Unit)
    fun getFileIcon(path: String, options: FileIconOptions, callback: (error: Error, icon: NativeImage) -> Unit)
    fun getGPUFeatureStatus(): GPUFeatureStatus
    fun getJumpListSettings(): JumpListSettings
    fun getLocale(): String
    fun getLoginItemSettings(options: LoginItemSettingsOptions? = definedExternally /* null */): LoginItemSettings
    fun getName(): String
    fun getPath(name: String): String
    fun getVersion(): String
    fun hide()
    fun importCertificate(options: ImportCertificateOptions, callback: (result: Number) -> Unit)
    fun invalidateCurrentActivity(type: String)
    fun isAccessibilitySupportEnabled(): Boolean
    fun isDefaultProtocolClient(
        protocol: String,
        path: String? = definedExternally /* null */,
        args: Array<String>? = definedExternally /* null */
    ): Boolean

    fun isInApplicationsFolder(): Boolean
    fun isReady(): Boolean
    fun isUnityRunning(): Boolean
    fun makeSingleInstance(callback: (argv: Array<String>, workingDirectory: String) -> Unit): Boolean
    fun moveToApplicationsFolder(): Boolean
    fun quit()
    fun relaunch(options: RelaunchOptions? = definedExternally /* null */)
    fun releaseSingleInstance()
    fun removeAsDefaultProtocolClient(
        protocol: String,
        path: String? = definedExternally /* null */,
        args: Array<String>? = definedExternally /* null */
    ): Boolean

    fun setAboutPanelOptions(options: AboutPanelOptionsOptions)
    fun setAccessibilitySupportEnabled(enabled: Boolean)
    fun setAppUserModelId(id: String)
    fun setAsDefaultProtocolClient(
        protocol: String,
        path: String? = definedExternally /* null */,
        args: Array<String>? = definedExternally /* null */
    ): Boolean

    fun setBadgeCount(count: Number): Boolean
    fun setJumpList(categories: Array<JumpListCategory>)
    fun setLoginItemSettings(settings: Settings)
    fun setName(name: String)
    fun setPath(name: String, path: String)
    fun setUserActivity(type: String, userInfo: Any, webpageURL: String? = definedExternally /* null */)
    fun setUserTasks(tasks: Array<Task>): Boolean
    fun show()
    fun startAccessingSecurityScopedResource(bookmarkData: String): Function<*>
    fun updateCurrentActivity(type: String, userInfo: Any)
    var commandLine: CommandLine
    var dock: Dock
}

external interface AutoUpdater : EventEmitter {
    override fun on(event: String /* "checking-for-update" */, listener: Function<*>): AutoUpdater /* this */
    override fun once(event: String /* "checking-for-update" */, listener: Function<*>): AutoUpdater /* this */
    override fun addListener(event: String /* "checking-for-update" */, listener: Function<*>): AutoUpdater /* this */
    override fun removeListener(
        event: String /* "checking-for-update" */,
        listener: Function<*>
    ): AutoUpdater /* this */

    fun on(event: String /* "error" */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun once(event: String /* "error" */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun addListener(event: String /* "error" */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun removeListener(event: String /* "error" */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    override fun on(event: String /* "update-available" */, listener: Function<*>): AutoUpdater /* this */
    override fun once(event: String /* "update-available" */, listener: Function<*>): AutoUpdater /* this */
    override fun addListener(event: String /* "update-available" */, listener: Function<*>): AutoUpdater /* this */
    override fun removeListener(event: String /* "update-available" */, listener: Function<*>): AutoUpdater /* this */
    fun on(
        event: String /* "update-downloaded" */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    fun once(
        event: String /* "update-downloaded" */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    fun addListener(
        event: String /* "update-downloaded" */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    fun removeListener(
        event: String /* "update-downloaded" */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    override fun on(event: String /* "update-not-available" */, listener: Function<*>): AutoUpdater /* this */
    override fun once(event: String /* "update-not-available" */, listener: Function<*>): AutoUpdater /* this */
    override fun addListener(event: String /* "update-not-available" */, listener: Function<*>): AutoUpdater /* this */
    override fun removeListener(
        event: String /* "update-not-available" */,
        listener: Function<*>
    ): AutoUpdater /* this */

    fun checkForUpdates()
    fun getFeedURL(): String
    fun quitAndInstall()
    fun setFeedURL(options: FeedURLOptions)
}

external interface BluetoothDevice {
    var deviceId: String
    var deviceName: String
}

open external class BrowserView(options: BrowserViewConstructorOptions? = definedExternally /* null */) :
    EventEmitter {
    open fun destroy(): Unit = definedExternally
    open fun isDestroyed(): Boolean = definedExternally
    open fun setAutoResize(options: AutoResizeOptions): Unit = definedExternally
    open fun setBackgroundColor(color: String): Unit = definedExternally
    open fun setBounds(bounds: Rectangle): Unit = definedExternally
    open var id: Number = definedExternally
    open var webContents: WebContents = definedExternally

    companion object {
        fun fromId(id: Number): BrowserView = definedExternally
        fun fromWebContents(webContents: WebContents): BrowserView? = definedExternally
        fun getAllViews(): Array<BrowserView> = definedExternally
    }
}

open external class BrowserWindow(options: BrowserWindowConstructorOptions? = definedExternally /* null */) :
    EventEmitter {
    open fun on(
        event: String /* "app-command" */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun once(
        event: String /* "app-command" */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun addListener(
        event: String /* "app-command" */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun removeListener(
        event: String /* "app-command" */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "blur" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "blur" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "blur" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "blur" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    open fun on(event: String /* "close" */, listener: (event: Event) -> Unit): BrowserWindow /* this */ =
        definedExternally

    open fun once(event: String /* "close" */, listener: (event: Event) -> Unit): BrowserWindow /* this */ =
        definedExternally

    open fun addListener(event: String /* "close" */, listener: (event: Event) -> Unit): BrowserWindow /* this */ =
        definedExternally

    open fun removeListener(event: String /* "close" */, listener: (event: Event) -> Unit): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "closed" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "closed" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "closed" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "closed" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "enter-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "enter-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "enter-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(
        event: String /* "enter-full-screen" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "enter-html-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "enter-html-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(
        event: String /* "enter-html-full-screen" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun removeListener(
        event: String /* "enter-html-full-screen" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "focus" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "focus" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "focus" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "focus" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "hide" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "hide" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "hide" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "hide" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "leave-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "leave-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "leave-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(
        event: String /* "leave-full-screen" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "leave-html-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "leave-html-full-screen" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(
        event: String /* "leave-html-full-screen" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun removeListener(
        event: String /* "leave-html-full-screen" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "maximize" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "maximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "maximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "maximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "minimize" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "minimize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "minimize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "minimize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "move" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "move" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "move" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "move" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "moved" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "moved" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "moved" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "moved" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "new-window-for-tab" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "new-window-for-tab" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(
        event: String /* "new-window-for-tab" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun removeListener(
        event: String /* "new-window-for-tab" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    open fun on(
        event: String /* "page-title-updated" */,
        listener: (event: Event, title: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun once(
        event: String /* "page-title-updated" */,
        listener: (event: Event, title: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun addListener(
        event: String /* "page-title-updated" */,
        listener: (event: Event, title: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun removeListener(
        event: String /* "page-title-updated" */,
        listener: (event: Event, title: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "ready-to-show" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "ready-to-show" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "ready-to-show" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "ready-to-show" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "resize" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "resize" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "resize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "resize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "responsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "responsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "responsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "responsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "restore" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "restore" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "restore" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "restore" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "scroll-touch-begin" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "scroll-touch-begin" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(
        event: String /* "scroll-touch-begin" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun removeListener(
        event: String /* "scroll-touch-begin" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "scroll-touch-edge" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "scroll-touch-edge" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "scroll-touch-edge" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(
        event: String /* "scroll-touch-edge" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "scroll-touch-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "scroll-touch-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "scroll-touch-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(
        event: String /* "scroll-touch-end" */,
        listener: Function<*>
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "session-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "session-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "session-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "session-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "sheet-begin" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "sheet-begin" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "sheet-begin" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "sheet-begin" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "sheet-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "sheet-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "sheet-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "sheet-end" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "show" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun once(event: String /* "show" */, listener: Function<*>): BrowserWindow /* this */ = definedExternally
    override fun addListener(event: String /* "show" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "show" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    open fun on(
        event: String /* "swipe" */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun once(
        event: String /* "swipe" */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun addListener(
        event: String /* "swipe" */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    open fun removeListener(
        event: String /* "swipe" */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */ = definedExternally

    override fun on(event: String /* "unmaximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "unmaximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "unmaximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "unmaximize" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun on(event: String /* "unresponsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun once(event: String /* "unresponsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun addListener(event: String /* "unresponsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    override fun removeListener(event: String /* "unresponsive" */, listener: Function<*>): BrowserWindow /* this */ =
        definedExternally

    open fun addTabbedWindow(browserWindow: BrowserWindow): Unit = definedExternally
    open fun blur(): Unit = definedExternally
    open fun blurWebView(): Unit = definedExternally
    open fun capturePage(callback: (image: NativeImage) -> Unit): Unit = definedExternally
    open fun capturePage(rect: Rectangle, callback: (image: NativeImage) -> Unit): Unit = definedExternally
    open fun center(): Unit = definedExternally
    open fun close(): Unit = definedExternally
    open fun closeFilePreview(): Unit = definedExternally
    open fun destroy(): Unit = definedExternally
    open fun flashFrame(flag: Boolean): Unit = definedExternally
    open fun focus(): Unit = definedExternally
    open fun focusOnWebView(): Unit = definedExternally
    open fun getBounds(): Rectangle = definedExternally
    open fun getBrowserView(): BrowserView? = definedExternally
    open fun getChildWindows(): Array<BrowserWindow> = definedExternally
    open fun getContentBounds(): Rectangle = definedExternally
    open fun getContentSize(): Array<Number> = definedExternally
    open fun getMaximumSize(): Array<Number> = definedExternally
    open fun getMinimumSize(): Array<Number> = definedExternally
    open fun getNativeWindowHandle(): Buffer = definedExternally
    open fun getOpacity(): Number = definedExternally
    open fun getParentWindow(): BrowserWindow = definedExternally
    open fun getPosition(): Array<Number> = definedExternally
    open fun getRepresentedFilename(): String = definedExternally
    open fun getSize(): Array<Number> = definedExternally
    open fun getTitle(): String = definedExternally
    open fun hasShadow(): Boolean = definedExternally
    open fun hide(): Unit = definedExternally
    open fun hookWindowMessage(message: Number, callback: Function<*>): Unit = definedExternally
    open fun isAlwaysOnTop(): Boolean = definedExternally
    open fun isClosable(): Boolean = definedExternally
    open fun isDestroyed(): Boolean = definedExternally
    open fun isDocumentEdited(): Boolean = definedExternally
    open fun isFocused(): Boolean = definedExternally
    open fun isFullScreen(): Boolean = definedExternally
    open fun isFullScreenable(): Boolean = definedExternally
    open fun isKiosk(): Boolean = definedExternally
    open fun isMaximizable(): Boolean = definedExternally
    open fun isMaximized(): Boolean = definedExternally
    open fun isMenuBarAutoHide(): Boolean = definedExternally
    open fun isMenuBarVisible(): Boolean = definedExternally
    open fun isMinimizable(): Boolean = definedExternally
    open fun isMinimized(): Boolean = definedExternally
    open fun isModal(): Boolean = definedExternally
    open fun isMovable(): Boolean = definedExternally
    open fun isResizable(): Boolean = definedExternally
    open fun isSimpleFullScreen(): Boolean = definedExternally
    open fun isVisible(): Boolean = definedExternally
    open fun isVisibleOnAllWorkspaces(): Boolean = definedExternally
    open fun isWindowMessageHooked(message: Number): Boolean = definedExternally
    open fun loadFile(filePath: String): Unit = definedExternally
    open fun loadURL(url: String, options: LoadURLOptions? = definedExternally /* null */): Unit = definedExternally
    open fun maximize(): Unit = definedExternally
    open fun mergeAllWindows(): Unit = definedExternally
    open fun minimize(): Unit = definedExternally
    open fun moveTabToNewWindow(): Unit = definedExternally
    open fun previewFile(path: String, displayName: String? = definedExternally /* null */): Unit = definedExternally
    open fun reload(): Unit = definedExternally
    open fun restore(): Unit = definedExternally
    open fun selectNextTab(): Unit = definedExternally
    open fun selectPreviousTab(): Unit = definedExternally
    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "normal" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "floating" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "torn-off-menu" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "modal-panel" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "main-menu" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "status" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "pop-up-menu" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String? /* "screen-saver" */ = definedExternally /* null */,
        relativeLevel: Number? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setAppDetails(options: AppDetailsOptions): Unit = definedExternally
    open fun setAspectRatio(aspectRatio: Number, extraSize: Size): Unit = definedExternally
    open fun setAutoHideCursor(autoHide: Boolean): Unit = definedExternally
    open fun setAutoHideMenuBar(hide: Boolean): Unit = definedExternally
    open fun setBounds(bounds: Rectangle, animate: Boolean? = definedExternally /* null */): Unit = definedExternally
    open fun setBrowserView(browserView: BrowserView): Unit = definedExternally
    open fun setClosable(closable: Boolean): Unit = definedExternally
    open fun setContentBounds(bounds: Rectangle, animate: Boolean? = definedExternally /* null */): Unit =
        definedExternally

    open fun setContentProtection(enable: Boolean): Unit = definedExternally
    open fun setContentSize(width: Number, height: Number, animate: Boolean? = definedExternally /* null */): Unit =
        definedExternally

    open fun setDocumentEdited(edited: Boolean): Unit = definedExternally
    open fun setEnabled(enable: Boolean): Unit = definedExternally
    open fun setFocusable(focusable: Boolean): Unit = definedExternally
    open fun setFullScreen(flag: Boolean): Unit = definedExternally
    open fun setFullScreenable(fullscreenable: Boolean): Unit = definedExternally
    open fun setHasShadow(hasShadow: Boolean): Unit = definedExternally
    open fun setIcon(icon: NativeImage): Unit = definedExternally
    open fun setIgnoreMouseEvents(
        ignore: Boolean,
        options: IgnoreMouseEventsOptions? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setKiosk(flag: Boolean): Unit = definedExternally
    open fun setMaximizable(maximizable: Boolean): Unit = definedExternally
    open fun setMaximumSize(width: Number, height: Number): Unit = definedExternally
    open fun setMenu(menu: Menu?): Unit = definedExternally
    open fun setMenuBarVisibility(visible: Boolean): Unit = definedExternally
    open fun setMinimizable(minimizable: Boolean): Unit = definedExternally
    open fun setMinimumSize(width: Number, height: Number): Unit = definedExternally
    open fun setMovable(movable: Boolean): Unit = definedExternally
    open fun setOpacity(opacity: Number): Unit = definedExternally
    open fun setOverlayIcon(overlay: NativeImage, description: String): Unit = definedExternally
    open fun setParentWindow(parent: BrowserWindow): Unit = definedExternally
    open fun setPosition(x: Number, y: Number, animate: Boolean? = definedExternally /* null */): Unit =
        definedExternally

    open fun setProgressBar(progress: Number, options: ProgressBarOptions? = definedExternally /* null */): Unit =
        definedExternally

    open fun setRepresentedFilename(filename: String): Unit = definedExternally
    open fun setResizable(resizable: Boolean): Unit = definedExternally
    open fun setSheetOffset(offsetY: Number, offsetX: Number? = definedExternally /* null */): Unit = definedExternally
    open fun setSimpleFullScreen(flag: Boolean): Unit = definedExternally
    open fun setSize(width: Number, height: Number, animate: Boolean? = definedExternally /* null */): Unit =
        definedExternally

    open fun setSkipTaskbar(skip: Boolean): Unit = definedExternally
    open fun setThumbarButtons(buttons: Array<ThumbarButton>): Boolean = definedExternally
    open fun setThumbnailClip(region: Rectangle): Unit = definedExternally
    open fun setThumbnailToolTip(toolTip: String): Unit = definedExternally
    open fun setTitle(title: String): Unit = definedExternally
    open fun setTouchBar(touchBar: TouchBar): Unit = definedExternally
    open fun setVibrancy(type: String /* "appearance-based" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "light" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "dark" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "titlebar" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "selection" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "menu" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "popover" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "sidebar" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "medium-light" */): Unit = definedExternally
    open fun setVibrancy(type: String /* "ultra-dark" */): Unit = definedExternally
    open fun setVisibleOnAllWorkspaces(visible: Boolean): Unit = definedExternally
    open fun show(): Unit = definedExternally
    open fun showDefinitionForSelection(): Unit = definedExternally
    open fun showInactive(): Unit = definedExternally
    open fun toggleTabBar(): Unit = definedExternally
    open fun unhookAllWindowMessages(): Unit = definedExternally
    open fun unhookWindowMessage(message: Number): Unit = definedExternally
    open fun unmaximize(): Unit = definedExternally
    open var id: Number = definedExternally
    open var webContents: WebContents = definedExternally

    companion object {
        fun addDevToolsExtension(path: String): Unit = definedExternally
        fun addExtension(path: String): Unit = definedExternally
        fun fromBrowserView(browserView: BrowserView): BrowserWindow? = definedExternally
        fun fromId(id: Number): BrowserWindow = definedExternally
        fun fromWebContents(webContents: WebContents): BrowserWindow = definedExternally
        fun getAllWindows(): Array<BrowserWindow> = definedExternally
        fun getDevToolsExtensions(): DevToolsExtensions = definedExternally
        fun getExtensions(): Extensions = definedExternally
        fun getFocusedWindow(): BrowserWindow = definedExternally
        fun removeDevToolsExtension(name: String): Unit = definedExternally
        fun removeExtension(name: String): Unit = definedExternally
    }

    open fun setAlwaysOnTop(flag: Boolean): Unit = definedExternally
}

open external class BrowserWindowProxy : EventEmitter {
    open fun blur(): Unit = definedExternally
    open fun close(): Unit = definedExternally
    open fun eval(code: String): Unit = definedExternally
    open fun focus(): Unit = definedExternally
    open fun postMessage(message: String, targetOrigin: String): Unit = definedExternally
    open fun print(): Unit = definedExternally
    open var closed: Boolean = definedExternally
}

external interface Certificate {
    var data: String
    var fingerprint: String
    var issuer: CertificatePrincipal
    var issuerCert: Certificate
    var issuerName: String
    var serialNumber: String
    var subject: CertificatePrincipal
    var subjectName: String
    var validExpiry: Number
    var validStart: Number
}

external interface CertificatePrincipal {
    var commonName: String
    var country: String
    var locality: String
    var organizations: Array<String>
    var organizationUnits: Array<String>
    var state: String
}

open external class ClientRequest : EventEmitter {
    constructor(options: String /* "redirect" */)
    constructor(options: String /* "method" */)
    constructor(options: String /* "url" */)
    constructor(options: String /* "session" */)
    constructor(options: String /* "partition" */)
    constructor(options: String /* "protocol" */)
    constructor(options: String /* "host" */)
    constructor(options: String /* "hostname" */)
    constructor(options: String /* "port" */)
    constructor(options: String /* "path" */)

    override fun on(event: String /* "abort" */, listener: Function<*>): ClientRequest /* this */ = definedExternally
    override fun once(event: String /* "abort" */, listener: Function<*>): ClientRequest /* this */ = definedExternally
    override fun addListener(event: String /* "abort" */, listener: Function<*>): ClientRequest /* this */ =
        definedExternally

    override fun removeListener(event: String /* "abort" */, listener: Function<*>): ClientRequest /* this */ =
        definedExternally

    override fun on(event: String /* "close" */, listener: Function<*>): ClientRequest /* this */ = definedExternally
    override fun once(event: String /* "close" */, listener: Function<*>): ClientRequest /* this */ = definedExternally
    override fun addListener(event: String /* "close" */, listener: Function<*>): ClientRequest /* this */ =
        definedExternally

    override fun removeListener(event: String /* "close" */, listener: Function<*>): ClientRequest /* this */ =
        definedExternally

    open fun on(event: String /* "error" */, listener: (error: Error) -> Unit): ClientRequest /* this */ =
        definedExternally

    open fun once(event: String /* "error" */, listener: (error: Error) -> Unit): ClientRequest /* this */ =
        definedExternally

    open fun addListener(event: String /* "error" */, listener: (error: Error) -> Unit): ClientRequest /* this */ =
        definedExternally

    open fun removeListener(event: String /* "error" */, listener: (error: Error) -> Unit): ClientRequest /* this */ =
        definedExternally

    override fun on(event: String /* "finish" */, listener: Function<*>): ClientRequest /* this */ = definedExternally
    override fun once(event: String /* "finish" */, listener: Function<*>): ClientRequest /* this */ = definedExternally
    override fun addListener(event: String /* "finish" */, listener: Function<*>): ClientRequest /* this */ =
        definedExternally

    override fun removeListener(event: String /* "finish" */, listener: Function<*>): ClientRequest /* this */ =
        definedExternally

    open fun on(
        event: String /* "login" */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun once(
        event: String /* "login" */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun addListener(
        event: String /* "login" */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun removeListener(
        event: String /* "login" */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun on(
        event: String /* "redirect" */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Any) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun once(
        event: String /* "redirect" */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Any) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun addListener(
        event: String /* "redirect" */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Any) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun removeListener(
        event: String /* "redirect" */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Any) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun on(
        event: String /* "response" */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun once(
        event: String /* "response" */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun addListener(
        event: String /* "response" */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun removeListener(
        event: String /* "response" */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */ = definedExternally

    open fun abort(): Unit = definedExternally
    open fun end(
        chunk: String? = definedExternally /* null */,
        encoding: String? = definedExternally /* null */,
        callback: Function<*>? = definedExternally /* null */
    ): Unit = definedExternally

    open fun end(
        chunk: Buffer? = definedExternally /* null */,
        encoding: String? = definedExternally /* null */,
        callback: Function<*>? = definedExternally /* null */
    ): Unit = definedExternally

    open fun followRedirect(): Unit = definedExternally
    open fun getHeader(name: String): Header = definedExternally
    open fun removeHeader(name: String): Unit = definedExternally
    open fun setHeader(name: String, value: Any): Unit = definedExternally
    open fun write(
        chunk: String,
        encoding: String? = definedExternally /* null */,
        callback: Function<*>? = definedExternally /* null */
    ): Unit = definedExternally

    open fun write(
        chunk: Buffer,
        encoding: String? = definedExternally /* null */,
        callback: Function<*>? = definedExternally /* null */
    ): Unit = definedExternally

    @Suppress("UNREACHABLE_CODE")
    open var chunkedEncoding: Boolean = definedExternally

    open fun end(): Unit = definedExternally
}

external interface Clipboard : EventEmitter {
    fun availableFormats(type: String? = definedExternally /* null */): Array<String>
    fun clear(type: String? = definedExternally /* null */)
    fun has(format: String, type: String? = definedExternally /* null */): Boolean
    fun read(format: String): String
    fun readBookmark(): ReadBookmark
    fun readBuffer(format: String): Buffer
    fun readFindText(): String
    fun readHTML(type: String? = definedExternally /* null */): String
    fun readImage(type: String? = definedExternally /* null */): NativeImage
    fun readRTF(type: String? = definedExternally /* null */): String
    fun readText(type: String? = definedExternally /* null */): String
    fun write(data: Data, type: String? = definedExternally /* null */)
    fun writeBookmark(title: String, url: String, type: String? = definedExternally /* null */)
    fun writeBuffer(format: String, buffer: Buffer, type: String? = definedExternally /* null */)
    fun writeFindText(text: String)
    fun writeHTML(markup: String, type: String? = definedExternally /* null */)
    fun writeImage(image: NativeImage, type: String? = definedExternally /* null */)
    fun writeRTF(text: String, type: String? = definedExternally /* null */)
    fun writeText(text: String, type: String? = definedExternally /* null */)
}

external interface ContentTracing : EventEmitter {
    fun captureMonitoringSnapshot(resultFilePath: String, callback: (resultFilePath: String) -> Unit)
    fun getCategories(callback: (categories: Array<String>) -> Unit)
    fun getTraceBufferUsage(callback: (value: Number, percentage: Number) -> Unit)
    fun startMonitoring(options: StartMonitoringOptions, callback: Function<*>)
    fun startRecording(options: StartRecordingOptions, callback: Function<*>)
    fun stopMonitoring(callback: Function<*>)
    fun stopRecording(resultFilePath: String, callback: (resultFilePath: String) -> Unit)
}

external interface Cookie {
    var domain: String? get() = definedExternally; set(value) = definedExternally
    var expirationDate: Number? get() = definedExternally; set(value) = definedExternally
    var hostOnly: Boolean? get() = definedExternally; set(value) = definedExternally
    var httpOnly: Boolean? get() = definedExternally; set(value) = definedExternally
    var name: String
    var path: String? get() = definedExternally; set(value) = definedExternally
    var secure: Boolean? get() = definedExternally; set(value) = definedExternally
    var session: Boolean? get() = definedExternally; set(value) = definedExternally
    var value: String
}

open external class Cookies : EventEmitter {
    open fun on(
        event: String /* "changed" */,
        listener: (event: Event, cookie: Cookie, cause: dynamic /* String /* "explicit" */ | String /* "overwrite" */ | String /* "expired" */ | String /* "evicted" */ | String /* "expired-overwrite" */ */, removed: Boolean) -> Unit
    ): Cookies /* this */ = definedExternally

    open fun once(
        event: String /* "changed" */,
        listener: (event: Event, cookie: Cookie, cause: dynamic /* String /* "explicit" */ | String /* "overwrite" */ | String /* "expired" */ | String /* "evicted" */ | String /* "expired-overwrite" */ */, removed: Boolean) -> Unit
    ): Cookies /* this */ = definedExternally

    open fun addListener(
        event: String /* "changed" */,
        listener: (event: Event, cookie: Cookie, cause: dynamic /* String /* "explicit" */ | String /* "overwrite" */ | String /* "expired" */ | String /* "evicted" */ | String /* "expired-overwrite" */ */, removed: Boolean) -> Unit
    ): Cookies /* this */ = definedExternally

    open fun removeListener(
        event: String /* "changed" */,
        listener: (event: Event, cookie: Cookie, cause: dynamic /* String /* "explicit" */ | String /* "overwrite" */ | String /* "expired" */ | String /* "evicted" */ | String /* "expired-overwrite" */ */, removed: Boolean) -> Unit
    ): Cookies /* this */ = definedExternally

    open fun flushStore(callback: Function<*>): Unit = definedExternally
    open fun get(filter: Filter, callback: (error: Error, cookies: Array<Cookie>) -> Unit): Unit = definedExternally
    open fun remove(url: String, name: String, callback: Function<*>): Unit = definedExternally
    open fun set(details: Details, callback: (error: Error) -> Unit): Unit = definedExternally
}

external interface CPUUsage {
    var idleWakeupsPerSecond: Number
    var percentCPUUsage: Number
}

external interface CrashReport {
    var date: Date
    var id: String
}

external interface CrashReporter : EventEmitter {
    fun addExtraParameter(key: String, value: String)
    fun getLastCrashReport(): CrashReport
    fun getParameters()
    fun getUploadedReports(): Array<CrashReport>
    fun getUploadToServer(): Boolean
    fun removeExtraParameter(key: String)
    fun setUploadToServer(uploadToServer: Boolean)
    fun start(options: CrashReporterStartOptions)
}

open external class Debugger : EventEmitter {
    open fun on(event: String /* "detach" */, listener: (event: Event, reason: String) -> Unit): Debugger /* this */ =
        definedExternally

    open fun once(event: String /* "detach" */, listener: (event: Event, reason: String) -> Unit): Debugger /* this */ =
        definedExternally

    open fun addListener(
        event: String /* "detach" */,
        listener: (event: Event, reason: String) -> Unit
    ): Debugger /* this */ = definedExternally

    open fun removeListener(
        event: String /* "detach" */,
        listener: (event: Event, reason: String) -> Unit
    ): Debugger /* this */ = definedExternally

    open fun on(
        event: String /* "message" */,
        listener: (event: Event, method: String, params: Any) -> Unit
    ): Debugger /* this */ = definedExternally

    open fun once(
        event: String /* "message" */,
        listener: (event: Event, method: String, params: Any) -> Unit
    ): Debugger /* this */ = definedExternally

    open fun addListener(
        event: String /* "message" */,
        listener: (event: Event, method: String, params: Any) -> Unit
    ): Debugger /* this */ = definedExternally

    open fun removeListener(
        event: String /* "message" */,
        listener: (event: Event, method: String, params: Any) -> Unit
    ): Debugger /* this */ = definedExternally

    open fun attach(protocolVersion: String? = definedExternally /* null */): Unit = definedExternally
    open fun detach(): Unit = definedExternally
    open fun isAttached(): Boolean = definedExternally
    open fun sendCommand(
        method: String,
        commandParams: Any? = definedExternally /* null */,
        callback: ((error: Any, result: Any) -> Unit)? = definedExternally /* null */
    ): Unit = definedExternally
}

external interface DesktopCapturer : EventEmitter {
    fun getSources(options: SourcesOptions, callback: (error: Error, sources: Array<DesktopCapturerSource>) -> Unit)
}

external interface DesktopCapturerSource {
    var id: String
    var name: String
    var thumbnail: NativeImage
}

external interface Dialog : EventEmitter {
    fun showCertificateTrustDialog(
        browserWindow: BrowserWindow,
        options: CertificateTrustDialogOptions,
        callback: Function<*>
    )

    fun showCertificateTrustDialog(options: CertificateTrustDialogOptions, callback: Function<*>)
    fun showCertificateTrustDialog(
        browserWindow: BrowserWindow,
        options: CertificateTrustDialogOptions,
        callback: Function<*>
    )

    fun showErrorBox(title: String, content: String)
    fun showMessageBox(
        browserWindow: BrowserWindow,
        options: MessageBoxOptions,
        callback: ((response: Number, checkboxChecked: Boolean) -> Unit)? = definedExternally /* null */
    ): Number

    fun showMessageBox(
        options: MessageBoxOptions,
        callback: ((response: Number, checkboxChecked: Boolean) -> Unit)? = definedExternally /* null */
    ): Number

    fun showOpenDialog(
        browserWindow: BrowserWindow,
        options: OpenDialogOptions,
        callback: ((filePaths: Array<String>, bookmarks: Array<String>) -> Unit)? = definedExternally /* null */
    ): Array<String>

    fun showOpenDialog(
        options: OpenDialogOptions,
        callback: ((filePaths: Array<String>, bookmarks: Array<String>) -> Unit)? = definedExternally /* null */
    ): Array<String>

    fun showSaveDialog(
        browserWindow: BrowserWindow,
        options: SaveDialogOptions,
        callback: ((filename: String, bookmark: String) -> Unit)? = definedExternally /* null */
    ): String

    fun showSaveDialog(
        options: SaveDialogOptions,
        callback: ((filename: String, bookmark: String) -> Unit)? = definedExternally /* null */
    ): String
}

external interface Display {
    var bounds: Rectangle
    var id: Number
    var rotation: Number
    var scaleFactor: Number
    var size: Size
    var touchSupport: dynamic /* String /* "available" */ | String /* "unavailable" */ | String /* "unknown" */ */
    var workArea: Rectangle
    var workAreaSize: Size
}

open external class DownloadItem : EventEmitter {
    open fun on(
        event: String /* "done" */,
        listener: (event: Event, state: dynamic /* String /* "completed" */ | String /* "cancelled" */ | String /* "interrupted" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun once(
        event: String /* "done" */,
        listener: (event: Event, state: dynamic /* String /* "completed" */ | String /* "cancelled" */ | String /* "interrupted" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun addListener(
        event: String /* "done" */,
        listener: (event: Event, state: dynamic /* String /* "completed" */ | String /* "cancelled" */ | String /* "interrupted" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun removeListener(
        event: String /* "done" */,
        listener: (event: Event, state: dynamic /* String /* "completed" */ | String /* "cancelled" */ | String /* "interrupted" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun on(
        event: String /* "updated" */,
        listener: (event: Event, state: dynamic /* String /* "interrupted" */ | String /* "progressing" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun once(
        event: String /* "updated" */,
        listener: (event: Event, state: dynamic /* String /* "interrupted" */ | String /* "progressing" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun addListener(
        event: String /* "updated" */,
        listener: (event: Event, state: dynamic /* String /* "interrupted" */ | String /* "progressing" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun removeListener(
        event: String /* "updated" */,
        listener: (event: Event, state: dynamic /* String /* "interrupted" */ | String /* "progressing" */ */) -> Unit
    ): DownloadItem /* this */ = definedExternally

    open fun cancel(): Unit = definedExternally
    open fun canResume(): Boolean = definedExternally
    open fun getContentDisposition(): String = definedExternally
    open fun getETag(): String = definedExternally
    open fun getFilename(): String = definedExternally
    open fun getLastModifiedTime(): String = definedExternally
    open fun getMimeType(): String = definedExternally
    open fun getReceivedBytes(): Number = definedExternally
    open fun getSavePath(): String = definedExternally
    open fun getStartTime(): Number = definedExternally
    open fun getState(): dynamic /* String /* "completed" */ | String /* "cancelled" */ | String /* "interrupted" */ | String /* "progressing" */ */ =
        definedExternally

    open fun getTotalBytes(): Number = definedExternally
    open fun getURL(): String = definedExternally
    open fun getURLChain(): Array<String> = definedExternally
    open fun hasUserGesture(): Boolean = definedExternally
    open fun isPaused(): Boolean = definedExternally
    open fun pause(): Unit = definedExternally
    open fun resume(): Unit = definedExternally
    open fun setSavePath(path: String): Unit = definedExternally
}

external interface FileFilter {
    var extensions: Array<String>
    var name: String
}

external interface GlobalShortcut : EventEmitter {
    fun isRegistered(accelerator: Accelerator): Boolean
    fun register(accelerator: Accelerator, callback: Function<*>)
    fun unregister(accelerator: Accelerator)
    fun unregisterAll()
}

external interface GPUFeatureStatus {
    var flash_3d: String
    var flash_stage3d: String
    var flash_stage3d_baseline: String
    var gpu_compositing: String
    var multiple_raster_threads: String
    var native_gpu_memory_buffers: String
    var rasterization: String
    var video_decode: String
    var video_encode: String
    var vpx_decode: String
    var webgl: String
    var webgl2: String
}

external interface InAppPurchase : EventEmitter {
    fun on(
        event: String /* "transactions-updated" */,
        listener: (event: Event, transactions: Array<Transaction>) -> Unit
    ): InAppPurchase /* this */

    fun once(
        event: String /* "transactions-updated" */,
        listener: (event: Event, transactions: Array<Transaction>) -> Unit
    ): InAppPurchase /* this */

    fun addListener(
        event: String /* "transactions-updated" */,
        listener: (event: Event, transactions: Array<Transaction>) -> Unit
    ): InAppPurchase /* this */

    fun removeListener(
        event: String /* "transactions-updated" */,
        listener: (event: Event, transactions: Array<Transaction>) -> Unit
    ): InAppPurchase /* this */

    fun canMakePayments(): Boolean
    fun getReceiptURL(): String
    fun purchaseProduct(
        productID: String,
        quantity: Number? = definedExternally /* null */,
        callback: ((isProductValid: Boolean) -> Unit)? = definedExternally /* null */
    )
}

open external class IncomingMessage : EventEmitter {
    override fun on(event: String /* "aborted" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun once(event: String /* "aborted" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun addListener(event: String /* "aborted" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun removeListener(event: String /* "aborted" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    open fun on(event: String /* "data" */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */ =
        definedExternally

    open fun once(event: String /* "data" */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */ =
        definedExternally

    open fun addListener(event: String /* "data" */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */ =
        definedExternally

    open fun removeListener(event: String /* "data" */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */ =
        definedExternally

    override fun on(event: String /* "end" */, listener: Function<*>): IncomingMessage /* this */ = definedExternally
    override fun once(event: String /* "end" */, listener: Function<*>): IncomingMessage /* this */ = definedExternally
    override fun addListener(event: String /* "end" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun removeListener(event: String /* "end" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun on(event: String /* "error" */, listener: Function<*>): IncomingMessage /* this */ = definedExternally
    override fun once(event: String /* "error" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun addListener(event: String /* "error" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    override fun removeListener(event: String /* "error" */, listener: Function<*>): IncomingMessage /* this */ =
        definedExternally

    open var headers: Any = definedExternally
    open var httpVersion: String = definedExternally
    open var httpVersionMajor: Number = definedExternally
    open var httpVersionMinor: Number = definedExternally
    open var statusCode: Number = definedExternally
    open var statusMessage: String = definedExternally
}

external interface IOCounters {
    var otherOperationCount: Number
    var otherTransferCount: Number
    var readOperationCount: Number
    var readTransferCount: Number
    var writeOperationCount: Number
    var writeTransferCount: Number
}

external interface IpcMain : EventEmitter {
    override fun on(event: String, listener: Function<*>): IpcMain /* this */
    override fun once(event: String, listener: Function<*>): IpcMain /* this */
    fun removeAllListeners(channel: String): IpcMain /* this */
    override fun removeListener(event: String, listener: Function<*>): IpcMain /* this */
}

external interface IpcRenderer : EventEmitter {
    override fun on(event: String, listener: Function<*>): IpcRenderer /* this */
    override fun once(event: String, listener: Function<*>): IpcRenderer /* this */
    fun removeAllListeners(channel: String): IpcRenderer /* this */
    override fun removeListener(event: String, listener: Function<*>): IpcRenderer /* this */
    fun send(channel: String, vararg args: Any)
    fun sendSync(channel: String, vararg args: Any): Any
    fun sendTo(windowId: Number, channel: String, vararg args: Any)
    fun sendToHost(channel: String, vararg args: Any)
}

external interface JumpListCategory {
    var items: Array<JumpListItem>? get() = definedExternally; set(value) = definedExternally
    var name: String? get() = definedExternally; set(value) = definedExternally
    var type: dynamic /* String /* "tasks" */ | String /* "frequent" */ | String /* "recent" */ | String /* "custom" */ */ get() = definedExternally; set(value) = definedExternally
}

external interface JumpListItem {
    var args: String? get() = definedExternally; set(value) = definedExternally
    var description: String? get() = definedExternally; set(value) = definedExternally
    var iconIndex: Number? get() = definedExternally; set(value) = definedExternally
    var iconPath: String? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var program: String? get() = definedExternally; set(value) = definedExternally
    var title: String? get() = definedExternally; set(value) = definedExternally
    var type: dynamic /* String /* "task" */ | String /* "separator" */ | String /* "file" */ */ get() = definedExternally; set(value) = definedExternally
}

external interface MemoryInfo {
    var peakWorkingSetSize: Number
    var pid: Number
    var privateBytes: Number
    var sharedBytes: Number
    var workingSetSize: Number
}

external interface MemoryUsageDetails {
    var count: Number
    var liveSize: Number
    var size: Number
}

open external class Menu {
    open fun on(event: String /* "menu-will-close" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun once(event: String /* "menu-will-close" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun addListener(event: String /* "menu-will-close" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun removeListener(event: String /* "menu-will-close" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun on(event: String /* "menu-will-show" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun once(event: String /* "menu-will-show" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun addListener(event: String /* "menu-will-show" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun removeListener(event: String /* "menu-will-show" */, listener: (event: Event) -> Unit): Menu /* this */ =
        definedExternally

    open fun append(menuItem: MenuItem): Unit = definedExternally
    open fun closePopup(browserWindow: BrowserWindow? = definedExternally /* null */): Unit = definedExternally
    open fun getMenuItemById(id: String): MenuItem = definedExternally
    open fun insert(pos: Number, menuItem: MenuItem): Unit = definedExternally
    open fun popup(options: PopupOptions): Unit = definedExternally
    open var items: Array<MenuItem> = definedExternally

    companion object {
        fun buildFromTemplate(template: Array<MenuItemConstructorOptions>): Menu = definedExternally
        fun getApplicationMenu(): Menu? = definedExternally
        fun sendActionToFirstResponder(action: String): Unit = definedExternally
        fun setApplicationMenu(menu: Menu?): Unit = definedExternally
    }
}

open external class MenuItem(options: MenuItemConstructorOptions) {
    open var checked: Boolean = definedExternally
    open var click: Function<*> = definedExternally
    open var enabled: Boolean = definedExternally
    open var label: String = definedExternally
    open var visible: Boolean = definedExternally
}

external interface MimeTypedBuffer {
    var data: Buffer
    var mimeType: String
}

open external class NativeImage {
    open fun addRepresentation(options: AddRepresentationOptions): Unit = definedExternally
    open fun crop(rect: Rectangle): NativeImage = definedExternally
    open fun getAspectRatio(): Number = definedExternally
    open fun getBitmap(options: BitmapOptions? = definedExternally /* null */): Buffer = definedExternally
    open fun getNativeHandle(): Buffer = definedExternally
    open fun getSize(): Size = definedExternally
    open fun isEmpty(): Boolean = definedExternally
    open fun isTemplateImage(): Boolean = definedExternally
    open fun resize(options: ResizeOptions): NativeImage = definedExternally
    open fun setTemplateImage(option: Boolean): Unit = definedExternally
    open fun toBitmap(options: ToBitmapOptions? = definedExternally /* null */): Buffer = definedExternally
    open fun toDataURL(options: ToDataURLOptions? = definedExternally /* null */): String = definedExternally
    open fun toJPEG(quality: Number): Buffer = definedExternally
    open fun toPNG(options: ToPNGOptions? = definedExternally /* null */): Buffer = definedExternally

    companion object {
        fun createEmpty(): NativeImage = definedExternally
        fun createFromBuffer(
            buffer: Buffer,
            options: CreateFromBufferOptions? = definedExternally /* null */
        ): NativeImage = definedExternally

        fun createFromDataURL(dataURL: String): NativeImage = definedExternally
        fun createFromNamedImage(imageName: String, hslShift: Array<Number>): NativeImage = definedExternally
        fun createFromPath(path: String): NativeImage = definedExternally
    }
}

external interface Net : EventEmitter {
    fun request(options: Any): ClientRequest
    fun request(options: String): ClientRequest
}

open external class Notification(options: NotificationConstructorOptions) :
    EventEmitter {
    open fun on(
        event: String /* "action" */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */ = definedExternally

    open fun once(
        event: String /* "action" */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */ = definedExternally

    open fun addListener(
        event: String /* "action" */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */ = definedExternally

    open fun removeListener(
        event: String /* "action" */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */ = definedExternally

    open fun on(event: String /* "click" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun once(event: String /* "click" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun addListener(event: String /* "click" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun removeListener(event: String /* "click" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun on(event: String /* "close" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun once(event: String /* "close" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun addListener(event: String /* "close" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun removeListener(event: String /* "close" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun on(event: String /* "reply" */, listener: (event: Event, reply: String) -> Unit): Notification /* this */ =
        definedExternally

    open fun once(
        event: String /* "reply" */,
        listener: (event: Event, reply: String) -> Unit
    ): Notification /* this */ = definedExternally

    open fun addListener(
        event: String /* "reply" */,
        listener: (event: Event, reply: String) -> Unit
    ): Notification /* this */ = definedExternally

    open fun removeListener(
        event: String /* "reply" */,
        listener: (event: Event, reply: String) -> Unit
    ): Notification /* this */ = definedExternally

    open fun on(event: String /* "show" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun once(event: String /* "show" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun addListener(event: String /* "show" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun removeListener(event: String /* "show" */, listener: (event: Event) -> Unit): Notification /* this */ =
        definedExternally

    open fun close(): Unit = definedExternally
    open fun show(): Unit = definedExternally

    companion object {
        fun isSupported(): Boolean = definedExternally
    }
}

external interface NotificationAction {
    var text: String? get() = definedExternally; set(value) = definedExternally
    var type: String /* "button" */
}

external interface Point {
    var x: Number
    var y: Number
}

external interface PowerMonitor : EventEmitter {
    override fun on(event: String /* "on-ac" */, listener: Function<*>): PowerMonitor /* this */
    override fun once(event: String /* "on-ac" */, listener: Function<*>): PowerMonitor /* this */
    override fun addListener(event: String /* "on-ac" */, listener: Function<*>): PowerMonitor /* this */
    override fun removeListener(event: String /* "on-ac" */, listener: Function<*>): PowerMonitor /* this */
    override fun on(event: String /* "on-battery" */, listener: Function<*>): PowerMonitor /* this */
    override fun once(event: String /* "on-battery" */, listener: Function<*>): PowerMonitor /* this */
    override fun addListener(event: String /* "on-battery" */, listener: Function<*>): PowerMonitor /* this */
    override fun removeListener(event: String /* "on-battery" */, listener: Function<*>): PowerMonitor /* this */
    override fun on(event: String /* "resume" */, listener: Function<*>): PowerMonitor /* this */
    override fun once(event: String /* "resume" */, listener: Function<*>): PowerMonitor /* this */
    override fun addListener(event: String /* "resume" */, listener: Function<*>): PowerMonitor /* this */
    override fun removeListener(event: String /* "resume" */, listener: Function<*>): PowerMonitor /* this */
    override fun on(event: String /* "shutdown" */, listener: Function<*>): PowerMonitor /* this */
    override fun once(event: String /* "shutdown" */, listener: Function<*>): PowerMonitor /* this */
    override fun addListener(event: String /* "shutdown" */, listener: Function<*>): PowerMonitor /* this */
    override fun removeListener(event: String /* "shutdown" */, listener: Function<*>): PowerMonitor /* this */
    override fun on(event: String /* "suspend" */, listener: Function<*>): PowerMonitor /* this */
    override fun once(event: String /* "suspend" */, listener: Function<*>): PowerMonitor /* this */
    override fun addListener(event: String /* "suspend" */, listener: Function<*>): PowerMonitor /* this */
    override fun removeListener(event: String /* "suspend" */, listener: Function<*>): PowerMonitor /* this */
}

external interface PowerSaveBlocker : EventEmitter {
    fun isStarted(id: Number): Boolean
    fun start(type: String /* "prevent-app-suspension" */): Number
    fun start(type: String /* "prevent-display-sleep" */): Number
    fun stop(id: Number)
}

external interface PrinterInfo {
    var description: String
    var isDefault: Boolean
    var name: String
    var status: Number
}

external interface ProcessMetric {
    var cpu: CPUUsage
    var memory: MemoryInfo
    var pid: Number
    var type: String
}

external interface Protocol : EventEmitter {
    fun interceptBufferProtocol(
        scheme: String,
        handler: (request: InterceptBufferProtocolRequest, callback: (buffer: Buffer? /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun interceptFileProtocol(
        scheme: String,
        handler: (request: InterceptFileProtocolRequest, callback: (filePath: String) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun interceptHttpProtocol(
        scheme: String,
        handler: (request: InterceptHttpProtocolRequest, callback: (redirectRequest: RedirectRequest) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun interceptStreamProtocol(
        scheme: String,
        handler: (request: InterceptStreamProtocolRequest, callback: (stream: dynamic /* ReadableStream | StreamProtocolResponse */ /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun interceptStringProtocol(
        scheme: String,
        handler: (request: InterceptStringProtocolRequest, callback: (data: String? /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun isProtocolHandled(scheme: String, callback: (error: Error) -> Unit)
    fun registerBufferProtocol(
        scheme: String,
        handler: (request: RegisterBufferProtocolRequest, callback: (buffer: dynamic /* Buffer | MimeTypedBuffer */ /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun registerFileProtocol(
        scheme: String,
        handler: (request: RegisterFileProtocolRequest, callback: (filePath: String? /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun registerHttpProtocol(
        scheme: String,
        handler: (request: RegisterHttpProtocolRequest, callback: (redirectRequest: RedirectRequest) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun registerServiceWorkerSchemes(schemes: Array<String>)
    fun registerStandardSchemes(
        schemes: Array<String>,
        options: RegisterStandardSchemesOptions? = definedExternally /* null */
    )

    fun registerStreamProtocol(
        scheme: String,
        handler: (request: RegisterStreamProtocolRequest, callback: (stream: dynamic /* ReadableStream | StreamProtocolResponse */ /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun registerStringProtocol(
        scheme: String,
        handler: (request: RegisterStringProtocolRequest, callback: (data: String? /*= null*/) -> Unit) -> Unit,
        completion: ((error: Error) -> Unit)? = definedExternally /* null */
    )

    fun uninterceptProtocol(scheme: String, completion: ((error: Error) -> Unit)? = definedExternally /* null */)
    fun unregisterProtocol(scheme: String, completion: ((error: Error) -> Unit)? = definedExternally /* null */)
}

external interface Rectangle {
    var height: Number
    var width: Number
    var x: Number
    var y: Number
}

external interface Remote : MainInterface {
    fun getCurrentWebContents(): WebContents
    fun getCurrentWindow(): BrowserWindow
    fun getGlobal(name: String): Any
    fun require(module: String): Any
    var process: Any? get() = definedExternally; set(value) = definedExternally
}

external interface RemoveClientCertificate {
    var origin: String
    var type: String
}

external interface RemovePassword {
    var origin: String? get() = definedExternally; set(value) = definedExternally
    var password: String? get() = definedExternally; set(value) = definedExternally
    var realm: String? get() = definedExternally; set(value) = definedExternally
    var scheme: dynamic /* String /* "basic" */ | String /* "digest" */ | String /* "ntlm" */ | String /* "negotiate" */ */ get() = definedExternally; set(value) = definedExternally
    var type: String
    var username: String? get() = definedExternally; set(value) = definedExternally
}

external interface Screen : EventEmitter {
    fun on(
        event: String /* "display-added" */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun once(
        event: String /* "display-added" */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun addListener(
        event: String /* "display-added" */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun removeListener(
        event: String /* "display-added" */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun on(
        event: String /* "display-metrics-changed" */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun once(
        event: String /* "display-metrics-changed" */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun addListener(
        event: String /* "display-metrics-changed" */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun removeListener(
        event: String /* "display-metrics-changed" */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun on(
        event: String /* "display-removed" */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun once(
        event: String /* "display-removed" */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun addListener(
        event: String /* "display-removed" */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun removeListener(
        event: String /* "display-removed" */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun getAllDisplays(): Array<Display>
    fun getCursorScreenPoint(): Point
    fun getDisplayMatching(rect: Rectangle): Display
    fun getDisplayNearestPoint(point: Point): Display
    fun getMenuBarHeight(): Number
    fun getPrimaryDisplay(): Display
}

external interface ScrubberItem {
    var icon: NativeImage? get() = definedExternally; set(value) = definedExternally
    var label: String? get() = definedExternally; set(value) = definedExternally
}

external interface SegmentedControlSegment {
    var enabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var icon: NativeImage? get() = definedExternally; set(value) = definedExternally
    var label: String? get() = definedExternally; set(value) = definedExternally
}

open external class Session : EventEmitter {
    open fun on(
        event: String /* "will-download" */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */ = definedExternally

    open fun once(
        event: String /* "will-download" */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */ = definedExternally

    open fun addListener(
        event: String /* "will-download" */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */ = definedExternally

    open fun removeListener(
        event: String /* "will-download" */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */ = definedExternally

    open fun allowNTLMCredentialsForDomains(domains: String): Unit = definedExternally
    open fun clearAuthCache(options: RemovePassword, callback: Function<*>? = definedExternally /* null */): Unit =
        definedExternally

    open fun clearAuthCache(
        options: RemoveClientCertificate,
        callback: Function<*>? = definedExternally /* null */
    ): Unit = definedExternally

    open fun clearCache(callback: Function<*>): Unit = definedExternally
    open fun clearHostResolverCache(callback: Function<*>? = definedExternally /* null */): Unit = definedExternally
    open fun clearStorageData(
        options: ClearStorageDataOptions? = definedExternally /* null */,
        callback: Function<*>? = definedExternally /* null */
    ): Unit = definedExternally

    open fun createInterruptedDownload(options: CreateInterruptedDownloadOptions): Unit = definedExternally
    open fun disableNetworkEmulation(): Unit = definedExternally
    open fun enableNetworkEmulation(options: EnableNetworkEmulationOptions): Unit = definedExternally
    open fun flushStorageData(): Unit = definedExternally
    open fun getBlobData(identifier: String, callback: (result: Buffer) -> Unit): Unit = definedExternally
    open fun getCacheSize(callback: (size: Number) -> Unit): Unit = definedExternally
    open fun getPreloads(): Array<String> = definedExternally
    open fun getUserAgent(): String = definedExternally
    open fun resolveProxy(url: String, callback: (proxy: String) -> Unit): Unit = definedExternally
    open fun setCertificateVerifyProc(proc: (request: CertificateVerifyProcRequest, callback: (verificationResult: Number) -> Unit) -> Unit): Unit =
        definedExternally

    open fun setDownloadPath(path: String): Unit = definedExternally
    open fun setPermissionRequestHandler(handler: (webContents: WebContents, permission: String, callback: (permissionGranted: Boolean) -> Unit, details: PermissionRequestHandlerDetails) -> Unit?): Unit =
        definedExternally

    open fun setPreloads(preloads: Array<String>): Unit = definedExternally
    open fun setProxy(config: Config, callback: Function<*>): Unit = definedExternally
    open fun setUserAgent(userAgent: String, acceptLanguages: String? = definedExternally /* null */): Unit =
        definedExternally

    open var cookies: Cookies = definedExternally
    open var protocol: Protocol = definedExternally
    open var webRequest: WebRequest = definedExternally

    companion object {
        fun fromPartition(partition: String, options: FromPartitionOptions? = definedExternally /* null */): Session =
            definedExternally

        var defaultSession: Session = definedExternally
    }
}

external interface Shell {
    fun beep()
    fun moveItemToTrash(fullPath: String): Boolean
    fun openExternal(
        url: String,
        options: OpenExternalOptions? = definedExternally /* null */,
        callback: ((error: Error) -> Unit)? = definedExternally /* null */
    ): Boolean

    fun openItem(fullPath: String): Boolean
    fun readShortcutLink(shortcutPath: String): ShortcutDetails
    fun showItemInFolder(fullPath: String): Boolean
    fun writeShortcutLink(shortcutPath: String, operation: String /* "create" */, options: ShortcutDetails): Boolean
    fun writeShortcutLink(shortcutPath: String, operation: String /* "update" */, options: ShortcutDetails): Boolean
    fun writeShortcutLink(shortcutPath: String, operation: String /* "replace" */, options: ShortcutDetails): Boolean
    fun writeShortcutLink(shortcutPath: String, options: ShortcutDetails): Boolean
}

external interface ShortcutDetails {
    var appUserModelId: String? get() = definedExternally; set(value) = definedExternally
    var args: String? get() = definedExternally; set(value) = definedExternally
    var cwd: String? get() = definedExternally; set(value) = definedExternally
    var description: String? get() = definedExternally; set(value) = definedExternally
    var icon: String? get() = definedExternally; set(value) = definedExternally
    var iconIndex: Number? get() = definedExternally; set(value) = definedExternally
    var target: String
}

external interface Size {
    var height: Number
    var width: Number
}

external interface StreamProtocolResponse {
    var data: ReadableStream
    var headers: Headers
    var statusCode: Number
}

external interface SystemPreferences : EventEmitter {
    fun on(
        event: String /* "accent-color-changed" */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun once(
        event: String /* "accent-color-changed" */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun addListener(
        event: String /* "accent-color-changed" */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun removeListener(
        event: String /* "accent-color-changed" */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun on(event: String /* "color-changed" */, listener: (event: Event) -> Unit): SystemPreferences /* this */
    fun once(event: String /* "color-changed" */, listener: (event: Event) -> Unit): SystemPreferences /* this */
    fun addListener(event: String /* "color-changed" */, listener: (event: Event) -> Unit): SystemPreferences /* this */
    fun removeListener(
        event: String /* "color-changed" */,
        listener: (event: Event) -> Unit
    ): SystemPreferences /* this */

    fun on(
        event: String /* "inverted-color-scheme-changed" */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun once(
        event: String /* "inverted-color-scheme-changed" */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun addListener(
        event: String /* "inverted-color-scheme-changed" */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun removeListener(
        event: String /* "inverted-color-scheme-changed" */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun getAccentColor(): String
    fun getColor(color: dynamic /* String /* "menu" */ | String /* "3d-dark-shadow" */ | String /* "3d-face" */ | String /* "3d-highlight" */ | String /* "3d-light" */ | String /* "3d-shadow" */ | String /* "active-border" */ | String /* "active-caption" */ | String /* "active-caption-gradient" */ | String /* "app-workspace" */ | String /* "button-text" */ | String /* "caption-text" */ | String /* "desktop" */ | String /* "disabled-text" */ | String /* "highlight" */ | String /* "highlight-text" */ | String /* "hotlight" */ | String /* "inactive-border" */ | String /* "inactive-caption" */ | String /* "inactive-caption-gradient" */ | String /* "inactive-caption-text" */ | String /* "info-background" */ | String /* "info-text" */ | String /* "menu-highlight" */ | String /* "menubar" */ | String /* "menu-text" */ | String /* "scrollbar" */ | String /* "window" */ | String /* "window-frame" */ | String /* "window-text" */ */): String
    fun getUserDefault(key: String, type: String /* "string" */): Any
    fun getUserDefault(key: String, type: String /* "boolean" */): Any
    fun getUserDefault(key: String, type: String /* "url" */): Any
    fun getUserDefault(key: String, type: String /* "integer" */): Any
    fun getUserDefault(key: String, type: String /* "float" */): Any
    fun getUserDefault(key: String, type: String /* "double" */): Any
    fun getUserDefault(key: String, type: String /* "array" */): Any
    fun getUserDefault(key: String, type: String /* "dictionary" */): Any
    fun isAeroGlassEnabled(): Boolean
    fun isDarkMode(): Boolean
    fun isInvertedColorScheme(): Boolean
    fun isSwipeTrackingFromScrollEventsEnabled(): Boolean
    fun postLocalNotification(event: String, userInfo: Any)
    fun postNotification(event: String, userInfo: Any)
    fun registerDefaults(defaults: Any)
    fun removeUserDefault(key: String)
    fun setUserDefault(key: String, type: String, value: String)
    fun subscribeLocalNotification(event: String, callback: (event: String, userInfo: Any) -> Unit)
    fun subscribeNotification(event: String, callback: (event: String, userInfo: Any) -> Unit)
    fun unsubscribeLocalNotification(id: Number)
    fun unsubscribeNotification(id: Number)
}

external interface Task {
    var arguments: String
    var description: String
    var iconIndex: Number
    var iconPath: String
    var program: String
    var title: String
}

external interface ThumbarButton {
    var click: Function<*>
    var flags: Array<String>? get() = definedExternally; set(value) = definedExternally
    var icon: NativeImage
    var tooltip: String? get() = definedExternally; set(value) = definedExternally
}

open external class TouchBarButton(options: TouchBarButtonConstructorOptions) :
    EventEmitter {
    open var backgroundColor: String = definedExternally
    open var icon: NativeImage = definedExternally
    open var label: String = definedExternally
}

open external class TouchBarColorPicker(options: TouchBarColorPickerConstructorOptions) :
    EventEmitter {
    open var availableColors: Array<String> = definedExternally
    open var selectedColor: String = definedExternally
}

open external class TouchBarGroup(options: TouchBarGroupConstructorOptions) :
    EventEmitter

open external class TouchBarLabel(options: TouchBarLabelConstructorOptions) :
    EventEmitter {
    open var label: String = definedExternally
    open var textColor: String = definedExternally
}

open external class TouchBarPopover(options: TouchBarPopoverConstructorOptions) :
    EventEmitter {
    open var icon: NativeImage = definedExternally
    open var label: String = definedExternally
}

open external class TouchBarScrubber(options: TouchBarScrubberConstructorOptions) :
    EventEmitter {
    open var continuous: Boolean = definedExternally
    open var items: Array<ScrubberItem> = definedExternally
    open var mode: String = definedExternally
    open var overlayStyle: String = definedExternally
    open var selectedStyle: String = definedExternally
    open var showArrowButtons: Boolean = definedExternally
}

open external class TouchBarSegmentedControl(options: TouchBarSegmentedControlConstructorOptions) :
    EventEmitter {
    open var segments: Array<SegmentedControlSegment> = definedExternally
    open var segmentStyle: String = definedExternally
    open var selectedIndex: Number = definedExternally
}

open external class TouchBarSlider(options: TouchBarSliderConstructorOptions) :
    EventEmitter {
    open var label: String = definedExternally
    open var maxValue: Number = definedExternally
    open var minValue: Number = definedExternally
    open var value: Number = definedExternally
}

open external class TouchBarSpacer(options: TouchBarSpacerConstructorOptions) :
    EventEmitter

open external class TouchBar(options: TouchBarConstructorOptions) :
    EventEmitter {
    open var escapeItem: dynamic /* TouchBarButton | TouchBarColorPicker | TouchBarGroup | TouchBarLabel | TouchBarPopover | TouchBarScrubber | TouchBarSegmentedControl | TouchBarSlider | TouchBarSpacer | Nothing? */ =
        definedExternally

    companion object {
        var TouchBarButton: Any? = definedExternally
        var TouchBarColorPicker: Any? = definedExternally
        var TouchBarGroup: Any? = definedExternally
        var TouchBarLabel: Any? = definedExternally
        var TouchBarPopover: Any? = definedExternally
        var TouchBarScrubber: Any? = definedExternally
        var TouchBarSegmentedControl: Any? = definedExternally
        var TouchBarSlider: Any? = definedExternally
        var TouchBarSpacer: Any? = definedExternally
    }
}

external interface Transaction {
    var errorCode: Number
    var errorMessage: String
    var originalTransactionIdentifier: String
    var payment: Payment
    var transactionDate: String
    var transactionIdentifier: String
    var transactionState: String
}

open external class Tray : EventEmitter {
    constructor(image: String)
    constructor(image: NativeImage)

    override fun on(event: String /* "balloon-click" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "balloon-click" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "balloon-click" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun removeListener(event: String /* "balloon-click" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun on(event: String /* "balloon-closed" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "balloon-closed" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "balloon-closed" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun removeListener(event: String /* "balloon-closed" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun on(event: String /* "balloon-show" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "balloon-show" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "balloon-show" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun removeListener(event: String /* "balloon-show" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    open fun on(
        event: String /* "click" */,
        listener: (event: Event, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun once(
        event: String /* "click" */,
        listener: (event: Event, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "click" */,
        listener: (event: Event, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "click" */,
        listener: (event: Event, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun on(
        event: String /* "double-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun once(
        event: String /* "double-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "double-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "double-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    override fun on(event: String /* "drag-end" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "drag-end" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "drag-end" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun removeListener(event: String /* "drag-end" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun on(event: String /* "drag-enter" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "drag-enter" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "drag-enter" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun removeListener(event: String /* "drag-enter" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun on(event: String /* "drag-leave" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "drag-leave" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "drag-leave" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun removeListener(event: String /* "drag-leave" */, listener: Function<*>): Tray /* this */ =
        definedExternally

    override fun on(event: String /* "drop" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun once(event: String /* "drop" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun addListener(event: String /* "drop" */, listener: Function<*>): Tray /* this */ = definedExternally
    override fun removeListener(event: String /* "drop" */, listener: Function<*>): Tray /* this */ = definedExternally
    open fun on(
        event: String /* "drop-files" */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */ = definedExternally

    open fun once(
        event: String /* "drop-files" */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "drop-files" */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "drop-files" */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */ = definedExternally

    open fun on(event: String /* "drop-text" */, listener: (event: Event, text: String) -> Unit): Tray /* this */ =
        definedExternally

    open fun once(event: String /* "drop-text" */, listener: (event: Event, text: String) -> Unit): Tray /* this */ =
        definedExternally

    open fun addListener(
        event: String /* "drop-text" */,
        listener: (event: Event, text: String) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "drop-text" */,
        listener: (event: Event, text: String) -> Unit
    ): Tray /* this */ = definedExternally

    open fun on(event: String /* "mouse-enter" */, listener: (event: Event, position: Point) -> Unit): Tray /* this */ =
        definedExternally

    open fun once(
        event: String /* "mouse-enter" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "mouse-enter" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "mouse-enter" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun on(event: String /* "mouse-leave" */, listener: (event: Event, position: Point) -> Unit): Tray /* this */ =
        definedExternally

    open fun once(
        event: String /* "mouse-leave" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "mouse-leave" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "mouse-leave" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun on(event: String /* "mouse-move" */, listener: (event: Event, position: Point) -> Unit): Tray /* this */ =
        definedExternally

    open fun once(
        event: String /* "mouse-move" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "mouse-move" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "mouse-move" */,
        listener: (event: Event, position: Point) -> Unit
    ): Tray /* this */ = definedExternally

    open fun on(
        event: String /* "right-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun once(
        event: String /* "right-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun addListener(
        event: String /* "right-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun removeListener(
        event: String /* "right-click" */,
        listener: (event: Event, bounds: Rectangle) -> Unit
    ): Tray /* this */ = definedExternally

    open fun destroy(): Unit = definedExternally
    open fun displayBalloon(options: DisplayBalloonOptions): Unit = definedExternally
    open fun getBounds(): Rectangle = definedExternally
    open fun isDestroyed(): Boolean = definedExternally
    open fun popUpContextMenu(
        menu: Menu? = definedExternally /* null */,
        position: Point? = definedExternally /* null */
    ): Unit = definedExternally

    open fun setContextMenu(menu: Menu): Unit = definedExternally
    open fun setHighlightMode(mode: String /* "selection" */): Unit = definedExternally
    open fun setHighlightMode(mode: String /* "always" */): Unit = definedExternally
    open fun setHighlightMode(mode: String /* "never" */): Unit = definedExternally
    open fun setImage(image: String): Unit = definedExternally
    open fun setImage(image: NativeImage): Unit = definedExternally
    open fun setPressedImage(image: NativeImage): Unit = definedExternally
    open fun setTitle(title: String): Unit = definedExternally
    open fun setToolTip(toolTip: String): Unit = definedExternally
}

external interface UploadBlob {
    var blobUUID: String
    var type: String
}

external interface UploadData {
    var blobUUID: String
    var bytes: Buffer
    var file: String
}

external interface UploadFile {
    var filePath: String
    var length: Number
    var modificationTime: Number
    var offset: Number
    var type: String
}

external interface UploadFileSystem {
    var filsSystemURL: String
    var length: Number
    var modificationTime: Number
    var offset: Number
    var type: String
}

external interface UploadRawData {
    var bytes: Buffer
    var type: String
}

open external class WebContents : EventEmitter {
    open fun on(
        event: String /* "before-input-event" */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "before-input-event" */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "before-input-event" */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "before-input-event" */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "certificate-error" */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "certificate-error" */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "certificate-error" */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "certificate-error" */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "console-message" */,
        listener: (level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "console-message" */,
        listener: (level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "console-message" */,
        listener: (level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "console-message" */,
        listener: (level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "context-menu" */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "context-menu" */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "context-menu" */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "context-menu" */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "crashed" */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "cursor-changed" */,
        listener: (event: Event, type: String, image: NativeImage? /*= null*/, scale: Number? /*= null*/, size: Size? /*= null*/, hotspot: Point? /*= null*/) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "cursor-changed" */,
        listener: (event: Event, type: String, image: NativeImage? /*= null*/, scale: Number? /*= null*/, size: Size? /*= null*/, hotspot: Point? /*= null*/) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "cursor-changed" */,
        listener: (event: Event, type: String, image: NativeImage? /*= null*/, scale: Number? /*= null*/, size: Size? /*= null*/, hotspot: Point? /*= null*/) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "cursor-changed" */,
        listener: (event: Event, type: String, image: NativeImage? /*= null*/, scale: Number? /*= null*/, size: Size? /*= null*/, hotspot: Point? /*= null*/) -> Unit
    ): WebContents /* this */ = definedExternally

    override fun on(event: String /* "destroyed" */, listener: Function<*>): WebContents /* this */ = definedExternally
    override fun once(event: String /* "destroyed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "destroyed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "destroyed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun on(event: String /* "devtools-closed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "devtools-closed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "devtools-closed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "devtools-closed" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun on(event: String /* "devtools-focused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "devtools-focused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "devtools-focused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "devtools-focused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun on(event: String /* "devtools-opened" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "devtools-opened" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "devtools-opened" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "devtools-opened" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun on(event: String /* "devtools-reload-page" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "devtools-reload-page" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(
        event: String /* "devtools-reload-page" */,
        listener: Function<*>
    ): WebContents /* this */ = definedExternally

    override fun removeListener(
        event: String /* "devtools-reload-page" */,
        listener: Function<*>
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-attach-webview" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-attach-webview" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-attach-webview" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-attach-webview" */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-change-theme-color" */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-change-theme-color" */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-change-theme-color" */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-change-theme-color" */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-fail-load" */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-fail-load" */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-fail-load" */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-fail-load" */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    override fun on(event: String /* "did-finish-load" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "did-finish-load" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "did-finish-load" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "did-finish-load" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    open fun on(
        event: String /* "did-frame-finish-load" */,
        listener: (event: Event, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-frame-finish-load" */,
        listener: (event: Event, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-frame-finish-load" */,
        listener: (event: Event, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-frame-finish-load" */,
        listener: (event: Event, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-get-redirect-request" */,
        listener: (event: Event, oldURL: String, newURL: String, isMainFrame: Boolean, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-get-redirect-request" */,
        listener: (event: Event, oldURL: String, newURL: String, isMainFrame: Boolean, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-get-redirect-request" */,
        listener: (event: Event, oldURL: String, newURL: String, isMainFrame: Boolean, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-get-redirect-request" */,
        listener: (event: Event, oldURL: String, newURL: String, isMainFrame: Boolean, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-get-response-details" */,
        listener: (event: Event, status: Boolean, newURL: String, originalURL: String, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any, resourceType: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-get-response-details" */,
        listener: (event: Event, status: Boolean, newURL: String, originalURL: String, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any, resourceType: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-get-response-details" */,
        listener: (event: Event, status: Boolean, newURL: String, originalURL: String, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any, resourceType: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-get-response-details" */,
        listener: (event: Event, status: Boolean, newURL: String, originalURL: String, httpResponseCode: Number, requestMethod: String, referrer: String, headers: Any, resourceType: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "did-navigate-in-page" */,
        listener: (event: Event, url: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "did-navigate-in-page" */,
        listener: (event: Event, url: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "did-navigate-in-page" */,
        listener: (event: Event, url: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "did-navigate-in-page" */,
        listener: (event: Event, url: String, isMainFrame: Boolean) -> Unit
    ): WebContents /* this */ = definedExternally

    override fun on(event: String /* "did-start-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "did-start-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "did-start-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(
        event: String /* "did-start-loading" */,
        listener: Function<*>
    ): WebContents /* this */ = definedExternally

    override fun on(event: String /* "did-stop-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "did-stop-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "did-stop-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "did-stop-loading" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    open fun on(event: String /* "dom-ready" */, listener: (event: Event) -> Unit): WebContents /* this */ =
        definedExternally

    open fun once(event: String /* "dom-ready" */, listener: (event: Event) -> Unit): WebContents /* this */ =
        definedExternally

    open fun addListener(event: String /* "dom-ready" */, listener: (event: Event) -> Unit): WebContents /* this */ =
        definedExternally

    open fun removeListener(event: String /* "dom-ready" */, listener: (event: Event) -> Unit): WebContents /* this */ =
        definedExternally

    open fun on(
        event: String /* "found-in-page" */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "found-in-page" */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "found-in-page" */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "found-in-page" */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "login" */,
        listener: (event: Event, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "login" */,
        listener: (event: Event, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "login" */,
        listener: (event: Event, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "login" */,
        listener: (event: Event, request: Request, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    override fun on(event: String /* "media-paused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "media-paused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(event: String /* "media-paused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun removeListener(event: String /* "media-paused" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun on(event: String /* "media-started-playing" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun once(event: String /* "media-started-playing" */, listener: Function<*>): WebContents /* this */ =
        definedExternally

    override fun addListener(
        event: String /* "media-started-playing" */,
        listener: Function<*>
    ): WebContents /* this */ = definedExternally

    override fun removeListener(
        event: String /* "media-started-playing" */,
        listener: Function<*>
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "new-window" */,
        listener: (event: Event, url: String, frameName: String, disposition: dynamic /* String /* "new-window" */ | String /* "default" */ | String /* "foreground-tab" */ | String /* "background-tab" */ | String /* "save-to-disk" */ | String /* "other" */ */, options: Any, additionalFeatures: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "new-window" */,
        listener: (event: Event, url: String, frameName: String, disposition: dynamic /* String /* "new-window" */ | String /* "default" */ | String /* "foreground-tab" */ | String /* "background-tab" */ | String /* "save-to-disk" */ | String /* "other" */ */, options: Any, additionalFeatures: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "new-window" */,
        listener: (event: Event, url: String, frameName: String, disposition: dynamic /* String /* "new-window" */ | String /* "default" */ | String /* "foreground-tab" */ | String /* "background-tab" */ | String /* "save-to-disk" */ | String /* "other" */ */, options: Any, additionalFeatures: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "new-window" */,
        listener: (event: Event, url: String, frameName: String, disposition: dynamic /* String /* "new-window" */ | String /* "default" */ | String /* "foreground-tab" */ | String /* "background-tab" */ | String /* "save-to-disk" */ | String /* "other" */ */, options: Any, additionalFeatures: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "page-favicon-updated" */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "page-favicon-updated" */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "page-favicon-updated" */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "page-favicon-updated" */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "paint" */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "paint" */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "paint" */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "paint" */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "plugin-crashed" */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "plugin-crashed" */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "plugin-crashed" */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "plugin-crashed" */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "select-bluetooth-device" */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "select-bluetooth-device" */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "select-bluetooth-device" */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "select-bluetooth-device" */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "select-client-certificate" */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "update-target-url" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "update-target-url" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "update-target-url" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "update-target-url" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "will-attach-webview" */,
        listener: (event: Event, webPreferences: Any, params: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "will-attach-webview" */,
        listener: (event: Event, webPreferences: Any, params: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "will-attach-webview" */,
        listener: (event: Event, webPreferences: Any, params: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "will-attach-webview" */,
        listener: (event: Event, webPreferences: Any, params: Any) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(
        event: String /* "will-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun once(
        event: String /* "will-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addListener(
        event: String /* "will-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "will-navigate" */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun on(event: String /* "will-prevent-unload" */, listener: (event: Event) -> Unit): WebContents /* this */ =
        definedExternally

    open fun once(event: String /* "will-prevent-unload" */, listener: (event: Event) -> Unit): WebContents /* this */ =
        definedExternally

    open fun addListener(
        event: String /* "will-prevent-unload" */,
        listener: (event: Event) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun removeListener(
        event: String /* "will-prevent-unload" */,
        listener: (event: Event) -> Unit
    ): WebContents /* this */ = definedExternally

    open fun addWorkSpace(path: String): Unit = definedExternally
    open fun beginFrameSubscription(callback: (frameBuffer: Buffer, dirtyRect: Rectangle) -> Unit): Unit =
        definedExternally

    open fun beginFrameSubscription(
        onlyDirty: Boolean,
        callback: (frameBuffer: Buffer, dirtyRect: Rectangle) -> Unit
    ): Unit = definedExternally

    open fun canGoBack(): Boolean = definedExternally
    open fun canGoForward(): Boolean = definedExternally
    open fun canGoToOffset(offset: Number): Boolean = definedExternally
    open fun capturePage(callback: (image: NativeImage) -> Unit): Unit = definedExternally
    open fun capturePage(rect: Rectangle, callback: (image: NativeImage) -> Unit): Unit = definedExternally
    open fun clearHistory(): Unit = definedExternally
    open fun closeDevTools(): Unit = definedExternally
    open fun copy(): Unit = definedExternally
    open fun copyImageAt(x: Number, y: Number): Unit = definedExternally
    open fun cut(): Unit = definedExternally
    open fun delete(): Unit = definedExternally
    open fun disableDeviceEmulation(): Unit = definedExternally
    open fun downloadURL(url: String): Unit = definedExternally
    open fun enableDeviceEmulation(parameters: Parameters): Unit = definedExternally
    open fun endFrameSubscription(): Unit = definedExternally
    open fun executeJavaScript(
        code: String,
        userGesture: Boolean? = definedExternally /* null */,
        callback: ((result: Any) -> Unit)? = definedExternally /* null */
    ): Promise<Any> = definedExternally

    open fun findInPage(text: String, options: FindInPageOptions? = definedExternally /* null */): Number =
        definedExternally

    open fun focus(): Unit = definedExternally
    open fun getFrameRate(): Number = definedExternally
    open fun getOSProcessId(): Number = definedExternally
    open fun getPrinters(): Array<PrinterInfo> = definedExternally
    open fun getTitle(): String = definedExternally
    open fun getURL(): String = definedExternally
    open fun getUserAgent(): String = definedExternally
    open fun getWebRTCIPHandlingPolicy(): String = definedExternally
    open fun getZoomFactor(callback: (zoomFactor: Number) -> Unit): Unit = definedExternally
    open fun getZoomLevel(callback: (zoomLevel: Number) -> Unit): Unit = definedExternally
    open fun goBack(): Unit = definedExternally
    open fun goForward(): Unit = definedExternally
    open fun goToIndex(index: Number): Unit = definedExternally
    open fun goToOffset(offset: Number): Unit = definedExternally
    open fun hasServiceWorker(callback: (hasWorker: Boolean) -> Unit): Unit = definedExternally
    open fun insertCSS(css: String): Unit = definedExternally
    open fun insertText(text: String): Unit = definedExternally
    open fun inspectElement(x: Number, y: Number): Unit = definedExternally
    open fun inspectServiceWorker(): Unit = definedExternally
    open fun invalidate(): Unit = definedExternally
    open fun isAudioMuted(): Boolean = definedExternally
    open fun isCrashed(): Boolean = definedExternally
    open fun isDestroyed(): Boolean = definedExternally
    open fun isDevToolsFocused(): Boolean = definedExternally
    open fun isDevToolsOpened(): Boolean = definedExternally
    open fun isFocused(): Boolean = definedExternally
    open fun isLoading(): Boolean = definedExternally
    open fun isLoadingMainFrame(): Boolean = definedExternally
    open fun isOffscreen(): Boolean = definedExternally
    open fun isPainting(): Boolean = definedExternally
    open fun isWaitingForResponse(): Boolean = definedExternally
    open fun loadFile(filePath: String): Unit = definedExternally
    open fun loadURL(url: String, options: LoadURLOptions? = definedExternally /* null */): Unit = definedExternally
    open fun openDevTools(options: OpenDevToolsOptions? = definedExternally /* null */): Unit = definedExternally
    open fun paste(): Unit = definedExternally
    open fun pasteAndMatchStyle(): Unit = definedExternally
    open fun print(
        options: PrintOptions? = definedExternally /* null */,
        callback: ((success: Boolean) -> Unit)? = definedExternally /* null */
    ): Unit = definedExternally

    open fun printToPDF(options: PrintToPDFOptions, callback: (error: Error, data: Buffer) -> Unit): Unit =
        definedExternally

    open fun redo(): Unit = definedExternally
    open fun reload(): Unit = definedExternally
    open fun reloadIgnoringCache(): Unit = definedExternally
    open fun removeWorkSpace(path: String): Unit = definedExternally
    open fun replace(text: String): Unit = definedExternally
    open fun replaceMisspelling(text: String): Unit = definedExternally
    open fun savePage(fullPath: String, saveType: String /* "HTMLOnly" */, callback: (error: Error) -> Unit): Boolean =
        definedExternally

    open fun savePage(
        fullPath: String,
        saveType: String /* "HTMLComplete" */,
        callback: (error: Error) -> Unit
    ): Boolean = definedExternally

    open fun savePage(fullPath: String, saveType: String /* "MHTML" */, callback: (error: Error) -> Unit): Boolean =
        definedExternally

    open fun selectAll(): Unit = definedExternally
    open fun send(channel: String, vararg args: Any): Unit = definedExternally
    open fun sendInputEvent(event: Event): Unit = definedExternally
    open fun setAudioMuted(muted: Boolean): Unit = definedExternally
    open fun setDevToolsWebContents(devToolsWebContents: WebContents): Unit = definedExternally
    open fun setFrameRate(fps: Number): Unit = definedExternally
    open fun setIgnoreMenuShortcuts(ignore: Boolean): Unit = definedExternally
    open fun setLayoutZoomLevelLimits(minimumLevel: Number, maximumLevel: Number): Unit = definedExternally
    open fun setSize(options: SizeOptions): Unit = definedExternally
    open fun setUserAgent(userAgent: String): Unit = definedExternally
    open fun setVisualZoomLevelLimits(minimumLevel: Number, maximumLevel: Number): Unit = definedExternally
    open fun setWebRTCIPHandlingPolicy(policy: String /* "default" */): Unit = definedExternally
    open fun setWebRTCIPHandlingPolicy(policy: String /* "default_public_interface_only" */): Unit = definedExternally
    open fun setWebRTCIPHandlingPolicy(policy: String /* "default_public_and_private_interfaces" */): Unit =
        definedExternally

    open fun setWebRTCIPHandlingPolicy(policy: String /* "disable_non_proxied_udp" */): Unit = definedExternally
    open fun setZoomFactor(factor: Number): Unit = definedExternally
    open fun setZoomLevel(level: Number): Unit = definedExternally
    open fun showDefinitionForSelection(): Unit = definedExternally
    open fun startDrag(item: Item): Unit = definedExternally
    open fun startPainting(): Unit = definedExternally
    open fun stop(): Unit = definedExternally
    open fun stopFindInPage(action: String /* "clearSelection" */): Unit = definedExternally
    open fun stopFindInPage(action: String /* "keepSelection" */): Unit = definedExternally
    open fun stopFindInPage(action: String /* "activateSelection" */): Unit = definedExternally
    open fun stopPainting(): Unit = definedExternally
    open fun toggleDevTools(): Unit = definedExternally
    open fun undo(): Unit = definedExternally
    open fun unregisterServiceWorker(callback: (success: Boolean) -> Unit): Unit = definedExternally
    open fun unselect(): Unit = definedExternally
    open var debugger: Debugger = definedExternally
    open var devToolsWebContents: WebContents = definedExternally
    open var hostWebContents: WebContents = definedExternally
    open var id: Number = definedExternally
    open var session: Session = definedExternally

    companion object {
        fun fromId(id: Number): WebContents = definedExternally
        fun getAllWebContents(): Array<WebContents> = definedExternally
        fun getFocusedWebContents(): WebContents = definedExternally
    }
}

external interface WebFrame : EventEmitter {
    fun clearCache()
    fun executeJavaScript(
        code: String,
        userGesture: Boolean? = definedExternally /* null */,
        callback: ((result: Any) -> Unit)? = definedExternally /* null */
    ): Promise<Any>

    fun executeJavaScriptInIsolatedWorld(
        worldId: Number,
        scripts: Array<WebSource>,
        userGesture: Boolean? = definedExternally /* null */,
        callback: ((result: Any) -> Unit)? = definedExternally /* null */
    )

    fun getResourceUsage(): ResourceUsage
    fun getZoomFactor(): Number
    fun getZoomLevel(): Number
    fun insertText(text: String)
    fun registerURLSchemeAsBypassingCSP(scheme: String)
    fun registerURLSchemeAsPrivileged(
        scheme: String,
        options: RegisterURLSchemeAsPrivilegedOptions? = definedExternally /* null */
    )

    fun registerURLSchemeAsSecure(scheme: String)
    fun setIsolatedWorldContentSecurityPolicy(worldId: Number, csp: String)
    fun setIsolatedWorldHumanReadableName(worldId: Number, name: String)
    fun setIsolatedWorldSecurityOrigin(worldId: Number, securityOrigin: String)
    fun setLayoutZoomLevelLimits(minimumLevel: Number, maximumLevel: Number)
    fun setSpellCheckProvider(language: String, autoCorrectWord: Boolean, provider: Provider)
    fun setVisualZoomLevelLimits(minimumLevel: Number, maximumLevel: Number)
    fun setZoomFactor(factor: Number)
    fun setZoomLevel(level: Number)
}

open external class WebRequest : EventEmitter {
    open fun onBeforeRedirect(listener: (details: OnBeforeRedirectDetails) -> Unit): Unit = definedExternally
    open fun onBeforeRedirect(
        filter: OnBeforeRedirectFilter,
        listener: (details: OnBeforeRedirectDetails) -> Unit
    ): Unit = definedExternally

    open fun onBeforeRequest(listener: (details: OnBeforeRequestDetails, callback: (response: Response) -> Unit) -> Unit): Unit =
        definedExternally

    open fun onBeforeRequest(
        filter: OnBeforeRequestFilter,
        listener: (details: OnBeforeRequestDetails, callback: (response: Response) -> Unit) -> Unit
    ): Unit = definedExternally

    open fun onBeforeSendHeaders(filter: OnBeforeSendHeadersFilter, listener: Function<*>): Unit = definedExternally
    open fun onBeforeSendHeaders(listener: Function<*>): Unit = definedExternally
    open fun onCompleted(filter: OnCompletedFilter, listener: (details: OnCompletedDetails) -> Unit): Unit =
        definedExternally

    open fun onCompleted(listener: (details: OnCompletedDetails) -> Unit): Unit = definedExternally
    open fun onErrorOccurred(listener: (details: OnErrorOccurredDetails) -> Unit): Unit = definedExternally
    open fun onErrorOccurred(filter: OnErrorOccurredFilter, listener: (details: OnErrorOccurredDetails) -> Unit): Unit =
        definedExternally

    open fun onHeadersReceived(filter: OnHeadersReceivedFilter, listener: Function<*>): Unit = definedExternally
    open fun onHeadersReceived(listener: Function<*>): Unit = definedExternally
    open fun onResponseStarted(listener: (details: OnResponseStartedDetails) -> Unit): Unit = definedExternally
    open fun onResponseStarted(
        filter: OnResponseStartedFilter,
        listener: (details: OnResponseStartedDetails) -> Unit
    ): Unit = definedExternally

    open fun onSendHeaders(filter: OnSendHeadersFilter, listener: (details: OnSendHeadersDetails) -> Unit): Unit =
        definedExternally

    open fun onSendHeaders(listener: (details: OnSendHeadersDetails) -> Unit): Unit = definedExternally
}

external interface WebSource {
    var code: String
    var startLine: Number? get() = definedExternally; set(value) = definedExternally
    var url: String? get() = definedExternally; set(value) = definedExternally
}

external interface WebviewTag : HTMLElement {
    fun addEventListener(
        event: String /* "load-commit" */,
        listener: (event: LoadCommitEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "load-commit" */,
        listener: (event: LoadCommitEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-finish-load" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-finish-load" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-fail-load" */,
        listener: (event: DidFailLoadEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-fail-load" */,
        listener: (event: DidFailLoadEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-frame-finish-load" */,
        listener: (event: DidFrameFinishLoadEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-frame-finish-load" */,
        listener: (event: DidFrameFinishLoadEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-start-loading" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-start-loading" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-stop-loading" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-stop-loading" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-get-response-details" */,
        listener: (event: DidGetResponseDetailsEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-get-response-details" */,
        listener: (event: DidGetResponseDetailsEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-get-redirect-request" */,
        listener: (event: DidGetRedirectRequestEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-get-redirect-request" */,
        listener: (event: DidGetRedirectRequestEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "dom-ready" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(event: String /* "dom-ready" */, listener: (event: Event) -> Unit): WebviewTag /* this */
    fun addEventListener(
        event: String /* "page-title-updated" */,
        listener: (event: PageTitleUpdatedEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "page-title-updated" */,
        listener: (event: PageTitleUpdatedEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "page-favicon-updated" */,
        listener: (event: PageFaviconUpdatedEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "page-favicon-updated" */,
        listener: (event: PageFaviconUpdatedEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "enter-html-full-screen" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "enter-html-full-screen" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "leave-html-full-screen" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "leave-html-full-screen" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "console-message" */,
        listener: (event: ConsoleMessageEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "console-message" */,
        listener: (event: ConsoleMessageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "found-in-page" */,
        listener: (event: FoundInPageEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "found-in-page" */,
        listener: (event: FoundInPageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "new-window" */,
        listener: (event: NewWindowEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "new-window" */,
        listener: (event: NewWindowEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "will-navigate" */,
        listener: (event: WillNavigateEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "will-navigate" */,
        listener: (event: WillNavigateEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-navigate" */,
        listener: (event: DidNavigateEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-navigate" */,
        listener: (event: DidNavigateEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "did-navigate-in-page" */,
        listener: (event: DidNavigateInPageEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-navigate-in-page" */,
        listener: (event: DidNavigateInPageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "close" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(event: String /* "close" */, listener: (event: Event) -> Unit): WebviewTag /* this */
    fun addEventListener(
        event: String /* "ipc-message" */,
        listener: (event: IpcMessageEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "ipc-message" */,
        listener: (event: IpcMessageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "crashed" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(event: String /* "crashed" */, listener: (event: Event) -> Unit): WebviewTag /* this */
    fun addEventListener(
        event: String /* "gpu-crashed" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(event: String /* "gpu-crashed" */, listener: (event: Event) -> Unit): WebviewTag /* this */
    fun addEventListener(
        event: String /* "plugin-crashed" */,
        listener: (event: PluginCrashedEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "plugin-crashed" */,
        listener: (event: PluginCrashedEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "destroyed" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(event: String /* "destroyed" */, listener: (event: Event) -> Unit): WebviewTag /* this */
    fun addEventListener(
        event: String /* "media-started-playing" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "media-started-playing" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "media-paused" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(event: String /* "media-paused" */, listener: (event: Event) -> Unit): WebviewTag /* this */
    fun addEventListener(
        event: String /* "did-change-theme-color" */,
        listener: (event: DidChangeThemeColorEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "did-change-theme-color" */,
        listener: (event: DidChangeThemeColorEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "update-target-url" */,
        listener: (event: UpdateTargetUrlEvent) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "update-target-url" */,
        listener: (event: UpdateTargetUrlEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "devtools-opened" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "devtools-opened" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "devtools-closed" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "devtools-closed" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* "devtools-focused" */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean? = definedExternally /* null */
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* "devtools-focused" */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun <K> addEventListener(
        type: K,
        listener: (`this`: HTMLElement, ev: Any?) -> Any,
        useCapture: Boolean? = definedExternally /* null */
    )

    fun addEventListener(type: String, listener: EventListener, useCapture: Boolean? = definedExternally /* null */)
    fun addEventListener(
        type: String,
        listener: EventListenerObject,
        useCapture: Boolean? = definedExternally /* null */
    )

    fun <K> removeEventListener(
        type: K,
        listener: (`this`: HTMLElement, ev: Any?) -> Any,
        useCapture: Boolean? = definedExternally /* null */
    )

    fun removeEventListener(type: String, listener: EventListener, useCapture: Boolean? = definedExternally /* null */)
    fun removeEventListener(
        type: String,
        listener: EventListenerObject,
        useCapture: Boolean? = definedExternally /* null */
    )

    fun canGoBack(): Boolean
    fun canGoForward(): Boolean
    fun canGoToOffset(offset: Number): Boolean
    fun capturePage(callback: (image: NativeImage) -> Unit)
    fun capturePage(rect: Rectangle, callback: (image: NativeImage) -> Unit)
    fun clearHistory()
    fun closeDevTools()
    fun copy()
    fun cut()
    fun delete()
    fun executeJavaScript(
        code: String,
        userGesture: Boolean? = definedExternally /* null */,
        callback: ((result: Any) -> Unit)? = definedExternally /* null */
    )

    fun findInPage(text: String, options: FindInPageOptions? = definedExternally /* null */): Number
    fun getTitle(): String
    fun getURL(): String
    fun getUserAgent(): String
    fun getWebContents(): WebContents
    fun goBack()
    fun goForward()
    fun goToIndex(index: Number)
    fun goToOffset(offset: Number)
    fun insertCSS(css: String)
    fun insertText(text: String)
    fun inspectElement(x: Number, y: Number)
    fun inspectServiceWorker()
    fun isAudioMuted(): Boolean
    fun isCrashed(): Boolean
    fun isDevToolsFocused(): Boolean
    fun isDevToolsOpened(): Boolean
    fun isLoading(): Boolean
    fun isWaitingForResponse(): Boolean
    fun loadURL(url: String, options: LoadURLOptions? = definedExternally /* null */)
    fun openDevTools()
    fun paste()
    fun pasteAndMatchStyle()
    fun print(options: PrintOptions? = definedExternally /* null */)
    fun printToPDF(options: PrintToPDFOptions, callback: (error: Error, data: Buffer) -> Unit)
    fun redo()
    fun reload()
    fun reloadIgnoringCache()
    fun replace(text: String)
    fun replaceMisspelling(text: String)
    fun selectAll()
    fun send(channel: String, vararg args: Any)
    fun sendInputEvent(event: Any)
    fun setAudioMuted(muted: Boolean)
    fun setUserAgent(userAgent: String)
    fun setZoomFactor(factor: Number)
    fun setZoomLevel(level: Number)
    fun showDefinitionForSelection()
    fun stop()
    fun stopFindInPage(action: String /* "clearSelection" */)
    fun stopFindInPage(action: String /* "keepSelection" */)
    fun stopFindInPage(action: String /* "activateSelection" */)
    fun undo()
    fun unselect()
    var allowpopups: String? get() = definedExternally; set(value) = definedExternally
    var autosize: String? get() = definedExternally; set(value) = definedExternally
    var blinkfeatures: String? get() = definedExternally; set(value) = definedExternally
    var disableblinkfeatures: String? get() = definedExternally; set(value) = definedExternally
    var disableguestresize: String? get() = definedExternally; set(value) = definedExternally
    var disablewebsecurity: String? get() = definedExternally; set(value) = definedExternally
    var guestinstance: String? get() = definedExternally; set(value) = definedExternally
    var httpreferrer: String? get() = definedExternally; set(value) = definedExternally
    var nodeintegration: String? get() = definedExternally; set(value) = definedExternally
    var partition: String? get() = definedExternally; set(value) = definedExternally
    var plugins: String? get() = definedExternally; set(value) = definedExternally
    var preload: String? get() = definedExternally; set(value) = definedExternally
    var src: String? get() = definedExternally; set(value) = definedExternally
    var useragent: String? get() = definedExternally; set(value) = definedExternally
    var webpreferences: String? get() = definedExternally; set(value) = definedExternally
}

external interface AboutPanelOptionsOptions {
    var applicationName: String? get() = definedExternally; set(value) = definedExternally
    var applicationVersion: String? get() = definedExternally; set(value) = definedExternally
    var copyright: String? get() = definedExternally; set(value) = definedExternally
    var credits: String? get() = definedExternally; set(value) = definedExternally
    var version: String? get() = definedExternally; set(value) = definedExternally
}

external interface AddRepresentationOptions {
    var scaleFactor: Number
    var width: Number? get() = definedExternally; set(value) = definedExternally
    var height: Number? get() = definedExternally; set(value) = definedExternally
    var buffer: Buffer? get() = definedExternally; set(value) = definedExternally
    var dataURL: String? get() = definedExternally; set(value) = definedExternally
}

external interface AppDetailsOptions {
    var appId: String? get() = definedExternally; set(value) = definedExternally
    var appIconPath: String? get() = definedExternally; set(value) = definedExternally
    var appIconIndex: Number? get() = definedExternally; set(value) = definedExternally
    var relaunchCommand: String? get() = definedExternally; set(value) = definedExternally
    var relaunchDisplayName: String? get() = definedExternally; set(value) = definedExternally
}

external interface AuthInfo {
    var isProxy: Boolean
    var scheme: String
    var host: String
    var port: Number
    var realm: String
}

external interface AutoResizeOptions {
    var width: Boolean
    var height: Boolean
}

external interface BitmapOptions {
    var scaleFactor: Number? get() = definedExternally; set(value) = definedExternally
}

external interface BrowserViewConstructorOptions {
    var webPreferences: WebPreferences? get() = definedExternally; set(value) = definedExternally
}

external interface BrowserWindowConstructorOptions {
    var width: Number? get() = definedExternally; set(value) = definedExternally
    var height: Number? get() = definedExternally; set(value) = definedExternally
    var x: Number? get() = definedExternally; set(value) = definedExternally
    var y: Number? get() = definedExternally; set(value) = definedExternally
    var useContentSize: Boolean? get() = definedExternally; set(value) = definedExternally
    var center: Boolean? get() = definedExternally; set(value) = definedExternally
    var minWidth: Number? get() = definedExternally; set(value) = definedExternally
    var minHeight: Number? get() = definedExternally; set(value) = definedExternally
    var maxWidth: Number? get() = definedExternally; set(value) = definedExternally
    var maxHeight: Number? get() = definedExternally; set(value) = definedExternally
    var resizable: Boolean? get() = definedExternally; set(value) = definedExternally
    var movable: Boolean? get() = definedExternally; set(value) = definedExternally
    var minimizable: Boolean? get() = definedExternally; set(value) = definedExternally
    var maximizable: Boolean? get() = definedExternally; set(value) = definedExternally
    var closable: Boolean? get() = definedExternally; set(value) = definedExternally
    var focusable: Boolean? get() = definedExternally; set(value) = definedExternally
    var alwaysOnTop: Boolean? get() = definedExternally; set(value) = definedExternally
    var fullscreen: Boolean? get() = definedExternally; set(value) = definedExternally
    var fullscreenable: Boolean? get() = definedExternally; set(value) = definedExternally
    var simpleFullscreen: Boolean? get() = definedExternally; set(value) = definedExternally
    var skipTaskbar: Boolean? get() = definedExternally; set(value) = definedExternally
    var kiosk: Boolean? get() = definedExternally; set(value) = definedExternally
    var title: String? get() = definedExternally; set(value) = definedExternally
    var icon: dynamic /* String | NativeImage */ get() = definedExternally; set(value) = definedExternally
    var show: Boolean? get() = definedExternally; set(value) = definedExternally
    var frame: Boolean? get() = definedExternally; set(value) = definedExternally
    var parent: BrowserWindow? get() = definedExternally; set(value) = definedExternally
    var modal: Boolean? get() = definedExternally; set(value) = definedExternally
    var acceptFirstMouse: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableAutoHideCursor: Boolean? get() = definedExternally; set(value) = definedExternally
    var autoHideMenuBar: Boolean? get() = definedExternally; set(value) = definedExternally
    var enableLargerThanScreen: Boolean? get() = definedExternally; set(value) = definedExternally
    var backgroundColor: String? get() = definedExternally; set(value) = definedExternally
    var hasShadow: Boolean? get() = definedExternally; set(value) = definedExternally
    var opacity: Number? get() = definedExternally; set(value) = definedExternally
    var darkTheme: Boolean? get() = definedExternally; set(value) = definedExternally
    var transparent: Boolean? get() = definedExternally; set(value) = definedExternally
    var type: String? get() = definedExternally; set(value) = definedExternally
    var titleBarStyle: dynamic /* String /* "default" */ | String /* "hidden" */ | String /* "hiddenInset" */ | String /* "customButtonsOnHover" */ */ get() = definedExternally; set(value) = definedExternally
    var fullscreenWindowTitle: Boolean? get() = definedExternally; set(value) = definedExternally
    var thickFrame: Boolean? get() = definedExternally; set(value) = definedExternally
    var vibrancy: dynamic /* String /* "appearance-based" */ | String /* "light" */ | String /* "dark" */ | String /* "titlebar" */ | String /* "selection" */ | String /* "menu" */ | String /* "popover" */ | String /* "sidebar" */ | String /* "medium-light" */ | String /* "ultra-dark" */ */ get() = definedExternally; set(value) = definedExternally
    var zoomToPageWidth: Boolean? get() = definedExternally; set(value) = definedExternally
    var tabbingIdentifier: String? get() = definedExternally; set(value) = definedExternally
    var webPreferences: WebPreferences? get() = definedExternally; set(value) = definedExternally
}

external interface CertificateTrustDialogOptions {
    var certificate: Certificate
    var message: String
}

external interface CertificateVerifyProcRequest {
    var hostname: String
    var certificate: Certificate
    var verificationResult: String
    var errorCode: Number
}

external interface ClearStorageDataOptions {
    var origin: String? get() = definedExternally; set(value) = definedExternally
    var storages: Array<String>? get() = definedExternally; set(value) = definedExternally
    var quotas: Array<String>? get() = definedExternally; set(value) = definedExternally
}

external interface CommandLine {
    var appendSwitch: (the_switch: String, value: String? /*= null*/) -> Unit
    var appendArgument: (value: String) -> Unit
}

external interface Config {
    var pacScript: String
    var proxyRules: String
    var proxyBypassRules: String
}

external interface ConsoleMessageEvent : Event {
    var level: Number
    var message: String
    var line: Number
    var sourceId: String
}

external interface ContextMenuParams {
    var x: Number
    var y: Number
    var linkURL: String
    var linkText: String
    var pageURL: String
    var frameURL: String
    var srcURL: String
    var mediaType: dynamic /* String /* "file" */ | String /* "none" */ | String /* "image" */ | String /* "audio" */ | String /* "video" */ | String /* "canvas" */ | String /* "plugin" */ */
    var hasImageContents: Boolean
    var isEditable: Boolean
    var selectionText: String
    var titleText: String
    var misspelledWord: String
    var frameCharset: String
    var inputFieldType: String
    var menuSourceType: dynamic /* String /* "none" */ | String /* "mouse" */ | String /* "keyboard" */ | String /* "touch" */ | String /* "touchMenu" */ */
    var mediaFlags: MediaFlags
    var editFlags: EditFlags
}

external interface CrashReporterStartOptions {
    var companyName: String? get() = definedExternally; set(value) = definedExternally
    var submitURL: String
    var productName: String? get() = definedExternally; set(value) = definedExternally
    var uploadToServer: Boolean? get() = definedExternally; set(value) = definedExternally
    var ignoreSystemCrashHandler: Boolean? get() = definedExternally; set(value) = definedExternally
    var extra: Extra? get() = definedExternally; set(value) = definedExternally
    var crashesDirectory: String? get() = definedExternally; set(value) = definedExternally
}

external interface CreateFromBufferOptions {
    var width: Number? get() = definedExternally; set(value) = definedExternally
    var height: Number? get() = definedExternally; set(value) = definedExternally
    var scaleFactor: Number? get() = definedExternally; set(value) = definedExternally
}

external interface CreateInterruptedDownloadOptions {
    var path: String
    var urlChain: Array<String>
    var mimeType: String? get() = definedExternally; set(value) = definedExternally
    var offset: Number
    var length: Number
    var lastModified: String
    var eTag: String
    var startTime: Number? get() = definedExternally; set(value) = definedExternally
}

external interface Data {
    var text: String? get() = definedExternally; set(value) = definedExternally
    var html: String? get() = definedExternally; set(value) = definedExternally
    var image: NativeImage? get() = definedExternally; set(value) = definedExternally
    var rtf: String? get() = definedExternally; set(value) = definedExternally
    var bookmark: String? get() = definedExternally; set(value) = definedExternally
}

external interface Details {
    var url: String
    var name: String? get() = definedExternally; set(value) = definedExternally
    var value: String? get() = definedExternally; set(value) = definedExternally
    var domain: String? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var secure: Boolean? get() = definedExternally; set(value) = definedExternally
    var httpOnly: Boolean? get() = definedExternally; set(value) = definedExternally
    var expirationDate: Number? get() = definedExternally; set(value) = definedExternally
}

external interface DevToolsExtensions
external interface DidChangeThemeColorEvent : Event {
    var themeColor: String
}

external interface DidFailLoadEvent : Event {
    var errorCode: Number
    var errorDescription: String
    var validatedURL: String
    var isMainFrame: Boolean
}

external interface DidFrameFinishLoadEvent : Event {
    var isMainFrame: Boolean
}

external interface DidGetRedirectRequestEvent : Event {
    var oldURL: String
    var newURL: String
    var isMainFrame: Boolean
}

external interface DidGetResponseDetailsEvent : Event {
    var status: Boolean
    var newURL: String
    var originalURL: String
    var httpResponseCode: Number
    var requestMethod: String
    var referrer: String
    var headers: Headers
    var resourceType: String
}

external interface DidNavigateEvent : Event {
    var url: String
}

external interface DidNavigateInPageEvent : Event {
    var isMainFrame: Boolean
    var url: String
}

external interface DisplayBalloonOptions {
    var icon: dynamic /* String | NativeImage */ get() = definedExternally; set(value) = definedExternally
    var title: String
    var content: String
}

external interface Dock {
    var bounce: (type: dynamic /* String /* "critical" */ | String /* "informational" */ */ /*= null*/) -> Number
    var cancelBounce: (id: Number) -> Unit
    var downloadFinished: (filePath: String) -> Unit
    var setBadge: (text: String) -> Unit
    var getBadge: () -> String
    var hide: () -> Unit
    var show: () -> Unit
    var isVisible: () -> Boolean
    var setMenu: (menu: Menu) -> Unit
    var setIcon: (image: dynamic /* String | NativeImage */) -> Unit
}

external interface EnableNetworkEmulationOptions {
    var offline: Boolean? get() = definedExternally; set(value) = definedExternally
    var latency: Number? get() = definedExternally; set(value) = definedExternally
    var downloadThroughput: Number? get() = definedExternally; set(value) = definedExternally
    var uploadThroughput: Number? get() = definedExternally; set(value) = definedExternally
}

external interface Extensions
external interface FeedURLOptions {
    var url: String
    var headers: Headers? get() = definedExternally; set(value) = definedExternally
    var serverType: String? get() = definedExternally; set(value) = definedExternally
}

external interface FileIconOptions {
    var size: dynamic /* String /* "normal" */ | String /* "small" */ | String /* "large" */ */
}

external interface Filter {
    var url: String? get() = definedExternally; set(value) = definedExternally
    var name: String? get() = definedExternally; set(value) = definedExternally
    var domain: String? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var secure: Boolean? get() = definedExternally; set(value) = definedExternally
    var session: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface FindInPageOptions {
    var forward: Boolean? get() = definedExternally; set(value) = definedExternally
    var findNext: Boolean? get() = definedExternally; set(value) = definedExternally
    var matchCase: Boolean? get() = definedExternally; set(value) = definedExternally
    var wordStart: Boolean? get() = definedExternally; set(value) = definedExternally
    var medialCapitalAsWordStart: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface FoundInPageEvent : Event {
    var result: FoundInPageResult
}

external interface FromPartitionOptions {
    var cache: Boolean
}

external interface Header {
    var name: String
}

external interface Headers
external interface IgnoreMouseEventsOptions {
    var forward: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface ImportCertificateOptions {
    var certificate: String
    var password: String
}

external interface Input {
    var type: String
    var key: String
    var code: String
    var isAutoRepeat: Boolean
    var shift: Boolean
    var control: Boolean
    var alt: Boolean
    var meta: Boolean
}

external interface InterceptBufferProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface InterceptFileProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface InterceptHttpProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface InterceptStreamProtocolRequest {
    var url: String
    var headers: Headers
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface InterceptStringProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface IpcMessageEvent : Event {
    var channel: String
    var args: Array<Any>
}

external interface Item {
    var file: String
    var icon: NativeImage
}

external interface JumpListSettings {
    var minItems: Number
    var removedItems: Array<JumpListItem>
}

external interface LoadCommitEvent : Event {
    var url: String
    var isMainFrame: Boolean
}

external interface LoadURLOptions {
    var httpReferrer: String? get() = definedExternally; set(value) = definedExternally
    var userAgent: String? get() = definedExternally; set(value) = definedExternally
    var extraHeaders: String? get() = definedExternally; set(value) = definedExternally
    var postData: dynamic /* Array<UploadRawData> | Array<UploadFile> | Array<UploadFileSystem> | Array<UploadBlob> */ get() = definedExternally; set(value) = definedExternally
    var baseURLForDataURL: String? get() = definedExternally; set(value) = definedExternally
}

external interface LoginItemSettings {
    var options: Options? get() = definedExternally; set(value) = definedExternally
    var openAtLogin: Boolean
    var openAsHidden: Boolean
    var wasOpenedAtLogin: Boolean
    var wasOpenedAsHidden: Boolean
    var restoreState: Boolean
}

external interface LoginItemSettingsOptions {
    var path: String? get() = definedExternally; set(value) = definedExternally
    var args: Array<String>? get() = definedExternally; set(value) = definedExternally
}

external interface MenuItemConstructorOptions {
    var click: ((menuItem: MenuItem, browserWindow: BrowserWindow, event: Event) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var role: String? get() = definedExternally; set(value) = definedExternally
    var type: dynamic /* String /* "normal" */ | String /* "separator" */ | String /* "submenu" */ | String /* "checkbox" */ | String /* "radio" */ */ get() = definedExternally; set(value) = definedExternally
    var label: String? get() = definedExternally; set(value) = definedExternally
    var sublabel: String? get() = definedExternally; set(value) = definedExternally
    var accelerator: Accelerator? get() = definedExternally; set(value) = definedExternally
    var icon: dynamic /* String | NativeImage */ get() = definedExternally; set(value) = definedExternally
    var enabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var visible: Boolean? get() = definedExternally; set(value) = definedExternally
    var checked: Boolean? get() = definedExternally; set(value) = definedExternally
    var submenu: dynamic /* Menu | Array<MenuItemConstructorOptions> */ get() = definedExternally; set(value) = definedExternally
    var id: String? get() = definedExternally; set(value) = definedExternally
    var position: String? get() = definedExternally; set(value) = definedExternally
}

external interface MessageBoxOptions {
    var type: String? get() = definedExternally; set(value) = definedExternally
    var buttons: Array<String>? get() = definedExternally; set(value) = definedExternally
    var defaultId: Number? get() = definedExternally; set(value) = definedExternally
    var title: String? get() = definedExternally; set(value) = definedExternally
    var message: String
    var detail: String? get() = definedExternally; set(value) = definedExternally
    var checkboxLabel: String? get() = definedExternally; set(value) = definedExternally
    var checkboxChecked: Boolean? get() = definedExternally; set(value) = definedExternally
    var icon: NativeImage? get() = definedExternally; set(value) = definedExternally
    var cancelId: Number? get() = definedExternally; set(value) = definedExternally
    var noLink: Boolean? get() = definedExternally; set(value) = definedExternally
    var normalizeAccessKeys: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface NewWindowEvent : Event {
    var url: String
    var frameName: String
    var disposition: dynamic /* String /* "new-window" */ | String /* "default" */ | String /* "foreground-tab" */ | String /* "background-tab" */ | String /* "save-to-disk" */ | String /* "other" */ */
    var options: Options
}

external interface NotificationConstructorOptions {
    var title: String
    var subtitle: String? get() = definedExternally; set(value) = definedExternally
    var body: String
    var silent: Boolean? get() = definedExternally; set(value) = definedExternally
    var icon: dynamic /* String | NativeImage */ get() = definedExternally; set(value) = definedExternally
    var hasReply: Boolean? get() = definedExternally; set(value) = definedExternally
    var replyPlaceholder: String? get() = definedExternally; set(value) = definedExternally
    var sound: String? get() = definedExternally; set(value) = definedExternally
    var actions: Array<NotificationAction>? get() = definedExternally; set(value) = definedExternally
    var closeButtonText: String? get() = definedExternally; set(value) = definedExternally
}

external interface OnBeforeRedirectDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number? get() = definedExternally; set(value) = definedExternally
    var resourceType: String
    var timestamp: Number
    var redirectURL: String
    var statusCode: Number
    var ip: String? get() = definedExternally; set(value) = definedExternally
    var fromCache: Boolean
    var responseHeaders: ResponseHeaders
}

external interface OnBeforeRedirectFilter {
    var urls: Array<String>
}

external interface OnBeforeRequestDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number? get() = definedExternally; set(value) = definedExternally
    var resourceType: String
    var timestamp: Number
    var uploadData: Array<UploadData>
}

external interface OnBeforeRequestFilter {
    var urls: Array<String>
}

external interface OnBeforeSendHeadersFilter {
    var urls: Array<String>
}

external interface OnCompletedDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number? get() = definedExternally; set(value) = definedExternally
    var resourceType: String
    var timestamp: Number
    var responseHeaders: ResponseHeaders
    var fromCache: Boolean
    var statusCode: Number
    var statusLine: String
}

external interface OnCompletedFilter {
    var urls: Array<String>
}

external interface OnErrorOccurredDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number? get() = definedExternally; set(value) = definedExternally
    var resourceType: String
    var timestamp: Number
    var fromCache: Boolean
    var error: String
}

external interface OnErrorOccurredFilter {
    var urls: Array<String>
}

external interface OnHeadersReceivedFilter {
    var urls: Array<String>
}

external interface OnResponseStartedDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number? get() = definedExternally; set(value) = definedExternally
    var resourceType: String
    var timestamp: Number
    var responseHeaders: ResponseHeaders
    var fromCache: Boolean
    var statusCode: Number
    var statusLine: String
}

external interface OnResponseStartedFilter {
    var urls: Array<String>
}

external interface OnSendHeadersDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number? get() = definedExternally; set(value) = definedExternally
    var resourceType: String
    var timestamp: Number
    var requestHeaders: RequestHeaders
}

external interface OnSendHeadersFilter {
    var urls: Array<String>
}

external interface OpenDevToolsOptions {
    var mode: dynamic /* String /* "detach" */ | String /* "right" */ | String /* "bottom" */ | String /* "undocked" */ */
}

external interface OpenDialogOptions {
    var title: String? get() = definedExternally; set(value) = definedExternally
    var defaultPath: String? get() = definedExternally; set(value) = definedExternally
    var buttonLabel: String? get() = definedExternally; set(value) = definedExternally
    var filters: Array<FileFilter>? get() = definedExternally; set(value) = definedExternally
    var properties: Array<dynamic /* String /* "openFile" */ | String /* "openDirectory" */ | String /* "multiSelections" */ | String /* "showHiddenFiles" */ | String /* "createDirectory" */ | String /* "promptToCreate" */ | String /* "noResolveAliases" */ | String /* "treatPackageAsDirectory" */ */>? get() = definedExternally; set(value) = definedExternally
    var message: String? get() = definedExternally; set(value) = definedExternally
    var securityScopedBookmarks: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface OpenExternalOptions {
    var activate: Boolean
}

external interface PageFaviconUpdatedEvent : Event {
    var favicons: Array<String>
}

external interface PageTitleUpdatedEvent : Event {
    var title: String
    var explicitSet: Boolean
}

external interface Parameters {
    var screenPosition: dynamic /* String /* "desktop" */ | String /* "mobile" */ */
    var screenSize: Size
    var viewPosition: Point
    var deviceScaleFactor: Number
    var viewSize: Size
    var scale: Number
}

external interface Payment {
    var productIdentifier: String
    var quantity: Number
}

external interface PermissionRequestHandlerDetails {
    var externalURL: String
}

external interface PluginCrashedEvent : Event {
    var name: String
    var version: String
}

external interface PopupOptions {
    var window: BrowserWindow? get() = definedExternally; set(value) = definedExternally
    var x: Number? get() = definedExternally; set(value) = definedExternally
    var y: Number? get() = definedExternally; set(value) = definedExternally
    var positioningItem: Number? get() = definedExternally; set(value) = definedExternally
    var callback: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
}

external interface PrintOptions {
    var silent: Boolean? get() = definedExternally; set(value) = definedExternally
    var printBackground: Boolean? get() = definedExternally; set(value) = definedExternally
    var deviceName: String? get() = definedExternally; set(value) = definedExternally
}

external interface PrintToPDFOptions {
    var marginsType: Number? get() = definedExternally; set(value) = definedExternally
    var pageSize: String? get() = definedExternally; set(value) = definedExternally
    var printBackground: Boolean? get() = definedExternally; set(value) = definedExternally
    var printSelectionOnly: Boolean? get() = definedExternally; set(value) = definedExternally
    var landscape: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface ProcessMemoryInfo {
    var workingSetSize: Number
    var peakWorkingSetSize: Number
    var privateBytes: Number
    var sharedBytes: Number
}

external interface ProgressBarOptions {
    var mode: dynamic /* String /* "error" */ | String /* "normal" */ | String /* "none" */ | String /* "indeterminate" */ | String /* "paused" */ */
}

external interface Provider {
    var spellCheck: (text: String) -> Unit
}

external interface ReadBookmark {
    var title: String
    var url: String
}

external interface RedirectRequest {
    var url: String
    var method: String
    var session: Session? get() = definedExternally; set(value) = definedExternally
    var uploadData: UploadData? get() = definedExternally; set(value) = definedExternally
}

external interface RegisterBufferProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface RegisterFileProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface RegisterHttpProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface RegisterStandardSchemesOptions {
    var secure: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface RegisterStreamProtocolRequest {
    var url: String
    var headers: Headers
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface RegisterStringProtocolRequest {
    var url: String
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface RegisterURLSchemeAsPrivilegedOptions {
    var secure: Boolean? get() = definedExternally; set(value) = definedExternally
    var bypassCSP: Boolean? get() = definedExternally; set(value) = definedExternally
    var allowServiceWorkers: Boolean? get() = definedExternally; set(value) = definedExternally
    var supportFetchAPI: Boolean? get() = definedExternally; set(value) = definedExternally
    var corsEnabled: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface RelaunchOptions {
    var args: Array<String>? get() = definedExternally; set(value) = definedExternally
    var execPath: String? get() = definedExternally; set(value) = definedExternally
}

external interface Request {
    var method: String
    var url: String
    var referrer: String
}

external interface ResizeOptions {
    var width: Number? get() = definedExternally; set(value) = definedExternally
    var height: Number? get() = definedExternally; set(value) = definedExternally
    var quality: String? get() = definedExternally; set(value) = definedExternally
}

external interface ResourceUsage {
    var images: MemoryUsageDetails
    var cssStyleSheets: MemoryUsageDetails
    var xslStyleSheets: MemoryUsageDetails
    var fonts: MemoryUsageDetails
    var other: MemoryUsageDetails
}

external interface Response {
    var cancel: Boolean? get() = definedExternally; set(value) = definedExternally
    var redirectURL: String? get() = definedExternally; set(value) = definedExternally
}

external interface Result {
    var requestId: Number
    var activeMatchOrdinal: Number
    var matches: Number
    var selectionArea: SelectionArea
    var finalUpdate: Boolean
}

external interface SaveDialogOptions {
    var title: String? get() = definedExternally; set(value) = definedExternally
    var defaultPath: String? get() = definedExternally; set(value) = definedExternally
    var buttonLabel: String? get() = definedExternally; set(value) = definedExternally
    var filters: Array<FileFilter>? get() = definedExternally; set(value) = definedExternally
    var message: String? get() = definedExternally; set(value) = definedExternally
    var nameFieldLabel: String? get() = definedExternally; set(value) = definedExternally
    var showsTagField: Boolean? get() = definedExternally; set(value) = definedExternally
    var securityScopedBookmarks: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface Settings {
    var openAtLogin: Boolean? get() = definedExternally; set(value) = definedExternally
    var openAsHidden: Boolean? get() = definedExternally; set(value) = definedExternally
    var path: String? get() = definedExternally; set(value) = definedExternally
    var args: Array<String>? get() = definedExternally; set(value) = definedExternally
}

external interface SizeOptions {
    var enableAutoSize: Boolean? get() = definedExternally; set(value) = definedExternally
    var normal: Size? get() = definedExternally; set(value) = definedExternally
    var min: Size? get() = definedExternally; set(value) = definedExternally
    var max: Size? get() = definedExternally; set(value) = definedExternally
}

external interface SourcesOptions {
    var types: Array<String>
    var thumbnailSize: Size? get() = definedExternally; set(value) = definedExternally
}

external interface StartMonitoringOptions {
    var categoryFilter: String
    var traceOptions: String
}

external interface StartRecordingOptions {
    var categoryFilter: String
    var traceOptions: String
}

external interface SystemMemoryInfo {
    var total: Number
    var free: Number
    var swapTotal: Number
    var swapFree: Number
}

external interface ToBitmapOptions {
    var scaleFactor: Number? get() = definedExternally; set(value) = definedExternally
}

external interface ToDataURLOptions {
    var scaleFactor: Number? get() = definedExternally; set(value) = definedExternally
}

external interface ToPNGOptions {
    var scaleFactor: Number? get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarButtonConstructorOptions {
    var label: String? get() = definedExternally; set(value) = definedExternally
    var backgroundColor: String? get() = definedExternally; set(value) = definedExternally
    var icon: NativeImage? get() = definedExternally; set(value) = definedExternally
    var iconPosition: dynamic /* String /* "right" */ | String /* "left" */ | String /* "overlay" */ */ get() = definedExternally; set(value) = definedExternally
    var click: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarColorPickerConstructorOptions {
    var availableColors: Array<String>? get() = definedExternally; set(value) = definedExternally
    var selectedColor: String? get() = definedExternally; set(value) = definedExternally
    var change: ((color: String) -> Unit)? get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarConstructorOptions {
    var items: Array<dynamic /* TouchBarButton | TouchBarColorPicker | TouchBarGroup | TouchBarLabel | TouchBarPopover | TouchBarScrubber | TouchBarSegmentedControl | TouchBarSlider | TouchBarSpacer | Nothing? */>
    var escapeItem: dynamic /* TouchBarButton | TouchBarColorPicker | TouchBarGroup | TouchBarLabel | TouchBarPopover | TouchBarScrubber | TouchBarSegmentedControl | TouchBarSlider | TouchBarSpacer | Nothing? */ get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarGroupConstructorOptions {
    var items: TouchBar
}

external interface TouchBarLabelConstructorOptions {
    var label: String? get() = definedExternally; set(value) = definedExternally
    var textColor: String? get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarPopoverConstructorOptions {
    var label: String? get() = definedExternally; set(value) = definedExternally
    var icon: NativeImage? get() = definedExternally; set(value) = definedExternally
    var items: TouchBar? get() = definedExternally; set(value) = definedExternally
    var showCloseButton: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarScrubberConstructorOptions {
    var items: Array<ScrubberItem>
    var select: (selectedIndex: Number) -> Unit
    var highlight: (highlightedIndex: Number) -> Unit
    var selectedStyle: String
    var overlayStyle: String
    var showArrowButtons: Boolean
    var mode: String
    var continuous: Boolean
}

external interface TouchBarSegmentedControlConstructorOptions {
    var segmentStyle: dynamic /* String /* "automatic" */ | String /* "rounded" */ | String /* "textured-rounded" */ | String /* "round-rect" */ | String /* "textured-square" */ | String /* "capsule" */ | String /* "small-square" */ | String /* "separated" */ */ get() = definedExternally; set(value) = definedExternally
    var mode: dynamic /* String /* "single" */ | String /* "multiple" */ | String /* "buttons" */ */ get() = definedExternally; set(value) = definedExternally
    var segments: Array<SegmentedControlSegment>
    var selectedIndex: Number? get() = definedExternally; set(value) = definedExternally
    var change: (selectedIndex: Number, isSelected: Boolean) -> Unit
}

external interface TouchBarSliderConstructorOptions {
    var label: String? get() = definedExternally; set(value) = definedExternally
    var value: Number? get() = definedExternally; set(value) = definedExternally
    var minValue: Number? get() = definedExternally; set(value) = definedExternally
    var maxValue: Number? get() = definedExternally; set(value) = definedExternally
    var change: ((newValue: Number) -> Unit)? get() = definedExternally; set(value) = definedExternally
}

external interface TouchBarSpacerConstructorOptions {
    var size: dynamic /* String /* "small" */ | String /* "large" */ | String /* "flexible" */ */ get() = definedExternally; set(value) = definedExternally
}

external interface UpdateTargetUrlEvent : Event {
    var url: String
}

external interface Versions {
    var chrome: String? get() = definedExternally; set(value) = definedExternally
    var electron: String? get() = definedExternally; set(value) = definedExternally
}

external interface WillNavigateEvent : Event {
    var url: String
}

external interface EditFlags {
    var canUndo: Boolean
    var canRedo: Boolean
    var canCut: Boolean
    var canCopy: Boolean
    var canPaste: Boolean
    var canDelete: Boolean
    var canSelectAll: Boolean
}

external interface Extra
external interface FoundInPageResult {
    var requestId: Number
    var activeMatchOrdinal: Number
    var matches: Number
    var selectionArea: SelectionArea
    var finalUpdate: Boolean
}

external interface MediaFlags {
    var inError: Boolean
    var isPaused: Boolean
    var isMuted: Boolean
    var hasAudio: Boolean
    var isLooping: Boolean
    var isControlsVisible: Boolean
    var canToggleControls: Boolean
    var canRotate: Boolean
}

external interface Options
external interface RequestHeaders
external interface ResponseHeaders
external interface SelectionArea
external interface WebPreferences {
    var devTools: Boolean? get() = definedExternally; set(value) = definedExternally
    var nodeIntegration: Boolean? get() = definedExternally; set(value) = definedExternally
    var nodeIntegrationInWorker: Boolean? get() = definedExternally; set(value) = definedExternally
    var preload: String? get() = definedExternally; set(value) = definedExternally
    var sandbox: Boolean? get() = definedExternally; set(value) = definedExternally
    var session: Session? get() = definedExternally; set(value) = definedExternally
    var partition: String? get() = definedExternally; set(value) = definedExternally
    var affinity: String? get() = definedExternally; set(value) = definedExternally
    var zoomFactor: Number? get() = definedExternally; set(value) = definedExternally
    var javascript: Boolean? get() = definedExternally; set(value) = definedExternally
    var webSecurity: Boolean? get() = definedExternally; set(value) = definedExternally
    var allowRunningInsecureContent: Boolean? get() = definedExternally; set(value) = definedExternally
    var images: Boolean? get() = definedExternally; set(value) = definedExternally
    var textAreasAreResizable: Boolean? get() = definedExternally; set(value) = definedExternally
    var webgl: Boolean? get() = definedExternally; set(value) = definedExternally
    var webaudio: Boolean? get() = definedExternally; set(value) = definedExternally
    var plugins: Boolean? get() = definedExternally; set(value) = definedExternally
    var experimentalFeatures: Boolean? get() = definedExternally; set(value) = definedExternally
    var experimentalCanvasFeatures: Boolean? get() = definedExternally; set(value) = definedExternally
    var scrollBounce: Boolean? get() = definedExternally; set(value) = definedExternally
    var blinkFeatures: String? get() = definedExternally; set(value) = definedExternally
    var disableBlinkFeatures: String? get() = definedExternally; set(value) = definedExternally
    var defaultFontFamily: DefaultFontFamily? get() = definedExternally; set(value) = definedExternally
    var defaultFontSize: Number? get() = definedExternally; set(value) = definedExternally
    var defaultMonospaceFontSize: Number? get() = definedExternally; set(value) = definedExternally
    var minimumFontSize: Number? get() = definedExternally; set(value) = definedExternally
    var defaultEncoding: String? get() = definedExternally; set(value) = definedExternally
    var backgroundThrottling: Boolean? get() = definedExternally; set(value) = definedExternally
    var offscreen: Boolean? get() = definedExternally; set(value) = definedExternally
    var contextIsolation: Boolean? get() = definedExternally; set(value) = definedExternally
    var nativeWindowOpen: Boolean? get() = definedExternally; set(value) = definedExternally
    var webviewTag: Boolean? get() = definedExternally; set(value) = definedExternally
    var additionArguments: Array<String>? get() = definedExternally; set(value) = definedExternally
}

external interface DefaultFontFamily {
    var standard: String? get() = definedExternally; set(value) = definedExternally
    var serif: String? get() = definedExternally; set(value) = definedExternally
    var sansSerif: String? get() = definedExternally; set(value) = definedExternally
    var monospace: String? get() = definedExternally; set(value) = definedExternally
    var cursive: String? get() = definedExternally; set(value) = definedExternally
    var fantasy: String? get() = definedExternally; set(value) = definedExternally
}
