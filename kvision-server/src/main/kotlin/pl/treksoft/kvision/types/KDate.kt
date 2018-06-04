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

import com.github.andrewoma.kwery.mapper.SimpleConverter
import com.github.andrewoma.kwery.mapper.TableConfiguration
import com.github.andrewoma.kwery.mapper.reifiedConverter
import com.github.andrewoma.kwery.mapper.standardConverters
import com.github.andrewoma.kwery.mapper.util.camelToLowerUnderscore
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * A serializable wrapper for a multiplatform Date type.
 */
@Suppress("MayBeConstant")
actual val KDATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

internal actual fun nowDate(): KDate =
    KDate(Date().time)

internal actual fun String.toKDateF(format: String): KDate =
    KDate(SimpleDateFormat(format).parse(this).time)

internal actual fun KDate.toStringF(format: String) =
    SimpleDateFormat(format).format(this.toJava())

fun KDate.toJava(): java.util.Date = java.util.Date(this.time)

object KDateConverter : SimpleConverter<KDate>(
    { row, c -> KDate(row.timestamp(c).time) },
    { Timestamp(it.time) }
)

val kvTableConfig = TableConfiguration(
    converters = standardConverters + reifiedConverter(KDateConverter),
    namingConvention = camelToLowerUnderscore
)
