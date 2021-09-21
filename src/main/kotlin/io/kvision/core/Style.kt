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

import io.kvision.panel.Root
import kotlin.reflect.KProperty

/**
 * CSS pseudo classes.
 */
enum class PClass(internal val pname: String) {
    ACTIVE("active"),
    CHECKED("checked"),
    DISABLED("disabled"),
    EMPTY("empty"),
    ENABLED("enabled"),
    FIRSTCHILD("first-child"),
    FIRSTOFTYPE("first-of-type"),
    FOCUS("focus"),
    HOVER("hover"),
    INRANGE("in-range"),
    INVALID("invalid"),
    LASTCHILD("last-child"),
    LASTOFTYPE("last-of-type"),
    LINK("link"),
    ONLYOFTYPE("only-of-type"),
    ONLYCHILD("only-child"),
    OPTIONAL("optional"),
    OUTOFRANGE("out-of-range"),
    READONLY("read-only"),
    READWRITE("read-write"),
    REQUIRED("required"),
    ROOT("root"),
    TARGET("target"),
    VALID("valid"),
    VISITED("visited")
}

/**
 * CSS pseudo elements.
 */
enum class PElement(internal val pname: String) {
    AFTER("after"),
    BEFORE("before"),
    FIRSTLETTER("first-letter"),
    FIRSTLINE("first-line"),
    MARKER("marker"),
    SELECTION("selection")
}

/**
 * CSS style object.
 *
 * @constructor
 * @param selector optional name of the CSS selector, it will be generated if not specified
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param parentStyle parent CSS style object
 * @param init an initializer extension function
 */
open class Style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    val parentStyle: Style? = null,
    init: (Style.() -> Unit)? = null
) : StyledComponent() {
    private val propertyValues = js("{}")

    /**
     * The name of the CSS selector.
     */
    val selector = selector ?: ".kv_styleclass_${counter++}"

    internal val cssClassName = this.selector.split(' ').last().split('.').last()

    /**
     * The CSS pseudo class.
     */
    var pClass by refreshOnUpdate(pClass)

    /**
     * The name of the custom CSS pseudo class.
     */
    var customPClass: String? by refreshOnUpdate()

    /**
     * The CSS pseudo element.
     */
    var pElement by refreshOnUpdate(pElement)

    /**
     * The CSS media query.
     */
    var mediaQuery by refreshOnUpdate(mediaQuery)

    init {
        @Suppress("LeakingThis")
        styles.add(this)
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    protected open fun getStyleDeclaration(): String {
        val pseudoElementName = pElement?.let { "::${it.pname}" } ?: ""
        val pseudoClassName = customPClass?.let { ":$it" } ?: pClass?.let { ":${it.pname}" } ?: ""
        val fullSelector = "$selector$pseudoElementName$pseudoClassName"
        return (parentStyle?.let { it.getStyleDeclaration() + " " } ?: "") + fullSelector
    }

    internal fun generateStyle(): String {
        val styles = getSnStyle()
        val stylesList = mutableListOf<StringPair>()
        for (key in js("Object").keys(styles)) {
            @Suppress("UnsafeCastFromDynamic")
            stylesList.add(key.unsafeCast<String>() to styles[key])
        }
        return "${getStyleDeclaration()} {\n" + stylesList.joinToString("\n") {
            "${it.first}: ${it.second};"
        } + "\n}"
    }

    override fun refresh(): Style {
        super.refresh()
        Root.getFirstRoot()?.reRender()
        return this
    }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> refreshOnUpdate(noinline refreshFunction: ((T) -> Unit) = { this.refresh() }) =
        RefreshDelegateProvider<T>(null, refreshFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> refreshOnUpdate(
        initialValue: T,
        noinline refreshFunction: ((T) -> Unit) = { this.refresh() }
    ) =
        RefreshDelegateProvider(initialValue, refreshFunction)

    protected inner class RefreshDelegateProvider<T>(
        private val initialValue: T?, private val refreshFunction: (T) -> Unit
    ) {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): RefreshDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            return RefreshDelegate(refreshFunction)
        }
    }

    protected inner class RefreshDelegate<T>(private val refreshFunction: ((T) -> Unit)) {
        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: StyledComponent, property: KProperty<*>): T {
            val value = propertyValues[property.name]
            return if (value != null) {
                value.unsafeCast<T>()
            } else {
                null.unsafeCast<T>()
            }
        }

        operator fun setValue(thisRef: StyledComponent, property: KProperty<*>, value: T) {
            propertyValues[property.name] = value
            refreshFunction(value)
        }
    }

    companion object {
        internal var counter = 0
        internal var styles = mutableListOf<Style>()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    init: (Style.() -> Unit)? = null
): Style {
    return Style(selector, pClass, pElement, mediaQuery, null, init)
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Widget.style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    init: (Style.() -> Unit)? = null
): Style {
    val style = Style(selector, pClass, pElement, mediaQuery, null, init)
    this.addCssStyle(style)
    return style
}

/**
 * DSL builder extension function for cascading styles.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Style.style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    init: (Style.() -> Unit)? = null
): Style {
    return Style(selector, pClass, pElement, mediaQuery, this, init)
}
