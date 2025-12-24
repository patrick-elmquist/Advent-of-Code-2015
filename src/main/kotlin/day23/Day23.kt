package day23

import common.day

// answer #1: 307
// answer #2: 160

fun main() {
    day(n = 23) {
        part1 { input ->
            val instructions = input.lines
            val registers = mutableMapOf(
                "a" to 0,
                "b" to 0,
            )
            executeInstructions(instructions, registers)
            registers["b"]
        }
        verify {
            expect result 307
            run test 1 expect 0
        }

        part2 { input ->
            val instructions = input.lines
            val registers = mutableMapOf(
                "a" to 1,
                "b" to 0,
            )
            executeInstructions(instructions, registers)
            registers["b"]
        }
        verify {
            expect result 160
        }
    }
}

private fun executeInstructions(
    instructions: List<String>,
    registers: MutableMap<String, Int>,
) {
    var pointer = 0
    while (true) {
        if (pointer !in instructions.indices) break

        val instruction = instructions[pointer]

        val split = instruction.split(" ")
        when (split[0]) {
            "hlf" -> {
                registers.computeIfPresent(split[1]) { _, v -> v / 2 }
                pointer++
            }

            "tpl" -> {
                registers.computeIfPresent(split[1]) { _, v -> v * 3 }
                pointer++
            }

            "inc" -> {
                registers.computeIfPresent(split[1]) { _, v -> v + 1 }
                pointer++
            }

            "jmp" -> {
                pointer += split[1].toInt()
            }

            "jie" -> {
                val value = registers.getValue(split[1].dropLast(1))
                if (value % 2 == 0) {
                    pointer += split[2].toInt()
                } else {
                    pointer++
                }
            }

            "jio" -> {
                val value = registers.getValue(split[1].dropLast(1))
                if (value == 1) {
                    pointer += split[2].toInt()
                } else {
                    pointer++
                }
            }
        }
    }
}
