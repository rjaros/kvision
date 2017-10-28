package pl.treksoft.kvision.data

import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class DataComponent {
    var container: DataUpdatable? = null

    fun <T> obs(initialValue: T): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            container?.update()
        }
    }
}
