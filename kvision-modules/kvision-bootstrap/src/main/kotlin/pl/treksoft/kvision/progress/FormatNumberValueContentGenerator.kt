package com.example.progress

import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.progress.Bounds
import pl.treksoft.kvision.progress.ContentGenerator
import pl.treksoft.kvision.progress.Intl

/**
 * Uses a `Intl.NumberFormat` to format a value.
 *
 * @param numberFormat the format to be used. Defaults to a system default formatter
 */
class FormatNumberValueContentGenerator(private val numberFormat: Intl.NumberFormat = Intl.NumberFormat()) :
    ContentGenerator<Number> {
    override fun generateContent(tag: Tag, value: Number, bounds: Bounds<Number>) {
        tag.content = numberFormat.format(value)
    }
}
