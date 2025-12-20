package day04

import common.day
import java.security.MessageDigest

// answer #1: 117946
// answer #2: 3938038

fun main() {
    day(n = 4) {
        part1 { input ->
            val secret = input.lines.first()
            findNumber(secret, numberOfZeroes = 5)
        }
        verify {
            expect result 117946
            run test 1 expect 609043
        }

        part2 { input ->
            val secret = input.lines.first()
            findNumber(secret, numberOfZeroes = 6)
        }
        verify {
            expect result 3938038
        }
    }
}

private fun findNumber(secret: String, numberOfZeroes: Int): Int {
    val startsWith = "0".repeat(numberOfZeroes)
    val md5 = MessageDigest.getInstance("MD5")
    var n = 0
    while (true) {
        val hash = md5.digest("$secret$n".toByteArray()).toHexString()
        if (hash.startsWith(startsWith)) {
            break
        } else {
            n++
        }
    }
    return n
}
