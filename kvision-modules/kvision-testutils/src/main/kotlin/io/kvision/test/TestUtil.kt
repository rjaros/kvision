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
package io.kvision.test

import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase
import io.kvision.core.Widget
import io.kvision.jquery.JQuery
import io.kvision.jquery.get
import io.kvision.jquery.invoke
import io.kvision.jquery.jQuery
import io.kvision.panel.Root
import io.kvision.test.Formatting.normalizeHtml
import kotlin.js.Promise
import kotlinx.browser.document
import kotlinx.coroutines.test.runTest
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.asList

interface TestSpec {
    fun beforeTest()

    fun afterTest()

    fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }

    /** Coroutine test, using [runTest] */
    fun runSuspend(code: suspend () -> Unit) = runTest {
        beforeTest()
        code()
        afterTest()
    }

    fun runAsync(code: (resolve: () -> Unit, reject: (Throwable) -> Unit) -> Unit): Promise<Unit> {
        beforeTest()
        @Suppress("UnsafeCastFromDynamic")
        return Promise<Unit> { resolve, reject ->
            code({ resolve(Unit) }, reject)
        }.asDynamic().finally {
            afterTest()
        }
    }
}

interface SimpleSpec : TestSpec {

    override fun beforeTest() {
    }

    override fun afterTest() {
    }

}

interface DomSpec : TestSpec {

    fun getTestId() = "test"

    override fun beforeTest() {
        val fixture = "<div style=\"display: none\" id=\"pretest\">" +
            "<div id=\"${getTestId()}\"></div></div>"
        document.body?.insertAdjacentHTML("afterbegin", fixture)
    }

    override fun afterTest() {
        val div = document.getElementById("pretest")
        div?.let { jQuery(it).remove() }
        jQuery(".modal-backdrop").remove()
        Root.disposeAllRoots()
    }

    fun assertEqualsHtml(
        expected: String?,
        actual: String?,
        message: String? = null,
        normalizeHtml: Boolean = false,
    ) {
        withClue(message) {
            if (!areEqualNodes(expected, actual)) {
                if (normalizeHtml) {
                    normalizeHtml(actual) shouldBe normalizeHtml(expected)
                } else {
                    actual shouldBe expected
                }
            }
        }
    }

    fun assertContainsHtml(
        expected: String,
        actual: String?,
        message: String? = null,
        ignoreCase: Boolean = false
    ) {
        withClue(message) {
            if (ignoreCase) {
                normalizeHtml(actual) shouldContainIgnoringCase normalizeHtml(expected)
            } else {
                normalizeHtml(actual) shouldContain normalizeHtml(expected)
            }
        }
    }

    companion object {

        /**
         * Compare the provided strings by [Node.isEqualNode]. The class lists are normalized using
         * JQuery.
         *
         * @return `true` if [expected] and [actual] are equal. Returns `false` if either is null,
         * blank, or [Node.isEqualNode] returns false.
         * @see [normalizeClassListRecursive]
         */
        fun areEqualNodes(expected: String?, actual: String?): Boolean {
            return if (expected.isNullOrBlank() || actual.isNullOrBlank()) {
                false
            } else {
                val exp = jQuery(expected).normalizeClassListRecursive()
                val act = jQuery(actual).normalizeClassListRecursive()
                exp[0]?.isEqualNode(act[0]) == true
            }
        }

    }
}

interface WSpec : DomSpec {

    fun runW(code: (widget: Widget, element: Element?) -> Unit) {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val widget = Widget()
            widget.id = "test_id"
            root.add(widget)
            val element = document.getElementById("test_id")
            code(widget, element)
        }
    }

}

fun removeAllAfter(referenceElement: Node) {
    var element = referenceElement
    while (true) {
        while (element.nextSibling?.also { it.parentNode?.removeChild(it) } != null) {
            // intentionally blank
        }
        element = element.parentNode ?: return
    }
}

private fun JQuery.normalizeClassList(): JQuery {
    each { _, elem ->
        elem.setAttribute("class", elem.classList.asList().sorted().joinToString(" "))
    }
    return this
}

private fun JQuery.normalizeClassListRecursive(): JQuery {
    jQuery("*", this).addBack().normalizeClassList()
    return this
}

fun toKeyValuePairString(obj: dynamic) =
    js("Object")
        .entries(obj)
        .unsafeCast<Array<Array<Any?>>>()
        .map { pair -> pair.map { it.toString() } }
        .apply { sortedBy { it[0] } }
        .joinToString(",") { it.joinToString("=") }

external fun require(name: String): dynamic
