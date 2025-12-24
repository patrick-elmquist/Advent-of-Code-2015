package day24

import common.day
import common.util.out

// answer #1: 11846773891
// answer #2: 80393059

fun main() {
    day(n = 24) {
        part1 { input ->
            val packets = input.lines.map { it.toLong() }

            val totalWeight = packets.sum()
            val third = totalWeight / 3
            val validGroups = findCombinationsWithSum(packets, third)

            val minSize = validGroups.minOf { it.size }
            val g1 = validGroups.filter { it.size == minSize }.toSet()
                .minByOrNull { it.reduce(Long::times) }!!
            validGroups.filter { it.none { it in g1 } }
                .forEach { g2 ->
                    validGroups
                        .filter { it.none { it in g1 } && it.none { it in g2 } }
                        .forEach { g3 ->
                            out(g1, g2, g3)
                            return@part1 g1.reduce(Long::times)
                        }
                }
        }
        verify {
            expect result 11846773891L
            run test 1 expect 99L
        }

        part2 { input ->
            val packets = input.lines.map { it.toLong() }

            val totalWeight = packets.sum()
            val third = totalWeight / 4
            val validGroups = findCombinationsWithSum(packets, third)

            val minSize = validGroups.minOf { it.size }
            val g1 = validGroups.filter { it.size == minSize }.toSet()
                .minByOrNull { it.reduce(Long::times) }!!
            validGroups.filter { it.none { it in g1 } }
                .forEach { g2 ->
                    validGroups
                        .filter { it.none { it in g1 } && it.none { it in g2 } }
                        .forEach { g3 ->
                            validGroups
                                .filter { it.none { it in g1 } && it.none { it in g2 } && it.none { it in g3 } }
                                .forEach { g4 ->
                                    out(g1, g2, g3, g4)
                                    return@part2 g1.reduce(Long::times)
                                }
                        }
                }

        }
        verify {
            expect result 80393059L
            run test 1 expect 44L
        }
    }
}

fun findCombinationsWithSum(numbers: List<Long>, target: Long): Set<List<Long>> {
    val results = mutableSetOf<List<Long>>()
    // Sorting descending helps prune the search space faster
    val sortedNumbers = numbers.sortedDescending()

    fun backtrack(index: Int, currentSum: Long, currentPath: MutableList<Long>) {
        if (currentSum == target) {
            results.add(currentPath.toList())
            return
        }

        if (currentSum > target || index >= sortedNumbers.size) {
            return
        }

        val num = sortedNumbers[index]

        // 1. Include the current number
        currentPath.add(num)
        backtrack(index + 1, currentSum + num, currentPath)
        currentPath.removeAt(currentPath.lastIndex)

        // 2. Exclude the current number
        // Optimization: Skip identical numbers to avoid generating the same combination
        // via a different path (e.g., skipping the first '5' but picking the second '5')
        var nextIndex = index + 1
        while (nextIndex < sortedNumbers.size && sortedNumbers[nextIndex] == num) {
            nextIndex++
        }
        backtrack(nextIndex, currentSum, currentPath)
    }

    backtrack(0, 0, mutableListOf())
    return results
}
