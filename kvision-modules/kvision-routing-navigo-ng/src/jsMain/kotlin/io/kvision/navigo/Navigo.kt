package io.kvision.navigo

import kotlin.js.RegExp

external interface Route {
    var name: String
    var path: dynamic /* String | RegExp */
        get() = definedExternally
        set(value) = definedExternally
    var handler: Handler
    var hooks: RouteHooksStorage
}

typealias Handler = (match: Match) -> Unit

external interface Match {
    var url: String
    var queryString: String
    var hashString: String
    var route: Route
    var data: dynamic
    var params: dynamic
}

@Suppress("IMPLEMENTING_FUNCTION_INTERFACE")
abstract class DoneFunction : () -> Unit {
    operator fun invoke(continueResolution: Boolean) {
        this.asDynamic()(continueResolution)
    }
}

typealias BeforeHook = (done: DoneFunction, match: Match) -> Unit

typealias AfterHook = (match: Match) -> Unit

typealias LeaveHook = (done: DoneFunction, match: dynamic /* Match | Array<Match> */) -> Unit

typealias AlreadyHook = (match: Match) -> Unit

external interface RouteHooks {
    var before: BeforeHook?
        get() = definedExternally
        set(value) = definedExternally
    var after: AfterHook?
        get() = definedExternally
        set(value) = definedExternally
    var leave: LeaveHook?
        get() = definedExternally
        set(value) = definedExternally
    var already: AlreadyHook?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RouteHooksStorage {
    var before: Array<BeforeHook>?
        get() = definedExternally
        set(value) = definedExternally
    var after: Array<AfterHook>?
        get() = definedExternally
        set(value) = definedExternally
    var leave: Array<LeaveHook>?
        get() = definedExternally
        set(value) = definedExternally
    var already: Array<AlreadyHook>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface NavigateOptions {
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var stateObj: Any?
        get() = definedExternally
        set(value) = definedExternally
    var historyAPIMethod: String?
        get() = definedExternally
        set(value) = definedExternally
    var updateBrowserURL: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var callHandler: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var callHooks: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var updateState: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var force: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var resolveOptions: ResolveOptions?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ResolveOptions {
    var strategy: String? /* "ONE" | "ALL" */
        get() = definedExternally
        set(value) = definedExternally
    var hash: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var noMatchWarning: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RouterOptions : ResolveOptions {
    var linksSelector: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GenerateOptions {
    var includeRoot: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface QContext {
    var currentLocationPath: String
    var to: String
    var instance: Navigo
    var matches: Array<Match>?
        get() = definedExternally
        set(value) = definedExternally
    var match: Match?
        get() = definedExternally
        set(value) = definedExternally
    var navigateOptions: NavigateOptions?
        get() = definedExternally
        set(value) = definedExternally
    var resolveOptions: ResolveOptions?
        get() = definedExternally
        set(value) = definedExternally
    var notFoundHandled: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

@JsModule("navigo")
@JsNonModule
open external class Navigo(root: String, options: RouterOptions = definedExternally) {
    open var root: String
    open var routes: Array<Route>
    open var destroyed: Boolean
    open var current: Array<Match>?
    open fun lastResolved(): Array<Match>?
    open fun on(f: Handler, hooks: RouteHooks = definedExternally): Navigo
    open fun on(map: Any, hooks: RouteHooks = definedExternally): Navigo
    open fun on(path: String, f: Handler, hooks: RouteHooks = definedExternally): Navigo
    open fun on(path: RegExp, f: Handler, hooks: RouteHooks = definedExternally): Navigo
    open fun off(path: String): Navigo
    open fun off(path: RegExp): Navigo
    open fun off(handler: Handler): Navigo
    open fun navigate(to: String, options: NavigateOptions = definedExternally)
    open fun navigateByName(
        name: String,
        data: Any = definedExternally,
        options: NavigateOptions = definedExternally
    ): Boolean

    open fun resolve(
        path: String = definedExternally,
        resolveOptions: ResolveOptions = definedExternally
    ): dynamic /* Boolean | Match */

    open fun destroy()
    open fun notFound(handler: Handler, hooks: RouteHooks = definedExternally): Navigo
    open fun updatePageLinks(): Navigo
    open fun link(path: String): String
    open fun generate(name: String, data: Any = definedExternally, options: GenerateOptions = definedExternally): String
    open fun hooks(hooks: RouteHooks): Navigo
    open fun getLinkPath(link: Any): String
    open fun match(path: String): dynamic /* Boolean | Array<Match> */
    open fun matchLocation(
        path: String,
        currentLocation: String = definedExternally,
        annotatePathWithRoot: Boolean = definedExternally
    ): dynamic /* Boolean | Match */

    open fun matchLocation(
        path: RegExp,
        currentLocation: String = definedExternally,
        annotatePathWithRoot: Boolean = definedExternally
    ): dynamic /* Boolean | Match */

    open fun getCurrentLocation(): Match
    open fun addBeforeHook(route: Route, hookFunction: (done: DoneFunction) -> Unit): () -> Unit
    open fun addBeforeHook(route: String, hookFunction: (done: DoneFunction) -> Unit): () -> Unit
    open fun addAfterHook(route: Route, hookFunction: () -> Unit): () -> Unit
    open fun addAfterHook(route: String, hookFunction: () -> Unit): () -> Unit
    open fun addAlreadyHook(route: Route, hookFunction: () -> Unit): () -> Unit
    open fun addAlreadyHook(route: String, hookFunction: () -> Unit): () -> Unit
    open fun addLeaveHook(route: Route, hookFunction: (done: DoneFunction) -> Unit): () -> Unit
    open fun addLeaveHook(route: String, hookFunction: (done: DoneFunction) -> Unit): () -> Unit
    open fun getRoute(nameOrHandler: String): Route?
    open fun getRoute(nameOrHandler: Handler): Route?
}
