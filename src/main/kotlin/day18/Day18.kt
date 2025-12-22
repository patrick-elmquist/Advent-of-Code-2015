package day18

import common.Input
import common.day
import common.grid
import common.util.Vec2i
import common.util.neighbors
import kotlin.collections.set

// answer #1: 821
// answer #2: 886

fun main() {
    day(n = 18) {
        part1 { input ->
            val isTest = input.lines.size == 6
            val size = if (isTest) 6 else 100
            val steps = if (isTest) 4 else 100
            val endState = runSteps(input, steps, size)
            endState.values.count { it == '#' }
        }
        verify {
            expect result 821
            run test 1 expect 4
        }

        part2 { input ->
            val isTest = input.lines.size == 6
            val size = if (isTest) 6 else 100
            val steps = if (isTest) 5 else 100
            val staticOn = setOf(
                Vec2i(0, 0),
                Vec2i(0, size - 1),
                Vec2i(size - 1, size - 1),
                Vec2i(size - 1, 0),
            )
            val endState = runSteps(input, steps, size, staticOn)
            endState.values.count { it == '#' }
        }
        verify {
            expect result 886
            run test 1 expect 17
        }
    }
}

private fun runSteps(
    input: Input,
    steps: Int,
    size: Int,
    staticOn: Set<Vec2i> = emptySet(),
): Map<Vec2i, Char> {
    var state = input.grid.toMutableMap()
    staticOn.forEach { p -> state[p] = '#' }

    repeat(steps) {
        val next = state.toMutableMap()
        (0 until size).forEach { y ->
            (0 until size).forEach { x ->
                val p = Vec2i(x, y)
                if (p !in staticOn) {
                    val isLit = state[p] == '#'
                    val litNeighbors = p.neighbors(diagonal = true)
                        .count { state[it] == '#' }
                    next[p] = when {
                        isLit && litNeighbors in 2..3 -> '#'
                        !isLit && litNeighbors == 3 -> '#'
                        else -> '.'
                    }
                }
            }
        }
        state = next
    }
    return state
}
