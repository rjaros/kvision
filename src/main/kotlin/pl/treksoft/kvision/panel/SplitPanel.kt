package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.jquery.JQuery
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.snabbdom.obj

enum class DIRECTION(val dir: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical")
}

open class SplitPanel(val direction: DIRECTION = DIRECTION.VERTICAL,
                      classes: Set<String> = setOf()) : Container(classes + ("splitpanel-" + direction.dir)) {

    internal val splitter = Splitter(this, direction)

    internal fun afterInsertSplitter() {
        if (children.size == 2) {
            val horizontal = direction == DIRECTION.HORIZONTAL
            children[0].getElementJQueryD().resizable(obj {
                handleSelector = "#" + splitter.id
                resizeWidth = !horizontal
                resizeHeight = horizontal
                onDragEnd = { _: dynamic, el: JQuery, _: dynamic ->
                    if (horizontal) {
                        children[0].height = el.height().toInt()
                    } else {
                        children[0].width = el.width().toInt()
                    }
                }
            })
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (children.size == 2) {
            arrayOf(children[0].render(), splitter.render(), children[1].render())
        } else {
            arrayOf()
        }
    }
}

class Splitter(val splitPanel: SplitPanel, direction: DIRECTION) : Tag(TAG.DIV,
        classes = setOf("splitter-" + direction.dir)) {
    private val idc = "kv_splitter_" + counter

    init {
        counter++
        this.id = idc
    }

    override fun afterInsert(node: VNode) {
        splitPanel.afterInsertSplitter()
    }

    companion object {
        var counter = 0
    }
}
