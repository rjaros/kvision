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

package io.kvision.remote

import dev.kilua.rpc.RpcSerialization
import io.kvision.types.JsonLocalDateSerializer
import io.kvision.types.JsonLocalDateTimeSerializer
import io.kvision.types.JsonLocalTimeSerializer
import io.kvision.types.JsonOffsetDateTimeSerializer
import io.kvision.types.JsonOffsetTimeSerializer
import io.kvision.types.JsonZonedDateTimeSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime

actual fun registerRemoteTypes() {
    RpcSerialization.customConfiguration = Json {
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, JsonLocalDateTimeSerializer)
            contextual(LocalDate::class, JsonLocalDateSerializer)
            contextual(LocalTime::class, JsonLocalTimeSerializer)
            contextual(OffsetDateTime::class, JsonOffsetDateTimeSerializer)
            contextual(OffsetTime::class, JsonOffsetTimeSerializer)
            contextual(ZonedDateTime::class, JsonZonedDateTimeSerializer)
        }
    }
}
