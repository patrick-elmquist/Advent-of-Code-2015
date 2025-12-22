package day14

import common.Input
import common.day

// answer #1: 2660
// answer #2: 1256

fun main() {
    day(n = 14) {
        part1 { input ->
            val (n, reindeer) = parseData(input)
            val distances = reindeer.associateWith { 0 }.toMutableMap()
            val state = reindeer.associateWith { 0 }.toMutableMap()
            repeat(n) {
                reindeer
                    .forEach {
                        val value = state.getValue(it)
                        when {
                            value in 0 until it.duration -> {
                                distances.computeIfPresent(it, { a, b -> b + it.speed } )
                            }

                            value >= it.duration -> {
                                state[it] = it.rest * -1
                            }
                        }
                    }
                state.entries.forEach { (key, value) ->
                    state[key] = value + 1
                }
            }
            distances.values.max()
        }
        verify {
            expect result 2660
            run test 1 expect 1120
        }

        part2 { input ->
            val (n, reindeer) = parseData(input)
            val distances = reindeer.associateWith { 0 }.toMutableMap()
            val state = reindeer.associateWith { 0 }.toMutableMap()
            val points = reindeer.associateWith { 0 }.toMutableMap()
            repeat(n) {
                reindeer
                    .forEach {
                        val value = state.getValue(it)
                        when {
                            value in 0 until it.duration -> {
                                distances.computeIfPresent(it, { a, b -> b + it.speed } )
                            }

                            value >= it.duration -> {
                                state[it] = it.rest * -1
                            }
                        }
                    }

                val maxDistance = distances.values.max()
                distances.entries
                    .filter { it.value == maxDistance }
                    .forEach { (key, _) ->
                        points[key] = points[key]!! + 1
                    }
                state.entries.forEach { (key, value) ->
                    state[key] = value + 1
                }
            }
            points.values.max()
        }
        verify {
            expect result 1256
            run test 1 expect 689
        }
    }
}

private data class Reindeer(
    val name: String,
    val speed: Int,
    val duration: Int,
    val rest: Int,
)

private fun parseData(input: Input): Pair<Int, List<Reindeer>> {
    val n = input.lines.first().toInt()
    val reindeer = input.lines.drop(1).map {
        val split = it.split(" ")
        val name = split.first()
        val speed = split[3].toInt()
        val duration = split[6].toInt()
        val rest = split.dropLast(1).last().toInt()
        Reindeer(name, speed, duration, rest)
    }
    return Pair(n, reindeer)
}
