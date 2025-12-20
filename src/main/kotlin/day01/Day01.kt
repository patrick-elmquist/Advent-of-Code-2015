package day01

import common.day

// answer #1: 232
// answer #2: 1783

fun main() {
    day(n = 1) {
        part1 { input ->
            input.lines.first()
                .runningFold(0) { floor, instruction ->
                    floor + if (instruction == '(') 1 else -1
                }
                .last()
        }
        verify {
            expect result 232
        }

        part2 { input ->
            input.lines.first()
                .runningFold(0) { floor, instruction ->
                    floor + if (instruction == '(') 1 else -1
                }
                .indexOfFirst { floor -> floor == -1 }
        }
        verify {
            expect result 1783
        }
    }
}
