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
    auto("auto")
}

/**
 * Definitions of CSS border styles.
 */
enum class BORDERSTYLE(internal val borderStyle: String) {
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
enum class COLOR(internal val color: String) {
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
enum class BGSIZE(internal val size: String) {
    COVER("cover"),
    CONTAIN("contain")
}

/**
 * Definitions of CSS background repeat options.
 */
enum class BGREPEAT(internal val repeat: String) {
    REPEAT("repeat"),
    REPEATX("repeat-x"),
    REPEATY("repeat-y"),
    NOREPEAT("no-repeat")
}

/**
 * Definitions of CSS background attachment options.
 */
enum class BGATTACH(internal val attachment: String) {
    SCROLL("scroll"),
    FIXED("fixed"),
    LOCAL("local")
}

/**
 * Definitions of CSS background origin options.
 */
enum class BGORIGIN(internal val origin: String) {
    PADDING("padding-box"),
    BORDER("border-box"),
    CONTENT("content-box")
}

/**
 * Definitions of CSS background clipping options.
 */
enum class BGCLIP(internal val clip: String) {
    PADDING("padding-box"),
    BORDER("border-box"),
    CONTENT("content-box")
}

/**
 * Type-safe definition of CSS border.
 */
class Border private constructor(
    private val width: CssSize? = null, private val style: BORDERSTYLE? = null,
    private val color: String? = null
) {
    /**
     * Creates CSS Border with given width and style.
     * @param width width of the border
     * @param style style of the border
     */
    constructor(width: CssSize? = null, style: BORDERSTYLE? = null) : this(width, style, null)

    /**
     * Creates CSS Border with given width, style and color given in hex format.
     * @param width width of the border
     * @param style style of the border
     * @param color color in hex format
     */
    constructor(width: CssSize? = null, style: BORDERSTYLE? = null, color: Int) : this(
        width, style,
        "#" + color.toHexString()
    )

    /**
     * Creates CSS Border with given width, style and color given with named constant.
     * @param width width of the border
     * @param style style of the border
     * @param color color named constant
     */
    constructor(width: CssSize? = null, style: BORDERSTYLE? = null, color: COLOR) : this(width, style, color.color)

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
    constructor(color: COLOR) : this(color.color)

    internal fun asString(): String {
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
    private val size: BGSIZE? = null, private val repeat: BGREPEAT? = null,
    private val origin: BGORIGIN? = null, private val clip: BGCLIP? = null,
    private val attachment: BGATTACH? = null
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
        sizeX: CssSize? = null, sizeY: CssSize? = null, size: BGSIZE? = null,
        repeat: BGREPEAT? = null, origin: BGORIGIN? = null, clip: BGCLIP? = null,
        attachment: BGATTACH? = null
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
        sizeX: CssSize? = null, sizeY: CssSize? = null, size: BGSIZE? = null,
        repeat: BGREPEAT? = null, origin: BGORIGIN? = null, clip: BGCLIP? = null,
        attachment: BGATTACH? = null
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
        color: COLOR, image: ResString? = null, positionX: CssSize? = null,
        positionY: CssSize? = null, sizeX: CssSize? = null, sizeY: CssSize? = null,
        size: BGSIZE? = null, repeat: BGREPEAT? = null, origin: BGORIGIN? = null, clip: BGCLIP? = null,
        attachment: BGATTACH? = null
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
