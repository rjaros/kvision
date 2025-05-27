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
package io.kvision.html

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

/**
 * Canvas component.
 *
 * @constructor
 * @param canvasWidth the width of the canvas
 * @param canvasHeight the height of the canvas
 * @param className CSS class names
 * @param init an initializer extension function
 */
@TagMarker
open class Canvas(
    canvasWidth: Int? = null, canvasHeight: Int? = null, className: String? = null,
    init: (Canvas.() -> Unit)? = null
) : Widget(className) {

    /**
     * The width of the canvas.
     */
    var canvasWidth by refreshOnUpdate(canvasWidth)

    /**
     * The height of the canvas.
     */
    var canvasHeight by refreshOnUpdate(canvasHeight)

    /**
     * The canvas rendering context.
     */
    lateinit var context2D: CanvasRenderingContext2D

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("canvas")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        canvasWidth?.let {
            attributeSetBuilder.add("width", "$it")
        }
        canvasHeight?.let {
            attributeSetBuilder.add("height", "$it")
        }
    }

    override fun afterInsertInternal(node: VNode) {
        super.afterInsertInternal(node)
        context2D = (node.elm as HTMLCanvasElement).getContext("2d") as CanvasRenderingContext2D
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.canvas(
    canvasWidth: Int? = null, canvasHeight: Int? = null,
    className: String? = null,
    init: (Canvas.() -> Unit)? = null
): Canvas {
    val canvas =
        Canvas(canvasWidth, canvasHeight, className, init)
    this.add(canvas)
    return canvas
}
