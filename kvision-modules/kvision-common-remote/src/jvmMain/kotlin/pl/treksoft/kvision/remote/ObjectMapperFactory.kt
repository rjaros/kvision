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

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import pl.treksoft.kvision.types.BigDecimalDeserializer
import pl.treksoft.kvision.types.BigDecimalSerializer
import pl.treksoft.kvision.types.LocalDateDeserializer
import pl.treksoft.kvision.types.LocalDateSerializer
import pl.treksoft.kvision.types.LocalDateTimeDeserializer
import pl.treksoft.kvision.types.LocalDateTimeSerializer
import pl.treksoft.kvision.types.LocalTimeDeserializer
import pl.treksoft.kvision.types.LocalTimeSerializer
import pl.treksoft.kvision.types.OffsetDateTimeDeserializer
import pl.treksoft.kvision.types.OffsetDateTimeSerializer
import pl.treksoft.kvision.types.OffsetTimeDeserializer
import pl.treksoft.kvision.types.OffsetTimeSerializer
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime

fun createDefaultObjectMapper() = jacksonObjectMapper().apply {
    registerModule(SimpleModule().apply {
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        addSerializer(LocalDate::class.java, LocalDateSerializer())
        addSerializer(LocalTime::class.java, LocalTimeSerializer())
        addSerializer(OffsetDateTime::class.java, OffsetDateTimeSerializer())
        addSerializer(OffsetTime::class.java, OffsetTimeSerializer())
        addSerializer(BigDecimal::class.java, BigDecimalSerializer())

        addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
        addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        addDeserializer(LocalTime::class.java, LocalTimeDeserializer())
        addDeserializer(OffsetDateTime::class.java, OffsetDateTimeDeserializer())
        addDeserializer(OffsetTime::class.java, OffsetTimeDeserializer())
        addDeserializer(BigDecimal::class.java, BigDecimalDeserializer())
    })
}
