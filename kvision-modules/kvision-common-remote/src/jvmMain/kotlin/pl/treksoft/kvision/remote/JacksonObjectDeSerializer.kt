package pl.treksoft.kvision.remote

fun jacksonObjectDeSerializer(): ObjectDeSerializer = JacksonObjectDeSerializer

private object JacksonObjectDeSerializer : ObjectDeSerializer {
    private val mapper = createDefaultObjectMapper()

    override fun <T> deserialize(str: String?, type: Class<T>): T =
        if (str == null || type == String::class.java) type.cast(str)
        else mapper.readValue(str, type)

    override fun serializeNonNullToString(obj: Any): String =
        if (obj.javaClass == String::class.java) obj as String
        else mapper.writeValueAsString(obj)
}
