package day11

import common.day

// answer #1: hepxxyzz
// answer #2: heqaabcc

fun main() {
    day(n = 11) {
        part1 { input ->
            var next = input.lines.first()
            while (!isValid(next)) next = nextPassword(next)
            next
        }
        verify {
            expect result "hepxxyzz"
        }

        part2 {
            var next = nextPassword("hepxxyzz")
            while (!isValid(next)) next = nextPassword(next)
            next
        }
        verify {
            expect result "heqaabcc"
        }
    }
}

private fun nextPassword(password: String): String {
    val new = password.toMutableList()
    var pos = password.length - 1
    while (true) {
        val c = password[pos] + 1
        if (c in 'a'..'z') {
            new[pos] = c
            return new.joinToString("")
        } else {
            new[pos] = 'a'
            pos--
        }
    }
}

private fun isValid(password: String): Boolean =
    hasStraight(password) && hasNoInvalidChars(password) && hasRepeatingPair(password)

private fun hasRepeatingPair(string: String): Boolean {
    val pairs = string.windowed(size = 2)
        .filter { it.first() == it.last() }

    if (pairs.size < 2) return false

    var s = string
    pairs.forEach { pair -> s = s.replace(pair, "") }
    return (string.length - s.length) == pairs.size * 2 && pairs.size >= 2
}

private fun hasStraight(password: String): Boolean =
    password.windowed(3).any { it.zipWithNext().all { (a, b) -> a.code + 1 == b.code } }

private fun hasNoInvalidChars(password: String): Boolean =
    password.none { it in setOf('i', 'o', 'l') }
