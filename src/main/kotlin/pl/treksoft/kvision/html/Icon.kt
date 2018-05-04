/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.html

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair

/**
 * Icon component with support for FontAwesome and Bootstrap glyphicons.
 *
 * @constructor
 * @param icon icon name
 */
open class Icon(icon: String) : Tag(TAG.SPAN) {

    /**
     * Icon type.
     */
    var icon by refreshOnUpdate(icon)

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        if (icon.startsWith("fa-")) {
            cl.add("fa" to true)
            cl.add(icon to true)
        } else {
            cl.add("glyphicon" to true)
            cl.add("glyphicon-$icon" to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.icon(
            icon: String, init: (Icon.() -> Unit)? = null
        ): Icon {
            val i = Icon(icon).apply { init?.invoke(this) }
            this.add(i)
            return i
        }
    }
}
