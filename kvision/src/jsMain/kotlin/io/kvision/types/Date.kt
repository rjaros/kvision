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
import io.kvision.KVManager
import kotlin.js.Date

/**
 * Extension function to convert String to Date with a given date format.
 * @param format date/time format
 * @return Date object
 */
@Suppress("UnsafeCastFromDynamic")
fun String.toDateF(format: String = KV_DEFAULT_DATE_FORMAT): Date {
    val result = KVManager.fecha.parse(this, format)
    return if (result) result else Date()
}

/**
 * Extension function to convert String to Date with a given date format.
 * @param format date/time format
 * @return Date object or null if the conversion fails
 */
@Suppress("UnsafeCastFromDynamic")
fun String.toDateFOrNull(format: String = KV_DEFAULT_DATE_FORMAT): Date? {
    return KVManager.fecha.parse(this, format)
}

/**
 * Extension function to convert Date to String with a given date format.
 * @param format date/time format
 * @return String object
 */
@Suppress("UnsafeCastFromDynamic")
fun Date.toStringF(format: String = KV_DEFAULT_DATE_FORMAT): String {
    return KVManager.fecha.format(this, format)
}

object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("kotlin.js.Date")

    override fun deserialize(decoder: Decoder): Date {
        val str = decoder.decodeString()
        return if (str.length == 10) {
            "$str 00:00:00".toDateF()
        } else {
            str.toDateF()
        }
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.toStringF())
    }
}
