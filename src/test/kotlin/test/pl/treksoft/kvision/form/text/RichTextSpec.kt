package test.pl.treksoft.kvision.form.text

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.panel.Root
import pl.treksoft.kvision.form.text.RichText
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class RichTextSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val hai = RichText(value = "abc", label = "Field").apply {
                placeholder = "place"
                id = "idti"
            }
            root.add(hai)
            val id = document.getElementById("test")?.let { jQuery(it).find("trix-editor").attr("trix-id") } ?: "0"
            val iid = hai.input.id
            val content = document.getElementById("test")?.let { jQuery(it).find("trix-editor")[0]?.outerHTML }
            assertEquals(
                "<trix-editor contenteditable=\"\" class=\"form-control trix-control\" id=\"$iid\" placeholder=\"place\" trix-id=\"$id\" input=\"trix-input-$id\" toolbar=\"trix-toolbar-$id\"></trix-editor>",
                content,
                "Should render correct html area form control"
            )
            val label = document.getElementById("test")?.let { jQuery(it).find("label")[0]?.outerHTML }
            assertEquals(
                "<label class=\"control-label\" for=\"$iid\">Field</label>",
                label,
                "Should render correct label for html area form control"
            )
        }
    }

}