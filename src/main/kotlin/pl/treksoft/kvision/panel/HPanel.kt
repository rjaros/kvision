package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.html.ALIGN
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

open class HPanel(align: ALIGN = ALIGN.NONE, classes: Set<String> = setOf()) : GridPanel(GRIDTYPE.DSG, align = align,
        classes = classes) {

    override fun add(child: Widget, row: Int, col: Int, size: Int, offset: Int): Container {
        return super.add(child, 0, col, size, offset)
    }

    override fun childrenVNodesDsg(): Array<VNode> {
        val ret = mutableListOf<VNode>()
        val rowContainer = Container(setOf("dsgrow"))
        val row = map[0]
        if (row != null) {
            for (j in 0 until cols) {
                val wp = row[j]
                val widget = wp?.widget?.addCssClass("dsgcolf") ?: Tag(TAG.DIV, classes = setOf("dsgcolf"))
                rowContainer.add(widget)
            }
        }
        ret.add(rowContainer.render())
        return ret.toTypedArray()
    }
}
