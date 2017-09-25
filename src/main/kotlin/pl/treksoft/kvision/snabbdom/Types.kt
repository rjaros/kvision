package pl.treksoft.kvision.snabbdom

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
import pl.treksoft.kvision.core.Widget

external class Object

fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

@Suppress("UnsafeCastFromDynamic")
private fun vNodeData(): VNodeData = js("({})")

class KvEvent(type: String, eventInitDict: CustomEventInit) : CustomEvent(type, eventInitDict) {
    override val detail: JQueryEventObject = obj({})
}

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
}

interface SnOn<T> : BtOn {
    var self: T
}

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

typealias StringPair = Pair<String, String>
typealias StringBoolPair = Pair<String, Boolean>

@Suppress("UnsafeCastFromDynamic")
fun snStyle(pairs: List<StringPair>): VNodeStyle {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic")
fun snProps(pairs: List<StringPair>): Props {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic")
fun snClasses(pairs: List<StringBoolPair>): Classes {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic")
fun snAttrs(pairs: List<StringPair>): Attrs {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}
