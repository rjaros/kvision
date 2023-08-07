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
package test.io.kvision.redux

import io.kvision.redux.createReduxStore
import redux.RAction
import io.kvision.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals

data class TestState(val counter: Int, val values: List<Int>)

sealed class TestAction : RAction {
    object Inc : TestAction()
    object Dec : TestAction()
}

fun testReducer(state: TestState, action: TestAction): TestState = when (action) {
    is TestAction.Inc -> {
        state.copy(counter = state.counter + 1, values = state.values + state.counter)
    }
    is TestAction.Dec -> {
        state.copy(counter = state.counter - 1, values = state.values + state.counter)
    }
}

class ReduxStoreSpec : SimpleSpec {

    @Test
    fun getState() {
        run {
            val store = createReduxStore(::testReducer, TestState(10, listOf()))
            assertEquals(TestState(10, listOf()), store.getState())
        }
    }

    @Test
    fun dispatch() {
        run {
            val store = createReduxStore(::testReducer, TestState(10, listOf()))
            store.dispatch(TestAction.Inc)
            store.dispatch(TestAction.Inc)
            store.dispatch(TestAction.Inc)
            store.dispatch(TestAction.Dec)
            store.dispatch(TestAction.Dec)
            assertEquals(TestState(11, listOf(10, 11, 12, 13, 12)), store.getState())
        }
    }

    @Test
    fun subscribe() {
        run {
            var counter = 0
            val store = createReduxStore(::testReducer, TestState(10, listOf()))
            store.subscribe {
                counter++
            }
            store.dispatch(TestAction.Inc)
            store.dispatch(TestAction.Dec)
            assertEquals(3, counter)
        }
    }
}
