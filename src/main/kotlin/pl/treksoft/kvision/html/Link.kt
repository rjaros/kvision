package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.snabbdom.StringPair

open class Link(label: String, url: String, icon: String? = null, image: ResString? = null,
                classes: Set<String> = setOf()) : Container(classes) {
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
    var icon = icon
        set(value) {
            field = value
            refresh()
        }
    var image = image
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        val t = createLabelWithIcon(label, icon, image)
        return kvh("a", t + childrenVNodes())
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("href" to url)
    }
}
