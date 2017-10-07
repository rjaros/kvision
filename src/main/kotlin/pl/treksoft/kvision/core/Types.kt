package pl.treksoft.kvision.core

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

typealias Length = Pair<Int, UNIT>
