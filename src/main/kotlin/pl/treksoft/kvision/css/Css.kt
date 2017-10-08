package pl.treksoft.kvision.css

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
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    BLACK("black"),
    WHITE("white")
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
        return w.orEmpty() + " " + style?.borderStyle.orEmpty() + " " + color.orEmpty()
    }
}
