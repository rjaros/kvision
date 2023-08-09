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

package io.kvision.onsenui.dialog

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.panel.SimplePanel

/**
 * An alert dialog component.
 *
 * @constructor Creates an alert dialog component.
 * @param dialogTitle a title of the alert dialog
 * @param cancelable whether the dialog can be canceled
 * @param animation determines if the transitions are animated
 * @param rowfooter horizontally aligns the footer buttons
 * @param className CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class AlertDialog(
    dialogTitle: String? = null,
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    rowfooter: Boolean? = null,
    className: String? = null,
    init: (AlertDialog.() -> Unit)? = null
) : Dialog(cancelable, animation, className) {

    /**
     * A title of the alert dialog.
     */
    var dialogTitle: String?
        get() = titlePanel.content
        set(value) {
            titlePanel.content = value
        }

    /**
     * Horizontally aligns the footer buttons.
     */
    var rowfooter: Boolean? by refreshOnUpdate(rowfooter)

    /**
     * The alert dialog title component.
     */
    val titlePanel = Div(dialogTitle, className = "alert-dialog-title")

    /**
     * The alert dialog content container.
     */
    val contentPanel = SimplePanel("alert-dialog-content")

    /**
     * The alert dialog footer container.
     */
    val footerPanel = SimplePanel("alert-dialog-footer")

    init {
        titlePanel.parent = this
        contentPanel.parent = this
        footerPanel.parent = this
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render(
            "ons-alert-dialog",
            arrayOf(titlePanel.renderVNode(), contentPanel.renderVNode(), footerPanel.renderVNode())
        )
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        val modRowfooter = if (rowfooter == true) "rowfooter" else null
        val modList = listOfNotNull(modifier, modRowfooter)
        if (modList.isNotEmpty()) {
            attributeSetBuilder.add("modifier", modList.joinToString(" "))
        }
    }

    override fun add(child: Component) {
        contentPanel.add(child)
    }

    override fun add(position: Int, child: Component) {
        contentPanel.add(position, child)
    }

    override fun addAll(children: List<Component>) {
        contentPanel.addAll(children)
    }

    override fun remove(child: Component) {
        contentPanel.remove(child)
    }

    override fun removeAt(position: Int) {
        contentPanel.removeAt(position)
    }

    override fun removeAll() {
        contentPanel.removeAll()
    }

    override fun disposeAll() {
        contentPanel.disposeAll()
    }

    override fun getChildren(): List<Component> {
        return contentPanel.getChildren()
    }

    override fun dispose() {
        super.dispose()
        titlePanel.dispose()
        contentPanel.dispose()
        footerPanel.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.alertDialog(
    dialogTitle: String? = null,
    cancelable: Boolean? = null,
    animation: Boolean? = null,
    rowfooter: Boolean? = null,
    className: String? = null,
    init: (AlertDialog.() -> Unit)? = null
): AlertDialog {
    return AlertDialog(dialogTitle, cancelable, animation, rowfooter, className, init)
}
