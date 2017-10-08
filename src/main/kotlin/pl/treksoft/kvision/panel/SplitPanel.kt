package pl.treksoft.kvision.panel

import com.github.snabbdom.VNode
import pl.treksoft.jquery.JQuery
import pl.treksoft.jquery.JQueryEventObject
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.css.UNIT
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.snabbdom.obj

enum class DIRECTION(val dir: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical")
}

open class SplitPanel(private val direction: DIRECTION = DIRECTION.VERTICAL,
                      classes: Set<String> = setOf()) : Container(classes + ("splitpanel-" + direction.dir)) {

    @Suppress("LeakingThis")
    private val splitter = Splitter(this, direction)

    @Suppress("UnsafeCastFromDynamic")
    internal fun afterInsertSplitter() {
        if (children.size == 2) {
            val horizontal = direction == DIRECTION.HORIZONTAL
            val px = UNIT.px
            val self = this
            children[0].getElementJQueryD().resizable(obj {
                handleSelector = "#" + splitter.id
                resizeWidth = !horizontal
                resizeHeight = horizontal
                onDrag = lok@ { e: JQueryEventObject, _: JQuery, newWidth: Int, newHeight: Int, _: dynamic ->
                    e.asDynamic()["newWidth"] = newWidth
                    e.asDynamic()["newHeight"] = newHeight
                    self.dispatchEvent("dragSplitPanel", obj({ detail = e }))
                    return@lok !e.isDefaultPrevented()
                }
                onDragEnd = { e: JQueryEventObject, el: JQuery, _: dynamic ->
                    if (horizontal) {
                        children[0].height = el.height().toInt() to px
                    } else {
                        children[0].width = el.width().toInt() to px
                    }
                    self.dispatchEvent("dragEndSplitPanel", obj({ detail = e }))
                }
            })
        }
    }

    override fun childrenVNodes(): Array<VNode> {
        return if (children.size == 2) {
            arrayOf(children[0].renderVNode(), splitter.renderVNode(), children[1].renderVNode())
        } else {
            arrayOf()
        }
    }
}

class Splitter(private val splitPanel: SplitPanel, direction: DIRECTION) : Tag(TAG.DIV,
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
