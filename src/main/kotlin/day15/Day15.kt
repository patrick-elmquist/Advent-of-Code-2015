package day15

import common.day

// answer #1: 13882464
// answer #2: 11171160

fun main() {
    day(n = 15) {
        part1 { input ->
            val p = input.lines
                .map { line ->
                    line.split(", ").map { it.split(" ").last().toLong() }.dropLast(1)
                }

            fun sum(a: Int, b: Int): Long {
                return p.first().indices.map { i ->
                    (p[0][i] * a + p[1][i] * b).coerceAtLeast(0)
                }.reduce(Long::times)
            }

            fun sum(a: Int, b: Int, c: Int, d: Int): Long {
                return p.first().indices.map { i ->
                    (p[0][i] * a + p[1][i] * b + p[2][i] * c + p[3][i] * d).coerceAtLeast(0)
                }.reduce { acc, i -> acc * i }
            }

            if (input.lines.size == 2) {
                generateDistributions(100, 2)
                    .maxOf { sum(it[0], it[1]) }
            } else {
                generateDistributions(100, 4)
                    .maxOf { sum(it[0], it[1], it[2], it[3]) }
            }
        }
        verify {
            expect result 13882464L
            run test 1 expect 62842880L
        }

        part2 { input ->
            val p = input.lines
                .map { line ->
                    line.split(", ").map { it.split(" ").last().toLong() }
                }

            fun sum(a: Int, b: Int): Long {
                val map = p.first().indices.map { i ->
                    (p[0][i] * a + p[1][i] * b).coerceAtLeast(0)
                }
                return if (map.last() == 500L) {
                    map.dropLast(1).reduce(Long::times)
                } else {
                    Long.MIN_VALUE
                }
            }

            fun sum(a: Int, b: Int, c: Int, d: Int): Long {
                val map = p.first().indices.map { i ->
                    (p[0][i] * a + p[1][i] * b + p[2][i] * c + p[3][i] * d).coerceAtLeast(0)
                }
                return if (map.last() == 500L) {
                    map.dropLast(1).reduce(Long::times)
                } else {
                    Long.MIN_VALUE
                }
            }

            if (input.lines.size == 2) {
                generateDistributions(100, 2)
                    .maxOf { sum(it[0], it[1]) }
            } else {
                generateDistributions(100, 4)
                    .maxOf { sum(it[0], it[1], it[2], it[3]) }
            }
        }
        verify {
            expect result 11171160L
            run test 1 expect 57600000L
        }
    }
}

private fun generateDistributions(coins: Int, people: Int): Sequence<List<Int>> = sequence {
    // Base Case: If only 1 person left, they take all remaining coins
    if (people == 1) {
        yield(listOf(coins))
        return@sequence
    }

    // Recursive Step: Iterate through how many coins the current person gets
    for (amount in 0..coins) {
        val remainingCoins = coins - amount
        val remainingPeople = people - 1

        // Recurse for the rest of the people
        for (tail in generateDistributions(remainingCoins, remainingPeople)) {
            // Combine current person's share with the result from the others
            yield(listOf(amount) + tail)
        }
    }
}
