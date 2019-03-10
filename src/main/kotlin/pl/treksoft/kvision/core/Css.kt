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
package pl.treksoft.kvision.core

import pl.treksoft.kvision.utils.asString
import pl.treksoft.kvision.utils.toHexString

/**
 * Definitions of CSS units.
 */
@Suppress("EnumNaming", "EnumEntryName")
enum class UNIT(internal val unit: String) {
    px("px"),
    pt("pt"),
    em("em"),
    cm("cm"),
    mm("mm"),
    `in`("in"),
    pc("pc"),
    ch("ch"),
    rem("rem"),
    vw("vw"),
    vh("vh"),
    vmin("vmin"),
    vmax("vmax"),
    perc("%"),
    auto("auto"),
    normal("normal")
}

/**
 * Definitions of CSS border styles.
 */
enum class BorderStyle(internal val borderStyle: String) {
    NONE("none"),
    HIDDEN("hidden"),
    DOTTED("dotted"),
    DASHED("dashed"),
    SOLID("solid"),
    DOUBLE("double"),
    GROOVE("groove"),
    RIDGE("ridge"),
    INSET("inset"),
    OUTSET("outset"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS color names.
 */
enum class Col(internal val color: String) {
    ALICEBLUE("aliceblue"),
    ANTIQUEWHITE("antiquewhite"),
    AQUA("aqua"),
    AQUAMARINE("aquamarine"),
    AZURE("azure"),
    BEIGE("beige"),
    BISQUE("bisque"),
    BLACK("black"),
    BLANCHEDALMOND("blanchedalmond"),
    BLUE("blue"),
    BLUEVIOLET("blueviolet"),
    BROWN("brown"),
    BURLYWOOD("burlywood"),
    CADETBLUE("cadetblue"),
    CHARTREUSE("chartreuse"),
    CHOCOLATE("chocolate"),
    CORAL("coral"),
    CORNFLOWERBLUE("cornflowerblue"),
    CORNSILK("cornsilk"),
    CRIMSON("crimson"),
    CYAN("cyan"),
    DARKBLUE("darkblue"),
    DARKCYAN("darkcyan"),
    DARKGOLDENROD("darkgoldenrod"),
    DARKGRAY("darkgray"),
    DARKGREEN("darkgreen"),
    DARKKHAKI("darkkhaki"),
    DARKMAGENTA("darkmagenta"),
    DARKOLIVEGREEN("darkolivegreen"),
    DARKORANGE("darkorange"),
    DARKORCHID("darkorchid"),
    DARKRED("darkred"),
    DARKSALMON("darksalmon"),
    DARKSEAGREEN("darkseagreen"),
    DARKSLATEBLUE("darkslateblue"),
    DARKSLATEGRAY("darkslategray"),
    DARKTURQUOISE("darkturquoise"),
    DARKVIOLET("darkviolet"),
    DEEPPINK("deeppink"),
    DEEPSKYBLUE("deepskyblue"),
    DIMGRAY("dimgray"),
    DODGERBLUE("dodgerblue"),
    FIREBRICK("firebrick"),
    FLORALWHITE("floralwhite"),
    FORESTGREEN("forestgreen"),
    FUCHSIA("fuchsia"),
    GAINSBORO("gainsboro"),
    GHOSTWHITE("ghostwhite"),
    GOLD("gold"),
    GOLDENROD("goldenrod"),
    GRAY("gray"),
    GREEN("green"),
    GREENYELLOW("greenyellow"),
    HONEYDEW("honeydew"),
    HOTPINK("hotpink"),
    INDIANRED("indianred"),
    INDIGO("indigo"),
    IVORY("ivory"),
    KHAKI("khaki"),
    LAVENDER("lavender"),
    LAVENDERBLUSH("lavenderblush"),
    LAWNGREEN("lawngreen"),
    LEMONCHIFFON("lemonchiffon"),
    LIGHTBLUE("lightblue"),
    LIGHTCORAL("lightcoral"),
    LIGHTCYAN("lightcyan"),
    LIGHTGOLDENRODYELLOW("lightgoldenrodyellow"),
    LIGHTGRAY("lightgray"),
    LIGHTGREEN("lightgreen"),
    LIGHTPINK("lightpink"),
    LIGHTSALMON("lightsalmon"),
    LIGHTSEAGREEN("lightseagreen"),
    LIGHTSKYBLUE("lightskyblue"),
    LIGHTSLATEGRAY("lightslategray"),
    LIGHTSTEELBLUE("lightsteelblue"),
    LIGHTYELLOW("lightyellow"),
    LIME("lime"),
    LIMEGREEN("limegreen"),
    LINEN("linen"),
    MAGENTA("magenta"),
    MAROON("maroon"),
    MEDIUMAQUAMARINE("mediumaquamarine"),
    MEDIUMBLUE("mediumblue"),
    MEDIUMORCHID("mediumorchid"),
    MEDIUMPURPLE("mediumpurple"),
    MEDIUMSEAGREEN("mediumseagreen"),
    MEDIUMSLATEBLUE("mediumslateblue"),
    MEDIUMSPRINGGREEN("mediumspringgreen"),
    MEDIUMTURQUOISE("mediumturquoise"),
    MEDIUMVIOLETRED("mediumvioletred"),
    MIDNIGHTBLUE("midnightblue"),
    MINTCREAM("mintcream"),
    MISTYROSE("mistyrose"),
    MOCCASIN("moccasin"),
    NAVAJOWHITE("navajowhite"),
    NAVY("navy"),
    OLDLACE("oldlace"),
    OLIVE("olive"),
    OLIVEDRAB("olivedrab"),
    ORANGE("orange"),
    ORANGERED("orangered"),
    ORCHID("orchid"),
    PALEGOLDENROD("palegoldenrod"),
    PALEGREEN("palegreen"),
    PALETURQUOISE("paleturquoise"),
    PALEVIOLETRED("palevioletred"),
    PAPAYAWHIP("papayawhip"),
    PEACHPUFF("peachpuff"),
    PERU("peru"),
    PINK("pink"),
    PLUM("plum"),
    POWDERBLUE("powderblue"),
    PURPLE("purple"),
    REBECCAPURPLE("rebeccapurple"),
    RED("red"),
    ROSYBROWN("rosybrown"),
    ROYALBLUE("royalblue"),
    SADDLEBROWN("saddlebrown"),
    SALMON("salmon"),
    SANDYBROWN("sandybrown"),
    SEAGREEN("seagreen"),
    SEASHELL("seashell"),
    SIENNA("sienna"),
    SILVER("silver"),
    SKYBLUE("skyblue"),
    SLATEBLUE("slateblue"),
    SLATEGRAY("slategray"),
    SNOW("snow"),
    SPRINGGREEN("springgreen"),
    STEELBLUE("steelblue"),
    TAN("tan"),
    TEAL("teal"),
    THISTLE("thistle"),
    TOMATO("tomato"),
    TURQUOISE("turquoise"),
    VIOLET("violet"),
    WHEAT("wheat"),
    WHITE("white"),
    WHITESMOKE("whitesmoke"),
    YELLOW("yellow"),
    YELLOWGREEN("yellowgreen")
}

/**
 * Definitions of CSS background size.
 */
enum class BgSize(internal val size: String) {
    COVER("cover"),
    CONTAIN("contain")
}

/**
 * Definitions of CSS background repeat options.
 */
enum class BgRepeat(internal val repeat: String) {
    REPEAT("repeat"),
    REPEATX("repeat-x"),
    REPEATY("repeat-y"),
    NOREPEAT("no-repeat")
}

/**
 * Definitions of CSS background attachment options.
 */
enum class BgAttach(internal val attachment: String) {
    SCROLL("scroll"),
    FIXED("fixed"),
    LOCAL("local")
}

/**
 * Definitions of CSS background origin options.
 */
enum class BgOrigin(internal val origin: String) {
    PADDING("padding-box"),
    BORDER("border-box"),
    CONTENT("content-box")
}

/**
 * Definitions of CSS background clipping options.
 */
enum class BgClip(internal val clip: String) {
    PADDING("padding-box"),
    BORDER("border-box"),
    CONTENT("content-box")
}

/**
 * Definitions of CSS display options.
 */
enum class Display(internal val display: String) {
    INLINE("inline"),
    BLOCK("block"),
    FLEX("flex"),
    GRID("grid"),
    INLINEBLOCK("inline-block"),
    INLINEFLEX("inline-flex"),
    INLINEGRID("inline-grid"),
    INLINETABLE("inline-table"),
    LISTITEM("list-item"),
    RUNIN("run-in"),
    TABLE("table"),
    TABLECAPTION("table-caption"),
    TABLECOLUMNGROUP("table-column-group"),
    TABLEHEADERGROUP("table-header-group"),
    TABLEFOOTERGROUP("table-footer-group"),
    TABLEROWGROUP("table-row-group"),
    TABLECELL("table-cell"),
    TABLECOLUMN("table-column"),
    TABLEROW("table-row"),
    NONE("none"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS position options.
 */
enum class Position(internal val position: String) {
    STATIC("static"),
    RELATIVE("relative"),
    FIXED("fixed"),
    ABSOLUTE("absolute"),
    STICKY("sticky")
}

/**
 * Definitions of CSS overflow options.
 */
enum class Overflow(internal val overflow: String) {
    VISIBLE("visible"),
    HIDDEN("hidden"),
    SCROLL("scroll"),
    AUTO("auto"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS overflow-wrap options.
 */
enum class OverflowWrap(internal val overflowWrap: String) {
    NORMAL("normal"),
    BREAKWORK("break-word"),
    ANYWHERE("anywhere")
}

/**
 * Definitions of CSS resize options.
 */
enum class Resize(internal val resize: String) {
    NONE("none"),
    BOTH("both"),
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS text direction options.
 */
enum class Direction(internal val direction: String) {
    LTR("ltr"),
    RTL("rtl"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS text align options.
 */
enum class TextAlign(internal val textAlign: String) {
    LEFT("left"),
    RIGHT("right"),
    CENTER("center"),
    JUSTIFY("justify"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS text decoration line options.
 */
enum class TextDecorationLine(internal val textDecorationLine: String) {
    NONE("none"),
    UNDERLINE("underline"),
    OVERLINE("overline"),
    LINETHROUGH("line-through"),
    JUSTIFY("justify"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS text decoration style options.
 */
enum class TextDecorationStyle(internal val textDecorationStyle: String) {
    SOLID("solid"),
    DOUBLE("double"),
    DOTTED("dotted"),
    DASHED("dashed"),
    WAVY("wavy"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS text transform options.
 */
enum class TextTransform(internal val textTransform: String) {
    NONE("none"),
    CAPITALIZE("capitalize"),
    UPPERCASE("uppercase"),
    LOWERCASE("lowercase"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS text overflow options.
 */
enum class TextOverflow(internal val textOverflow: String) {
    CLIP("clip"),
    ELLIPSIS("ellipsis"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS unicode-bidi options.
 */
enum class UnicodeBidi(internal val unicodeBidi: String) {
    NORMAL("normal"),
    EMBED("embed"),
    BIDIOVERRIDE("bidi-override"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS vertical align options.
 */
enum class VerticalAlign(internal val verticalAlign: String) {
    BASELINE("baseline"),
    SUB("sub"),
    SUPER("super"),
    TOP("top"),
    TEXTTOP("text-top"),
    MIDDLE("middle"),
    BOTTOM("bottom"),
    TEXTBOTTOM("text-bottom"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS white space options.
 */
enum class WhiteSpace(internal val whiteSpace: String) {
    NORMAL("normal"),
    NOWRAP("nowrap"),
    PRE("pre"),
    PRELINE("pre-line"),
    PREWRAP("pre-wrap"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS font style options.
 */
enum class FontStyle(internal val fontStyle: String) {
    NORMAL("normal"),
    ITALIC("italic"),
    OBLIQUE("oblique"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS font variant options.
 */
enum class FontVariant(internal val fontVariant: String) {
    NORMAL("normal"),
    SMALLCAPS("small-caps"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS font weight options.
 */
enum class FontWeight(internal val fontWeight: String) {
    NORMAL("normal"),
    BOLD("bold"),
    BOLDER("bolder"),
    LIGHTER("lighter"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS float options.
 */
enum class PosFloat(internal val posFloat: String) {
    NONE("none"),
    LEFT("left"),
    RIGHT("right"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS clear options.
 */
enum class Clear(internal val clear: String) {
    NONE("none"),
    LEFT("left"),
    RIGHT("right"),
    BOTH("both"),
    INITIAL("initial"),
    INHERIT("inherit")
}

/**
 * Definitions of CSS word-break options.
 */
enum class WordBreak(internal val wordBreak: String) {
    NORMAL("normal"),
    KEEPALL("keep-all"),
    BREAKALL("break-all")
}

/**
 * Definitions of CSS line-break options.
 */
enum class LineBreak(internal val lineBreak: String) {
    AUTO("auto"),
    LOOSE("loose"),
    NORMAL("normal"),
    STRICT("strict"),
    ANYWHERE("anywhere")
}

/**
 * Type-safe definition of CSS border.
 */
class Border private constructor(
    private val width: CssSize? = null, private val style: BorderStyle? = null,
    private val color: String? = null
) {
    /**
     * Creates CSS Border with given width and style.
     * @param width width of the border
     * @param style style of the border
     */
    constructor(width: CssSize? = null, style: BorderStyle? = null) : this(width, style, null)

    /**
     * Creates CSS Border with given width, style and color given in hex format.
     * @param width width of the border
     * @param style style of the border
     * @param color color in hex format
     */
    constructor(width: CssSize? = null, style: BorderStyle? = null, color: Int) : this(
        width, style,
        "#" + color.toHexString()
    )

    /**
     * Creates CSS Border with given width, style and color given with named constant.
     * @param width width of the border
     * @param style style of the border
     * @param color color named constant
     */
    constructor(width: CssSize? = null, style: BorderStyle? = null, color: Col) : this(width, style, color.color)

    internal fun asString(): String {
        val w = width?.asString()
        return w.orEmpty() + " " + (style?.borderStyle).orEmpty() + " " + color.orEmpty()
    }
}

/**
 * Type-safe definition of CSS color.
 */
class Color private constructor(private val color: String? = null) {
    /**
     * Creates CSS Color with color given in hex format.
     * @param color color in hex format
     */
    constructor(color: Int) : this("#" + color.toHexString())

    /**
     * Creates CSS Color with color given with named constant.
     * @param color color named constant
     */
    constructor(color: Col) : this(color.color)

    fun asString(): String {
        return color.orEmpty()
    }
}

/**
 * Type-safe definition of CSS background.
 */
class Background private constructor(
    private val color: String? = null, private val image: ResString? = null,
    private val positionX: CssSize? = null, private val positionY: CssSize? = null,
    private val sizeX: CssSize? = null, private val sizeY: CssSize? = null,
    private val size: BgSize? = null, private val repeat: BgRepeat? = null,
    private val origin: BgOrigin? = null, private val clip: BgClip? = null,
    private val attachment: BgAttach? = null
) {
    /**
     * Creates CSS Background with given parameters.
     * @param image background image
     * @param positionX horizontal position of the background image
     * @param positionY vertical position of the background image
     * @param sizeX horizontal size of the background image
     * @param sizeY vertical size of the background image
     * @param size resize of the background image
     * @param repeat repeat option of the background image
     * @param origin origin option of the background image
     * @param clip clipping option of the background image
     * @param attachment attachment option of the background image
     */
    constructor(
        image: ResString? = null, positionX: CssSize? = null, positionY: CssSize? = null,
        sizeX: CssSize? = null, sizeY: CssSize? = null, size: BgSize? = null,
        repeat: BgRepeat? = null, origin: BgOrigin? = null, clip: BgClip? = null,
        attachment: BgAttach? = null
    ) : this(
        null,
        image, positionX, positionY, sizeX, sizeY, size, repeat, origin, clip, attachment
    )

    /**
     * Creates CSS Background with given parameters.
     * @param color color of the background in hex format
     * @param image background image
     * @param positionX horizontal position of the background image
     * @param positionY vertical position of the background image
     * @param sizeX horizontal size of the background image
     * @param sizeY vertical size of the background image
     * @param size resize of the background image
     * @param repeat repeat option of the background image
     * @param origin origin option of the background image
     * @param clip clipping option of the background image
     * @param attachment attachment option of the background image
     */
    constructor(
        color: Int, image: ResString? = null, positionX: CssSize? = null,
        positionY: CssSize? = null,
        sizeX: CssSize? = null, sizeY: CssSize? = null, size: BgSize? = null,
        repeat: BgRepeat? = null, origin: BgOrigin? = null, clip: BgClip? = null,
        attachment: BgAttach? = null
    ) : this(
        "#" +
                color.toHexString(), image, positionX, positionY, sizeX, sizeY, size, repeat, origin, clip,
        attachment
    )

    /**
     * Creates CSS Background with given parameters.
     * @param color color of the background with named constant
     * @param image background image
     * @param positionX horizontal position of the background image
     * @param positionY vertical position of the background image
     * @param sizeX horizontal size of the background image
     * @param sizeY vertical size of the background image
     * @param size resize of the background image
     * @param repeat repeat option of the background image
     * @param origin origin option of the background image
     * @param clip clipping option of the background image
     * @param attachment attachment option of the background image
     */
    constructor(
        color: Col, image: ResString? = null, positionX: CssSize? = null,
        positionY: CssSize? = null, sizeX: CssSize? = null, sizeY: CssSize? = null,
        size: BgSize? = null, repeat: BgRepeat? = null, origin: BgOrigin? = null, clip: BgClip? = null,
        attachment: BgAttach? = null
    ) : this(
        color.color, image,
        positionX, positionY, sizeX, sizeY, size, repeat, origin, clip, attachment
    )

    internal fun asString(): String {
        val img = image?.let {
            "url($image)"
        }
        val posX = positionX?.asString()
        val posY = positionY?.asString()
        val sX = sizeX?.asString()
        val sY = sizeY?.asString()
        return color.orEmpty() + " " + img.orEmpty() + " " + posX.orEmpty() + " " + posY.orEmpty() +
                if (sX != null || sY != null || size != null) {
                    (if (posX != null || posY != null) " / " else " 0px 0px / ") +
                            sX.orEmpty() + " " + sY.orEmpty() + " " + (size?.size).orEmpty()
                } else {
                    ""
                } + " " + (repeat?.repeat).orEmpty() + " " + (origin?.origin).orEmpty() + " " +
                (clip?.clip).orEmpty() + " " + (attachment?.attachment).orEmpty()
    }
}

/**
 * Type-safe definition of CSS text decoration.
 */
class TextDecoration private constructor(
    private val line: TextDecorationLine? = null, private val style: TextDecorationStyle? = null,
    private val color: String? = null
) {
    /**
     * Creates CSS text decoration with given line and style.
     * @param line text decoration line
     * @param style text decoration style
     */
    constructor(line: TextDecorationLine? = null, style: TextDecorationStyle? = null) : this(line, style, null)

    /**
     * Creates CSS text decoration with given line, style and color given in hex format.
     * @param line text decoration line
     * @param style text decoration style
     * @param color color in hex format
     */
    constructor(line: TextDecorationLine? = null, style: TextDecorationStyle? = null, color: Int) : this(
        line, style, "#" + color.toHexString()
    )

    /**
     * Creates CSS text decoration with given line, style and color given with named constant.
     * @param line text decoration line
     * @param style text decoration style
     * @param color color named constant
     */
    constructor(line: TextDecorationLine? = null, style: TextDecorationStyle? = null, color: Col) : this(
        line, style, color.color
    )

    internal fun asString(): String {
        return (line?.textDecorationLine).orEmpty() + " " +
                (style?.textDecorationStyle).orEmpty() + " " +
                color.orEmpty()
    }
}

/**
 * Type-safe definition of CSS text shadow.
 */
class TextShadow private constructor(
    private val hShadow: CssSize? = null, private val vShadow: CssSize? = null,
    private val blurRadius: CssSize? = null, private val color: String? = null
) {
    /**
     * Creates CSS text shadow with given position and radius.
     * @param hShadow the position of horizontal shadow
     * @param vShadow the position of vertical shadow
     * @param blurRadius the blur radius
     */
    constructor(hShadow: CssSize? = null, vShadow: CssSize? = null, blurRadius: CssSize? = null) : this(
        hShadow, vShadow, blurRadius, null
    )

    /**
     * Creates CSS text shadow with given position and radius and color given in hex format.
     * @param hShadow the position of horizontal shadow
     * @param vShadow the position of vertical shadow
     * @param blurRadius the blur radius
     * @param color color in hex format
     */
    constructor(hShadow: CssSize? = null, vShadow: CssSize? = null, blurRadius: CssSize? = null, color: Int) : this(
        hShadow, vShadow, blurRadius, "#" + color.toHexString()
    )

    /**
     * Creates CSS text shadow with given position and radius and color given with named constant.
     * @param hShadow the position of horizontal shadow
     * @param vShadow the position of vertical shadow
     * @param blurRadius the blur radius
     * @param color color named constant
     */
    constructor(hShadow: CssSize? = null, vShadow: CssSize? = null, blurRadius: CssSize? = null, color: Col) : this(
        hShadow, vShadow, blurRadius, color.color
    )

    internal fun asString(): String {
        return (hShadow?.asString()).orEmpty() + " " +
                (vShadow?.asString()).orEmpty() + " " +
                (blurRadius?.asString()).orEmpty() + " " +
                color.orEmpty()
    }
}
