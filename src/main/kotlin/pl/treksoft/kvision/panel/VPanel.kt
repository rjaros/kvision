package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.ALIGN

open class VPanel(align: ALIGN = ALIGN.NONE, classes: Set<String> = setOf()) : GridPanel(GRIDTYPE.BOOTSTRAP,
        align = align, classes = classes) {

    override fun add(child: Widget, row: Int, col: Int, size: Int, offset: Int): Container {
        return super.add(child, row, 0, size, offset)
    }

    override fun add(child: Widget): Container {
        return this.add(child, this.rows, 0)
    }

    override fun addAll(children: List<Widget>): Container {
        children.forEach { this.add(it) }
        return this
    }

    override fun removeAt(index: Int): Container {
        return this.removeAt(index, 0)
    }

}
