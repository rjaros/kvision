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