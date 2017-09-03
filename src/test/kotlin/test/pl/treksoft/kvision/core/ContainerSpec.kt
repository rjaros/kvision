package test.pl.treksoft.kvision.core

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.core.Widget
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class ContainerSpec : DomSpec {

    @Test
    fun add() {
        run {
            val root = Root("test")
            val container = Container()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.add(child1)
            container.add(child2)
            root.add(container)
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("Container renders children") { elem1 != null && elem2 != null }
        }
    }

    @Test
    fun addAll() {
        run {
            val root = Root("test")
            val container = Container()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.addAll(listOf(child1, child2))
            root.add(container)
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("Container renders children") { elem1 != null && elem2 != null }
        }
    }

    @Test
    fun remove() {
        run {
            val root = Root("test")
            val container = Container()
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
            assertTrue("Container renders children") { elem1 != null && elem2 == null }
        }
    }

    @Test
    fun removeAt() {
        run {
            val root = Root("test")
            val container = Container()
            val child1 = Widget()
            child1.id = "child1"
            val child2 = Widget()
            child2.id = "child2"
            container.add(child1)
            container.add(child2)
            root.add(container)
            container.removeAt(0)
            val elem1 = document.getElementById("child1")
            val elem2 = document.getElementById("child2")
            assertTrue("Container renders children") { elem1 == null && elem2 != null }
        }
    }
}