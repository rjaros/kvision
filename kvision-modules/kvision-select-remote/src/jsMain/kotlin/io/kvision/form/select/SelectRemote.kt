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
package io.kvision.form.select

import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.rpc.SimpleRemoteOption
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.form.FieldLabel
import io.kvision.form.InvalidFeedback
import io.kvision.form.StringFormControl
import io.kvision.panel.SimplePanel
import io.kvision.state.MutableState
import io.kvision.utils.SnOn
import web.http.RequestInit

/**
 * The form field component for SelectRemote control.
 *
 * @constructor
 * @param value selected value
 * @param serviceManager fullstack service manager
 * @param function fullstack service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param name the name attribute of the generated HTML input element
 * @param emptyOption determines if an empty option is automatically generated
 * @param multiple allows multiple value selection (multiple values are comma delimited)
 * @param selectSize the number of visible options
 * @param requestFilter a request filtering function
 * @param label label text bound to the input element
 * @param rich determines if [label] can contain HTML code
 * @param init an initializer extension function
 */
@Suppress("TooManyFunctions")
open class SelectRemote<out T : Any>(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)? = null,
    value: String? = null,
    name: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    label: String? = null,
    rich: Boolean = false,
    init: (SelectRemote<T>.() -> Unit)? = null
) : SimplePanel("form-group kv-mb-3"), StringFormControl, MutableState<String?> {
    /**
     * A value of the selected option.
     */
    override var value
        get() = input.value
        set(value) {
            input.value = value
        }

    /**
     * Determines if an empty option is automatically generated.
     */
    var emptyOption
        get() = input.emptyOption
        set(value) {
            input.emptyOption = value
        }

    /**
     * Determines if multiple value selection is allowed.
     */
    var multiple
        get() = input.multiple
        set(value) {
            input.multiple = value
        }

    /**
     * The number of visible options.
     */
    var selectSize
        get() = input.selectSize
        set(value) {
            input.selectSize = value
        }

    /**
     * Determines if the select is automatically focused.
     */
    var autofocus
        get() = input.autofocus
        set(value) {
            input.autofocus = value
        }

    /**
     * The placeholder for the select control.
     */
    var placeholder
        get() = input.placeholder
        set(value) {
            input.placeholder = value
        }

    /**
     * The label text bound to the select element.
     */
    var label
        get() = flabel.content
        set(value) {
            flabel.content = value
        }

    /**
     * Determines if [label] can contain HTML code.
     */
    var rich
        get() = flabel.rich
        set(value) {
            flabel.rich = value
        }

    private val idc = "kv_form_SelectRemote_$counter"
    final override val input: SelectRemoteInput<T> = SelectRemoteInput(
        serviceManager, function, stateFunction, value, emptyOption, multiple, selectSize, requestFilter,
        "form-control"
    ).apply {
        this.id = this@SelectRemote.idc
        this.name = name
    }
    final override val flabel: FieldLabel = FieldLabel(idc, label, rich, "form-label")
    final override val invalidFeedback: InvalidFeedback = InvalidFeedback().apply { visible = false }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addPrivate(flabel)
        this.addPrivate(input)
        this.addPrivate(invalidFeedback)
        counter++
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (validatorError != null) {
            classSetBuilder.add("kv-text-danger")
        }
    }

    override fun <T : Widget> setEventListener(block: SnOn<T>.() -> Unit): Int {
        return input.setEventListener(block)
    }

    override fun removeEventListener(id: Int) {
        input.removeEventListener(id)
    }

    override fun removeEventListeners() {
        input.removeEventListeners()
    }

    override fun add(child: Component) {
        input.add(child)
    }

    override fun add(position: Int, child: Component) {
        input.add(position, child)
    }

    override fun addAll(children: List<Component>) {
        input.addAll(children)
    }

    override fun remove(child: Component) {
        input.remove(child)
    }

    override fun removeAt(position: Int) {
        input.removeAt(position)
    }

    override fun removeAll() {
        input.removeAll()
    }

    override fun disposeAll() {
        input.disposeAll()
    }

    override fun getChildren(): List<Component> {
        return input.getChildren()
    }

    override fun focus() {
        input.focus()
    }

    override fun blur() {
        input.blur()
    }

    override fun getState(): String? = input.getState()

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        return input.subscribe(observer)
    }

    override fun setState(state: String?) {
        input.setState(state)
    }

    companion object {
        internal var counter = 0
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <T : Any> Container.selectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>, stateFunction: (() -> String)? = null,
    value: String? = null,
    name: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    selectSize: Int? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    label: String? = null, rich: Boolean = false, init: (SelectRemote<T>.() -> Unit)? = null
): SelectRemote<T> {
    val selectRemote =
        SelectRemote(
            serviceManager,
            function,
            stateFunction,
            value,
            name,
            emptyOption,
            multiple,
            selectSize,
            requestFilter,
            label,
            rich,
            init
        )
    this.add(selectRemote)
    return selectRemote
}
