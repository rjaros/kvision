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

import com.github.snabbdom.VNode
import pl.treksoft.kvision.KVManager
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.i18n.I18n
import pl.treksoft.kvision.panel.SimplePanel

/**
 * HTML tags.
 */
@Suppress("EnumNaming")
enum class TAG(internal val tagName: String) {
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6"),
    P("p"),
    ABBR("abbr"),
    ADDRESS("address"),
    BLOCKQUOTE("blockquote"),
    SECTION("section"),
    HEADER("header"),
    FOOTER("footer"),
    PRE("pre"),
    UL("ul"),
    OL("ol"),
    DIV("div"),
    LABEL("label"),

    I("i"),
    B("b"),
    MARK("mark"),
    DEL("del"),
    S("s"),
    INS("ins"),
    U("u"),
    SMALL("small"),
    STRONG("strong"),
    EM("em"),
    CITE("cite"),
    CODE("code"),
    KBD("kbd"),
    VAR("var"),
    SAMP("samp"),
    SPAN("span"),
    LI("li"),

    CAPTION("caption"),
    THEAD("thead"),
    TH("th"),
    TBODY("tbody"),
    TR("tr"),
    TD("td"),

    FORM("form"),
    INPUT("input"),
    SELECT("select"),
    OPTION("option"),
    BUTTON("button")
}

/**
 * CSS align attributes.
 */
enum class Align(val className: String) {
    LEFT("text-left"),
    CENTER("text-center"),
    RIGHT("text-right"),
    JUSTIFY("text-justify"),
    NOWRAP("text-nowrap")
}

/**
 * HTML tag component.
 *
 * @constructor
 * @param type tag type
 * @param content content text of the tag
 * @param rich determines if [content] can contain HTML code
 * @param align content align
 * @param classes a set of CSS class names
 * @param attributes a map of additional attributes
 * @param init an initializer extension function
 */
open class Tag(
    type: TAG, content: String? = null, rich: Boolean = false, align: Align? = null,
    classes: Set<String> = setOf(), attributes: Map<String, String> = mapOf(),
    init: (Tag.() -> Unit)? = null
) : SimplePanel(classes), Template {

    protected val attributes = attributes.toMutableMap()

    /**
     * Tag type.
     */
    var type by refreshOnUpdate(type)
    /**
     * Text content of the tag.
     */
    override var content by refreshOnUpdate(content)
    /**
     * Determines if [content] can contain HTML code.
     */
    override var rich by refreshOnUpdate(rich)
    /**
     * Text align.
     */
    var align by refreshOnUpdate(align)
    /**
     * @suppress
     * Internal property
     */
    override var templateDataObj: Any? = null
    /**
     * Handlebars template.
     */
    override var template: ((Any?) -> String)? by refreshOnUpdate()
    /**
     * Handlebars templates for i18n.
     */
    override var templates: Map<String, (Any?) -> String> by refreshOnUpdate(mapOf())

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        if (templateDataObj != null && lastLanguage != null && lastLanguage != I18n.language) {
            getRoot()?.renderDisabled = true
            templateData = templateDataObj
            getRoot()?.renderDisabled = false
        }
        return if (content != null) {
            val translatedContent = content?.let { translate(it) }
            if (rich) {
                render(
                    type.tagName,
                    arrayOf(KVManager.virtualize("<span>$translatedContent</span>")) + childrenVNodes()
                )
            } else {
                render(type.tagName, childrenVNodes() + arrayOf(translatedContent))
            }
        } else {
            render(type.tagName, childrenVNodes())
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        align?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        return if (attributes.isEmpty()) {
            super.getSnAttrs()
        } else {
            attributes.toList() + super.getSnAttrs()
        }
    }

    operator fun String.unaryPlus() {
        if (content == null)
            content = this
        else
            content += translate(this)
    }

    /**
     * Returns the value of an additional attribute.
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    fun getAttribute(name: String): String? {
        return this.attributes[name]
    }

    /**
     * Sets the value of additional attribute.
     * @param name the name of the attribute
     * @param value the value of the attribute
     */
    fun setAttribute(name: String, value: String): Widget {
        this.attributes[name] = value
        refresh()
        return this
    }

    /**
     * Removes the value of additional attribute.
     * @param name the name of the attribute
     */
    fun removeAttribute(name: String): Widget {
        this.attributes.remove(name)
        refresh()
        return this
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Container.tag(
            type: TAG, content: String? = null, rich: Boolean = false, align: Align? = null,
            classes: Set<String> = setOf(), attributes: Map<String, String> = mapOf(),
            init: (Tag.() -> Unit)? = null
        ): Tag {
            val tag = Tag(type, content, rich, align, classes, attributes, init)
            this.add(tag)
            return tag
        }
    }
}
