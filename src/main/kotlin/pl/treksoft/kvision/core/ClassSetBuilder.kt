package pl.treksoft.kvision.core

/**
 * A builder in order to create a set of CSS-classes
 */
interface ClassSetBuilder {
    fun add(value: String)
    fun addAll(values: Collection<String>)
}

internal class ClassSetBuilderImpl : ClassSetBuilder {
    val classes: Set<String>
        get() = HashSet(_classes)

    private val _classes: MutableSet<String> = HashSet()

    override fun add(value: String) {
        _classes.add(value)
    }

    override fun addAll(values: Collection<String>) {
        _classes.addAll(values)
    }
}

fun buildClassSet(delegate: (builder: ClassSetBuilder) -> Unit): Set<String> =
    ClassSetBuilderImpl().also { delegate(it) }.classes
