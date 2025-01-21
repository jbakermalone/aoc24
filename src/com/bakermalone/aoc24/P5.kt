package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val (l1, l2) = readLines("p5").partition { it.contains('|') }
    val ruleText = l1.map { it.split('|').map { it.toInt()} }
    val rules = ruleText.map { (f, s) ->
        { l: List<Int> -> f !in l || s !in l || l.indexOf(f) < l.indexOf(s) }
    }
    val pages = l2.map { it.split(',').map { it.toInt() } }
    val good = p5a(rules, pages)
    val bad = pages - good
    val rules2 = ruleText.map { (f, s) -> f to s}.toSet()
    p5b(bad, rules2)
}

fun p5b(bad: List<List<Int>>, rules: Set<Pair<Int, Int>>) {
    println(bad.sumOf { b ->
        val left = b.toMutableList()
        val result = mutableListOf<Int>()
        while (left.isNotEmpty()) {
            val v = left.first { t ->
                left.minus(t).all { o ->
                    (o to t) !in rules
                }
            }
            result.add(v)
            left.remove(v)
        }
        println(result)
        result[result.size / 2]
    })
}

fun p5a(rules: List<(List<Int>) -> Boolean>, pages: List<List<Int>>): List<List<Int>> {
    val good = pages.filter { p -> rules.all { it(p) }}
    //println(good.sumOf { it[it.size / 2] })
    return good
}