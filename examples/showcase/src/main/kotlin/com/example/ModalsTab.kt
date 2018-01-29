package com.example

import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.Image
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.modal.Confirm
import pl.treksoft.kvision.modal.Modal
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.panel.VPanel
import pl.treksoft.kvision.utils.px

class ModalsTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        this.minHeight = 400.px()
        val panel = VPanel(spacing = 30)
        val alertButton = Button("Alert dialog", style = BUTTONSTYLE.DANGER).onClick {
            Alert.show(
                "Alert dialog",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor."
            )
        }
        panel.add(alertButton)
        val confirmButton = Button("Confirm dialog", style = BUTTONSTYLE.WARNING).onClick {
            Confirm.show(
                "Confirm dialog",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor.",
                noCallback = {
                    Alert.show("Result", "You pressed NO button.")
                }) {
                Alert.show("Result", "You pressed YES button.")
            }
        }
        panel.add(confirmButton)
        val confirmButtonC = Button("Cancelable confirm dialog", style = BUTTONSTYLE.INFO).onClick {
            Confirm.show(
                "Cancelable confirm dialog",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor.",
                align = ALIGN.CENTER,
                cancelVisible = true,
                noCallback = {
                    Alert.show("Result", "You pressed NO button.")
                }) {
                Alert.show("Result", "You pressed YES button.")
            }
        }
        panel.add(confirmButtonC)

        val modal = Modal("Custom modal dialog")
        modal.add(
            Tag(
                TAG.H4,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor."
            )
        )
        modal.add(Image(require("./img/dog.jpg")))
        modal.addButton(Button("Close").onClick {
            modal.hide()
        })
        val modalButton = Button("Custom modal dialog", style = BUTTONSTYLE.SUCCESS).onClick {
            modal.show()
        }
        panel.add(modalButton)
        val fastAlertButton = Button("Alert dialog without animation", style = BUTTONSTYLE.PRIMARY).onClick {
            Alert.show(
                "Alert dialog without animation",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor.",
                animation = false
            )
        }
        panel.add(fastAlertButton)
        this.add(panel)
    }
}
