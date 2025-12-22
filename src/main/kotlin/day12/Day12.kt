package day12

import common.day
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray

// answer #1: 156366
// answer #2: 96852

fun main() {
    day(n = 12) {
        part1 { input ->
            """(-?\d+)""".toRegex()
                .findAll(input.lines.first())
                .sumOf { it.value.toInt() }
        }
        verify {
            expect result 156366
        }

        part2 { input ->
            calculateSum(input.lines.first())
        }
        verify {
            expect result 96852
        }
    }
}

private fun calculateSum(jsonString: String): Int {
    val json = Json.parseToJsonElement(jsonString)
    val queue = mutableListOf(json)
    var sum = 0
    while (queue.isNotEmpty()) {
        when (val element = queue.removeLast()) {
            is JsonArray -> queue.addAll(element.jsonArray)
            is JsonObject -> {
                val pendingElements = mutableListOf<JsonElement>()
                var pendingSum = 0
                var isValid = true
                element.values.forEach {
                    when (it) {
                        is JsonArray,
                        is JsonObject -> pendingElements.add(it)

                        is JsonPrimitive -> {
                            if (it.content == "red") isValid = false
                            pendingSum += it.intOrNull ?: 0
                        }
                    }
                }
                if (isValid) {
                    sum += pendingSum
                    queue.addAll(pendingElements)
                }
            }
            is JsonPrimitive -> sum += element.intOrNull ?: 0
        }
    }
    return sum
}
