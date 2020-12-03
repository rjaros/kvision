package pl.treksoft.kvision.core

/**
 * A cache that behaves like a resettable `Lazy`: It generates a value from a given initializer lazily, however that
 * cache can be cleared, so that the value will be regenerated when queried next
 */
class LazyCache<T : Any>(val initializer: () -> T) {
    val value: T
        get() = _value ?: initializer().also { _value = it }

    private var _value: T? = null

    fun clear() {
        _value = null
    }
}
