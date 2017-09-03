package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.snabbdom.StringPair

open class Link(label: String, url: String, classes: Set<String> = setOf()) : Container(classes) {
    var label = label
        set(value) {
            field = value
            refresh()
        }
    var url = url
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return kvh("a", arrayOf(label) + childrenVNodes())
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("href" to url)
    }
}
