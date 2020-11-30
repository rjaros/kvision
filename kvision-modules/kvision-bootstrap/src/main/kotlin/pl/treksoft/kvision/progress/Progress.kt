package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableValue

/**
 * The Bootstrap progress
 *
 * @param T the type that describes the progress, typically @see Number
 */
class Progress<T>(
    bounds: Bounds<T>,
    classes: Set<String> = setOf(),
    init: (Progress<T>.() -> Unit)? = null
) :
    SimplePanel(classes + "progress") {

    val bounds = ObservableValue(bounds)

    fun setBounds(min: T, max: T) {
        bounds.value = Bounds(min, max)
    }

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}


fun <T> Container.progress(
    bounds: Bounds<T>,
    classes: Set<String> = setOf(),
    init: (Progress<T>.() -> Unit)?
): Progress<T> = Progress(bounds, classes, init).also { add(it) }

fun Container.progress(
    min: Number = 0,
    max: Number = 100,
    classes: Set<String> = setOf(),
    init: Progress<Number>.() -> Unit = {}
): Progress<Number> = Progress(Bounds(min, max), classes, init).also { add(it) }
