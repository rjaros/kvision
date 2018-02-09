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
package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.panel.SimplePanel
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class ContainerSpec : DomSpec {

    @Test
    fun add() {
        run {
            val root = Root("test")
            val container = SimplePanel()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.add(child1)
            container.add(child2)
            root.add(container)
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("SimplePanel renders children") { elem1 != null && elem2 != null }
        }
    }

    @Test
    fun addAll() {
        run {
            val root = Root("test")
            val container = SimplePanel()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.addAll(listOf(child1, child2))
            root.add(container)
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("SimplePanel renders children") { elem1 != null && elem2 != null }
        }
    }

    @Test
    fun remove() {
        run {
            val root = Root("test")
            val container = SimplePanel()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.add(child1)
            container.add(child2)
            root.add(container)
            container.remove(child2)
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("SimplePanel renders children") { elem1 != null && elem2 == null }
        }
    }

    @Test
    fun removeAll() {
        run {
            val root = Root("test")
            val container = SimplePanel()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.add(child1)
            container.add(child2)
            root.add(container)
            container.removeAll()
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("Children are not rendered") { elem1 == null && elem2 == null }
        }
    }

    @Test
    fun getChildren() {
        run {
            val root = Root("test")
            val container = SimplePanel()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.add(child1)
            container.add(child2)
            root.add(container)
            val childern = container.getChildren()
            assertTrue("Returns children of current element") { childern.size == 2 }
        }
    }
}