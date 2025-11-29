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

package io.kvision.react

import io.kvision.ReactModule
import io.kvision.core.Container
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableState
import react.ChildrenBuilder
import react.StateSetter
import react.create
import react.dom.client.Root
import react.dom.client.createRoot
import web.dom.Element

/**
 * React component for KVision with support for state holder.
 * @param S state type
 * @param className CSS class names
 * @param builder a builder function for external react components with support for the state holder.
 */
class React<S>(
    state: S,
    className: String? = null,
    private val builder: ChildrenBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
) : Widget(className), ObservableState<S> {

    private val observers = mutableListOf<(S) -> Unit>()
    private var root: Root? = null

    var state = state
        set(value) {
            field = value
            refreshFunction?.invoke(value)
            observers.forEach { it(state) }
        }

    private var refreshFunction: (StateSetter<S>)? = null

    init {
        useSnabbdomDistinctKey()
    }

    @Suppress("UnsafeCastFromDynamic")
    internal constructor(
        className: String? = null,
        builder: ChildrenBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
    ) : this(js("{}"), className, builder)

    override fun afterInsert(node: VNode) {
        val element = reactWrapper { refresh ->
            refreshFunction = refresh
            builder({ state }) { changeState: (S) -> S ->
                state = changeState(state)
                refresh(state)
            }
        }
        root = createRoot(node.elm.unsafeCast<Element>()).also {
            it.render(element.create())
        }
    }

    override fun afterDestroy() {
        root?.unmount()
    }

    override fun getState(): S = state

    override fun subscribe(observer: (S) -> Unit): () -> Unit {
        observers += observer
        observer(state)
        return {
            observers -= observer
        }
    }

    companion object {
        init {
            ReactModule.initialize()
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.react(
    state: S,
    className: String? = null,
    builder: ChildrenBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
): React<S> {
    val react = React(state, className, builder)
    this.add(react)
    return react
}

/**
 * DSL builder extension function for binding to ObservableState
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.reactBind(
    state: ObservableState<S>,
    className: String? = null,
    builder: ChildrenBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
): React<S> {
    val react = React(state.getState(), className, builder)
    react.addBeforeDisposeHook(state.subscribe { react.state = it })
    this.add(react)
    return react
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.react(
    className: String? = null,
    builder: ChildrenBuilder.() -> Unit
): React<dynamic> {
    val fullBuilder = fun ChildrenBuilder.(_: () -> dynamic, _: ((dynamic) -> dynamic) -> Unit) {
        this.builder()
    }
    val react = React(className, fullBuilder)
    this.add(react)
    return react
}
