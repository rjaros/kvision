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
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE"
)

package pl.treksoft.kvision.electron.nodejs

import NodeModule
import NodeRequire
import org.khronos.webgl.Uint8Array
import pl.treksoft.kvision.electron.electron.BlinkMemoryInfo
import pl.treksoft.kvision.electron.electron.CPUUsage
import pl.treksoft.kvision.electron.electron.HeapStatistics
import pl.treksoft.kvision.electron.electron.IOCounters
import pl.treksoft.kvision.electron.electron.ProcessMemoryInfo
import pl.treksoft.kvision.electron.electron.SystemMemoryInfo
import kotlin.js.Console
import kotlin.js.Promise

external interface InspectOptions {
    var getters: dynamic /* String | String | Boolean? */
        get() = definedExternally
        set(value) = definedExternally
    var showHidden: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var depth: Number?
        get() = definedExternally
        set(value) = definedExternally
    var colors: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var customInspect: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var showProxy: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maxArrayLength: Number?
        get() = definedExternally
        set(value) = definedExternally
    var breakLength: Number?
        get() = definedExternally
        set(value) = definedExternally
    var compact: dynamic /* Boolean? | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var sorted: dynamic /* Boolean? | ((a: String, b: String) -> Number)? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConsoleConstructorOptions {
    var stdout: WritableStream
    var stderr: WritableStream?
        get() = definedExternally
        set(value) = definedExternally
    var ignoreErrors: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var colorMode: dynamic /* Boolean? | String */
        get() = definedExternally
        set(value) = definedExternally
    var inspectOptions: InspectOptions?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConsoleConstructor {
    var prototype: Console
}

external interface CallSite {
    fun getThis(): Any
    fun getTypeName(): String?
    fun getFunction(): Function<*>?
    fun getFunctionName(): String?
    fun getMethodName(): String?
    fun getFileName(): String?
    fun getLineNumber(): Number?
    fun getColumnNumber(): Number?
    fun getEvalOrigin(): String?
    fun isToplevel(): Boolean
    fun isEval(): Boolean
    fun isNative(): Boolean
    fun isConstructor(): Boolean
}

external open class EventEmitter {
    open fun addListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun addListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun on(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun on(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun once(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun once(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun removeListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun removeListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun off(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun off(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun removeAllListeners(event: String = definedExternally): EventEmitter /* this */
    open fun removeAllListeners(event: Any = definedExternally): EventEmitter /* this */
    open fun setMaxListeners(n: Number): EventEmitter /* this */
    open fun getMaxListeners(): Number
    open fun listeners(event: String): Array<Function<*>>
    open fun listeners(event: Any): Array<Function<*>>
    open fun rawListeners(event: String): Array<Function<*>>
    open fun rawListeners(event: Any): Array<Function<*>>
    open fun emit(event: String, vararg args: Any): Boolean
    open fun emit(event: Any, vararg args: Any): Boolean
    open fun listenerCount(type: String): Number
    open fun listenerCount(type: Any): Number
    open fun prependListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun prependListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun prependOnceListener(event: String, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): EventEmitter /* this */
    open fun eventNames(): Array<dynamic /* String | Any */>
}

external interface `T$3` {
    var end: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ReadableStream : EventEmitter {
    var readable: Boolean
    fun read(size: Number = definedExternally): dynamic /* String | Buffer */
    fun setEncoding(encoding: String): ReadableStream /* this */
    fun pause(): ReadableStream /* this */
    fun resume(): ReadableStream /* this */
    fun isPaused(): Boolean
    fun <T : WritableStream> pipe(destination: T, options: `T$3` = definedExternally): T
    fun unpipe(destination: WritableStream = definedExternally): ReadableStream /* this */
    fun unshift(
        chunk: String,
        encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */ = definedExternally
    )

    fun unshift(
        chunk: Uint8Array,
        encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */ = definedExternally
    )

    fun wrap(oldStream: ReadableStream): ReadableStream /* this */
}

external interface WritableStream : EventEmitter {
    var writable: Boolean
    fun write(buffer: Uint8Array, cb: (err: Error?) -> Unit = definedExternally): Boolean
    fun write(buffer: String, cb: (err: Error?) -> Unit = definedExternally): Boolean
    fun write(str: String, encoding: String = definedExternally, cb: (err: Error?) -> Unit = definedExternally): Boolean
    fun end(cb: () -> Unit = definedExternally)
    fun end(data: String, cb: () -> Unit = definedExternally)
    fun end(data: Uint8Array, cb: () -> Unit = definedExternally)
    fun end(str: String, encoding: String = definedExternally, cb: () -> Unit = definedExternally)
}

external interface ReadWriteStream : ReadableStream, WritableStream

external interface Domain : EventEmitter {
    fun <T> run(fn: (args: Any) -> T, vararg args: Any): T
    fun add(emitter: EventEmitter)
    fun add(emitter: Timer)
    fun remove(emitter: EventEmitter)
    fun remove(emitter: Timer)
    fun <T : Function<*>> bind(cb: T): T
    fun <T : Function<*>> intercept(cb: T): T
    override fun addListener(event: String, listener: (args: Any) -> Unit): Domain /* this */
    override fun on(event: String, listener: (args: Any) -> Unit): Domain /* this */
    override fun once(event: String, listener: (args: Any) -> Unit): Domain /* this */
    override fun removeListener(event: String, listener: (args: Any) -> Unit): Domain /* this */
    override fun removeAllListeners(event: String): Domain /* this */
}

external interface MemoryUsage {
    var rss: Number
    var heapTotal: Number
    var heapUsed: Number
    var external: Number
}

external interface CpuUsage {
    var user: Number
    var system: Number
}

external interface ProcessRelease {
    var name: String
    var sourceUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var headersUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var libUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var lts: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ProcessVersions {
    var electron: String
    var chrome: String
    var http_parser: String
    var node: String
    var v8: String
    var ares: String
    var uv: String
    var zlib: String
    var modules: String
    var openssl: String
}

external interface Socket : ReadWriteStream {
    var isTTY: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ProcessEnv

@Suppress("NOTHING_TO_INLINE")
inline operator fun ProcessEnv.get(key: String): String? = asDynamic()[key]

@Suppress("NOTHING_TO_INLINE")
inline operator fun ProcessEnv.set(key: String, value: String?) {
    asDynamic()[key] = value
}

external interface HRTime {
    fun bigint(): Any
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun HRTime.invoke(time: dynamic): dynamic /* JsTuple<Number, Number> */ {
    return asDynamic()(time)
}

external interface ProcessReport {
    var directory: String
    var filename: String
    fun getReport(err: Error = definedExternally): String
    var reportOnFatalError: Boolean
    var reportOnSignal: Boolean
    var reportOnUncaughtException: Boolean
    var signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */
    fun writeReport(fileName: String = definedExternally): String
    fun writeReport(error: Error = definedExternally): String
    fun writeReport(fileName: String = definedExternally, err: Error = definedExternally): String
}

external interface ResourceUsage {
    var fsRead: Number
    var fsWrite: Number
    var involuntaryContextSwitches: Number
    var ipcReceived: Number
    var ipcSent: Number
    var majorPageFault: Number
    var maxRSS: Number
    var minorPageFault: Number
    var sharedMemorySize: Number
    var signalsCount: Number
    var swappedOut: Number
    var systemCPUTime: Number
    var unsharedDataSize: Number
    var unsharedStackSize: Number
    var userCPUTime: Number
    var voluntaryContextSwitches: Number
}

external interface `T$4` {
    var cflags: Array<Any>
    var default_configuration: String
    var defines: Array<String>
    var include_dirs: Array<String>
    var libraries: Array<String>
}

external interface `T$5` {
    var clang: Number
    var host_arch: String
    var node_install_npm: Boolean
    var node_install_waf: Boolean
    var node_prefix: String
    var node_shared_openssl: Boolean
    var node_shared_v8: Boolean
    var node_shared_zlib: Boolean
    var node_use_dtrace: Boolean
    var node_use_etw: Boolean
    var node_use_openssl: Boolean
    var target_arch: String
    var v8_no_strict_aliasing: Number
    var v8_use_snapshot: Boolean
    var visibility: String
}

external interface `T$6` {
    var target_defaults: `T$4`
    var variables: `T$5`
}

external interface `T$7` {
    var inspector: Boolean
    var debug: Boolean
    var uv: Boolean
    var ipv6: Boolean
    var tls_alpn: Boolean
    var tls_sni: Boolean
    var tls_ocsp: Boolean
    var tls: Boolean
}

external interface `T$8` {
    var swallowErrors: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Process : EventEmitter {
    fun on(event: String /* 'loaded' */, listener: Function<*>): Process /* this */
    fun once(event: String /* 'loaded' */, listener: Function<*>): Process /* this */
    fun addListener(event: String /* 'loaded' */, listener: Function<*>): Process /* this */
    fun removeListener(event: String /* 'loaded' */, listener: Function<*>): Process /* this */
    fun crash()
    fun getBlinkMemoryInfo(): BlinkMemoryInfo
    fun getCPUUsage(): CPUUsage
    fun getCreationTime(): Number?
    fun getHeapStatistics(): HeapStatistics
    fun getIOCounters(): IOCounters
    fun getProcessMemoryInfo(): Promise<ProcessMemoryInfo>
    fun getSystemMemoryInfo(): SystemMemoryInfo
    fun getSystemVersion(): String
    fun hang()
    fun setFdLimit(maxDescriptors: Number)
    fun takeHeapSnapshot(filePath: String): Boolean
    var chrome: String
    var defaultApp: Boolean
    var electron: String
    var enablePromiseAPIs: Boolean
    var isMainFrame: Boolean
    var mas: Boolean
    var noAsar: Boolean
    var noDeprecation: Boolean
    var resourcesPath: String
    var sandboxed: Boolean
    var throwDeprecation: Boolean
    var traceDeprecation: Boolean
    var traceProcessWarnings: Boolean
    var type: String
    var windowsStore: Boolean
    var stdout: tty.WriteStream
    var stderr: tty.WriteStream
    var stdin: tty.ReadStream
    fun openStdin(): Socket
    var argv: Array<String>
    var argv0: String
    var execArgv: Array<String>
    var execPath: String
    fun abort()
    fun chdir(directory: String)
    fun cwd(): String
    var debugPort: Number
    fun emitWarning(warning: String, name: String = definedExternally, ctor: Function<*> = definedExternally)
    fun emitWarning(warning: Error, name: String = definedExternally, ctor: Function<*> = definedExternally)
    var env: ProcessEnv
    fun exit(code: Number = definedExternally): Any
    var exitCode: Number?
        get() = definedExternally
        set(value) = definedExternally

    fun getgid(): Number
    fun setgid(id: Number)
    fun setgid(id: String)
    fun getuid(): Number
    fun setuid(id: Number)
    fun setuid(id: String)
    fun geteuid(): Number
    fun seteuid(id: Number)
    fun seteuid(id: String)
    fun getegid(): Number
    fun setegid(id: Number)
    fun setegid(id: String)
    fun getgroups(): Array<Number>
    fun setgroups(groups: Array<dynamic /* String | Number */>)
    fun setUncaughtExceptionCaptureCallback(cb: ((err: Error) -> Unit)?)
    fun hasUncaughtExceptionCaptureCallback(): Boolean
    var version: String
    var versions: ProcessVersions
    var config: `T$6`
    fun kill(pid: Number, signal: String = definedExternally): Boolean
    fun kill(pid: Number, signal: Number = definedExternally): Boolean
    var pid: Number
    var ppid: Number
    var title: String
    var arch: String
    var platform: String /* 'aix' | 'android' | 'darwin' | 'freebsd' | 'linux' | 'openbsd' | 'sunos' | 'win32' | 'cygwin' | 'netbsd' */
    var mainModule: NodeModule?
        get() = definedExternally
        set(value) = definedExternally

    fun memoryUsage(): MemoryUsage
    fun cpuUsage(previousValue: CpuUsage = definedExternally): CpuUsage
    fun nextTick(callback: Function<*>, vararg args: Any)
    var release: ProcessRelease
    var features: `T$7`
    fun umask(mask: Number = definedExternally): Number
    fun uptime(): Number
    var hrtime: HRTime
    var domain: Domain
    val send: ((message: Any, sendHandle: Any, options: `T$8`, callback: (error: Error?) -> Unit) -> Boolean)?
        get() = definedExternally

    fun disconnect()
    var connected: Boolean
    var allowedNodeEnvironmentFlags: Set<String>
    var report: ProcessReport?
        get() = definedExternally
        set(value) = definedExternally

    fun resourceUsage(): ResourceUsage
    fun addListener(event: String /* "beforeExit" */, listener: BeforeExitListener): Process /* this */
    fun addListener(event: String /* "disconnect" */, listener: DisconnectListener): Process /* this */
//    fun addListener(event: String /* "exit" */, listener: ExitListener): Process /* this */
    fun addListener(event: String /* "rejectionHandled" */, listener: RejectionHandledListener): Process /* this */
    fun addListener(event: String /* "uncaughtException" */, listener: UncaughtExceptionListener): Process /* this */
    fun addListener(event: String /* "unhandledRejection" */, listener: UnhandledRejectionListener): Process /* this */
//    fun addListener(event: String /* "warning" */, listener: WarningListener): Process /* this */
    fun addListener(event: String /* "message" */, listener: MessageListener): Process /* this */
    fun addListener(
        event: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */,
        listener: SignalsListener
    ): Process /* this */

    fun addListener(event: String /* "newListener" */, listener: NewListenerListener): Process /* this */
//    fun addListener(event: String /* "removeListener" */, listener: RemoveListenerListener): Process /* this */
    fun addListener(event: String /* "multipleResolves" */, listener: MultipleResolveListener): Process /* this */
    fun emit(event: String /* "beforeExit" | "exit" */, code: Number): Boolean
    override fun emit(event: String, vararg args: Any): Boolean
    fun emit(event: String /* "disconnect" */): Boolean
    fun emit(event: String /* "rejectionHandled" */, promise: Promise<Any>): Boolean
    fun emit(event: String /* "uncaughtException" | "warning" */, error: Error): Boolean
    fun emit(event: String /* "unhandledRejection" */, reason: Any, promise: Promise<Any>): Boolean
    fun emit(event: String /* "message" */, message: Any, sendHandle: Any): Process /* this */
    fun emit(
        event: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */,
        signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */
    ): Boolean

    fun emit(
        event: String /* "newListener" | "removeListener" */,
        eventName: String,
        listener: (args: Any) -> Unit
    ): Process /* this */

    fun emit(event: String /* "newListener" */, eventName: Any, listener: (args: Any) -> Unit): Process /* this */
    fun emit(event: String /* "multipleResolves" */, listener: MultipleResolveListener): Process /* this */
    fun on(event: String /* "beforeExit" */, listener: BeforeExitListener): Process /* this */
    fun on(event: String /* "disconnect" */, listener: DisconnectListener): Process /* this */
//    fun on(event: String /* "exit" */, listener: ExitListener): Process /* this */
    fun on(event: String /* "rejectionHandled" */, listener: RejectionHandledListener): Process /* this */
    fun on(event: String /* "uncaughtException" */, listener: UncaughtExceptionListener): Process /* this */
    fun on(event: String /* "unhandledRejection" */, listener: UnhandledRejectionListener): Process /* this */
//    fun on(event: String /* "warning" */, listener: WarningListener): Process /* this */
    fun on(event: String /* "message" */, listener: MessageListener): Process /* this */
    fun on(
        event: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */,
        listener: SignalsListener
    ): Process /* this */

    fun on(event: String /* "newListener" */, listener: NewListenerListener): Process /* this */
//    fun on(event: String /* "removeListener" */, listener: RemoveListenerListener): Process /* this */
    fun on(event: String /* "multipleResolves" */, listener: MultipleResolveListener): Process /* this */
    fun once(event: String /* "beforeExit" */, listener: BeforeExitListener): Process /* this */
    fun once(event: String /* "disconnect" */, listener: DisconnectListener): Process /* this */
//    fun once(event: String /* "exit" */, listener: ExitListener): Process /* this */
    fun once(event: String /* "rejectionHandled" */, listener: RejectionHandledListener): Process /* this */
    fun once(event: String /* "uncaughtException" */, listener: UncaughtExceptionListener): Process /* this */
    fun once(event: String /* "unhandledRejection" */, listener: UnhandledRejectionListener): Process /* this */
//    fun once(event: String /* "warning" */, listener: WarningListener): Process /* this */
    fun once(event: String /* "message" */, listener: MessageListener): Process /* this */
    fun once(
        event: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */,
        listener: SignalsListener
    ): Process /* this */

    fun once(event: String /* "newListener" */, listener: NewListenerListener): Process /* this */
//    fun once(event: String /* "removeListener" */, listener: RemoveListenerListener): Process /* this */
    fun once(event: String /* "multipleResolves" */, listener: MultipleResolveListener): Process /* this */
    fun prependListener(event: String /* "beforeExit" */, listener: BeforeExitListener): Process /* this */
    fun prependListener(event: String /* "disconnect" */, listener: DisconnectListener): Process /* this */
//    fun prependListener(event: String /* "exit" */, listener: ExitListener): Process /* this */
    fun prependListener(event: String /* "rejectionHandled" */, listener: RejectionHandledListener): Process /* this */
    fun prependListener(
        event: String /* "uncaughtException" */,
        listener: UncaughtExceptionListener
    ): Process /* this */

    fun prependListener(
        event: String /* "unhandledRejection" */,
        listener: UnhandledRejectionListener
    ): Process /* this */

//    fun prependListener(event: String /* "warning" */, listener: WarningListener): Process /* this */
    fun prependListener(event: String /* "message" */, listener: MessageListener): Process /* this */
    fun prependListener(
        event: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */,
        listener: SignalsListener
    ): Process /* this */

    fun prependListener(event: String /* "newListener" */, listener: NewListenerListener): Process /* this */
//    fun prependListener(event: String /* "removeListener" */, listener: RemoveListenerListener): Process /* this */
    fun prependListener(event: String /* "multipleResolves" */, listener: MultipleResolveListener): Process /* this */
    fun prependOnceListener(event: String /* "beforeExit" */, listener: BeforeExitListener): Process /* this */
    fun prependOnceListener(event: String /* "disconnect" */, listener: DisconnectListener): Process /* this */
//    fun prependOnceListener(event: String /* "exit" */, listener: ExitListener): Process /* this */
    fun prependOnceListener(
        event: String /* "rejectionHandled" */,
        listener: RejectionHandledListener
    ): Process /* this */

    fun prependOnceListener(
        event: String /* "uncaughtException" */,
        listener: UncaughtExceptionListener
    ): Process /* this */

    fun prependOnceListener(
        event: String /* "unhandledRejection" */,
        listener: UnhandledRejectionListener
    ): Process /* this */

//    fun prependOnceListener(event: String /* "warning" */, listener: WarningListener): Process /* this */
    fun prependOnceListener(event: String /* "message" */, listener: MessageListener): Process /* this */
    fun prependOnceListener(
        event: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */,
        listener: SignalsListener
    ): Process /* this */

    fun prependOnceListener(event: String /* "newListener" */, listener: NewListenerListener): Process /* this */
//    fun prependOnceListener(event: String /* "removeListener" */, listener: RemoveListenerListener): Process /* this */
    fun prependOnceListener(
        event: String /* "multipleResolves" */,
        listener: MultipleResolveListener
    ): Process /* this */

    override fun listeners(event: String /* "beforeExit" | "disconnect" | "exit" | "rejectionHandled" | "uncaughtException" | "unhandledRejection" | "warning" | "message" | "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" | "newListener" | "removeListener" | "multipleResolves" */): dynamic /* Array */
}

external interface Global {
    var Array: Any
    var ArrayBuffer: Any
    var Boolean: Any
    var Buffer: Any
    var DataView: Any
    var Date: Any
    var Error: Any
    var EvalError: Any
    var Float32Array: Any
    var Float64Array: Any
    var Function: Any
    var GLOBAL: Global
    var Infinity: Any
    var Int16Array: Any
    var Int32Array: Any
    var Int8Array: Any
    var Intl: Any
    var JSON: Any
    var Map: Any
    var Math: Any
    var NaN: Any
    var Number: Any
    var Object: Any
    var Promise: Function<*>
    var RangeError: Any
    var ReferenceError: Any
    var RegExp: Any
    var Set: Any
    var String: Any
    var Symbol: Function<*>
    var SyntaxError: Any
    var TypeError: Any
    var URIError: Any
    var Uint16Array: Any
    var Uint32Array: Any
    var Uint8Array: Any
    var Uint8ClampedArray: Function<*>
    var WeakMap: Any
    var WeakSet: Any
    var clearImmediate: (immediateId: Immediate) -> Unit
    var clearInterval: (intervalId: Timeout) -> Unit
    var clearTimeout: (timeoutId: Timeout) -> Unit
    var console: Any
    var decodeURI: Any
    var decodeURIComponent: Any
    var encodeURI: Any
    var encodeURIComponent: Any
    var escape: (str: String) -> String
    var eval: Any
    var global: Global
    var isFinite: Any
    var isNaN: Any
    var parseFloat: Any
    var parseInt: Any
    var process: Process
    var root: Global
    var setImmediate: (callback: (args: Any) -> Unit, args: Any) -> Immediate
    var setInterval: (callback: (args: Any) -> Unit, ms: Number, args: Any) -> Timeout
    var setTimeout: (callback: (args: Any) -> Unit, ms: Number, args: Any) -> Timeout
    var queueMicrotask: Any
    var undefined: Any
    var unescape: (str: String) -> String
    var gc: () -> Unit
    var v8debug: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Timer {
    fun hasRef(): Boolean
    fun ref(): Timer /* this */
    fun refresh(): Timer /* this */
    fun unref(): Timer /* this */
}

external open class Immediate {
    open fun hasRef(): Boolean
    open fun ref(): Immediate /* this */
    open fun unref(): Immediate /* this */
    open var _onImmediate: Function<*>
}

external open class Timeout : Timer {
    override fun hasRef(): Boolean
    override fun ref(): Timeout /* this */
    override fun refresh(): Timeout /* this */
    override fun unref(): Timeout /* this */
}

external open class Module(id: String, parent: Module = definedExternally) {
    open var exports: Any
    open var require: dynamic
    open var id: String
    open var filename: String
    open var loaded: Boolean
    open var parent: Module?
    open var children: Array<Module>
    open var path: String
    open var paths: Array<String>

    companion object {
        fun runMain()
        fun wrap(code: String): String
        fun createRequireFromPath(path: String): NodeRequire
        fun createRequire(path: String): NodeRequire
        var builtinModules: Array<String>
        var Module: Any
    }
}

external interface ReadStream : tty.ReadStream

external interface WriteStream : tty.WriteStream