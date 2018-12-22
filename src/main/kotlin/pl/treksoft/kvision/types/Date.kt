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
import kotlinx.serialization.internal.SerialClassDescImpl
import pl.treksoft.kvision.KVManager
import kotlin.js.Date

actual val KV_DATE_FORMAT = "YYYY-MM-DD HH:mm:ss"

actual typealias Date = kotlin.js.Date

/**
 * Extension function to convert String to Date with a given date format.
 * @param format date/time format
 * @return Date object
 */
@Suppress("UnsafeCastFromDynamic")
actual fun String.toDateF(format: String): Date {
    val result = KVManager.fecha.parse(this, format)
    return if (result) result else Date()
}

/**
 * Extension function to convert Date to String with a given date format.
 * @param format date/time format
 * @return String object
 */
@Suppress("UnsafeCastFromDynamic")
actual fun Date.toStringF(format: String): String {
    return KVManager.fecha.format(this, format)
}

object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = SerialClassDescImpl("kotlin.js.Date")

    override fun deserialize(input: Decoder): Date {
        return input.decodeString().toDateF()
    }

    override fun serialize(output: Encoder, obj: Date) {
        output.encodeString(obj.toStringF())
    }
}
