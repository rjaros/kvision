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

import pl.treksoft.kvision.utils.obj
import react.Component
import react.RBuilder
import react.RProps
import react.RState
import react.ReactElement
import react.buildElements
import react.setState

/**
 * @suppress Internal interface for properties
 */
external interface ReactComponentProps : RProps {
    var renderFunction: (rBuilder: RBuilder, refresh: () -> Unit) -> Unit
}

/**
 * @suppress Internal class for React components
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
internal abstract class KVRComponent<P : ReactComponentProps, S : RState> : Component<P, S>() {
    init {
        state = obj<S> { init(props) }
    }

    open fun S.init(props: P) {}

    @JsName("RBuilder_children")
    fun RBuilder.children() {
        props.children()
    }

    fun <T> RBuilder.children(value: T) {
        props.children(value)
    }

    @JsName("RBuilder_render")
    fun RBuilder.render() {
        props.renderFunction(this) {
            setState {
            }
        }
    }

    override fun render() = buildElements { render() }
}

/**
 * Internal React component wrapper
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
internal class ReactComponent : KVRComponent<ReactComponentProps, RState>() {
    @Suppress("RedundantOverride")
    override fun render(): dynamic {
        return super.render()
    }
}

/**
 * Internal React component builder function
 */
internal fun RBuilder.reactComponent(handler: ReactComponentProps.() -> Unit): ReactElement {
    return child(ReactComponent::class) {
        this.attrs(handler)
    }
}
