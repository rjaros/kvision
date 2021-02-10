/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
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
package io.kvision.remote

import org.testng.annotations.Test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

class NameGeneratorKtTest {
    @Test
    fun createNameGenerator_generatesNamesWithPrefixAndCounter() {
        // setup
        val generator1 = createNameGenerator("some prefix")
        val generator2 = createNameGenerator("other prefix")

        // execution
        val actual1 = Array(3) { generator1() }
        val actual2 = Array(3) { generator2() }

        // evaluation
        assertThat(actual1, arrayContaining("some prefix0", "some prefix1", "some prefix2"))
        assertThat(actual2, arrayContaining("other prefix0", "other prefix1", "other prefix2"))
    }
}
