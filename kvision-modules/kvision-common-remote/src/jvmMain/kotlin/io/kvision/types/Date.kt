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
package io.kvision.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

/**
 * @suppress internal object
 * JSON LocalDateTime serializer.
 */
internal object JsonLocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("java.time.LocalDateTime")

    override fun deserialize(decoder: Decoder): LocalDateTime {
        @Suppress("MagicNumber")
        return LocalDateTime.parse(
            decoder.decodeString().split("[").first().dropLast(6),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        )
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
    }
}

/**
 * @suppress internal object
 * JSON LocalDate serializer.
 */
internal object JsonLocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("java.time.LocalDate")

    override fun deserialize(decoder: Decoder): LocalDate {
        @Suppress("MagicNumber")
        return LocalDate.parse(
            decoder.decodeString().split("[").first().dropLast(6),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        )
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
    }
}

/**
 * @suppress internal object
 * JSON LocalTime serializer.
 */
internal object JsonLocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("java.time.LocalTime")

    override fun deserialize(decoder: Decoder): LocalTime {
        @Suppress("MagicNumber")
        return LocalTime.parse(
            decoder.decodeString().split("[").first().dropLast(6),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        )
    }

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(
            value.atDate(LocalDate.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
        )
    }
}

/**
 * @suppress internal object
 * JSON OffsetDateTime serializer.
 */
internal object JsonOffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("java.time.OffsetDateTime")

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(
            decoder.decodeString().split("[").first(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
        )
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(
            value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"))
        )
    }
}

/**
 * @suppress internal object
 * JSON OffsetTime serializer.
 */
internal object JsonOffsetTimeSerializer : KSerializer<OffsetTime> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("java.time.OffsetTime")

    override fun deserialize(decoder: Decoder): OffsetTime {
        return OffsetTime.parse(
            decoder.decodeString().split("[").first(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
        )
    }

    override fun serialize(encoder: Encoder, value: OffsetTime) {
        encoder.encodeString(
            value.atDate(LocalDate.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"))
        )
    }
}

/**
 * @suppress internal object
 * JSON ZonedDateTime serializer.
 */
internal object JsonZonedDateTimeSerializer : KSerializer<ZonedDateTime> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("java.time.ZonedDateTime")

    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return ZonedDateTime.parse(
            decoder.decodeString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ'['VV']'")
        )
    }

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(
            value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ'['VV']'"))
        )
    }
}
