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
package io.kvision.core

/**
 * CSS style object that inherits all properties from its parent.
 *
 * @constructor
 * @param selector optional name of the CSS selector, it will be generated if not specified
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param parentStyle the style object to inherit properties from
 * @param init an initializer extension function
 */
open class InheritingStyle(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    parentStyle: Style? = null,
    init: (InheritingStyle.() -> Unit)? = null
) : Style(selector, pClass, pElement, mediaQuery, parentStyle) {

    init {
        parentStyle?.let {
            val otherStyles = it.getSnStyle()
            val myStyles = getSnStyle()
            for (key in js("Object").keys(otherStyles)) {
                @Suppress("UnsafeCastFromDynamic")
                stylePairs(key, otherStyles[key]).forEach { res ->
                    setStyle(res.first, res.second)
                }
            }
            for (key in js("Object").keys(myStyles)) {
                @Suppress("UnsafeCastFromDynamic")
                stylePairs(key, myStyles[key]).forEach { res ->
                    setStyle(res.first, res.second)
                }
            }
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getStyleDeclaration(): String {
        val pseudoElementName = pElement?.let { "::${it.pname}" } ?: ""
        val pseudoClassName = customPClass?.let { ":$it" } ?: pClass?.let { ":${it.pname}" } ?: ""
        return "$selector$pseudoElementName$pseudoClassName"
    }

}

private fun stylePairs(styleName: String, styleProp: String): List<Pair<String, String>> {
    return when (styleName) {
        "padding" -> {
            listOf(
                "padding-bottom" to styleProp,
                "padding-top" to styleProp,
                "padding-left" to styleProp,
                "padding-right" to styleProp
            )
        }
        "margin" -> {
            listOf(
                "margin-bottom" to styleProp,
                "margin-top" to styleProp,
                "margin-left" to styleProp,
                "margin-right" to styleProp
            )
        }
        else -> {
            listOf(styleName to styleProp)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun inheritingStyle(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    parentStyle: Style? = null,
    init: (InheritingStyle.() -> Unit)? = null
): InheritingStyle {
    return InheritingStyle(selector, pClass, pElement, mediaQuery, parentStyle, init)
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Widget.inheritingStyle(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    parentStyle: Style? = null,
    init: (InheritingStyle.() -> Unit)? = null
): InheritingStyle {
    val style = InheritingStyle(selector, pClass, pElement, mediaQuery, parentStyle, init)
    this.addCssStyle(style)
    return style
}

/**
 * DSL builder extension function for cascading styles.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Style.inheritingStyle(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    init: (InheritingStyle.() -> Unit)? = null
): Style {
    return InheritingStyle(selector, pClass, pElement, mediaQuery, this, init)
}
