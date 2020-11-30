package pl.treksoft.kvision.progress

data class Bounds<T>(val min: T, val max: T)

fun Bounds<Number>.fraction(value: Double): Double {
    val minDouble = min.toDouble()
    val maxDouble = max.toDouble()
    return (value - minDouble) / (maxDouble - minDouble)
}
