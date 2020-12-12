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
package test.pl.treksoft.kvision.progress

import kotlinx.browser.document
import org.w3c.dom.Element
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.progress.FormatFractionContentGenerator
import pl.treksoft.kvision.progress.progress
import pl.treksoft.kvision.progress.progressNumeric
import pl.treksoft.kvision.test.DomSpec
import kotlin.test.Test

class ProgressBarSpec : DomSpec {
    private lateinit var root: Root
    private lateinit var element: Element

    override fun beforeTest() {
        super.beforeTest()
        root = Root("test", containerType = pl.treksoft.kvision.panel.ContainerType.FIXED)
        element = requireNotNull(document.getElementById("test"))
    }

    @Test
    fun simpleProgress_rendersCorrectly() {
        run {
            // execution
            root.progress { progressNumeric(25) }

            // evaluation
            assertEqualsHtml(
                """<div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100" style="width: 25%;"></div></div>""",
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

    @Test
    fun simpleProgress_withAdditionalBarClass_rendersCorrectly() {
        run {
            // execution
            root.progress { progressNumeric(25, classes = setOf("class-for-bar")) }

            // evaluation
            assertEqualsHtml(
                """<div class="progress"><div class="class-for-bar progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100" style="width: 25%;"></div></div>""",
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

    @Test
    fun multipleProgressBars_rendersCorrectly() {
        run {
            // execution
            root.progress {
                progressNumeric(25)
                progressNumeric(30, classes = setOf("second-progress-bar"))
            }

            // evaluation
            assertEqualsHtml(
                """
                    |<div class="progress">
                        |<div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100" style="width: 25%;"></div>
                        |<div class="second-progress-bar progress-bar" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 30%;"></div>
                    |</div>
                """.trimMargin().replace("\n", ""),
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

    @Test
    fun multipleProgressBars_withCustomBounds_renderCorrectly() {
        run {
            // execution
            root.progress(100, 110) {
                progressNumeric(102)
                progressNumeric(104)
            }

            // evaluation
            assertEqualsHtml(
                """
                    |<div class="progress">
                        |<div class="progress-bar" role="progressbar" aria-valuenow="102" aria-valuemin="100" aria-valuemax="110" style="width: 20%;"></div>
                        |<div class="progress-bar" role="progressbar" aria-valuenow="104" aria-valuemin="100" aria-valuemax="110" style="width: 40%;"></div>
                    |</div>
                """.trimMargin().replace("\n", ""),
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

    @Test
    fun multipleProgressBars_updates_ifBoundsAreChanged() {
        run {
            // setup
            val progress = root.progress {
                progressNumeric(12)
                progressNumeric(14)
            }

            // execution
            progress.setBounds(10, 20)

            // evaluation
            assertEqualsHtml(
                """
                    |<div class="progress">
                        |<div class="progress-bar" role="progressbar" aria-valuenow="12" aria-valuemin="10" aria-valuemax="20" style="width: 20%;"></div>
                        |<div class="progress-bar" role="progressbar" aria-valuenow="14" aria-valuemin="10" aria-valuemax="20" style="width: 40%;"></div>
                    |</div>
                """.trimMargin().replace("\n", ""),
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

    @Test
    fun multipleProgressBars_updates_ifValueChanges() {
        run {
            // setup
            val progress = root.progress()
            val bar1 = progress.progressNumeric()
            val bar2 = progress.progressNumeric()

            // execution
            bar1.value = 10
            bar2.value = 20

            // evaluation
            assertEqualsHtml(
                """
                    |<div class="progress">
                        |<div class="progress-bar" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100" style="width: 10%;"></div>
                        |<div class="progress-bar" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%;"></div>
                    |</div>
                """.trimMargin().replace("\n", ""),
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

    @Test
    fun rendersUsingContentGenerator_ifSupplied() {
        run {
            // execution
            root.progress(10, 20) {
                progressNumeric(12, contentGenerator = FormatFractionContentGenerator())
            }

            // evaluation
            assertEqualsHtml(
                """
                    |<div class="progress">
                        |<div class="progress-bar" role="progressbar" aria-valuenow="12" aria-valuemin="10" aria-valuemax="20" style="width: 20%;">20%</div>
                    |</div>
                """.trimMargin().replace("\n", ""),
                element.innerHTML,
                "Should render correct progress bar"
            )
        }
    }

}
