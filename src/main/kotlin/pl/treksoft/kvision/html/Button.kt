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
enum class BUTTONSTYLE(internal val className: String) {
    DEFAULT("btn-default"),
    PRIMARY("btn-primary"),
    SUCCESS("btn-success"),
    INFO("btn-info"),
    WARNING("btn-warning"),
    DANGER("btn-danger"),
    LINK("btn-link")
}

/**
 * Button sizes.
 */
enum class BUTTONSIZE(internal val className: String) {
    LARGE("btn-lg"),
    SMALL("btn-sm"),
    XSMALL("btn-xs")
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
    text: String, icon: String? = null, style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT,
    disabled: Boolean = false, classes: Set<String> = setOf()
) : Widget(classes) {

    /**
     * Button label.
     */
    var text = text
        set(value) {
            field = value
            refresh()
        }
    /**
     * Button icon.
     */
    var icon = icon
        set(value) {
            field = value
            refresh()
        }
    /**
     * Button style.
     */
    var style = style
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if button is disabled.
     */
    var disabled = disabled
        set(value) {
            field = value
            refresh()
        }
    /**
     * Button image.
     */
    var image: ResString? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Button size.
     */
    var size: BUTTONSIZE? = null
        set(value) {
            field = value
            refresh()
        }
    /**
     * Determines if the button takes all the space horizontally.
     */
    var block = false
        set(value) {
            field = value
            refresh()
        }

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
        if (disabled) {
            cl.add("disabled" to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        return super.getSnAttrs() + ("type" to "button")
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

    companion object {
        /**
         * DSL builder extension function
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.button(
            text: String, icon: String? = null, style: BUTTONSTYLE = BUTTONSTYLE.DEFAULT,
            disabled: Boolean = false, classes: Set<String> = setOf(), init: (Button.() -> Unit)? = null
        ) {
            this.add(Button(text, icon, style, disabled, classes).apply { init?.invoke(this) })
        }
    }
}
