package pl.treksoft.kvision.core

typealias ResString = String

object Img {
    operator fun invoke(src: String) = at(src)

    @Suppress("UnsafeCastFromDynamic", "NOTHING_TO_INLINE")
    private inline fun at(src: String): ResString = pl.treksoft.kvision.require("./img/" + src)
}
