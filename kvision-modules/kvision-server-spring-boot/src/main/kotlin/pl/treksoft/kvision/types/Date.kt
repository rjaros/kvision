/*
 * Copyright (c) 2017-present Robert Jaros
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
package pl.treksoft.kvision.types

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Deprecated("Compatibility with KVision 1. Use LocalDateTime or OffsetDateTime instead.")
actual typealias Date = LocalDateTime

actual typealias LocalDateTime = LocalDateTime

actual typealias LocalDate = LocalDate

actual typealias LocalTime = LocalTime

actual typealias OffsetDateTime = OffsetDateTime

actual typealias OffsetTime = OffsetTime

fun String.toDateTimeF(): LocalDateTime = LocalDateTime.parse(this)

fun String.toDateF(): LocalDate = LocalDate.parse(this)

fun String.toTimeF(): LocalTime = LocalTime.parse(this)

fun String.toOffsetDateTimeF(): OffsetDateTime = OffsetDateTime.parse(this)

fun String.toOffsetTimeF(): OffsetTime = OffsetTime.parse(this)

fun LocalDateTime.toStringF(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

fun LocalDate.toStringF(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE)

fun LocalTime.toStringF(): String = this.format(DateTimeFormatter.ISO_LOCAL_TIME)

fun OffsetDateTime.toStringF(): String = this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

fun OffsetTime.toStringF(): String = this.format(DateTimeFormatter.ISO_OFFSET_TIME)

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    @Throws(IOException::class)
    override fun serialize(value: LocalDateTime, gen: JsonGenerator, provider: SerializerProvider) {
        try {
            val s = value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            gen.writeString(s)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            gen.writeString("")
        }
    }
}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): LocalDateTime? {
        val str = p.text
        try {
            return LocalDateTime.parse(str.dropLast(6), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            return null
        }
    }
}

class LocalDateSerializer : JsonSerializer<LocalDate>() {
    @Throws(IOException::class)
    override fun serialize(value: LocalDate, gen: JsonGenerator, provider: SerializerProvider) {
        try {
            val s = value.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            gen.writeString(s)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            gen.writeString("")
        }
    }
}

class LocalDateDeserializer : JsonDeserializer<LocalDate>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): LocalDate? {
        val str = p.text
        try {
            return LocalDate.parse(str.dropLast(6), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            return null
        }
    }
}

class LocalTimeSerializer : JsonSerializer<LocalTime>() {
    @Throws(IOException::class)
    override fun serialize(value: LocalTime, gen: JsonGenerator, provider: SerializerProvider) {
        try {
            val s = value.atDate(LocalDate.now()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            gen.writeString(s)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            gen.writeString("")
        }
    }
}

class LocalTimeDeserializer : JsonDeserializer<LocalTime>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): LocalTime? {
        val str = p.text
        try {
            return LocalTime.parse(str.dropLast(6), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            return null
        }
    }
}

class OffsetDateTimeSerializer : JsonSerializer<OffsetDateTime>() {
    @Throws(IOException::class)
    override fun serialize(value: OffsetDateTime, gen: JsonGenerator, provider: SerializerProvider) {
        try {
            val s = value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            gen.writeString(s)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            gen.writeString("")
        }
    }
}

class OffsetDateTimeDeserializer : JsonDeserializer<OffsetDateTime>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): OffsetDateTime? {
        val str = p.text
        try {
            return OffsetDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            return null
        }
    }
}

class OffsetTimeSerializer : JsonSerializer<OffsetTime>() {
    @Throws(IOException::class)
    override fun serialize(value: OffsetTime, gen: JsonGenerator, provider: SerializerProvider) {
        try {
            val s = value.atDate(LocalDate.now()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            gen.writeString(s)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            gen.writeString("")
        }
    }
}

class OffsetTimeDeserializer : JsonDeserializer<OffsetTime>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): OffsetTime? {
        val str = p.text
        try {
            return OffsetTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            return null
        }
    }
}
