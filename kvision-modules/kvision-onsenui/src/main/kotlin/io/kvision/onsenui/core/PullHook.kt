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

package io.kvision.onsenui.core

import com.github.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.CssSize
import io.kvision.html.Align
import io.kvision.html.CustomTag
import io.kvision.utils.asString
import io.kvision.utils.obj
import io.kvision.utils.set

/**
 * A pull hook component supporting "Pull to refresh" functionality.
 *
 * @constructor Creates a pull hook component.
 * @param content the content of the component.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class PullHook(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String> = setOf(),
    init: (PullHook.() -> Unit)? = null
) : CustomTag("ons-pull-hook", content, rich, align, classes) {

    /**
     * When pulled down further than this value it will switch to the "preaction" state.
     */
    var pullHeight: CssSize? by refreshOnUpdate()

    /**
     * The component automatically switches to the "action" state when pulled further than this value.
     */
    var thresholdHeight: CssSize? by refreshOnUpdate()

    /**
     * Whether the content of the page is not moving when pulling.
     */
    var fixedContent: Boolean? by refreshOnUpdate()

    /**
     * Whether the component is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * The current state of the element.
     */
    @Suppress("UnsafeCastFromDynamic")
    val state: String?
        get() = getElement()?.asDynamic()?.state

    /**
     * The current number of pixels the pull hook has moved.
     */
    @Suppress("UnsafeCastFromDynamic")
    val pullDistance: Number?
        get() = getElement()?.asDynamic()?.pullDistance

    /**
     * Action event listener function.
     */
    protected var onActionCallback: ((() -> Unit) -> Unit)? = null

    /**
     * Pull event listener function.
     */
    protected var onPullCallback: ((Number) -> Unit)? = null

    init {
        init?.invoke(this)
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        pullHeight?.let {
            attributeSetBuilder.add("height", it.asString())
        }
        thresholdHeight?.let {
            attributeSetBuilder.add("threshold-height", it.asString())
        }
        if (fixedContent == true) {
            attributeSetBuilder.add("fixed-content")
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        if (onActionCallback != null) {
            getElement()?.asDynamic()?.onAction = onActionCallback
        }
        if (onPullCallback != null) {
            getElement()?.asDynamic()?.onPull = onPullCallback
        }
        this.getElementJQuery()?.on("changestate") { e, _ ->
            this.dispatchEvent("onsChangestate", obj { detail = e })
        }
    }

    /**
     * Sets action event listener.
     * @param callback an event listener
     */
    open fun onAction(callback: (done: () -> Unit) -> Unit) {
        onActionCallback = callback
        getElement()?.asDynamic()?.onAction = callback
    }

    /**
     * Clears action event listener.
     */
    open fun onActionClear() {
        onActionCallback = null
        getElement()?.asDynamic()?.onAction = undefined
    }

    /**
     * Sets pull event listener.
     * @param callback an event listener
     */
    open fun onPull(callback: (ratio: Number) -> Unit) {
        onPullCallback = callback
        getElement()?.asDynamic()?.onPull = callback
    }

    /**
     * Clears pull event listener.
     */
    open fun onPullClear() {
        onPullCallback = null
        getElement()?.asDynamic()?.onPull = undefined
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Page.pullHook(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (PullHook.() -> Unit)? = null
): PullHook {
    val pullHook = PullHook(content, rich, align, classes ?: className.set, init)
    this.add(pullHook)
    return pullHook
}
