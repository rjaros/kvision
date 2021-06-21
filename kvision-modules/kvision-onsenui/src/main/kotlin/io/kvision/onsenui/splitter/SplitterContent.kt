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

package io.kvision.onsenui.splitter

import com.github.snabbdom.VNode
import org.w3c.dom.HTMLElement
import io.kvision.KVManagerOnsenui.ons
import io.kvision.onsenui.core.Page
import io.kvision.panel.SimplePanel
import io.kvision.utils.createInstance
import io.kvision.utils.set
import kotlin.js.Promise

/**
 * A splitter content component.
 *
 * @constructor Creates a splitter content component.
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class SplitterContent(
    className: String? = null,
    init: (SplitterContent.() -> Unit)? = null
) : SimplePanel(className) {

    /**
     * A dynamic property returning current page.
     */
    @Suppress("UnsafeCastFromDynamic")
    val page: HTMLElement?
        get() = getElement()?.asDynamic()?.page

    internal val pagesMap = mutableMapOf<String, Page>()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-splitter-content", childrenVNodes())
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        node.elm.asDynamic().pageLoader = (ons.PageLoader as Any).createInstance<Any>({ o: dynamic, done: dynamic ->
            @Suppress("UnsafeCastFromDynamic")
            val page: Page = o.page
            done(page.getElement())
        }, { })
    }

    /**
     * Loads the specified page into the splitter content.
     * @param pageId a given page id
     * @param options a parameter object
     */
    open fun load(pageId: String, options: dynamic = undefined): Promise<Unit>? {
        return pagesMap[pageId]?.let { load(it, options) }
    }

    /**
     * Loads the specified page into the splitter content.
     * @param page a given page
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun load(page: Page, options: dynamic = undefined): Promise<Unit>? {
        (children?.first() as? Page)?.let {
            it.dispatchHideEvent()
            it.dispatchDestroyEvent()
            remove(it)
        }
        add(page)
        return getElement()?.asDynamic()?.load(page, options)
    }

}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Splitter.splitterContent(
    className: String? = null,
    init: (SplitterContent.() -> Unit)? = null
): SplitterContent {
    val splitterContent = SplitterContent(className, init)
    this.add(splitterContent)
    return splitterContent
}
