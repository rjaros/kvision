/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.progress

import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.progress.ProgressBarStyle
import pl.treksoft.kvision.progress.ProgressIndicator
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class ProgressIndicatorSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val ind = ProgressIndicator(50, style = ProgressBarStyle.SUCCESS, striped = true)
            root.add(ind)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"progress-bar progress-bar-success progress-bar-striped\" role=\"progressbar\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 50%;\"></div>",
                element?.innerHTML,
                "Should render correct progress bar indicator"
            )
            ind.max = 200
            assertEqualsHtml(
                "<div class=\"progress-bar progress-bar-success progress-bar-striped\" role=\"progressbar\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"200\" style=\"width: 25%;\"></div>",
                element?.innerHTML,
                "Should render correct progress bar indicator after max value change"
            )

        }
    }

}
