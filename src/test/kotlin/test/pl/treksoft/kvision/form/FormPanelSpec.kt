package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.form.FormPanel
import pl.treksoft.kvision.form.text.Text
import pl.treksoft.kvision.form.time.DateTime
import test.pl.treksoft.kvision.SimpleSpec
import kotlin.js.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Suppress("CanBeParameter")
class FormPanelSpec : SimpleSpec {

    @Test
    fun add() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val a: String? by map
                val b: Boolean? by map
                val c: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            val data = DataForm(mapOf("a" to "Test value"))
            formPanel.setData(data)
            val result = formPanel.getData()
            assertNull(result.a, "FormPanel should return null without adding any control")
            val textField = Text()
            formPanel.add("a", textField)
            formPanel.setData(data)
            val result2 = formPanel.getData()
            assertEquals("Test value", result2.a, "FormPanel should return initial value")
        }
    }

    @Test
    fun remove() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val a: String? by map
                val b: Boolean? by map
                val c: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            val data = DataForm(mapOf("a" to "Test value"))
            formPanel.add("a", Text())
            formPanel.setData(data)
            formPanel.remove("a")
            val result = formPanel.getData()
            assertNull(result.a, "FormPanel should return null after removing control")
        }
    }

    @Test
    fun removeAll() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val a: String? by map
                val b: Boolean? by map
                val c: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            val data = DataForm(mapOf("a" to "Test value"))
            formPanel.add("a", Text())
            formPanel.setData(data)
            formPanel.removeAll()
            val result = formPanel.getData()
            assertNull(result.a, "FormPanel should return null after removing all controls")
        }
    }

    @Test
    fun getControl() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val a: String? by map
                val b: Boolean? by map
                val c: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            formPanel.add("a", Text())
            val control = formPanel.getControl("b")
            assertNull(control, "Should return null when there is no such control")
            val control2 = formPanel.getControl("a")
            assertNotNull(control2, "Should return correct control")
        }
    }

    @Test
    fun get() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val a: String? by map
                val b: Boolean? by map
                val c: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            val data = DataForm(mapOf("a" to "Test value"))
            formPanel.add("a", Text())
            val b = formPanel["b"]
            assertNull(b, "Should return null value when there is no added control")
            val a = formPanel["a"]
            assertNull(a, "Should return null value when control is empty")
            formPanel.setData(data)
            val a2 = formPanel["a"]
            assertEquals("Test value", a2, "Should return correct value")
        }
    }

    @Test
    fun getData() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val a: String? by map
                val b: Boolean? by map
                val c: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            val data = DataForm(mapOf("a" to "Test value"))
            val textField = Text()
            formPanel.add("a", textField)
            formPanel.setData(data)
            textField.value = "New value"
            val result = formPanel.getData()
            assertEquals("New value", result.a, "Form should return changed value")
        }
    }

    @Test
    fun validate() {
        run {
            class DataForm(val map: Map<String, Any?>) {
                val s: String? by map
                val d: Date? by map
            }

            val formPanel = FormPanel {
                DataForm(it)
            }
            formPanel.add("s", Text()) {
                it.getValue()?.length ?: 0 > 4
            }
            formPanel.add("d", DateTime(), required = true)
            formPanel.setData(DataForm(mapOf("s" to "123")))
            val valid = formPanel.validate()
            assertEquals(false, valid, "Should be invalid with initial data")
            formPanel.setData(DataForm(mapOf("s" to "12345")))
            val valid2 = formPanel.validate()
            assertEquals(false, valid2, "Should be invalid with partially changed data")
            formPanel.setData(DataForm(mapOf("s" to "12345", "d" to Date())))
            val valid3 = formPanel.validate()
            assertEquals(true, valid3, "Should be valid")
        }
    }

}