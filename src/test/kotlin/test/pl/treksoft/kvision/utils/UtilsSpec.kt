package test.pl.treksoft.kvision.utils

import pl.treksoft.kvision.utils.toDateF
import pl.treksoft.kvision.utils.toHexString
import pl.treksoft.kvision.utils.toStringF
import test.pl.treksoft.kvision.SimpleSpec
import kotlin.js.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsSpec : SimpleSpec {

    @Test
    fun intToHexString() {
        run {
            val res = 0xabcdef.toHexString()
            assertEquals("abcdef", res, "Should convert int value to hex string")
            val res2 = 0x123456.toHexString()
            assertEquals("123456", res2, "Should convert int value to hex string")
        }
    }

    @Test
    fun toDateF() {
        run {
            val res = "2017-03-14 14:50:35".toDateF()
            assertEquals(
                js("new Date(2017,2,14,14,50,35).getTime()"),
                res.getTime(),
                "Should convert String value to Date"
            )
        }
    }

    @Test
    fun toStringF() {
        run {
            val date = js("new Date()")
            val res = Date().toStringF()
            val y = date.getFullYear()
            val m = date.getMonth() + 1
            val m2 = if (m < 10) "0$m" else "$m"
            val d = date.getDate()
            val d2 = if (d < 10) "0$d" else "$d"
            val h = date.getHours()
            val h2 = if (h < 10) "0$h" else "$h"
            val min = date.getMinutes()
            val min2 = if (min < 10) "0$min" else "$min"
            val sec = date.getSeconds()
            val sec2 = if (sec < 10) "0$sec" else "$sec"
            assertEquals("$y-$m2-$d2 $h2:$min2:$sec2", res, "Should convert Date value to String")
        }
    }
}
