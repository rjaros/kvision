package com.example

import pl.treksoft.kvision.ApplicationBase
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.FLEXDIR
import pl.treksoft.kvision.panel.FLEXJUSTIFY
import pl.treksoft.kvision.panel.FlexPanel
import pl.treksoft.kvision.utils.px

class Helloworld : ApplicationBase() {

    override fun start(state: Map<String, Any>) {
        val root = Root("helloworld")
        val panel = FlexPanel(FLEXDIR.ROW, justify = FLEXJUSTIFY.CENTER)
        val hello = Tag(TAG.DIV, "Hello world!", classes = setOf("helloworld")).apply {
            marginTop = 50.px()

        }
        panel.add(hello)
        root.add(panel)
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }

    companion object {
        val css = require("./css/style.css")
    }
}
