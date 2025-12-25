package day25

import common.day

// answer #1: 19980801
// answer #2: N/A

fun main() {
    day(n = 25) {
        part1 { input ->
            val split = input.lines.first().split(" ")
            val row = split.dropLast(2).last().dropLast(1).toInt()
            val col = split.last().dropLast(1).toInt()

            var x = 1
            var y = 1
            var yMax = 1
            var n = 20151125L
            while (true) {
                if (y == 1) {
                    yMax++
                    x = 1
                    y = yMax
                } else {
                    x += 1
                    y -= 1
                }
                n = (n * 252533L) % 33554393L
                if (x == col && y == row) break
            }
            n
        }
        verify {
            expect result 19980801L
        }

        part2 {
            // N/A
        }
        verify {
            expect result Unit
        }
    }
}
