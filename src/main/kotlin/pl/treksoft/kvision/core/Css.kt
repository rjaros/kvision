package pl.treksoft.kvision.core

import pl.treksoft.kvision.utils.Utils

@Suppress("EnumNaming", "EnumEntryName")
enum class UNIT(val unit: String) {
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
    perc("%")
}

typealias CssSize = Pair<Int, UNIT>

enum class BORDERSTYLE(val borderStyle: String) {
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

enum class COLOR(val color: String) {
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

enum class BGSIZE(val size: String) {
    COVER("cover"),
    CONTAIN("contain")
}

enum class BGREPEAT(val repeat: String) {
    REPEAT("repeat"),
    REPEATX("repeat-x"),
    REPEATY("repeat-y"),
    NOREPEAT("no-repeat")
}

enum class BGATTACH(val attachment: String) {
    SCROLL("scroll"),
    FIXED("fixed"),
    LOCAL("local")
}

enum class BGORIGIN(val origin: String) {
    PADDING("padding-box"),
    BORDER("border-box"),
    CONTENT("content-box")
}

enum class BGCLIP(val clip: String) {
    PADDING("padding-box"),
    BORDER("border-box"),
    CONTENT("content-box")
}

class Border private constructor(private val width: CssSize? = null, private val style: BORDERSTYLE? = null,
                                 private val color: String? = null) {
    constructor(width: CssSize? = null, style: BORDERSTYLE? = null) : this(width, style, null)
    constructor(width: CssSize? = null, style: BORDERSTYLE? = null, color: Int) : this(width, style,
            "#" + Utils.intToHexString(color))

    constructor(width: CssSize? = null, style: BORDERSTYLE? = null, color: COLOR) : this(width, style, color.color)

    fun asString(): String {
        val w = width?.let {
            it.first.toString() + it.second.unit
        }
        return w.orEmpty() + " " + (style?.borderStyle).orEmpty() + " " + color.orEmpty()
    }
}

class Color private constructor(private val color: String? = null) {
    constructor(color: Int) : this("#" + Utils.intToHexString(color))
    constructor(color: COLOR) : this(color.color)

    fun asString(): String {
        return color.orEmpty()
    }
}

class Background private constructor(private val color: String? = null, private val image: ResString? = null,
                                     private val positionX: CssSize? = null, private val positionY: CssSize? = null,
                                     private val sizeX: CssSize? = null, private val sizeY: CssSize? = null,
                                     private val size: BGSIZE? = null, private val repeat: BGREPEAT? = null,
                                     private val origin: BGORIGIN? = null, private val clip: BGCLIP? = null,
                                     private val attachment: BGATTACH? = null) {
    constructor(image: ResString? = null, positionX: CssSize? = null, positionY: CssSize? = null,
                sizeX: CssSize? = null, sizeY: CssSize? = null, size: BGSIZE? = null,
                repeat: BGREPEAT? = null, origin: BGORIGIN? = null, clip: BGCLIP? = null,
                attachment: BGATTACH? = null) : this(null,
            image, positionX, positionY, sizeX, sizeY, size, repeat, origin, clip, attachment)

    constructor(color: Int, image: ResString? = null, positionX: CssSize? = null,
                positionY: CssSize? = null,
                sizeX: CssSize? = null, sizeY: CssSize? = null, size: BGSIZE? = null,
                repeat: BGREPEAT? = null, origin: BGORIGIN? = null, clip: BGCLIP? = null,
                attachment: BGATTACH? = null) : this("#" +
            Utils.intToHexString(color), image, positionX, positionY, sizeX, sizeY, size, repeat, origin, clip,
            attachment)

    constructor(color: COLOR, image: ResString? = null, positionX: CssSize? = null,
                positionY: CssSize? = null, sizeX: CssSize? = null, sizeY: CssSize? = null,
                size: BGSIZE? = null, repeat: BGREPEAT? = null, origin: BGORIGIN? = null, clip: BGCLIP? = null,
                attachment: BGATTACH? = null) : this(color.color, image,
            positionX, positionY, sizeX, sizeY, size, repeat, origin, clip, attachment)

    fun asString(): String {
        val img = image?.let {
            "url($image)"
        }
        val posX = positionX?.let {
            it.first.toString() + it.second.unit
        }
        val posY = positionY?.let {
            it.first.toString() + it.second.unit
        }
        val sX = sizeX?.let {
            it.first.toString() + it.second.unit
        }
        val sY = sizeY?.let {
            it.first.toString() + it.second.unit
        }
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
