package pl.treksoft.kvision.snabbdom

import com.github.snabbdom.*
import pl.treksoft.kvision.core.Widget

external class Object

fun obj(init: dynamic.() -> Unit): dynamic {
    return (Object()).apply(init)
}

@Suppress("UnsafeCastFromDynamic")
private fun VNodeData(): VNodeData = js("({})")

interface SnOn<T> : On {
    var self: T
}

fun snOpt(block: VNodeData.() -> Unit) = (VNodeData()::apply)(block)

@Suppress("UnsafeCastFromDynamic")
internal fun On(widget: Widget): SnOn<Widget> {
    val obj = js("({})")
    obj["self"] = widget
    return obj
}

typealias StringPair = Pair<String, String>
typealias StringBoolPair = Pair<String, Boolean>

@Suppress("UnsafeCastFromDynamic")
fun snStyle(vararg pairs: StringPair): VNodeStyle {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic")
fun snProps(vararg pairs: StringPair): Props {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic")
fun snClasses(vararg pairs: StringBoolPair): Classes {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}

@Suppress("UnsafeCastFromDynamic")
fun snAttrs(vararg pairs: StringPair): Attrs {
    return obj {
        pairs.forEach { (key, value) -> this[key] = value }
    }
}
