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
fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

@Suppress("UnsafeCastFromDynamic")
private fun vNodeData(): VNodeData = js("({})")

/**
 * @suppress
 * Internal interface.
 */
interface KvJQueryEventObject : JQueryEventObject {
    val clickedIndex: Int
}

/**
 * Helper class for defining custom events.
 */
@Suppress("UnsafeCastFromDynamic")
class KvEvent(type: String, eventInitDict: CustomEventInit) : CustomEvent(type, eventInitDict) {
    override val detail: KvJQueryEventObject = obj {}
}

/**
 * @suppress
 * Internal interface.
 */
interface BtOn : On {
    var showBsDropdown: ((KvEvent) -> kotlin.Unit)?
    var shownBsDropdown: ((KvEvent) -> kotlin.Unit)?
    var hideBsDropdown: ((KvEvent) -> kotlin.Unit)?
    var hiddenBsDropdown: ((KvEvent) -> kotlin.Unit)?
    var showBsModal: ((KvEvent) -> kotlin.Unit)?
    var shownBsModal: ((KvEvent) -> kotlin.Unit)?
    var hideBsModal: ((KvEvent) -> kotlin.Unit)?
    var hiddenBsModal: ((KvEvent) -> kotlin.Unit)?
    var dragSplitPanel: ((KvEvent) -> kotlin.Unit)?
    var dragEndSplitPanel: ((KvEvent) -> kotlin.Unit)?
    var showBsSelect: ((KvEvent) -> kotlin.Unit)?
    var shownBsSelect: ((KvEvent) -> kotlin.Unit)?
    var hideBsSelect: ((KvEvent) -> kotlin.Unit)?
    var hiddenBsSelect: ((KvEvent) -> kotlin.Unit)?
    var loadedBsSelect: ((KvEvent) -> kotlin.Unit)?
    var renderedBsSelect: ((KvEvent) -> kotlin.Unit)?
    var refreshedBsSelect: ((KvEvent) -> kotlin.Unit)?
    var changedBsSelect: ((KvEvent) -> kotlin.Unit)?
    var changeDate: ((KvEvent) -> kotlin.Unit)?
    var showBsDateTime: ((KvEvent) -> kotlin.Unit)?
    var hideBsDateTime: ((KvEvent) -> kotlin.Unit)?
    var onMinBsSpinner: ((KvEvent) -> kotlin.Unit)?
    var onMaxBsSpinner: ((KvEvent) -> kotlin.Unit)?
    var updateModel: ((KvEvent) -> kotlin.Unit)?
    var fileSelectUpload: ((KvEvent) -> kotlin.Unit)?
    var fileClearUpload: ((KvEvent) -> kotlin.Unit)?
    var fileResetUpload: ((KvEvent) -> kotlin.Unit)?
    var fileBrowseUpload: ((KvEvent) -> kotlin.Unit)?
    var filePreUpload: ((KvEvent) -> kotlin.Unit)?
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
fun snOpt(block: VNodeData.() -> Unit) = (vNodeData()::apply)(block)

@Suppress("UnsafeCastFromDynamic")
internal fun on(widget: Widget): SnOn<Widget> {
    val obj = js("({})")
    obj["self"] = widget
    return obj
}

@Suppress("UnsafeCastFromDynamic")
internal fun hooks(): Hooks {
    return js("({})")
}

/**
 * Helper function for creating style parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic")
fun snStyle(pairs: List<StringPair>): VNodeStyle {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

/**
 * Helper function for creating properties parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic")
fun snProps(pairs: List<StringPair>): Props {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

/**
 * Helper function for creating classes parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic")
fun snClasses(pairs: List<StringBoolPair>): Classes {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

/**
 * Helper function for creating attributes parameters for Snabbdom.
 */
@Suppress("UnsafeCastFromDynamic")
fun snAttrs(pairs: List<StringPair>): Attrs {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}
