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
package pl.treksoft.kvision.utils

import com.github.snabbdom.Attrs
import com.github.snabbdom.Classes
import com.github.snabbdom.Hooks
import com.github.snabbdom.On
import com.github.snabbdom.Props
import com.github.snabbdom.VNodeData
import com.github.snabbdom.VNodeStyle
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import pl.treksoft.jquery.JQueryEventObject
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget

/**
 * JavaScript Object type
 */
external class Object

/**
 * Helper function for creating JavaScript objects.
 */
inline fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

/**
 * Helper function for creating JavaScript objects from dynamic constructors.
 */
@Suppress("UNUSED_VARIABLE")
inline fun <reified T> Any?.createInstance(vararg args: dynamic): T {
    val jsClass = this
    val argsArray = (listOf(null) + args).toTypedArray()
    return js("new (Function.prototype.bind.apply(jsClass, argsArray))").unsafeCast<T>()
}

/**
 * @suppress
 * Internal function
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun vNodeData(): VNodeData = js("{}")

/**
 * @suppress
 * Internal interface.
 */
interface KvJQueryEventObject : JQueryEventObject {
    val clickedIndex: Int
    val width: Int
    val height: Int
}

/**
 * Helper class for defining custom events.
 */
@Suppress("UnsafeCastFromDynamic")
class KvEvent(type: String, eventInitDict: CustomEventInit) : CustomEvent(type, eventInitDict) {
    override val detail: KvJQueryEventObject = js("{}")
}

/**
 * @suppress
 * Internal interface.
 */
interface BtOn : On {
    var showBsDropdown: ((KvEvent) -> Unit)?
    var shownBsDropdown: ((KvEvent) -> Unit)?
    var hideBsDropdown: ((KvEvent) -> Unit)?
    var hiddenBsDropdown: ((KvEvent) -> Unit)?
    var showBsModal: ((KvEvent) -> Unit)?
    var shownBsModal: ((KvEvent) -> Unit)?
    var hideBsModal: ((KvEvent) -> Unit)?
    var hiddenBsModal: ((KvEvent) -> Unit)?
    var dragSplitPanel: ((KvEvent) -> Unit)?
    var dragEndSplitPanel: ((KvEvent) -> Unit)?
    var showBsSelect: ((KvEvent) -> Unit)?
    var shownBsSelect: ((KvEvent) -> Unit)?
    var hideBsSelect: ((KvEvent) -> Unit)?
    var hiddenBsSelect: ((KvEvent) -> Unit)?
    var loadedBsSelect: ((KvEvent) -> Unit)?
    var renderedBsSelect: ((KvEvent) -> Unit)?
    var refreshedBsSelect: ((KvEvent) -> Unit)?
    var changedBsSelect: ((KvEvent) -> Unit)?
    var changeDate: ((KvEvent) -> Unit)?
    var showBsDateTime: ((KvEvent) -> Unit)?
    var hideBsDateTime: ((KvEvent) -> Unit)?
    var onMinBsSpinner: ((KvEvent) -> Unit)?
    var onMaxBsSpinner: ((KvEvent) -> Unit)?
    var updateModel: ((KvEvent) -> Unit)?
    var fileSelectUpload: ((KvEvent) -> Unit)?
    var fileClearUpload: ((KvEvent) -> Unit)?
    var fileResetUpload: ((KvEvent) -> Unit)?
    var fileBrowseUpload: ((KvEvent) -> Unit)?
    var filePreUpload: ((KvEvent) -> Unit)?
    var resizeWindow: ((KvEvent) -> Unit)?
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
}

/**
 * @suppress
 * Internal interface.
 */
interface SnOn<T> : BtOn {
    var self: T
}

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
internal fun on(widget: Widget): SnOn<Widget> {
    val obj = js("{}")
    obj["self"] = widget
    return obj
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
    return obj {
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
 * Helper function for creating attributes parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
inline fun snAttrs(pairs: List<StringPair>): Attrs {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}
