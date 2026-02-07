@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE")

package io.kvision.jquery

import org.w3c.dom.Document
import org.w3c.dom.DocumentFragment
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.Text
import org.w3c.dom.XMLDocument
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Json

external interface JQueryAjaxSettings {
    var accepts: Any? get() = definedExternally; set(value) = definedExternally
    var async: Boolean? get() = definedExternally; set(value) = definedExternally
    val beforeSend: ((jqXHR: JQueryXHR, settings: JQueryAjaxSettings) -> Any?)? get() = definedExternally
    var cache: Boolean? get() = definedExternally; set(value) = definedExternally
    val complete: ((jqXHR: JQueryXHR, textStatus: String) -> Any?)? get() = definedExternally
    var contents: Json? get() = definedExternally; set(value) = definedExternally
    var contentType: Any? get() = definedExternally; set(value) = definedExternally
    var context: Any? get() = definedExternally; set(value) = definedExternally
    var converters: Json? get() = definedExternally; set(value) = definedExternally
    var crossDomain: Boolean? get() = definedExternally; set(value) = definedExternally
    var data: Any? get() = definedExternally; set(value) = definedExternally
    val dataFilter: ((data: Any, ty: Any) -> Any?)? get() = definedExternally
    var dataType: String? get() = definedExternally; set(value) = definedExternally
    val error: ((jqXHR: JQueryXHR, textStatus: String, errorThrown: String) -> Any?)? get() = definedExternally
    var global: Boolean? get() = definedExternally; set(value) = definedExternally
    var headers: Json? get() = definedExternally; set(value) = definedExternally
    var ifModified: Boolean? get() = definedExternally; set(value) = definedExternally
    var isLocal: Boolean? get() = definedExternally; set(value) = definedExternally
    var jsonp: Any? get() = definedExternally; set(value) = definedExternally
    var jsonpCallback: Any? get() = definedExternally; set(value) = definedExternally
    var method: String? get() = definedExternally; set(value) = definedExternally
    var mimeType: String? get() = definedExternally; set(value) = definedExternally
    var password: String? get() = definedExternally; set(value) = definedExternally
    var processData: Boolean? get() = definedExternally; set(value) = definedExternally
    var scriptCharset: String? get() = definedExternally; set(value) = definedExternally
    var statusCode: Json? get() = definedExternally; set(value) = definedExternally
    val success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? get() = definedExternally
    var timeout: Number? get() = definedExternally; set(value) = definedExternally
    var traditional: Boolean? get() = definedExternally; set(value) = definedExternally
    var type: String? get() = definedExternally; set(value) = definedExternally
    var url: String? get() = definedExternally; set(value) = definedExternally
    var username: String? get() = definedExternally; set(value) = definedExternally
    var xhr: Any? get() = definedExternally; set(value) = definedExternally
    var xhrFields: Json? get() = definedExternally; set(value) = definedExternally
}

external interface JQueryXHR : XMLHttpRequest, JQueryPromise<Any> {
    override fun overrideMimeType(mime: String): Any
    fun abort(statusText: String? = definedExternally /* null */)
    fun <R> then(
        doneCallback: (data: Any, textStatus: String, jqXHR: JQueryXHR) -> R,
        failCallback: ((jqXHR: JQueryXHR, textStatus: String, errorThrown: Any) -> Unit)? = definedExternally /* null */
    ): JQueryPromise<R>

    var responseJSON: Any? get() = definedExternally; set(value) = definedExternally
    fun error(xhr: JQueryXHR, textStatus: String, errorThrown: String)
}

external interface JQueryCallback {
    fun add(callbacks: Function<*>): JQueryCallback
    fun add(callbacks: Array<Function<*>>): JQueryCallback
    fun disable(): JQueryCallback
    fun disabled(): Boolean
    fun empty(): JQueryCallback
    fun fire(vararg arguments: Any): JQueryCallback
    fun fired(): Boolean
    fun fireWith(
        context: Any? = definedExternally /* null */,
        args: Array<Any>? = definedExternally /* null */
    ): JQueryCallback

    fun has(callback: Function<*>): Boolean
    fun lock(): JQueryCallback
    fun locked(): Boolean
    fun remove(callbacks: Function<*>): JQueryCallback
    fun remove(callbacks: Array<Function<*>>): JQueryCallback
}

external interface JQueryGenericPromise<T> {
    fun <U> then(
        doneFilter: (value: T? /*= null*/, values: Any) -> dynamic /* U | JQueryPromise<U> */,
        failFilter: ((reasons: Any) -> Any?)? = definedExternally /* null */,
        progressFilter: ((progression: Any) -> Any?)? = definedExternally /* null */
    ): JQueryPromise<U>

    fun then(
        doneFilter: (value: T? /*= null*/, values: Any) -> Unit,
        failFilter: ((reasons: Any) -> Any?)? = definedExternally /* null */,
        progressFilter: ((progression: Any) -> Any?)? = definedExternally /* null */
    ): JQueryPromise<Unit>
}

external interface JQueryPromiseCallback<T>

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> JQueryPromiseCallback<T>.invoke(value: T? = null, vararg args: Any) {
    asDynamic()(value, args)
}

external interface JQueryPromiseOperator<T, U>

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T, U> JQueryPromiseOperator<T, U>.invoke(
    callback1: JQueryPromiseCallback<T>,
    vararg callbacksN: JQueryPromiseCallback<Any>
): JQueryPromise<U> {
    return asDynamic()(callback1, callbacksN)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T, U> JQueryPromiseOperator<T, U>.invoke(
    callback1: JQueryPromiseCallback<T>,
    vararg callbacksN: Array<JQueryPromiseCallback<Any>>
): JQueryPromise<U> {
    return asDynamic()(callback1, callbacksN)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T, U> JQueryPromiseOperator<T, U>.invoke(
    callback1: Array<JQueryPromiseCallback<T>>,
    vararg callbacksN: JQueryPromiseCallback<Any>
): JQueryPromise<U> {
    return asDynamic()(callback1, callbacksN)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T, U> JQueryPromiseOperator<T, U>.invoke(
    callback1: Array<JQueryPromiseCallback<T>>,
    vararg callbacksN: Array<JQueryPromiseCallback<Any>>
): JQueryPromise<U> {
    return asDynamic()(callback1, callbacksN)
}

external interface JQueryPromise<T> : JQueryGenericPromise<T> {
    fun state(): String
    fun always(
        alwaysCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg alwaysCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryPromise<T>

    fun always(
        alwaysCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg alwaysCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryPromise<T>

    fun always(
        alwaysCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg alwaysCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryPromise<T>

    fun always(
        alwaysCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg alwaysCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryPromise<T>

    fun done(
        doneCallback1: JQueryPromiseCallback<T>? = definedExternally /* null */,
        vararg doneCallbackN: JQueryPromiseCallback<T>
    ): JQueryPromise<T>

    fun done(
        doneCallback1: JQueryPromiseCallback<T>? = definedExternally /* null */,
        vararg doneCallbackN: Array<JQueryPromiseCallback<T>>
    ): JQueryPromise<T>

    fun done(
        doneCallback1: Array<JQueryPromiseCallback<T>>? = definedExternally /* null */,
        vararg doneCallbackN: JQueryPromiseCallback<T>
    ): JQueryPromise<T>

    fun done(
        doneCallback1: Array<JQueryPromiseCallback<T>>? = definedExternally /* null */,
        vararg doneCallbackN: Array<JQueryPromiseCallback<T>>
    ): JQueryPromise<T>

    fun fail(
        failCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg failCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryPromise<T>

    fun fail(
        failCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg failCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryPromise<T>

    fun fail(
        failCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg failCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryPromise<T>

    fun fail(
        failCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg failCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryPromise<T>

    fun progress(
        progressCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg progressCallbackN: JQueryPromiseCallback<Any>
    ): JQueryPromise<T>

    fun progress(
        progressCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg progressCallbackN: Array<JQueryPromiseCallback<Any>>
    ): JQueryPromise<T>

    fun progress(
        progressCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg progressCallbackN: JQueryPromiseCallback<Any>
    ): JQueryPromise<T>

    fun progress(
        progressCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg progressCallbackN: Array<JQueryPromiseCallback<Any>>
    ): JQueryPromise<T>

    fun pipe(
        doneFilter: ((x: Any) -> Any?)? = definedExternally /* null */,
        failFilter: ((x: Any) -> Any?)? = definedExternally /* null */,
        progressFilter: ((x: Any) -> Any?)? = definedExternally /* null */
    ): JQueryPromise<Any>

    fun promise(target: Any? = definedExternally /* null */): JQueryPromise<T>
}

external interface JQueryDeferred<T> : JQueryGenericPromise<T> {
    fun state(): String
    fun always(
        alwaysCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg alwaysCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryDeferred<T>

    fun always(
        alwaysCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg alwaysCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryDeferred<T>

    fun always(
        alwaysCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg alwaysCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryDeferred<T>

    fun always(
        alwaysCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg alwaysCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryDeferred<T>

    fun done(
        doneCallback1: JQueryPromiseCallback<T>? = definedExternally /* null */,
        vararg doneCallbackN: JQueryPromiseCallback<T>
    ): JQueryDeferred<T>

    fun done(
        doneCallback1: JQueryPromiseCallback<T>? = definedExternally /* null */,
        vararg doneCallbackN: Array<JQueryPromiseCallback<T>>
    ): JQueryDeferred<T>

    fun done(
        doneCallback1: Array<JQueryPromiseCallback<T>>? = definedExternally /* null */,
        vararg doneCallbackN: JQueryPromiseCallback<T>
    ): JQueryDeferred<T>

    fun done(
        doneCallback1: Array<JQueryPromiseCallback<T>>? = definedExternally /* null */,
        vararg doneCallbackN: Array<JQueryPromiseCallback<T>>
    ): JQueryDeferred<T>

    fun fail(
        failCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg failCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryDeferred<T>

    fun fail(
        failCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg failCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryDeferred<T>

    fun fail(
        failCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg failCallbacksN: JQueryPromiseCallback<Any>
    ): JQueryDeferred<T>

    fun fail(
        failCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg failCallbacksN: Array<JQueryPromiseCallback<Any>>
    ): JQueryDeferred<T>

    fun progress(
        progressCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg progressCallbackN: JQueryPromiseCallback<Any>
    ): JQueryDeferred<T>

    fun progress(
        progressCallback1: JQueryPromiseCallback<Any>? = definedExternally /* null */,
        vararg progressCallbackN: Array<JQueryPromiseCallback<Any>>
    ): JQueryDeferred<T>

    fun progress(
        progressCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg progressCallbackN: JQueryPromiseCallback<Any>
    ): JQueryDeferred<T>

    fun progress(
        progressCallback1: Array<JQueryPromiseCallback<Any>>? = definedExternally /* null */,
        vararg progressCallbackN: Array<JQueryPromiseCallback<Any>>
    ): JQueryDeferred<T>

    fun notify(value: Any? = definedExternally /* null */, vararg args: Any): JQueryDeferred<T>
    fun notifyWith(context: Any, value: Array<Any>? = definedExternally /* null */): JQueryDeferred<T>
    fun reject(value: Any? = definedExternally /* null */, vararg args: Any): JQueryDeferred<T>
    fun rejectWith(context: Any, value: Array<Any>? = definedExternally /* null */): JQueryDeferred<T>
    fun resolve(value: T? = definedExternally /* null */, vararg args: Any): JQueryDeferred<T>
    fun resolveWith(context: Any, value: Array<T>? = definedExternally /* null */): JQueryDeferred<T>
    fun promise(target: Any? = definedExternally /* null */): JQueryPromise<T>
    fun pipe(
        doneFilter: ((x: Any) -> Any?)? = definedExternally /* null */,
        failFilter: ((x: Any) -> Any?)? = definedExternally /* null */,
        progressFilter: ((x: Any) -> Any?)? = definedExternally /* null */
    ): JQueryPromise<Any>
}

external interface BaseJQueryEventObject : Event {
    override var currentTarget: Element
    var data: Any
    var delegateTarget: Element
    fun isDefaultPrevented(): Boolean
    fun isImmediatePropagationStopped(): Boolean
    fun isPropagationStopped(): Boolean
    var namespace: String
    var originalEvent: Event
    override fun preventDefault(): Any
    var relatedTarget: Element
    var result: Any
    override fun stopImmediatePropagation()
    override fun stopPropagation()
    override var target: Element
    var pageX: Number
    var pageY: Number
    var which: Number
    var metaKey: Boolean
}

external interface JQueryInputEventObject : BaseJQueryEventObject {
    var altKey: Boolean
    var ctrlKey: Boolean
    override var metaKey: Boolean
    var shiftKey: Boolean
}

external interface JQueryMouseEventObject : JQueryInputEventObject {
    var button: Number
    var clientX: Number
    var clientY: Number
    var offsetX: Number
    var offsetY: Number
    override var pageX: Number
    override var pageY: Number
    var screenX: Number
    var screenY: Number
}

external interface JQueryKeyEventObject : JQueryInputEventObject {
    var char: Any
    var charCode: Number
    var key: Any
    var keyCode: Number
}

external interface JQueryEventObject : BaseJQueryEventObject, JQueryInputEventObject, JQueryMouseEventObject,
    JQueryKeyEventObject

external interface JQuerySupport {
    var ajax: Boolean? get() = definedExternally; set(value) = definedExternally
    var boxModel: Boolean? get() = definedExternally; set(value) = definedExternally
    var changeBubbles: Boolean? get() = definedExternally; set(value) = definedExternally
    var checkClone: Boolean? get() = definedExternally; set(value) = definedExternally
    var checkOn: Boolean? get() = definedExternally; set(value) = definedExternally
    var cors: Boolean? get() = definedExternally; set(value) = definedExternally
    var cssFloat: Boolean? get() = definedExternally; set(value) = definedExternally
    var hrefNormalized: Boolean? get() = definedExternally; set(value) = definedExternally
    var htmlSerialize: Boolean? get() = definedExternally; set(value) = definedExternally
    var leadingWhitespace: Boolean? get() = definedExternally; set(value) = definedExternally
    var noCloneChecked: Boolean? get() = definedExternally; set(value) = definedExternally
    var noCloneEvent: Boolean? get() = definedExternally; set(value) = definedExternally
    var opacity: Boolean? get() = definedExternally; set(value) = definedExternally
    var optDisabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var optSelected: Boolean? get() = definedExternally; set(value) = definedExternally
    val scriptEval: (() -> Boolean)? get() = definedExternally
    var style: Boolean? get() = definedExternally; set(value) = definedExternally
    var submitBubbles: Boolean? get() = definedExternally; set(value) = definedExternally
    var tbody: Boolean? get() = definedExternally; set(value) = definedExternally
}

external interface JQueryParam

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryParam.invoke(obj: Any): String {
    return asDynamic()(obj)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryParam.invoke(obj: Any, traditional: Boolean): String {
    return asDynamic()(obj, traditional)
}

external interface JQueryEventConstructor

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryEventConstructor.invoke(name: String, eventProperties: Any? = null): JQueryEventObject {
    return asDynamic()(name, eventProperties)
}

external interface JQueryCoordinates {
    var left: Number
    var top: Number
}

external interface JQuerySerializeArrayElement {
    var name: String
    var value: String
}

external interface JQueryAnimationOptions {
    var duration: Any? get() = definedExternally; set(value) = definedExternally
    var easing: String? get() = definedExternally; set(value) = definedExternally
    var complete: Function<*>? get() = definedExternally; set(value) = definedExternally
    var step: ((now: Number, tween: Any) -> Any?)? get() = definedExternally; set(value) = definedExternally
    var progress: ((animation: JQueryPromise<Any>, progress: Number, remainingMs: Number) -> Any?)? get() = definedExternally; set(value) = definedExternally
    var start: ((animation: JQueryPromise<Any>) -> Any?)? get() = definedExternally; set(value) = definedExternally
    var done: ((animation: JQueryPromise<Any>, jumpedToEnd: Boolean) -> Any?)? get() = definedExternally; set(value) = definedExternally
    var fail: ((animation: JQueryPromise<Any>, jumpedToEnd: Boolean) -> Any?)? get() = definedExternally; set(value) = definedExternally
    var always: ((animation: JQueryPromise<Any>, jumpedToEnd: Boolean) -> Any?)? get() = definedExternally; set(value) = definedExternally
    var queue: Any? get() = definedExternally; set(value) = definedExternally
    var specialEasing: Any? get() = definedExternally; set(value) = definedExternally
}

external interface JQueryEasingFunction

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryEasingFunction.invoke(percent: Number): Number {
    return asDynamic()(percent)
}

external interface JQueryEasingFunctions {
    var linear: JQueryEasingFunction
    var swing: JQueryEasingFunction
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryEasingFunctions.get(name: String): JQueryEasingFunction? = asDynamic()[name]

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryEasingFunctions.set(name: String, value: JQueryEasingFunction) {
    asDynamic()[name] = value
}

external interface `T$0` {
    var slow: Number
    var fast: Number
}

external interface `T$1` {
    var tick: () -> Unit
    var interval: Number
    var stop: () -> Unit
    var speeds: `T$0`
    var off: Boolean
    var step: Any
}

external interface JQueryStatic {
    fun ajax(settings: JQueryAjaxSettings): JQueryXHR
    fun ajax(url: String, settings: JQueryAjaxSettings? = definedExternally /* null */): JQueryXHR
    fun ajaxPrefilter(
        dataTypes: String,
        handler: (opts: Any, originalOpts: JQueryAjaxSettings, jqXHR: JQueryXHR) -> Any?
    )

    fun ajaxPrefilter(handler: (opts: Any, originalOpts: JQueryAjaxSettings, jqXHR: JQueryXHR) -> Any?)
    var ajaxSettings: JQueryAjaxSettings
    fun ajaxSetup(options: JQueryAjaxSettings)
    fun get(
        url: String,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */,
        dataType: String? = definedExternally /* null */
    ): JQueryXHR

    fun get(
        url: String,
        data: Any? = definedExternally /* null */,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */,
        dataType: String? = definedExternally /* null */
    ): JQueryXHR

    fun get(
        url: String,
        data: String? = definedExternally /* null */,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */,
        dataType: String? = definedExternally /* null */
    ): JQueryXHR

    fun get(settings: JQueryAjaxSettings): JQueryXHR
    fun getJSON(
        url: String,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */
    ): JQueryXHR

    fun getJSON(
        url: String,
        data: Any? = definedExternally /* null */,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */
    ): JQueryXHR

    fun getJSON(
        url: String,
        data: String? = definedExternally /* null */,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */
    ): JQueryXHR

    fun getScript(
        url: String,
        success: ((script: String, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */
    ): JQueryXHR

    var param: JQueryParam
    fun post(
        url: String,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */,
        dataType: String? = definedExternally /* null */
    ): JQueryXHR

    fun post(
        url: String,
        data: Any? = definedExternally /* null */,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */,
        dataType: String? = definedExternally /* null */
    ): JQueryXHR

    fun post(
        url: String,
        data: String? = definedExternally /* null */,
        success: ((data: Any, textStatus: String, jqXHR: JQueryXHR) -> Any?)? = definedExternally /* null */,
        dataType: String? = definedExternally /* null */
    ): JQueryXHR

    fun post(settings: JQueryAjaxSettings): JQueryXHR
    fun Callbacks(flags: String? = definedExternally /* null */): JQueryCallback
    fun holdReady(hold: Boolean)

    fun noConflict(removeAll: Boolean? = definedExternally /* null */): JQueryStatic
    fun <T> `when`(vararg deferreds: T): JQueryPromise<T>
    fun <T> `when`(vararg deferreds: JQueryPromise<T>): JQueryPromise<T>
    var cssHooks: Json
    var cssNumber: Any
    fun <T> data(element: Element, key: String, value: T): T
    fun data(element: Element, key: String): Any
    fun data(element: Element): Any
    fun dequeue(element: Element, queueName: String? = definedExternally /* null */)
    fun hasData(element: Element): Boolean
    fun queue(element: Element, queueName: String? = definedExternally /* null */): Array<Any>
    fun queue(element: Element, queueName: String, newQueue: Array<Function<*>>): JQuery
    fun queue(element: Element, queueName: String, callback: Function<*>): JQuery
    fun removeData(element: Element, name: String? = definedExternally /* null */): JQuery
    fun <T> Deferred(beforeStart: ((deferred: JQueryDeferred<T>) -> Any?)? = definedExternally /* null */): JQueryDeferred<T>
    var easing: JQueryEasingFunctions
    var fx: `T$1`
    fun proxy(fnction: (args: Any) -> Any, context: Any, vararg additionalArguments: Any): Any
    fun proxy(context: Any, name: String, vararg additionalArguments: Any): Any
    var Event: JQueryEventConstructor
    fun error(message: Any): JQuery
    var expr: Any
    var fn: Any
    var isReady: Boolean
    var support: JQuerySupport
    fun contains(container: Element, contained: Element): Boolean
    fun <T> each(collection: Array<T>, callback: (indexInArray: Number, valueOfElement: T) -> Any?): Any
    fun each(collection: Any, callback: (indexInArray: Any, valueOfElement: Any) -> Any?): Any
    fun extend(target: Any, object1: Any? = definedExternally /* null */, vararg objectN: Any): Any
    fun extend(deep: Boolean, target: Any, object1: Any? = definedExternally /* null */, vararg objectN: Any): Any
    fun globalEval(code: String): Any
    fun <T> grep(
        array: Array<T>,
        func: (elementOfArray: T? /*= null*/, indexInArray: Number? /*= null*/) -> Boolean,
        invert: Boolean? = definedExternally /* null */
    ): Array<T>

    fun <T> inArray(value: T, array: Array<T>, fromIndex: Number? = definedExternally /* null */): Number
    fun isArray(obj: Any): Boolean
    fun isEmptyObject(obj: Any): Boolean
    fun isFunction(obj: Any): Boolean
    fun isNumeric(value: Any): Boolean
    fun isPlainObject(obj: Any): Boolean
    fun isWindow(obj: Any): Boolean
    fun isXMLDoc(node: Node): Boolean
    fun makeArray(obj: Any): Array<Any>
    fun <T, U> map(
        array: Array<T>,
        callback: (elementOfArray: T? /*= null*/, indexInArray: Number? /*= null*/) -> U
    ): Array<U>

    fun map(arrayOrObject: Any, callback: (value: Any? /*= null*/, indexOrKey: Any? /*= null*/) -> Any?): Any
    fun <T> merge(first: Array<T>, second: Array<T>): Array<T>
    fun noop(): Any
    fun now(): Number
    fun parseJSON(json: String): Any
    fun parseXML(data: String): XMLDocument
    fun trim(str: String): String
    fun type(obj: Any): String
    fun unique(array: Array<Element>): Array<Element>
    fun parseHTML(
        data: String,
        context: HTMLElement? = definedExternally /* null */,
        keepScripts: Boolean? = definedExternally /* null */
    ): Array<Any>

    fun parseHTML(
        data: String,
        context: Document? = definedExternally /* null */,
        keepScripts: Boolean? = definedExternally /* null */
    ): Array<Any>

    fun get(url: String): JQueryXHR
    fun getJSON(url: String): JQueryXHR
    fun post(url: String): JQueryXHR

    fun parseHTML(data: String): Array<Any>
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(selector: String, context: Element): JQuery {
    return asDynamic()(selector, context)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(selector: String, context: JQuery): JQuery {
    return asDynamic()(selector, context)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(element: Element): JQuery {
    return asDynamic()(element)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(elementArray: Array<Element>): JQuery {
    return asDynamic()(elementArray)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(noinline callback: (jQueryAlias: JQueryStatic? /*= null*/) -> Any?): JQuery {
    return asDynamic()(callback)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(`object`: Any?): JQuery {
    return asDynamic()(`object`)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(`object`: JQuery): JQuery {
    return asDynamic()(`object`)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(): JQuery {
    return asDynamic()()
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(html: String, ownerDocument: Document): JQuery {
    return asDynamic()(html, ownerDocument)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(html: String, attributes: Any): JQuery {
    return asDynamic()(html, attributes)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQueryStatic.invoke(selector: String): JQuery {
    return asDynamic()(selector)
}

external interface JQuery {
    fun ajaxComplete(handler: (event: JQueryEventObject, XMLHttpRequest: XMLHttpRequest, ajaxOptions: Any) -> Any?): JQuery
    fun ajaxError(handler: (event: JQueryEventObject, jqXHR: JQueryXHR, ajaxSettings: JQueryAjaxSettings, thrownError: Any) -> Any?): JQuery
    fun ajaxSend(handler: (event: JQueryEventObject, jqXHR: JQueryXHR, ajaxOptions: JQueryAjaxSettings) -> Any?): JQuery
    fun ajaxStart(handler: () -> Any?): JQuery
    fun ajaxStop(handler: () -> Any?): JQuery
    fun ajaxSuccess(handler: (event: JQueryEventObject, XMLHttpRequest: XMLHttpRequest, ajaxOptions: JQueryAjaxSettings) -> Any?): JQuery
    fun load(
        url: String,
        data: String? = definedExternally /* null */,
        complete: ((responseText: String, textStatus: String, XMLHttpRequest: XMLHttpRequest) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun load(
        url: String,
        data: Any? = definedExternally /* null */,
        complete: ((responseText: String, textStatus: String, XMLHttpRequest: XMLHttpRequest) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun serialize(): String
    fun serializeArray(): Array<JQuerySerializeArrayElement>
    fun addClass(className: String): JQuery
    fun addClass(func: (index: Number, className: String) -> String): JQuery
    fun addBack(selector: String? = definedExternally /* null */): JQuery
    fun attr(attributeName: String): String
    fun attr(attributeName: String, value: String): JQuery
    fun attr(attributeName: String, value: Number): JQuery
    fun attr(attributeName: String, func: (index: Number, attr: String) -> dynamic /* String | Number */): JQuery
    fun attr(attributes: Any): JQuery
    fun hasClass(className: String): Boolean
    fun html(): String
    fun html(htmlString: String): JQuery
    fun html(func: (index: Number, oldhtml: String) -> String): JQuery
    fun prop(propertyName: String): Any
    fun prop(propertyName: String, value: String): JQuery
    fun prop(propertyName: String, value: Number): JQuery
    fun prop(propertyName: String, value: Boolean): JQuery
    fun prop(properties: Any): JQuery
    fun prop(propertyName: String, func: (index: Number, oldPropertyValue: Any) -> Any?): JQuery
    fun removeAttr(attributeName: String): JQuery
    fun removeClass(className: String? = definedExternally /* null */): JQuery
    fun removeClass(func: (index: Number, className: String) -> String): JQuery
    fun removeProp(propertyName: String): JQuery
    fun toggleClass(className: String, swtch: Boolean? = definedExternally /* null */): JQuery
    fun toggleClass(swtch: Boolean? = definedExternally /* null */): JQuery
    fun toggleClass(
        func: (index: Number, className: String, swtch: Boolean) -> String,
        swtch: Boolean? = definedExternally /* null */
    ): JQuery

    fun `val`(): Any
    fun `val`(value: String): JQuery
    fun `val`(value: Array<String>): JQuery
    fun `val`(value: Number): JQuery
    fun `val`(func: (index: Number, value: String) -> String): JQuery
    fun css(propertyName: String): String
    fun css(propertyName: String, value: String): JQuery
    fun css(propertyName: String, value: Number): JQuery
    fun css(propertyName: String, value: (index: Number, value: String) -> dynamic /* String | Number */): JQuery
    fun css(properties: Any): JQuery
    fun height(): Number
    fun height(value: Number): JQuery
    fun height(value: String): JQuery
    fun height(func: (index: Number, height: Number) -> dynamic /* Number | String */): JQuery
    fun innerHeight(): Number
    fun innerHeight(height: Number): JQuery
    fun innerHeight(height: String): JQuery
    fun innerWidth(): Number
    fun innerWidth(width: Number): JQuery
    fun innerWidth(width: String): JQuery
    fun offset(): JQueryCoordinates
    fun offset(coordinates: JQueryCoordinates): JQuery
    fun offset(func: (index: Number, coords: JQueryCoordinates) -> JQueryCoordinates): JQuery
    fun outerHeight(includeMargin: Boolean? = definedExternally /* null */): Number
    fun outerHeight(height: Number): JQuery
    fun outerHeight(height: String): JQuery
    fun outerWidth(includeMargin: Boolean? = definedExternally /* null */): Number
    fun outerWidth(width: Number): JQuery
    fun outerWidth(width: String): JQuery
    fun position(): JQueryCoordinates
    fun scrollLeft(): Number
    fun scrollLeft(value: Number): JQuery
    fun scrollTop(): Number
    fun scrollTop(value: Number): JQuery
    fun width(): Number
    fun width(value: Number): JQuery
    fun width(value: String): JQuery
    fun width(func: (index: Number, width: Number) -> dynamic /* Number | String */): JQuery
    fun clearQueue(queueName: String? = definedExternally /* null */): JQuery
    fun data(key: String, value: Any): JQuery
    fun data(key: String): Any
    fun data(obj: Json): JQuery
    fun data(): Any
    fun dequeue(queueName: String? = definedExternally /* null */): JQuery
    fun removeData(name: String): JQuery
    fun removeData(list: Array<String>): JQuery
    fun removeData(): JQuery
    fun promise(
        type: String? = definedExternally /* null */,
        target: Any? = definedExternally /* null */
    ): JQueryPromise<Any>

    fun animate(
        properties: Any,
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun animate(
        properties: Any,
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun animate(
        properties: Any,
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun animate(
        properties: Any,
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun animate(properties: Any, options: JQueryAnimationOptions): JQuery
    fun delay(duration: Number, queueName: String? = definedExternally /* null */): JQuery
    fun fadeIn(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeIn(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeIn(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeIn(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeIn(options: JQueryAnimationOptions): JQuery
    fun fadeOut(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeOut(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeOut(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeOut(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeOut(options: JQueryAnimationOptions): JQuery
    fun fadeTo(duration: String, opacity: Number, complete: Function<*>? = definedExternally /* null */): JQuery
    fun fadeTo(duration: Number, opacity: Number, complete: Function<*>? = definedExternally /* null */): JQuery
    fun fadeTo(
        duration: String,
        opacity: Number,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeTo(
        duration: Number,
        opacity: Number,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeToggle(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeToggle(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeToggle(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeToggle(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun fadeToggle(options: JQueryAnimationOptions): JQuery
    fun finish(queue: String? = definedExternally /* null */): JQuery
    fun hide(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun hide(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun hide(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun hide(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun hide(options: JQueryAnimationOptions): JQuery
    fun show(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun show(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun show(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun show(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun show(options: JQueryAnimationOptions): JQuery
    fun slideDown(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideDown(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideDown(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideDown(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideDown(options: JQueryAnimationOptions): JQuery
    fun slideToggle(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideToggle(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideToggle(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideToggle(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideToggle(options: JQueryAnimationOptions): JQuery
    fun slideUp(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideUp(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideUp(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideUp(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun slideUp(options: JQueryAnimationOptions): JQuery
    fun stop(
        clearQueue: Boolean? = definedExternally /* null */,
        jumpToEnd: Boolean? = definedExternally /* null */
    ): JQuery

    fun stop(
        queue: String? = definedExternally /* null */,
        clearQueue: Boolean? = definedExternally /* null */,
        jumpToEnd: Boolean? = definedExternally /* null */
    ): JQuery

    fun toggle(
        duration: Number? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun toggle(
        duration: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun toggle(
        duration: Number? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun toggle(
        duration: String? = definedExternally /* null */,
        easing: String? = definedExternally /* null */,
        complete: Function<*>? = definedExternally /* null */
    ): JQuery

    fun toggle(options: JQueryAnimationOptions): JQuery
    fun toggle(showOrHide: Boolean): JQuery
    fun bind(eventType: String, eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun bind(eventType: String, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun bind(eventType: String, eventData: Any, preventBubble: Boolean): JQuery
    fun bind(eventType: String, preventBubble: Boolean): JQuery
    fun bind(events: Any): JQuery
    fun blur(): JQuery
    fun blur(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun blur(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun change(): JQuery
    fun change(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun change(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun click(): JQuery
    fun click(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun click(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun contextmenu(): JQuery
    fun contextmenu(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun contextmenu(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun dblclick(): JQuery
    fun dblclick(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun dblclick(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun delegate(selector: Any, eventType: String, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun delegate(
        selector: Any,
        eventType: String,
        eventData: Any,
        handler: (eventObject: JQueryEventObject) -> Any?
    ): JQuery

    fun focus(): JQuery
    fun focus(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun focus(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun focusin(): JQuery
    fun focusin(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun focusin(eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun focusout(): JQuery
    fun focusout(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun focusout(eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun hover(
        handlerIn: (eventObject: JQueryEventObject) -> Any,
        handlerOut: (eventObject: JQueryEventObject) -> Any?
    ): JQuery

    fun hover(handlerInOut: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun keydown(): JQuery
    fun keydown(handler: (eventObject: JQueryKeyEventObject) -> Any?): JQuery
    fun keydown(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryKeyEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun keypress(): JQuery
    fun keypress(handler: (eventObject: JQueryKeyEventObject) -> Any?): JQuery
    fun keypress(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryKeyEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun keyup(): JQuery
    fun keyup(handler: (eventObject: JQueryKeyEventObject) -> Any?): JQuery
    fun keyup(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryKeyEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun load(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun load(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun mousedown(): JQuery
    fun mousedown(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mousedown(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseenter(): JQuery
    fun mouseenter(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseenter(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseleave(): JQuery
    fun mouseleave(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseleave(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mousemove(): JQuery
    fun mousemove(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mousemove(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseout(): JQuery
    fun mouseout(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseout(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseover(): JQuery
    fun mouseover(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseover(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseup(): JQuery
    fun mouseup(handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun mouseup(eventData: Any, handler: (eventObject: JQueryMouseEventObject) -> Any?): JQuery
    fun off(): JQuery
    fun off(
        events: String,
        selector: String? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun off(events: String, handler: (eventObject: JQueryEventObject, args: Any) -> Any?): JQuery
    fun off(events: String, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun off(events: Json, selector: String? = definedExternally /* null */): JQuery
    fun on(events: String, handler: (eventObject: JQueryEventObject, args: Any) -> Any?): JQuery
    fun on(events: String, data: Any, handler: (eventObject: JQueryEventObject, args: Any) -> Any?): JQuery
    fun on(events: String, selector: String, handler: (eventObject: JQueryEventObject, eventData: Any) -> Any?): JQuery
    fun on(
        events: String,
        selector: String,
        data: Any,
        handler: (eventObject: JQueryEventObject, eventData: Any) -> Any?
    ): JQuery

    fun on(
        events: Json,
        selector: String? = definedExternally /* null */,
        data: Any? = definedExternally /* null */
    ): JQuery

    fun on(events: Json, data: Any? = definedExternally /* null */): JQuery
    fun one(events: String, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun one(events: String, data: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun one(events: String, selector: String, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun one(events: String, selector: String, data: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun one(
        events: Json,
        selector: String? = definedExternally /* null */,
        data: Any? = definedExternally /* null */
    ): JQuery

    fun one(events: Json, data: Any? = definedExternally /* null */): JQuery
    fun ready(handler: (jQueryAlias: JQueryStatic? /*= null*/) -> Any?): JQuery
    fun resize(): JQuery
    fun resize(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun resize(eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun scroll(): JQuery
    fun scroll(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun scroll(eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun select(): JQuery
    fun select(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun select(eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun submit(): JQuery
    fun submit(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun submit(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun trigger(eventType: String, extraParameters: Array<Any>? = definedExternally /* null */): JQuery
    fun trigger(eventType: String, extraParameters: Any? = definedExternally /* null */): JQuery
    fun trigger(event: JQueryEventObject, extraParameters: Array<Any>? = definedExternally /* null */): JQuery
    fun trigger(event: JQueryEventObject, extraParameters: Any? = definedExternally /* null */): JQuery
    fun triggerHandler(eventType: String, vararg extraParameters: Any): Any
    fun triggerHandler(event: JQueryEventObject, vararg extraParameters: Any): Any
    fun unbind(
        eventType: String? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun unbind(eventType: String, fls: Boolean): JQuery
    fun unbind(evt: Any): JQuery
    fun undelegate(): JQuery
    fun undelegate(
        selector: String,
        eventType: String,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    fun undelegate(selector: String, events: Any): JQuery
    fun undelegate(namespace: String): JQuery
    fun unload(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun unload(
        eventData: Any? = definedExternally /* null */,
        handler: ((eventObject: JQueryEventObject) -> Any?)? = definedExternally /* null */
    ): JQuery

    var context: Element
    var jquery: String
    fun error(handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun error(eventData: Any, handler: (eventObject: JQueryEventObject) -> Any?): JQuery
    fun pushStack(elements: Array<Any>): JQuery
    fun pushStack(elements: Array<Any>, name: String, arguments: Array<Any>): JQuery
    fun after(content1: JQuery, vararg content2: Any): JQuery
    fun after(content1: Array<Any>, vararg content2: Any): JQuery
    fun after(content1: Element, vararg content2: Any): JQuery
    fun after(content1: DocumentFragment, vararg content2: Any): JQuery
    fun after(content1: Text, vararg content2: Any): JQuery
    fun after(content1: String, vararg content2: Any): JQuery
    fun after(func: (index: Number, html: String) -> dynamic /* String | Element | JQuery */): JQuery
    fun append(content1: JQuery, vararg content2: Any): JQuery
    fun append(content1: Array<Any>, vararg content2: Any): JQuery
    fun append(content1: Element, vararg content2: Any): JQuery
    fun append(content1: DocumentFragment, vararg content2: Any): JQuery
    fun append(content1: Text, vararg content2: Any): JQuery
    fun append(content1: String, vararg content2: Any): JQuery
    fun append(func: (index: Number, html: String) -> dynamic /* String | Element | JQuery */): JQuery
    fun appendTo(target: JQuery): JQuery
    fun appendTo(target: Array<Any>): JQuery
    fun appendTo(target: Element): JQuery
    fun appendTo(target: String): JQuery
    fun before(content1: JQuery, vararg content2: Any): JQuery
    fun before(content1: Array<Any>, vararg content2: Any): JQuery
    fun before(content1: Element, vararg content2: Any): JQuery
    fun before(content1: DocumentFragment, vararg content2: Any): JQuery
    fun before(content1: Text, vararg content2: Any): JQuery
    fun before(content1: String, vararg content2: Any): JQuery
    fun before(func: (index: Number, html: String) -> dynamic /* String | Element | JQuery */): JQuery
    fun clone(
        withDataAndEvents: Boolean? = definedExternally /* null */,
        deepWithDataAndEvents: Boolean? = definedExternally /* null */
    ): JQuery

    fun detach(selector: String? = definedExternally /* null */): JQuery
    fun empty(): JQuery
    fun insertAfter(target: JQuery): JQuery
    fun insertAfter(target: Array<Any>): JQuery
    fun insertAfter(target: Element): JQuery
    fun insertAfter(target: Text): JQuery
    fun insertAfter(target: String): JQuery
    fun insertBefore(target: JQuery): JQuery
    fun insertBefore(target: Array<Any>): JQuery
    fun insertBefore(target: Element): JQuery
    fun insertBefore(target: Text): JQuery
    fun insertBefore(target: String): JQuery
    fun prepend(content1: JQuery, vararg content2: Any): JQuery
    fun prepend(content1: Array<Any>, vararg content2: Any): JQuery
    fun prepend(content1: Element, vararg content2: Any): JQuery
    fun prepend(content1: DocumentFragment, vararg content2: Any): JQuery
    fun prepend(content1: Text, vararg content2: Any): JQuery
    fun prepend(content1: String, vararg content2: Any): JQuery
    fun prepend(func: (index: Number, html: String) -> dynamic /* String | Element | JQuery */): JQuery
    fun prependTo(target: JQuery): JQuery
    fun prependTo(target: Array<Any>): JQuery
    fun prependTo(target: Element): JQuery
    fun prependTo(target: String): JQuery
    fun remove(selector: String? = definedExternally /* null */): JQuery
    fun replaceAll(target: JQuery): JQuery
    fun replaceAll(target: Array<Any>): JQuery
    fun replaceAll(target: Element): JQuery
    fun replaceAll(target: String): JQuery
    fun replaceWith(newContent: JQuery): JQuery
    fun replaceWith(newContent: Array<Any>): JQuery
    fun replaceWith(newContent: Element): JQuery
    fun replaceWith(newContent: Text): JQuery
    fun replaceWith(newContent: String): JQuery
    fun replaceWith(func: () -> dynamic /* Element | JQuery */): JQuery
    fun text(): String
    fun text(text: String): JQuery
    fun text(text: Number): JQuery
    fun text(text: Boolean): JQuery
    fun text(func: (index: Number, text: String) -> String): JQuery
    fun toArray(): Array<HTMLElement>
    fun unwrap(): JQuery
    fun wrap(wrappingElement: JQuery): JQuery
    fun wrap(wrappingElement: Element): JQuery
    fun wrap(wrappingElement: String): JQuery
    fun wrap(func: (index: Number) -> dynamic /* String | JQuery */): JQuery
    fun wrapAll(wrappingElement: JQuery): JQuery
    fun wrapAll(wrappingElement: Element): JQuery
    fun wrapAll(wrappingElement: String): JQuery
    fun wrapAll(func: (index: Number) -> String): JQuery
    fun wrapInner(wrappingElement: JQuery): JQuery
    fun wrapInner(wrappingElement: Element): JQuery
    fun wrapInner(wrappingElement: String): JQuery
    fun wrapInner(func: (index: Number) -> String): JQuery
    fun each(func: (index: Number, elem: Element) -> Any?): JQuery
    fun get(): Array<HTMLElement>
    fun index(): Number
    fun index(selector: String): Number
    fun index(selector: JQuery): Number
    fun index(selector: Element): Number
    var length: Number
    var selector: String

    fun add(selector: String, context: Element? = definedExternally /* null */): JQuery
    fun add(vararg elements: Element): JQuery
    fun add(html: String): JQuery
    fun add(obj: JQuery): JQuery
    fun children(selector: String? = definedExternally /* null */): JQuery
    fun closest(selector: String): JQuery
    fun closest(selector: String, context: Element? = definedExternally /* null */): JQuery
    fun closest(obj: JQuery): JQuery
    fun closest(element: Element): JQuery
    fun closest(selectors: Any, context: Element? = definedExternally /* null */): Array<Any>
    fun contents(): JQuery
    fun end(): JQuery
    fun eq(index: Number): JQuery
    fun filter(selector: String): JQuery
    fun filter(func: (index: Number, element: Element) -> Any?): JQuery
    fun filter(element: Element): JQuery
    fun filter(obj: JQuery): JQuery
    fun find(selector: String): JQuery
    fun find(element: Element): JQuery
    fun find(obj: JQuery): JQuery
    fun first(): JQuery
    fun has(selector: String): JQuery
    fun has(contained: Element): JQuery
    fun `is`(selector: String): Boolean
    fun `is`(func: (index: Number, element: Element) -> Boolean): Boolean
    fun `is`(obj: JQuery): Boolean
    fun `is`(elements: Any): Boolean
    fun last(): JQuery
    fun map(callback: (index: Number, domElement: Element) -> Any?): JQuery
    fun next(selector: String? = definedExternally /* null */): JQuery
    fun nextAll(selector: String? = definedExternally /* null */): JQuery
    fun nextUntil(
        selector: String? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun nextUntil(
        element: Element? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun nextUntil(obj: JQuery? = definedExternally /* null */, filter: String? = definedExternally /* null */): JQuery
    fun not(selector: String): JQuery
    fun not(func: (index: Number, element: Element) -> Boolean): JQuery
    fun not(elements: Element): JQuery
    fun not(elements: Array<Element>): JQuery
    fun not(obj: JQuery): JQuery
    fun offsetParent(): JQuery
    fun parent(selector: String? = definedExternally /* null */): JQuery
    fun parents(selector: String? = definedExternally /* null */): JQuery
    fun parentsUntil(
        selector: String? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun parentsUntil(
        element: Element? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun parentsUntil(
        obj: JQuery? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun prev(selector: String? = definedExternally /* null */): JQuery
    fun prevAll(selector: String? = definedExternally /* null */): JQuery
    fun prevUntil(
        selector: String? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun prevUntil(
        element: Element? = definedExternally /* null */,
        filter: String? = definedExternally /* null */
    ): JQuery

    fun prevUntil(obj: JQuery? = definedExternally /* null */, filter: String? = definedExternally /* null */): JQuery
    fun siblings(selector: String? = definedExternally /* null */): JQuery
    fun slice(start: Number, end: Number? = definedExternally /* null */): JQuery
    fun queue(queueName: String? = definedExternally /* null */): Array<Any>
    fun queue(newQueue: Array<Function<*>>): JQuery
    fun queue(callback: Function<*>): JQuery
    fun queue(queueName: String, newQueue: Array<Function<*>>): JQuery
    fun queue(queueName: String, callback: Function<*>): JQuery
    fun load(url: String): JQuery
    fun animate(properties: Any): JQuery
    fun fadeIn(): JQuery
    fun fadeOut(): JQuery
    fun fadeTo(duration: String, opacity: Number): JQuery
    fun fadeTo(duration: Number, opacity: Number): JQuery
    fun fadeToggle(): JQuery
    fun hide(): JQuery
    fun show(): JQuery
    fun slideDown(): JQuery
    fun slideToggle(): JQuery
    fun slideUp(): JQuery
    fun stop(): JQuery
    fun toggle(): JQuery
    fun on(events: Json): JQuery
    fun one(events: Json): JQuery
    fun trigger(eventType: String): JQuery
    fun trigger(event: JQueryEventObject): JQuery
    fun nextUntil(): JQuery
    fun parentsUntil(): JQuery
    fun prevUntil(): JQuery
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQuery.get(index: String): Any? = asDynamic()[index]

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQuery.set(index: String, value: Any) {
    asDynamic()[index] = value
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQuery.get(index: Number): HTMLElement? = asDynamic()[index]

@Suppress("NOTHING_TO_INLINE")
inline operator fun JQuery.set(index: Number, value: HTMLElement) {
    asDynamic()[index] = value
}

@JsModule("jquery")
@JsNonModule
external val jQuery: JQueryStatic = definedExternally
external val `$`: JQueryStatic = definedExternally

object Factory {
    fun getInstance(): JQueryStatic = jQuery
}
