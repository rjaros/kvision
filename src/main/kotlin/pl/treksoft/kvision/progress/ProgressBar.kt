/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.panel.SimplePanel

/**
 * The Bootstrap progress bar.
 *
 * @constructor
 * @param progress the current progress
 * @param min the minimal progress
 * @param max the maximal progress
 * @param style the style of the progress bar
 * @param striped determines if the progress bar is striped
 * @param animated determines if the progress bar is animated
 * @param content element text
 * @param rich determines if content can contain HTML code
 * @param align content align
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class ProgressBar(
    progress: Int, min: Int = DEFAULT_MIN, max: Int = DEFAULT_MAX, style: ProgressBarStyle? = null,
    striped: Boolean = false, animated: Boolean = false, content: String? = null,
    rich: Boolean = false, align: Align? = null,
    classes: Set<String> = setOf(), init: (ProgressBar.() -> Unit)? = null
) :
    SimplePanel(classes + "progress") {

    /**
     * The current progress.
     */
    var progress
        get() = indicator.progress
        set(value) {
            indicator.progress = value
        }
    /**
     * The minimal progress.
     */
    var min
        get() = indicator.min
        set(value) {
            indicator.min = value
        }
    /**
     * The maximal progress.
     */
    var max
        get() = indicator.max
        set(value) {
            indicator.max = value
        }
    /**
     * The style of the progress bar.
     */
    var style
        get() = indicator.style
        set(value) {
            indicator.style = value
        }
    /**
     * Determines if the progress bar is striped.
     */
    var striped
        get() = indicator.striped
        set(value) {
            indicator.striped = value
        }
    /**
     * Determines if the progress bar is animated.
     */
    var animated
        get() = indicator.animated
        set(value) {
            indicator.animated = value
        }

    internal val indicator = ProgressIndicator(progress, min, max, style, striped, animated, content, rich, align)

    init {
        addInternal(indicator)

        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.progressBar(
            progress: Int, min: Int = DEFAULT_MIN, max: Int = DEFAULT_MAX, style: ProgressBarStyle? = null,
            striped: Boolean = false, animated: Boolean = false,
            content: String? = null, rich: Boolean = false, align: Align? = null,
            classes: Set<String> = setOf(), init: (ProgressBar.() -> Unit)? = null
        ): ProgressBar {
            val progressBar = ProgressBar(
                progress,
                min,
                max,
                style,
                striped,
                animated,
                content,
                rich,
                align,
                classes
            ).apply { init?.invoke(this) }
            this.add(progressBar)
            return progressBar
        }
    }
}
