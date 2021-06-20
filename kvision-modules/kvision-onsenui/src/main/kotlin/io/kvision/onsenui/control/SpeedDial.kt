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

package io.kvision.onsenui.control

import com.github.snabbdom.VNode
import org.w3c.dom.events.MouseEvent
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.core.getElementJQuery
import io.kvision.html.CustomTag
import io.kvision.onsenui.FloatDirection
import io.kvision.onsenui.FloatPosition
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.obj
import io.kvision.utils.set

/**
 * An Onsen UI speed dial component.
 *
 * @constructor Creates a speed dial component.
 * @param icon an icon placed on the speed dial button
 * @param floatPosition a position of the button
 * @param floatDirection a direction the items are displayed
 * @param content the content the component.
 * @param rich whether [content] can contain HTML code
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class SpeedDial(
    icon: String? = null,
    floatPosition: FloatPosition? = null,
    floatDirection: FloatDirection? = null,
    content: String? = null,
    rich: Boolean = false,
    classes: Set<String> = setOf(),
    init: (SpeedDial.() -> Unit)? = null
) : CustomTag("ons-speed-dial", content, rich, null, classes) {

    /**
     * An icon placed on the speed dial button.
     */
    var icon: String?
        get() = fabWidget?.icon
        set(value) {
            if (value != null) {
                if (fabWidget != null) {
                    fabWidget?.icon = value
                } else {
                    fabWidget = Fab(icon = value).apply { this.parent = this@SpeedDial }
                }
            } else {
                fabWidget?.dispose()
                fabWidget = null
            }
        }

    /**
     * A position of the floating button.
     */
    var floatPosition: FloatPosition? by refreshOnUpdate(floatPosition)

    /**
     * A direction the items are displayed.
     */
    var floatDirection: FloatDirection? by refreshOnUpdate(floatDirection)

    /**
     * Whether the item will have a ripple effect when tapped.
     */
    var ripple: Boolean? by refreshOnUpdate()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * Whether the speed dial is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * A dynamic property returning whether the button is visible.
     */
    @Suppress("UnsafeCastFromDynamic")
    val isVisible: Boolean?
        get() = getElement()?.asDynamic()?.visible

    /**
     * A dynamic property returning whether the button is inline.
     */
    @Suppress("UnsafeCastFromDynamic")
    val isInline: Boolean?
        get() = getElement()?.asDynamic()?.inline

    /**
     * An internal floating button widget.
     */
    protected var fabWidget: Fab? = icon?.let { Fab(icon = it).apply { this.parent = this@SpeedDial } }

    init {
        init?.invoke(this)
    }

    override fun childrenVNodes(): Array<VNode> {
        val iconArr = fabWidget?.let { arrayOf(it.renderVNode()) } ?: emptyArray()
        return iconArr + super.childrenVNodes()
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add(floatPosition)
        attributeSetBuilder.add(floatDirection)
        if (ripple == true) {
            attributeSetBuilder.add("ripple")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("open") { e, _ ->
            this.dispatchEvent("onsOpen", obj { detail = e })
        }
        this.getElementJQuery()?.on("close") { e, _ ->
            this.dispatchEvent("onsClose", obj { detail = e })
        }
    }

    /**
     * Shows the speed dial button.
     */
    open fun showSpeedDial() {
        getElement()?.asDynamic()?.show()
    }

    /**
     * Hides the speed dial button.
     */
    open fun hideSpeedDial() {
        getElement()?.asDynamic()?.hide()
    }

    /**
     * Toggles the visibility of the speed dial button.
     */
    open fun toggleSpeedDial() {
        getElement()?.asDynamic()?.toggle()
    }

    /**
     * Shows the speed dial items.
     */
    open fun showSpeedDialItems() {
        getElement()?.asDynamic()?.showItems()
    }

    /**
     * Hides the speed dial items.
     */
    open fun hideSpeedDialItems() {
        getElement()?.asDynamic()?.hideItems()
    }

    /**
     * Toggles the visibility of the speed dial items.
     */
    open fun toggleSpeedDialItems() {
        getElement()?.asDynamic()?.toggleItems()
    }

    /**
     * Returns whether the speed dial menu is open.
     */
    @Suppress("UnsafeCastFromDynamic")
    open fun isOpen(): Boolean? {
        return getElement()?.asDynamic()?.isOpen()
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: SpeedDial.(MouseEvent) -> Unit): SpeedDial {
        this.setEventListener<SpeedDial> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }

    override fun dispose() {
        super.dispose()
        fabWidget?.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.speedDial(
    icon: String? = null,
    floatPosition: FloatPosition? = null,
    floatDirection: FloatDirection? = null,
    content: String? = null,
    rich: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SpeedDial.() -> Unit)? = null
): SpeedDial {
    val speedDial = SpeedDial(icon, floatPosition, floatDirection, content, rich, classes ?: className.set, init)
    this.add(speedDial)
    return speedDial
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.speedDial(
    state: ObservableState<S>,
    icon: String? = null,
    floatPosition: FloatPosition? = null,
    floatDirection: FloatDirection? = null,
    content: String? = null,
    rich: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (SpeedDial.(S) -> Unit)
) = speedDial(icon, floatPosition, floatDirection, content, rich, classes, className).bind(state, true, init)
