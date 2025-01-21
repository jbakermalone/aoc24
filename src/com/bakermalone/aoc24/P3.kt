package com.bakermalone.aoc24

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readString(Paths.get("data", "p3"))
    p3b(input)
}

fun p3a(input: String): Int {
    val matches = Regex("mul\\(([0-9]+),([0-9]+)\\)").findAll(input)
    val result = matches.sumOf { it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt() }
    return result
}

fun p3b(inp: String) {
    val input = inp.replace('\n', ' ')
    val splits = input.split(Regex("don't\\(\\).*?do\\(\\)")).toMutableList()
    splits[splits.size - 1] = splits[splits.size - 1].substringBefore(("don't()"))
    println(splits.find { it.contains("don't()") })
    println(splits.sumOf { p3a(it)})
}