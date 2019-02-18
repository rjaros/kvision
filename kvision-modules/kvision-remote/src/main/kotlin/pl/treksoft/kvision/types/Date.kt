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
import kotlin.js.Date

actual typealias Date = kotlin.js.Date

/**
 * JSON date serializer.
 */
object JsonDateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = SerialClassDescImpl("kotlin.js.Date")

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeLong())
    }

    override fun serialize(encoder: Encoder, obj: Date) {
        encoder.encodeLong(obj.getTime().toLong())
    }
}

/**
 * @suppress
 * Not used in this module.
 */
actual val KV_DEFAULT_DATE_FORMAT = ""

/**
 * @suppress
 * Not used in this module.
 */
actual fun String.toDateF(format: String): Date {
    TODO("Unimplemented")
}

/**
 * @suppress
 * Not used in this module.
 */
actual fun Date.toStringF(format: String): String {
    TODO("Unimplemented")
}
