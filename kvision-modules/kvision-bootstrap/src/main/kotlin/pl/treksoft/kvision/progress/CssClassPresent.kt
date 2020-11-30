package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.Widget
import kotlin.reflect.KProperty

class CssClassPresent(private val className: String) {
    operator fun getValue(thisRef: Widget, property: KProperty<*>): Boolean {
        return thisRef.hasCssClass(className)
    }

    operator fun setValue(thisRef: Widget, property: KProperty<*>, value: Boolean) {
        if (value) {
            thisRef.addCssClass(className)
        } else {
            thisRef.removeCssClass(className)
        }
    }
}
