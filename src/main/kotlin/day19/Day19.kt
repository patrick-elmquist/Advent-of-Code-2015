package day19

import common.day
import common.util.out
import common.util.sliceByBlankLine

// answer #1: 576
// answer #2: 207

fun main() {
    day(n = 19) {
        part1 { input ->
            val (rules, string) = input.lines.sliceByBlankLine()
                .let { (a, b) ->
                    a.map { it.split(" => ") } to b.first()
                }

            val set = mutableSetOf<String>()
            for ((a, b) in rules) {
                var start = ""
                var end = string
                while (true) {
                    val index = end.indexOf(a)
                    if (index == -1) break

                    val i = end.substring(0, index)
                    val j = end.substring(index + a.length)

                    set += start + i + b + j
                    start += i + a
                    end = j
                }
            }
            set.size
        }
        verify {
            expect result 576
            run test 1 expect 4
            run test 2 expect 7
        }

        part2 { input ->
            val (_, endState) = input.lines.sliceByBlankLine()
                .let { (a, b) ->
                    a.map { it.split(" => ") }.sortedByDescending {
                        it.last().length - it.first().length
                    } to b.first()
                }

            val elements = endState.count { it.isUpperCase() }
            val rns = """(Rn)""".toRegex().findAll(endState).count()
            val ars = """(Ar)""".toRegex().findAll(endState).count()
            val ys = """(Y)""".toRegex().findAll(endState).count() * 2
            out(elements, rns, ars, ys)
            elements - rns - ars - ys - 1
        }
        verify {
            expect result 207
        }
    }
}
