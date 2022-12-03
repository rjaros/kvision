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

package io.kvision.core

import io.kvision.BootstrapModule
import io.kvision.utils.createInstance

internal fun Widget.createBsInstance(
    constructor: Bootstrap.() -> dynamic,
    vararg args: dynamic
): dynamic {
    return getElement()?.let { BootstrapModule.bootstrap.constructor().unsafeCast<Any>().createInstance(it, *args) }
}

internal fun Widget.getBsInstance(constructor: Bootstrap.() -> dynamic): dynamic {
    return getElement()?.let { BootstrapModule.bootstrap.constructor().getInstance(it) }
}

/**
 * Enables tooltip for the current widget.
 * @param options tooltip options
 */
fun Widget.enableTooltip(options: TooltipOptions = TooltipOptions()) {
    disablePopover()
    tooltipOptions = options
    createBsInstance(
        { Tooltip },
        options.copy(title = options.title?.let { translate(it) }).toJs()
    )
    if (!tooltipHooksActive) {
        addAfterInsertHook {
            if (tooltipOptions != null) {
                createBsInstance(
                    { Tooltip },
                    tooltipOptions.unsafeCast<TooltipOptions>().copy(title = options.title?.let { translate(it) })
                        .toJs()
                )
            }
        }
        addAfterDestroyHook {
            if (tooltipOptions != null) {
                getBsInstance { Tooltip }?.dispose()
            }
        }
        tooltipHooksActive = true
    }
}

/**
 * Shows tooltip for the current widget.
 */
fun Widget.showTooltip() {
    if (tooltipOptions != null) getBsInstance { Tooltip }?.show()
}

/**
 * Hides tooltip for the current widget.
 */
fun Widget.hideTooltip() {
    if (tooltipOptions != null) getBsInstance { Tooltip }?.hide()
}

/**
 * Disables tooltip for the current widget.
 */
fun Widget.disableTooltip() {
    if (tooltipOptions != null) {
        tooltipOptions = null
        getBsInstance { Tooltip }?.dispose()
    }
}

/**
 * Enables popover for the current widget.
 * @param options popover options
 */
fun Widget.enablePopover(options: PopoverOptions = PopoverOptions()) {
    disableTooltip()
    popoverOptions = options
    createBsInstance(
        { Popover },
        options.copy(title = options.title?.let { translate(it) },
            content = options.content?.let { translate(it) }).toJs()
    )
    if (!popoverHooksActive) {
        addAfterInsertHook {
            if (popoverOptions != null) {
                createBsInstance(
                    { Popover },
                    popoverOptions.unsafeCast<PopoverOptions>().copy(title = options.title?.let { translate(it) },
                        content = options.content?.let { translate(it) }).toJs()
                )
            }
        }
        addAfterDestroyHook {
            if (popoverOptions != null) {
                getBsInstance { Popover }?.dispose()
            }
        }
        popoverHooksActive = true
    }
}

/**
 * Shows popover for the current widget.
 */
fun Widget.showPopover() {
    if (popoverOptions != null) getBsInstance { Popover }?.show()
}

/**
 * Hides popover for the current widget.
 */
fun Widget.hidePopover() {
    if (popoverOptions != null) getBsInstance { Popover }?.hide()
}

/**
 * Disables popover for the current widget.
 */
fun Widget.disablePopover() {
    if (popoverOptions != null) {
        popoverOptions = null
        getBsInstance { Popover }?.dispose()
    }
}
