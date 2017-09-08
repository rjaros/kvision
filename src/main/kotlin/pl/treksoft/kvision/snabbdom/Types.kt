package pl.treksoft.kvision.snabbdom

import com.github.snabbdom.Attrs
import com.github.snabbdom.Classes
import com.github.snabbdom.On
import com.github.snabbdom.Props
import com.github.snabbdom.VNodeData
import com.github.snabbdom.VNodeStyle
import pl.treksoft.kvision.core.Widget

external class Object

fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

@Suppress("UnsafeCastFromDynamic")
private fun vNodeData(): VNodeData = js("({})")

interface SnOn<T> : On {
    var self: T
}

fun snOpt(block: VNodeData.() -> Unit) = (vNodeData()::apply)(block)

@Suppress("UnsafeCastFromDynamic")
internal fun on(widget: Widget): SnOn<Widget> {
    val obj = js("({})")
    obj["self"] = widget
    return obj
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
