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

package pl.treksoft.kvision.pace

import org.w3c.dom.events.Event
import org.w3c.dom.get
import pl.treksoft.kvision.require
import pl.treksoft.kvision.utils.obj
import kotlin.browser.window

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
    target: String? = null
) {
    internal var paceOptionsJs: dynamic = obj {
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
    }
}

enum class PaceColor(var paceColorString: String) {
    Black("black"),
    Blue("blue"),
    Green("green"),
    Orange("orange"),
    Pink("pink"),
    Purple("purple"),
    Red("red"),
    Silver("silver"),
    White("white"),
    Yellow("yellow")
}

enum class PaceTheme(var paceThemeString: String) {
    BarberShop("barber-shop"),
    BigCounter("big-counter"),
    Bounce("bounce"),
    CenterAtom("center-atom"),
    CenterCircle("center-circle"),
    CenterRadar("center-radar"),
    CenterSimple("center-simple"),
    CornerIndicator("corner-indicator"),
    FillLeft("fill-left"),
    Flash("flash"),
    FlatTop("flat-top"),
    LoadingBar("loading-bar"),
    MacOSX("mac-osx"),
    Material("material"),
    Minimal("minimal")
}

class Pace {
    companion object {
        fun init() {
            setNewTheme(PaceColor.Blue, PaceTheme.Flash)
        }

        fun setNewTheme(color: PaceColor, theme: PaceTheme) {
            require("pace-progressbar/themes/${color.paceColorString}/pace-theme-${theme.paceThemeString}.css")
        }

        fun setOptions(options: PaceOptions) {
            window["Pace"].options = options.paceOptionsJs
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
    }
}
