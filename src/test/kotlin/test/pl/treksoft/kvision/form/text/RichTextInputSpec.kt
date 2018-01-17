package test.pl.treksoft.kvision.form.text

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.text.RichTextInput
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class RichTextInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val hai = RichTextInput(value = "abc").apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(hai)
            val id = document.getElementById("test")?.let { jQuery(it).find("trix-editor").attr("trix-id") } ?: "0"
            val content = document.getElementById("test")?.let { jQuery(it).find("trix-editor")[0]?.outerHTML }
            assertEquals(
                "<trix-editor contenteditable=\"\" class=\"form-control trix-control\" id=\"idti\" placeholder=\"place\" trix-id=\"$id\" input=\"trix-input-$id\" toolbar=\"trix-toolbar-$id\"></trix-editor>",
                content,
                "Should render correct html area control"
            )
        }
    }

}