package com.example

import pl.treksoft.kvision.form.check.CHECKBOXSTYLE
import pl.treksoft.kvision.form.check.CheckBox
import pl.treksoft.kvision.form.check.RADIOSTYLE
import pl.treksoft.kvision.form.check.Radio
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.panel.HPanel
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.panel.VPanel
import pl.treksoft.kvision.utils.px

class ButtonsTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        val mainPanel = HPanel(spacing = 100)
        val buttonsPanel = VPanel(spacing = 7)
        buttonsPanel.add(Button("Default button", style = BUTTONSTYLE.DEFAULT).apply { width = 200.px() })
        buttonsPanel.add(Button("Primary button", style = BUTTONSTYLE.PRIMARY).apply { width = 200.px() })
        buttonsPanel.add(Button("Success button", style = BUTTONSTYLE.SUCCESS).apply { width = 200.px() })
        buttonsPanel.add(Button("Info button", style = BUTTONSTYLE.INFO).apply { width = 200.px() })
        buttonsPanel.add(Button("Warning button", style = BUTTONSTYLE.WARNING).apply { width = 200.px() })
        buttonsPanel.add(Button("Danger button", style = BUTTONSTYLE.DANGER).apply { width = 200.px() })
        buttonsPanel.add(Button("Link button", style = BUTTONSTYLE.LINK).apply { width = 200.px() })
        mainPanel.add(buttonsPanel)
        val ckPanel = VPanel()
        ckPanel.add(CheckBox(true, label = "Default checkbox").apply { style = CHECKBOXSTYLE.DEFAULT })
        ckPanel.add(CheckBox(true, label = "Primary checkbox").apply { style = CHECKBOXSTYLE.PRIMARY })
        ckPanel.add(CheckBox(true, label = "Success checkbox").apply { style = CHECKBOXSTYLE.SUCCESS })
        ckPanel.add(CheckBox(true, label = "Info checkbox").apply { style = CHECKBOXSTYLE.INFO })
        ckPanel.add(CheckBox(true, label = "Warning checkbox").apply { style = CHECKBOXSTYLE.WARNING })
        ckPanel.add(CheckBox(true, label = "Danger checkbox").apply { style = CHECKBOXSTYLE.DANGER })
        ckPanel.add(CheckBox(true, label = "Circled checkbox").apply { circled = true })
        mainPanel.add(ckPanel)
        val radioPanel = VPanel()
        radioPanel.add(Radio(name = "radio", label = "Default radiobutton").apply { style = RADIOSTYLE.DEFAULT })
        radioPanel.add(Radio(name = "radio", label = "Primary radiobutton").apply { style = RADIOSTYLE.PRIMARY })
        radioPanel.add(Radio(name = "radio", label = "Success radiobutton").apply { style = RADIOSTYLE.SUCCESS })
        radioPanel.add(Radio(name = "radio", label = "Info radiobutton").apply { style = RADIOSTYLE.INFO })
        radioPanel.add(Radio(name = "radio", label = "Warning radiobutton").apply { style = RADIOSTYLE.WARNING })
        radioPanel.add(Radio(name = "radio", label = "Danger radiobutton").apply { style = RADIOSTYLE.DANGER })
        radioPanel.add(Radio(name = "radio", label = "Squared radiobutton").apply { squared = true })
        mainPanel.add(radioPanel)
        this.add(mainPanel)
    }
}