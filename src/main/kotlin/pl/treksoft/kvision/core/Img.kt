package pl.treksoft.kvision.core

typealias ResString = String

object Img {
    operator fun invoke(src: ResString) = at(src)

    @Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
    private inline fun at(src: ResString): String = pl.treksoft.kvision.require("./img/" + src)
}
