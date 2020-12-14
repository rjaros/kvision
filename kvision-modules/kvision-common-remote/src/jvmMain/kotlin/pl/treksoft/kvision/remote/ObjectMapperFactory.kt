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
