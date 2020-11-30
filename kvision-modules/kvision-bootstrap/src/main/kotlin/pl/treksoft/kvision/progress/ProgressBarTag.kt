package pl.treksoft.kvision.progress

import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.utils.perc

/**
 * A base class for the progress bar tag. This class only exposes utility methods in order to set the typical
 * attributes used by the bootstrap progress bar in a type-safe manner.
 */
abstract class ProgressBarTag(classes: Set<String> = setOf()) : Div(classes = classes + "progress-bar") {
    init {
        role = "progressbar"
    }

    var striped by CssClassPresent("progress-bar-striped")
    var animated by CssClassPresent("progress-bar-animated")
    protected var ariaValue by AttributeDelegate("aria-valuenow")
    protected var ariaMin by AttributeDelegate("aria-valuemin")
    protected var ariaMax by AttributeDelegate("aria-valuemax")

    protected fun setFraction(fraction: Double) {
        setPercentage(fraction * 100)
    }

    protected fun setPercentage(percentage: Double) {
        width = percentage.perc
    }
}
