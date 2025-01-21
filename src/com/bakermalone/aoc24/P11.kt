package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val input = readLines("p11")[0].split(' ').map { it.toLong() }
    p11a(input)
    p11b(input)
}

sealed interface Product
class Unresolved(val p1: Pair<Long, Int>, val p2: Pair<Long, Int>?) : Product
class Resolved(val v: Long) : Product

fun p11b(input: List<Long>) {
    val start = input.map { it to 75 }
    val need = start.toMutableSet()
    val have = mutableMapOf<Pair<Long, Int>, Product>()

    while (need.isNotEmpty()) {
        val item = need.first()
        need.remove(item)
        val value = item.first
        val length = value.toString().length
        val next = item.second - 1
        val new = when {
            item.second == 0 -> Resolved(1)
            value == 0L -> {
                Unresolved(1L to next, null)
            }
            length % 2 == 0 -> Unresolved(
                value.toString().substring(0, length / 2).toLong() to next,
                value.toString().substring(length / 2).toLong() to next
            )
            else -> Unresolved(value * 2024 to next, null)
        }
        have[item] = new
        if (new is Unresolved) {
            need.add(new.p1)
            new.p2?.let { need.add(it) }
        }
    }
    for (i in 1..75) {
        val new = have.filterKeys { it.second == i }.mapValues { (k, v) ->
            if (v is Resolved) v
            else Resolved((have[(v as Unresolved).p1] as Resolved).v + (
                    v.p2?.let { (have[v.p2] as Resolved).v } ?: 0))
        }
        have.putAll(new)
    }
    println(start.sumOf { (have[it] as Resolved).v })
}

fun p11a(input: List<Long>) {
    var s = input
    repeat(25) {
        s = s.flatMap {
            val length = it.toString().length
            when {
                it == 0L -> listOf(1L)
                length % 2 == 0 -> listOf(
                    it.toString().substring(0, length / 2).toLong(),
                    it.toString().substring(length / 2).toLong()
                )
                else -> listOf(it * 2024)
            }
        }
    }
    assert(s.none { it < 0 })
    println(s.size)
}
