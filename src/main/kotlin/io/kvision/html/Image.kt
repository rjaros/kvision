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
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.core.ResString
import io.kvision.core.Widget

/**
 * Image shapes.
 */
enum class ImageShape(override val className: String) : CssClass {
    ROUNDED("rounded"),
    CIRCLE("rounded-circle"),
    THUMBNAIL("img-thumbnail")
}

/**
 * Image component.
 *
 * @constructor
 * @param src image URL
 * @param alt alternative text
 * @param responsive determines if the image is rendered as responsive
 * @param shape image shape
 * @param centered determines if the image is rendered centered
 * @param className CSS class names
 * @param init an initializer extension function
 */
@TagMarker
open class Image(
    src: ResString?, alt: String? = null, responsive: Boolean = false, shape: ImageShape? = null,
    centered: Boolean = false, className: String? = null, init: (Image.() -> Unit)? = null
) : Widget(className) {
    /**
     * URL of the image.
     */
    var src by refreshOnUpdate(src)

    /**
     * The alternative text of the image.
     */
    var alt by refreshOnUpdate(alt)

    /**
     * Determines if the image is rendered as responsive.
     */
    var responsive by refreshOnUpdate(responsive)

    /**
     * The shape of the image.
     */
    var shape by refreshOnUpdate(shape)

    /**
     * Determines if the image is rendered as centered.
     */
    var centered by refreshOnUpdate(centered)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("img")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        src?.let {
            attributeSetBuilder.add("src", it)
        }
        alt?.let {
            attributeSetBuilder.add("alt", translate(it))
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (responsive) {
            classSetBuilder.add("img-fluid")
        }
        if (centered) {
            classSetBuilder.add("center-block")
        }
        classSetBuilder.add(shape)
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.image(
    src: ResString?, alt: String? = null, responsive: Boolean = false, shape: ImageShape? = null,
    centered: Boolean = false,
    className: String? = null,
    init: (Image.() -> Unit)? = null
): Image {
    val image = Image(src, alt, responsive, shape, centered, className, init)
    this.add(image)
    return image
}
