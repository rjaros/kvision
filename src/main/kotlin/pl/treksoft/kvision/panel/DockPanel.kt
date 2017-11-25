package pl.treksoft.kvision.panel

import pl.treksoft.kvision.core.Component

enum class SIDE {
    LEFT,
    RIGHT,
    CENTER,
    UP,
    DOWN
}

open class DockPanel(classes: Set<String> = setOf()) : SimplePanel(classes = classes) {
    protected var left: Component? = null
    protected var center: Component? = null
    protected var right: Component? = null
    protected var up: Component? = null
    protected var down: Component? = null

    protected val mainContainer = FlexPanel(direction = FLEXDIR.COLUMN, justify = FLEXJUSTIFY.SPACEBETWEEN,
            alignItems = FLEXALIGNITEMS.STRETCH)
    protected val subContainer = FlexPanel(justify = FLEXJUSTIFY.SPACEBETWEEN, alignItems = FLEXALIGNITEMS.CENTER)

    init {
        this.addInternal(mainContainer)
        mainContainer.add(subContainer, 2)
    }

    @Suppress("MagicNumber")
    open fun add(widget: Component, position: SIDE): DockPanel {
        when (position) {
            SIDE.UP -> {
                up?.let { mainContainer.remove(it) }
                up = widget
                mainContainer.add(widget, 1, alignSelf = FLEXALIGNITEMS.CENTER)
            }
            SIDE.CENTER -> {
                center?.let { subContainer.remove(it) }
                center = widget
                subContainer.add(widget, 2)
            }
            SIDE.LEFT -> {
                left?.let { subContainer.remove(it) }
                left = widget
                subContainer.add(widget, 1)
            }
            SIDE.RIGHT -> {
                right?.let { subContainer.remove(it) }
                right = widget
                subContainer.add(widget, 3)
            }
            SIDE.DOWN -> {
                down?.let { mainContainer.remove(it) }
                down = widget
                mainContainer.add(widget, 3, alignSelf = FLEXALIGNITEMS.CENTER)
            }
        }
        return this
    }

    override fun add(child: Component): DockPanel {
        return this.add(child, SIDE.CENTER)
    }

    override fun addAll(children: List<Component>): DockPanel {
        children.forEach { this.add(it) }
        return this
    }

    override fun remove(child: Component): DockPanel {
        if (child == left) removeAt(SIDE.LEFT)
        if (child == center) removeAt(SIDE.CENTER)
        if (child == right) removeAt(SIDE.RIGHT)
        if (child == up) removeAt(SIDE.UP)
        if (child == down) removeAt(SIDE.DOWN)
        return this
    }

    open fun removeAt(position: SIDE): DockPanel {
        when (position) {
            SIDE.UP -> {
                up?.let { mainContainer.remove(it) }
                up = null
            }
            SIDE.CENTER -> {
                center?.let { subContainer.remove(it) }
                center = null
            }
            SIDE.LEFT -> {
                left?.let { subContainer.remove(it) }
                left = null
            }
            SIDE.RIGHT -> {
                right?.let { subContainer.remove(it) }
                right = null
            }
            SIDE.DOWN -> {
                down?.let { mainContainer.remove(it) }
                down = null
            }
        }
        return this
    }

    override fun removeAll(): DockPanel {
        removeAt(SIDE.LEFT)
        removeAt(SIDE.CENTER)
        removeAt(SIDE.RIGHT)
        removeAt(SIDE.UP)
        removeAt(SIDE.DOWN)
        return this
    }
}
