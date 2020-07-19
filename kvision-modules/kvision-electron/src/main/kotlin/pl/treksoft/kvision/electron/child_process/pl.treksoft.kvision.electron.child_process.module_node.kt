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

@file:JsModule("child_process")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.child_process

import node.buffer.Buffer
import pl.treksoft.kvision.electron.NodeJS.ProcessEnv
import pl.treksoft.kvision.electron.events.internal.EventEmitter
import pl.treksoft.kvision.electron.net.Server
import pl.treksoft.kvision.electron.net.Socket
import pl.treksoft.kvision.electron.stream.internal.*
import kotlin.js.Promise

external interface ChildProcess : EventEmitter {
    var stdin: Writable?
    var stdout: Readable?
    var stderr: Readable?
    var channel: Pipe?
        get() = definedExternally
        set(value) = definedExternally
    var stdio: dynamic /* JsTuple<Writable?, Readable?, Readable?, dynamic, dynamic> */
        get() = definedExternally
        set(value) = definedExternally
    var killed: Boolean
    var pid: Number
    var connected: Boolean
    var exitCode: Number?
    var signalCode: Number?
    var spawnargs: Array<String>
    var spawnfile: String
    fun kill(signal: dynamic /* String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | Number */ = definedExternally): Boolean
    fun send(message: Any, callback: (error: Error?) -> Unit = definedExternally): Boolean
    fun send(
        message: Any,
        sendHandle: Socket = definedExternally,
        callback: (error: Error?) -> Unit = definedExternally
    ): Boolean

    fun send(
        message: Any,
        sendHandle: Server = definedExternally,
        callback: (error: Error?) -> Unit = definedExternally
    ): Boolean

    fun send(
        message: Any,
        sendHandle: Socket = definedExternally,
        options: MessageOptions = definedExternally,
        callback: (error: Error?) -> Unit = definedExternally
    ): Boolean

    fun send(
        message: Any,
        sendHandle: Server = definedExternally,
        options: MessageOptions = definedExternally,
        callback: (error: Error?) -> Unit = definedExternally
    ): Boolean

    fun disconnect()
    fun unref()
    fun ref()
    override fun addListener(event: String, listener: (args: Any) -> Unit): ChildProcess /* this */
    fun addListener(
        event: String /* "close" */,
        listener: (code: Number, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun addListener(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun addListener(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun addListener(
        event: String /* "exit" */,
        listener: (code: Number?, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun addListener(
        event: String /* "message" */,
        listener: (message: Any, sendHandle: dynamic /* net.Socket | net.Server */) -> Unit
    ): ChildProcess /* this */

    override fun emit(event: String, vararg args: Any): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    fun emit(
        event: String /* "close" */,
        code: Number,
        signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */
    ): Boolean

    fun emit(event: String /* "disconnect" */): Boolean
    fun emit(event: String /* "error" */, err: Error): Boolean
    fun emit(
        event: String /* "exit" */,
        code: Number?,
        signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */
    ): Boolean

    fun emit(event: String /* "message" */, message: Any, sendHandle: Socket): Boolean
    fun emit(event: String /* "message" */, message: Any, sendHandle: Server): Boolean
    override fun on(event: String, listener: (args: Any) -> Unit): ChildProcess /* this */
    fun on(
        event: String /* "close" */,
        listener: (code: Number, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun on(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun on(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun on(
        event: String /* "exit" */,
        listener: (code: Number?, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun on(
        event: String /* "message" */,
        listener: (message: Any, sendHandle: dynamic /* net.Socket | net.Server */) -> Unit
    ): ChildProcess /* this */

    override fun once(event: String, listener: (args: Any) -> Unit): ChildProcess /* this */
    fun once(
        event: String /* "close" */,
        listener: (code: Number, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun once(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun once(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun once(
        event: String /* "exit" */,
        listener: (code: Number?, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun once(
        event: String /* "message" */,
        listener: (message: Any, sendHandle: dynamic /* net.Socket | net.Server */) -> Unit
    ): ChildProcess /* this */

    override fun prependListener(event: String, listener: (args: Any) -> Unit): ChildProcess /* this */
    fun prependListener(
        event: String /* "close" */,
        listener: (code: Number, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun prependListener(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun prependListener(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun prependListener(
        event: String /* "exit" */,
        listener: (code: Number?, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun prependListener(
        event: String /* "message" */,
        listener: (message: Any, sendHandle: dynamic /* net.Socket | net.Server */) -> Unit
    ): ChildProcess /* this */

    override fun prependOnceListener(event: String, listener: (args: Any) -> Unit): ChildProcess /* this */
    fun prependOnceListener(
        event: String /* "close" */,
        listener: (code: Number, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun prependOnceListener(event: String /* "disconnect" */, listener: () -> Unit): ChildProcess /* this */
    fun prependOnceListener(event: String /* "error" */, listener: (err: Error) -> Unit): ChildProcess /* this */
    fun prependOnceListener(
        event: String /* "exit" */,
        listener: (code: Number?, signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */) -> Unit
    ): ChildProcess /* this */

    fun prependOnceListener(
        event: String /* "message" */,
        listener: (message: Any, sendHandle: dynamic /* net.Socket | net.Server */) -> Unit
    ): ChildProcess /* this */
}

external interface ChildProcessWithoutNullStreams : ChildProcess {
    override var stdio: dynamic /* JsTuple<Writable, Readable, Readable, dynamic, dynamic> */
        get() = definedExternally
        set(value) = definedExternally
}

external interface ChildProcessByStdio<I : Writable?, O : Readable?, E : Readable?> : ChildProcess {
    override var stdio: dynamic /* JsTuple<I, O, E, dynamic, dynamic> */
        get() = definedExternally
        set(value) = definedExternally
}

external interface MessageOptions {
    var keepOpen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ProcessEnvOptions {
    var uid: Number?
        get() = definedExternally
        set(value) = definedExternally
    var gid: Number?
        get() = definedExternally
        set(value) = definedExternally
    var cwd: String?
        get() = definedExternally
        set(value) = definedExternally
    var env: ProcessEnv?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CommonOptions : ProcessEnvOptions {
    var windowsHide: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SpawnOptions : CommonOptions {
    var argv0: String?
        get() = definedExternally
        set(value) = definedExternally
    var stdio: dynamic /* String | String | String | Array<dynamic /* String | String | String | String | Stream? | Number? */>? */
        get() = definedExternally
        set(value) = definedExternally
    var detached: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var shell: dynamic /* Boolean? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var windowsVerbatimArguments: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SpawnOptionsWithoutStdio : SpawnOptions {
    override var stdio: dynamic /* String | Array<String /* 'pipe' */>? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface SpawnOptionsWithStdioTuple<Stdin, Stdout, Stderr> : SpawnOptions {
    override var stdio: dynamic /* JsTuple<Stdin, Stdout, Stderr> */
        get() = definedExternally
        set(value) = definedExternally
}

external fun spawn(
    command: String,
    options: SpawnOptionsWithoutStdio = definedExternally
): ChildProcessWithoutNullStreams

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, String /* 'pipe' */, String /* 'pipe' */>
): ChildProcessByStdio<Writable, Readable, Readable>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, String /* 'pipe' */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Writable, Readable, Nothing?>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, dynamic /* String | String | Stream */, String /* 'pipe' */>
): ChildProcessByStdio<Writable, Nothing?, Readable>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, String /* 'pipe' */, String /* 'pipe' */>
): ChildProcessByStdio<Nothing?, Readable, Readable>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, dynamic /* String | String | Stream */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Writable, Nothing?, Nothing?>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, String /* 'pipe' */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Nothing?, Readable, Nothing?>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, dynamic /* String | String | Stream */, String /* 'pipe' */>
): ChildProcessByStdio<Nothing?, Nothing?, Readable>

external fun spawn(
    command: String,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, dynamic /* String | String | Stream */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Nothing?, Nothing?, Nothing?>

external fun spawn(command: String, options: SpawnOptions): ChildProcess

external fun spawn(
    command: String,
    args: Array<String> = definedExternally,
    options: SpawnOptionsWithoutStdio = definedExternally
): ChildProcessWithoutNullStreams

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, String /* 'pipe' */, String /* 'pipe' */>
): ChildProcessByStdio<Writable, Readable, Readable>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, String /* 'pipe' */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Writable, Readable, Nothing?>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, dynamic /* String | String | Stream */, String /* 'pipe' */>
): ChildProcessByStdio<Writable, Nothing?, Readable>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, String /* 'pipe' */, String /* 'pipe' */>
): ChildProcessByStdio<Nothing?, Readable, Readable>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<String /* 'pipe' */, dynamic /* String | String | Stream */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Writable, Nothing?, Nothing?>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, String /* 'pipe' */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Nothing?, Readable, Nothing?>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, dynamic /* String | String | Stream */, String /* 'pipe' */>
): ChildProcessByStdio<Nothing?, Nothing?, Readable>

external fun spawn(
    command: String,
    args: Array<String>,
    options: SpawnOptionsWithStdioTuple<dynamic /* String | String | Stream */, dynamic /* String | String | Stream */, dynamic /* String | String | Stream */>
): ChildProcessByStdio<Nothing?, Nothing?, Nothing?>

external fun spawn(command: String, args: Array<String>, options: SpawnOptions): ChildProcess

external interface ExecOptions : CommonOptions {
    var shell: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxBuffer: Number?
        get() = definedExternally
        set(value) = definedExternally
    var killSignal: dynamic /* String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | Number? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface ExecOptionsWithStringEncoding : ExecOptions {
    var encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
}

external interface ExecOptionsWithBufferEncoding : ExecOptions {
    var encoding: String?
}

external interface ExecException {
    var cmd: String?
        get() = definedExternally
        set(value) = definedExternally
    var killed: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var code: Number?
        get() = definedExternally
        set(value) = definedExternally
    var signal: String? /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */
        get() = definedExternally
        set(value) = definedExternally
}

external fun exec(
    command: String,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit = definedExternally
): ChildProcess

external interface `T$19` {
    var encoding: String /* "buffer" */
}

external fun exec(
    command: String,
    options: `T$19` /* `T$19` & ExecOptions */,
    callback: (error: ExecException?, stdout: Buffer, stderr: Buffer) -> Unit = definedExternally
): ChildProcess

external interface `T$20` {
    var encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
}

external fun exec(
    command: String,
    options: `T$20` /* `T$20` & ExecOptions */,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit = definedExternally
): ChildProcess

external interface `T$21` {
    var encoding: String
}

external fun exec(
    command: String,
    options: `T$21` /* `T$21` & ExecOptions */,
    callback: (error: ExecException?, stdout: dynamic /* String | Buffer */, stderr: dynamic /* String | Buffer */) -> Unit = definedExternally
): ChildProcess

external fun exec(
    command: String,
    options: ExecOptions,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit = definedExternally
): ChildProcess

external interface `T$22` {
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
}

external fun exec(
    command: String,
    options: `T$22` /* `T$22` & ExecOptions */,
    callback: (error: ExecException?, stdout: dynamic /* String | Buffer */, stderr: dynamic /* String | Buffer */) -> Unit = definedExternally
): ChildProcess

external interface PromiseWithChild<T> : Promise<T> {
    var child: ChildProcess
}

external interface ExecFileOptions : CommonOptions {
    var maxBuffer: Number?
        get() = definedExternally
        set(value) = definedExternally
    var killSignal: dynamic /* String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var windowsVerbatimArguments: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var shell: dynamic /* Boolean? | String? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface ExecFileOptionsWithStringEncoding : ExecFileOptions {
    var encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "latin1" | "binary" | "hex" */
}

external interface ExecFileOptionsWithBufferEncoding : ExecFileOptions {
    var encoding: String /* 'buffer' */
}

external interface ExecFileOptionsWithOtherEncoding : ExecFileOptions {
    var encoding: String
}

external fun execFile(file: String): ChildProcess

external fun execFile(file: String, options: `T$22` /* `T$22` & ExecFileOptions */): ChildProcess

external fun execFile(file: String, args: Array<String>? = definedExternally): ChildProcess

external fun execFile(file: String, args: Array<String>?, options: `T$22` /* `T$22` & ExecFileOptions */): ChildProcess

external fun execFile(
    file: String,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    args: Array<String>?,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    options: ExecFileOptionsWithBufferEncoding,
    callback: (error: ExecException?, stdout: Buffer, stderr: Buffer) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    args: Array<String>?,
    options: ExecFileOptionsWithBufferEncoding,
    callback: (error: ExecException?, stdout: Buffer, stderr: Buffer) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    options: ExecFileOptionsWithStringEncoding,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    args: Array<String>?,
    options: ExecFileOptionsWithStringEncoding,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    options: ExecFileOptionsWithOtherEncoding,
    callback: (error: ExecException?, stdout: dynamic /* String | Buffer */, stderr: dynamic /* String | Buffer */) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    args: Array<String>?,
    options: ExecFileOptionsWithOtherEncoding,
    callback: (error: ExecException?, stdout: dynamic /* String | Buffer */, stderr: dynamic /* String | Buffer */) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    options: ExecFileOptions,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    args: Array<String>?,
    options: ExecFileOptions,
    callback: (error: ExecException?, stdout: String, stderr: String) -> Unit
): ChildProcess

external fun execFile(
    file: String,
    options: `T$22` /* `T$22` & ExecFileOptions */,
    callback: ((error: ExecException?, stdout: dynamic /* String | Buffer */, stderr: dynamic /* String | Buffer */) -> Unit)?
): ChildProcess

external fun execFile(
    file: String,
    args: Array<String>?,
    options: `T$22` /* `T$22` & ExecFileOptions */,
    callback: ((error: ExecException?, stdout: dynamic /* String | Buffer */, stderr: dynamic /* String | Buffer */) -> Unit)?
): ChildProcess

external interface ForkOptions : ProcessEnvOptions {
    var execPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var execArgv: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var silent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var stdio: dynamic /* String | String | String | Array<dynamic /* String | String | String | String | Stream? | Number? */>? */
        get() = definedExternally
        set(value) = definedExternally
    var detached: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var windowsVerbatimArguments: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external fun fork(
    modulePath: String,
    args: Array<String> = definedExternally,
    options: ForkOptions = definedExternally
): ChildProcess

external interface SpawnSyncOptions : CommonOptions {
    var argv0: String?
        get() = definedExternally
        set(value) = definedExternally
    var input: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */
        get() = definedExternally
        set(value) = definedExternally
    var stdio: dynamic /* String | String | String | Array<dynamic /* String | String | String | String | Stream? | Number? */>? */
        get() = definedExternally
        set(value) = definedExternally
    var killSignal: dynamic /* String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var maxBuffer: Number?
        get() = definedExternally
        set(value) = definedExternally
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var shell: dynamic /* Boolean? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var windowsVerbatimArguments: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SpawnSyncOptionsWithStringEncoding : SpawnSyncOptions

external interface SpawnSyncOptionsWithBufferEncoding : SpawnSyncOptions

external interface SpawnSyncReturns<T> {
    var pid: Number
    var output: Array<String>
    var stdout: T
    var stderr: T
    var status: Number?
    var signal: String /* "SIGABRT" | "SIGALRM" | "SIGBUS" | "SIGCHLD" | "SIGCONT" | "SIGFPE" | "SIGHUP" | "SIGILL" | "SIGINT" | "SIGIO" | "SIGIOT" | "SIGKILL" | "SIGPIPE" | "SIGPOLL" | "SIGPROF" | "SIGPWR" | "SIGQUIT" | "SIGSEGV" | "SIGSTKFLT" | "SIGSTOP" | "SIGSYS" | "SIGTERM" | "SIGTRAP" | "SIGTSTP" | "SIGTTIN" | "SIGTTOU" | "SIGUNUSED" | "SIGURG" | "SIGUSR1" | "SIGUSR2" | "SIGVTALRM" | "SIGWINCH" | "SIGXCPU" | "SIGXFSZ" | "SIGBREAK" | "SIGLOST" | "SIGINFO" */
    var error: Error?
        get() = definedExternally
        set(value) = definedExternally
}

external fun spawnSync(command: String): SpawnSyncReturns<Buffer>

external fun spawnSync(
    command: String,
    options: SpawnSyncOptionsWithStringEncoding = definedExternally
): SpawnSyncReturns<String>

external fun spawnSync(
    command: String,
    options: SpawnSyncOptionsWithBufferEncoding = definedExternally
): SpawnSyncReturns<Buffer>

external fun spawnSync(command: String, options: SpawnSyncOptions = definedExternally): SpawnSyncReturns<Buffer>

external fun spawnSync(
    command: String,
    args: Array<String> = definedExternally,
    options: SpawnSyncOptionsWithStringEncoding = definedExternally
): SpawnSyncReturns<String>

external fun spawnSync(
    command: String,
    args: Array<String> = definedExternally,
    options: SpawnSyncOptionsWithBufferEncoding = definedExternally
): SpawnSyncReturns<Buffer>

external fun spawnSync(
    command: String,
    args: Array<String> = definedExternally,
    options: SpawnSyncOptions = definedExternally
): SpawnSyncReturns<Buffer>

external interface ExecSyncOptions : CommonOptions {
    var input: dynamic /* String? | Uint8Array? */
        get() = definedExternally
        set(value) = definedExternally
    var stdio: dynamic /* String | String | String | Array<dynamic /* String | String | String | String | Stream? | Number? */>? */
        get() = definedExternally
        set(value) = definedExternally
    var shell: String?
        get() = definedExternally
        set(value) = definedExternally
    var killSignal: dynamic /* String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var maxBuffer: Number?
        get() = definedExternally
        set(value) = definedExternally
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ExecSyncOptionsWithStringEncoding : ExecSyncOptions

external interface ExecSyncOptionsWithBufferEncoding : ExecSyncOptions

external fun execSync(command: String): Buffer

external fun execSync(command: String, options: ExecSyncOptionsWithStringEncoding = definedExternally): String

external fun execSync(command: String, options: ExecSyncOptionsWithBufferEncoding = definedExternally): Buffer

external fun execSync(command: String, options: ExecSyncOptions = definedExternally): Buffer

external interface ExecFileSyncOptions : CommonOptions {
    var input: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */
        get() = definedExternally
        set(value) = definedExternally
    var stdio: dynamic /* String | String | String | Array<dynamic /* String | String | String | String | Stream? | Number? */>? */
        get() = definedExternally
        set(value) = definedExternally
    var killSignal: dynamic /* String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | String | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var maxBuffer: Number?
        get() = definedExternally
        set(value) = definedExternally
    var encoding: String?
        get() = definedExternally
        set(value) = definedExternally
    var shell: dynamic /* Boolean? | String? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface ExecFileSyncOptionsWithStringEncoding : ExecFileSyncOptions

external interface ExecFileSyncOptionsWithBufferEncoding : ExecFileSyncOptions

external fun execFileSync(command: String): Buffer

external fun execFileSync(command: String, options: ExecFileSyncOptionsWithStringEncoding = definedExternally): String

external fun execFileSync(command: String, options: ExecFileSyncOptionsWithBufferEncoding = definedExternally): Buffer

external fun execFileSync(command: String, options: ExecFileSyncOptions = definedExternally): Buffer

external fun execFileSync(
    command: String,
    args: Array<String> = definedExternally,
    options: ExecFileSyncOptionsWithStringEncoding = definedExternally
): String

external fun execFileSync(
    command: String,
    args: Array<String> = definedExternally,
    options: ExecFileSyncOptionsWithBufferEncoding = definedExternally
): Buffer

external fun execFileSync(
    command: String,
    args: Array<String> = definedExternally,
    options: ExecFileSyncOptions = definedExternally
): Buffer