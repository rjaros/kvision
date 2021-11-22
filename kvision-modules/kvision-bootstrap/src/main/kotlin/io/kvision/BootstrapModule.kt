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

import io.kvision.core.Bootstrap
import io.kvision.core.Component
import kotlinx.browser.document

/**
 * Initializer for Bootstrap module.
 */
object BootstrapModule : ModuleInitializer {

    internal val bootstrap: Bootstrap

    init {
        document.body?.setAttribute("data-bs-no-jquery", "true")
        @Suppress("UnsafeCastFromDynamic")
        bootstrap = require("bootstrap")
    }

    private val elementResizeEvent = require("element-resize-event")

    @Suppress("UnsafeCastFromDynamic")
    internal fun setResizeEvent(component: Component, callback: () -> Unit) {
        component.getElement()?.let {
            elementResizeEvent(it, callback)
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    internal fun clearResizeEvent(component: Component) {
        if (component.getElement()?.asDynamic()?.__resizeTrigger__?.contentDocument != null) {
            component.getElement()?.let {
                elementResizeEvent.unbind(it)
            }
        }
    }

    override fun initialize() {
        require("awesome-bootstrap-checkbox")
    }
}
