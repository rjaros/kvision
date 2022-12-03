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
package io.kvision.html

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.core.ResString
import io.kvision.panel.SimplePanel
import org.w3c.dom.events.MouseEvent

/**
 * Button styles.
 */
enum class ButtonStyle(override val className: String) : CssClass {
    PRIMARY("btn-primary"),
    SECONDARY("btn-secondary"),
    SUCCESS("btn-success"),
    DANGER("btn-danger"),
    WARNING("btn-warning"),
    INFO("btn-info"),
    LIGHT("btn-light"),
    DARK("btn-dark"),
    LINK("btn-link"),
    OUTLINEPRIMARY("btn-outline-primary"),
    OUTLINESECONDARY("btn-outline-secondary"),
    OUTLINESUCCESS("btn-outline-success"),
    OUTLINEDANGER("btn-outline-danger"),
    OUTLINEWARNING("btn-outline-warning"),
    OUTLINEINFO("btn-outline-info"),
    OUTLINELIGHT("btn-outline-light"),
    OUTLINEDARK("btn-outline-dark")
}

/**
 * Button sizes.
 */
enum class ButtonSize(override val className: String) : CssClass {
    LARGE("btn-lg"),
    SMALL("btn-sm")
}

/**
 * Button types.
 */
enum class ButtonType(internal val buttonType: String) {
    BUTTON("button"),
    SUBMIT("submit"),
    RESET("reset")
}

/**
 * Button component.
 *
 * @constructor
 * @param text button label
 * @param icon button icon
 * @param style button style
 * @param disabled button state
 * @param separator a separator between label and icon/image (defaults to space)
 * @param labelFirst determines if the label is put before children elements (defaults to true)
 * @param className CSS class names
 */
open class Button(
    text: String, icon: String? = null, style: ButtonStyle = ButtonStyle.PRIMARY, type: ButtonType = ButtonType.BUTTON,
    disabled: Boolean = false, separator: String? = null, labelFirst: Boolean = true,
    className: String? = null
) : SimplePanel(className) {

    /**
     * Button label.
     */
    var text by refreshOnUpdate(text)

    /**
     * Button icon.
     */
    var icon by refreshOnUpdate(icon)

    /**
     * Button style.
     */
    var style by refreshOnUpdate(style)

    /**
     * Button types.
     */
    var type by refreshOnUpdate(type)

    /**
     * Determines if button is disabled.
     */
    var disabled by refreshOnUpdate(disabled)

    /**
     * Button image.
     */
    var image: ResString? by refreshOnUpdate()

    /**
     * Button size.
     */
    var size: ButtonSize? by refreshOnUpdate()

    /**
     * Determines if the button takes all the space horizontally.
     */
    var block by refreshOnUpdate(false)

    /**
     * A separator between label and icon/image.
     */
    var separator by refreshOnUpdate(separator)

    /**
     * Determines if the label is put before children elements.
     */
    var labelFirst by refreshOnUpdate(labelFirst)

    override fun render(): VNode {
        val t = createLabelWithIcon(text, icon, image, separator)
        return if (labelFirst) {
            render("button", t + childrenVNodes())
        } else {
            render("button", childrenVNodes() + t)
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add("btn")
        classSetBuilder.add(style)
        classSetBuilder.add(size)
        if (block) {
            classSetBuilder.add("btn-block")
        }
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        attributeSetBuilder.add("type", type.buttonType)
        if (disabled) {
            attributeSetBuilder.add("disabled")
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: Button.(MouseEvent) -> Unit) {
        this.setEventListener<Button> {
            click = { e ->
                self.handler(e)
            }
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.button(
    text: String,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    type: ButtonType = ButtonType.BUTTON,
    disabled: Boolean = false,
    separator: String? = null,
    labelFirst: Boolean = true,
    className: String? = null,
    init: (Button.() -> Unit)? = null
): Button {
    val button = Button(text, icon, style, type, disabled, separator, labelFirst, className).apply {
        init?.invoke(this)
    }
    this.add(button)
    return button
}
