package com.example

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.html.IMAGESHAPE
import pl.treksoft.kvision.html.Image
import pl.treksoft.kvision.html.LIST
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.FLEXALIGNITEMS
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.panel.VPanel
import pl.treksoft.kvision.utils.px

class BasicTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        this.minHeight = 400.px()
        val panel = VPanel(spacing = 3)
        panel.add(Label("A simple label"))
        panel.add(Label("A list:"))
        panel.add(ListTag(LIST.UL, listOf("First list element", "Second list element", "Third list element")))
        panel.add(Label("An image:"))
        panel.add(Image(require("./img/dog.jpg"), shape = IMAGESHAPE.CIRCLE))
        panel.add(Tag(TAG.CODE, "Some text written in <code></code> HTML tag."))
        panel.add(
            Tag(
                TAG.DIV,
                "Rich <b>text</b> <i>written</i> with <span style=\"font-family: Verdana; font-size: 14pt\">" +
                        "any <strong>forma</strong>tting</span>.",
                rich = true
            )
        )
        panel.add(Link("A link to Google", "http://www.google.com"))
        this.add(panel)
    }
}