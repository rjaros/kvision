/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.dropdown

import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * Menu header component.
 *
 * @constructor
 * @param content header content text
 * @param classes a set of CSS class names
 */
open class Header(content: String? = null, classes: Set<String> = setOf()) :
    Tag(TAG.LI, content, classes = classes + "dropdown-header") {


    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun ListTag.header(content: String? = null, classes: Set<String> = setOf()): Header {
            val header = Header(content, classes)
            this.add(header)
            return header
        }

        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun DropDown.header(content: String? = null, classes: Set<String> = setOf()): Header {
            val header = Header(content, classes)
            this.add(header)
            return header
        }
    }
}
