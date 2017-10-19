package test.pl.treksoft.kvision.utils

import pl.treksoft.kvision.utils.Utils
import test.pl.treksoft.kvision.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsSpec : SimpleSpec {

    @Test
    fun intToHexString() {
        run {
            val res = Utils.intToHexString(0xabcdef)
            assertEquals("abcdef", res, "Should convert int value to hex string")
            val res2 = Utils.intToHexString(0x123456)
            assertEquals("123456", res2, "Should convert int value to hex string")
        }
    }
}