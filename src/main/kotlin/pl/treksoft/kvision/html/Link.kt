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
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.panel.SimplePanel

/**
 * Link component.
 *
 * @constructor
 * @param label link label
 * @param url link URL address
 * @param icon link icon
 * @param image link image
 * @param classes a set of CSS class names
 */
open class Link(
    label: String, url: String, icon: String? = null, image: ResString? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {
    /**
     * Link label.
     */
    var label = label
        set(value) {
            field = value
            refresh()
        }
    /**
     * Link URL address.
     */
    var url = url
        set(value) {
            field = value
            refresh()
        }
    /**
     * Link icon.
     */
    var icon = icon
        set(value) {
            field = value
            refresh()
        }
    /**
     * Link image.
     */
    var image = image
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        val t = createLabelWithIcon(label, icon, image)
        return render("a", t + childrenVNodes())
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("href" to url)
    }

    companion object {
        /**
         * DSL builder extension function
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.link(
            label: String, url: String, icon: String? = null, image: ResString? = null,
            classes: Set<String> = setOf(), init: (Link.() -> Unit)? = null
        ) {
            this.add(Link(label, url, icon, image, classes).apply { init?.invoke(this) })
        }
    }
}
