package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringPair

open class Link(
    label: String, url: String, icon: String? = null, image: ResString? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {
    private var label = label
        set(value) {
            field = value
            refresh()
        }
    private var url = url
        set(value) {
            field = value
            refresh()
        }
    private var icon = icon
        set(value) {
            field = value
            refresh()
        }
    private var image = image
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
