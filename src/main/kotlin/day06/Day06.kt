package day06

import common.day
import common.util.match

// answer #1: 543903
// answer #2: 14687245

private val regex = """(.*) (\d+),(\d+) through (\d+),(\d+)""".toRegex()

fun main() {
    day(n = 6) {
        part1 { input ->
            val matrix = Array(1000) { Array(1000) { 0 } }
            input.lines.forEach {
                val (instr, x1, y1, x2, y2) = regex.match(it)
                (y1.toInt()..y2.toInt()).forEach { y ->
                    (x1.toInt()..x2.toInt()).forEach { x ->
                        matrix[y][x] = when (instr) {
                            "toggle" -> if (matrix[y][x] == 1) 0 else 1
                            "turn on" -> 1
                            "turn off" -> 0
                            else -> error("Unrecognized light: $instr")
                        }
                    }
                }
            }
            matrix.sumOf { row -> row.count { it == 1 } }
        }
        verify {
            expect result 543903
        }

        part2 { input ->
            val matrix = Array(1000) { Array(1000) { 0 } }
            input.lines.forEach {
                val (instr, x1, y1, x2, y2) = regex.match(it)
                val change = when (instr) {
                    "toggle" -> 2
                    "turn on" -> 1
                    "turn off" -> -1
                    else -> error("Unrecognized light: $instr")
                }
                (y1.toInt()..y2.toInt()).forEach { y ->
                    (x1.toInt()..x2.toInt()).forEach { x ->
                        matrix[y][x] = (matrix[y][x] + change).coerceAtLeast(0)
                    }
                }
            }
            matrix.sumOf(Array<Int>::sum)
        }
        verify {
            expect result 14687245
        }
    }
}
