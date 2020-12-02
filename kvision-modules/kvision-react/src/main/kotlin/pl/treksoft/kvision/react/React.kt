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

package pl.treksoft.kvision.react

import com.github.snabbdom.VNode
import org.w3c.dom.HTMLElement
import pl.treksoft.kvision.KVManagerReact
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.state.ObservableState
import pl.treksoft.kvision.utils.set
import react.RBuilder
import react.dom.render as ReactRender

/**
 * React component for KVision with support for state holder.
 * @param S state type
 * @param classes a set of CSS class names
 * @param builder a builder function for external react components with support for the state holder.
 */
class React<S>(
    state: S,
    classes: Set<String> = setOf(),
    private val builder: RBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
) : Widget(classes), ObservableState<S> {

    private val observers = mutableListOf<(S) -> Unit>()

    var state = state
        set(value) {
            field = value
            refreshFunction?.invoke()
            observers.forEach { it(state) }
        }

    private var refreshFunction: (() -> Unit)? = null

    @Suppress("UnsafeCastFromDynamic")
    internal constructor(
        classes: Set<String> = setOf(),
        builder: RBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
    ) : this(js("{}"), classes, builder)

    override fun afterInsert(node: VNode) {
        ReactRender(node.elm as HTMLElement) {
            reactComponent {
                this.renderFunction = { rBuilder, refresh ->
                    refreshFunction = refresh
                    rBuilder.builder({ state }) { changeState: (S) -> S ->
                        state = changeState(state)
                        refresh()
                    }
                }
            }
        }
    }

    override fun afterDestroy() {
        vnode?.elm?.let {
            KVManagerReact.reactDom.unmountComponentAtNode(it)
        }
    }

    override fun getState(): S = state

    override fun subscribe(observer: (S) -> Unit): () -> Unit {
        observers += observer
        observer(state)
        return {
            observers -= observer
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
    classes: Set<String>? = null,
    className: String? = null,
    builder: RBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
): React<S> {
    val react = React(state, classes ?: className.set, builder)
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
    classes: Set<String>? = null,
    className: String? = null,
    builder: RBuilder.(getState: () -> S, changeState: ((S) -> S) -> Unit) -> Unit
): React<S> {
    val react = React(state.getState(), classes ?: className.set, builder)
    react.addAfterDisposeHook(state.subscribe { react.state = it })
    this.add(react)
    return react
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.react(
    classes: Set<String>? = null,
    className: String? = null,
    builder: RBuilder.() -> Unit
): React<dynamic> {
    val fullBuilder = fun RBuilder.(_: () -> dynamic, _: ((dynamic) -> dynamic) -> Unit) {
        this.builder()
    }
    val react = React(classes ?: className.set, fullBuilder)
    this.add(react)
    return react
}
