package com.example

import pl.treksoft.kvision.ApplicationBase
import pl.treksoft.kvision.core.BORDERSTYLE
import pl.treksoft.kvision.core.Border
import pl.treksoft.kvision.core.COLOR
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.panel.TabPanel
import pl.treksoft.kvision.utils.auto
import pl.treksoft.kvision.utils.perc
import pl.treksoft.kvision.utils.px

class Showcase : ApplicationBase() {

    override fun start(state: Map<String, Any>) {
        val root = Root("showcase")
        val tabPanel = TabPanel().apply {
            width = 80.perc()
            margin = 20.px()
            marginLeft = auto()
            marginRight = auto()
            padding = 20.px()
            border = Border(2.px(), BORDERSTYLE.SOLID, COLOR.SILVER)
        }
        tabPanel.addTab("Basic formatting", BasicTab(), "fa-bars", route = "/basic")
        tabPanel.addTab("Forms", FormTab(), "fa-edit", route = "/forms")
        tabPanel.addTab("Buttons", ButtonsTab(), "fa-check-square-o", route = "/buttons")
        tabPanel.addTab("Dropdowns", DropDownTab(), "fa-arrow-down", route = "/dropdowns")
        tabPanel.addTab("Containers", ContainersTab(), "fa-database", route = "/containers")
        tabPanel.addTab("Layouts", LayoutsTab(), "fa-th-list", route = "/layouts")
        tabPanel.addTab("Modals", ModalsTab(), "fa-window-maximize", route = "/modals")
        tabPanel.addTab("Data binding", DataTab(), "fa-retweet", route = "/data")
        root.add(tabPanel)
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }

    companion object {
        val css = require("./css/style.css")
    }
}
