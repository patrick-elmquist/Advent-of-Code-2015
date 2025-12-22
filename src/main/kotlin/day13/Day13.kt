package day13

import common.Input
import common.day

// answer #1: 664
// answer #2: 640

fun main() {
    day(n = 13) {
        part1 { input ->
            val rankings = parseRankings(input)
            maximizeHappiness(rankings)
        }
        verify {
            expect result 664
            run test 1 expect 330
        }

        part2 { input ->
            val rankings = parseRankings(input)
            rankings["m"] = rankings.keys.associateWith { 0 }.toMutableMap()
            rankings.forEach { (_, map) -> map["m"] = 0 }

            maximizeHappiness(rankings)
        }
        verify {
            expect result 640
        }
    }
}

private fun maximizeHappiness(allRankings: Map<String, Map<String, Int>>): Int =
    getPermutations(allRankings.keys.toList()).maxOf { variant ->
        variant
            .mapIndexed { index, name ->
                val rankings = allRankings.getValue(name)
                val left = variant[(index + variant.size - 1) % variant.size]
                val right = variant[(index + variant.size + 1) % variant.size]
                rankings.getValue(left) + rankings.getValue(right)
            }
            .sum()
    }

private fun <T> getPermutations(list: List<T>): List<List<T>> {
    if (list.isEmpty()) return listOf(emptyList())
    return list.flatMap { item ->
        // Get permutations of the list WITHOUT the current item
        val remaining = list - item

        // Add the current item to the front of every sub-permutation
        getPermutations(remaining).map { subPermutation ->
            listOf(item) + subPermutation
        }
    }
}

private fun parseRankings(input: Input): MutableMap<String, MutableMap<String, Int>> {
    val rankings = mutableMapOf<String, MutableMap<String, Int>>()
    input.lines.forEach {
        val split = it.split(" ")
        val name = split[0]
        val sign = split[2].let { s -> if (s == "gain") 1 else -1 }
        val amount = split[3].toInt()
        val otherPerson = split.last().dropLast(1)
        val map = rankings.getOrPut(name) { mutableMapOf() }
        map[otherPerson] = sign * amount
    }
    return rankings
}
