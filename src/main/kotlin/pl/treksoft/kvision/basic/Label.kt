package pl.treksoft.kvision.basic

import pl.treksoft.kvision.html.InTag
import pl.treksoft.kvision.html.INTAG

open class Label(text: String, rich: Boolean = false) : InTag(INTAG.SPAN, text, rich) {
}