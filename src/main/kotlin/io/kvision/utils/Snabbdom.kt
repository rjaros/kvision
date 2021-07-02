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
package io.kvision.utils

import com.github.snabbdom.Attrs
import com.github.snabbdom.Classes
import com.github.snabbdom.Hooks
import com.github.snabbdom.On
import com.github.snabbdom.Props
import com.github.snabbdom.VNodeData
import com.github.snabbdom.VNodeStyle
import com.github.snabbdom.set
import io.kvision.core.StringBoolPair
import io.kvision.core.StringPair
import io.kvision.core.Widget
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import org.w3c.dom.events.Event

/**
 * @suppress
 * Internal function
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun vNodeData(): VNodeData = js("{}")

/**
 * Helper class for defining custom events.
 */
external class KvEvent(type: String, eventInitDict: CustomEventInit = definedExternally) : CustomEvent {
    override val detail: Event
}

/**
 * @suppress
 * Internal interface.
 */
external interface BtOn : On {
    var dragSplitPanel: ((KvEvent) -> Unit)?
    var dragEndSplitPanel: ((KvEvent) -> Unit)?
    var updateModel: ((KvEvent) -> Unit)?
    var fileSelectUpload: ((KvEvent) -> Unit)?
    var fileClearUpload: ((KvEvent) -> Unit)?
    var fileResetUpload: ((KvEvent) -> Unit)?
    var fileBrowseUpload: ((KvEvent) -> Unit)?
    var filePreUpload: ((KvEvent) -> Unit)?
    var resizeWindow: ((KvEvent) -> Unit)?
    var closeWindow: ((KvEvent) -> Unit)?
    var maximizeWindow: ((KvEvent) -> Unit)?
    var minimizeWindow: ((KvEvent) -> Unit)?
    var tabulatorRowClick: ((KvEvent) -> Unit)?
    var tabulatorRowDblClick: ((KvEvent) -> Unit)?
    var tabulatorRowSelectionChanged: ((KvEvent) -> Unit)?
    var tabulatorRowSelected: ((KvEvent) -> Unit)?
    var tabulatorRowDeselected: ((KvEvent) -> Unit)?
    var tabulatorCellClick: ((KvEvent) -> Unit)?
    var tabulatorCellDblClick: ((KvEvent) -> Unit)?
    var tabulatorCellEditing: ((KvEvent) -> Unit)?
    var tabulatorCellEdited: ((KvEvent) -> Unit)?
    var tabulatorCellEditCancelled: ((KvEvent) -> Unit)?
    var tabulatorDataLoading: ((KvEvent) -> Unit)?
    var tabulatorDataLoaded: ((KvEvent) -> Unit)?
    var tabulatorDataEdited: ((KvEvent) -> Unit)?
    var tabChange: ((KvEvent) -> Unit)?
    var tabClosing: ((KvEvent) -> Unit)?
    var tabClosed: ((KvEvent) -> Unit)?
    var onsPrepush: ((KvEvent) -> Unit)?
    var onsPrepop: ((KvEvent) -> Unit)?
    var onsPostpush: ((KvEvent) -> Unit)?
    var onsPostpop: ((KvEvent) -> Unit)?
    var onsInit: ((KvEvent) -> Unit)?
    var onsShow: ((KvEvent) -> Unit)?
    var onsHide: ((KvEvent) -> Unit)?
    var onsDestroy: ((KvEvent) -> Unit)?
    var onsPreopen: ((KvEvent) -> Unit)?
    var onsPreclose: ((KvEvent) -> Unit)?
    var onsPostopen: ((KvEvent) -> Unit)?
    var onsPostclose: ((KvEvent) -> Unit)?
    var onsModechange: ((KvEvent) -> Unit)?
    var onsPrechange: ((KvEvent) -> Unit)?
    var onsPostchange: ((KvEvent) -> Unit)?
    var onsReactive: ((KvEvent) -> Unit)?
    var onsRefresh: ((KvEvent) -> Unit)?
    var onsOverscroll: ((KvEvent) -> Unit)?
    var onsChangestate: ((KvEvent) -> Unit)?
    var onsOpen: ((KvEvent) -> Unit)?
    var onsClose: ((KvEvent) -> Unit)?
    var onsPreshow: ((KvEvent) -> Unit)?
    var onsPrehide: ((KvEvent) -> Unit)?
    var onsPostshow: ((KvEvent) -> Unit)?
    var onsPosthide: ((KvEvent) -> Unit)?
    var dragleft: ((KvEvent) -> Unit)?
    var dragright: ((KvEvent) -> Unit)?
    var dragup: ((KvEvent) -> Unit)?
    var dragdown: ((KvEvent) -> Unit)?
    var gesture: ((KvEvent) -> Unit)?
    var hold: ((KvEvent) -> Unit)?
    var release: ((KvEvent) -> Unit)?
    var swipe: ((KvEvent) -> Unit)?
    var swipeleft: ((KvEvent) -> Unit)?
    var swiperight: ((KvEvent) -> Unit)?
    var swipeup: ((KvEvent) -> Unit)?
    var swipedown: ((KvEvent) -> Unit)?
    var tap: ((KvEvent) -> Unit)?
    var doubletap: ((KvEvent) -> Unit)?
    var touch: ((KvEvent) -> Unit)?
    var transform: ((KvEvent) -> Unit)?
    var transformstart: ((KvEvent) -> Unit)?
    var transformend: ((KvEvent) -> Unit)?
    var pinchin: ((KvEvent) -> Unit)?
    var pinchout: ((KvEvent) -> Unit)?
    var rotate: ((KvEvent) -> Unit)?
}

/**
 * @suppress
 * Internal interface.
 */
external interface SnOn<T> : BtOn {
    var self: T
}

/**
 * Helper function for defining custom event types.
 */
inline fun <T> SnOn<T>.event(name: String, noinline handler: (Event) -> Unit) = set(name, handler)

/**
 * Helper function for creating object parameters for Snabbdom.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun snOpt(noinline block: VNodeData.() -> Unit) = (vNodeData()::apply)(block)

/**
 * @suppress
 * Internal function.
 */
@Suppress("UnsafeCastFromDynamic")
internal fun on(target: Widget): SnOn<Widget> {
    val obj = js("{}")
    obj["self"] = target
    return obj
}

/**
 * @suppress
 * Internal function.
 */
@Suppress("UnsafeCastFromDynamic")
internal fun emptyOn(): SnOn<Widget> {
    return js("{}")
}

@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
internal inline fun hooks(): Hooks {
    return js("{}")
}

/**
 * Helper function for creating style parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snStyle(pairs: List<StringPair>): VNodeStyle {
    return if (pairs.isEmpty()) js("{}") else obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

/**
 * Helper function for creating properties parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snProps(pairs: List<StringPair>): Props {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

/**
 * Helper function for creating classes parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snClasses(pairs: List<StringBoolPair>): Classes {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

/**
 * Helper function for creating classes parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snClasses(classes: Iterable<String>): Classes {
    return obj {
        classes.forEach { this[it] = true }
    }
}

/**
 * Helper function for creating attributes parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snAttrs(pairs: List<StringPair>): Attrs {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snAttrs(pairs: Map<String, String>): Attrs {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}
