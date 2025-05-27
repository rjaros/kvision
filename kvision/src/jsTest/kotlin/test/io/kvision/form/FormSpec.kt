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
package test.io.kvision.form

import kotlinx.serialization.Serializable
import io.kvision.form.Form
import io.kvision.form.check.CheckBox
import io.kvision.form.text.Text
import io.kvision.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Serializable
data class DataForm(
    val a: String? = null,
    val b: Boolean? = null,
    val c: String? = null
)

@Serializable
data class DataForm2(
    val s: String? = null,
    val d: String? = null
)

class FormSpec : SimpleSpec {

    @Test
    fun add() {
        run {
            val form = Form.create<DataForm>()
            val data = DataForm(a = "Test value")
            form.setData(data)
            val result = form.getData()
            assertEquals("Test value", result.a, "Form should return initial value without adding any control")
            val textField = Text()
            form.add(DataForm::a, textField)
            form.setData(data)
            val result2 = form.getData()
            assertEquals("Test value", result2.a, "Form should return initial value after adding a control")
        }
    }

    @Test
    fun remove() {
        run {
            val form = Form.create<DataForm>()
            val data = DataForm(a = "Test value")
            form.add(DataForm::a, Text())
            form.setData(data)
            form.remove(DataForm::a)
            val result = form.getData()
            assertNull(result.a, "Form should return null after removing control")
        }
    }

    @Test
    fun removeAll() {
        run {
            val form = Form.create<DataForm>()
            val data = DataForm(a = "Test value")
            form.add(DataForm::a, Text())
            form.setData(data)
            form.removeAll()
            val result = form.getData()
            assertNull(result.a, "Form should return null after removing all controls")
        }
    }

    @Test
    fun getControl() {
        run {
            val form = Form.create<DataForm>()
            form.add(DataForm::a, Text())
            val control = form.getControl(DataForm::b)
            assertNull(control, "Should return null when there is no such control")
            val control2 = form.getControl(DataForm::a)
            assertNotNull(control2, "Should return correct control")
        }
    }

    @Test
    fun get() {
        run {
            val form = Form.create<DataForm>()
            val data = DataForm(a = "Test value")
            form.add(DataForm::a, Text())
            val b = form[DataForm::b]
            assertNull(b, "Should return null value when there is no added control")
            val a = form[DataForm::a]
            assertNull(a, "Should return null value when control is empty")
            form.setData(data)
            val a2 = form[DataForm::a]
            assertEquals("Test value", a2, "Should return correct value")
        }
    }

    @Test
    fun getData() {
        run {
            val form = Form.create<DataForm>()
            val data = DataForm(a = "Test value")
            val textField = Text()
            form.add(DataForm::a, textField)
            form.setData(data)
            textField.value = "New value"
            val result = form.getData()
            assertEquals("New value", result.a, "Form should return changed value")
        }
    }

    @Test
    fun validate() {
        run {
            val form = Form.create<DataForm2>()
            form.add(DataForm2::s, Text()) {
                (it.getValue()?.length ?: 0) > 4
            }
            form.add(DataForm2::d, Text(), required = true)
            form.setData(DataForm2(s = "123"))
            val valid = form.validate()
            assertEquals(false, valid, "Should be invalid with initial data")
            form.setData(DataForm2(s = "12345"))
            val valid2 = form.validate()
            assertEquals(false, valid2, "Should be invalid with partially changed data")
            form.setData(DataForm2(s = "12345", d = "abc"))
            val valid3 = form.validate()
            assertEquals(true, valid3, "Should be valid")
        }
    }

    @Test
    fun dynamicForm() {
        run {
            val form = Form<Map<String, Any?>>()
            val data = mapOf("a" to "Test value", "b" to true, "c" to 5)
            form.setData(data)
            val result = form.getData()
            assertEquals(data, result, "Dynamic form should return initial value without adding any control")
            val textField = Text()
            form.add("a", textField)
            val check = CheckBox()
            form.add("b", check)
            form.setData(data)
            val result2 = form.getData()
            assertEquals(data, result2, "Dynamic form should return initial value after adding a control")
            assertEquals(data["a"], textField.value, "Text field value should be set correctly")
            assertEquals(data["b"], check.value, "CheckBox field value should be set correctly")
            textField.value = "Test value 2"
            check.value = false
            val result3 = form.getData()
            assertEquals(mapOf("a" to "Test value 2", "b" to false, "c" to 5), result3, "Dynamic form should return changed value")
        }
    }

}
