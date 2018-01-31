package pl.treksoft.kvision.data

import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface DataComponent {
    var container: DataUpdatable?

    fun <T> obs(initialValue: T): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            container?.update()
        }
    }
}

open class BaseDataComponent : DataComponent {
    override var container: DataUpdatable? = null
}
