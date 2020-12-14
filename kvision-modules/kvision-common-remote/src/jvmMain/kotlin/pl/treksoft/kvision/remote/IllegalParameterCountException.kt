package pl.treksoft.kvision.remote

class IllegalParameterCountException(val actualParameterCount: Int, val expectedParamterCount: Int) :
    RuntimeException() {
    override val message: String
        get() = "Expected <$expectedParamterCount> parameters, but got <$actualParameterCount> parameters"
}

fun requireParameterCountEqualTo(actualParameterCount: Int, expectedParamterCount: Int) {
    if (actualParameterCount != expectedParamterCount) {
        throw IllegalParameterCountException(actualParameterCount, expectedParamterCount)
    }
}

fun requireParameterCountEqualTo(params: Collection<*>, expectedParameterCount: Int) =
    requireParameterCountEqualTo(params.size, expectedParameterCount)

