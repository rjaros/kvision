package pl.treksoft.kvision.remote

typealias NameGenerator = () -> String

fun createNameGenerator(prefix: String): NameGenerator {
    var counter = 0
    return { "$prefix${counter++}" }
}
