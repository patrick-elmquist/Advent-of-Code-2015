package day10

import common.day

// answer #1: 252594
// answer #2: 3579328

fun main() {
    day(n = 10) {
        part1 { input ->
            var string = input.lines.first()
            repeat(40) { string = expand(string) }
            string.length
        }
        verify {
            expect result 252594
        }

        part2 { input ->
            var string = input.lines.first()
            repeat(50) { string = expand(string) }
            string.length
        }
        verify {
            expect result 3579328
        }
    }
}

private val regex = """(.)\1*""".toRegex()
private fun expand(input: String): String =
    regex
        .findAll(input)
        .map { it.value }
        .joinToString("") { "${it.length}${it.first()}" }
