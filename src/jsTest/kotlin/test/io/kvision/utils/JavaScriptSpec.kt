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
package test.io.kvision.utils

import io.kvision.test.SimpleSpec
import io.kvision.utils.deepMerge
import io.kvision.utils.obj
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaScriptSpec : SimpleSpec {

    @Test
    fun deepMergeTest() {
        run {
            val target = obj {
                a = obj {
                    b = obj {
                        c = "c"
                        d = "d"
                    }
                }
            }
            val source = obj {
                a = obj {
                    b = obj {
                        e = "e"
                    }
                }
            }
            val result = deepMerge(target, source)
            assertEquals("{\"a\":{\"b\":{\"c\":\"c\",\"d\":\"d\",\"e\":\"e\"}}}", JSON.stringify(result), "Should deeply merge two JS objects")
        }
    }
}
