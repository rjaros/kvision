@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package io.kvision.navigo

import kotlin.js.RegExp

external interface NavigoHooks {
    val before: ((done: (suppress: Boolean?) -> Unit) -> Unit)? get() = definedExternally
    val after: (() -> Unit)? get() = definedExternally
    val leave: (() -> Unit)? get() = definedExternally
    val already: (() -> Unit)? get() = definedExternally
}

@JsModule("navigo")
@JsNonModule
open external class Navigo(root: String? = definedExternally, useHash: Boolean? = definedExternally, hash: String? = definedExternally) {
    open fun on(rootHandler: () -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(rootHandler: (query: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun notFound(handler: () -> Unit, hooks: NavigoHooks? = definedExternally): Unit = definedExternally
    open fun notFound(handler: (query: String) -> Unit, hooks: NavigoHooks? = definedExternally): Unit = definedExternally
    open fun on(location: String, handler: () -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: String, handler: (parametersObj: Any) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: String, handler: (parametersObj: Any, query: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: RegExp, handler: (param1: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: RegExp, handler: (param1: String, param2: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: RegExp, handler: (param1: String, param2: String, param3: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: RegExp, handler: (param1: String, param2: String, param3: String, param4: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: RegExp, handler: (param1: String, param2: String, param3: String, param4: String, param5: String) -> Unit, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(location: RegExp, handler: Function<Unit>, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun on(routes: Any, hooks: NavigoHooks? = definedExternally): Navigo = definedExternally
    open fun off(handler: Any): Unit = definedExternally
    open fun navigate(path: String, absolute: Boolean? = definedExternally): Unit = definedExternally
    open fun resolve(currentURL: String? = definedExternally): Boolean = definedExternally
    open fun updatePageLinks(): Unit = definedExternally
    open fun generate(path: String, params: Any? = definedExternally): String = definedExternally
    open fun link(path: String): String = definedExternally
    open fun disableIfAPINotAvailable(): Unit = definedExternally
    open fun pause(boolean: Boolean? = definedExternally): Unit = definedExternally
    open fun resume(): Unit = definedExternally
    open fun destroy(): Unit = definedExternally
    open fun lastRouteResolved(): dynamic = definedExternally
    open fun historyAPIUpdateMethod(state: String): Unit = definedExternally
    open fun hooks(hooks: NavigoHooks): Unit = definedExternally
}
