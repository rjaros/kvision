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

package io.kvision.chart.js

open external class Animation(cfg: AnyObject, target: AnyObject, prop: String, to: Any = definedExternally) {
    open fun active(): Boolean
    open fun update(cfg: AnyObject, to: Any, date: Number)
    open fun cancel()
    open fun tick(date: Number)
}

external interface AnimationEvent {
    var chart: Chart
    var numSteps: Number
    var initial: Boolean
    var currentStep: Number
}

open external class Animator {
    open fun listen(chart: Chart, event: String /* "complete" | "progress" */, cb: (event: AnimationEvent) -> Unit)
    open fun add(chart: Chart, items: Array<Animation>)
    open fun has(chart: Chart): Boolean
    open fun start(chart: Chart)
    open fun running(chart: Chart): Boolean
    open fun stop(chart: Chart)
    open fun remove(chart: Chart): Boolean
}

open external class Animations(chart: Chart, animations: AnyObject) {
    open fun configure(animations: AnyObject)
    open fun update(target: AnyObject, values: AnyObject): Boolean?
}
