package test.com.example

import pl.treksoft.jquery.jQuery
import kotlin.browser.document

interface TestSpec {
    fun beforeTest()

    fun afterTest()

    fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }
}

interface DomSpec : TestSpec {

    override fun beforeTest() {
        val fixture = "<div style=\"display: none\" id=\"pretest\">" +
                "<div id=\"helloworld\"></div></div>"
        document.body?.insertAdjacentHTML("afterbegin", fixture)
    }

    override fun afterTest() {
        val div = document.getElementById("pretest")
        div?.remove()
        jQuery(`object` = ".modal-backdrop").remove()
    }

}
