/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2019-present Robert Cronin
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

package io.kvision.pace

import io.kvision.utils.obj
import io.kvision.utils.useModule
import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.get

@JsModule("pace-progressbar")
internal external val paceProgressbar: dynamic

class PaceAjaxOptions(
    trackMethods: Array<dynamic>? = null,
    trackWebSockets: Boolean? = null,
    ignoreURLs: Array<dynamic>? = null
) {
    internal var paceAjaxOptionsJs: dynamic = obj {
        if (trackMethods != null) {
            this.trackMethods = trackMethods
        }
        if (trackWebSockets != null) {
            this.trackWebSockets = trackWebSockets
        }
        if (ignoreURLs != null) {
            this.ignoreURLs = ignoreURLs
        }
    }
}

class PaceElementsOptions(
    minSamples: Int? = null,
    selectors: Array<dynamic>? = null
) {
    internal var paceElementsOptionsJs: dynamic = obj {
        if (minSamples != null) {
            this.minSamples = minSamples
        }
        if (selectors != null) {
            this.selectors = selectors
        }
    }
}

class PaceEventLagOptions(
    minSamples: Int? = null,
    sampleCount: Int? = null,
    lagThreshold: Int? = null
) {
    internal var paceEventLagOptionsJs: dynamic = obj {
        if (minSamples != null) {
            this.minSamples = minSamples
        }
        if (sampleCount != null) {
            this.sampleCount = sampleCount
        }
        if (lagThreshold != null) {
            this.lagThreshold = lagThreshold
        }
    }
}

class PaceOptions(
    ajax: PaceAjaxOptions? = null,
    catchupTime: Int? = null,
    easeFactor: Double? = null,
    elements: PaceElementsOptions? = null,
    eventLag: PaceEventLagOptions? = null,
    ghostTime: Int? = null,
    initialRate: Double? = null,
    maxProgressPerFrame: Int? = null,
    minTime: Int? = null,
    restartOnPushState: Boolean? = null,
    restartOnRequestAfter: Double? = null,
    startOnPageLoad: Boolean? = null,
    target: String? = null,
    manual: Boolean = false
) {
    internal var paceOptionsJs: dynamic = obj {
        if (!manual) {
            if (ajax != null) {
                this.ajax = ajax.paceAjaxOptionsJs
            }
            if (catchupTime != null) {
                this.catchupTime = catchupTime
            }
            if (easeFactor != null) {
                this.easeFactor = easeFactor
            }
            if (elements != null) {
                this.elements = elements.paceElementsOptionsJs
            }
            if (eventLag != null) {
                this.eventLag = eventLag.paceEventLagOptionsJs
            }
            if (ghostTime != null) {
                this.ghostTime = ghostTime
            }
            if (initialRate != null) {
                this.initialRate = initialRate
            }
            if (maxProgressPerFrame != null) {
                this.maxProgressPerFrame = maxProgressPerFrame
            }
            if (minTime != null) {
                this.minTime = minTime
            }
            if (restartOnPushState != null) {
                this.restartOnPushState = restartOnPushState
            }
            if (restartOnRequestAfter != null) {
                this.restartOnRequestAfter = restartOnRequestAfter
            }
            if (startOnPageLoad != null) {
                this.startOnPageLoad = startOnPageLoad
            }
            if (target != null) {
                this.target = target
            }
        } else {
            this.ajax = false
            this.elements = false
            this.document = false
            this.eventLag = false
            this.startOnPageLoad = false
            this.restartOnPushState = false
            this.restartOnRequestAfter = false
        }
    }
}

class Pace {
    companion object {
        fun init(
            @Suppress("UNUSED_PARAMETER") req: dynamic
        ) {
            useModule(paceProgressbar)
        }

        fun setOptions(options: PaceOptions) {
            window.asDynamic().paceOptions = options.paceOptionsJs
        }

        fun on(event: Event, handler: (() -> Unit)? = null, context: List<dynamic>? = null): dynamic {
            return window["Pace"].on(event, handler, context)
        }

        fun start() {
            window["Pace"].start()
        }

        fun stop() {
            window["Pace"].stop()
        }

        fun show() {
            window["Pace"].bar.render()
        }

        fun hide() {
            window["Pace"].stop()
        }
    }
}
