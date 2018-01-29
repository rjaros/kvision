package com.example

import pl.treksoft.kvision.core.Background
import pl.treksoft.kvision.core.COLOR
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.panel.DIRECTION
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.panel.SplitPanel
import pl.treksoft.kvision.panel.StackPanel
import pl.treksoft.kvision.panel.TabPanel
import pl.treksoft.kvision.panel.VPanel
import pl.treksoft.kvision.utils.px

class ContainersTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        val panel = VPanel(spacing = 5)
        addStackPanel(panel)
        addTabPanel(panel)
        addVerticalSplitPanel(panel)
        addHorizontalSplitPanel(panel)
        this.add(panel)
    }

    private fun addStackPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Stack panel"))

        val stack = StackPanel()
        stack.add(Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.BLUE)
            height = 40.px()
        }, "/containers/blue")
        stack.add(Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.GREEN)
            height = 40.px()
        }, "/containers/green")
        panel.add(stack)

        val ldd = DropDown(
            "Activate panel from the stack", listOf(
                "Blue panel" to "#!/containers/blue",
                "Green panel" to "#!/containers/green"
            )
        )
        panel.add(ldd)
    }

    private fun addTabPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Tab panel"))

        val tabs = TabPanel()
        tabs.addTab("Blue panel", Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.BLUE)
            height = 40.px()
        })
        tabs.addTab("Green panel", Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.GREEN)
            height = 40.px()
        })
        panel.add(tabs)
    }

    private fun addVerticalSplitPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Vertical split panel"))

        val split = SplitPanel()
        split.add(Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.BLUE)
            height = 200.px()
        })
        split.add(Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.GREEN)
            height = 200.px()
        })
        panel.add(split)
    }

    private fun addHorizontalSplitPanel(panel: Container) {
        panel.add(Tag(TAG.H4, "Horizontal split panel"))

        val split = SplitPanel(direction = DIRECTION.HORIZONTAL).apply { height = 220.px() }
        split.add(Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.BLUE)
            height = 100.px()
        })
        split.add(Tag(TAG.DIV, "&nbsp;", rich = true).apply {
            background = Background(COLOR.GREEN)
            height = 100.px()
        })
        panel.add(split)
    }
}
