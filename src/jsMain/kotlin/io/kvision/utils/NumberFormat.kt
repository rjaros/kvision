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

package io.kvision.utils

import kotlin.math.floor
import kotlin.math.pow

external object Intl {
    class NumberFormat(locales: String = definedExternally, options: NumberFormatOptions = definedExternally) {
        constructor(locales: Nothing?, options: NumberFormatOptions = definedExternally)

        fun format(num: Number): String
    }
}

/**
 * Options for Intl.NumberFormat
 *
 * Documentation by Mozilla Contributors is licensed under CC-BY-SA 2.5.
 */
external interface NumberFormatOptions {
    /** Only used when notation is "compact". Takes either "short" (default) or "long". */
    var compactDisplay: String

    /** The currency to use in currency formatting. Possible values are the ISO 4217 currency codes, such as "USD" for the US dollar, "EUR" for the euro, or "CNY" for the Chinese RMB — see the Current currency & funds code list. There is no default value; if the style is "currency", the currency property must be provided. */
    var currency: String

    /** How to display the currency in currency formatting. Possible values are:

    - "symbol" to use a localized currency symbol such as €, this is the default value,
    - "narrowSymbol" to use a narrow format symbol ("$100" rather than "US$100"),
    - "code" to use the ISO currency code,
    - "name" to use a localized currency name such as "dollar",
     */
    var currencyDisplay: String


    /** In many locales, accounting format means to wrap the number with parentheses instead of appending a minus sign. You can enable this formatting by setting the currencySign option to "accounting". The default value is "standard". */
    var currencySign: String

    /** The locale matching algorithm to use. Possible values are "lookup" and "best fit"; the default is "best fit". For information about this option, see the Intl page. */
    var localeMatcher: String

    /** The formatting that should be displayed for the number, the defaults is "standard"

    - "standard" plain number formatting
    - "scientific" return the order-of-magnitude for formatted number.
    - "engineering" return the exponent of ten when divisible by three
    - "compact" string representing exponent, defaults is using the "short" form.
     */
    var notation: String


    /** Numbering System. Possible values include: "arab", "arabext", " bali", "beng", "deva", "fullwide", " gujr", "guru", "hanidec", "khmr", " knda", "laoo", "latn", "limb", "mlym", " mong", "mymr", "orya", "tamldec", " telu", "thai", "tibt". */
    var numberingSystem: String

    /** When to display the sign for the number; defaults to "auto"

    - "auto" sign display for negative numbers only
    - "never" never display sign
    - "always" always display sign
    - "exceptZero" sign display for positive and negative numbers, but not zero
     */
    var signDisplay: String


    /** The formatting style to use , the default is "decimal".

    - "decimal" for plain number formatting.
    - "currency" for currency formatting.
    - "percent" for percent formatting
    - "unit" for unit formatting
     */
    var style: String


    /** The unit to use in unit formatting, Possible values are core unit identifiers, defined in UTS #35, Part 2, Section 6. A subset of units from the full list was selected for use in ECMAScript. Pairs of simple units can be concatenated with "-per-" to make a compound unit. There is no default value; if the style is "unit", the unit property must be provided. */
    var unit: String

    /** The unit formatting style to use in unit formatting, the defaults is "short".

    - "long" (e.g., 16 litres)
    - "short" (e.g., 16 l)
    - "narrow" (e.g., 16l)
     */
    var unitDisplay: String

    /** Whether to use grouping separators, such as thousands separators or thousand/lakh/crore separators. Possible values are true and false; the default is true. */
    var useGrouping: Boolean


    /** The minimum number of integer digits to use. Possible values are from 1 to 21; the default is 1. */
    var minimumIntegerDigits: Int

    /** The minimum number of fraction digits to use. Possible values are from 0 to 20; the default for plain number and percent formatting is 0; the default for currency formatting is the number of minor unit digits provided by the ISO 4217 currency code list (2 if the list doesn't provide that information). */
    var minimumFractionDigits: Int

    /** The maximum number of fraction digits to use. Possible values are from 0 to 20; the default for plain number formatting is the larger of minimumFractionDigits and 3; the default for currency formatting is the larger of minimumFractionDigits and the number of minor unit digits provided by the ISO 4217 currency code list (2 if the list doesn't provide that information); the default for percent formatting is the larger of minimumFractionDigits and 0. */
    var maximumFractionDigits: Int

    /** The minimum number of significant digits to use. Possible values are from 1 to 21; the default is 1. */
    var minimumSignificantDigits: Int

    /** The maximum number of significant digits to use. Possible values are from 1 to 21; the default is 21. */
    var maximumSignificantDigits: Int
}

fun numberFormat(optionsBuilder: NumberFormatOptions.() -> Unit): Intl.NumberFormat =
    Intl.NumberFormat(undefined, Any().unsafeCast<NumberFormatOptions>().apply(optionsBuilder))

fun numberFormat(locales: String, optionsBuilder: NumberFormatOptions.() -> Unit): Intl.NumberFormat =
    Intl.NumberFormat(locales, Any().unsafeCast<NumberFormatOptions>().apply(optionsBuilder))

/**
 * Formats a number to fixed decimal digits without rounding.
 */
fun Number.toFixedNoRound(precision: Int): String {
    val factor = 10.0.pow(precision)
    return (floor(this.toDouble() * factor) / factor).toString()
}
