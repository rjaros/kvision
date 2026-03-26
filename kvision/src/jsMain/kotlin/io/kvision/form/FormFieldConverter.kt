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
package io.kvision.form

/**
 * Converts between a model property value and the form control's
 * value/JSON representation. Registered per-field via [Form.add] or
 * [Form.registerConverter] to handle custom types beyond the built-in
 * String/Number/Boolean/Date/KFiles.
 */
interface FormFieldConverter {
    /**
     * Convert a JSON-dynamic value (from the serialized model) to the value
     * expected by [FormControl.setValue].
     * Called during [Form.setData] to populate form controls.
     *
     * @param jsonValue the dynamic JSON value from the serialized model
     * @return the value to pass to FormControl.setValue()
     */
    fun fromJson(jsonValue: dynamic): Any?

    /**
     * Convert a [FormControl.getValue] result to a JSON-dynamic value
     * suitable for model deserialization.
     * Called during [Form.getData] to collect form values.
     *
     * @param controlValue the value returned by FormControl.getValue()
     * @return the dynamic JSON value for model deserialization
     */
    fun toJson(controlValue: Any?): dynamic
}