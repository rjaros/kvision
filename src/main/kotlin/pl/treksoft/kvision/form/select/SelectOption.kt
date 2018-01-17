package pl.treksoft.kvision.form.select

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringPair

open class SelectOption(
    value: String? = null, label: String? = null, subtext: String? = null, icon: String? = null,
    divider: Boolean = false, disabled: Boolean = false,
    classes: Set<String> = setOf()
) : Widget(classes) {

    var value: String? = value
        set(value) {
            field = value
            refresh()
        }

    var label: String? = label
        set(value) {
            field = value
            refresh()
        }

    var subtext: String? = subtext
        set(value) {
            field = value
            refresh()
        }

    var icon: String? = icon
        set(value) {
            field = value
            refresh()
        }

    var divider: Boolean = divider
        set(value) {
            field = value
            refresh()
        }

    var disabled: Boolean = disabled
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return if (!divider) {
            kvh("option", arrayOf(label ?: value))
        } else {
            kvh("option")
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        if (!divider) {
            value?.let {
                sn.add("value" to it)
            }
            subtext?.let {
                sn.add("data-subtext" to it)
            }
            icon?.let {
                if (it.startsWith("fa-")) {
                    sn.add("data-icon" to "fa $it")
                } else {
                    sn.add("data-icon" to "glyphicon-$it")
                }
            }
            if (disabled) {
                sn.add("disabled" to "true")
            }
        } else {
            sn.add("data-divider" to "true")
        }
        return sn
    }
}
