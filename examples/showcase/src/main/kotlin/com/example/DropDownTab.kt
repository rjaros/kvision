package com.example

import pl.treksoft.kvision.dropdown.DD
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.Image
import pl.treksoft.kvision.panel.HPanel
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.panel.VPanel
import pl.treksoft.kvision.utils.px

class DropDownTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        this.minHeight = 400.px()
        val panel = VPanel(spacing = 30)
        val ndd = DropDown(
            "Dropdown with navigation menu", listOf(
                "Basic formatting" to "#!/basic",
                "Forms" to "#!/forms",
                "Buttons" to "#!/buttons",
                "Dropdowns" to "#!/dropdowns",
                "Containers" to "#!/containers"
            ), "fa-arrow-right", style = BUTTONSTYLE.SUCCESS
        ).apply {
            width = 250.px()
        }
        panel.add(ndd)

        val idd = DropDown("Dropdown with custom list", icon = "fa-picture-o", style = BUTTONSTYLE.WARNING).apply {
            width = 250.px()
        }
        idd.add(Image(require("./img/cat.jpg")).apply { margin = 10.px(); title = "Cat" })
        idd.add(Image(require("./img/dog.jpg")).apply { margin = 10.px(); title = "Dog" })
        panel.add(idd)

        val hpanel = HPanel(spacing = 5)
        val fdd = DropDown(
            "Dropdown with special options", listOf(
                "Header" to DD.HEADER.type,
                "Basic formatting" to "#!/basic",
                "Forms" to "#!/forms",
                "Buttons" to "#!/buttons",
                "Separator" to DD.SEPARATOR.type,
                "Dropdowns (disabled)" to DD.DISABLED.type,
                "Separator" to DD.SEPARATOR.type,
                "Containers" to "#!/containers"
            ), "fa-asterisk", style = BUTTONSTYLE.PRIMARY
        ).apply {
            dropup = true
            width = 250.px()
        }
        hpanel.add(fdd)
        val ddbutton = Button("Toggle dropdown", style = BUTTONSTYLE.INFO).onClick { e ->
            fdd.toggle()
            e.stopPropagation()
        }
        hpanel.add(ddbutton)
        panel.add(hpanel)
        this.add(panel)
    }
}