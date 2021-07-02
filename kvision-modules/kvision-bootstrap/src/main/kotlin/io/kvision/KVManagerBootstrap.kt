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
package io.kvision

import io.kvision.core.Component
import io.kvision.utils.isIE11
import kotlinx.browser.document

internal external object Bootstrap {
    val Tooltip: dynamic
    val Popover: dynamic
    val Modal: dynamic
}

/**
 * Internal singleton object which initializes and configures KVision Bootstrap module.
 */
internal object KVManagerBootstrap {
    internal val bootstrap: Bootstrap

    init {
        document.body?.setAttribute("data-bs-no-jquery", "true")
        @Suppress("UnsafeCastFromDynamic")
        bootstrap = require("bootstrap")
        require("awesome-bootstrap-checkbox")
    }

    private val elementResizeEvent = require("element-resize-event")

    @Suppress("UnsafeCastFromDynamic")
    internal fun setResizeEvent(component: Component, callback: () -> Unit) {
        if (!isIE11()) {
            component.getElement()?.let {
                elementResizeEvent(it, callback)
            }
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun clearResizeEvent(component: Component) {
        if (!isIE11()) {
            if (component.getElement()?.asDynamic()?.__resizeTrigger__?.contentDocument != null) {
                component.getElement()?.let {
                    elementResizeEvent.unbind(it)
                }
            }
        }
    }

    internal fun init() {}
}
