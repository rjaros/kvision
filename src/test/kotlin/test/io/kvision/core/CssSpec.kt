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
package test.io.kvision.core

import io.kvision.core.Col
import io.kvision.core.Color
import io.kvision.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class CssSpec : SimpleSpec {

    @Test
    fun colorHex() {
        run {
            val color = Color.hex(0x112233)
            assertEquals(
                "#112233",
                color.asString(),
                "Should generate color from hex integer"
            )
        }
    }

    @Test
    fun colorName() {
        run {
            val color = Color.name(Col.BLUE)
            assertEquals(
                "blue",
                color.asString(),
                "Should generate color from name"
            )
        }
    }

    @Test
    fun colorRgb() {
        run {
            val color = Color.rgb(17, 34, 51)
            assertEquals(
                "#112233",
                color.asString(),
                "Should generate color from RGB values"
            )
        }
    }

    @Test
    fun colorRgba() {
        run {
            val color = Color.rgba(17, 34, 51, 221)
            assertEquals(
                "#112233dd",
                color.asString(),
                "Should generate color from RGBA values"
            )
        }
    }
}
