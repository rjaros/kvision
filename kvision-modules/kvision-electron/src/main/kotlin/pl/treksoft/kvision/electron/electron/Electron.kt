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

@file:JsModule("electron")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.electron

import Buffer
import org.khronos.webgl.Uint8Array
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.EventListener
import pl.treksoft.kvision.electron.GlobalEvent
import pl.treksoft.kvision.electron.nodejs.EventEmitter
import pl.treksoft.kvision.electron.nodejs.Process
import pl.treksoft.kvision.electron.nodejs.ReadableStream
import kotlin.js.Date
import kotlin.js.Promise

external interface Record<T, R>

external interface CommonInterface {
    var app: App
    var autoUpdater: AutoUpdater
    var BrowserView: Any
    var BrowserWindowProxy: Any
    var BrowserWindow: Any
    var ClientRequest: Any
    var clipboard: Clipboard
    var CommandLine: Any
    var contentTracing: ContentTracing
    var contextBridge: ContextBridge
    var Cookies: Any
    var crashReporter: CrashReporter
    var Debugger: Any
    var desktopCapturer: DesktopCapturer
    var dialog: Dialog
    var Dock: Any
    var DownloadItem: Any
    var globalShortcut: GlobalShortcut
    var inAppPurchase: InAppPurchase
    var IncomingMessage: Any
    var ipcMain: IpcMain
    var ipcRenderer: IpcRenderer
    var MenuItem: Any
    var Menu: Any
    var nativeImage: Any
    var nativeTheme: NativeTheme
    var netLog: NetLog
    var net: Net
    var Notification: Any
    var powerMonitor: PowerMonitor
    var powerSaveBlocker: PowerSaveBlocker
    var protocol: Protocol
    var remote: Remote
    var screen: Screen
    var session: Any
    var shell: Shell
    var systemPreferences: SystemPreferences
    var TouchBarButton: Any
    var TouchBarColorPicker: Any
    var TouchBarGroup: Any
    var TouchBarLabel: Any
    var TouchBarPopover: Any
    var TouchBarScrubber: Any
    var TouchBarSegmentedControl: Any
    var TouchBarSlider: Any
    var TouchBarSpacer: Any
    var TouchBar: Any
    var Tray: Any
    var webContents: Any
    var webFrame: WebFrame
    var WebRequest: Any
    var webviewTag: WebviewTag
}

external interface MainInterface : CommonInterface

external interface RendererInterface : CommonInterface

external interface AllElectron : MainInterface, RendererInterface

external var app: App

external var autoUpdater: AutoUpdater

external var clipboard: Clipboard

external var contentTracing: ContentTracing

external var contextBridge: ContextBridge

external var crashReporter: CrashReporter

external var desktopCapturer: DesktopCapturer

external var dialog: Dialog

external var globalShortcut: GlobalShortcut

external var inAppPurchase: InAppPurchase

external var ipcMain: IpcMain

external var ipcRenderer: IpcRenderer

external var nativeImage: Any

external var nativeTheme: NativeTheme

external var netLog: NetLog

external var net: Net

external var powerMonitor: PowerMonitor

external var powerSaveBlocker: PowerSaveBlocker

external var protocol: Protocol

external var remote: Remote

external var screen: Screen

external var session: Any

external var shell: Shell

external var systemPreferences: SystemPreferences

external var webContents: Any

external var webFrame: WebFrame

external var webviewTag: WebviewTag

external interface App : EventEmitter {
    fun on(
        event: String /* 'accessibility-support-changed' */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'accessibility-support-changed' */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'accessibility-support-changed' */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'accessibility-support-changed' */,
        listener: (event: Event, accessibilitySupportEnabled: Boolean) -> Unit
    ): App /* this */

    fun on(event: String /* 'activate' */, listener: (event: Event, hasVisibleWindows: Boolean) -> Unit): App /* this */
    fun once(
        event: String /* 'activate' */,
        listener: (event: Event, hasVisibleWindows: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'activate' */,
        listener: (event: Event, hasVisibleWindows: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'activate' */,
        listener: (event: Event, hasVisibleWindows: Boolean) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'activity-was-continued' | 'continue-activity' | 'update-activity-state' */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'activity-was-continued' | 'continue-activity' | 'update-activity-state' */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'activity-was-continued' | 'continue-activity' | 'update-activity-state' */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'activity-was-continued' | 'continue-activity' | 'update-activity-state' */,
        listener: (event: Event, type: String, userInfo: Any) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'before-quit' | 'new-window-for-tab' | 'will-quit' */,
        listener: (event: Event) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'before-quit' | 'new-window-for-tab' | 'will-quit' */,
        listener: (event: Event) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'before-quit' | 'new-window-for-tab' | 'will-quit' */,
        listener: (event: Event) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'before-quit' | 'new-window-for-tab' | 'will-quit' */,
        listener: (event: Event) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'browser-window-blur' | 'browser-window-created' | 'browser-window-focus' */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'browser-window-blur' | 'browser-window-created' | 'browser-window-focus' */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'browser-window-blur' | 'browser-window-created' | 'browser-window-focus' */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'browser-window-blur' | 'browser-window-created' | 'browser-window-focus' */,
        listener: (event: Event, window: BrowserWindow) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'certificate-error' */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'certificate-error' */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'certificate-error' */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'certificate-error' */,
        listener: (event: Event, webContents: WebContents, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'continue-activity-error' */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'continue-activity-error' */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'continue-activity-error' */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'continue-activity-error' */,
        listener: (event: Event, type: String, error: String) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'desktop-capturer-get-sources' | 'remote-get-current-web-contents' | 'remote-get-current-window' | 'web-contents-created' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'desktop-capturer-get-sources' | 'remote-get-current-web-contents' | 'remote-get-current-window' | 'web-contents-created' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'desktop-capturer-get-sources' | 'remote-get-current-web-contents' | 'remote-get-current-window' | 'web-contents-created' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'desktop-capturer-get-sources' | 'remote-get-current-web-contents' | 'remote-get-current-window' | 'web-contents-created' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'gpu-info-update' | 'will-finish-launching' | 'window-all-closed' */,
        listener: Function<*>
    ): App /* this */

    fun once(
        event: String /* 'gpu-info-update' | 'will-finish-launching' | 'window-all-closed' */,
        listener: Function<*>
    ): App /* this */

    fun addListener(
        event: String /* 'gpu-info-update' | 'will-finish-launching' | 'window-all-closed' */,
        listener: Function<*>
    ): App /* this */

    fun removeListener(
        event: String /* 'gpu-info-update' | 'will-finish-launching' | 'window-all-closed' */,
        listener: Function<*>
    ): App /* this */

    fun on(event: String /* 'gpu-process-crashed' */, listener: (event: Event, killed: Boolean) -> Unit): App /* this */
    fun once(
        event: String /* 'gpu-process-crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'gpu-process-crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'gpu-process-crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'login' */,
        listener: (event: Event, webContents: WebContents, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'login' */,
        listener: (event: Event, webContents: WebContents, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'login' */,
        listener: (event: Event, webContents: WebContents, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'login' */,
        listener: (event: Event, webContents: WebContents, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): App /* this */

    fun on(event: String /* 'open-file' */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun once(event: String /* 'open-file' */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun addListener(event: String /* 'open-file' */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun removeListener(event: String /* 'open-file' */, listener: (event: Event, path: String) -> Unit): App /* this */
    fun on(event: String /* 'open-url' */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun once(event: String /* 'open-url' */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun addListener(event: String /* 'open-url' */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun removeListener(event: String /* 'open-url' */, listener: (event: Event, url: String) -> Unit): App /* this */
    fun on(event: String /* 'quit' */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun once(event: String /* 'quit' */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun addListener(event: String /* 'quit' */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    fun removeListener(event: String /* 'quit' */, listener: (event: Event, exitCode: Number) -> Unit): App /* this */
    override fun on(event: String /* 'ready' */, listener: (launchInfo: Any) -> Unit): App /* this */
    override fun once(event: String /* 'ready' */, listener: (launchInfo: Any) -> Unit): App /* this */
    override fun addListener(event: String /* 'ready' */, listener: (launchInfo: Any) -> Unit): App /* this */
    override fun removeListener(event: String /* 'ready' */, listener: (launchInfo: Any) -> Unit): App /* this */
    fun on(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: Event, webContents: WebContents, moduleName: String) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: Event, webContents: WebContents, moduleName: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: Event, webContents: WebContents, moduleName: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: Event, webContents: WebContents, moduleName: String) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'remote-get-global' */,
        listener: (event: Event, webContents: WebContents, globalName: String) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'remote-get-global' */,
        listener: (event: Event, webContents: WebContents, globalName: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'remote-get-global' */,
        listener: (event: Event, webContents: WebContents, globalName: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'remote-get-global' */,
        listener: (event: Event, webContents: WebContents, globalName: String) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: Event, webContents: WebContents, guestWebContents: WebContents) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: Event, webContents: WebContents, guestWebContents: WebContents) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: Event, webContents: WebContents, guestWebContents: WebContents) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: Event, webContents: WebContents, guestWebContents: WebContents) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, webContents: WebContents, details: Details) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, webContents: WebContents, details: Details) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, webContents: WebContents, details: Details) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, webContents: WebContents, details: Details) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'renderer-process-crashed' */,
        listener: (event: Event, webContents: WebContents, killed: Boolean) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'renderer-process-crashed' */,
        listener: (event: Event, webContents: WebContents, killed: Boolean) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'renderer-process-crashed' */,
        listener: (event: Event, webContents: WebContents, killed: Boolean) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'renderer-process-crashed' */,
        listener: (event: Event, webContents: WebContents, killed: Boolean) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'second-instance' */,
        listener: (event: Event, argv: Array<String>, workingDirectory: String) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'second-instance' */,
        listener: (event: Event, argv: Array<String>, workingDirectory: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'second-instance' */,
        listener: (event: Event, argv: Array<String>, workingDirectory: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'second-instance' */,
        listener: (event: Event, argv: Array<String>, workingDirectory: String) -> Unit
    ): App /* this */

    fun on(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): App /* this */

    fun once(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, webContents: WebContents, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): App /* this */

    fun on(event: String /* 'session-created' */, listener: (session: Session) -> Unit): App /* this */
    fun once(event: String /* 'session-created' */, listener: (session: Session) -> Unit): App /* this */
    fun addListener(event: String /* 'session-created' */, listener: (session: Session) -> Unit): App /* this */
    fun removeListener(event: String /* 'session-created' */, listener: (session: Session) -> Unit): App /* this */
    fun on(event: String /* 'will-continue-activity' */, listener: (event: Event, type: String) -> Unit): App /* this */
    fun once(
        event: String /* 'will-continue-activity' */,
        listener: (event: Event, type: String) -> Unit
    ): App /* this */

    fun addListener(
        event: String /* 'will-continue-activity' */,
        listener: (event: Event, type: String) -> Unit
    ): App /* this */

    fun removeListener(
        event: String /* 'will-continue-activity' */,
        listener: (event: Event, type: String) -> Unit
    ): App /* this */

    fun addRecentDocument(path: String)
    fun clearRecentDocuments()
    fun disableDomainBlockingFor3DAPIs()
    fun disableHardwareAcceleration()
    fun enableSandbox()
    fun exit(exitCode: Number = definedExternally)
    fun focus(options: FocusOptions = definedExternally)
    fun getApplicationNameForProtocol(url: String): String
    fun getAppMetrics(): Array<ProcessMetric>
    fun getAppPath(): String
    fun getBadgeCount(): Number
    fun getCurrentActivityType(): String
    fun getFileIcon(path: String, options: FileIconOptions = definedExternally): Promise<NativeImage>
    fun getGPUFeatureStatus(): GPUFeatureStatus
    fun getGPUInfo(infoType: String /* 'basic' | 'complete' */): Promise<Any>
    fun getJumpListSettings(): JumpListSettings
    fun getLocale(): String
    fun getLocaleCountryCode(): String
    fun getLoginItemSettings(options: LoginItemSettingsOptions = definedExternally): LoginItemSettings
    fun getName(): String
    fun getPath(name: String /* 'home' | 'appData' | 'userData' | 'cache' | 'temp' | 'exe' | 'module' | 'desktop' | 'documents' | 'downloads' | 'music' | 'pictures' | 'videos' | 'logs' | 'pepperFlashSystemPlugin' */): String
    fun getVersion(): String
    fun hasSingleInstanceLock(): Boolean
    fun hide()
    fun importCertificate(options: ImportCertificateOptions, callback: (result: Number) -> Unit)
    fun invalidateCurrentActivity()
    fun isAccessibilitySupportEnabled(): Boolean
    fun isDefaultProtocolClient(
        protocol: String,
        path: String = definedExternally,
        args: Array<String> = definedExternally
    ): Boolean

    fun isEmojiPanelSupported(): Boolean
    fun isInApplicationsFolder(): Boolean
    fun isReady(): Boolean
    fun isUnityRunning(): Boolean
    fun moveToApplicationsFolder(options: MoveToApplicationsFolderOptions = definedExternally): Boolean
    fun quit()
    fun relaunch(options: RelaunchOptions = definedExternally)
    fun releaseSingleInstanceLock()
    fun removeAsDefaultProtocolClient(
        protocol: String,
        path: String = definedExternally,
        args: Array<String> = definedExternally
    ): Boolean

    fun requestSingleInstanceLock(): Boolean
    fun resignCurrentActivity()
    fun setAboutPanelOptions(options: AboutPanelOptionsOptions)
    fun setAccessibilitySupportEnabled(enabled: Boolean)
    fun setAppLogsPath(path: String = definedExternally)
    fun setAppUserModelId(id: String)
    fun setAsDefaultProtocolClient(
        protocol: String,
        path: String = definedExternally,
        args: Array<String> = definedExternally
    ): Boolean

    fun setBadgeCount(count: Number): Boolean
    fun setJumpList(categories: Array<JumpListCategory>?)
    fun setLoginItemSettings(settings: Settings)
    fun setName(name: String)
    fun setPath(name: String, path: String)
    fun setUserActivity(type: String, userInfo: Any, webpageURL: String = definedExternally)
    fun setUserTasks(tasks: Array<Task>): Boolean
    fun show()
    fun showAboutPanel()
    fun showEmojiPanel()
    fun startAccessingSecurityScopedResource(bookmarkData: String): Function<*>
    fun updateCurrentActivity(type: String, userInfo: Any)
    fun whenReady(): Promise<Unit>
    var accessibilitySupportEnabled: Boolean
    var allowRendererProcessReuse: Boolean
    var applicationMenu: Menu?
    var badgeCount: Number
    var commandLine: CommandLine
    var dock: Dock
    var isPackaged: Boolean
    var name: String
    var userAgentFallback: String
}

external interface AutoUpdater : EventEmitter {
    fun on(
        event: String /* 'before-quit-for-update' | 'checking-for-update' | 'update-available' | 'update-not-available' */,
        listener: Function<*>
    ): AutoUpdater /* this */

    fun once(
        event: String /* 'before-quit-for-update' | 'checking-for-update' | 'update-available' | 'update-not-available' */,
        listener: Function<*>
    ): AutoUpdater /* this */

    fun addListener(
        event: String /* 'before-quit-for-update' | 'checking-for-update' | 'update-available' | 'update-not-available' */,
        listener: Function<*>
    ): AutoUpdater /* this */

    fun removeListener(
        event: String /* 'before-quit-for-update' | 'checking-for-update' | 'update-available' | 'update-not-available' */,
        listener: Function<*>
    ): AutoUpdater /* this */

    fun on(event: String /* 'error' */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun once(event: String /* 'error' */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun addListener(event: String /* 'error' */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun removeListener(event: String /* 'error' */, listener: (error: Error) -> Unit): AutoUpdater /* this */
    fun on(
        event: String /* 'update-downloaded' */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    fun once(
        event: String /* 'update-downloaded' */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    fun addListener(
        event: String /* 'update-downloaded' */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
    ): AutoUpdater /* this */

    fun removeListener(
        event: String /* 'update-downloaded' */,
        listener: (event: Event, releaseNotes: String, releaseName: String, releaseDate: Date, updateURL: String) -> Unit
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

external open class BrowserView(options: BrowserViewConstructorOptions = definedExternally) {
    open fun destroy()
    open fun getBounds(): Rectangle
    open fun isDestroyed(): Boolean
    open fun setAutoResize(options: AutoResizeOptions)
    open fun setBackgroundColor(color: String)
    open fun setBounds(bounds: Rectangle)
    open var id: Number
    open var webContents: WebContents

    companion object {
        fun fromId(id: Number): BrowserView
        fun fromWebContents(webContents: WebContents): BrowserView?
        fun getAllViews(): Array<BrowserView>
    }
}

external open class BrowserWindow(options: BrowserWindowConstructorOptions = definedExternally) : EventEmitter {
    open fun on(
        event: String /* 'always-on-top-changed' */,
        listener: (event: Event, isAlwaysOnTop: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'always-on-top-changed' */,
        listener: (event: Event, isAlwaysOnTop: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'always-on-top-changed' */,
        listener: (event: Event, isAlwaysOnTop: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'always-on-top-changed' */,
        listener: (event: Event, isAlwaysOnTop: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun on(
        event: String /* 'app-command' */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'app-command' */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'app-command' */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'app-command' */,
        listener: (event: Event, command: String) -> Unit
    ): BrowserWindow /* this */

    open fun on(
        event: String /* 'blur' | 'closed' | 'enter-full-screen' | 'enter-html-full-screen' | 'focus' | 'hide' | 'leave-full-screen' | 'leave-html-full-screen' | 'maximize' | 'minimize' | 'move' | 'moved' | 'new-window-for-tab' | 'ready-to-show' | 'resize' | 'responsive' | 'restore' | 'scroll-touch-begin' | 'scroll-touch-edge' | 'scroll-touch-end' | 'session-end' | 'sheet-begin' | 'sheet-end' | 'show' | 'unmaximize' | 'unresponsive' */,
        listener: Function<*>
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'blur' | 'closed' | 'enter-full-screen' | 'enter-html-full-screen' | 'focus' | 'hide' | 'leave-full-screen' | 'leave-html-full-screen' | 'maximize' | 'minimize' | 'move' | 'moved' | 'new-window-for-tab' | 'ready-to-show' | 'resize' | 'responsive' | 'restore' | 'scroll-touch-begin' | 'scroll-touch-edge' | 'scroll-touch-end' | 'session-end' | 'sheet-begin' | 'sheet-end' | 'show' | 'unmaximize' | 'unresponsive' */,
        listener: Function<*>
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'blur' | 'closed' | 'enter-full-screen' | 'enter-html-full-screen' | 'focus' | 'hide' | 'leave-full-screen' | 'leave-html-full-screen' | 'maximize' | 'minimize' | 'move' | 'moved' | 'new-window-for-tab' | 'ready-to-show' | 'resize' | 'responsive' | 'restore' | 'scroll-touch-begin' | 'scroll-touch-edge' | 'scroll-touch-end' | 'session-end' | 'sheet-begin' | 'sheet-end' | 'show' | 'unmaximize' | 'unresponsive' */,
        listener: Function<*>
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'blur' | 'closed' | 'enter-full-screen' | 'enter-html-full-screen' | 'focus' | 'hide' | 'leave-full-screen' | 'leave-html-full-screen' | 'maximize' | 'minimize' | 'move' | 'moved' | 'new-window-for-tab' | 'ready-to-show' | 'resize' | 'responsive' | 'restore' | 'scroll-touch-begin' | 'scroll-touch-edge' | 'scroll-touch-end' | 'session-end' | 'sheet-begin' | 'sheet-end' | 'show' | 'unmaximize' | 'unresponsive' */,
        listener: Function<*>
    ): BrowserWindow /* this */

    open fun on(event: String /* 'close' */, listener: (event: Event) -> Unit): BrowserWindow /* this */
    open fun once(event: String /* 'close' */, listener: (event: Event) -> Unit): BrowserWindow /* this */
    open fun addListener(event: String /* 'close' */, listener: (event: Event) -> Unit): BrowserWindow /* this */
    open fun removeListener(event: String /* 'close' */, listener: (event: Event) -> Unit): BrowserWindow /* this */
    open fun on(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): BrowserWindow /* this */

    open fun on(
        event: String /* 'rotate-gesture' */,
        listener: (event: Event, rotation: Number) -> Unit
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'rotate-gesture' */,
        listener: (event: Event, rotation: Number) -> Unit
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'rotate-gesture' */,
        listener: (event: Event, rotation: Number) -> Unit
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'rotate-gesture' */,
        listener: (event: Event, rotation: Number) -> Unit
    ): BrowserWindow /* this */

    open fun on(
        event: String /* 'swipe' */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'swipe' */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'swipe' */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'swipe' */,
        listener: (event: Event, direction: String) -> Unit
    ): BrowserWindow /* this */

    open fun on(
        event: String /* 'will-move' | 'will-resize' */,
        listener: (event: Event, newBounds: Rectangle) -> Unit
    ): BrowserWindow /* this */

    open fun once(
        event: String /* 'will-move' | 'will-resize' */,
        listener: (event: Event, newBounds: Rectangle) -> Unit
    ): BrowserWindow /* this */

    open fun addListener(
        event: String /* 'will-move' | 'will-resize' */,
        listener: (event: Event, newBounds: Rectangle) -> Unit
    ): BrowserWindow /* this */

    open fun removeListener(
        event: String /* 'will-move' | 'will-resize' */,
        listener: (event: Event, newBounds: Rectangle) -> Unit
    ): BrowserWindow /* this */

    open fun addBrowserView(browserView: BrowserView)
    open fun addTabbedWindow(browserWindow: BrowserWindow)
    open fun blur()
    open fun blurWebView()
    open fun capturePage(rect: Rectangle = definedExternally): Promise<NativeImage>
    open fun center()
    open fun close()
    open fun closeFilePreview()
    open fun destroy()
    open fun flashFrame(flag: Boolean)
    open fun focus()
    open fun focusOnWebView()
    open fun getBounds(): Rectangle
    open fun getBrowserView(): BrowserView?
    open fun getBrowserViews(): Array<BrowserView>
    open fun getChildWindows(): Array<BrowserWindow>
    open fun getContentBounds(): Rectangle
    open fun getContentSize(): Array<Number>
    open fun getMaximumSize(): Array<Number>
    open fun getMediaSourceId(): String
    open fun getMinimumSize(): Array<Number>
    open fun getNativeWindowHandle(): Buffer
    open fun getNormalBounds(): Rectangle
    open fun getOpacity(): Number
    open fun getParentWindow(): BrowserWindow
    open fun getPosition(): Array<Number>
    open fun getRepresentedFilename(): String
    open fun getSize(): Array<Number>
    open fun getTitle(): String
    open fun getTrafficLightPosition(): Point
    open fun hasShadow(): Boolean
    open fun hide()
    open fun hookWindowMessage(message: Number, callback: () -> Unit)
    open fun isAlwaysOnTop(): Boolean
    open fun isClosable(): Boolean
    open fun isDestroyed(): Boolean
    open fun isDocumentEdited(): Boolean
    open fun isEnabled()
    open fun isFocused(): Boolean
    open fun isFullScreen(): Boolean
    open fun isFullScreenable(): Boolean
    open fun isKiosk(): Boolean
    open fun isMaximizable(): Boolean
    open fun isMaximized(): Boolean
    open fun isMenuBarAutoHide(): Boolean
    open fun isMenuBarVisible(): Boolean
    open fun isMinimizable(): Boolean
    open fun isMinimized(): Boolean
    open fun isModal(): Boolean
    open fun isMovable(): Boolean
    open fun isNormal(): Boolean
    open fun isResizable(): Boolean
    open fun isSimpleFullScreen(): Boolean
    open fun isVisible(): Boolean
    open fun isVisibleOnAllWorkspaces(): Boolean
    open fun isWindowMessageHooked(message: Number): Boolean
    open fun loadFile(filePath: String, options: LoadFileOptions = definedExternally): Promise<Unit>
    open fun loadURL(url: String, options: LoadURLOptions = definedExternally): Promise<Unit>
    open fun maximize()
    open fun mergeAllWindows()
    open fun minimize()
    open fun moveAbove(mediaSourceId: String)
    open fun moveTabToNewWindow()
    open fun moveTop()
    open fun previewFile(path: String, displayName: String = definedExternally)
    open fun reload()
    open fun removeBrowserView(browserView: BrowserView)
    open fun removeMenu()
    open fun restore()
    open fun selectNextTab()
    open fun selectPreviousTab()
    open fun setAlwaysOnTop(
        flag: Boolean,
        level: String /* 'normal' | 'floating' | 'torn-off-menu' | 'modal-panel' | 'main-menu' | 'status' | 'pop-up-menu' | 'screen-saver' */ = definedExternally,
        relativeLevel: Number = definedExternally
    )

    open fun setAppDetails(options: AppDetailsOptions)
    open fun setAspectRatio(aspectRatio: Number, extraSize: Size = definedExternally)
    open fun setAutoHideCursor(autoHide: Boolean)
    open fun setAutoHideMenuBar(hide: Boolean)
    open fun setBackgroundColor(backgroundColor: String)
    open fun setBounds(bounds: RectanglePartial, animate: Boolean = definedExternally)
    open fun setBrowserView(browserView: BrowserView?)
    open fun setClosable(closable: Boolean)
    open fun setContentBounds(bounds: Rectangle, animate: Boolean = definedExternally)
    open fun setContentProtection(enable: Boolean)
    open fun setContentSize(width: Number, height: Number, animate: Boolean = definedExternally)
    open fun setDocumentEdited(edited: Boolean)
    open fun setEnabled(enable: Boolean)
    open fun setFocusable(focusable: Boolean)
    open fun setFullScreen(flag: Boolean)
    open fun setFullScreenable(fullscreenable: Boolean)
    open fun setHasShadow(hasShadow: Boolean)
    open fun setIcon(icon: NativeImage)
    open fun setIcon(icon: String)
    open fun setIgnoreMouseEvents(ignore: Boolean, options: IgnoreMouseEventsOptions = definedExternally)
    open fun setKiosk(flag: Boolean)
    open fun setMaximizable(maximizable: Boolean)
    open fun setMaximumSize(width: Number, height: Number)
    open fun setMenu(menu: Menu?)
    open fun setMenuBarVisibility(visible: Boolean)
    open fun setMinimizable(minimizable: Boolean)
    open fun setMinimumSize(width: Number, height: Number)
    open fun setMovable(movable: Boolean)
    open fun setOpacity(opacity: Number)
    open fun setOverlayIcon(overlay: NativeImage?, description: String)
    open fun setParentWindow(parent: BrowserWindow?)
    open fun setPosition(x: Number, y: Number, animate: Boolean = definedExternally)
    open fun setProgressBar(progress: Number, options: ProgressBarOptions = definedExternally)
    open fun setRepresentedFilename(filename: String)
    open fun setResizable(resizable: Boolean)
    open fun setShape(rects: Array<Rectangle>)
    open fun setSheetOffset(offsetY: Number, offsetX: Number = definedExternally)
    open fun setSimpleFullScreen(flag: Boolean)
    open fun setSize(width: Number, height: Number, animate: Boolean = definedExternally)
    open fun setSkipTaskbar(skip: Boolean)
    open fun setThumbarButtons(buttons: Array<ThumbarButton>): Boolean
    open fun setThumbnailClip(region: Rectangle)
    open fun setThumbnailToolTip(toolTip: String)
    open fun setTitle(title: String)
    open fun setTouchBar(touchBar: TouchBar?)
    open fun setTrafficLightPosition(position: Point)
    open fun setVibrancy(type: String /* 'appearance-based' | 'light' | 'dark' | 'titlebar' | 'selection' | 'menu' | 'popover' | 'sidebar' | 'medium-light' | 'ultra-dark' | 'header' | 'sheet' | 'window' | 'hud' | 'fullscreen-ui' | 'tooltip' | 'content' | 'under-window' | 'under-page' */)
    open fun setVisibleOnAllWorkspaces(visible: Boolean, options: VisibleOnAllWorkspacesOptions = definedExternally)
    open fun setWindowButtonVisibility(visible: Boolean)
    open fun show()
    open fun showDefinitionForSelection()
    open fun showInactive()
    open fun toggleTabBar()
    open fun unhookAllWindowMessages()
    open fun unhookWindowMessage(message: Number)
    open fun unmaximize()
    open var accessibleTitle: String
    open var autoHideMenuBar: Boolean
    open var closable: Boolean
    open var excludedFromShownWindowsMenu: Boolean
    open var fullScreenable: Boolean
    open var id: Number
    open var maximizable: Boolean
    open var minimizable: Boolean
    open var movable: Boolean
    open var resizable: Boolean
    open var webContents: WebContents

    companion object {
        fun addDevToolsExtension(path: String)
        fun addExtension(path: String)
        fun fromBrowserView(browserView: BrowserView): BrowserWindow?
        fun fromId(id: Number): BrowserWindow
        fun fromWebContents(webContents: WebContents): BrowserWindow?
        fun getAllWindows(): Array<BrowserWindow>
        fun getDevToolsExtensions(): Record<String, ExtensionInfo>
        fun getExtensions(): Record<String, ExtensionInfo>
        fun getFocusedWindow(): BrowserWindow?
        fun removeDevToolsExtension(name: String)
        fun removeExtension(name: String)
    }
}

external open class BrowserWindowProxy {
    open fun blur()
    open fun close()
    open fun eval(code: String)
    open fun focus()
    open fun postMessage(message: Any, targetOrigin: String)
    open fun print()
    open var closed: Boolean
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

external open class ClientRequest : EventEmitter {
    open fun on(event: String /* 'abort' | 'close' | 'finish' */, listener: Function<*>): ClientRequest /* this */
    open fun once(event: String /* 'abort' | 'close' | 'finish' */, listener: Function<*>): ClientRequest /* this */
    open fun addListener(
        event: String /* 'abort' | 'close' | 'finish' */,
        listener: Function<*>
    ): ClientRequest /* this */

    open fun removeListener(
        event: String /* 'abort' | 'close' | 'finish' */,
        listener: Function<*>
    ): ClientRequest /* this */

    open fun on(event: String /* 'error' */, listener: (error: Error) -> Unit): ClientRequest /* this */
    open fun once(event: String /* 'error' */, listener: (error: Error) -> Unit): ClientRequest /* this */
    open fun addListener(event: String /* 'error' */, listener: (error: Error) -> Unit): ClientRequest /* this */
    open fun removeListener(event: String /* 'error' */, listener: (error: Error) -> Unit): ClientRequest /* this */
    open fun on(
        event: String /* 'login' */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */

    open fun once(
        event: String /* 'login' */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */

    open fun addListener(
        event: String /* 'login' */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */

    open fun removeListener(
        event: String /* 'login' */,
        listener: (authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): ClientRequest /* this */

    open fun on(
        event: String /* 'redirect' */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Record<String, Array<String>>) -> Unit
    ): ClientRequest /* this */

    open fun once(
        event: String /* 'redirect' */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Record<String, Array<String>>) -> Unit
    ): ClientRequest /* this */

    open fun addListener(
        event: String /* 'redirect' */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Record<String, Array<String>>) -> Unit
    ): ClientRequest /* this */

    open fun removeListener(
        event: String /* 'redirect' */,
        listener: (statusCode: Number, method: String, redirectUrl: String, responseHeaders: Record<String, Array<String>>) -> Unit
    ): ClientRequest /* this */

    open fun on(event: String /* 'response' */, listener: (response: IncomingMessage) -> Unit): ClientRequest /* this */
    open fun once(
        event: String /* 'response' */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */

    open fun addListener(
        event: String /* 'response' */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */

    open fun removeListener(
        event: String /* 'response' */,
        listener: (response: IncomingMessage) -> Unit
    ): ClientRequest /* this */

    constructor(options: ClientRequestConstructorOptions)
    constructor(options: String)

    open fun abort()
    open fun end(
        chunk: String = definedExternally,
        encoding: String = definedExternally,
        callback: () -> Unit = definedExternally
    )

    open fun end(
        chunk: Buffer = definedExternally,
        encoding: String = definedExternally,
        callback: () -> Unit = definedExternally
    )

    open fun followRedirect()
    open fun getHeader(name: String): String
    open fun getUploadProgress(): UploadProgress
    open fun removeHeader(name: String)
    open fun setHeader(name: String, value: String)
    open fun write(chunk: String, encoding: String = definedExternally, callback: () -> Unit = definedExternally)
    open fun write(chunk: Buffer, encoding: String = definedExternally, callback: () -> Unit = definedExternally)
    open var chunkedEncoding: Boolean
}

external interface Clipboard {
    fun availableFormats(type: String /* 'selection' | 'clipboard' */ = definedExternally): Array<String>
    fun clear(type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun has(format: String, type: String /* 'selection' | 'clipboard' */ = definedExternally): Boolean
    fun read(format: String): String
    fun readBookmark(): ReadBookmark
    fun readBuffer(format: String): Buffer
    fun readFindText(): String
    fun readHTML(type: String /* 'selection' | 'clipboard' */ = definedExternally): String
    fun readImage(type: String /* 'selection' | 'clipboard' */ = definedExternally): NativeImage
    fun readRTF(type: String /* 'selection' | 'clipboard' */ = definedExternally): String
    fun readText(type: String /* 'selection' | 'clipboard' */ = definedExternally): String
    fun write(data: Data, type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun writeBookmark(title: String, url: String, type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun writeBuffer(format: String, buffer: Buffer, type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun writeFindText(text: String)
    fun writeHTML(markup: String, type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun writeImage(image: NativeImage, type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun writeRTF(text: String, type: String /* 'selection' | 'clipboard' */ = definedExternally)
    fun writeText(text: String, type: String /* 'selection' | 'clipboard' */ = definedExternally)
}

external open class CommandLine {
    open fun appendArgument(value: String)
    open fun appendSwitch(the_switch: String, value: String = definedExternally)
    open fun getSwitchValue(the_switch: String): String
    open fun hasSwitch(the_switch: String): Boolean
}

external interface ContentTracing {
    fun getCategories(): Promise<Array<String>>
    fun getTraceBufferUsage(): Promise<TraceBufferUsageReturnValue>
    fun startRecording(options: TraceConfig): Promise<Unit>
    fun startRecording(options: TraceCategoriesAndOptions): Promise<Unit>
    fun stopRecording(resultFilePath: String = definedExternally): Promise<String>
}

external interface ContextBridge : EventEmitter {
    fun exposeInMainWorld(apiKey: String, api: Record<String, Any>)
}

external interface Cookie {
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var expirationDate: Number?
        get() = definedExternally
        set(value) = definedExternally
    var hostOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var httpOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var name: String
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var secure: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var session: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var value: String
}

external open class Cookies : EventEmitter {
    open fun on(event: String /* 'changed' */, listener: Function<*>): Cookies /* this */
    open fun once(event: String /* 'changed' */, listener: Function<*>): Cookies /* this */
    open fun addListener(event: String /* 'changed' */, listener: Function<*>): Cookies /* this */
    open fun removeListener(event: String /* 'changed' */, listener: Function<*>): Cookies /* this */
    open fun flushStore(): Promise<Unit>
    open fun get(filter: CookiesGetFilter): Promise<Array<Cookie>>
    open fun remove(url: String, name: String): Promise<Unit>
    open fun set(details: CookiesSetDetails): Promise<Unit>
}

external interface CPUUsage {
    var idleWakeupsPerSecond: Number
    var percentCPUUsage: Number
}

external interface CrashReport {
    var date: Date
    var id: String
}

external interface CrashReporter {
    fun addExtraParameter(key: String, value: String)
    fun getCrashesDirectory(): String
    fun getLastCrashReport(): CrashReport
    fun getParameters()
    fun getUploadedReports(): Array<CrashReport>
    fun getUploadToServer(): Boolean
    fun removeExtraParameter(key: String)
    fun setUploadToServer(uploadToServer: Boolean)
    fun start(options: CrashReporterStartOptions)
}

external interface CustomScheme {
    var privileges: Privileges?
        get() = definedExternally
        set(value) = definedExternally
    var scheme: String
}

external open class Debugger : EventEmitter {
    open fun on(event: String /* 'detach' */, listener: (event: Event, reason: String) -> Unit): Debugger /* this */
    open fun once(event: String /* 'detach' */, listener: (event: Event, reason: String) -> Unit): Debugger /* this */
    open fun addListener(
        event: String /* 'detach' */,
        listener: (event: Event, reason: String) -> Unit
    ): Debugger /* this */

    open fun removeListener(
        event: String /* 'detach' */,
        listener: (event: Event, reason: String) -> Unit
    ): Debugger /* this */

    open fun on(
        event: String /* 'message' */,
        listener: (event: Event, method: String, params: Any, sessionId: String) -> Unit
    ): Debugger /* this */

    open fun once(
        event: String /* 'message' */,
        listener: (event: Event, method: String, params: Any, sessionId: String) -> Unit
    ): Debugger /* this */

    open fun addListener(
        event: String /* 'message' */,
        listener: (event: Event, method: String, params: Any, sessionId: String) -> Unit
    ): Debugger /* this */

    open fun removeListener(
        event: String /* 'message' */,
        listener: (event: Event, method: String, params: Any, sessionId: String) -> Unit
    ): Debugger /* this */

    open fun attach(protocolVersion: String = definedExternally)
    open fun detach()
    open fun isAttached(): Boolean
    open fun sendCommand(
        method: String,
        commandParams: Any = definedExternally,
        sessionId: String = definedExternally
    ): Promise<Any>
}

external interface DesktopCapturer {
    fun getSources(options: SourcesOptions): Promise<Array<DesktopCapturerSource>>
}

external interface DesktopCapturerSource {
    var appIcon: NativeImage
    var display_id: String
    var id: String
    var name: String
    var thumbnail: NativeImage
}

external interface Dialog {
    fun showCertificateTrustDialog(browserWindow: BrowserWindow, options: CertificateTrustDialogOptions): Promise<Unit>
    fun showCertificateTrustDialog(options: CertificateTrustDialogOptions): Promise<Unit>
    fun showErrorBox(title: String, content: String)
    fun showMessageBox(browserWindow: BrowserWindow, options: MessageBoxOptions): Promise<MessageBoxReturnValue>
    fun showMessageBox(options: MessageBoxOptions): Promise<MessageBoxReturnValue>
    fun showMessageBoxSync(browserWindow: BrowserWindow, options: MessageBoxSyncOptions): Number
    fun showMessageBoxSync(options: MessageBoxSyncOptions): Number
    fun showOpenDialog(browserWindow: BrowserWindow, options: OpenDialogOptions): Promise<OpenDialogReturnValue>
    fun showOpenDialog(options: OpenDialogOptions): Promise<OpenDialogReturnValue>
    fun showOpenDialogSync(browserWindow: BrowserWindow, options: OpenDialogSyncOptions): Array<String>?
    fun showOpenDialogSync(options: OpenDialogSyncOptions): Array<String>?
    fun showSaveDialog(browserWindow: BrowserWindow, options: SaveDialogOptions): Promise<SaveDialogReturnValue>
    fun showSaveDialog(options: SaveDialogOptions): Promise<SaveDialogReturnValue>
    fun showSaveDialogSync(browserWindow: BrowserWindow, options: SaveDialogSyncOptions): String?
    fun showSaveDialogSync(options: SaveDialogSyncOptions): String?
}

external interface Display {
    var accelerometerSupport: String /* 'available' | 'unavailable' | 'unknown' */
    var bounds: Rectangle
    var colorDepth: Number
    var colorSpace: String
    var depthPerComponent: Number
    var id: Number
    var internal: Boolean
    var monochrome: Boolean
    var rotation: Number
    var scaleFactor: Number
    var size: Size
    var touchSupport: String /* 'available' | 'unavailable' | 'unknown' */
    var workArea: Rectangle
    var workAreaSize: Size
}

external open class Dock {
    open fun bounce(type: String /* 'critical' | 'informational' */ = definedExternally): Number
    open fun cancelBounce(id: Number)
    open fun downloadFinished(filePath: String)
    open fun getBadge(): String
    open fun getMenu(): Menu?
    open fun hide()
    open fun isVisible(): Boolean
    open fun setBadge(text: String)
    open fun setIcon(image: NativeImage)
    open fun setIcon(image: String)
    open fun setMenu(menu: Menu)
    open fun show(): Promise<Unit>
}

external open class DownloadItem : EventEmitter {
    open fun on(
        event: String /* 'done' */,
        listener: (event: Event, state: String /* 'completed' | 'cancelled' | 'interrupted' */) -> Unit
    ): DownloadItem /* this */

    open fun once(
        event: String /* 'done' */,
        listener: (event: Event, state: String /* 'completed' | 'cancelled' | 'interrupted' */) -> Unit
    ): DownloadItem /* this */

    open fun addListener(
        event: String /* 'done' */,
        listener: (event: Event, state: String /* 'completed' | 'cancelled' | 'interrupted' */) -> Unit
    ): DownloadItem /* this */

    open fun removeListener(
        event: String /* 'done' */,
        listener: (event: Event, state: String /* 'completed' | 'cancelled' | 'interrupted' */) -> Unit
    ): DownloadItem /* this */

    open fun cancel()
    open fun canResume(): Boolean
    open fun getContentDisposition(): String
    open fun getETag(): String
    open fun getFilename(): String
    open fun getLastModifiedTime(): String
    open fun getMimeType(): String
    open fun getReceivedBytes(): Number
    open fun getSaveDialogOptions(): SaveDialogOptions
    open fun getSavePath(): String
    open fun getStartTime(): Number
    open fun getState(): String /* 'progressing' | 'completed' | 'cancelled' | 'interrupted' */
    open fun getTotalBytes(): Number
    open fun getURL(): String
    open fun getURLChain(): Array<String>
    open fun hasUserGesture(): Boolean
    open fun isPaused(): Boolean
    open fun pause()
    open fun resume()
    open fun setSaveDialogOptions(options: SaveDialogOptions)
    open fun setSavePath(path: String)
    open var savePath: String
}

external interface Event : GlobalEvent {
    var preventDefault: () -> Unit
}

external interface ExtensionInfo {
    var name: String
    var version: String
}

external interface FileFilter {
    var extensions: Array<String>
    var name: String
}

external interface FilePathWithHeaders {
    var headers: Record<String, String>?
        get() = definedExternally
        set(value) = definedExternally
    var path: String
}

external interface GlobalShortcut {
    fun isRegistered(accelerator: dynamic): Boolean
    fun register(accelerator: dynamic, callback: () -> Unit): Boolean
    fun registerAll(accelerators: Array<String>, callback: () -> Unit)
    fun unregister(accelerator: dynamic)
    fun unregisterAll()
}

external interface GPUFeatureStatus {
    operator fun get(key: String): String
    operator fun set(key: String, value: String)
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
    fun on(event: String /* 'transactions-updated' */, listener: Function<*>): InAppPurchase /* this */
    fun once(event: String /* 'transactions-updated' */, listener: Function<*>): InAppPurchase /* this */
    fun addListener(event: String /* 'transactions-updated' */, listener: Function<*>): InAppPurchase /* this */
    fun removeListener(event: String /* 'transactions-updated' */, listener: Function<*>): InAppPurchase /* this */
    fun canMakePayments(): Boolean
    fun finishAllTransactions()
    fun finishTransactionByDate(date: String)
    fun getProducts(productIDs: Array<String>): Promise<Array<Product>>
    fun getReceiptURL(): String
    fun purchaseProduct(productID: String, quantity: Number = definedExternally): Promise<Boolean>
}

external open class IncomingMessage : EventEmitter {
    open fun on(event: String /* 'aborted' | 'end' | 'error' */, listener: Function<*>): IncomingMessage /* this */
    open fun once(event: String /* 'aborted' | 'end' | 'error' */, listener: Function<*>): IncomingMessage /* this */
    open fun addListener(
        event: String /* 'aborted' | 'end' | 'error' */,
        listener: Function<*>
    ): IncomingMessage /* this */

    open fun removeListener(
        event: String /* 'aborted' | 'end' | 'error' */,
        listener: Function<*>
    ): IncomingMessage /* this */

    open fun on(event: String /* 'data' */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */
    open fun once(event: String /* 'data' */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */
    open fun addListener(event: String /* 'data' */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */
    open fun removeListener(event: String /* 'data' */, listener: (chunk: Buffer) -> Unit): IncomingMessage /* this */
    open var headers: Record<String, Array<String>>
    open var httpVersion: String
    open var httpVersionMajor: Number
    open var httpVersionMinor: Number
    open var statusCode: Number
    open var statusMessage: String
}

external interface InputEvent {
    var modifiers: Array<String /* 'shift' | 'control' | 'ctrl' | 'alt' | 'meta' | 'command' | 'cmd' | 'isKeypad' | 'isAutoRepeat' | 'leftButtonDown' | 'middleButtonDown' | 'rightButtonDown' | 'capsLock' | 'numLock' | 'left' | 'right' */>?
        get() = definedExternally
        set(value) = definedExternally
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
    fun handle(channel: String, listener: (event: IpcMainInvokeEvent, args: Any) -> dynamic)
    fun handleOnce(channel: String, listener: (event: IpcMainInvokeEvent, args: Any) -> dynamic)
    fun on(channel: String, listener: (event: IpcMainEvent, args: Any) -> Unit): IpcMain /* this */
    fun once(channel: String, listener: (event: IpcMainEvent, args: Any) -> Unit): IpcMain /* this */
    override fun removeAllListeners(event: String): IpcMain /* this */
    fun removeHandler(channel: String)
    override fun removeListener(event: String, listener: (args: Any) -> Unit): IpcMain /* this */
}

external interface IpcMainEvent : Event {
    var frameId: Number
    var reply: Function<*>
    var sender: WebContents
}

external interface IpcMainInvokeEvent : Event {
    var frameId: Number
    var sender: WebContents
}

external interface IpcRenderer : EventEmitter {
    fun invoke(channel: String, vararg args: Any): Promise<Any>
    fun on(channel: String, listener: (event: IpcRendererEvent, args: Any) -> Unit): IpcRenderer /* this */
    fun once(channel: String, listener: (event: IpcRendererEvent, args: Any) -> Unit): IpcRenderer /* this */
    override fun removeAllListeners(event: String): IpcRenderer /* this */
    override fun removeListener(event: String, listener: (args: Any) -> Unit): IpcRenderer /* this */
    fun send(channel: String, vararg args: Any)
    fun sendSync(channel: String, vararg args: Any): Any
    fun sendTo(webContentsId: Number, channel: String, vararg args: Any)
    fun sendToHost(channel: String, vararg args: Any)
}

external interface IpcRendererEvent : Event {
    var sender: IpcRenderer
    var senderId: Number
}

external interface JumpListCategory {
    var items: Array<JumpListItem>?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* 'tasks' | 'frequent' | 'recent' | 'custom' */
        get() = definedExternally
        set(value) = definedExternally
}

external interface JumpListItem {
    var args: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var iconIndex: Number?
        get() = definedExternally
        set(value) = definedExternally
    var iconPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var program: String?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* 'task' | 'separator' | 'file' */
        get() = definedExternally
        set(value) = definedExternally
    var workingDirectory: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface KeyboardEvent : Event {
    var altKey: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ctrlKey: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var metaKey: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var shiftKey: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var triggeredByAccelerator: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface KeyboardInputEvent : InputEvent {
    var keyCode: String
    var type: String /* 'keyDown' | 'keyUp' | 'char' */
}

external interface MemoryInfo {
    var peakWorkingSetSize: Number
    var privateBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var workingSetSize: Number
}

external interface MemoryUsageDetails {
    var count: Number
    var liveSize: Number
    var size: Number
}

external open class Menu {
    open fun on(
        event: String /* 'menu-will-close' | 'menu-will-show' */,
        listener: (event: Event) -> Unit
    ): Menu /* this */

    open fun once(
        event: String /* 'menu-will-close' | 'menu-will-show' */,
        listener: (event: Event) -> Unit
    ): Menu /* this */

    open fun addListener(
        event: String /* 'menu-will-close' | 'menu-will-show' */,
        listener: (event: Event) -> Unit
    ): Menu /* this */

    open fun removeListener(
        event: String /* 'menu-will-close' | 'menu-will-show' */,
        listener: (event: Event) -> Unit
    ): Menu /* this */

    open fun append(menuItem: MenuItem)
    open fun closePopup(browserWindow: BrowserWindow = definedExternally)
    open fun getMenuItemById(id: String): MenuItem
    open fun insert(pos: Number, menuItem: MenuItem)
    open fun popup(options: PopupOptions = definedExternally)
    open var items: Array<MenuItem>

    companion object {
        fun buildFromTemplate(template: Array<dynamic /* MenuItemConstructorOptions | MenuItem */>): Menu
        fun getApplicationMenu(): Menu?
        fun sendActionToFirstResponder(action: String)
        fun setApplicationMenu(menu: Menu?)
    }
}

external open class MenuItem(options: MenuItemConstructorOptions) {
    open var accelerator: dynamic
    open var checked: Boolean
    open var click: Function<*>
    open var commandId: Number
    open var enabled: Boolean
    open var icon: dynamic /* NativeImage | String */
    open var id: String
    open var label: String
    open var menu: Menu
    open var registerAccelerator: Boolean
    open var role: String /* 'undo' | 'redo' | 'cut' | 'copy' | 'paste' | 'pasteAndMatchStyle' | 'delete' | 'selectAll' | 'reload' | 'forceReload' | 'toggleDevTools' | 'resetZoom' | 'zoomIn' | 'zoomOut' | 'togglefullscreen' | 'window' | 'minimize' | 'close' | 'help' | 'about' | 'services' | 'hide' | 'hideOthers' | 'unhide' | 'quit' | 'startSpeaking' | 'stopSpeaking' | 'zoom' | 'front' | 'appMenu' | 'fileMenu' | 'editMenu' | 'viewMenu' | 'recentDocuments' | 'toggleTabBar' | 'selectNextTab' | 'selectPreviousTab' | 'mergeAllWindows' | 'clearRecentDocuments' | 'moveTabToNewWindow' | 'windowMenu' */
    open var sublabel: String
    open var submenu: Menu
    open var toolTip: String
    open var type: String /* 'normal' | 'separator' | 'submenu' | 'checkbox' | 'radio' */
    open var visible: Boolean
}

external interface MimeTypedBuffer {
    var data: Buffer
    var mimeType: String
}

external interface MouseInputEvent : InputEvent {
    var button: String? /* 'left' | 'middle' | 'right' */
        get() = definedExternally
        set(value) = definedExternally
    var clickCount: Number?
        get() = definedExternally
        set(value) = definedExternally
    var globalX: Number?
        get() = definedExternally
        set(value) = definedExternally
    var globalY: Number?
        get() = definedExternally
        set(value) = definedExternally
    var movementX: Number?
        get() = definedExternally
        set(value) = definedExternally
    var movementY: Number?
        get() = definedExternally
        set(value) = definedExternally
    var type: String /* 'mouseDown' | 'mouseUp' | 'mouseEnter' | 'mouseLeave' | 'contextMenu' | 'mouseWheel' | 'mouseMove' */
    var x: Number
    var y: Number
}

external interface MouseWheelInputEvent : MouseInputEvent {
    var accelerationRatioX: Number?
        get() = definedExternally
        set(value) = definedExternally
    var accelerationRatioY: Number?
        get() = definedExternally
        set(value) = definedExternally
    var canScroll: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var deltaX: Number?
        get() = definedExternally
        set(value) = definedExternally
    var deltaY: Number?
        get() = definedExternally
        set(value) = definedExternally
    var hasPreciseScrollingDeltas: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    override var type: String /* 'mouseWheel' */
    var wheelTicksX: Number?
        get() = definedExternally
        set(value) = definedExternally
    var wheelTicksY: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external open class NativeImage {
    open fun addRepresentation(options: AddRepresentationOptions)
    open fun crop(rect: Rectangle): NativeImage
    open fun getAspectRatio(): Number
    open fun getBitmap(options: BitmapOptions = definedExternally): Buffer
    open fun getNativeHandle(): Buffer
    open fun getSize(): Size
    open fun isEmpty(): Boolean
    open fun isTemplateImage(): Boolean
    open fun resize(options: ResizeOptions): NativeImage
    open fun setTemplateImage(option: Boolean)
    open fun toBitmap(options: ToBitmapOptions = definedExternally): Buffer
    open fun toDataURL(options: ToDataURLOptions = definedExternally): String
    open fun toJPEG(quality: Number): Buffer
    open fun toPNG(options: ToPNGOptions = definedExternally): Buffer
    open var isMacTemplateImage: Boolean

    companion object {
        fun createEmpty(): NativeImage
        fun createFromBitmap(buffer: Buffer, options: CreateFromBitmapOptions): NativeImage
        fun createFromBuffer(buffer: Buffer, options: CreateFromBufferOptions = definedExternally): NativeImage
        fun createFromDataURL(dataURL: String): NativeImage
        fun createFromNamedImage(imageName: String, hslShift: Array<Number> = definedExternally): NativeImage
        fun createFromPath(path: String): NativeImage
    }
}

external interface NativeTheme : EventEmitter {
    fun on(event: String /* 'updated' */, listener: Function<*>): NativeTheme /* this */
    fun once(event: String /* 'updated' */, listener: Function<*>): NativeTheme /* this */
    fun addListener(event: String /* 'updated' */, listener: Function<*>): NativeTheme /* this */
    fun removeListener(event: String /* 'updated' */, listener: Function<*>): NativeTheme /* this */
    var shouldUseDarkColors: Boolean
    var shouldUseHighContrastColors: Boolean
    var shouldUseInvertedColorScheme: Boolean
    var themeSource: String /* 'system' | 'light' | 'dark' */
}

external interface Net {
    fun request(options: ClientRequestConstructorOptions): ClientRequest
    fun request(options: String): ClientRequest
}

external interface NetLog {
    fun startLogging(path: String, options: StartLoggingOptions = definedExternally): Promise<Unit>
    fun stopLogging(): Promise<String>
    var currentlyLogging: Boolean
    var currentlyLoggingPath: String
}

external interface NewWindowEvent : Event {
    var url: String
    var frameName: String
    var disposition: String /* 'default' | 'foreground-tab' | 'background-tab' | 'new-window' | 'save-to-disk' | 'other' */
    var options: BrowserWindowConstructorOptions
    var newGuest: BrowserWindow?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Notification(options: NotificationConstructorOptions = definedExternally) : EventEmitter {
    open fun on(event: String /* 'action' */, listener: (event: Event, index: Number) -> Unit): Notification /* this */
    open fun once(
        event: String /* 'action' */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */

    open fun addListener(
        event: String /* 'action' */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */

    open fun removeListener(
        event: String /* 'action' */,
        listener: (event: Event, index: Number) -> Unit
    ): Notification /* this */

    open fun on(
        event: String /* 'click' | 'close' | 'show' */,
        listener: (event: Event) -> Unit
    ): Notification /* this */

    open fun once(
        event: String /* 'click' | 'close' | 'show' */,
        listener: (event: Event) -> Unit
    ): Notification /* this */

    open fun addListener(
        event: String /* 'click' | 'close' | 'show' */,
        listener: (event: Event) -> Unit
    ): Notification /* this */

    open fun removeListener(
        event: String /* 'click' | 'close' | 'show' */,
        listener: (event: Event) -> Unit
    ): Notification /* this */

    open fun on(event: String /* 'reply' */, listener: (event: Event, reply: String) -> Unit): Notification /* this */
    open fun once(event: String /* 'reply' */, listener: (event: Event, reply: String) -> Unit): Notification /* this */
    open fun addListener(
        event: String /* 'reply' */,
        listener: (event: Event, reply: String) -> Unit
    ): Notification /* this */

    open fun removeListener(
        event: String /* 'reply' */,
        listener: (event: Event, reply: String) -> Unit
    ): Notification /* this */

    open fun close()
    open fun show()
    open var actions: Array<NotificationAction>
    open var body: String
    open var closeButtonText: String
    open var hasReply: Boolean
    open var replyPlaceholder: String
    open var silent: Boolean
    open var sound: String
    open var subtitle: String
    open var timeoutType: String /* 'default' | 'never' */
    open var title: String
    open var urgency: String /* 'normal' | 'critical' | 'low' */

    companion object {
        fun isSupported(): Boolean
    }
}

external interface NotificationAction {
    var text: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String /* 'button' */
}

external interface Point {
    var x: Number
    var y: Number
}

external interface PowerMonitor : EventEmitter {
    fun on(
        event: String /* 'lock-screen' | 'on-ac' | 'on-battery' | 'resume' | 'shutdown' | 'suspend' | 'unlock-screen' */,
        listener: Function<*>
    ): PowerMonitor /* this */

    fun once(
        event: String /* 'lock-screen' | 'on-ac' | 'on-battery' | 'resume' | 'shutdown' | 'suspend' | 'unlock-screen' */,
        listener: Function<*>
    ): PowerMonitor /* this */

    fun addListener(
        event: String /* 'lock-screen' | 'on-ac' | 'on-battery' | 'resume' | 'shutdown' | 'suspend' | 'unlock-screen' */,
        listener: Function<*>
    ): PowerMonitor /* this */

    fun removeListener(
        event: String /* 'lock-screen' | 'on-ac' | 'on-battery' | 'resume' | 'shutdown' | 'suspend' | 'unlock-screen' */,
        listener: Function<*>
    ): PowerMonitor /* this */

    fun getSystemIdleState(idleThreshold: Number): String /* 'active' | 'idle' | 'locked' | 'unknown' */
    fun getSystemIdleTime(): Number
}

external interface PowerSaveBlocker {
    fun isStarted(id: Number): Boolean
    fun start(type: String /* 'prevent-app-suspension' | 'prevent-display-sleep' */): Number
    fun stop(id: Number)
}

external interface PrinterInfo {
    var description: String
    var isDefault: Boolean
    var name: String
    var status: Number
}

external interface ProcessMemoryInfo {
    var private: Number
    var residentSet: Number
    var shared: Number
}

external interface ProcessMetric {
    var cpu: CPUUsage
    var creationTime: Number
    var integrityLevel: String? /* 'untrusted' | 'low' | 'medium' | 'high' | 'unknown' */
        get() = definedExternally
        set(value) = definedExternally
    var memory: MemoryInfo
    var pid: Number
    var sandboxed: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var type: String /* 'Browser' | 'Tab' | 'Utility' | 'Zygote' | 'Sandbox helper' | 'GPU' | 'Pepper Plugin' | 'Pepper Plugin Broker' | 'Unknown' */
}

external interface Product {
    var contentLengths: Array<Number>
    var contentVersion: String
    var formattedPrice: String
    var isDownloadable: Boolean
    var localizedDescription: String
    var localizedTitle: String
    var price: Number
    var productIdentifier: String
}

external interface Protocol {
    fun interceptBufferProtocol(
        scheme: String,
        handler: (request: Request, callback: (buffer: Buffer) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun interceptFileProtocol(
        scheme: String,
        handler: (request: Request, callback: (filePath: String) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun interceptHttpProtocol(
        scheme: String,
        handler: (request: Request, callback: (redirectRequest: RedirectRequest) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun interceptStreamProtocol(
        scheme: String,
        handler: (request: Request, callback: (stream: dynamic /* NodeJS.ReadableStream | StreamProtocolResponse */) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun interceptStringProtocol(
        scheme: String,
        handler: (request: Request, callback: (data: dynamic /* String | StringProtocolResponse */) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun isProtocolHandled(scheme: String): Promise<Boolean>
    fun registerBufferProtocol(
        scheme: String,
        handler: (request: Request, callback: (buffer: dynamic /* Buffer | MimeTypedBuffer */) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun registerFileProtocol(
        scheme: String,
        handler: (request: Request, callback: (filePath: dynamic /* String | FilePathWithHeaders */) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun registerHttpProtocol(
        scheme: String,
        handler: (request: Request, callback: (redirectRequest: RedirectRequest) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun registerSchemesAsPrivileged(customSchemes: Array<CustomScheme>)
    fun registerStreamProtocol(
        scheme: String,
        handler: (request: Request, callback: (stream: dynamic /* NodeJS.ReadableStream | StreamProtocolResponse */) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun registerStringProtocol(
        scheme: String,
        handler: (request: Request, callback: (data: dynamic /* String | StringProtocolResponse */) -> Unit) -> Unit,
        completion: (error: Error) -> Unit = definedExternally
    )

    fun uninterceptProtocol(scheme: String, completion: (error: Error) -> Unit = definedExternally)
    fun unregisterProtocol(scheme: String, completion: (error: Error) -> Unit = definedExternally)
}

external interface ProtocolRequest {
    var method: String
    var referrer: String
    var uploadData: Array<UploadData>?
        get() = definedExternally
        set(value) = definedExternally
    var url: String
}

external interface ProtocolResponse {
    var charset: String?
        get() = definedExternally
        set(value) = definedExternally
    var data: dynamic /* Buffer? | String? | NodeJS.ReadableStream? */
        get() = definedExternally
        set(value) = definedExternally
    var error: Number?
        get() = definedExternally
        set(value) = definedExternally
    var headers: Record<String, dynamic /* String | Array<String> */>?
        get() = definedExternally
        set(value) = definedExternally
    var method: String?
        get() = definedExternally
        set(value) = definedExternally
    var mimeType: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var referrer: String?
        get() = definedExternally
        set(value) = definedExternally
    var session: Session?
        get() = definedExternally
        set(value) = definedExternally
    var statusCode: Number?
        get() = definedExternally
        set(value) = definedExternally
    var uploadData: ProtocolResponseUploadData?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ProtocolResponseUploadData {
    var contentType: String
    var data: dynamic /* String | Buffer */
        get() = definedExternally
        set(value) = definedExternally
}

external interface Rectangle {
    var height: Number
    var width: Number
    var x: Number
    var y: Number
}

external interface RectanglePartial {
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var x: Number?
        get() = definedExternally
        set(value) = definedExternally
    var y: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Referrer {
    var policy: String /* 'default' | 'unsafe-url' | 'no-referrer-when-downgrade' | 'no-referrer' | 'origin' | 'strict-origin-when-cross-origin' | 'same-origin' | 'strict-origin' */
    var url: String
}

external interface Remote : MainInterface {
    fun getCurrentWebContents(): WebContents
    fun getCurrentWindow(): BrowserWindow
    fun getGlobal(name: String): Any
    fun require(module: String): Any
    var process: Process
}

external interface RemoveClientCertificate {
    var origin: String
    var type: String
}

external interface RemovePassword {
    var origin: String?
        get() = definedExternally
        set(value) = definedExternally
    var password: String?
        get() = definedExternally
        set(value) = definedExternally
    var realm: String?
        get() = definedExternally
        set(value) = definedExternally
    var scheme: String? /* 'basic' | 'digest' | 'ntlm' | 'negotiate' */
        get() = definedExternally
        set(value) = definedExternally
    var type: String
    var username: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Screen : EventEmitter {
    fun on(
        event: String /* 'display-added' */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun once(
        event: String /* 'display-added' */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun addListener(
        event: String /* 'display-added' */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun removeListener(
        event: String /* 'display-added' */,
        listener: (event: Event, newDisplay: Display) -> Unit
    ): Screen /* this */

    fun on(
        event: String /* 'display-metrics-changed' */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun once(
        event: String /* 'display-metrics-changed' */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun addListener(
        event: String /* 'display-metrics-changed' */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun removeListener(
        event: String /* 'display-metrics-changed' */,
        listener: (event: Event, display: Display, changedMetrics: Array<String>) -> Unit
    ): Screen /* this */

    fun on(
        event: String /* 'display-removed' */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun once(
        event: String /* 'display-removed' */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun addListener(
        event: String /* 'display-removed' */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun removeListener(
        event: String /* 'display-removed' */,
        listener: (event: Event, oldDisplay: Display) -> Unit
    ): Screen /* this */

    fun dipToScreenPoint(point: Point): Point
    fun dipToScreenRect(window: BrowserWindow?, rect: Rectangle): Rectangle
    fun getAllDisplays(): Array<Display>
    fun getCursorScreenPoint(): Point
    fun getDisplayMatching(rect: Rectangle): Display
    fun getDisplayNearestPoint(point: Point): Display
    fun getPrimaryDisplay(): Display
    fun screenToDipPoint(point: Point): Point
    fun screenToDipRect(window: BrowserWindow?, rect: Rectangle): Rectangle
}

external interface ScrubberItem {
    var icon: NativeImage?
        get() = definedExternally
        set(value) = definedExternally
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SegmentedControlSegment {
    var enabled: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var icon: NativeImage?
        get() = definedExternally
        set(value) = definedExternally
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Session : EventEmitter {
    open fun on(
        event: String /* 'preconnect' */,
        listener: (event: Event, preconnectUrl: String, allowCredentials: Boolean) -> Unit
    ): Session /* this */

    open fun once(
        event: String /* 'preconnect' */,
        listener: (event: Event, preconnectUrl: String, allowCredentials: Boolean) -> Unit
    ): Session /* this */

    open fun addListener(
        event: String /* 'preconnect' */,
        listener: (event: Event, preconnectUrl: String, allowCredentials: Boolean) -> Unit
    ): Session /* this */

    open fun removeListener(
        event: String /* 'preconnect' */,
        listener: (event: Event, preconnectUrl: String, allowCredentials: Boolean) -> Unit
    ): Session /* this */

    open fun on(
        event: String /* 'spellcheck-dictionary-download-begin' | 'spellcheck-dictionary-download-failure' | 'spellcheck-dictionary-download-success' | 'spellcheck-dictionary-initialized' */,
        listener: (event: Event, languageCode: String) -> Unit
    ): Session /* this */

    open fun once(
        event: String /* 'spellcheck-dictionary-download-begin' | 'spellcheck-dictionary-download-failure' | 'spellcheck-dictionary-download-success' | 'spellcheck-dictionary-initialized' */,
        listener: (event: Event, languageCode: String) -> Unit
    ): Session /* this */

    open fun addListener(
        event: String /* 'spellcheck-dictionary-download-begin' | 'spellcheck-dictionary-download-failure' | 'spellcheck-dictionary-download-success' | 'spellcheck-dictionary-initialized' */,
        listener: (event: Event, languageCode: String) -> Unit
    ): Session /* this */

    open fun removeListener(
        event: String /* 'spellcheck-dictionary-download-begin' | 'spellcheck-dictionary-download-failure' | 'spellcheck-dictionary-download-success' | 'spellcheck-dictionary-initialized' */,
        listener: (event: Event, languageCode: String) -> Unit
    ): Session /* this */

    open fun on(
        event: String /* 'will-download' */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */

    open fun once(
        event: String /* 'will-download' */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */

    open fun addListener(
        event: String /* 'will-download' */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */

    open fun removeListener(
        event: String /* 'will-download' */,
        listener: (event: Event, item: DownloadItem, webContents: WebContents) -> Unit
    ): Session /* this */

    open fun addWordToSpellCheckerDictionary(word: String): Boolean
    open fun allowNTLMCredentialsForDomains(domains: String)
    open fun clearAuthCache(options: RemovePassword): Promise<Unit>
    open fun clearAuthCache(options: RemoveClientCertificate): Promise<Unit>
    open fun clearCache(): Promise<Unit>
    open fun clearHostResolverCache(): Promise<Unit>
    open fun clearStorageData(options: ClearStorageDataOptions = definedExternally): Promise<Unit>
    open fun createInterruptedDownload(options: CreateInterruptedDownloadOptions)
    open fun disableNetworkEmulation()
    open fun downloadURL(url: String)
    open fun enableNetworkEmulation(options: EnableNetworkEmulationOptions)
    open fun flushStorageData()
    open fun getBlobData(identifier: String): Promise<Buffer>
    open fun getCacheSize(): Promise<Number>
    open fun getPreloads(): Array<String>
    open fun getSpellCheckerLanguages(): Array<String>
    open fun getUserAgent(): String
    open fun preconnect(options: PreconnectOptions)
    open fun resolveProxy(url: String): Promise<String>
    open fun setCertificateVerifyProc(proc: ((request: CertificateVerifyProcProcRequest, callback: (verificationResult: Number) -> Unit) -> Unit)?)
    open fun setDownloadPath(path: String)
    open fun setPermissionCheckHandler(handler: ((webContents: WebContents, permission: String, requestingOrigin: String, details: PermissionCheckHandlerHandlerDetails) -> Boolean)?)
    open fun setPermissionRequestHandler(handler: ((webContents: WebContents, permission: String, callback: (permissionGranted: Boolean) -> Unit, details: PermissionRequestHandlerHandlerDetails) -> Unit)?)
    open fun setPreloads(preloads: Array<String>)
    open fun setProxy(config: Config): Promise<Unit>
    open fun setSpellCheckerDictionaryDownloadURL(url: String)
    open fun setSpellCheckerLanguages(languages: Array<String>)
    open fun setUserAgent(userAgent: String, acceptLanguages: String = definedExternally)
    open var availableSpellCheckerLanguages: Array<String>
    open var cookies: Cookies
    open var netLog: NetLog
    open var protocol: Protocol
    open var webRequest: WebRequest

    companion object {
        fun fromPartition(partition: String, options: FromPartitionOptions = definedExternally): Session
        var defaultSession: Session
    }
}

external interface SharedWorkerInfo {
    var id: String
    var url: String
}

external interface Shell {
    fun beep()
    fun moveItemToTrash(fullPath: String, deleteOnFail: Boolean = definedExternally): Boolean
    fun openExternal(url: String, options: OpenExternalOptions = definedExternally): Promise<Unit>
    fun openItem(fullPath: String): Boolean
    fun readShortcutLink(shortcutPath: String): ShortcutDetails
    fun showItemInFolder(fullPath: String)
    fun writeShortcutLink(
        shortcutPath: String,
        operation: String /* 'create' | 'update' | 'replace' */,
        options: ShortcutDetails
    ): Boolean

    fun writeShortcutLink(shortcutPath: String, options: ShortcutDetails): Boolean
}

external interface ShortcutDetails {
    var appUserModelId: String?
        get() = definedExternally
        set(value) = definedExternally
    var args: String?
        get() = definedExternally
        set(value) = definedExternally
    var cwd: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var icon: String?
        get() = definedExternally
        set(value) = definedExternally
    var iconIndex: Number?
        get() = definedExternally
        set(value) = definedExternally
    var target: String
}

external interface Size {
    var height: Number
    var width: Number
}

external interface StreamProtocolResponse {
    var data: ReadableStream?
    var headers: Record<String, dynamic /* String | Array<String> */>?
        get() = definedExternally
        set(value) = definedExternally
    var statusCode: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StringProtocolResponse {
    var charset: String?
        get() = definedExternally
        set(value) = definedExternally
    var data: String?
    var mimeType: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SystemPreferences : EventEmitter {
    fun on(
        event: String /* 'accent-color-changed' */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun once(
        event: String /* 'accent-color-changed' */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun addListener(
        event: String /* 'accent-color-changed' */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun removeListener(
        event: String /* 'accent-color-changed' */,
        listener: (event: Event, newColor: String) -> Unit
    ): SystemPreferences /* this */

    fun on(event: String /* 'color-changed' */, listener: (event: Event) -> Unit): SystemPreferences /* this */
    fun once(event: String /* 'color-changed' */, listener: (event: Event) -> Unit): SystemPreferences /* this */
    fun addListener(event: String /* 'color-changed' */, listener: (event: Event) -> Unit): SystemPreferences /* this */
    fun removeListener(
        event: String /* 'color-changed' */,
        listener: (event: Event) -> Unit
    ): SystemPreferences /* this */

    fun on(
        event: String /* 'high-contrast-color-scheme-changed' */,
        listener: (event: Event, highContrastColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun once(
        event: String /* 'high-contrast-color-scheme-changed' */,
        listener: (event: Event, highContrastColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun addListener(
        event: String /* 'high-contrast-color-scheme-changed' */,
        listener: (event: Event, highContrastColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun removeListener(
        event: String /* 'high-contrast-color-scheme-changed' */,
        listener: (event: Event, highContrastColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun on(
        event: String /* 'inverted-color-scheme-changed' */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun once(
        event: String /* 'inverted-color-scheme-changed' */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun addListener(
        event: String /* 'inverted-color-scheme-changed' */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun removeListener(
        event: String /* 'inverted-color-scheme-changed' */,
        listener: (event: Event, invertedColorScheme: Boolean) -> Unit
    ): SystemPreferences /* this */

    fun askForMediaAccess(mediaType: String /* 'microphone' | 'camera' */): Promise<Boolean>
    fun canPromptTouchID(): Boolean
    fun getAccentColor(): String
    fun getAnimationSettings(): AnimationSettings
    fun getAppLevelAppearance(): String /* 'dark' | 'light' | 'unknown' */
    fun getColor(color: String /* '3d-dark-shadow' | '3d-face' | '3d-highlight' | '3d-light' | '3d-shadow' | 'active-border' | 'active-caption' | 'active-caption-gradient' | 'app-workspace' | 'button-text' | 'caption-text' | 'desktop' | 'disabled-text' | 'highlight' | 'highlight-text' | 'hotlight' | 'inactive-border' | 'inactive-caption' | 'inactive-caption-gradient' | 'inactive-caption-text' | 'info-background' | 'info-text' | 'menu' | 'menu-highlight' | 'menubar' | 'menu-text' | 'scrollbar' | 'window' | 'window-frame' | 'window-text' | 'alternate-selected-control-text' | 'control-background' | 'control' | 'control-text' | 'disabled-control-text' | 'find-highlight' | 'grid' | 'header-text' | 'keyboard-focus-indicator' | 'label' | 'link' | 'placeholder-text' | 'quaternary-label' | 'scrubber-textured-background' | 'secondary-label' | 'selected-content-background' | 'selected-control' | 'selected-control-text' | 'selected-menu-item-text' | 'selected-text-background' | 'selected-text' | 'separator' | 'shadow' | 'tertiary-label' | 'text-background' | 'text' | 'under-page-background' | 'unemphasized-selected-content-background' | 'unemphasized-selected-text-background' | 'unemphasized-selected-text' | 'window-background' | 'window-frame-text' */): String

    fun getEffectiveAppearance(): String /* 'dark' | 'light' | 'unknown' */
    fun getMediaAccessStatus(mediaType: String /* 'microphone' | 'camera' | 'screen' */): String /* 'not-determined' | 'granted' | 'denied' | 'restricted' | 'unknown' */
    fun getSystemColor(color: String /* 'blue' | 'brown' | 'gray' | 'green' | 'orange' | 'pink' | 'purple' | 'red' | 'yellow' */): String
    fun getUserDefault(
        key: String,
        type: String /* 'string' | 'boolean' | 'integer' | 'float' | 'double' | 'url' | 'array' | 'dictionary' */
    ): Any

    fun isAeroGlassEnabled(): Boolean
    fun isDarkMode(): Boolean
    fun isHighContrastColorScheme(): Boolean
    fun isInvertedColorScheme(): Boolean
    fun isSwipeTrackingFromScrollEventsEnabled(): Boolean
    fun isTrustedAccessibilityClient(prompt: Boolean): Boolean
    fun postLocalNotification(event: String, userInfo: Record<String, Any>)
    fun postNotification(event: String, userInfo: Record<String, Any>, deliverImmediately: Boolean = definedExternally)
    fun postWorkspaceNotification(event: String, userInfo: Record<String, Any>)
    fun promptTouchID(reason: String): Promise<Unit>
    fun registerDefaults(defaults: Record<String, dynamic /* String | Boolean | Number */>)
    fun removeUserDefault(key: String)
    fun setAppLevelAppearance(appearance: String /* 'dark' | 'light' */)
    fun setUserDefault(key: String, type: String, value: String)
    fun subscribeLocalNotification(
        event: String,
        callback: (event: String, userInfo: Record<String, Any>, obj: String) -> Unit
    ): Number

    fun subscribeNotification(
        event: String,
        callback: (event: String, userInfo: Record<String, Any>, obj: String) -> Unit
    ): Number

    fun subscribeWorkspaceNotification(
        event: String,
        callback: (event: String, userInfo: Record<String, Any>, obj: String) -> Unit
    )

    fun unsubscribeLocalNotification(id: Number)
    fun unsubscribeNotification(id: Number)
    fun unsubscribeWorkspaceNotification(id: Number)
    var appLevelAppearance: String /* 'dark' | 'light' | 'unknown' */
    var effectiveAppearance: String /* 'dark' | 'light' | 'unknown' */
}

external interface Task {
    var arguments: String
    var description: String
    var iconIndex: Number
    var iconPath: String
    var program: String
    var title: String
    var workingDirectory: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ThumbarButton {
    var click: Function<*>
    var flags: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var icon: NativeImage
    var tooltip: String?
        get() = definedExternally
        set(value) = definedExternally
}

external open class TouchBar(options: TouchBarConstructorOptions) {
    open var escapeItem: dynamic /* TouchBarButton? | TouchBarColorPicker? | TouchBarGroup? | TouchBarLabel? | TouchBarPopover? | TouchBarScrubber? | TouchBarSegmentedControl? | TouchBarSlider? | TouchBarSpacer? */

    companion object {
        var TouchBarButton: Any
        var TouchBarColorPicker: Any
        var TouchBarGroup: Any
        var TouchBarLabel: Any
        var TouchBarPopover: Any
        var TouchBarScrubber: Any
        var TouchBarSegmentedControl: Any
        var TouchBarSlider: Any
        var TouchBarSpacer: Any
    }
}

external open class TouchBarButton(options: TouchBarButtonConstructorOptions) {
    open var accessibilityLabel: String
    open var backgroundColor: String
    open var icon: NativeImage
    open var label: String
}

external open class TouchBarColorPicker(options: TouchBarColorPickerConstructorOptions) : EventEmitter {
    open var availableColors: Array<String>
    open var selectedColor: String
}

external open class TouchBarGroup(options: TouchBarGroupConstructorOptions) : EventEmitter

external open class TouchBarLabel(options: TouchBarLabelConstructorOptions) : EventEmitter {
    open var accessibilityLabel: String
    open var label: String
    open var textColor: String
}

external open class TouchBarPopover(options: TouchBarPopoverConstructorOptions) : EventEmitter {
    open var icon: NativeImage
    open var label: String
}

external open class TouchBarScrubber(options: TouchBarScrubberConstructorOptions) : EventEmitter {
    open var continuous: Boolean
    open var items: Array<ScrubberItem>
    open var mode: String /* 'fixed' | 'free' */
    open var overlayStyle: String /* 'background' | 'outline' | 'none' */
    open var selectedStyle: String /* 'background' | 'outline' | 'none' */
    open var showArrowButtons: Boolean
}

external open class TouchBarSegmentedControl(options: TouchBarSegmentedControlConstructorOptions) : EventEmitter {
    open var segments: Array<SegmentedControlSegment>
    open var segmentStyle: String
    open var selectedIndex: Number
}

external open class TouchBarSlider(options: TouchBarSliderConstructorOptions) : EventEmitter {
    open var label: String
    open var maxValue: Number
    open var minValue: Number
    open var value: Number
}

external open class TouchBarSpacer(options: TouchBarSpacerConstructorOptions) : EventEmitter

external interface TraceCategoriesAndOptions {
    var categoryFilter: String
    var traceOptions: String
}

external interface TraceConfig {
    var enable_argument_filter: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var excluded_categories: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var histogram_names: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var included_categories: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var included_process_ids: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
    var memory_dump_config: Record<String, Any>?
        get() = definedExternally
        set(value) = definedExternally
    var recording_mode: String? /* 'record-until-full' | 'record-continuously' | 'record-as-much-as-possible' | 'trace-to-console' */
        get() = definedExternally
        set(value) = definedExternally
    var trace_buffer_size_in_events: Number?
        get() = definedExternally
        set(value) = definedExternally
    var trace_buffer_size_in_kb: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Transaction {
    var errorCode: Number
    var errorMessage: String
    var originalTransactionIdentifier: String
    var payment: Payment
    var transactionDate: String
    var transactionIdentifier: String
    var transactionState: String /* 'purchasing' | 'purchased' | 'failed' | 'restored' | 'deferred' */
}

external open class Tray : EventEmitter {
    open fun on(
        event: String /* 'balloon-click' | 'balloon-closed' | 'balloon-show' | 'drag-end' | 'drag-enter' | 'drag-leave' | 'drop' */,
        listener: Function<*>
    ): Tray /* this */

    open fun once(
        event: String /* 'balloon-click' | 'balloon-closed' | 'balloon-show' | 'drag-end' | 'drag-enter' | 'drag-leave' | 'drop' */,
        listener: Function<*>
    ): Tray /* this */

    open fun addListener(
        event: String /* 'balloon-click' | 'balloon-closed' | 'balloon-show' | 'drag-end' | 'drag-enter' | 'drag-leave' | 'drop' */,
        listener: Function<*>
    ): Tray /* this */

    open fun removeListener(
        event: String /* 'balloon-click' | 'balloon-closed' | 'balloon-show' | 'drag-end' | 'drag-enter' | 'drag-leave' | 'drop' */,
        listener: Function<*>
    ): Tray /* this */

    open fun on(
        event: String /* 'click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */

    open fun once(
        event: String /* 'click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */

    open fun addListener(
        event: String /* 'click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */

    open fun removeListener(
        event: String /* 'click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle, position: Point) -> Unit
    ): Tray /* this */

    open fun on(
        event: String /* 'double-click' | 'right-click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle) -> Unit
    ): Tray /* this */

    open fun once(
        event: String /* 'double-click' | 'right-click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle) -> Unit
    ): Tray /* this */

    open fun addListener(
        event: String /* 'double-click' | 'right-click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle) -> Unit
    ): Tray /* this */

    open fun removeListener(
        event: String /* 'double-click' | 'right-click' */,
        listener: (event: KeyboardEvent, bounds: Rectangle) -> Unit
    ): Tray /* this */

    open fun on(
        event: String /* 'drop-files' */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */

    open fun once(
        event: String /* 'drop-files' */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */

    open fun addListener(
        event: String /* 'drop-files' */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */

    open fun removeListener(
        event: String /* 'drop-files' */,
        listener: (event: Event, files: Array<String>) -> Unit
    ): Tray /* this */

    open fun on(event: String /* 'drop-text' */, listener: (event: Event, text: String) -> Unit): Tray /* this */
    open fun once(event: String /* 'drop-text' */, listener: (event: Event, text: String) -> Unit): Tray /* this */
    open fun addListener(
        event: String /* 'drop-text' */,
        listener: (event: Event, text: String) -> Unit
    ): Tray /* this */

    open fun removeListener(
        event: String /* 'drop-text' */,
        listener: (event: Event, text: String) -> Unit
    ): Tray /* this */

    open fun on(
        event: String /* 'mouse-enter' | 'mouse-leave' | 'mouse-move' */,
        listener: (event: KeyboardEvent, position: Point) -> Unit
    ): Tray /* this */

    open fun once(
        event: String /* 'mouse-enter' | 'mouse-leave' | 'mouse-move' */,
        listener: (event: KeyboardEvent, position: Point) -> Unit
    ): Tray /* this */

    open fun addListener(
        event: String /* 'mouse-enter' | 'mouse-leave' | 'mouse-move' */,
        listener: (event: KeyboardEvent, position: Point) -> Unit
    ): Tray /* this */

    open fun removeListener(
        event: String /* 'mouse-enter' | 'mouse-leave' | 'mouse-move' */,
        listener: (event: KeyboardEvent, position: Point) -> Unit
    ): Tray /* this */

    constructor(image: NativeImage)
    constructor(image: String)

    open fun destroy()
    open fun displayBalloon(options: DisplayBalloonOptions)
    open fun focus()
    open fun getBounds(): Rectangle
    open fun getIgnoreDoubleClickEvents(): Boolean
    open fun getTitle(): String
    open fun isDestroyed(): Boolean
    open fun popUpContextMenu(menu: Menu = definedExternally, position: Point = definedExternally)
    open fun removeBalloon()
    open fun setContextMenu(menu: Menu?)
    open fun setIgnoreDoubleClickEvents(ignore: Boolean)
    open fun setImage(image: NativeImage)
    open fun setImage(image: String)
    open fun setPressedImage(image: NativeImage)
    open fun setPressedImage(image: String)
    open fun setTitle(title: String)
    open fun setToolTip(toolTip: String)
}

external interface UploadBlob {
    var blobUUID: String
    var type: String
}

external interface UploadData {
    var blobUUID: String?
        get() = definedExternally
        set(value) = definedExternally
    var bytes: Buffer
    var file: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface UploadFile {
    var filePath: String
    var length: Number
    var modificationTime: Number
    var offset: Number
    var type: String
}

external interface UploadRawData {
    var bytes: Buffer
    var type: String
}

external open class WebContents : EventEmitter {
    open fun on(
        event: String /* 'before-input-event' */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'before-input-event' */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'before-input-event' */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'before-input-event' */,
        listener: (event: Event, input: Input) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'certificate-error' */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'certificate-error' */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'certificate-error' */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'certificate-error' */,
        listener: (event: Event, url: String, error: String, certificate: Certificate, callback: (isTrusted: Boolean) -> Unit) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'console-message' */,
        listener: (event: Event, level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'console-message' */,
        listener: (event: Event, level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'console-message' */,
        listener: (event: Event, level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'console-message' */,
        listener: (event: Event, level: Number, message: String, line: Number, sourceId: String) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'context-menu' */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'context-menu' */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'context-menu' */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'context-menu' */,
        listener: (event: Event, params: ContextMenuParams) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'crashed' */,
        listener: (event: Event, killed: Boolean) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'cursor-changed' */,
        listener: (event: Event, type: String, image: NativeImage, scale: Number, size: Size, hotspot: Point) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'cursor-changed' */,
        listener: (event: Event, type: String, image: NativeImage, scale: Number, size: Size, hotspot: Point) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'cursor-changed' */,
        listener: (event: Event, type: String, image: NativeImage, scale: Number, size: Size, hotspot: Point) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'cursor-changed' */,
        listener: (event: Event, type: String, image: NativeImage, scale: Number, size: Size, hotspot: Point) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'desktop-capturer-get-sources' | 'dom-ready' | 'will-prevent-unload' */,
        listener: (event: Event) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'desktop-capturer-get-sources' | 'dom-ready' | 'will-prevent-unload' */,
        listener: (event: Event) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'desktop-capturer-get-sources' | 'dom-ready' | 'will-prevent-unload' */,
        listener: (event: Event) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'desktop-capturer-get-sources' | 'dom-ready' | 'will-prevent-unload' */,
        listener: (event: Event) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'destroyed' | 'devtools-closed' | 'devtools-focused' | 'devtools-opened' | 'devtools-reload-page' | 'did-finish-load' | 'did-start-loading' | 'did-stop-loading' | 'enter-html-full-screen' | 'leave-html-full-screen' | 'media-paused' | 'media-started-playing' | 'responsive' | 'unresponsive' */,
        listener: Function<*>
    ): WebContents /* this */

    open fun once(
        event: String /* 'destroyed' | 'devtools-closed' | 'devtools-focused' | 'devtools-opened' | 'devtools-reload-page' | 'did-finish-load' | 'did-start-loading' | 'did-stop-loading' | 'enter-html-full-screen' | 'leave-html-full-screen' | 'media-paused' | 'media-started-playing' | 'responsive' | 'unresponsive' */,
        listener: Function<*>
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'destroyed' | 'devtools-closed' | 'devtools-focused' | 'devtools-opened' | 'devtools-reload-page' | 'did-finish-load' | 'did-start-loading' | 'did-stop-loading' | 'enter-html-full-screen' | 'leave-html-full-screen' | 'media-paused' | 'media-started-playing' | 'responsive' | 'unresponsive' */,
        listener: Function<*>
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'destroyed' | 'devtools-closed' | 'devtools-focused' | 'devtools-opened' | 'devtools-reload-page' | 'did-finish-load' | 'did-start-loading' | 'did-stop-loading' | 'enter-html-full-screen' | 'leave-html-full-screen' | 'media-paused' | 'media-started-playing' | 'responsive' | 'unresponsive' */,
        listener: Function<*>
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-attach-webview' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-attach-webview' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-attach-webview' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-attach-webview' */,
        listener: (event: Event, webContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-change-theme-color' */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-change-theme-color' */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-change-theme-color' */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-change-theme-color' */,
        listener: (event: Event, color: String?) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-fail-load' | 'did-fail-provisional-load' */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-fail-load' | 'did-fail-provisional-load' */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-fail-load' | 'did-fail-provisional-load' */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-fail-load' | 'did-fail-provisional-load' */,
        listener: (event: Event, errorCode: Number, errorDescription: String, validatedURL: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-frame-finish-load' */,
        listener: (event: Event, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-frame-finish-load' */,
        listener: (event: Event, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-frame-finish-load' */,
        listener: (event: Event, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-frame-finish-load' */,
        listener: (event: Event, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-frame-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-frame-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-frame-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-frame-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-navigate' */,
        listener: (event: Event, url: String, httpResponseCode: Number, httpStatusText: String) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-navigate-in-page' */,
        listener: (event: Event, url: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-navigate-in-page' */,
        listener: (event: Event, url: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-navigate-in-page' */,
        listener: (event: Event, url: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-navigate-in-page' */,
        listener: (event: Event, url: String, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'did-redirect-navigation' | 'did-start-navigation' | 'will-redirect' */,
        listener: (event: Event, url: String, isInPlace: Boolean, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'did-redirect-navigation' | 'did-start-navigation' | 'will-redirect' */,
        listener: (event: Event, url: String, isInPlace: Boolean, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'did-redirect-navigation' | 'did-start-navigation' | 'will-redirect' */,
        listener: (event: Event, url: String, isInPlace: Boolean, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'did-redirect-navigation' | 'did-start-navigation' | 'will-redirect' */,
        listener: (event: Event, url: String, isInPlace: Boolean, isMainFrame: Boolean, frameProcessId: Number, frameRoutingId: Number) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'found-in-page' */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'found-in-page' */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'found-in-page' */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'found-in-page' */,
        listener: (event: Event, result: Result) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'ipc-message' | 'ipc-message-sync' */,
        listener: (event: Event, channel: String, args: Any) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'ipc-message' | 'ipc-message-sync' */,
        listener: (event: Event, channel: String, args: Any) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'ipc-message' | 'ipc-message-sync' */,
        listener: (event: Event, channel: String, args: Any) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'ipc-message' | 'ipc-message-sync' */,
        listener: (event: Event, channel: String, args: Any) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'login' */,
        listener: (event: Event, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'login' */,
        listener: (event: Event, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'login' */,
        listener: (event: Event, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'login' */,
        listener: (event: Event, authenticationResponseDetails: AuthenticationResponseDetails, authInfo: AuthInfo, callback: (username: String, password: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'new-window' */,
        listener: (event: NewWindowEvent, url: String, frameName: String, disposition: String /* 'default' | 'foreground-tab' | 'background-tab' | 'new-window' | 'save-to-disk' | 'other' */, options: BrowserWindowConstructorOptions, additionalFeatures: Array<String>, referrer: Referrer) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'new-window' */,
        listener: (event: NewWindowEvent, url: String, frameName: String, disposition: String /* 'default' | 'foreground-tab' | 'background-tab' | 'new-window' | 'save-to-disk' | 'other' */, options: BrowserWindowConstructorOptions, additionalFeatures: Array<String>, referrer: Referrer) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'new-window' */,
        listener: (event: NewWindowEvent, url: String, frameName: String, disposition: String /* 'default' | 'foreground-tab' | 'background-tab' | 'new-window' | 'save-to-disk' | 'other' */, options: BrowserWindowConstructorOptions, additionalFeatures: Array<String>, referrer: Referrer) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'new-window' */,
        listener: (event: NewWindowEvent, url: String, frameName: String, disposition: String /* 'default' | 'foreground-tab' | 'background-tab' | 'new-window' | 'save-to-disk' | 'other' */, options: BrowserWindowConstructorOptions, additionalFeatures: Array<String>, referrer: Referrer) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'page-favicon-updated' */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'page-favicon-updated' */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'page-favicon-updated' */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'page-favicon-updated' */,
        listener: (event: Event, favicons: Array<String>) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'page-title-updated' */,
        listener: (event: Event, title: String, explicitSet: Boolean) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'paint' */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'paint' */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'paint' */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'paint' */,
        listener: (event: Event, dirtyRect: Rectangle, image: NativeImage) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'plugin-crashed' */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'plugin-crashed' */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'plugin-crashed' */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'plugin-crashed' */,
        listener: (event: Event, name: String, version: String) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'preload-error' */,
        listener: (event: Event, preloadPath: String, error: Error) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'preload-error' */,
        listener: (event: Event, preloadPath: String, error: Error) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'preload-error' */,
        listener: (event: Event, preloadPath: String, error: Error) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'preload-error' */,
        listener: (event: Event, preloadPath: String, error: Error) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: IpcMainEvent, moduleName: String) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: IpcMainEvent, moduleName: String) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: IpcMainEvent, moduleName: String) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'remote-get-builtin' | 'remote-require' */,
        listener: (event: IpcMainEvent, moduleName: String) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'remote-get-current-web-contents' | 'remote-get-current-window' */,
        listener: (event: IpcMainEvent) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'remote-get-current-web-contents' | 'remote-get-current-window' */,
        listener: (event: IpcMainEvent) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'remote-get-current-web-contents' | 'remote-get-current-window' */,
        listener: (event: IpcMainEvent) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'remote-get-current-web-contents' | 'remote-get-current-window' */,
        listener: (event: IpcMainEvent) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'remote-get-global' */,
        listener: (event: IpcMainEvent, globalName: String) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'remote-get-global' */,
        listener: (event: IpcMainEvent, globalName: String) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'remote-get-global' */,
        listener: (event: IpcMainEvent, globalName: String) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'remote-get-global' */,
        listener: (event: IpcMainEvent, globalName: String) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: IpcMainEvent, guestWebContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: IpcMainEvent, guestWebContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: IpcMainEvent, guestWebContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'remote-get-guest-web-contents' */,
        listener: (event: IpcMainEvent, guestWebContents: WebContents) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, details: Details) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, details: Details) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, details: Details) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'render-process-gone' */,
        listener: (event: Event, details: Details) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'select-bluetooth-device' */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'select-bluetooth-device' */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'select-bluetooth-device' */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'select-bluetooth-device' */,
        listener: (event: Event, devices: Array<BluetoothDevice>, callback: (deviceId: String) -> Unit) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'select-client-certificate' */,
        listener: (event: Event, url: String, certificateList: Array<Certificate>, callback: (certificate: Certificate) -> Unit) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'update-target-url' | 'will-navigate' */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'update-target-url' | 'will-navigate' */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'update-target-url' | 'will-navigate' */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'update-target-url' | 'will-navigate' */,
        listener: (event: Event, url: String) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'will-attach-webview' */,
        listener: (event: Event, webPreferences: WebPreferences, params: Record<String, String>) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'will-attach-webview' */,
        listener: (event: Event, webPreferences: WebPreferences, params: Record<String, String>) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'will-attach-webview' */,
        listener: (event: Event, webPreferences: WebPreferences, params: Record<String, String>) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'will-attach-webview' */,
        listener: (event: Event, webPreferences: WebPreferences, params: Record<String, String>) -> Unit
    ): WebContents /* this */

    open fun on(
        event: String /* 'zoom-changed' */,
        listener: (event: Event, zoomDirection: String /* 'in' | 'out' */) -> Unit
    ): WebContents /* this */

    open fun once(
        event: String /* 'zoom-changed' */,
        listener: (event: Event, zoomDirection: String /* 'in' | 'out' */) -> Unit
    ): WebContents /* this */

    open fun addListener(
        event: String /* 'zoom-changed' */,
        listener: (event: Event, zoomDirection: String /* 'in' | 'out' */) -> Unit
    ): WebContents /* this */

    open fun removeListener(
        event: String /* 'zoom-changed' */,
        listener: (event: Event, zoomDirection: String /* 'in' | 'out' */) -> Unit
    ): WebContents /* this */

    open fun addWorkSpace(path: String)
    open fun beginFrameSubscription(onlyDirty: Boolean, callback: (image: NativeImage, dirtyRect: Rectangle) -> Unit)
    open fun beginFrameSubscription(callback: (image: NativeImage, dirtyRect: Rectangle) -> Unit)
    open fun canGoBack(): Boolean
    open fun canGoForward(): Boolean
    open fun canGoToOffset(offset: Number): Boolean
    open fun capturePage(rect: Rectangle = definedExternally): Promise<NativeImage>
    open fun clearHistory()
    open fun closeDevTools()
    open fun copy()
    open fun copyImageAt(x: Number, y: Number)
    open fun cut()
    open fun decrementCapturerCount(stayHidden: Boolean = definedExternally)
    open fun delete()
    open fun disableDeviceEmulation()
    open fun downloadURL(url: String)
    open fun enableDeviceEmulation(parameters: Parameters)
    open fun endFrameSubscription()
    open fun executeJavaScript(code: String, userGesture: Boolean = definedExternally): Promise<Any>
    open fun executeJavaScriptInIsolatedWorld(
        worldId: Number,
        scripts: Array<WebSource>,
        userGesture: Boolean = definedExternally
    ): Promise<Any>

    open fun findInPage(text: String, options: FindInPageOptions = definedExternally): Number
    open fun focus()
    open fun getAllSharedWorkers(): Array<SharedWorkerInfo>
    open fun getFrameRate(): Number
    open fun getOSProcessId(): Number
    open fun getPrinters(): Array<PrinterInfo>
    open fun getProcessId(): Number
    open fun getTitle(): String
    open fun getType(): String /* 'backgroundPage' | 'window' | 'browserView' | 'remote' | 'webview' | 'offscreen' */
    open fun getURL(): String
    open fun getUserAgent(): String
    open fun getWebRTCIPHandlingPolicy(): String
    open fun getZoomFactor(): Number
    open fun getZoomLevel(): Number
    open fun goBack()
    open fun goForward()
    open fun goToIndex(index: Number)
    open fun goToOffset(offset: Number)
    open fun incrementCapturerCount(size: Size = definedExternally, stayHidden: Boolean = definedExternally)
    open fun insertCSS(css: String, options: InsertCSSOptions = definedExternally): Promise<String>
    open fun insertText(text: String): Promise<Unit>
    open fun inspectElement(x: Number, y: Number)
    open fun inspectServiceWorker()
    open fun inspectSharedWorker()
    open fun inspectSharedWorkerById(workerId: String)
    open fun invalidate()
    open fun isAudioMuted(): Boolean
    open fun isBeingCaptured(): Boolean
    open fun isCrashed(): Boolean
    open fun isCurrentlyAudible(): Boolean
    open fun isDestroyed(): Boolean
    open fun isDevToolsFocused(): Boolean
    open fun isDevToolsOpened(): Boolean
    open fun isFocused(): Boolean
    open fun isLoading(): Boolean
    open fun isLoadingMainFrame(): Boolean
    open fun isOffscreen(): Boolean
    open fun isPainting(): Boolean
    open fun isWaitingForResponse(): Boolean
    open fun loadFile(filePath: String, options: LoadFileOptions = definedExternally): Promise<Unit>
    open fun loadURL(url: String, options: LoadURLOptions = definedExternally): Promise<Unit>
    open fun openDevTools(options: OpenDevToolsOptions = definedExternally)
    open fun paste()
    open fun pasteAndMatchStyle()
    open fun print(
        options: WebContentsPrintOptions = definedExternally,
        callback: (success: Boolean, failureReason: String) -> Unit = definedExternally
    )

    open fun printToPDF(options: PrintToPDFOptions): Promise<Buffer>
    open fun redo()
    open fun reload()
    open fun reloadIgnoringCache()
    open fun removeInsertedCSS(key: String): Promise<Unit>
    open fun removeWorkSpace(path: String)
    open fun replace(text: String)
    open fun replaceMisspelling(text: String)
    open fun savePage(fullPath: String, saveType: String /* 'HTMLOnly' | 'HTMLComplete' | 'MHTML' */): Promise<Unit>
    open fun selectAll()
    open fun send(channel: String, vararg args: Any)
    open fun sendInputEvent(inputEvent: MouseInputEvent)
    open fun sendInputEvent(inputEvent: MouseWheelInputEvent)
    open fun sendInputEvent(inputEvent: KeyboardInputEvent)
    open fun sendToFrame(frameId: Number, channel: String, vararg args: Any)
    open fun setAudioMuted(muted: Boolean)
    open fun setBackgroundThrottling(allowed: Boolean)
    open fun setDevToolsWebContents(devToolsWebContents: WebContents)
    open fun setFrameRate(fps: Number)
    open fun setIgnoreMenuShortcuts(ignore: Boolean)
    open fun setLayoutZoomLevelLimits(minimumLevel: Number, maximumLevel: Number): Promise<Unit>
    open fun setUserAgent(userAgent: String)
    open fun setVisualZoomLevelLimits(minimumLevel: Number, maximumLevel: Number): Promise<Unit>
    open fun setWebRTCIPHandlingPolicy(policy: String /* 'default' | 'default_public_interface_only' | 'default_public_and_private_interfaces' | 'disable_non_proxied_udp' */)
    open fun setZoomFactor(factor: Number)
    open fun setZoomLevel(level: Number)
    open fun showDefinitionForSelection()
    open fun startDrag(item: Item)
    open fun startPainting()
    open fun stop()
    open fun stopFindInPage(action: String /* 'clearSelection' | 'keepSelection' | 'activateSelection' */)
    open fun stopPainting()
    open fun takeHeapSnapshot(filePath: String): Promise<Unit>
    open fun toggleDevTools()
    open fun undo()
    open fun unselect()
    open var audioMuted: Boolean
    open var debugger: Debugger
    open var devToolsWebContents: WebContents?
    open var frameRate: Number
    open var hostWebContents: WebContents
    open var id: Number
    open var session: Session
    open var userAgent: String
    open var zoomFactor: Number
    open var zoomLevel: Number

    companion object {
        fun fromId(id: Number): WebContents
        fun getAllWebContents(): Array<WebContents>
        fun getFocusedWebContents(): WebContents
    }
}

external interface WebFrame : EventEmitter {
    fun clearCache()
    fun executeJavaScript(code: String, userGesture: Boolean = definedExternally): Promise<Any>
    fun executeJavaScriptInIsolatedWorld(
        worldId: Number,
        scripts: Array<WebSource>,
        userGesture: Boolean = definedExternally
    ): Promise<Any>

    fun findFrameByName(name: String): WebFrame
    fun findFrameByRoutingId(routingId: Number): WebFrame
    fun getFrameForSelector(selector: String): WebFrame
    fun getResourceUsage(): ResourceUsage
    fun getZoomFactor(): Number
    fun getZoomLevel(): Number
    fun insertCSS(css: String): String
    fun insertText(text: String)
    fun removeInsertedCSS(key: String)
    fun setIsolatedWorldInfo(worldId: Number, info: Info)
    fun setLayoutZoomLevelLimits(minimumLevel: Number, maximumLevel: Number)
    fun setSpellCheckProvider(language: String, provider: Provider)
    fun setVisualZoomLevelLimits(minimumLevel: Number, maximumLevel: Number)
    fun setZoomFactor(factor: Number)
    fun setZoomLevel(level: Number)
    var firstChild: WebFrame?
    var nextSibling: WebFrame?
    var opener: WebFrame?
    var parent: WebFrame?
    var routingId: Number
    var top: WebFrame?
}

external open class WebRequest {
    open fun onBeforeRedirect(filter: Filter, listener: ((details: OnBeforeRedirectListenerDetails) -> Unit)?)
    open fun onBeforeRedirect(listener: ((details: OnBeforeRedirectListenerDetails) -> Unit)?)
    open fun onBeforeRequest(
        filter: Filter,
        listener: ((details: OnBeforeRequestListenerDetails, callback: (response: Response) -> Unit) -> Unit)?
    )

    open fun onBeforeRequest(listener: ((details: OnBeforeRequestListenerDetails, callback: (response: Response) -> Unit) -> Unit)?)
    open fun onBeforeSendHeaders(
        filter: Filter,
        listener: ((details: OnBeforeSendHeadersListenerDetails, callback: (beforeSendResponse: BeforeSendResponse) -> Unit) -> Unit)?
    )

    open fun onBeforeSendHeaders(listener: ((details: OnBeforeSendHeadersListenerDetails, callback: (beforeSendResponse: BeforeSendResponse) -> Unit) -> Unit)?)
    open fun onCompleted(filter: Filter, listener: ((details: OnCompletedListenerDetails) -> Unit)?)
    open fun onCompleted(listener: ((details: OnCompletedListenerDetails) -> Unit)?)
    open fun onErrorOccurred(filter: Filter, listener: ((details: OnErrorOccurredListenerDetails) -> Unit)?)
    open fun onErrorOccurred(listener: ((details: OnErrorOccurredListenerDetails) -> Unit)?)
    open fun onHeadersReceived(
        filter: Filter,
        listener: ((details: OnHeadersReceivedListenerDetails, callback: (headersReceivedResponse: HeadersReceivedResponse) -> Unit) -> Unit)?
    )

    open fun onHeadersReceived(listener: ((details: OnHeadersReceivedListenerDetails, callback: (headersReceivedResponse: HeadersReceivedResponse) -> Unit) -> Unit)?)
    open fun onResponseStarted(filter: Filter, listener: ((details: OnResponseStartedListenerDetails) -> Unit)?)
    open fun onResponseStarted(listener: ((details: OnResponseStartedListenerDetails) -> Unit)?)
    open fun onSendHeaders(filter: Filter, listener: ((details: OnSendHeadersListenerDetails) -> Unit)?)
    open fun onSendHeaders(listener: ((details: OnSendHeadersListenerDetails) -> Unit)?)
}

external interface WebSource {
    var code: String
    var startLine: Number?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface WebviewTag : HTMLElement {
    fun addEventListener(
        event: String /* 'load-commit' */,
        listener: (event: LoadCommitEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'load-commit' */,
        listener: (event: LoadCommitEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'did-finish-load' | 'did-start-loading' | 'did-stop-loading' | 'dom-ready' | 'enter-html-full-screen' | 'leave-html-full-screen' | 'close' | 'crashed' | 'destroyed' | 'media-started-playing' | 'media-paused' | 'devtools-opened' | 'devtools-closed' | 'devtools-focused' */,
        listener: (event: Event) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'did-finish-load' | 'did-start-loading' | 'did-stop-loading' | 'dom-ready' | 'enter-html-full-screen' | 'leave-html-full-screen' | 'close' | 'crashed' | 'destroyed' | 'media-started-playing' | 'media-paused' | 'devtools-opened' | 'devtools-closed' | 'devtools-focused' */,
        listener: (event: Event) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'did-fail-load' */,
        listener: (event: DidFailLoadEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'did-fail-load' */,
        listener: (event: DidFailLoadEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'did-frame-finish-load' */,
        listener: (event: DidFrameFinishLoadEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'did-frame-finish-load' */,
        listener: (event: DidFrameFinishLoadEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'page-title-updated' */,
        listener: (event: PageTitleUpdatedEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'page-title-updated' */,
        listener: (event: PageTitleUpdatedEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'page-favicon-updated' */,
        listener: (event: PageFaviconUpdatedEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'page-favicon-updated' */,
        listener: (event: PageFaviconUpdatedEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'console-message' */,
        listener: (event: ConsoleMessageEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'console-message' */,
        listener: (event: ConsoleMessageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'found-in-page' */,
        listener: (event: FoundInPageEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'found-in-page' */,
        listener: (event: FoundInPageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'new-window' */,
        listener: (event: NewWindowEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'new-window' */,
        listener: (event: NewWindowEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'will-navigate' */,
        listener: (event: WillNavigateEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'will-navigate' */,
        listener: (event: WillNavigateEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'did-navigate' */,
        listener: (event: DidNavigateEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'did-navigate' */,
        listener: (event: DidNavigateEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'did-navigate-in-page' */,
        listener: (event: DidNavigateInPageEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'did-navigate-in-page' */,
        listener: (event: DidNavigateInPageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'ipc-message' */,
        listener: (event: IpcMessageEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'ipc-message' */,
        listener: (event: IpcMessageEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'plugin-crashed' */,
        listener: (event: PluginCrashedEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'plugin-crashed' */,
        listener: (event: PluginCrashedEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'did-change-theme-color' */,
        listener: (event: DidChangeThemeColorEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'did-change-theme-color' */,
        listener: (event: DidChangeThemeColorEvent) -> Unit
    ): WebviewTag /* this */

    fun addEventListener(
        event: String /* 'update-target-url' */,
        listener: (event: UpdateTargetUrlEvent) -> Unit,
        useCapture: Boolean = definedExternally
    ): WebviewTag /* this */

    fun removeEventListener(
        event: String /* 'update-target-url' */,
        listener: (event: UpdateTargetUrlEvent) -> Unit
    ): WebviewTag /* this */

    fun <K : Any> addEventListener(type: K, listener: (self: HTMLElement, ev: Any) -> Any, useCapture: Boolean)
    fun addEventListener(type: String, listener: EventListener, useCapture: Boolean)
    fun <K : Any> removeEventListener(type: K, listener: (self: HTMLElement, ev: Any) -> Any, useCapture: Boolean)
    fun removeEventListener(type: String, listener: EventListener, useCapture: Boolean)
    fun canGoBack(): Boolean
    fun canGoForward(): Boolean
    fun canGoToOffset(offset: Number): Boolean
    fun capturePage(rect: Rectangle = definedExternally): Promise<NativeImage>
    fun clearHistory()
    fun closeDevTools()
    fun copy()
    fun cut()
    fun delete()
    fun downloadURL(url: String)
    fun executeJavaScript(code: String, userGesture: Boolean = definedExternally): Promise<Any>
    fun findInPage(text: String, options: FindInPageOptions = definedExternally): Number
    fun getTitle(): String
    fun getURL(): String
    fun getUserAgent(): String
    fun getWebContents(): WebContents
    fun getWebContentsId(): Number
    fun getZoomFactor(): Number
    fun getZoomLevel(): Number
    fun goBack()
    fun goForward()
    fun goToIndex(index: Number)
    fun goToOffset(offset: Number)
    fun insertCSS(css: String): Promise<String>
    fun insertText(text: String): Promise<Unit>
    fun inspectElement(x: Number, y: Number)
    fun inspectServiceWorker()
    fun inspectSharedWorker()
    fun isAudioMuted(): Boolean
    fun isCrashed(): Boolean
    fun isCurrentlyAudible(): Boolean
    fun isDevToolsFocused(): Boolean
    fun isDevToolsOpened(): Boolean
    fun isLoading(): Boolean
    fun isLoadingMainFrame(): Boolean
    fun isWaitingForResponse(): Boolean
    fun loadURL(url: String, options: LoadURLOptions = definedExternally): Promise<Unit>
    fun openDevTools()
    fun paste()
    fun pasteAndMatchStyle()
    fun print(options: WebviewTagPrintOptions = definedExternally): Promise<Unit>
    fun printToPDF(options: PrintToPDFOptions): Promise<Uint8Array>
    fun redo()
    fun reload()
    fun reloadIgnoringCache()
    fun removeInsertedCSS(key: String): Promise<Unit>
    fun replace(text: String)
    fun replaceMisspelling(text: String)
    fun selectAll()
    fun send(channel: String, vararg args: Any): Promise<Unit>
    fun sendInputEvent(event: MouseInputEvent): Promise<Unit>
    fun sendInputEvent(event: MouseWheelInputEvent): Promise<Unit>
    fun sendInputEvent(event: KeyboardInputEvent): Promise<Unit>
    fun setAudioMuted(muted: Boolean)
    fun setLayoutZoomLevelLimits(minimumLevel: Number, maximumLevel: Number): Promise<Unit>
    fun setUserAgent(userAgent: String)
    fun setVisualZoomLevelLimits(minimumLevel: Number, maximumLevel: Number): Promise<Unit>
    fun setZoomFactor(factor: Number)
    fun setZoomLevel(level: Number)
    fun showDefinitionForSelection()
    fun stop()
    fun stopFindInPage(action: String /* 'clearSelection' | 'keepSelection' | 'activateSelection' */)
    fun undo()
    fun unselect()
    var allowpopups: Boolean
    var disableblinkfeatures: String
    var disablewebsecurity: Boolean
    var enableblinkfeatures: String
    var enableremotemodule: Boolean
    var httpreferrer: String
    var nodeintegration: Boolean
    var nodeintegrationinsubframes: Boolean
    var partition: String
    var plugins: Boolean
    var preload: String
    var src: String
    var useragent: String
    var webpreferences: String
}

external interface AboutPanelOptionsOptions {
    var applicationName: String?
        get() = definedExternally
        set(value) = definedExternally
    var applicationVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var copyright: String?
        get() = definedExternally
        set(value) = definedExternally
    var version: String?
        get() = definedExternally
        set(value) = definedExternally
    var credits: String?
        get() = definedExternally
        set(value) = definedExternally
    var authors: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var website: String?
        get() = definedExternally
        set(value) = definedExternally
    var iconPath: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AddRepresentationOptions {
    var scaleFactor: Number
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var buffer: Buffer?
        get() = definedExternally
        set(value) = definedExternally
    var dataURL: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AnimationSettings {
    var shouldRenderRichAnimation: Boolean
    var scrollAnimationsEnabledBySystem: Boolean
    var prefersReducedMotion: Boolean
}

external interface AppDetailsOptions {
    var appId: String?
        get() = definedExternally
        set(value) = definedExternally
    var appIconPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var appIconIndex: Number?
        get() = definedExternally
        set(value) = definedExternally
    var relaunchCommand: String?
        get() = definedExternally
        set(value) = definedExternally
    var relaunchDisplayName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AuthenticationResponseDetails {
    var url: String
}

external interface AuthInfo {
    var isProxy: Boolean
    var scheme: String
    var host: String
    var port: Number
    var realm: String
}

external interface AutoResizeOptions {
    var width: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var height: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var horizontal: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var vertical: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BeforeSendResponse {
    var cancel: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var requestHeaders: Record<String, dynamic /* String | Array<String> */>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BitmapOptions {
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BlinkMemoryInfo {
    var allocated: Number
    var marked: Number
    var total: Number
}

external interface BrowserViewConstructorOptions {
    var webPreferences: WebPreferences?
        get() = definedExternally
        set(value) = definedExternally
}

external interface BrowserWindowConstructorOptions {
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var x: Number?
        get() = definedExternally
        set(value) = definedExternally
    var y: Number?
        get() = definedExternally
        set(value) = definedExternally
    var useContentSize: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var center: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var minWidth: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minHeight: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxWidth: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxHeight: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resizable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var movable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var minimizable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maximizable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var closable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var focusable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var alwaysOnTop: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var fullscreen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var fullscreenable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var simpleFullscreen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var skipTaskbar: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var kiosk: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var icon: dynamic /* NativeImage? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var show: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var paintWhenInitiallyHidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var frame: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var parent: BrowserWindow?
        get() = definedExternally
        set(value) = definedExternally
    var modal: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var acceptFirstMouse: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var disableAutoHideCursor: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var autoHideMenuBar: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var enableLargerThanScreen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var backgroundColor: String?
        get() = definedExternally
        set(value) = definedExternally
    var hasShadow: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var opacity: Number?
        get() = definedExternally
        set(value) = definedExternally
    var darkTheme: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var transparent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var titleBarStyle: String? /* 'default' | 'hidden' | 'hiddenInset' | 'customButtonsOnHover' */
        get() = definedExternally
        set(value) = definedExternally
    var trafficLightPosition: Point?
        get() = definedExternally
        set(value) = definedExternally
    var fullscreenWindowTitle: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var thickFrame: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var vibrancy: String? /* 'appearance-based' | 'light' | 'dark' | 'titlebar' | 'selection' | 'menu' | 'popover' | 'sidebar' | 'medium-light' | 'ultra-dark' | 'header' | 'sheet' | 'window' | 'hud' | 'fullscreen-ui' | 'tooltip' | 'content' | 'under-window' | 'under-page' */
        get() = definedExternally
        set(value) = definedExternally
    var zoomToPageWidth: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var tabbingIdentifier: String?
        get() = definedExternally
        set(value) = definedExternally
    var webPreferences: WebPreferences?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CertificateTrustDialogOptions {
    var certificate: Certificate
    var message: String
}

external interface CertificateVerifyProcProcRequest {
    var hostname: String
    var certificate: Certificate
    var verificationResult: String
    var errorCode: Number
}

external interface ClearStorageDataOptions {
    var origin: String?
        get() = definedExternally
        set(value) = definedExternally
    var storages: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var quotas: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ClientRequestConstructorOptions {
    var method: String?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var session: Session?
        get() = definedExternally
        set(value) = definedExternally
    var partition: String?
        get() = definedExternally
        set(value) = definedExternally
    var useSessionCookies: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var protocol: String?
        get() = definedExternally
        set(value) = definedExternally
    var host: String?
        get() = definedExternally
        set(value) = definedExternally
    var hostname: String?
        get() = definedExternally
        set(value) = definedExternally
    var port: Number?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var redirect: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Config {
    var pacScript: String?
        get() = definedExternally
        set(value) = definedExternally
    var proxyRules: String?
        get() = definedExternally
        set(value) = definedExternally
    var proxyBypassRules: String?
        get() = definedExternally
        set(value) = definedExternally
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
    var mediaType: String /* 'none' | 'image' | 'audio' | 'video' | 'canvas' | 'file' | 'plugin' */
    var hasImageContents: Boolean
    var isEditable: Boolean
    var selectionText: String
    var titleText: String
    var misspelledWord: String
    var dictionarySuggestions: Array<String>
    var frameCharset: String
    var inputFieldType: String
    var menuSourceType: String /* 'none' | 'mouse' | 'keyboard' | 'touch' | 'touchMenu' */
    var mediaFlags: MediaFlags
    var editFlags: EditFlags
}

external interface CookiesGetFilter {
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var secure: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var session: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CookiesSetDetails {
    var url: String
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var secure: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var httpOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var expirationDate: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CrashReporterStartOptions {
    var companyName: String
    var submitURL: String
    var productName: String?
        get() = definedExternally
        set(value) = definedExternally
    var uploadToServer: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ignoreSystemCrashHandler: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var extra: Record<String, String>?
        get() = definedExternally
        set(value) = definedExternally
    var crashesDirectory: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CreateFromBitmapOptions {
    var width: Number
    var height: Number
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CreateFromBufferOptions {
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CreateInterruptedDownloadOptions {
    var path: String
    var urlChain: Array<String>
    var mimeType: String?
        get() = definedExternally
        set(value) = definedExternally
    var offset: Number
    var length: Number
    var lastModified: String?
        get() = definedExternally
        set(value) = definedExternally
    var eTag: String?
        get() = definedExternally
        set(value) = definedExternally
    var startTime: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Data {
    var text: String?
        get() = definedExternally
        set(value) = definedExternally
    var html: String?
        get() = definedExternally
        set(value) = definedExternally
    var image: NativeImage?
        get() = definedExternally
        set(value) = definedExternally
    var rtf: String?
        get() = definedExternally
        set(value) = definedExternally
    var bookmark: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Details {
    var reason: String /* 'clean-exit' | 'abnormal-exit' | 'killed' | 'crashed' | 'oom' | 'launch-failure' | 'integrity-failure' */
}

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

external interface DidNavigateEvent : Event {
    var url: String
}

external interface DidNavigateInPageEvent : Event {
    var isMainFrame: Boolean
    var url: String
}

external interface DisplayBalloonOptions {
    var icon: dynamic /* NativeImage? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var iconType: String? /* 'none' | 'info' | 'warning' | 'error' | 'custom' */
        get() = definedExternally
        set(value) = definedExternally
    var title: String
    var content: String
    var largeIcon: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var noSound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var respectQuietTime: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface EnableNetworkEmulationOptions {
    var offline: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var latency: Number?
        get() = definedExternally
        set(value) = definedExternally
    var downloadThroughput: Number?
        get() = definedExternally
        set(value) = definedExternally
    var uploadThroughput: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface FeedURLOptions {
    var url: String
    var headers: Record<String, String>?
        get() = definedExternally
        set(value) = definedExternally
    var serverType: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface FileIconOptions {
    var size: String /* 'small' | 'normal' | 'large' */
}

external interface Filter {
    var urls: Array<String>
}

external interface FindInPageOptions {
    var forward: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var findNext: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var matchCase: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var wordStart: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var medialCapitalAsWordStart: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface FocusOptions {
    var steal: Boolean
}

external interface FoundInPageEvent : Event {
    var result: FoundInPageResult
}

external interface FromPartitionOptions {
    var cache: Boolean
}

external interface HeadersReceivedResponse {
    var cancel: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var responseHeaders: Record<String, dynamic /* String | Array<String> */>?
        get() = definedExternally
        set(value) = definedExternally
    var statusLine: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface HeapStatistics {
    var totalHeapSize: Number
    var totalHeapSizeExecutable: Number
    var totalPhysicalSize: Number
    var totalAvailableSize: Number
    var usedHeapSize: Number
    var heapSizeLimit: Number
    var mallocedMemory: Number
    var peakMallocedMemory: Number
    var doesZapGarbage: Boolean
}

external interface IgnoreMouseEventsOptions {
    var forward: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ImportCertificateOptions {
    var certificate: String
    var password: String
}

external interface Info {
    var securityOrigin: String?
        get() = definedExternally
        set(value) = definedExternally
    var csp: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Input {
    var type: String
    var key: String
    var code: String
    var isAutoRepeat: Boolean
    var isComposing: Boolean
    var shift: Boolean
    var control: Boolean
    var alt: Boolean
    var meta: Boolean
}

external interface InsertCSSOptions {
    var cssOrigin: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface IpcMessageEvent : Event {
    var channel: String
    var args: Array<Any>
}

external interface Item {
    var file: dynamic /* Array<String> | String */
        get() = definedExternally
        set(value) = definedExternally
    var icon: dynamic /* NativeImage | String */
        get() = definedExternally
        set(value) = definedExternally
}

external interface JumpListSettings {
    var minItems: Number
    var removedItems: Array<JumpListItem>
}

external interface LoadCommitEvent : Event {
    var url: String
    var isMainFrame: Boolean
}

external interface LoadFileOptions {
    var query: Record<String, String>?
        get() = definedExternally
        set(value) = definedExternally
    var search: String?
        get() = definedExternally
        set(value) = definedExternally
    var hash: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LoadURLOptions {
    var httpReferrer: dynamic /* String? | Referrer? */
        get() = definedExternally
        set(value) = definedExternally
    var userAgent: String?
        get() = definedExternally
        set(value) = definedExternally
    var extraHeaders: String?
        get() = definedExternally
        set(value) = definedExternally
    var postData: dynamic /* Array<UploadRawData>? | Array<UploadFile>? | Array<UploadBlob>? */
        get() = definedExternally
        set(value) = definedExternally
    var baseURLForDataURL: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LoginItemSettings {
    var openAtLogin: Boolean
    var openAsHidden: Boolean
    var wasOpenedAtLogin: Boolean
    var wasOpenedAsHidden: Boolean
    var restoreState: Boolean
}

external interface LoginItemSettingsOptions {
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var args: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MenuItemConstructorOptions {
    var click: ((menuItem: MenuItem, browserWindow: BrowserWindow?, event: KeyboardEvent) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var role: String? /* 'undo' | 'redo' | 'cut' | 'copy' | 'paste' | 'pasteAndMatchStyle' | 'delete' | 'selectAll' | 'reload' | 'forceReload' | 'toggleDevTools' | 'resetZoom' | 'zoomIn' | 'zoomOut' | 'togglefullscreen' | 'window' | 'minimize' | 'close' | 'help' | 'about' | 'services' | 'hide' | 'hideOthers' | 'unhide' | 'quit' | 'startSpeaking' | 'stopSpeaking' | 'zoom' | 'front' | 'appMenu' | 'fileMenu' | 'editMenu' | 'viewMenu' | 'recentDocuments' | 'toggleTabBar' | 'selectNextTab' | 'selectPreviousTab' | 'mergeAllWindows' | 'clearRecentDocuments' | 'moveTabToNewWindow' | 'windowMenu' */
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* 'normal' | 'separator' | 'submenu' | 'checkbox' | 'radio' */
        get() = definedExternally
        set(value) = definedExternally
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
    var sublabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var toolTip: String?
        get() = definedExternally
        set(value) = definedExternally
    var accelerator: dynamic
        get() = definedExternally
        set(value) = definedExternally
    var icon: dynamic /* NativeImage? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var enabled: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var acceleratorWorksWhenHidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var visible: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var checked: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var registerAccelerator: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var submenu: dynamic /* Array<MenuItemConstructorOptions>? | Menu? */
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var before: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var after: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var beforeGroupContaining: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var afterGroupContaining: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MessageBoxOptions {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttons: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var defaultId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var message: String
    var detail: String?
        get() = definedExternally
        set(value) = definedExternally
    var checkboxLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var checkboxChecked: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var icon: NativeImage?
        get() = definedExternally
        set(value) = definedExternally
    var cancelId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var noLink: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var normalizeAccessKeys: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MessageBoxReturnValue {
    var response: Number
    var checkboxChecked: Boolean
}

external interface MessageBoxSyncOptions {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttons: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var defaultId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var message: String
    var detail: String?
        get() = definedExternally
        set(value) = definedExternally
    var checkboxLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var checkboxChecked: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var icon: dynamic /* NativeImage? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var cancelId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var noLink: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var normalizeAccessKeys: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface MoveToApplicationsFolderOptions {
    var conflictHandler: ((conflictType: String /* 'exists' | 'existsAndRunning' */) -> Boolean)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface NotificationConstructorOptions {
    var title: String
    var subtitle: String?
        get() = definedExternally
        set(value) = definedExternally
    var body: String
    var silent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var icon: dynamic /* String? | NativeImage? */
        get() = definedExternally
        set(value) = definedExternally
    var hasReply: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timeoutType: String? /* 'default' | 'never' */
        get() = definedExternally
        set(value) = definedExternally
    var replyPlaceholder: String?
        get() = definedExternally
        set(value) = definedExternally
    var sound: String?
        get() = definedExternally
        set(value) = definedExternally
    var urgency: String? /* 'normal' | 'critical' | 'low' */
        get() = definedExternally
        set(value) = definedExternally
    var actions: Array<NotificationAction>?
        get() = definedExternally
        set(value) = definedExternally
    var closeButtonText: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OnBeforeRedirectListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var redirectURL: String
    var statusCode: Number
    var statusLine: String
    var ip: String?
        get() = definedExternally
        set(value) = definedExternally
    var fromCache: Boolean
    var responseHeaders: Record<String, Array<String>>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OnBeforeRequestListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var uploadData: Array<UploadData>
}

external interface OnBeforeSendHeadersListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var requestHeaders: Record<String, String>
}

external interface OnCompletedListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var responseHeaders: Record<String, Array<String>>?
        get() = definedExternally
        set(value) = definedExternally
    var fromCache: Boolean
    var statusCode: Number
    var statusLine: String
    var error: String
}

external interface OnErrorOccurredListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var fromCache: Boolean
    var error: String
}

external interface OnHeadersReceivedListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var statusLine: String
    var statusCode: Number
    var requestHeaders: Record<String, String>
    var responseHeaders: Record<String, Array<String>>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OnResponseStartedListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var responseHeaders: Record<String, Array<String>>?
        get() = definedExternally
        set(value) = definedExternally
    var fromCache: Boolean
    var statusCode: Number
    var statusLine: String
}

external interface OnSendHeadersListenerDetails {
    var id: Number
    var url: String
    var method: String
    var webContentsId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String
    var referrer: String
    var timestamp: Number
    var requestHeaders: Record<String, String>
}

external interface OpenDevToolsOptions {
    var mode: String /* 'right' | 'bottom' | 'undocked' | 'detach' */
    var activate: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OpenDialogOptions {
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var filters: Array<FileFilter>?
        get() = definedExternally
        set(value) = definedExternally
    var properties: Array<String /* 'openFile' | 'openDirectory' | 'multiSelections' | 'showHiddenFiles' | 'createDirectory' | 'promptToCreate' | 'noResolveAliases' | 'treatPackageAsDirectory' | 'dontAddToRecent' */>?
        get() = definedExternally
        set(value) = definedExternally
    var message: String?
        get() = definedExternally
        set(value) = definedExternally
    var securityScopedBookmarks: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OpenDialogReturnValue {
    var canceled: Boolean
    var filePaths: Array<String>
    var bookmarks: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OpenDialogSyncOptions {
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var filters: Array<FileFilter>?
        get() = definedExternally
        set(value) = definedExternally
    var properties: Array<String /* 'openFile' | 'openDirectory' | 'multiSelections' | 'showHiddenFiles' | 'createDirectory' | 'promptToCreate' | 'noResolveAliases' | 'treatPackageAsDirectory' | 'dontAddToRecent' */>?
        get() = definedExternally
        set(value) = definedExternally
    var message: String?
        get() = definedExternally
        set(value) = definedExternally
    var securityScopedBookmarks: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OpenExternalOptions {
    var activate: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var workingDirectory: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PageFaviconUpdatedEvent : Event {
    var favicons: Array<String>
}

external interface PageTitleUpdatedEvent : Event {
    var title: String
    var explicitSet: Boolean
}

external interface Parameters {
    var screenPosition: String /* 'desktop' | 'mobile' */
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

external interface PermissionCheckHandlerHandlerDetails {
    var securityOrigin: String
    var mediaType: String /* 'video' | 'audio' | 'unknown' */
    var requestingUrl: String
    var isMainFrame: Boolean
}

external interface PermissionRequestHandlerHandlerDetails {
    var externalURL: String?
        get() = definedExternally
        set(value) = definedExternally
    var mediaTypes: Array<String /* 'video' | 'audio' */>?
        get() = definedExternally
        set(value) = definedExternally
    var requestingUrl: String
    var isMainFrame: Boolean
}

external interface PluginCrashedEvent : Event {
    var name: String
    var version: String
}

external interface PopupOptions {
    var window: BrowserWindow?
        get() = definedExternally
        set(value) = definedExternally
    var x: Number?
        get() = definedExternally
        set(value) = definedExternally
    var y: Number?
        get() = definedExternally
        set(value) = definedExternally
    var positioningItem: Number?
        get() = definedExternally
        set(value) = definedExternally
    var callback: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PreconnectOptions {
    var url: String
    var numSockets: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PrintToPDFOptions {
    var marginsType: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageSize: dynamic /* String? | Size? */
        get() = definedExternally
        set(value) = definedExternally
    var printBackground: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var printSelectionOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var landscape: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Privileges {
    var standard: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var secure: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var bypassCSP: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var allowServiceWorkers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var supportFetchAPI: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var corsEnabled: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ProgressBarOptions {
    var mode: String /* 'none' | 'normal' | 'indeterminate' | 'error' | 'paused' */
}

external interface Provider {
    var spellCheck: (words: Array<String>, callback: (misspeltWords: Array<String>) -> Unit) -> Unit
}

external interface ReadBookmark {
    var title: String
    var url: String
}

external interface RedirectRequest {
    var url: String
    var method: String?
        get() = definedExternally
        set(value) = definedExternally
    var session: Session?
        get() = definedExternally
        set(value) = definedExternally
    var uploadData: ProtocolResponseUploadData?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RelaunchOptions {
    var args: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var execPath: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Request {
    var url: String
    var headers: Record<String, String>
    var referrer: String
    var method: String
    var uploadData: Array<UploadData>
}

external interface ResizeOptions {
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var quality: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ResourceUsage {
    var images: MemoryUsageDetails
    var scripts: MemoryUsageDetails
    var cssStyleSheets: MemoryUsageDetails
    var xslStyleSheets: MemoryUsageDetails
    var fonts: MemoryUsageDetails
    var other: MemoryUsageDetails
}

external interface Response {
    var cancel: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var redirectURL: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Result {
    var requestId: Number
    var activeMatchOrdinal: Number
    var matches: Number
    var selectionArea: Rectangle
    var finalUpdate: Boolean
}

external interface SaveDialogOptions {
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var filters: Array<FileFilter>?
        get() = definedExternally
        set(value) = definedExternally
    var message: String?
        get() = definedExternally
        set(value) = definedExternally
    var nameFieldLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var showsTagField: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var properties: Array<String /* 'showHiddenFiles' | 'createDirectory' | 'treatPackageAsDirectory' | 'showOverwriteConfirmation' | 'dontAddToRecent' */>?
        get() = definedExternally
        set(value) = definedExternally
    var securityScopedBookmarks: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SaveDialogReturnValue {
    var canceled: Boolean
    var filePath: String?
        get() = definedExternally
        set(value) = definedExternally
    var bookmark: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SaveDialogSyncOptions {
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var buttonLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var filters: Array<FileFilter>?
        get() = definedExternally
        set(value) = definedExternally
    var message: String?
        get() = definedExternally
        set(value) = definedExternally
    var nameFieldLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var showsTagField: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var properties: Array<String /* 'showHiddenFiles' | 'createDirectory' | 'treatPackageAsDirectory' | 'showOverwriteConfirmation' | 'dontAddToRecent' */>?
        get() = definedExternally
        set(value) = definedExternally
    var securityScopedBookmarks: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Settings {
    var openAtLogin: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var openAsHidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var path: String?
        get() = definedExternally
        set(value) = definedExternally
    var args: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SourcesOptions {
    var types: Array<String>
    var thumbnailSize: Size?
        get() = definedExternally
        set(value) = definedExternally
    var fetchWindowIcons: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface StartLoggingOptions {
    var captureMode: String? /* 'default' | 'includeSensitive' | 'everything' */
        get() = definedExternally
        set(value) = definedExternally
    var maxFileSize: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SystemMemoryInfo {
    var total: Number
    var free: Number
    var swapTotal: Number
    var swapFree: Number
}

external interface ToBitmapOptions {
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ToDataURLOptions {
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ToPNGOptions {
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarButtonConstructorOptions {
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
    var accessibilityLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var backgroundColor: String?
        get() = definedExternally
        set(value) = definedExternally
    var icon: dynamic /* NativeImage? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var iconPosition: String? /* 'left' | 'right' | 'overlay' */
        get() = definedExternally
        set(value) = definedExternally
    var click: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarColorPickerConstructorOptions {
    var availableColors: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var selectedColor: String?
        get() = definedExternally
        set(value) = definedExternally
    var change: ((color: String) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarConstructorOptions {
    var items: Array<dynamic /* TouchBarButton | TouchBarColorPicker | TouchBarGroup | TouchBarLabel | TouchBarPopover | TouchBarScrubber | TouchBarSegmentedControl | TouchBarSlider | TouchBarSpacer */>?
        get() = definedExternally
        set(value) = definedExternally
    var escapeItem: dynamic /* TouchBarButton? | TouchBarColorPicker? | TouchBarGroup? | TouchBarLabel? | TouchBarPopover? | TouchBarScrubber? | TouchBarSegmentedControl? | TouchBarSlider? | TouchBarSpacer? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarGroupConstructorOptions {
    var items: TouchBar
}

external interface TouchBarLabelConstructorOptions {
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
    var accessibilityLabel: String?
        get() = definedExternally
        set(value) = definedExternally
    var textColor: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarPopoverConstructorOptions {
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
    var icon: NativeImage?
        get() = definedExternally
        set(value) = definedExternally
    var items: TouchBar
    var showCloseButton: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarScrubberConstructorOptions {
    var items: Array<ScrubberItem>
    var select: ((selectedIndex: Number) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var highlight: ((highlightedIndex: Number) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var selectedStyle: String? /* 'background' | 'outline' | 'none' */
        get() = definedExternally
        set(value) = definedExternally
    var overlayStyle: String? /* 'background' | 'outline' | 'none' */
        get() = definedExternally
        set(value) = definedExternally
    var showArrowButtons: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var mode: String? /* 'fixed' | 'free' */
        get() = definedExternally
        set(value) = definedExternally
    var continuous: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarSegmentedControlConstructorOptions {
    var segmentStyle: String? /* 'automatic' | 'rounded' | 'textured-rounded' | 'round-rect' | 'textured-square' | 'capsule' | 'small-square' | 'separated' */
        get() = definedExternally
        set(value) = definedExternally
    var mode: String? /* 'single' | 'multiple' | 'buttons' */
        get() = definedExternally
        set(value) = definedExternally
    var segments: Array<SegmentedControlSegment>
    var selectedIndex: Number?
        get() = definedExternally
        set(value) = definedExternally
    var change: ((selectedIndex: Number, isSelected: Boolean) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarSliderConstructorOptions {
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minValue: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxValue: Number?
        get() = definedExternally
        set(value) = definedExternally
    var change: ((newValue: Number) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TouchBarSpacerConstructorOptions {
    var size: String? /* 'small' | 'large' | 'flexible' */
        get() = definedExternally
        set(value) = definedExternally
}

external interface TraceBufferUsageReturnValue {
    var value: Number
    var percentage: Number
}

external interface UpdateTargetUrlEvent : Event {
    var url: String
}

external interface UploadProgress {
    var active: Boolean
    var started: Boolean
    var current: Number
    var total: Number
}

external interface VisibleOnAllWorkspacesOptions {
    var visibleOnFullScreen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface WebContentsPrintOptions {
    var silent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var printBackground: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var deviceName: String?
        get() = definedExternally
        set(value) = definedExternally
    var color: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var margins: Margins?
        get() = definedExternally
        set(value) = definedExternally
    var landscape: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var scaleFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pagesPerSheet: Number?
        get() = definedExternally
        set(value) = definedExternally
    var collate: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var copies: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageRanges: Record<String, Number>?
        get() = definedExternally
        set(value) = definedExternally
    var duplexMode: String? /* 'simplex' | 'shortEdge' | 'longEdge' */
        get() = definedExternally
        set(value) = definedExternally
    var dpi: Dpi?
        get() = definedExternally
        set(value) = definedExternally
    var header: String?
        get() = definedExternally
        set(value) = definedExternally
    var footer: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface WebviewTagPrintOptions {
    var silent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var printBackground: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var deviceName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface WillNavigateEvent : Event {
    var url: String
}

external interface Dpi {
    var horizontal: Number?
        get() = definedExternally
        set(value) = definedExternally
    var vertical: Number?
        get() = definedExternally
        set(value) = definedExternally
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

external interface FoundInPageResult {
    var requestId: Number
    var activeMatchOrdinal: Number
    var matches: Number
    var selectionArea: Rectangle
    var finalUpdate: Boolean
}

external interface Margins {
    var marginType: String? /* 'default' | 'none' | 'printableArea' | 'custom' */
        get() = definedExternally
        set(value) = definedExternally
    var top: Number?
        get() = definedExternally
        set(value) = definedExternally
    var bottom: Number?
        get() = definedExternally
        set(value) = definedExternally
    var left: Number?
        get() = definedExternally
        set(value) = definedExternally
    var right: Number?
        get() = definedExternally
        set(value) = definedExternally
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

external interface WebPreferences {
    var devTools: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var nodeIntegration: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var nodeIntegrationInWorker: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var nodeIntegrationInSubFrames: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var preload: String?
        get() = definedExternally
        set(value) = definedExternally
    var sandbox: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var enableRemoteModule: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var session: Session?
        get() = definedExternally
        set(value) = definedExternally
    var partition: String?
        get() = definedExternally
        set(value) = definedExternally
    var affinity: String?
        get() = definedExternally
        set(value) = definedExternally
    var zoomFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
    var javascript: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var webSecurity: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var allowRunningInsecureContent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var images: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var textAreasAreResizable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var webgl: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var plugins: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var experimentalFeatures: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var scrollBounce: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var enableBlinkFeatures: String?
        get() = definedExternally
        set(value) = definedExternally
    var disableBlinkFeatures: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultFontFamily: DefaultFontFamily?
        get() = definedExternally
        set(value) = definedExternally
    var defaultFontSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var defaultMonospaceFontSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minimumFontSize: Number?
        get() = definedExternally
        set(value) = definedExternally
    var defaultEncoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var backgroundThrottling: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var offscreen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var contextIsolation: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var nativeWindowOpen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var webviewTag: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var additionalArguments: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var safeDialogs: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var safeDialogsMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var disableDialogs: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var navigateOnDragDrop: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var autoplayPolicy: String? /* 'no-user-gesture-required' | 'user-gesture-required' | 'document-user-activation-required' */
        get() = definedExternally
        set(value) = definedExternally
    var disableHtmlFullscreenWindowResize: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var accessibleTitle: String?
        get() = definedExternally
        set(value) = definedExternally
    var spellcheck: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var enableWebSQL: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var v8CacheOptions: String? /* 'none' | 'code' | 'bypassHeatCheck' | 'bypassHeatCheckAndEagerCompile' */
        get() = definedExternally
        set(value) = definedExternally
}

external interface DefaultFontFamily {
    var standard: String?
        get() = definedExternally
        set(value) = definedExternally
    var serif: String?
        get() = definedExternally
        set(value) = definedExternally
    var sansSerif: String?
        get() = definedExternally
        set(value) = definedExternally
    var monospace: String?
        get() = definedExternally
        set(value) = definedExternally
    var cursive: String?
        get() = definedExternally
        set(value) = definedExternally
    var fantasy: String?
        get() = definedExternally
        set(value) = definedExternally
}