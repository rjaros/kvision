/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.set

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

    /**
     * Text content of the progress bar.
     */
    var content
        get() = indicator.content
        set(value) {
            indicator.content = value
        }

    /**
     * Determines if [content] can contain HTML code.
     */
    var rich
        get() = indicator.rich
        set(value) {
            indicator.rich = value
        }

    /**
     * Text align of the progress bar.
     */
    var align
        get() = indicator.align
        set(value) {
            indicator.align = value
        }

    internal val indicator = ProgressIndicator(progress, min, max, style, striped, animated, content, rich, align)

    init {
        addPrivate(indicator)

        @Suppress("LeakingThis")
        init?.invoke(this)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.progressBar(
    progress: Int, min: Int = DEFAULT_MIN, max: Int = DEFAULT_MAX, style: ProgressBarStyle? = null,
    striped: Boolean = false, animated: Boolean = false,
    content: String? = null, rich: Boolean = false, align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (ProgressBar.() -> Unit)? = null
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
        classes ?: className.set
    ).apply { init?.invoke(this) }
    this.add(progressBar)
    return progressBar
}
