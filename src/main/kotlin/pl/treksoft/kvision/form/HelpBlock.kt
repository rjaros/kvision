package pl.treksoft.kvision.form

import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

open class HelpBlock(text: String? = null, rich: Boolean = false) : Tag(
    TAG.SPAN, text, rich,
    classes = setOf("help-block", "small")
)
