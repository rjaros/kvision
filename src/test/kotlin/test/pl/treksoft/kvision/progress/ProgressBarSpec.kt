/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.progress

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.progress.ProgressBar
import pl.treksoft.kvision.progress.ProgressBarStyle
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ProgressBarSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val progressBar =
                ProgressBar(50, style = ProgressBarStyle.SUCCESS, striped = true, content = "Processing ...")
            root.add(progressBar)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"progress\"><div class=\"progress-bar progress-bar-success progress-bar-striped\" role=\"progressbar\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 50%;\">Processing ...</div></div>",
                element?.innerHTML,
                "Should render correct progress bar"
            )
            progressBar.max = 200
            assertEqualsHtml(
                "<div class=\"progress\"><div class=\"progress-bar progress-bar-success progress-bar-striped\" role=\"progressbar\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"200\" style=\"width: 25%;\">Processing ...</div></div>",
                element?.innerHTML,
                "Should render correct progress bar after max value change"
            )

        }
    }

}
