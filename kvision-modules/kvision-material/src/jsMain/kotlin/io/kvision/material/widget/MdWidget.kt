/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2024 Maanrifa Bacar Ali <dev.manrif@gmail.com>
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
package io.kvision.material.widget

import io.kvision.material.util.WidgetPropertyKeepDelegateProvider
import io.kvision.material.util.WidgetPropertySyncDelegateProvider
import io.kvision.material.util.WidgetPropertySyncTransformDelegateProvider
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode

/**
 * Base class for material widget.
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
abstract class MdWidget internal constructor(
    protected val tag: String,
    className: String?
) : Widget(className) {

    private val slotComponents = mutableMapOf<Slot, Component>()

    override var parent: Container?
        get() = super.parent
        set(value) {
            super.parent = value
            onParentChanged(value)
            attachSlots()
        }

    init {
        @Suppress("LeakingThis")
        useSnabbdomDistinctKey()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    protected open fun onParentChanged(parent: Container?) = Unit

    override fun dispose() {
        super.dispose()

        slotComponents
            .onEach { component ->
                component.value.dispose()
                component.value.clearParent()
            }
            .clear()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Rendering
    ///////////////////////////////////////////////////////////////////////////

    override fun render(): VNode {
        return renderWithContentAndChildren()
    }

    protected fun renderWithText(text: String?): VNode {
        return renderWithContentAndChildren(text)
    }

    protected fun renderWithTranslatableText(text: String?): VNode {
        return renderWithContentAndChildren(translate(text))
    }

    private fun renderWithContentAndChildren(text: String? = null): VNode {
        return if (text == null && slotComponents.isEmpty()) {
            render(tag, childrenVNodes())
        } else if (text == null && slotComponents.isNotEmpty()) {
            render(tag, slotVNodes() + childrenVNodes())
        } else if (text != null && slotComponents.isEmpty()) {
            render(tag, arrayOf(text) + childrenVNodes())
        } else {
            render(tag, arrayOf(text) + slotVNodes() + childrenVNodes())
        }
    }

    private fun slotVNodes(): Array<VNode> {
        return slotComponents.values.toVNodeArray()

    }

    private fun childrenVNodes(): Array<VNode> {
        return childComponents()
            .takeIf { it.isNotEmpty() }
            ?.toVNodeArray()
            ?: emptyArray()
    }

    @Suppress("UnsafeCastFromDynamic")
    private fun Collection<Component>.toVNodeArray(): Array<VNode> {
        return toTypedArray()
            .asDynamic()
            .filter { c: Component -> c.visible }
            .map { c: Component -> c.renderVNode() }
    }

    protected open fun childComponents(): Collection<Component> {
        return emptyList()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Slots
    ///////////////////////////////////////////////////////////////////////////

    private fun attachSlots() {
        if (slotComponents.isNotEmpty()) {
            slotComponents.values.forEach(this::attachParent)
        }
    }

    private fun attachParent(child: Component) {
        child.clearParent()
        child.parent = parent
    }

    internal operator fun Slot.invoke(child: Component?) {
        slotComponents
            .remove(this)
            ?.clearParent()

        if (child != null) {
            slotComponents[this] = child

            if (this != Slot.None) {
                child.setAttribute("slot", value)
            }

            // The parent maybe null if called from constructor DSL
            attachParent(child)
        }

        refresh()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Delegate
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Keep the last set value but return it only if the DOM element is not available.
     * DOM element value will be updated if available.
     *
     * @see [WidgetPropertyKeepDelegateProvider]
     */
    internal fun <T> keepOnUpdate(initialValue: T): WidgetPropertyKeepDelegateProvider<T> {
        return WidgetPropertyKeepDelegateProvider(initialValue)
    }

    /**
     * Synchronise changes between JS object and Kotlin object by their property name.
     * The main purpose is to avoid full and unnecessary widget refreshing.
     *
     * @see [WidgetPropertySyncDelegateProvider]
     */
    internal fun <T> syncOnUpdate(initialValue: T): WidgetPropertySyncDelegateProvider<T> {
        return WidgetPropertySyncDelegateProvider { thisRef, prop, refreshFunction ->
            refreshOnUpdate(initialValue, refreshFunction).provideDelegate(thisRef, prop)
        }
    }

    /**
     * Synchronise changes between JS object and Kotlin object by their property name.
     * The value is first transformed using [transform] before it is assigned to element.
     * The main purpose is to avoid full and unnecessary widget refreshing.
     *
     * @see [WidgetPropertySyncTransformDelegateProvider]
     */
    internal fun <T, U> syncOnUpdate(
        initialValue: T,
        transform: (T) -> U
    ): WidgetPropertySyncTransformDelegateProvider<T, U> {
        return WidgetPropertySyncTransformDelegateProvider(transform) { thisRef, prop, refreshFunction ->
            refreshOnUpdate(initialValue, refreshFunction).provideDelegate(thisRef, prop)
        }
    }
}
