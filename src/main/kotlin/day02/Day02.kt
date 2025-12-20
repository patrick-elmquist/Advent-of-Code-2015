package day02

import common.day
import common.util.match

// answer #1: 1598415
// answer #2: 3812909

private val regex = """(\d+)x(\d+)x(\d+)""".toRegex()

fun main() {
    day(n = 2) {
        part1 { input ->
            input.lines.sumOf { dimensions ->
                val (l, w, h) = regex.match(dimensions).toList().map(String::toInt)
                val sides = listOf(l * w, w * h, h * l)
                2 * sides.sum() + sides.min()
            }
        }
        verify {
            expect result 1598415
            run test 1 expect 101
        }

        part2 { input ->
            input.lines.sumOf { dimensions ->
                val list = regex.match(dimensions).toList().map(String::toInt)
                val volume = list.reduce(Int::times)
                val perimeter = 2 * list.sorted().take(2).sum()
                perimeter + volume
            }
        }
        verify {
            expect result 3812909
            run test 1 expect 48
        }
    }
}
