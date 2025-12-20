package day05

import common.day

// answer #1: 255
// answer #2: 55

fun main() {
    day(n = 5) {
        part1 { input ->
            input.lines
                .filter(::hasNoForbiddenPatterns)
                .filter(::hasPair)
                .filter(::hasVowels)
                .size
        }
        verify {
            expect result 255
        }

        part2 { input ->
            input.lines
                .filter(::hasRepeatingWithSpace)
                .filter(::hasRepeatingPair)
                .size
        }
        verify {
            expect result 55
        }
    }
}

private fun hasPair(string: String): Boolean =
    string.windowed(size = 2, partialWindows = false)
        .any { it.first() == it.last() }

private fun hasNoForbiddenPatterns(string: String): Boolean =
    setOf("ab", "cd", "pq", "xy").none { it in string }

private fun hasVowels(string: String): Boolean =
    string.count { it in setOf('a', 'e', 'i', 'o', 'u') } >= 3

private fun hasRepeatingWithSpace(string: String): Boolean =
    string.windowed(3).any { it.first() == it.last() }

private fun hasRepeatingPair(string: String): Boolean =
    string.windowed(size = 2)
        .groupingBy { it }
        .eachCount()
        .filter { it.value >= 2 }
        .keys
        .any { it in string.replaceFirst(it, "") }
