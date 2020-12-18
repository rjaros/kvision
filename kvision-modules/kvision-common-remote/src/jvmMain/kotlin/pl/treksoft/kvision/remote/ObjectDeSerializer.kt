package pl.treksoft.kvision.remote

interface ObjectDeSerializer {
    fun <T> deserialize(str: String?, type: Class<T>): T
    fun serializeNonNullToString(obj: Any): String

    fun serializeNullableToString(obj: Any?): String? =
        if (obj == null) null
        else serializeNonNullToString(obj)
}

inline fun <reified T> ObjectDeSerializer.deserialize(str: String?) = deserialize(str, T::class.java)
