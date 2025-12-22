package day14

import common.Input
import common.day

// answer #1: 2660
// answer #2: 1256

fun main() {
    day(n = 14) {
        part1 { input ->
            val (seconds, reindeer) = parseData(input)
            val (distances, _) = calculatePointsAndDistance(reindeer, seconds)
            distances.values.max()
        }
        verify {
            expect result 2660
            run test 1 expect 1120
        }

        part2 { input ->
            val (seconds, reindeer) = parseData(input)
            val (_, points) = calculatePointsAndDistance(reindeer, seconds)
            points.values.max()
        }
        verify {
            expect result 1256
            run test 1 expect 689
        }
    }
}

private fun calculatePointsAndDistance(
    reindeer: List<Reindeer>,
    seconds: Int,
): Pair<MutableMap<Reindeer, Int>, MutableMap<Reindeer, Int>> {
    val distances = reindeer.associateWith { 0 }.toMutableMap()
    val state = reindeer.associateWith { 0 }.toMutableMap()
    val points = reindeer.associateWith { 0 }.toMutableMap()
    repeat(seconds) {
        state.forEach { (it, value) ->
            when {
                value in 0 until it.duration ->
                    distances.computeIfPresent(it) { _, b -> b + it.speed }

                value >= it.duration ->
                    state[it] = it.rest * -1
            }
        }

        val maxDistance = distances.values.max()
        distances.entries
            .filter { it.value == maxDistance }
            .forEach { (key, _) ->
                points[key] = points.getValue(key) + 1
            }

        state.entries
            .forEach { (key, value) ->
                state[key] = value + 1
            }
    }
    return Pair(distances, points)
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
