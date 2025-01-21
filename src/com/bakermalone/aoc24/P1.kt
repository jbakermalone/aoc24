package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines
import java.util.regex.Pattern
import kotlin.math.abs

fun main() {
    val pairs = readLines("p1").map {
        it.split(Pattern.compile("  *"))
    }
    val first = pairs.map { it.first().toInt() }.sorted()
    val second = pairs.map { it.get(1).toInt() }.sorted()
    p1b(first, second)
}

fun p1a(first: List<Int>, second: List<Int>) {
    println(first.zip(second).sumOf { (a, b) -> abs(a - b) })
}

fun p1b(first: List<Int>, second: List<Int>) {
    val s = second.groupBy { it }.mapValues { it.value.size }
    println(first.sumOf { it * s.getOrDefault(it, 0) })
}