package day20

import common.day
import kotlin.math.sqrt

// answer #1: 776160
// answer #2: 786240

private const val GOAL = 33100000L

fun main() {
    day(n = 20) {
        part1 {
            fun packetsForHouse(n: Long): Long =
                getDivisors(n).sumOf { it * 10L }

            var n = 0L
            do n++ while (packetsForHouse(n) < GOAL)
            n
        }
        verify {
            expect result 776160L
        }

        part2 {
            fun packetsForHouse(n: Long): Long =
                getDivisors(n)
                    .filter { n <= it * 50 }
                    .sumOf { it * 11L }

            var n = 0L
            do n++ while (packetsForHouse(n) < GOAL)
            n
        }
        verify {
            expect result 786240L
        }
    }
}

private fun getDivisors(n: Long): List<Long> {
    val divisors = mutableListOf<Long>()
    val sqrtN = sqrt(n.toDouble()).toLong()

    for (i in sqrtN downTo 1) {

        if (n % i == 0L) {
            divisors.add(i)

            val pair = n / i
            if (i != pair) {
                divisors.add(pair)
            }
        }
    }
    return divisors
}
