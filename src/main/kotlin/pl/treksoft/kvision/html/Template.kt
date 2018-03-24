/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.html

interface Template {
    var content: String?
    var rich: Boolean
    var template: (Any?) -> String

    var templateData: Any?
        get() {
            return null
        }
        set(value) {
            if (!rich) rich = true
            content = template(value)
        }
}
