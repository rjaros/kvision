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

package pl.treksoft.kvision.onsenui.control

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.onsenui.tabbar.Tabbar
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.utils.obj
import pl.treksoft.kvision.utils.set
import kotlin.js.Promise

/**
 * A segment component.
 *
 * @constructor Creates a segment component.
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Segment(
    classes: Set<String> = setOf(),
    init: (Segment.() -> Unit)? = null
) : SimplePanel(classes) {

    /**
     * The connected tab bar component.
     */
    var tabbar: Tabbar? by refreshOnUpdate()

    /**
     * The index of the first active button.
     */
    var activeIndex: Int? by refreshOnUpdate()

    /**
     * Whether the component is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("ons-segment", childrenVNodes())
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        tabbar?.id?.let {
            attributeSetBuilder.add("tabbar-id", it)
        }
        activeIndex?.let {
            attributeSetBuilder.add("active-index", "$it")
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("postchange") { e, _ ->
            this.dispatchEvent("onsPostchange", obj { detail = e })
            e.stopPropagation()
        }
    }

    /**
     * Activates the button at given index.
     * @param index the button index
     * @param options a parameter object
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun setActiveButton(index: Int, options: dynamic = undefined): Promise<Unit>? {
        return getElement()?.asDynamic()?.setActiveButton(index, options)
    }

    /**
     * Gets the active button index.
     * @return active button index
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun getActiveButtonIndex(): Number {
        return getElement()?.asDynamic()?.getActiveButtonIndex() ?: -1
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.segment(
    classes: Set<String>? = null,
    className: String? = null,
    init: (Segment.() -> Unit)? = null
): Segment {
    val segment = Segment(classes ?: className.set, init)
    this.add(segment)
    return segment
}
