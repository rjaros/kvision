package pl.treksoft.kvision.progress

import pl.treksoft.kvision.html.Tag

fun interface ContentGenerator<T> {
    fun generateContent(tag: Tag, value: T, bounds: Bounds<T>)
}
