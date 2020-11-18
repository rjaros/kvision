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
package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.state.bind
import pl.treksoft.kvision.utils.set

/**
 * Link component.
 *
 * @constructor
 * @param label link label
 * @param url link URL address
 * @param icon link icon
 * @param image link image
 * @param separator a separator between label and icon/image (defaults to space)
 * @param labelFirst determines if the label is put before children elements (defaults to true)
 * @param target link target
 * @param classes a set of CSS class names
 */
open class Link(
    label: String, url: String? = null, icon: String? = null, image: ResString? = null,
    separator: String? = null, labelFirst: Boolean = true, target: String? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {
    /**
     * Link label.
     */
    var label by refreshOnUpdate(label)

    /**
     * Link URL address.
     */
    var url by refreshOnUpdate(url)

    /**
     * Link icon.
     */
    var icon by refreshOnUpdate(icon)

    /**
     * Link image.
     */
    var image by refreshOnUpdate(image)

    /**
     * A separator between label and icon/image.
     */
    var separator by refreshOnUpdate(separator)

    /**
     * Determines if the label is put before children elements.
     */
    var labelFirst by refreshOnUpdate(labelFirst)

    /**
     * Link target
     */
    var target by refreshOnUpdate(target)

    override fun render(): VNode {
        val t = createLabelWithIcon(label, icon, image, separator)
        return if (labelFirst) {
            render("a", t + childrenVNodes())
        } else {
            render("a", childrenVNodes() + t)
        }
    }

    override fun getSnAttrs(): List<StringPair> {
        val pr = super.getSnAttrs().toMutableList()
        url?.let {
            pr.add("href" to it)
        }
        target?.let {
            pr.add("target" to it)
        }
        return pr
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: Link.(MouseEvent) -> Unit): Link {
        this.setEventListener<Link> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.link(
    label: String, url: String? = null, icon: String? = null, image: ResString? = null,
    separator: String? = null, labelFirst: Boolean = true, target: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.() -> Unit)? = null
): Link {
    val link =
        Link(
            label,
            url,
            icon,
            image,
            separator,
            labelFirst,
            target,
            classes ?: className.set
        ).apply { init?.invoke(this) }
    this.add(link)
    return link
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.link(
    state: ObservableState<S>,
    label: String, url: String? = null, icon: String? = null, image: ResString? = null,
    separator: String? = null, labelFirst: Boolean = true, target: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Link.(S) -> Unit)
) = link(label, url, icon, image, separator, labelFirst, target, classes, className).bind(state, true, init)
