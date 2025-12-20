package day03

import common.day
import common.util.Vec2i

// answer #1: 2592
// answer #2: 2360

fun main() {
    day(n = 3) {
        part1 { input ->
            countHousesReceivingPresents(
                instructions = input.lines.first(),
                workers = 1,
            )
        }
        verify {
            expect result 2592
        }

        part2 { input ->
            countHousesReceivingPresents(
                instructions = input.lines.first(),
                workers = 2,
            )
        }
        verify {
            expect result 2360
        }
    }
}

private fun countHousesReceivingPresents(
    instructions: String,
    workers: Int,
): Int {
    val positions = Array(workers) { Vec2i.Origin }
    val visited = mutableSetOf(Vec2i.Origin)
    instructions.forEachIndexed { i, ch ->
        positions[i % workers] += when (ch) {
            '^' -> Vec2i(0, -1)
            '<' -> Vec2i(-1, 0)
            '>' -> Vec2i(1, 0)
            'v' -> Vec2i(0, 1)
            else -> error("Unexpected character: $ch")
        }
        visited += positions[i % workers]
    }
    return visited.size
}
