package pl.treksoft.kvision.progress

/**
 * An implementation of the @see ProgressBarTag, which works with plain old numbers
 */
class NumberProgressBarTag(
    private val progress: Progress<Number>,
    initialValue: Number = 0,
    private val contentGenerator: ContentGenerator<Number>,
    classes: Set<String> = setOf(),
) : ProgressBarTag(classes), ProgressBar<Number> {

    override var value: Number = initialValue
        set(value) {
            field = value
            update()
        }

    init {
        progress.bounds.subscribe { update() }
        update()
    }

    private fun update() {
        val bounds = progress.bounds.value
        val fraction = bounds.fraction(value.toDouble())
        setFraction(fraction)
        ariaMax = bounds.max.toString()
        ariaMin = bounds.min.toString()
        ariaValue = value.toString()
        contentGenerator.generateContent(this, value, bounds)
    }
}

fun Progress<Number>.progressNumeric(
    initialValue: Number = 0,
    contentGenerator: ContentGenerator<Number> = ContentGenerator { _, _, _ -> },
    classes: Set<String> = setOf(),
) = NumberProgressBarTag(this, initialValue, contentGenerator, classes).also { add(it) }
