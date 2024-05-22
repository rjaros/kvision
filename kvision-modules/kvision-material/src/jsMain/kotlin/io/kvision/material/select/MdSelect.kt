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
package io.kvision.material.select

import io.kvision.material.ExperimentalMaterialApi
import io.kvision.material.form.MdFormInputWidget
import io.kvision.material.util.add
import io.kvision.material.util.addBool
import io.kvision.material.util.requireElementD
import io.kvision.material.widget.MdListWidgetContainer
import io.kvision.material.widget.MdListWidgetContainerDelegate
import io.kvision.material.widget.toItemWidgetArray
import io.kvision.material.widget.toItemWidgetArrayOrDefault
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.snabbdom.VNode
import org.w3c.dom.events.Event

enum class SelectMenuPositioning(internal val value: String) {
    Absolute("absolute"),
    Fixed("fixed"),
    Popover("popover")
}

/**
 * Select menus display a list of choices on temporary surfaces and display the currently selected
 * menu item above the menu.
 *
 * See https://material-web.dev/components/select/
 *
 * @author Maanrifa Bacar Ali <dev.manrif@gmail.com>
 */
@ExperimentalMaterialApi
open class MdSelect internal constructor(
    tag: String,
    quick: Boolean,
    disabled: Boolean,
    required: Boolean,
    errorText: String?,
    label: String?,
    supportingText: String?,
    error: Boolean,
    menuPositioning: SelectMenuPositioning,
    typeaheadDelay: Int,
    selectedIndex: Int,
    value: String?,
    name: String?,
    validationMessage: String?,
    className: String?
) : MdFormInputWidget<String?>(
    tag = tag,
    disabled = disabled,
    required = required,
    value = value,
    name = name,
    validationMessage = validationMessage,
    className = className
), MdListWidgetContainer<MdSelectOption> {

    private val listDelegate =
        MdListWidgetContainerDelegate<MdSelectOption>(@Suppress("LeakingThis") this)

    /**
     * Opens the menu synchronously with no animation.
     */
    var quick by refreshOnUpdate(quick)

    /**
     * The error message that replaces supporting text when error is true.
     * If errorText is an empty string, then the [supportingText] will continue to show.
     * This error message overrides the error message displayed by [reportValidity].
     */
    var errorText by refreshOnUpdate(errorText)

    /**
     * The floating label for the field.
     */
    var label by refreshOnUpdate(label)

    /**
     * Conveys additional information below the select, such as how it should be used.
     */
    var supportingText by refreshOnUpdate(supportingText)

    /**
     * Gets or sets whether or not the select is in a visually invalid state.
     * This error state overrides the error state controlled by [reportValidity].
     */
    var error by refreshOnUpdate(error)

    /**
     * Whether or not the underlying md-menu should be position: fixed to display in a top-level manner,
     * or position: absolute.
     *
     * position:fixed is useful for cases where select is inside of another element with stacking
     * context and hidden overflows such as md-dialog.
     */
    var menuPositioning by refreshOnUpdate(menuPositioning)

    /**
     * The max time between the keystrokes of the typeahead select / menu behavior before it clears
     * the typeahead buffer.
     */
    var typeaheadDelay by refreshOnUpdate(typeaheadDelay)

    /**
     * Index of the selected option.
     */
    var selectedIndex: Int by syncOnUpdate(selectedIndex)

    /**
     * List of available options.
     */
    val options: Array<MdSelectOption>
        get() = toItemWidgetArrayOrDefault(getElementD()?.options, listDelegate::items)

    /**
     * List of selected options.
     */
    val selectedOptions: Array<MdSelectOption>
        get() = toItemWidgetArray(getElementD()?.selectedOptions)

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun onParentChanged(parent: Container?) {
        super.onParentChanged(parent)
        listDelegate.updateParent(parent)
    }

    override fun afterCreate(node: VNode) {
        super.afterCreate(node)
        getElementD().selectedIndex = selectedIndex
    }

    ///////////////////////////////////////////////////////////////////////////
    // Rendering
    ///////////////////////////////////////////////////////////////////////////

    override fun childComponents(): Collection<Component> {
        return listDelegate.items
    }

    ///////////////////////////////////////////////////////////////////////////
    // Attributes
    ///////////////////////////////////////////////////////////////////////////

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)

        if (quick) {
            attributeSetBuilder.addBool("quick")
        }

        label?.let {
            attributeSetBuilder.add("label", translate(it))
        }

        errorText?.let {
            attributeSetBuilder.add("error-text", translate(it))
        }

        supportingText?.let {
            attributeSetBuilder.add("supporting-text", translate(it))
        }

        if (error) {
            attributeSetBuilder.addBool("error")
        }

        attributeSetBuilder.add("menu-positioning", menuPositioning.value)
        attributeSetBuilder.add("typeahead-delay", typeaheadDelay)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Selection
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Selects an option given the value of the option.
     */
    fun select(value: String) {
        requireElementD().select(value)
    }

    /**
     * Selects an option given the index of the option.
     */
    fun select(index: Int) {
        requireElementD().selectIndex(index)
    }

    /**
     * Reset the select to its default value.
     */
    fun reset() {
        requireElementD().reset()
    }

    override fun onChange(event: Event) {
        super.onChange(event)
        selectedIndex = getElementD().selectedIndex.unsafeCast<Int>()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Options
    ///////////////////////////////////////////////////////////////////////////

    override fun add(item: MdSelectOption) {
        listDelegate.add(item)
    }

    override fun add(position: Int, item: MdSelectOption) {
        listDelegate.add(position, item)
    }

    override fun addAll(items: List<MdSelectOption>) {
        listDelegate.addAll(items)
    }

    override fun remove(item: MdSelectOption) {
        listDelegate.add(item)
    }

    override fun removeAt(position: Int) {
        listDelegate.removeAt(position)
    }

    override fun removeAll() {
        listDelegate.removeAll()
    }

    override fun disposeAll() {
        listDelegate.disposeAll()
    }
}
