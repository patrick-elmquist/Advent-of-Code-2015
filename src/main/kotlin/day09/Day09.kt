package day09

import common.Input
import common.day
import common.util.match

// answer #1: 141
// answer #2: 736

fun main() {
    day(n = 9) {
        part1 { input ->
            val map = parseMap(input)
            map.keys
                .flatMap { city ->
                    findRouteDistances(from = city, map = map)
                }
                .min()
        }
        verify {
            expect result 141
            run test 1 expect 605
        }

        part2 { input ->
            val map = parseMap(input)
            map.keys
                .flatMap { city ->
                    findRouteDistances(from = city, map = map)
                }
                .max()
        }
        verify {
            expect result 736
            run test 1 expect 982
        }
    }
}

private fun findRouteDistances(
    from: String,
    map: Map<String, Map<String, Int>>,
    visited: Set<String> = setOf(from),
    totalDistance: Int = 0,
): List<Int> {
    if (visited == map.keys) return listOf(totalDistance)
    return map.getValue(from)
        .filterKeys { it !in visited }
        .flatMap { (city, distance) ->
            findRouteDistances(
                from = city,
                map = map,
                visited = visited + city,
                totalDistance = totalDistance + distance,
            )
        }
}

private fun parseMap(input: Input): MutableMap<String, MutableMap<String, Int>> {
    val map = mutableMapOf<String, MutableMap<String, Int>>()
    val regex = """(.+) to (.+) = (\d+)""".toRegex()
    input.lines.forEach { line ->
        val (from, to, distance) = regex.match(line)
        map.getOrPut(from) { mutableMapOf() }[to] = distance.toInt()
        map.getOrPut(to) { mutableMapOf() }[from] = distance.toInt()
    }
    return map
}
