package day16

import common.Input
import common.day
import common.util.match

// answer #1: 373
// answer #2: 260

fun main() {
    day(n = 16) {
        part1 { input ->
            var aunts = parseAunts(input)

            attrs.forEach { (attr, count) ->
                aunts = aunts.filter { (_, values) ->
                    values[attr] == null || values[attr] == count
                }
            }

            aunts.entries.single().key
        }
        verify {
            expect result 373
        }

        part2 { input ->
            var aunts = parseAunts(input)

            attrs.forEach { (attribute, count) ->
                aunts = aunts.filter { (_, auntMap) ->
                    val value = auntMap[attribute]
                    if (value == null) {
                        true
                    } else {
                        when (attribute) {
                            "cats",
                            "trees" -> value > count
                            "goldfish",
                            "pomeranians" -> value < count
                            else -> value == count
                        }
                    }
                }
            }

            aunts.entries.single().key
        }
        verify {
            expect result 260
        }
    }
}

private val regex = """Sue (\d+): (.*)""".toRegex()

private val attrs = mapOf(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1,
)

private fun parseAunts(input: Input): Map<Int, Map<String, Int>> =
    input.lines.associate {
        val (name, other) = regex.match(it)
        name.toInt() to other.trim().split(", ").associate {
            val (key, value) = it.split(": ")
            key to value.toInt()
        }
    }
