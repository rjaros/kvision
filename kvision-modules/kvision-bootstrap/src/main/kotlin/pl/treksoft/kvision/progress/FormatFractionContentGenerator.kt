package pl.treksoft.kvision.progress

import pl.treksoft.kvision.html.Tag

/**
 * Uses a `Intl.NumberFormat` to format the fraction of a value within bounds. E.g. if value = 2, bounds=[1,10], then
 * the fraction is 0.2 which is formatted using a given `numberFormat`.
 *
 * @param numberFormat the number format to be used. Defaults to formatting as percentage
 */
class FormatFractionContentGenerator(private val numberFormat: Intl.NumberFormat = numberFormat { style = "percent" }) :
    ContentGenerator<Number> {
    override fun generateContent(tag: Tag, value: Number, bounds: Bounds<Number>) {
        tag.content = numberFormat.format(bounds.fraction(value.toDouble()))
    }
}
