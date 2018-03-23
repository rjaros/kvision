/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * Menu separator component.
 *
 * @constructor
 * @param classes a set of CSS class names
 */
open class Separator(classes: Set<String> = setOf()) : Tag(TAG.LI, classes = classes + "divider") {

    init {
        role = "separator"
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun ListTag.separator(classes: Set<String> = setOf()): Separator {
            val separator = Separator(classes)
            this.add(separator)
            return separator
        }

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun DropDown.separator(classes: Set<String> = setOf()): Separator {
            val separator = Separator(classes)
            this.add(separator)
            return separator
        }
    }
}
