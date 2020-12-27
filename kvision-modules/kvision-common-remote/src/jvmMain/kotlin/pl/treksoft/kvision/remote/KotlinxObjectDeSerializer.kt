/*
 * Copyright (c) 2017-present Robert Jaros
 * Copyright (c) 2020 Yannik Hampe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.remote

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import pl.treksoft.kvision.types.JsonBigDecimalSerializer
import pl.treksoft.kvision.types.JsonLocalDateSerializer
import pl.treksoft.kvision.types.JsonLocalDateTimeSerializer
import pl.treksoft.kvision.types.JsonLocalTimeSerializer
import pl.treksoft.kvision.types.JsonOffsetDateTimeSerializer
import pl.treksoft.kvision.types.JsonOffsetTimeSerializer
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime

fun kotlinxObjectDeSerializer(): ObjectDeSerializer = KotlinxObjectDeSerializer

val kvSerializersModule = SerializersModule {
    contextual(LocalDateTime::class, JsonLocalDateTimeSerializer)
    contextual(LocalDate::class, JsonLocalDateSerializer)
    contextual(LocalTime::class, JsonLocalTimeSerializer)
    contextual(OffsetDateTime::class, JsonOffsetDateTimeSerializer)
    contextual(OffsetTime::class, JsonOffsetTimeSerializer)
    contextual(BigDecimal::class, JsonBigDecimalSerializer)
}

private object KotlinxObjectDeSerializer : ObjectDeSerializer {

    val json = Json {
        ignoreUnknownKeys = true
        serializersModule = kvSerializersModule
    }

    override fun <T> deserialize(str: String?, serializer: KSerializer<T>): T =
        @Suppress("UNCHECKED_CAST")
        if (str == null) null as T
        else json.decodeFromString(serializer, str)

    override fun <T> serializeNonNullToString(obj: T, serializer: KSerializer<T>): String =
        json.encodeToString(serializer, obj)
}

inline fun <reified T> ObjectDeSerializer.deserialize(str: String?): T =
    deserialize(str, kvSerializersModule.serializer())

inline fun <reified T> ObjectDeSerializer.serializeNonNull(obj: T) =
    serializeNonNullToString(obj, kvSerializersModule.serializer())

inline fun <reified T> ObjectDeSerializer.serializeNullable(obj: T?) =
    serializeNullableToString(obj, kvSerializersModule.serializer())
