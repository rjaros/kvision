/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.table

import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * HTML table header cell component.
 *
 * @constructor
 * @param text text content of the cell
 * @param rich determines if [text] can contain HTML code
 * @param align text align
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class HeaderCell(
    text: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    classes: Set<String> = setOf(),
    init: (HeaderCell.() -> Unit)? = null
) : Tag(TAG.TH, text, rich, align, classes) {

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Row.headerCell(
            text: String? = null,
            rich: Boolean = false,
            align: Align? = null,
            classes: Set<String> = setOf(), init: (HeaderCell.() -> Unit)? = null
        ): HeaderCell {
            val cell = HeaderCell(text, rich, align, classes, init)
            this.add(cell)
            return cell
        }
    }

}
