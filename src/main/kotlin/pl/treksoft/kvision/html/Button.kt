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
import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.ResString
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget

/**
 * Button styles.
 */
enum class ButtonStyle(val className: String) {
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
enum class ButtonSize(internal val className: String) {
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
 * @param classes a set of CSS class names
 */
open class Button(
    text: String, icon: String? = null, style: ButtonStyle = ButtonStyle.PRIMARY, type: ButtonType = ButtonType.BUTTON,
    disabled: Boolean = false, classes: Set<String> = setOf()
) : Widget(classes) {

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

    override fun render(): VNode {
        val t = createLabelWithIcon(text, icon, image)
        return render("button", t)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("btn" to true)
        cl.add(style.className to true)
        size?.let {
            cl.add(it.className to true)
        }
        if (block) {
            cl.add("btn-block" to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val snattrs = super.getSnAttrs().toMutableList()
        snattrs.add("type" to type.buttonType)
        if (disabled) {
            snattrs.add("disabled" to "disabled")
        }
        return snattrs
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: Button.(MouseEvent) -> Unit): Button {
        this.setEventListener<Button> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }
    /**
     * Makes the button focused.
     */
    open fun focus() {
        getElementJQuery()?.focus()
    }

    /**
     * Makes the button blur.
     */
    open fun blur() {
        getElementJQuery()?.blur()
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
    classes: Set<String> = setOf(),
    init: (Button.() -> Unit)? = null
): Button {
    val button = Button(text, icon, style, type, disabled, classes).apply { init?.invoke(this) }
    this.add(button)
    return button
}
