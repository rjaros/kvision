package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.Widget
import kotlin.reflect.KProperty

class AttributeDelegate(private val attributeName: String) {
    operator fun getValue(thisRef: Widget, property: KProperty<*>): String? {
        return thisRef.getAttribute(attributeName)
    }

    operator fun setValue(thisRef: Widget, property: KProperty<*>, value: String?) {
        if (value == null) {
            thisRef.removeAttribute(attributeName)
        } else {
            thisRef.setAttribute(attributeName, value)
        }
    }
}
