package com.example

import com.lightningkite.kotlin.observable.list.observableListOf
import pl.treksoft.kvision.data.BaseDataComponent
import pl.treksoft.kvision.data.DataContainer
import pl.treksoft.kvision.form.check.CHECKBOXSTYLE
import pl.treksoft.kvision.form.check.CheckBox
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.panel.FLEXWRAP
import pl.treksoft.kvision.panel.HPanel
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.panel.VPanel
import pl.treksoft.kvision.utils.px

class DataTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        this.minHeight = 400.px()

        val panel = VPanel(spacing = 5)

        class DataModel(checked: Boolean, text: String) : BaseDataComponent() {
            var checked: Boolean by obs(checked)
            var text: String by obs(text)
        }

        val list = observableListOf(
            DataModel(false, "January"),
            DataModel(false, "February"),
            DataModel(false, "March"),
            DataModel(false, "April"),
            DataModel(false, "May"),
            DataModel(false, "June"),
            DataModel(false, "July"),
            DataModel(false, "August"),
            DataModel(false, "September"),
            DataModel(false, "October"),
            DataModel(false, "November")
        )
        val dataContainer = DataContainer(list, { index ->
            CheckBox(
                value = list[index].checked,
                label = if (list[index].checked) "<b>${list[index].text}</b>" else "${list[index].text}"
            ).apply {
                rich = true
                style = CHECKBOXSTYLE.PRIMARY
                onClick {
                    list[index].checked = this.value
                }
            }
        }, child = HPanel(spacing = 10, wrap = FLEXWRAP.WRAP))
        panel.add(dataContainer)

        val butPanel = HPanel(spacing = 10, wrap = FLEXWRAP.WRAP)
        butPanel.add(Button("Add December", style = BUTTONSTYLE.SUCCESS).onClick {
            list.add(DataModel(true, "December"))
        })
        butPanel.add(Button("Check all", style = BUTTONSTYLE.INFO).onClick {
            list.forEach { it.checked = true }
        })
        butPanel.add(Button("Uncheck all", style = BUTTONSTYLE.INFO).onClick {
            list.forEach { it.checked = false }
        })
        butPanel.add(Button("Reverse list", style = BUTTONSTYLE.DANGER).onClick {
            list.reverse()
        })
        butPanel.add(Button("Remove checked", style = BUTTONSTYLE.DANGER).onClick {
            list.filter { it.checked }.forEach { list.remove(it) }
        })
        panel.add(butPanel)
        this.add(panel)
    }
}
