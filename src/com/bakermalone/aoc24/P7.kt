package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val input = readLines("p7").map {
        it.split(": ").let { (t, l) ->
            t.toLong() to l.split(" ").map { it.toLong() }
        }
    }
    p7b(input)
}

fun p7a(input: List<Pair<Long, List<Long>>>) {
    println(input.sumOf { (target, values) ->
        var subtotals = listOf(values[0])
        values.subList(1, values.size).forEach { v ->
            subtotals = subtotals.flatMap { listOf(it + v, it * v) }
        }
        if (target in subtotals) target else 0L
    })
}

fun p7b(input: List<Pair<Long, List<Long>>>) {
    println(input.sumOf { (target, values) ->
        var subtotals = listOf(values[0])
        values.subList(1, values.size).forEach { v ->
            subtotals = subtotals.flatMap { listOf(it + v, it * v, "$it$v".toLong()) }
        }
        if (target in subtotals) target else 0L
    })
}
