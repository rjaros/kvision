package test.pl.treksoft.kvision.form.spinner

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.spinner.Spinner
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class SpinnerSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val ti = Spinner(value = 13, label = "Label").apply {
                placeholder = "place"
                name = "name"
                disabled = true
            }
            root.add(ti)
            val element = document.getElementById("test")
            val id = ti.input.id
            assertEquals(
                "<div class=\"form-group\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"input-group kv-spinner-btn-vertical\"><span><div class=\"input-group bootstrap-touchspin\"><span class=\"input-group-addon bootstrap-touchspin-prefix\" style=\"display: none;\"></span><input class=\"form-control\" id=\"$id\" type=\"text\" value=\"13\" placeholder=\"place\" name=\"name\" disabled=\"\" style=\"display: block;\"><span class=\"input-group-addon bootstrap-touchspin-postfix\" style=\"display: none;\"></span><span class=\"input-group-btn-vertical\"><button class=\"btn btn-default bootstrap-touchspin-up\" type=\"button\"><i class=\"glyphicon glyphicon-chevron-up\"></i></button><button class=\"btn btn-default bootstrap-touchspin-down\" type=\"button\"><i class=\"glyphicon glyphicon-chevron-down\"></i></button></span></div></span></div></div>",
                element?.innerHTML,
                "Should render correct spinner input form control"
            )
            ti.validatorError = "Validation Error"
            assertEquals(
                "<div class=\"form-group has-error\"><label class=\"control-label\" for=\"$id\">Label</label><div class=\"input-group kv-spinner-btn-vertical\"><span><div class=\"input-group bootstrap-touchspin\"><span class=\"input-group-addon bootstrap-touchspin-prefix\" style=\"display: none;\"></span><input class=\"form-control\" id=\"$id\" type=\"text\" value=\"13\" placeholder=\"place\" name=\"name\" disabled=\"\" style=\"display: block;\"><span class=\"input-group-addon bootstrap-touchspin-postfix\" style=\"display: none;\"></span><span class=\"input-group-btn-vertical\"><button class=\"btn btn-default bootstrap-touchspin-up\" type=\"button\"><i class=\"glyphicon glyphicon-chevron-up\"></i></button><button class=\"btn btn-default bootstrap-touchspin-down\" type=\"button\"><i class=\"glyphicon glyphicon-chevron-down\"></i></button></span></div></span></div><span class=\"help-block small\">Validation Error</span></div>",
                element?.innerHTML,
                "Should render correct spinner input form control with validation error"
            )
        }
    }

    @Test
    fun spinUp() {
        run {
            val root = Root("test")
            val si = Spinner(value = 13)
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinUp")
            si.spinUp()
            assertEquals(14, si.value, "Should return changed value after spinUp")
        }
    }

    @Test
    fun spinDown() {
        run {
            val root = Root("test")
            val si = Spinner(value = 13)
            root.add(si)
            assertEquals(13, si.value, "Should return initial value before spinDown")
            si.spinDown()
            assertEquals(12, si.value, "Should return changed value after spinDown")
        }
    }
}