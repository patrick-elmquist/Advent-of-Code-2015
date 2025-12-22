package day17

import common.day
import common.util.out

// answer #1: 654
// answer #2: 57

fun main() {
    day(n = 17) {
        part1 { input ->
            val goal = if (input.lines.size == 5) 25 else 150
            val containers = input.lines.mapIndexed { i, it -> i to it.toInt() }
            rec(
                usedContainers = emptyList(),
                availableContainers = containers,
                goal = goal,
            ).map { it.toSet() }.toSet().size
        }
        verify {
            expect result 654
            run test 1 expect 4
        }

        part2 { input ->
            val goal = if (input.lines.size == 5) 25 else 150
            val containers = input.lines.mapIndexed { i, it -> i to it.toInt() }
            val versions = rec(
                usedContainers = emptyList(),
                availableContainers = containers,
                goal = goal,
            )

            val min = versions.minOf { it.size }
            out(min)

            rec(
                usedContainers = emptyList(),
                availableContainers = containers,
                goal = goal,
                max = min,
            ).also { out(it) }.map { it.toSet() }.toSet().size
        }
        verify {
            expect result 57
            run test 1 expect 3
        }
    }
}

private fun rec(
    usedContainers: List<Pair<Int, Int>>,
    availableContainers: List<Pair<Int, Int>>,
    goal: Int = 150,
    max: Int? = null,
): List<List<Pair<Int, Int>>> {
    val sum = usedContainers.sumOf { it.second }
    return when {
        max != null && usedContainers.size > max -> emptyList()
        sum == goal -> listOf(usedContainers)
        sum > goal -> emptyList()
        availableContainers.isEmpty() -> emptyList()
        else -> {
            availableContainers.flatMap { container ->
                rec(
                    usedContainers = usedContainers + container,
                    availableContainers = availableContainers - container,
                    goal = goal,
                    max = max,
                )
            }
        }
    }
}
