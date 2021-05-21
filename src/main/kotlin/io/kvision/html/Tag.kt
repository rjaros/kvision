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
package io.kvision.html

import com.github.snabbdom.VNode
import io.kvision.KVManager
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.CssClass
import io.kvision.i18n.I18n
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableState
import io.kvision.state.bind
import io.kvision.utils.set

/**
 * HTML tags.
 */
@Suppress("EnumNaming")
enum class TAG(internal val tagName: String) {
    A("a"),
    ABBR("abbr"),
    ADDRESS("address"),
    AREA("area"),
    ARTICLE("article"),
    ASIDE("aside"),
    AUDIO("audio"),
    B("b"),
    BASE("base"),
    BDI("bdi"),
    BDO("bdo"),
    BLOCKQUOTE("blockquote"),
    BODY("body"),
    BR("br"),
    BUTTON("button"),
    CANVAS("canvas"),
    CAPTION("caption"),
    CITE("cite"),
    CODE("code"),
    COL("col"),
    COLGROUP("colgroup"),
    DATA("data"),
    DATALIST("datalist"),
    DD("dd"),
    DEL("del"),
    DETAILS("details"),
    DFN("dfn"),
    DIALOG("dialog"),
    DIV("div"),
    DL("dl"),
    DT("dt"),
    EM("em"),
    EMBED("embed"),
    FIELDSET("fieldset"),
    FIGCAPTION("figcaption"),
    FIGURE("figure"),
    FOOTER("footer"),
    FORM("form"),
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6"),
    HEAD("head"),
    HEADER("header"),
    HGROUP("hgroup"),
    HR("hr"),
    HTML("html"),
    I("i"),
    IFRAME("iframe"),
    IMG("img"),
    INPUT("input"),
    INS("ins"),
    KBD("kbd"),
    LABEL("label"),
    LEGEND("legend"),
    LI("li"),
    LINK("link"),
    MAIN("main"),
    MAP("map"),
    MARK("mark"),
    MATH("math"),
    MENU("menu"),
    MENUITEM("menuitem"),
    META("meta"),
    METER("meter"),
    NAV("nav"),
    NOSCRIPT("noscript"),
    OBJECT("object"),
    OL("ol"),
    OPTGROUP("optgroup"),
    OPTION("option"),
    OUTPUT("output"),
    P("p"),
    PARAM("param"),
    PICTURE("picture"),
    PRE("pre"),
    PROGRESS("progress"),
    Q("q"),
    RB("rb"),
    RP("rp"),
    RT("rt"),
    RTC("rtc"),
    RUBY("ruby"),
    S("s"),
    SAMP("samp"),
    SCRIPT("script"),
    SECTION("section"),
    SELECT("select"),
    SLOT("slot"),
    SMALL("small"),
    SOURCE("source"),
    SPAN("span"),
    STRONG("strong"),
    STYLE("style"),
    SUB("sub"),
    SUMMARY("summary"),
    SUP("sup"),
    SVG("svg"),
    TABLE("table"),
    TBODY("tbody"),
    TD("td"),
    TEMPLATE("template"),
    TEXTAREA("textarea"),
    TFOOT("tfoot"),
    TH("th"),
    THEAD("thead"),
    TIME("time"),
    TITLE("title"),
    TR("tr"),
    TRACK("track"),
    U("u"),
    UL("ul"),
    VAR("var"),
    VIDEO("video"),
    WBR("wbr")
}

/**
 * CSS align attributes.
 */
enum class Align(override val className: String) : CssClass {
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
@TagMarker
open class Tag(
    type: TAG, content: String? = null, rich: Boolean = false, align: Align? = null,
    classes: Set<String> = setOf(), attributes: Map<String, String>? = null,
    init: (Tag.() -> Unit)? = null
) : SimplePanel(classes), Template {

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
        if (attributes != null) {
            if (this.attributes == null) this.attributes = mutableMapOf()
            this.attributes!! += attributes
        }
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        if (templateDataObj != null && lastLanguage != null && lastLanguage != I18n.language) {
            getRoot()?.let { it.singleRenderers++ }
            templateData = templateDataObj
            getRoot()?.let { it.singleRenderers-- }
        }
        return if (content != null) {
            val translatedContent = content?.let { translate(it) }
            if (rich) {
                render(
                    type.tagName,
                    arrayOf(KVManager.virtualize("<span style=\"display: contents;\">$translatedContent</span>")) + childrenVNodes()
                )
            } else {
                render(type.tagName, childrenVNodes() + arrayOf(translatedContent))
            }
        } else {
            render(type.tagName, childrenVNodes())
        }
    }

    override fun focus() {
        if (getElementJQuery()?.attr("tabindex") == undefined) getElementJQuery()?.attr("tabindex", "-1")
        super.focus()
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        classSetBuilder.add(align)
    }

    override operator fun String.unaryPlus() {
        add(TextNode(this, rich))
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.tag(
    type: TAG, content: String? = null, rich: Boolean = false, align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    attributes: Map<String, String>? = null,
    init: (Tag.() -> Unit)? = null
): Tag {
    val tag = Tag(type, content, rich, align, classes ?: className.set, attributes, init)
    this.add(tag)
    return tag
}

/**
 * DSL builder extension function for observable state.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun <S> Container.tag(
    state: ObservableState<S>,
    type: TAG, content: String? = null, rich: Boolean = false, align: Align? = null,
    classes: Set<String>? = null,
    className: String? = null,
    attributes: Map<String, String>? = null,
    init: (Tag.(S) -> Unit)
) = tag(type, content, rich, align, classes, className, attributes).bind(state, true, init)
