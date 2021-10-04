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

import io.kvision.form.FormPanel
import io.kvision.form.text.Text
import io.kvision.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Suppress("CanBeParameter")
class FormPanelSpec : SimpleSpec {

    @Test
    fun add() {
        run {
            val formPanel = FormPanel.create<DataForm>()
            val data = DataForm(a = "Test value")
            formPanel.setData(data)
            val result = formPanel.getData()
            assertEquals("Test value", result.a, "FormPanel should return initial value without adding any control")
            val textField = Text()
            formPanel.add(DataForm::a, textField)
            formPanel.setData(data)
            val result2 = formPanel.getData()
            assertEquals("Test value", result2.a, "FormPanel should return initial value after adding a control")
        }
    }

    @Test
    fun remove() {
        run {
            val formPanel = FormPanel.create<DataForm>()
            val data = DataForm(a = "Test value")
            formPanel.add(DataForm::a, Text())
            formPanel.setData(data)
            formPanel.remove(DataForm::a)
            val result = formPanel.getData()
            assertNull(result.a, "FormPanel should return null after removing control")
        }
    }

    @Test
    fun removeAll() {
        run {
            val formPanel = FormPanel.create<DataForm>()
            val data = DataForm(a = "Test value")
            formPanel.add(DataForm::a, Text())
            formPanel.setData(data)
            formPanel.removeAll()
            val result = formPanel.getData()
            assertNull(result.a, "FormPanel should return null after removing all controls")
        }
    }

    @Test
    fun getControl() {
        run {
            val formPanel = FormPanel.create<DataForm>()
            formPanel.add(DataForm::a, Text())
            val control = formPanel.getControl(DataForm::b)
            assertNull(control, "Should return null when there is no such control")
            val control2 = formPanel.getControl(DataForm::a)
            assertNotNull(control2, "Should return correct control")
        }
    }

    @Test
    fun get() {
        run {
            val formPanel = FormPanel.create<DataForm>()
            val data = DataForm(a = "Test value")
            formPanel.add(DataForm::a, Text())
            val b = formPanel[DataForm::b]
            assertNull(b, "Should return null value when there is no added control")
            val a = formPanel[DataForm::a]
            assertNull(a, "Should return null value when control is empty")
            formPanel.setData(data)
            val a2 = formPanel[DataForm::a]
            assertEquals("Test value", a2, "Should return correct value")
        }
    }

    @Test
    fun getData() {
        run {
            val formPanel = FormPanel.create<DataForm>()
            val data = DataForm(a = "Test value")
            val textField = Text()
            formPanel.add(DataForm::a, textField)
            formPanel.setData(data)
            textField.value = "New value"
            val result = formPanel.getData()
            assertEquals("New value", result.a, "Form should return changed value")
        }
    }

    @Test
    fun validate() {
        run {
            val formPanel = FormPanel.create<DataForm2>()
            formPanel.add(DataForm2::s, Text()) {
                (it.getValue()?.length ?: 0) > 4
            }
            formPanel.add(DataForm2::d, Text(), required = true)
            formPanel.setData(DataForm2(s = "123"))
            val valid = formPanel.validate()
            assertEquals(false, valid, "Should be invalid with initial data")
            formPanel.setData(DataForm2(s = "12345"))
            val valid2 = formPanel.validate()
            assertEquals(false, valid2, "Should be invalid with partially changed data")
            formPanel.setData(DataForm2(s = "12345", d = "abc"))
            val valid3 = formPanel.validate()
            assertEquals(true, valid3, "Should be valid")
        }
    }

}