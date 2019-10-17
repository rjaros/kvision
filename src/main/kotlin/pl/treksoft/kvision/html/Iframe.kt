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
import org.w3c.dom.Window
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget

/**
 * Iframe sandbox options.
 */
enum class Sandbox(internal val option: String) {
    ALLOWFORMS("allow-forms"),
    ALLOWPOINTERLOCK("allow-pointer-lock"),
    ALLOWPOPUPS("allow-popups"),
    ALLOWSAMEORIGIN("allow-same-origin"),
    ALLOWSCRIPTS("allow-scripts"),
    ALLOWTOPNAVIGATION("allow-top-navigation")
}

/**
 * Iframe component.
 *
 * @constructor
 * @param src the iframe document address
 * @param srcdoc the HTML content of the iframe
 * @param name the name of the iframe
 * @param iframeWidth the width of the iframe
 * @param iframeHeight the height of the iframe
 * @param sandbox a set of Sandbox options
 * @param classes a set of CSS class names
 */
open class Iframe(
    src: String? = null, srcdoc: String? = null, name: String? = null, iframeWidth: Int? = null,
    iframeHeight: Int? = null, sandbox: Set<Sandbox>? = null, classes: Set<String> = setOf()
) : Widget(classes) {
    /**
     * The iframe document address.
     */
    var src by refreshOnUpdate(src)
    /**
     * The HTML content of the iframe.
     */
    var srcdoc by refreshOnUpdate(srcdoc)
    /**
     * The name of the iframe.
     */
    var name by refreshOnUpdate(name)
    /**
     * The width of the iframe.
     */
    var iframeWidth by refreshOnUpdate(iframeWidth)
    /**
     * The height of the iframe.
     */
    var iframeHeight by refreshOnUpdate(iframeHeight)
    /**
     * A set of Sandbox options.
     */
    var sandbox by refreshOnUpdate(sandbox)
    /**
     * A current location URL of the iframe.
     */
    var location: String?
        get() = getLocationHref()
        set(value) {
            setLocationHref(value)
        }

    override fun render(): VNode {
        return render("iframe")
    }

    override fun getSnAttrs(): List<StringPair> {
        val pr = super.getSnAttrs().toMutableList()
        src?.let {
            pr.add("src" to it)
        }
        srcdoc?.let {
            pr.add("srcdoc" to it)
        }
        name?.let {
            pr.add("name" to it)
        }
        iframeWidth?.let {
            pr.add("width" to it.toString())
        }
        iframeHeight?.let {
            pr.add("height" to it.toString())
        }
        sandbox?.let {
            pr.add("sandbox" to it.joinToString(" ") { it.option })
        }
        return pr
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun getLocationHref(): String? {
        return getElementJQueryD()[0].contentWindow.location.href
    }

    private fun setLocationHref(location: String?) {
        getElementJQueryD()[0].contentWindow.location.href = location ?: "about:blank"
    }

    /**
     * Returns content window object of the iframe.
     * @return content window object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getIframeWindow(): Window {
        return getElementJQueryD()[0].contentWindow
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.iframe(
    src: String? = null, srcdoc: String? = null, name: String? = null, iframeWidth: Int? = null,
    iframeHeight: Int? = null, sandbox: Set<Sandbox>? = null, classes: Set<String> = setOf(),
    init: (Iframe.() -> Unit)? = null
): Iframe {
    val iframe =
        Iframe(src, srcdoc, name, iframeWidth, iframeHeight, sandbox, classes).apply { init?.invoke(this) }
    this.add(iframe)
    return iframe
}
