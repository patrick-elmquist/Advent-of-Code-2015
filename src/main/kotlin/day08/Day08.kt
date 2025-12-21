package day08

import common.day

// answer #1: 1342
// answer #2: 2074

fun main() {
    day(n = 8) {
        part1 { input ->
            input.lines.sumOf { line ->
                val after = line
                    .removeSurrounding("\"")
                    .replace("\\\\", "1")
                    .replace("\\\"", "2")
                val countHex = after.windowed(4).count { it.startsWith("\\x") }
                line.length - (after.length - countHex * 3)
            }
        }
        verify {
            expect result 1342
            run test 1 expect 12
        }

        part2 { input ->
            input.lines.sumOf { line ->
                2 + line.sumOf {
                    when (it) {
                        '"', '\\' -> 2
                        else -> 1
                    }
                } - line.length
            }

        }
        verify {
            expect result 2074
            run test 1 expect 19
        }
    }
}
