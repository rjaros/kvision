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

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlin.js.Date
import kotlin.math.absoluteValue


/**
 * JSON date serializer.
 */
internal object JsonDateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = SerialDescriptor("kotlin.js.Date")

    override fun deserialize(decoder: Decoder): Date {
        return decoder.decodeString().toDateInternal()
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.toStringInternal())
    }
}

@Suppress("ComplexMethod", "MagicNumber")
internal fun String.toDateInternal(): Date {
    val dt = this.split(':', 'T', '-', '+')
    val utcCheck = this[length - 1] == 'Z'
    val ds = if (utcCheck) dt[5].dropLast(1).split(".") else dt[5].split(".")
    val tzCheck = this[length - 6]
    return if (!utcCheck && tzCheck != '-' && tzCheck != '+') {
        Date(
            dt[0].toInt(),
            dt[1].toInt() - 1,
            dt[2].toInt(),
            dt[3].toInt(),
            dt[4].toInt(),
            ds[0].toInt(),
            if (ds.size == 2) ds[1].toInt() else 0
        )
    } else {
        val sign = if (utcCheck || tzCheck == '+') 1 else -1
        Date(
            Date.UTC(
                dt[0].toInt(),
                dt[1].toInt() - 1,
                dt[2].toInt(),
                if (utcCheck) {
                    dt[3].toInt()
                } else {
                    dt[3].toInt() - sign * dt[6].toInt()
                },
                dt[4].toInt(),
                ds[0].toInt(),
                if (ds.size == 2) ds[1].toInt() else 0
            )
        )
    }
}

internal fun Date.toStringInternal(): String {
    @Suppress("MagicNumber")
    val tz = this.getTimezoneOffset() / 60
    val sign = if (tz > 0) "-" else "+"
    return "" + this.getFullYear() + "-" + ("0" + (this.getMonth() + 1)).takeLast(2) + "-" +
            ("0" + this.getDate()).takeLast(2) + "T" + ("0" + this.getHours()).takeLast(2) + ":" +
            ("0" + this.getMinutes()).takeLast(2) + ":" + ("0" + this.getSeconds()).takeLast(2) + "." +
            this.getMilliseconds() + sign + ("0${tz.absoluteValue}").takeLast(2) + ":00"
}
