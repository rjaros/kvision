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

package pl.treksoft.kvision.onsenui.toolbar

import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.core.ClassSetBuilder
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.CustomTag
import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.utils.set

/**
 * A button component designed to be placed inside the toolbar.
 *
 * @constructor Creates a toolbar button component.
 * @param content the content of the button.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param icon an icon placed on the toolbar button
 * @param disabled specify if the button should be disabled
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class ToolbarButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    icon: String? = null,
    disabled: Boolean? = null,
    classes: Set<String> = setOf(),
    init: (ToolbarButton.() -> Unit)? = null
) : CustomTag("ons-toolbar-button", content, rich, align, classes) {

    /**
     *  The icon placed on the toolbar button.
     */
    var icon: String? by refreshOnUpdate(icon)

    /**
     *  Specify if the button should be disabled.
     */
    var disabled: Boolean? by refreshOnUpdate(disabled)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        init?.invoke(this)
    }

    /**
     * A dynamic property returning current state of the component.
     */
    val isDisabled: Boolean?
        @Suppress("UnsafeCastFromDynamic")
        get() = getElement()?.asDynamic()?.disabled

    override fun buildAttributesSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributesSet(attributeSetBuilder)
        icon?.let {
            attributeSetBuilder.add("icon", it)
        }
        if (disabled == true) {
            attributeSetBuilder.add("disabled")
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (content != null) {
            classSetBuilder.add("kv-button-with-text")
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: ToolbarButton.(MouseEvent) -> Unit): ToolbarButton {
        this.setEventListener<ToolbarButton> {
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
fun Div.toolbarButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    icon: String? = null,
    disabled: Boolean? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (ToolbarButton.() -> Unit)? = null
): ToolbarButton {
    val toolbarButton = ToolbarButton(content, rich, align, icon, disabled, classes ?: className.set, init)
    this.add(toolbarButton)
    return toolbarButton
}
