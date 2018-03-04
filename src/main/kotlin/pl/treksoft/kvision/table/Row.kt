/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.table

import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * HTML table row component.
 *
 * @constructor
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Row(classes: Set<String> = setOf(), init: (Row.() -> Unit)? = null) : Tag(
    TAG.TR, classes = classes
) {

    init {
        init?.invoke(this)
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Table.row(
            classes: Set<String> = setOf(), init: (Row.() -> Unit)? = null
        ): Row {
            val row = Row(classes, init)
            this.add(row)
            return row
        }
    }

}
