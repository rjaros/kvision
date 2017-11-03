package test.pl.treksoft.kvision.utils

import pl.treksoft.kvision.utils.toHexString
import test.pl.treksoft.kvision.SimpleSpec
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
}