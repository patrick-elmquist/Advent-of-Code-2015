package day07

import common.day

// answer #1: 956
// answer #2: 40149

fun main() {
    day(n = 7) {
        part1 { input ->
            findValueOfA(input.lines)
        }
        verify {
            expect result 956.toUShort()
        }

        part2 { input ->
            val lines = input.lines
                .map { if (it.endsWith(" -> b")) "956 -> b" else it }
            findValueOfA(lines)
        }
        verify {
            expect result 40149.toUShort()
        }
    }
}

private fun findValueOfA(instructions: List<String>): UShort {
    val queue = ArrayDeque<List<String>>()
    queue.addAll(instructions.map { it.split(" ") })
    val wires = mutableMapOf<String, UShort>()

    fun List<String>.lookup(i: Int): UShort? =
        get(i).let { it.toUShortOrNull() ?: wires[it] }

    while (queue.isNotEmpty()) {
        val split = queue.removeFirst()
        val value: UShort? = when {
            "AND" in split -> {
                val a = split.lookup(0)
                val b = split.lookup(2)
                if (a == null || b == null) null else a and b
            }

            "OR" in split -> {
                val a = split.lookup(0)
                val b = split.lookup(2)
                if (a == null || b == null) null else a or b
            }

            "LSHIFT" in split -> {
                val a = split.lookup(0)
                a?.let { (a.toInt() shl split[2].toInt()).toUShort() }
            }

            "RSHIFT" in split -> {
                val a = split.lookup(0)
                a?.let { (a.toInt() shr split[2].toInt()).toUShort() }
            }

            "NOT" in split -> {
                split.lookup(1)?.inv()
            }

            else -> {
                split.lookup(0)
            }
        }

        if (value == null) {
            queue += split
        } else {
            wires[split.last()] = value
        }
    }

    return wires.getValue("a")
}
