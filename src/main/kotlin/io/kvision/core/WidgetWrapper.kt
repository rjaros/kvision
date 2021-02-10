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

import com.github.snabbdom.VNode
import io.kvision.panel.SimplePanel

/**
 * This class allows to wrap a component into separately styled DIV element.
 *
 * @constructor
 * @param wrapped wrapped component
 * @param classes Set of CSS class names
 */
class WidgetWrapper(internal var wrapped: Component?, classes: Set<String> = setOf()) : SimplePanel(classes) {

    override var visible
        get() = wrapped?.visible == true
        set(value) {
            wrapped?.visible = value
        }

    init {
        @Suppress("LeakingThis")
        wrapped?.parent = this
    }

    override fun render(): VNode {
        return wrapped?.let {
            render("div", arrayOf(it.renderVNode()))
        } ?: render("div")
    }

    override fun dispose() {
        super.dispose()
        wrapped?.clearParent()
        wrapped = null
    }
}
