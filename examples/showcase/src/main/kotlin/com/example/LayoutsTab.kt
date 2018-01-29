package com.example

import pl.treksoft.kvision.core.Background
import pl.treksoft.kvision.core.COLOR
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.CssSize
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.*
import pl.treksoft.kvision.utils.perc
import pl.treksoft.kvision.utils.px

class LayoutsTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        this.minHeight = 400.px()
        val panel = VPanel(spacing = 5)
        addHPanel(panel)
        addVPanel(panel)
        addFlexPanel1(panel)
        addFlexPanel2(panel)
        addFlexPanel3(panel)
        addFlexPanel4(panel)
        addFlexPanel5(panel)
        addGridPanel1(panel)
        addGridPanel2(panel)
        addRespGridPanel(panel)
        addDockPanel(panel)
        this.add(panel)
    }

    private fun addHPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Horizontal layout"))
        val hpanel = HPanel(spacing = 5)
        hpanel.add(getDiv("1", 100))
        hpanel.add(getDiv("2", 150))
        hpanel.add(getDiv("3", 200))
        panel.add(hpanel)
    }

    private fun addVPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Vertical layout"))
        val vpanel = VPanel(spacing = 5)
        vpanel.add(getDiv("1", 100))
        vpanel.add(getDiv("2", 150))
        vpanel.add(getDiv("3", 200))
        panel.add(vpanel)
    }

    private fun addFlexPanel1(panel: Container) {
        panel.add(Tag(TAG.H4, "CSS flexbox layouts"))
        val flexpanel = FlexPanel(
            FLEXDIR.ROW, FLEXWRAP.WRAP, FLEXJUSTIFY.FLEXEND, FLEXALIGNITEMS.CENTER,
            spacing = 5
        )
        flexpanel.add(getDiv("1", 100))
        flexpanel.add(getDiv("2", 150))
        flexpanel.add(getDiv("3", 200))
        panel.add(flexpanel)
    }

    private fun addFlexPanel2(panel: Container) {
        val flexpanel = FlexPanel(
            FLEXDIR.ROW, FLEXWRAP.WRAP, FLEXJUSTIFY.SPACEBETWEEN, FLEXALIGNITEMS.CENTER,
            spacing = 5
        )
        flexpanel.add(getDiv("1", 100))
        flexpanel.add(getDiv("2", 150))
        flexpanel.add(getDiv("3", 200))
        panel.add(flexpanel)
    }

    private fun addFlexPanel3(panel: Container) {
        val flexpanel = FlexPanel(
            FLEXDIR.ROW, FLEXWRAP.WRAP, FLEXJUSTIFY.CENTER, FLEXALIGNITEMS.CENTER,
            spacing = 5
        )
        flexpanel.add(getDiv("1", 100))
        flexpanel.add(getDiv("2", 150))
        flexpanel.add(getDiv("3", 200))
        panel.add(flexpanel)
    }

    private fun addFlexPanel4(panel: Container) {
        val flexpanel = FlexPanel(
            FLEXDIR.ROW, FLEXWRAP.WRAP, FLEXJUSTIFY.FLEXSTART, FLEXALIGNITEMS.CENTER,
            spacing = 5
        )
        flexpanel.add(getDiv("1", 100), order = 3)
        flexpanel.add(getDiv("2", 150), order = 1)
        flexpanel.add(getDiv("3", 200), order = 2)
        panel.add(flexpanel)
    }

    private fun addFlexPanel5(panel: Container) {
        val flexpanel = FlexPanel(
            FLEXDIR.COLUMN, FLEXWRAP.WRAP, FLEXJUSTIFY.FLEXSTART, FLEXALIGNITEMS.FLEXEND,
            spacing = 5
        )
        flexpanel.add(getDiv("1", 100), order = 3)
        flexpanel.add(getDiv("2", 150), order = 1)
        flexpanel.add(getDiv("3", 200), order = 2)
        panel.add(flexpanel)
    }

    private fun addGridPanel1(panel: Container) {
        panel.add(Tag(TAG.H4, "CSS grid layouts"))
        val gridpanel = GridPanel(columnGap = 5, rowGap = 5, justifyItems = GRIDJUSTIFY.CENTER)
        gridpanel.background = Background(COLOR.KHAKI)
        gridpanel.add(getDiv("1,1", 100), 1, 1)
        gridpanel.add(getDiv("1,2", 100), 1, 2)
        gridpanel.add(getDiv("2,1", 100), 2, 1)
        gridpanel.add(getDiv("2,2", 100), 2, 2)
        panel.add(gridpanel)
    }

    private fun addGridPanel2(panel: Container) {
        val gridpanel = GridPanel(columnGap = 5, rowGap = 5, justifyItems = GRIDJUSTIFY.CENTER)
        gridpanel.background = Background(COLOR.CORNFLOWERBLUE)
        gridpanel.add(getDiv("1,1", 150), 1, 1)
        gridpanel.add(getDiv("2,2", 150), 2, 2)
        gridpanel.add(getDiv("3,3", 150), 3, 3)
        panel.add(gridpanel)
    }

    private fun addRespGridPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Responsive grid layout"))
        val gridpanel = ResponsiveGridPanel()
        gridpanel.background = Background(COLOR.SILVER)
        gridpanel.add(getDiv("1,1", 150), 1, 1)
        gridpanel.add(getDiv("3,1", 150), 3, 1)
        gridpanel.add(getDiv("2,2", 150), 2, 2)
        gridpanel.add(getDiv("3,3", 150), 3, 3)
        panel.add(gridpanel)
    }

    private fun addDockPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Dock layout"))
        val dockpanel = DockPanel()
        dockpanel.background = Background(COLOR.YELLOW)
        dockpanel.add(getDiv("CENTER", 150), SIDE.CENTER)
        dockpanel.add(getDiv("LEFT", 150), SIDE.LEFT)
        dockpanel.add(getDiv("RIGHT", 150), SIDE.RIGHT)
        dockpanel.add(getDiv("UP", 150).apply { marginBottom = 10.px() }, SIDE.UP)
        dockpanel.add(getDiv("DOWN", 150).apply { marginTop = 10.px() }, SIDE.DOWN)
        panel.add(dockpanel)
    }


    private fun getDiv(value: String, size: Int): Tag {
        return Tag(TAG.DIV, value).apply {
            paddingTop = ((size / 2) - 10).px()
            align = ALIGN.CENTER
            background = Background(COLOR.GREEN)
            width = size.px()
            height = size.px()
        }
    }
}
