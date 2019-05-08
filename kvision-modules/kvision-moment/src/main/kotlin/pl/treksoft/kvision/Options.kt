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

package com.fleethome.util.kvision

import pl.treksoft.kvision.utils.obj
import kotlin.js.Promise

/**
 * Format Display Option.
 */
enum class FormatDisplayOption(internal val displayOption: String) {
    MonthM("M"),
    MonthMo("Mo"),
    MonthMM("MM"),
    MonthMMM("MMM"),
    MonthMMMM("MMMM"),
    QuarterQ("Q"),
    QuarterQo("Qo"),
    DayOfMonthD("D"),
    DayOfMonthDo("Do"),
    DayOfMonthDD("DD"),
    DayOfYearDDD("DDD"),
    DayOfYearDDDo("DDDo"),
    DayOfYearDDDD("DDDD"),
    DayOfWeekdo("do"),
    DayOfWeekdd("dd"),
    DayOfWeekddd("ddd"),
    DayOfWeekdddd("dddd"),
    DayOfWeekLocale("e"),
    DayOfWeekISO("E"),
    WeekOfYearw("w"),
    WeekOfYearwo("wo"),
    WeekOfYearww("ww"),
    WeekOfYearISOW("W"),
    WeekOfYearISOWo("Wo"),
    WeekOfYearISOWW("WW"),
    YearYY("YY"),
    YearYYYY("YYYY"),
    YearY("Y"),
    WeekYeargg("gg"),
    WeekYeargggg("gggg"),
    WeekYearISOGG("GG"),
    WeekYearISOGGGG("GGGG"),
    AmPmUpperCase("A"),
    AmPmLowerCase("a"),
    HourH("H"),
    HourHH("HH"),
    Hourh("h"),
    Hourhh("hh"),
    Hourk("k"),
    Hourkk("kk"),
    Minutem("m"),
    Minutemm("mm"),
    Seconds("s"),
    Secondss("ss"),
    FractionalSecondS("S"),
    FractionalSecondSS("SS"),
    FractionalSecondSSS("SSS"),
    FractionalSecondSSSS("SSSS"),
    TimeZoneZ("Z"),
    TimeZoneZZ("ZZ"),
    Unix("X"),
    UnixMM("x")
}

/**
 * Localized Format Display Option.
 */
enum class LocalizedFormatDisplayOption(internal val displayOption: String) {
    LT("LT"),
    LTS("LTS"),
    L("L"),
    l("l"),
    LL("LL"),
    ll("ll"),
    LLL("LLL"),
    lll("lll"),
    LLLL("LLLL"),
    llll("llll")
}