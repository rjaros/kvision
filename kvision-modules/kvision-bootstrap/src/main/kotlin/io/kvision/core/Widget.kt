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

/**
 * Enables tooltip for the current widget.
 * @param options tooltip options
 * @return current widget
 */
fun Widget.enableTooltip(options: TooltipOptions = TooltipOptions()): Widget {
    tooltipOptions = options
    getElementJQueryD()?.tooltip(
        options.copy(title = options.title?.let { translate(it) }).toJs()
    )
    if (!tooltipHooksActive) {
        addAfterInsertHook {
            if (tooltipOptions != null) {
                getElementJQueryD()?.tooltip(
                    tooltipOptions.unsafeCast<TooltipOptions>().copy(title = options.title?.let { translate(it) })
                        .toJs()
                )
            }
        }
        addAfterDestroyHook {
            if (tooltipOptions != null) {
                getElementJQueryD()?.tooltip("dispose")
            }
        }
        tooltipHooksActive = true
    }
    return this
}

/**
 * Shows tooltip for the current widget.
 * @return current widget
 */
fun Widget.showTooltip(): Widget {
    if (tooltipOptions != null) getElementJQueryD()?.tooltip("show")
    return this
}

/**
 * Hides tooltip for the current widget.
 * @return current widget
 */
fun Widget.hideTooltip(): Widget {
    if (tooltipOptions != null) getElementJQueryD()?.tooltip("hide")
    return this
}

/**
 * Disables tooltip for the current widget.
 * @return current widget
 */
fun Widget.disableTooltip(): Widget {
    if (tooltipOptions != null) {
        tooltipOptions = null
        getElementJQueryD()?.tooltip("dispose")
    }
    return this
}

/**
 * Enables popover for the current widget.
 * @param options popover options
 * @return current widget
 */
fun Widget.enablePopover(options: PopoverOptions = PopoverOptions()): Widget {
    popoverOptions = options
    getElementJQueryD()?.popover(
        options.copy(title = options.title?.let { translate(it) },
            content = options.content?.let { translate(it) }).toJs()
    )
    if (!popoverHooksActive) {
        addAfterInsertHook {
            if (popoverOptions != null) {
                getElementJQueryD()?.popover(
                    popoverOptions.unsafeCast<PopoverOptions>().copy(title = options.title?.let { translate(it) },
                        content = options.content?.let { translate(it) }).toJs()
                )
            }
        }
        addAfterDestroyHook {
            if (popoverOptions != null) {
                getElementJQueryD()?.popover("dispose")
            }
        }
        popoverHooksActive = true
    }
    return this
}

/**
 * Shows popover for the current widget.
 * @return current widget
 */
fun Widget.showPopover(): Widget {
    if (popoverOptions != null) getElementJQueryD()?.popover("show")
    return this
}

/**
 * Hides popover for the current widget.
 * @return current widget
 */
fun Widget.hidePopover(): Widget {
    if (popoverOptions != null) getElementJQueryD()?.popover("hide")
    return this
}

/**
 * Disables popover for the current widget.
 * @return current widget
 */
fun Widget.disablePopover(): Widget {
    if (popoverOptions != null) {
        popoverOptions = null
        getElementJQueryD()?.popover("dispose")
    }
    return this
}
