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

import io.kvision.core.Container
import io.kvision.panel.ContainerType
import io.kvision.panel.Root
import io.kvision.utils.obj
import react.ChildrenBuilder
import react.FC
import react.Props
import react.StateSetter
import react.dom.html.ReactHTML.div
import react.useEffectWithCleanup
import react.useRef
import react.useState
import web.dom.document
import web.html.HTMLDivElement
import web.html.HTMLElement

/**
 * A helper functional component used by KVision React.
 */
fun <S> reactWrapper(builder: ChildrenBuilder.(refresh: StateSetter<S>) -> Unit) = FC<Props> {
    @Suppress("UnsafeCastFromDynamic")
    val state = useState<S> { js("{}") }
    builder(state.component2())
}

/**
 * A helper functional component which allows to use KVision components as React children.
 */
fun kvisionWrapper(builder: Container.() -> Unit) = FC<Props> {
    val elRef = useRef<HTMLDivElement>()
    useEffectWithCleanup {
        var root: Root? = null
        var el: HTMLElement? = null
        elRef.current?.let {
            el = document.createElement("div")
            it.appendChild(el)
            root = Root(el.unsafeCast<org.w3c.dom.HTMLElement>(), ContainerType.NONE, false, init = builder)
        }
        onCleanup {
            root?.dispose()
            el?.let { it.parentNode?.removeChild(it) }
        }
    }
    div {
        ref = elRef
    }
}

/**
 * An extension function to simplify kvisionWrapper usage.
 */
fun ChildrenBuilder.kv(builder: Container.() -> Unit) = child(kvisionWrapper(builder), obj {})
